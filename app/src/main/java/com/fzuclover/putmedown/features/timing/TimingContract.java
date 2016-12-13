package com.fzuclover.putmedown.features.timing;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * Created by lkl on 2016/11/4.
 */

public interface TimingContract {

    interface View {
        void startTiming(int totalTime, int second);
        void stopTimng();
        String getFailComments();
        boolean isSuccess();
        int getRecordId();
        int getTotalTime();
    }

    interface Presenter {
        //计时结束后保存记录到数据库
        void updateTimingRecord(int time);
        //微信分享接口
        void shareText();
        //通知
        void sendNotify();
    }
}
