package com.fzuclover.putmedown.utils;

import android.os.CountDownTimer;

/**
 * Created by lkl on 2016/11/18.
 */

public class TimeCountUtil extends CountDownTimer {

    private CountListener mListener;

    public TimeCountUtil(long millisInFuture, long countDownInterval, CountListener l) {
        super(millisInFuture, countDownInterval);
        mListener = l;
    }

    @Override
    public void onTick(long l) {
        mListener.onTick(l);
    }

    @Override
    public void onFinish() {
        mListener.onFinish();
    }

    public interface CountListener{

        void onTick(long l);

        void onFinish();
    }
}
