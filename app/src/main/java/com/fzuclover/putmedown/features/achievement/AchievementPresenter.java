package com.fzuclover.putmedown.features.achievement;

import android.content.Context;

import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.utils.LogUtil;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 */

public class AchievementPresenter implements AchievementContract.Presenter {

    private AchievementContract.View mView;
    private IAchievementModel mAchievementModel;
    private static final String APP_ID = "wx63e23a6376ee0569";
    private IWXAPI api;

    public AchievementPresenter(AchievementContract.View view) {
        this.mView = view;
        mAchievementModel = AchievementModel.getAchieveMentModelInstance((Context)mView);
        //初始化
        api = WXAPIFactory.createWXAPI((Context)mView, APP_ID, true);
        //向微信终端注册
        api.registerApp(APP_ID);
    }

    @Override
    public Achievement getAchievement() {
        return mAchievementModel.getAchievement();
    }

    @Override
    public int getTotalTime() {
        return getAchievement().getTotalTime();
    }

    @Override
    public List<DayAchievement> getDayAchievements() {
        return mAchievementModel.getDayAchievements();
    }

    @Override
    public List<Integer> getDaySuccessTimes() {
        List<Integer> list = new ArrayList<Integer>();
        List<DayAchievement> achievements = getDayAchievements();
        int i = 6;
        if(achievements.size() < 7){
            i = achievements.size() -1;
        }
        for(;i >= 0;i--){
            list.add(achievements.get(i).getSucces_times());
        }
        return list;
    }

    @Override
    public List<Integer> getDayFailedTimes() {
        List<Integer> list = new ArrayList<Integer>();
        List<DayAchievement> achievements = getDayAchievements();
        int i = 6;
        if(achievements.size() < 7){
            i = achievements.size() -1;
        }
        for(;i >= 0;i--){
            list.add(achievements.get(i).getFailed_times());
        }
        return list;
    }

    @Override
    public List<Integer> getDayTotalTime() {
        List<Integer> list = new ArrayList<Integer>();
        List<DayAchievement> achievements = getDayAchievements();
        int i = 6;
        if(achievements.size() < 7){
            i = achievements.size() -1;
        }
        for(;i >= 0;i--){
            list.add(achievements.get(i).getTotal_time());
        }
        return list;
    }

    @Override
    public PieDataSet getAchievementPieDataSet() {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Achievement achievement = getAchievement();
        float successTimes = achievement.getTotalSuccess();
        float failedTimes = achievement.getTotalFailed();
        float successPercent = successTimes/(successTimes+failedTimes);
        float failedPercent = failedTimes/(successTimes+failedTimes);
        LogUtil.d("percent",successPercent + "  " + failedPercent);
        entries.add(new PieEntry(successPercent, "成功" + (int)successTimes + "次"));
        entries.add(new PieEntry(failedPercent, "失败" + (int)failedTimes +"次"));
        PieDataSet pieDataSet = new PieDataSet(entries, "总计");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);

        return pieDataSet;
    }

    @Override
    public void share() {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = "我已经" + getTotalTime() + "分钟没有碰手机啦";
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
