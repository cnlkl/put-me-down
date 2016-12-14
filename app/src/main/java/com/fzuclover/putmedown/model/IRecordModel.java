package com.fzuclover.putmedown.model;

import com.fzuclover.putmedown.model.bean.Record;

import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 */

public interface IRecordModel {
    //计时开始保存记录
    int saveTimingRecord(int totalTime, String comments);
    //计时结束更新记录
    void updateTimingRecord(Boolean isSuccess, int id, String failComment);
    //获取所有计时记录
    List<Record> getTimingRecord();

    void close();
}
