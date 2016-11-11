package com.fzuclover.putmedown.businessmodule.timingrecord;

import android.graphics.Color;
import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.view.pmdtimeline.FreeTimeLine;
import com.fzuclover.putmedown.view.pmdtimeline.FreeTimeLineConfig;
import com.fzuclover.putmedown.view.pmdtimeline.FreeTimeLineElement;

import java.util.ArrayList;
import java.util.List;

public class TimingRecordActivity extends BaseActivity implements TimingRecordContract.View {

    private FreeTimeLine mTimeLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing_record);
        init();
    }

    private void init(){
        mTimeLine = (FreeTimeLine) findViewById(R.id.time_line_view);
        FreeTimeLineConfig config = new FreeTimeLineConfig();
        config.setHollowColor(Color.parseColor("#5b9ef4"));
        config.setSolidColor(Color.parseColor("#5b9ef4"));
        config.setLineColor(Color.parseColor("#5b9ef4"));
        config.setSuckerColor(Color.parseColor("#5b9ef4"));
        config.setShowToggle(true);
        config.setTopType(0);
        mTimeLine.setConfig(config);
        mTimeLine.setElements(getElements());
    }

    private List<FreeTimeLineElement> getElements(){
        List<FreeTimeLineElement> elements = new ArrayList<>();
        FreeTimeLineElement element;
        for(int i = 0;i < 30;i++){
            element = new FreeTimeLineElement("睡觉", "time:15min\nisSuccess:false\n停止理由：写代码", "2016/01/06\n09:10",false);
            elements.add(element);
            element = new FreeTimeLineElement("写代码", "time:15min\nisSuccess:true", "2016/01/08\n17:30", true);
            elements.add(element);
        }


        return elements;
    }

}
