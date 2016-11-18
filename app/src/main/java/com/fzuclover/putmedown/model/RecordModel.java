package com.fzuclover.putmedown.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.fzuclover.putmedown.model.bean.Record;
import com.fzuclover.putmedown.model.db.DBHelper;
import com.fzuclover.putmedown.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 */

public class RecordModel implements IRecordModel{

    private static RecordModel mRecordModel;

    private DBHelper mDbHelper;

    private RecordModel(Context context){
        if(context != null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            LogUtil.d("db_name",sharedPreferences.getString("username", "root")+".db");
            mDbHelper = new DBHelper(context, sharedPreferences.getString("username", "root") + ".db", null, 1);
        }
    }

    public static RecordModel getInstance(Context context){
        if(mRecordModel == null){
            mRecordModel = new RecordModel(context);
        }
        return mRecordModel;
    }

    @Override
    public int saveTimingRecord(int totalTime, String comments) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(new Date());

        ContentValues values = new ContentValues();
        values.put("start_time", date);
        values.put("total_time", totalTime);
        if (!TextUtils.isEmpty(comments)){
            values.put("timing_comments", comments);
        }else {
            values.put("timing_comments", "");
        }

        db.insert("timing_record", null, values);

        //获取本条记录的id
        Cursor cursor = db.query("timing_record", null, "start_time=?", new String[]{date}, null, null, null);
        int id = 0;
        if(cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        LogUtil.d("recoid_id", String.valueOf(id));

        return id;
    }

    @Override
    public void updateTimingRecord(Boolean isSuccess, int id, String failComment) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String date = simpleDateFormat.format(new Date());

        ContentValues values = new ContentValues();
        if(isSuccess){
            values.put("is_success", 1);
        }else{
            values.put("is_success", 0);
            values.put("fail_comments", failComment);
            values.put("end_time", date);
            LogUtil.d("fail_comments", failComment);
        }

        db.update("timing_record", values, "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public List<Record> getTimingRecord() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Record> records = new ArrayList<Record>();

        Cursor cursor = db.query("timing_record", null, null, null, null, null, "id desc");
        if(cursor.moveToFirst()){
            do {
                Record record = new Record();
                int isSuccess = cursor.getInt(cursor.getColumnIndex("is_success"));
                if(isSuccess == 1) {
                    record.setSuccess(true);
                }else {
                    record.setSuccess(false);
                    record.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
                    record.setFailComents(cursor.getString(cursor.getColumnIndex("fail_comments")));
                }
                record.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
                record.setComment(cursor.getString(cursor.getColumnIndex("timing_comments")));
                record.setTotalTime(cursor.getInt(cursor.getColumnIndex("total_time")));

                records.add(record);
            } while (cursor.moveToNext());
        }
        return records;
    }

    public void close(){
        mRecordModel = null;
    }
}
