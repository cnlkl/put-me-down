package com.fzuclover.putmedown.features.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lkl on 2016/11/4.
 */

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mView;

    private SharedPreferences mSharedPreferences;

    private String mUsername;

    public SettingPresenter(SettingContract.View view){
        mView = view;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)mView);
        mUsername = mSharedPreferences.getString("username","root");
    }


    @Override
    public void setIsSendWxMsg(boolean b) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(mUsername + "is_send_wx_msg", b);
        editor.commit();
    }

    @Override
    public boolean getIsSendWxMsg() {
        return mSharedPreferences.getBoolean(mUsername + "is_send_wx_msg", true);
    }

    @Override
    public void setWxMsgContent(String content) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(mUsername + "wx_msg_content", content);
        editor.commit();
    }

    @Override
    public String getWxMsgContent() {
        return mSharedPreferences.getString(mUsername + "wx_msg_content", "控记不住记几的手啊");
    }

    @Override
    public void setIsNotify(boolean b) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(mUsername + "is_notify", b);
        editor.commit();
    }

    @Override
    public boolean getIsNotify() {
        return mSharedPreferences.getBoolean(mUsername + "is_notify", true);
    }

    @Override
    public void setIsShock(boolean b) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(mUsername + "is_shock", b);
        editor.commit();
    }

    @Override
    public boolean getIsShock() {
        return mSharedPreferences.getBoolean(mUsername + "is_shock", true);
    }

    @Override
    public void setIsLight(boolean b) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(mUsername + "is_light", b);
        editor.commit();
    }

    @Override
    public boolean getIsLight() {
        return mSharedPreferences.getBoolean(mUsername + "is_light", true);
    }
}
