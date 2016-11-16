package com.fzuclover.putmedown.model.bean;

/**
 * Created by lkl on 2016/11/4.
 */

public class Achievement {
    //总计时时间
    private int totalTime;
    //总成功次数
    private int totalSuccess;
    //总失败次数
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
