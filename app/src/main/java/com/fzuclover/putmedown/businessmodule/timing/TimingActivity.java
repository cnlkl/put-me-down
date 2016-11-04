package com.fzuclover.putmedown.businessmodule.timing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;

public class TimingActivity extends BaseActivity implements TimingContract.Presenter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
    }
}
