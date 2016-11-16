package com.fzuclover.putmedown.features.timing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.IRecordModel;
import com.fzuclover.putmedown.model.RecordModel;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by lkl on 2016/11/4.
 */

public class TimingPresenter implements TimingContract.Presenter {

    private TimingContract.View mView;
    private IRecordModel mRecordModel;
    private IAchievementModel mAchievementModel;
    private static final String APP_ID = "wx63e23a6376ee0569";
    private IWXAPI api;

    public TimingPresenter(TimingContract.View view){
        mView = view;
        mRecordModel = RecordModel.getInstance((Context)view);
        mAchievementModel = AchievementModel.getAchieveMentModelInstance((Context)view);
        //初始化
        api = WXAPIFactory.createWXAPI((Context)mView, APP_ID, true);
        //向微信终端注册
        api.registerApp(APP_ID);

    }

    @Override
    public void saveTimedToday(Context context, int time) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = sharedPreferences.getString("date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)) {
            editor.putInt("timed_today", (sharedPreferences.getInt("timed_today", 0) + time));
            editor.putInt("success_times_totay", (sharedPreferences.getInt("success_times_today", 0) + 1));
            editor.commit();
        }else {
            //保存前一天的数据到数据库
            int successTimesToday = sharedPreferences.getInt("success_times_today", 0);
            int failedTImesToday = sharedPreferences.getInt("failed_times_today", 0);
            int timedToday = sharedPreferences.getInt("timed_today", 0);
            mAchievementModel.saveAchievementEveryDay(date, timedToday, successTimesToday, failedTImesToday);
            //存入新一天的数据
            editor.putInt("timed_today", time);
            editor.putString("date", dateNow);
            editor.putInt("success_times_today",1);
            editor.putInt("failed_times_today", 0);
            editor.commit();
        }
    }
    //TODO 0点计时可能数据错误
    @Override
    public void updateTimingRecord() {
        mRecordModel.updateTimingRecord(mView.isSuccess(), mView.getRecordId(), mView.getFailComments());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)mView);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = sharedPreferences.getString("date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)) {
            editor.putInt("failed_times_today", sharedPreferences.getInt("failed_times_today", 0) + 1);
            editor.commit();
        }else {
            //保存前一天的数据到数据库
            int successTimesToday = sharedPreferences.getInt("success_times_today", 0);
            int failedTImesToday = sharedPreferences.getInt("failed_times_today", 0);
            int timedToday = sharedPreferences.getInt("timed_today", 0);
            mAchievementModel.saveAchievementEveryDay(date, timedToday, successTimesToday, failedTImesToday);
            //存入新一天的数据
            editor.putInt("timed_today", 0);
            editor.putString("date", dateNow);
            editor.putInt("success_times_today",0);
            editor.putInt("failed_times_today", 1);
            editor.commit();
        }
    }

    @Override
    public void shareText() {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = "test";
        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = "PMD微信分享";
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction字段用于唯一标识一个请求
        req.transaction = buildTransaction("text");
        req.message = msg;
        // 分享或收藏的目标场景，通过修改scene场景值实现。
        // 发送到聊天界面 —— WXSceneSession
        // 发送到朋友圈 —— WXSceneTimeline
        // 添加到微信收藏 —— WXSceneFavorite
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
