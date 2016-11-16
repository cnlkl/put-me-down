package com.fzuclover.putmedown.features.totaltimingtoday;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.IRecordModel;
import com.fzuclover.putmedown.model.IUserModel;
import com.fzuclover.putmedown.model.RecordModel;
import com.fzuclover.putmedown.model.UserModel;
import com.fzuclover.putmedown.model.bean.Achievement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lkl on 2016/11/4.
 */

public class TotalTimingTodayPresenter implements TotalTimingTodayContract.Presenter {

    private TotalTimingTodayContract.View mView;
    private IRecordModel mRecordModel;
    private IUserModel mUserModel;
    private IAchievementModel mAchievementModel;

    public TotalTimingTodayPresenter(TotalTimingTodayContract.View view){
        mView = view;
        mRecordModel = RecordModel.getInstance((Context)view);
        mUserModel = UserModel.getInstance((Context)view);
        mAchievementModel = AchievementModel.getAchieveMentModelInstance((Context)view);

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
            //保存前一天的数据到数据库
            int successTimesToday = sharedPreferences.getInt("success_times_today", 0);
            int failedTImesToday = sharedPreferences.getInt("failed_times_today", 0);
            int timedToday = sharedPreferences.getInt("timed_today", 0);
            mAchievementModel.saveAchievementEveryDay(date, timedToday, successTimesToday, failedTImesToday);
            //保存新一天的数据
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("date", dateNow);
            editor.putInt("timed_today", 0);
            editor.putInt("success_times_today",0);
            editor.putInt("failed_times_today", 0);
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
