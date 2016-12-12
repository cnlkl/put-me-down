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

        //获取总计时时间
        int getTotalTime();

        //获取每日成就,用于图表绘制
        List<DayAchievement> getDayAchievements();

        //获取每日成功次数
        List<Integer> getDaySuccessTimes();
        //获取每日失败次数
        List<Integer> getDayFailedTimes();
        //获取每日总计时时间
        List<Integer> getDayTotalTime();
        //获取总成功次数和失败次数
        PieDataSet getAchievementPieDataSet();
    }

}
