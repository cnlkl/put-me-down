package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

import android.content.Context;

/**
 * Created by lkl on 2016/11/4.
 */

public class TotalTimingTodayContract {

    interface View {
        void toSettingActivity();
        void toLoginActivity();
        void toTimingActivity(int minute);
        void toAchievementActivity();
        void toHistoryActivity();
        void showSetTargetTimeDialog();
        void showEditTimingCommentsDialog();
    }

    interface Presenter {
        void saveTargetTime(Context context, int targetTime);
        int getTargetTime(Context context);
        int getTimedToday(Context context);
    }

}
