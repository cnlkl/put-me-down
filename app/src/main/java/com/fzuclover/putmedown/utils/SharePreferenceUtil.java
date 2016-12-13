package com.fzuclover.putmedown.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by lkl on 2016/12/8.
 */

public class SharePreferenceUtil {

    private SharedPreferences mSharedPreferences;

    private SharedPreferences.Editor mEditor;

    private String mUsername;

    private Context mContext;

    private static SharePreferenceUtil mInstance;

    private SharePreferenceUtil(Context context) {
        mContext = context.getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mSharedPreferences.edit();
        mUsername = getUserName();
    }

    public static SharePreferenceUtil getInstance(Context context){

        if(mInstance == null){
            mInstance = new SharePreferenceUtil(context);
        }
        return mInstance;
    }

    public void saveTargetTime(int targetTime) {
        mEditor.putInt(mUsername + "target_time",targetTime);
        mEditor.commit();
    }

    public int getTargetTime() {
        return mSharedPreferences.getInt(mUsername + "target_time",180);
    }

    public void saveDate(String date){
        mEditor.putString(mUsername + "date", date);
        mEditor.commit();
    }

    public String getDate(){
        return mSharedPreferences.getString(mUsername + "date", "");
    }

    public void saveTimedToday(int timedToday){
        mEditor.putInt(mUsername + "timed_today", timedToday);
        mEditor.commit();
    }

    public int getTimedToday(){
        return mSharedPreferences.getInt(mUsername + "timed_today", 0);
    }

    public void saveSuccessTimesToday(int successTimes){
        mEditor.putInt(mUsername + "success_times_today",successTimes);
    }

    public int getSuccessTimesToday(){
        return mSharedPreferences.getInt(mUsername + "success_times_today", 0);
    }

    public void saveFailedTimesToday(int failedTimes){
        mEditor.putInt(mUsername + "failed_times_today", failedTimes);
    }

    public int getFailedTimesToday(){
        return mSharedPreferences.getInt(mUsername + "failed_times_today", 0);
    }

    public void saveLoginStatu(boolean isLogin){
        mEditor.putBoolean("is_login", isLogin);
        mEditor.commit();
    }

    public boolean getLoginStatu(){
        return mSharedPreferences.getBoolean("is_login",false);
    }

    public void saveUserName(String username){
        mEditor.putString("username", username);
        mEditor.commit();
    }

    public String getUserName(){
        return mSharedPreferences.getString("username", "root");
    }

    public void saveIsSendWeChatMsg(boolean isSend){
        mEditor.putBoolean(mUsername + "is_send_wx_msg", isSend);
        mEditor.commit();
    }

    public boolean getIsSendWeChatMsg(){
        return mSharedPreferences.getBoolean(mUsername + "is_send_wx_msg", true);
    }

    public void saveWeChatMsg(String content){
        mEditor.putString(mUsername + "wx_msg_content", content);
        mEditor.commit();
    }

    public String getWeChatMsg(){
        return mSharedPreferences.getString(mUsername + "wx_msg_content", "控记不住记几的手啊");
    }

    public void saveIsNotify(boolean isNotify){
        mEditor.putBoolean(mUsername + "is_notify", isNotify);
        mEditor.commit();
    }

    public boolean getIsNotify(){
        return mSharedPreferences.getBoolean(mUsername + "is_notify", true);
    }

    public void saveIsShock(boolean isShock){
        mEditor.putBoolean(mUsername + "is_shock", isShock);
        mEditor.commit();
    }

    public boolean getIsShock(){
        return mSharedPreferences.getBoolean(mUsername + "is_shock", true);
    }

    public void saveIsLight(boolean isLight){
        mEditor.putBoolean(mUsername + "is_light", isLight);
        mEditor.commit();
    }

    public boolean getIsLight(){
        return mSharedPreferences.getBoolean(mUsername + "is_light", true);
    }

    public void close(){
        mInstance = null;
    }
}
