package com.fzuclover.putmedown.features.totaltimingtoday;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.features.achievement.AchievementActivity;
import com.fzuclover.putmedown.features.login.LoginActivity;
import com.fzuclover.putmedown.features.setting.SettingActivity;
import com.fzuclover.putmedown.features.timing.TimingActivity;
import com.fzuclover.putmedown.features.timingrecord.TimingRecordActivity;
import com.fzuclover.putmedown.utils.LogUtil;
import com.fzuclover.putmedown.utils.SharePreferenceUtil;
import com.fzuclover.putmedown.views.NumPickerView;
import com.fzuclover.putmedown.views.WaveProgressView;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import at.markushi.ui.CircleButton;

public class TotalTimingTodayActivity extends BaseActivity implements TotalTimingTodayContract.View, View.OnClickListener {

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
    //按两次退出app标志
    private boolean mExitTag;
    private Handler mHandler;
    private final int MSG_EXIT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_timing_today);
        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mTimedToday = mPresenter.getTimedToday();
        mWaveProgressView.setCurrent(mTimedToday, mTimedToday + "min/" + mTargetTime + "min");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {

        mExitTag = false;

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_EXIT:
                        mExitTag = false;
                        break;
                }
            }
        };

        mPresenter = new TotalTimingTodayPresenter(this);

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
        //今日已计时时间
        mTimedToday = mPresenter.getTimedToday();
        mTargetTime = mPresenter.getTargetTime();
        //设置进度球最大进度为目标时间
        mWaveProgressView.setMaxProgress(mTargetTime);
        mWaveProgressView.setCurrent(mTimedToday, mTimedToday + "min/" + mTargetTime + "min");
        mWaveProgressView.setWaveColor("#5b9ef4");
        //设置波浪高度宽度
        mWaveProgressView.setWave(10, 30);
        //设置波动速读
        mWaveProgressView.setmWaveSpeed(10);
        //设置字体颜色大小
        mWaveProgressView.setText("#f0f0f0", 80);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_timing_btn:
                showEditTimingCommentsDialog();
                break;
            case R.id.set_btn:
                toSettingActivity();
                break;
            case R.id.logout_btn:
                mPresenter.setLoginStatu(false);
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
        Intent intent = new Intent(TotalTimingTodayActivity.this,SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(TotalTimingTodayActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void toTimingActivity(int mimute, int id) {
        Intent intent = new Intent(TotalTimingTodayActivity.this, TimingActivity.class);
        intent.putExtra("minute", mimute);
        intent.putExtra("id", id);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置每日目标时间");
        builder.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_set_target_time, null);
        builder.setView(view);
        final EditText targetTimeEdt = (EditText) view.findViewById(R.id.set_target_time_edt);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //正则表达式判断输入是否是数字
                Pattern pattern = Pattern.compile("[0-9]*");
                String targetTimeStr = targetTimeEdt.getText().toString();
                if (!TextUtils.isEmpty(targetTimeStr)) {
                    Matcher isNum = pattern.matcher(targetTimeStr);
                    if (isNum.matches()) {
                        int temp;
                        temp = Integer.valueOf(targetTimeStr);
                        if (temp <= 1440) {
                            mTargetTime = temp;
                            mPresenter.saveTargetTime(mTargetTime);
                            mWaveProgressView.setMaxProgress(mTargetTime);
                            mWaveProgressView.setCurrent(mTimedToday, mTimedToday + "min/" + mTargetTime + "min");
                        } else {
                            toastShort("目标时间超过24小时啦>.<");
                        }
                    } else {
                        toastShort("请输入数字");
                    }
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
        targetTimeEdt.setText(String.valueOf(mTargetTime));
    }

    @Override
    public void showEditTimingCommentsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_set_timing_comments, null);
        //设置时间选择
        final NumPickerView minutePicker = (NumPickerView) view.findViewById(R.id.minute_picker);
        //TODO 1分钟测试用，后期删除
        final String[] minuteStrs = {"15", "30", "45", "60", "1"};
        minutePicker.setDisplayedValues(minuteStrs);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(4);

        builder.setView(view);
        final EditText commentEdt = (EditText) view.findViewById(R.id.comment_edt);
        builder.setPositiveButton("开始", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int index = minutePicker.getValue();
                int minute = Integer.valueOf(minuteStrs[index]);
                String comment = commentEdt.getText().toString();
                if(TextUtils.isEmpty(comment)){
                    toastShort("请输入备注");
                }else{
                    int id = mPresenter.saveTimingRecord(minute, comment);
                    toTimingActivity(minute, id);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (!mExitTag) {
                mExitTag = true;
               toastShort("再按一次退出程序");
                mHandler.sendEmptyMessageDelayed(MSG_EXIT, 2000);
            } else {
                this.finish();
            }
        }
        return false;
    }
}
