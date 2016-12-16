package com.fzuclover.putmedown.model;

import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;

import java.util.List;
import java.util.Map;

/**
 * Created by lkl on 2016/11/4.
 */

public interface IAchievementModel {
    //获取总的成就信息
    Achievement getAchievement();

    //获取每日成就,用于图标绘制
    List<DayAchievement> getDayAchievements();

    /**
     * 存储每日成就信息
     *
     * @param date         日期
     * @param totalTime    每日总计时时间
     * @param successTimes 每日总成功次数
     * @param failedTimes  每日总失败次数
     */
    void saveAchievementEveryDay(String date, int totalTime, int successTimes, int failedTimes);

    void close();

    /**
     * 获取指定日期成就信息
     * @param date 日期，如：2016-12-12
     * @return 指定日期成就信息
     */

    DayAchievement getDayAchievement(String date);

    /**
     * 更新指定日期成就信息
     * @param date 日期
     * @param totalTime 累计计时
     * @param successTimes 成功次数
     * @param failedTimes 失败次数
     */

    void updateAchievementEveryDay(String date, int totalTime, int successTimes, int failedTimes);

}