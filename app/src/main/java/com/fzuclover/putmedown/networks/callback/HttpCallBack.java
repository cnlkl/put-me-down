package com.fzuclover.putmedown.networks.callback;

/**
 * Created by lkl on 2016/11/18.
 */

public interface HttpCallBack {
    void onFinish(String response);
    void onError(String err);
}
