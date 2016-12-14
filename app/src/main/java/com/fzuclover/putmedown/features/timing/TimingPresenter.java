package com.fzuclover.putmedown.features.timing;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.IRecordModel;
import com.fzuclover.putmedown.model.RecordModel;
import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.utils.SharePreferenceUtil;
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
    private SharePreferenceUtil mSharePreferenceUtil;
    private static final String APP_ID = "wx63e23a6376ee0569";
    private IWXAPI api;

    public TimingPresenter(TimingContract.View view){
        mView = view;
        mRecordModel = RecordModel.getInstance((Context)view);
        mAchievementModel = AchievementModel.getAchieveMentModelInstance((Context)view);
        mSharePreferenceUtil = SharePreferenceUtil.getInstance((Context)mView);
        //初始化
        api = WXAPIFactory.createWXAPI((Context)mView, APP_ID, true);
        //向微信终端注册
        api.registerApp(APP_ID);

    }

    @Override
    public void updateTimingRecord(int time) {
        mRecordModel.updateTimingRecord(mView.isSuccess(), mView.getRecordId(), mView.getFailComments());
        String date = mSharePreferenceUtil.getDate();

        DayAchievement dayAchievement = mAchievementModel.getDayAchievement(date);
        int totalTime = dayAchievement.getTotal_time();
        int successTimes = dayAchievement.getSucces_times();
        int failedTImes = dayAchievement.getFailed_times();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());

        if(date.equals(dateNow)) {
            if(mView.isSuccess()) {
                mAchievementModel.updateAchievementEveryDay(date, totalTime+time, successTimes+1, failedTImes);
            } else {
                mAchievementModel.updateAchievementEveryDay(date, totalTime, successTimes, failedTImes+1);
            }
        } else {
            mSharePreferenceUtil.saveDate(dateNow);
            if(mView.isSuccess()){
                mAchievementModel.saveAchievementEveryDay(dateNow, time, 1, 0);
            }else{
                mAchievementModel.saveAchievementEveryDay(dateNow, 0, 0, 1);
            }
        }
    }

    @Override
    public void shareText() {
        if(mSharePreferenceUtil.getIsSendWeChatMsg()){
            // 初始化一个WXTextObject对象
            WXTextObject textObj = new WXTextObject();
            textObj.text = mSharePreferenceUtil.getWeChatMsg();
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
    }

    @Override
    public void sendNotify() {
        if(mSharePreferenceUtil.getIsNotify()){
            NotificationManager notificationManager= (NotificationManager) ((Context)mView).getSystemService(Context.NOTIFICATION_SERVICE);
//            Intent intent = new Intent((Context)mView, TimingActivity.class);
//            PendingIntent pi = PendingIntent.getActivity((Context)mView, 0, intent, 0);
            Notification.Builder builder = new Notification.Builder((Context)mView)
                    .setTicker("计时成功")
                    .setAutoCancel(true)
                    .setContentTitle("pmd")
//                    .setContentIntent(pi)
                    .setContentText("恭喜！计时成功！")
                    .setSmallIcon(R.mipmap.white_logo);
            if(mSharePreferenceUtil.getIsShock()){
                builder.setVibrate(new long[]{0,500,500});
            }

            if(mSharePreferenceUtil.getIsLight()){
                builder.setLights(Color.GREEN,1000,1000);
            }
            Notification notification = builder.getNotification();
            notificationManager.notify(1,notification);

        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
