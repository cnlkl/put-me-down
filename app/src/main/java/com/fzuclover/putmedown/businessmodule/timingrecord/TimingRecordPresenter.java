package com.fzuclover.putmedown.businessmodule.timingrecord;

import android.text.TextUtils;

import com.fzuclover.putmedown.businessmodule.timing.TimingContract;
import com.fzuclover.putmedown.model.RecordModel;
import com.fzuclover.putmedown.model.bean.Record;
import com.fzuclover.putmedown.view.pmdtimeline.FreeTimeLineElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 */

public class TimingRecordPresenter implements TimingRecordContract.Presenter {

    private TimingRecordContract.View mView;

    private RecordModel mRecordModel;

    public TimingRecordPresenter(TimingRecordContract.View view, RecordModel recordModel){
        mView = view;
        mRecordModel = recordModel;
    }

    @Override
    public List<FreeTimeLineElement> getElements() {

        List<Record> records = mRecordModel.getTimingRecord();

        List<FreeTimeLineElement> elements = new ArrayList<>();
        FreeTimeLineElement element;
        for(Record record : records){
            String totalTime = "目标时间:" + record.getTotalTime() + "分钟";
            String comment = record.getComment();

            String startTime = record.getStartTime();
            String[] temp = startTime.split(" ");
            startTime = temp[0] + "\n" + temp[1];

            if(TextUtils.isEmpty(comment)){
                comment = "未填写备注";
            }

            boolean isSuccess = record.isSuccess();
            if(isSuccess){
                element = new FreeTimeLineElement(comment, totalTime, startTime, isSuccess);
            }else {
                String failComment = "终止备注:" + record.getFailComents() + "\n";
                String endTime = record.getEndTime();
                element = new FreeTimeLineElement(comment, totalTime + "\n" + failComment + endTime, startTime, isSuccess);
            }
            elements.add(element);
        }

        return elements;
    }
}
