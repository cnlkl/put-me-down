package com.fzuclover.putmedown.features.timing;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.fzuclover.putmedown.R;
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
        String username = sharedPreferences.getString("username","root");
        String date = sharedPreferences.getString(username + "date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)) {
            editor.putInt(username + "timed_today", (sharedPreferences.getInt(username + "timed_today", 0) + time));
            editor.putInt(username + "success_times_totay", (sharedPreferences.getInt(username + "success_times_today", 0) + 1));
            editor.commit();
        }else {
            //保存前一天的数据到数据库
            int successTimesToday = sharedPreferences.getInt(username + "success_times_today", 0);
            int failedTImesToday = sharedPreferences.getInt(username + "failed_times_today", 0);
            int timedToday = sharedPreferences.getInt(username + "timed_today", 0);
            mAchievementModel.saveAchievementEveryDay(date, timedToday, successTimesToday, failedTImesToday);
            //存入新一天的数据
            editor.putInt(username + "timed_today", time);
            editor.putString(username + "date", dateNow);
            editor.putInt(username + "success_times_today",1);
            editor.putInt(username + "failed_times_today", 0);
            editor.commit();
        }
    }
    //TODO 0点计时可能数据错误
    @Override
    public void updateTimingRecord() {
        mRecordModel.updateTimingRecord(mView.isSuccess(), mView.getRecordId(), mView.getFailComments());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)mView);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username","root");
        String date = sharedPreferences.getString(username + "date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)) {
            editor.putInt(username + "failed_times_today", sharedPreferences.getInt(username + "failed_times_today", 0) + 1);
            editor.commit();
        }else {
            //保存前一天的数据到数据库
            int successTimesToday = sharedPreferences.getInt(username + "success_times_today", 0);
            int failedTImesToday = sharedPreferences.getInt(username + "failed_times_today", 0);
            int timedToday = sharedPreferences.getInt(username + "timed_today", 0);
            mAchievementModel.saveAchievementEveryDay(date, timedToday, successTimesToday, failedTImesToday);
            //存入新一天的数据
            editor.putInt(username + "timed_today", 0);
            editor.putString(username + "date", dateNow);
            editor.putInt(username + "success_times_today",0);
            editor.putInt(username + "failed_times_today", 1);
            editor.commit();
        }
    }

    @Override
    public void shareText() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)mView);
        String username = sharedPreferences.getString("username", "root");
        if(sharedPreferences.getBoolean(username + "is_send_wx_msg", true)){
            // 初始化一个WXTextObject对象
            WXTextObject textObj = new WXTextObject();
            textObj.text = sharedPreferences.getString(username + "wx_msg_content", "控记不住记几的手啊");
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)mView);
        String username = sharedPreferences.getString("username","root");
        if(sharedPreferences.getBoolean(username + "is_notify", true)){
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
            if(sharedPreferences.getBoolean(username + "is_shock", true)){
                builder.setVibrate(new long[]{0,500,500});
            }

            if(sharedPreferences.getBoolean(username + "is_light", true)){
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
