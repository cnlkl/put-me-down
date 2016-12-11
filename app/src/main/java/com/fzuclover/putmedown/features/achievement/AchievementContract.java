package com.fzuclover.putmedown.features.achievement;

import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 *
 * 指定成就模块view层与presenter层需要实现的接口
 */

public interface AchievementContract {

    interface View {

    }

    interface Presenter {
        //获取历史总的成就信息
        Achievement getAchievement();

        //获取每日成就,用于图表绘制
        List<DayAchievement> getDayAchievements();

        //获取7日每天总计时次数
        BarDataSet getTotalTimeBarDataSet();

        //获取7日每日总成功次数
        BarDataSet getSuccessBarDataSet();

        //获取7日每日总失败次数
        BarDataSet getFailedBarDataSet();

        //获取总成功次数和失败次数
        PieDataSet getAchievementPieDataSet();
    }

}
