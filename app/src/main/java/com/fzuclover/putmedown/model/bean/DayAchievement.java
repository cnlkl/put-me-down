package com.fzuclover.putmedown.model.bean;

/**
 * Created by lkl on 2016/11/16.
 */

public class DayAchievement {
    private String date;
    private int total_time;
    private int succes_times;
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
