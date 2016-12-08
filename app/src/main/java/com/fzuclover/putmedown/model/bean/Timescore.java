package com.fzuclover.putmedown.model.bean;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2016/12/8.
 */
//图表显示数据的数据类

public class Timescore  extends RealmObject {


    private int  todayTime;

    private float scoreNr;

    private String  num;

    public Timescore() { } // no arguments constructor required for realm

    public Timescore(int totayTime, float scoreNr, String num) {
        this.scoreNr = scoreNr;
        this.num = num;
        this.todayTime = todayTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public float getScoreNr() {
        return scoreNr;
    }

    public int getTodayTime() {
        return todayTime;
    }

    public void setScoreNr(float scoreNr) {
        this.scoreNr = scoreNr;
    }

    public void setTodayTime(int todayTime) {
        this.todayTime = todayTime;
    }



}
