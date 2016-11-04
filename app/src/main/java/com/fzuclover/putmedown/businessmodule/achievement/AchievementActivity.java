package com.fzuclover.putmedown.businessmodule.achievement;

import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;

public class AchievementActivity extends BaseActivity implements AchievementContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
    }
}
