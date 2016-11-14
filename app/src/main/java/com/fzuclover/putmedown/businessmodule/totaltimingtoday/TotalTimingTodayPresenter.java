package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.fzuclover.putmedown.model.IRecordModel;
import com.fzuclover.putmedown.model.IUserModel;
import com.fzuclover.putmedown.model.db.TimingRecordDBHelper;
import com.fzuclover.putmedown.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lkl on 2016/11/4.
 */

public class TotalTimingTodayPresenter implements TotalTimingTodayContract.Presenter {

    private TotalTimingTodayContract.View mView;
    private IRecordModel mRecordModel;
    private IUserModel mUserModel;

    public TotalTimingTodayPresenter(TotalTimingTodayContract.View view, IRecordModel recordModel,
                                     IUserModel userModel){
        mView = view;
        mRecordModel = recordModel;
        mUserModel = userModel;
    }

    @Override
    public void saveTargetTime(Context context, int targetTime) {
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt("target_time",targetTime);
        editor.commit();
    }

    @Override
    public int getTargetTime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("target_time",180);
    }

    @Override
    public int getTimedToday(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String date = sharedPreferences.getString("date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)){
            return sharedPreferences.getInt("timed_today", 0);
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("date", dateNow);
            editor.putInt("timed_today", 0);
            editor.commit();
            return 0;
        }
    }

    @Override
    public int saveTimingRecord(Context context, int totalTime, String comments) {
        int id = 0;
        id = mRecordModel.saveTimingRecord(totalTime, comments);
        return id;
    }


}
