package com.fzuclover.putmedown.model.bean;

/**
 * Created by lkl on 2016/11/4.
 */

public class Achievement {
    //历史总计时时间
    private int totalTime;
    //历史总成功次数
    private int totalSuccess;
    //历史总失败次数
    private int totalFailed;

    public int getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(int totalFailed) {
        this.totalFailed = totalFailed;
    }

    public int getTotalSuccess() {
        return totalSuccess;
    }

    public void setTotalSuccess(int totalSuccess) {
        this.totalSuccess = totalSuccess;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
