package com.fzuclover.putmedown.features.totaltimingtoday;

import android.content.Context;

import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.IRecordModel;
import com.fzuclover.putmedown.model.IUserModel;
import com.fzuclover.putmedown.model.RecordModel;
import com.fzuclover.putmedown.model.UserModel;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.utils.SharePreferenceUtil;

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
    private SharePreferenceUtil mSharePreferenceUtil;

    public TotalTimingTodayPresenter(TotalTimingTodayContract.View view){
        mView = view;
        mRecordModel = RecordModel.getInstance((Context)view);
        mUserModel = UserModel.getInstance((Context)view);
        mAchievementModel = AchievementModel.getAchieveMentModelInstance((Context)view);
        mSharePreferenceUtil = SharePreferenceUtil.getInstance((Context) view);
    }

    @Override
    public void saveTargetTime(int targetTime) {
        mSharePreferenceUtil.saveTargetTime(targetTime);
    }

    @Override
    public int getTargetTime() {
        return mSharePreferenceUtil.getTargetTime();
    }

    @Override
    public int getTimedToday() {
        String date = mSharePreferenceUtil.getDate();
        DayAchievement dayAchievement = mAchievementModel.getDayAchievement(date);
        int totalTime = dayAchievement.getTotal_time();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());

        if(date.equals(dateNow)) {
            return totalTime;
        }else {
            mSharePreferenceUtil.saveDate(dateNow);
            mAchievementModel.saveAchievementEveryDay(dateNow, 0, 0, 0);
            return 0;
        }

    }

    @Override
    public int saveTimingRecord(int totalTime, String comments) {
        return mRecordModel.saveTimingRecord(totalTime, comments);
    }

    @Override
    public void setLoginStatu(boolean b) {
        mSharePreferenceUtil.saveLoginStatu(b);
        mSharePreferenceUtil.saveUserName("");
        RecordModel.getInstance((Context)mView).close();
        AchievementModel.getAchieveMentModelInstance((Context)mView).close();
        mSharePreferenceUtil.close();
    }


}
