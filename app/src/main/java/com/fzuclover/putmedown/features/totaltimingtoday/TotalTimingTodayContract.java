package com.fzuclover.putmedown.features.totaltimingtoday;

import android.content.Context;

/**
 * Created by lkl on 2016/11/4.
 */

public class TotalTimingTodayContract {

    interface View {
        void toSettingActivity();
        void toLoginActivity();
        void toTimingActivity(int minute, int id);
        void toAchievementActivity();
        void toHistoryActivity();
        void showSetTargetTimeDialog();
        void showEditTimingCommentsDialog();
    }

    interface Presenter {
        void saveTargetTime(int targetTime);
        int getTargetTime();
        int getTimedToday();
        int saveTimingRecord(int totalTime, String comments);
        void setLoginStatu(boolean b);
    }

}
