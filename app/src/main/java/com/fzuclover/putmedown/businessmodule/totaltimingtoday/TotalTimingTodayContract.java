package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

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
    }

}
