package com.fzuclover.putmedown.features.setting;

/**
 * Created by lkl on 2016/11/4.
 */

public interface SettingContract {

    interface View {

    }

    interface Presenter {
        void setIsSendWxMsg(boolean b);
        boolean getIsSendWxMsg();
        void setWxMsgContent(String content);
        String getWxMsgContent();
        void setIsNotify(boolean b);
        boolean getIsNotify();
        void setIsShock(boolean b);
        boolean getIsShock();
        void setIsLight(boolean b);
        boolean getIsLight();
    }
}
