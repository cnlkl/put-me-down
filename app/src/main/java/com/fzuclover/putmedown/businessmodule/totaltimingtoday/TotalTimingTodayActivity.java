package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;

public class TotalTimingTodayActivity extends BaseActivity implements TotalTimingTodayContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_timing_today);
    }
}
