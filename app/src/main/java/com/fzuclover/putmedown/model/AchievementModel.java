package com.fzuclover.putmedown.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 */

public class AchievementModel implements IAchievementModel{
    private static AchievementModel mAchievementModel;

    private DBHelper mDbHelper;

    private AchievementModel(Context context){
        if(context != null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            mDbHelper = new DBHelper(context, sharedPreferences.getString("username", "root") + ".db", null, 1);
        }
    }
    public static IAchievementModel getAchieveMentModelInstance(Context context) {
        if(mAchievementModel == null){
            mAchievementModel = new AchievementModel(context);
        }
        return mAchievementModel;
    }

    @Override
    public Achievement getAchievement() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Achievement achievement = new Achievement();

        Cursor cursor = db.rawQuery("select sum(total_time_today), "
               + "sum(success_times), "
               + "sum(failed_times)"
               + "from achievement", null);

        if(cursor.moveToFirst()){
            achievement.setTotalTime(cursor.getInt(cursor.getColumnIndex("sum(total_time_today)")));
            achievement.setTotalSuccess(cursor.getInt(cursor.getColumnIndex("sum(success_times)")));
            achievement.setTotalFailed(cursor.getInt(cursor.getColumnIndex("sum(failed_times)")));
        }

        return achievement;
    }

    @Override
    public List<DayAchievement> getDayAchievements() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<DayAchievement> dayAchievements = new ArrayList<DayAchievement>();
        DayAchievement dayAchievement;

        Cursor cursor = db.query(true, "achievement", null, null, null, "_date", null, "id desc", null);
        if(cursor.moveToFirst()){
            do {
                dayAchievement = new DayAchievement();
                dayAchievement.setDate(cursor.getString(cursor.getColumnIndex("_date")));
                dayAchievement.setTotal_time(cursor.getInt(cursor.getColumnIndex("total_time_today")));
                dayAchievement.setSucces_times(cursor.getInt(cursor.getColumnIndex("success_times")));
                dayAchievement.setFailed_times(cursor.getInt(cursor.getColumnIndex("failed_times")));
                dayAchievements.add(dayAchievement);
            }while (cursor.moveToNext());
        }


        return dayAchievements;
    }


    @Override
    public void saveAchievementEveryDay(String date, int totalTime, int successTimes, int failedTimes) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_date", date);
        values.put("total_time_today", totalTime);
        values.put("success_times", successTimes);
        values.put("failed_times", failedTimes);

        db.insert("achievement", null, values);

    }
    @Override
    public void close(){
        mAchievementModel = null;
    }
}
