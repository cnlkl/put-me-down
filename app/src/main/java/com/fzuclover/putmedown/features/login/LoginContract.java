package com.fzuclover.putmedown.features.login;

/**
 * Created by lkl on 2016/11/4.
 *
 * 指定登入模块view层与presenter层需要实现的接口
 */


public interface LoginContract {

    interface View {
        void toTotalTimingTodayActivity();
        void toForgotPasswordActivity();
        void toRegisterActivity();
    }

    interface Presenter {
        void login(String username, String password);
    }


}
