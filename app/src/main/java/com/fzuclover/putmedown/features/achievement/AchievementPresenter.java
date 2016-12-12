package com.fzuclover.putmedown.features.achievement;

import android.content.Context;

import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.utils.LogUtil;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 */

public class AchievementPresenter implements AchievementContract.Presenter {

    AchievementContract.View mView;

    IAchievementModel mAchievementModel;

    public AchievementPresenter(AchievementContract.View view) {
        this.mView = view;
        mAchievementModel = AchievementModel.getAchieveMentModelInstance((Context)mView);
    }

    @Override
    public Achievement getAchievement() {
        return mAchievementModel.getAchievement();
    }

    @Override
    public int getTotalTime() {
        return getAchievement().getTotalTime();
    }

    @Override
    public List<DayAchievement> getDayAchievements() {
        return mAchievementModel.getDayAchievements();
    }

    @Override
    public List<Integer> getDaySuccessTimes() {
        List<Integer> list = new ArrayList<Integer>();
        List<DayAchievement> achievements = getDayAchievements();
        for(int i = 6;i >= 0;i--){
            list.add(achievements.get(i).getSucces_times());
        }
        return list;
    }

    @Override
    public List<Integer> getDayFailedTimes() {
        List<Integer> list = new ArrayList<Integer>();
        List<DayAchievement> achievements = getDayAchievements();
        for(int i = 6;i >= 0;i--){
            list.add(achievements.get(i).getFailed_times());
        }
        return list;
    }

    @Override
    public List<Integer> getDayTotalTime() {
        List<Integer> list = new ArrayList<Integer>();
        List<DayAchievement> achievements = getDayAchievements();
        for(int i = 6;i >= 0;i--){
            list.add(achievements.get(i).getTotal_time());
        }
        return list;
    }

    @Override
    public PieDataSet getAchievementPieDataSet() {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Achievement achievement = getAchievement();
        float successTimes = achievement.getTotalSuccess();
        float failedTimes = achievement.getTotalFailed();
        float successPercent = successTimes/(successTimes+failedTimes);
        float failedPercent = failedTimes/(successTimes+failedTimes);
        LogUtil.d("percent",successPercent + "  " + failedPercent);
        entries.add(new PieEntry(successPercent, "成功" + (int)successTimes + "次"));
        entries.add(new PieEntry(failedPercent, "失败" + (int)failedTimes +"次"));
        PieDataSet pieDataSet = new PieDataSet(entries, "总计");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);

        return pieDataSet;
    }


}
