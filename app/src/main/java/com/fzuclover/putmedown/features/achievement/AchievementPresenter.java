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
import com.github.mikephil.charting.utils.ColorTemplate;

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
    public List<DayAchievement> getDayAchievements() {
        return mAchievementModel.getDayAchievements();
    }

    @Override
    public BarDataSet getTotalTimeBarDataSet() {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        List<DayAchievement> dayAchievements = getDayAchievements();

        int i = 1;
        for (DayAchievement dayAchievement : dayAchievements){
            entries.add(new BarEntry(i, dayAchievement.getTotal_time()));
            LogUtil.d("time",dayAchievement.getDate());
            if(i == 7) {
                break;
            }

            i++;
        }

        BarDataSet barDataSet = new BarDataSet(entries, "total_time");
        barDataSet.setColors(ColorTemplate.getHoloBlue());
        barDataSet.setDrawValues(true);

        return barDataSet;
    }

    @Override
    public BarDataSet getSuccessBarDataSet() {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        List<DayAchievement> dayAchievements = getDayAchievements();

        int i = 1;
        for (DayAchievement dayAchievement : dayAchievements){
            entries.add(new BarEntry(i, dayAchievement.getSucces_times()));
            if(i == 7){
                break;
            }
            i++;
        }

        BarDataSet barDataSet = new BarDataSet(entries, "success");
        barDataSet.setColors(ColorTemplate.getHoloBlue());
        barDataSet.setDrawValues(true);

        return barDataSet;
    }

    @Override
    public BarDataSet getFailedBarDataSet() {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        List<DayAchievement> dayAchievements = getDayAchievements();

        int i = 1;
        for (DayAchievement dayAchievement : dayAchievements){
            entries.add(new BarEntry(i, dayAchievement.getFailed_times()));
            if(i == 7){
                break;
            }
            i++;
        }

        BarDataSet barDataSet = new BarDataSet(entries, "failed");
        barDataSet.setColors(ColorTemplate.getHoloBlue());
        barDataSet.setDrawValues(true);

        return barDataSet;
    }

    @Override
    public PieDataSet getAchievementPieDataSet() {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Achievement achievement = getAchievement();
        int successTimes = achievement.getTotalSuccess();
        int failedTimes = achievement.getTotalFailed();
        entries.add(new PieEntry(successTimes, successTimes/(successTimes+failedTimes)));
        entries.add(new PieEntry(failedTimes, failedTimes/(successTimes+failedTimes)));
        PieDataSet pieDataSet = new PieDataSet(entries, "总计");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);

        return pieDataSet;
    }


}
