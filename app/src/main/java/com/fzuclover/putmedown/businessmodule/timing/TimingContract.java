package com.fzuclover.putmedown.businessmodule.timing;

/**
 * Created by lkl on 2016/11/4.
 */

public interface TimingContract {

    interface View {
        void startTiming(int totalTime);
        void stopTimng();
        void showStopTimingDialog();
        void toTotalTimingTodayActivity();
    }

    interface Presenter {

    }
}
