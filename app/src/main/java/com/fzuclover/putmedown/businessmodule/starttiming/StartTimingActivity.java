package com.fzuclover.putmedown.businessmodule.starttiming;

import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;

public class StartTimingActivity extends BaseActivity implements StartTimingContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_timing);
    }
}
