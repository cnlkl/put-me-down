package com.fzuclover.putmedown.businessmodule.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;

public class SettingActivity extends BaseActivity implements SettingContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
