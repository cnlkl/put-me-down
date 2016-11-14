package com.fzuclover.putmedown.model.bean;

/**
 * Created by lkl on 2016/11/4.
 */

public class Record {
    private int id;
    private String startTime;
    private int totalTime;
    private String comment;
    private boolean isSuccess;
    private String endTime;
    private String failComents;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFailComents() {
        return failComents;
    }

    public void setFailComents(String failComents) {
        this.failComents = failComents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
