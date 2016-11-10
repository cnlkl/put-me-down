package com.fzuclover.putmedown.businessmodule.timingrecord;

import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;

public class TimingRecordActivity extends BaseActivity implements TimingRecordContract.View {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing_record);
    }


}
