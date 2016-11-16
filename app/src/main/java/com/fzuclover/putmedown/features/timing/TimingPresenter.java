package com.fzuclover.putmedown.features.timing;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.IRecordModel;
import com.fzuclover.putmedown.model.RecordModel;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by lkl on 2016/11/4.
 */

public class TimingPresenter implements TimingContract.Presenter {

    private TimingContract.View mView;
    private IRecordModel mRecordModel;
    private IAchievementModel mAchievementModel;

    public TimingPresenter(TimingContract.View view){
        mView = view;
        mRecordModel = RecordModel.getInstance((Context)view);
        mAchievementModel = AchievementModel.getAchieveMentModelInstance((Context)view);
    }

    @Override
    public void saveTimedToday(Context context, int time) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = sharedPreferences.getString("date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)) {
            editor.putInt("timed_today", (sharedPreferences.getInt("timed_today", 0) + time));
            editor.putInt("success_times_totay", (sharedPreferences.getInt("success_times_today", 0) + 1));
            editor.commit();
        }else {
            //保存前一天的数据到数据库
            int successTimesToday = sharedPreferences.getInt("success_times_today", 0);
            int failedTImesToday = sharedPreferences.getInt("failed_times_today", 0);
            int timedToday = sharedPreferences.getInt("timed_today", 0);
            mAchievementModel.saveAchievementEveryDay(date, timedToday, successTimesToday, failedTImesToday);
            //存入新一天的数据
            editor.putInt("timed_today", time);
            editor.putString("date", dateNow);
            editor.putInt("success_times_today",1);
            editor.putInt("failed_times_today", 0);
            editor.commit();
        }
    }
    //TODO 0点计时可能数据错误
    @Override
    public void updateTimingRecord() {
        mRecordModel.updateTimingRecord(mView.isSuccess(), mView.getRecordId(), mView.getFailComments());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)mView);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = sharedPreferences.getString("date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)) {
            editor.putInt("failed_times_today", sharedPreferences.getInt("failed_times_today", 0) + 1);
            editor.commit();
        }else {
            //保存前一天的数据到数据库
            int successTimesToday = sharedPreferences.getInt("success_times_today", 0);
            int failedTImesToday = sharedPreferences.getInt("failed_times_today", 0);
            int timedToday = sharedPreferences.getInt("timed_today", 0);
            mAchievementModel.saveAchievementEveryDay(date, timedToday, successTimesToday, failedTImesToday);
            //存入新一天的数据
            editor.putInt("timed_today", 0);
            editor.putString("date", dateNow);
            editor.putInt("success_times_today",0);
            editor.putInt("failed_times_today", 1);
            editor.commit();
        }
    }
}
