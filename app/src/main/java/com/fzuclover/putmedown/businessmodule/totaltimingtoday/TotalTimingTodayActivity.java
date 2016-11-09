package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.businessmodule.achievement.AchievementActivity;
import com.fzuclover.putmedown.businessmodule.login.LoginActivity;
import com.fzuclover.putmedown.businessmodule.setting.SettingActivity;
import com.fzuclover.putmedown.businessmodule.timing.TimingActivity;
import com.fzuclover.putmedown.businessmodule.timingrecord.TimingRecordActivity;
import com.fzuclover.putmedown.model.RecordModel;
import com.fzuclover.putmedown.model.UserModel;
import com.fzuclover.putmedown.view.WaveProgressView;


public class TotalTimingTodayActivity extends BaseActivity implements TotalTimingTodayContract.View ,
        View.OnClickListener{

    private TotalTimingTodayContract.Presenter mPresenter;

    private Button mStartBtn;
    private TextView mSetBtn;
    private TextView mLogoutBtn;
    private LinearLayout mAchievementLayout;
    private LinearLayout mHistoryLayout;
    private WaveProgressView mWaveProgressView;
    private DrawerLayout mDrawerLayout;
    private ImageView mCurrentPlaceImg;
    //今日已经计时的时间(分钟)
    private int mTimedToday;
    //目标时间(分钟)
    private int mTargetTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_timing_today);
        init();
    }

    private void init(){
        mPresenter = new TotalTimingTodayPresenter(this, RecordModel.getRecordModelInstance(),
                UserModel.getUserModelInstance());

        mStartBtn = (Button) findViewById(R.id.start_timing_btn);
        mStartBtn.setOnClickListener(this);
        mSetBtn = (TextView) findViewById(R.id.set_btn);
        mSetBtn.setOnClickListener(this);
        mLogoutBtn = (TextView) findViewById(R.id.logout_btn);
        mLogoutBtn.setOnClickListener(this);
        mAchievementLayout = (LinearLayout) findViewById(R.id.achievement_layout);
        mAchievementLayout.setOnClickListener(this);
        mHistoryLayout = (LinearLayout) findViewById(R.id.history_layout);
        mHistoryLayout.setOnClickListener(this);

        mCurrentPlaceImg = (ImageView) findViewById(R.id.current_place_img);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mWaveProgressView = (WaveProgressView) findViewById(R.id.wave_progress_view);
        mWaveProgressView.setOnClickListener(this);
        //TODO 本日已经计时时间从数据库获取
        mTimedToday = 0;
        //默认目标时间为3小时
        mTargetTime = 3 * 60;
        //设置进度球最大进度为目标时间
        mWaveProgressView.setMaxProgress(mTargetTime);
        mWaveProgressView.setCurrent(mTimedToday, mTimedToday + "min/" + mTargetTime + "min");
        mWaveProgressView.setWaveColor("#5b9ef4");
        //设置波浪高度宽度
        mWaveProgressView.setWave(10,30);
        //设置波动速读
        mWaveProgressView.setmWaveSpeed(10);
        //设置字体颜色大小
        mWaveProgressView.setText("#f0f0f0", 80);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_timing_btn:
                toTimingActivity();
                break;
            case R.id.set_btn:
                toSettingActivity();
                break;
            case R.id.logout_btn:
                toLoginActivity();
                this.finish();
                break;
            case R.id.wave_progress_view:
                showSetTargetTimeDialog();
                break;
            case R.id.achievement_layout:
                toAchievementActivity();
                break;
            case R.id.history_layout:
                toHistoryActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void toSettingActivity() {
        Intent intent = new Intent(TotalTimingTodayActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(TotalTimingTodayActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void toTimingActivity() {
        Intent intent = new Intent(TotalTimingTodayActivity.this, TimingActivity.class);
        startActivity(intent);
    }

    @Override
    public void toAchievementActivity() {
        Intent intent = new Intent(this, AchievementActivity.class);
        startActivity(intent);
    }

    @Override
    public void toHistoryActivity() {
        Intent intent = new Intent(this, TimingRecordActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSetTargetTimeDialog() {
        mTimedToday += 30;
        mWaveProgressView.setCurrent(mTimedToday, mTimedToday + "min/" + mTargetTime + "min");
    }

    @Override
    public void getTargetTime() {

    }

    @Override
    public void setTargetTime() {

    }
}
