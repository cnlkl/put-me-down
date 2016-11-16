package com.fzuclover.putmedown.features.achievement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.utils.LogUtil;

import java.util.List;

public class AchievementActivity extends BaseActivity implements AchievementContract.View {
    /*
        todo 在此处声明私有变量
        如：TextView mTotalTime;
     */

    //调用presenter的方法获取数据
    private AchievementContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        init();
    }

    private void init(){
        //todo 在此处做一些变量的初始化工作
        mPresenter = new AchievementPresenter(this);
    }
}
