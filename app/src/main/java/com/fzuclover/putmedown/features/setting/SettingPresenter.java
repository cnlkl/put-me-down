package com.fzuclover.putmedown.features.setting;

import android.content.Context;

import com.fzuclover.putmedown.utils.SharePreferenceUtil;

/**
 * Created by lkl on 2016/11/4.
 */

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mView;

    private SharePreferenceUtil mSharedPreferencesUtil;

    public SettingPresenter(SettingContract.View view){
        mView = view;
        mSharedPreferencesUtil = SharePreferenceUtil.getInstance((Context)mView);
    }


    @Override
    public void setIsSendWxMsg(boolean b) {
        mSharedPreferencesUtil.saveIsSendWeChatMsg(b);
    }

    @Override
    public boolean getIsSendWxMsg() {
        return mSharedPreferencesUtil.getIsSendWeChatMsg();
    }

    @Override
    public void setWxMsgContent(String content) {
        mSharedPreferencesUtil.saveWeChatMsg(content);
    }

    @Override
    public String getWxMsgContent() {
        return mSharedPreferencesUtil.getWeChatMsg();
    }

    @Override
    public void setIsNotify(boolean b) {
        mSharedPreferencesUtil.saveIsNotify(b);
    }

    @Override
    public boolean getIsNotify() {
        return mSharedPreferencesUtil.getIsNotify();
    }

    @Override
    public void setIsShock(boolean b) {
        mSharedPreferencesUtil.saveIsShock(b);
    }

    @Override
    public boolean getIsShock() {
        return mSharedPreferencesUtil.getIsShock();
    }

    @Override
    public void setIsLight(boolean b) {
       mSharedPreferencesUtil.saveIsLight(b);
    }

    @Override
    public boolean getIsLight() {
        return mSharedPreferencesUtil.getIsLight();
    }
}
