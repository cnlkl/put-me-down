package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

/**
 * Created by lkl on 2016/11/4.
 */

public class TotalTimingTodayContract {

    interface View {
        void toSettingActivity();
        void toLoginActivity();
        void toStartTimingActivity();
        void toAchievementActivity();
        void toHistoryActivity();
        void showSetTargetTimeDialog();
    }

    interface Presenter {
        void logout();
    }

}
