package com.fzuclover.putmedown.businessmodule.timing;

import android.content.Context;

/**
 * Created by lkl on 2016/11/4.
 */

public interface TimingContract {

    interface View {
        void startTiming(int totalTime);
        void stopTimng();
        void showStopTimingDialog();
    }

    interface Presenter {
        //保存到今日已计时时间
        void saveTimedToday(Context context, int time);
    }
}
