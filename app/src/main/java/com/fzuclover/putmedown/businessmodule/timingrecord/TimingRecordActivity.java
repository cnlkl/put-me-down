package com.fzuclover.putmedown.businessmodule.timingrecord;

import android.graphics.Color;
import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.model.RecordModel;
import com.fzuclover.putmedown.view.pmdtimeline.FreeTimeLine;
import com.fzuclover.putmedown.view.pmdtimeline.FreeTimeLineConfig;
import com.fzuclover.putmedown.view.pmdtimeline.FreeTimeLineElement;

import java.util.ArrayList;
import java.util.List;

public class TimingRecordActivity extends BaseActivity implements TimingRecordContract.View {

    private FreeTimeLine mTimeLine;
    private TimingRecordContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing_record);
        init();
    }

    private void init(){
        mPresenter = new TimingRecordPresenter(this, RecordModel.getInstance(this));
        mTimeLine = (FreeTimeLine) findViewById(R.id.time_line_view);
        FreeTimeLineConfig config = new FreeTimeLineConfig();
        config.setHollowColor(Color.parseColor("#5b9ef4"));
        config.setSolidColor(Color.parseColor("#5b9ef4"));
        config.setLineColor(Color.parseColor("#5b9ef4"));
        config.setSuckerColor(Color.parseColor("#5b9ef4"));
        config.setShowToggle(true);
        config.setTopType(0);
        mTimeLine.setConfig(config);
        mTimeLine.setElements(mPresenter.getElements());
    }



}
