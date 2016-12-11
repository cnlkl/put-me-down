package com.fzuclover.putmedown.features.achievement;

import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;

import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 *
 * 指定成就模块view层与presenter层需要实现的接口
 */

public interface AchievementContract {

    interface View {
        void toShow();
        int parseTravel(int time);
        String timeToachieveplace(int time);
    }

    interface Presenter {
        //获取历史总的成就信息
        Achievement getAchievement();

        //获取每日成就,用于图表绘制
        List<DayAchievement> getDayAchievements();
    }

}
