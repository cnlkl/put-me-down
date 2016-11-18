package com.fzuclover.putmedown.features.forgotpassword;

/**
 * Created by lkl on 2016/11/4.
 */

public interface ForgotPasswordContract {

    interface View {
        void toLoginActivity();
        String getPhone();
        String getPassword();
        String getConfirmPassword();
        void setGetCodeBtnTimeCount();
    }

    interface Presenter {
        void getCode(String phone);
        void submit(String phone, String code);
        void unRegisterSMSHandler();
    }
}
