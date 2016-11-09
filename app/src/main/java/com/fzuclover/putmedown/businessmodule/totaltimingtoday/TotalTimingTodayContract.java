package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

/**
 * Created by lkl on 2016/11/4.
 */

public class TotalTimingTodayContract {

    interface View {
        void toSettingActivity();
        void toLoginActivity();
        void toTimingActivity();
        void toAchievementActivity();
        void toHistoryActivity();
        void showSetTargetTimeDialog();
        void getTargetTime();
        void setTargetTime();
    }

    interface Presenter {
    }

}
