package com.fzuclover.putmedown.model.bean;

/**
 * Created by lkl on 2016/11/16.
 */

public class DayAchievement {
    //日期
    private String date;
    //每日总计时时间
    private int total_time;
    //每日总成功次数
    private int succes_times;
    //每日总失败次数
    private int failed_times;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFailed_times() {
        return failed_times;
    }

    public void setFailed_times(int failed_times) {
        this.failed_times = failed_times;
    }

    public int getSucces_times() {
        return succes_times;
    }

    public void setSucces_times(int succes_times) {
        this.succes_times = succes_times;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }
}
