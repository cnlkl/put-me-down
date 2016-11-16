package com.fzuclover.putmedown.features.achievement;

import android.content.Context;

import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;

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
}
