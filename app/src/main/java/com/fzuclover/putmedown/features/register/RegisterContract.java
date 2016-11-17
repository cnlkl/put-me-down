package com.fzuclover.putmedown.features.register;

/**
 * Created by lkl on 2016/11/4.
 */

public interface RegisterContract {

    interface View {
        void toLoginActivity();
        String getPhone();
        String getPassword();
    }

    interface Presenter {
        //发送验证码
        void getCode(String phone);
        //提交注册信息
        void submit(String phone, String code);
        //在onDestroy中调用回收资源
        void unRegisterHandler();
    }
}
