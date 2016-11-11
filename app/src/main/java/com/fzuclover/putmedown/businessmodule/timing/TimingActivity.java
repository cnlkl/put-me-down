package com.fzuclover.putmedown.businessmodule.timing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.businessmodule.totaltimingtoday.TotalTimingTodayActivity;
import com.fzuclover.putmedown.util.LogUtil;
import com.fzuclover.putmedown.view.TickTockView;

import java.util.Calendar;

public class TimingActivity extends BaseActivity implements TimingContract.View , View.OnClickListener{

    private TickTockView mTickTockView;
    private Button mStopBtn;
    private int mTotalTime;
    private int mRemainingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
        init();
        mTotalTime = getIntent().getIntExtra("minute",20);
        LogUtil.d("minute", String.valueOf(mTotalTime));
        startTiming(mTotalTime);
    }

    private void init(){
        mStopBtn = (Button) findViewById(R.id.stop_timing_btn);
        mStopBtn.setOnClickListener(this);

        mTickTockView = (TickTockView) findViewById(R.id.count_down_view);
        mTickTockView.setOnTickListener(new TickTockView.OnTickListener() {
            @Override
            public String getText(long timeRemainingInMillis) {
                if(timeRemainingInMillis != 0){
                    mRemainingTime = (int) (timeRemainingInMillis/1000);
                    int seconds = (int) ( timeRemainingInMillis/ 1000) % 60;
                    int minutes = (int) ((timeRemainingInMillis / (1000 * 60)) % 60);
                    String secStr = String.valueOf(seconds);
                    String minStr = String.valueOf(minutes);
                    if(seconds < 10){
                        secStr = "0" + secStr;
                    }
                    if(minutes < 10){
                        minStr = "0" + minStr;
                    }
                    return minStr + ":" + secStr;
                }else{
                    return "SUCCESS!";
                }

            }
        });

        mTickTockView.setOnFinishListener(new TickTockView.OnFinishListener() {
            @Override
            public void onStop() {
                //TODO 计时结束时的动作
                toastShort("success!!!");
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTickTockView.stop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.stop_timing_btn:
                //todo 设置停止计时按钮的事件到presenter
                showStopTimingDialog();
                stopTimng();
                break;
            default:
                break;
        }
    }

    @Override
    public void startTiming(int totalTime) {
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, totalTime);
        end.add(Calendar.SECOND, 0);

        Calendar start = Calendar.getInstance();

        start.add(Calendar.MINUTE, 0);
        if (mTickTockView != null) {
            mTickTockView.start(start, end);
        }
    }

    @Override
    public void stopTimng() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_stoptiming_comments, null);
        builder.setView(view);
        EditText commentEdt = (EditText) findViewById(R.id.comment_edt);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO 保存终止备注到数据库
                mTickTockView.stop();
                toTotalTimingTodayActivity();
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
    public void showStopTimingDialog() {

    }

    @Override
    public void toTotalTimingTodayActivity() {
        Intent intent = new Intent(TimingActivity.this, TotalTimingTodayActivity.class);
        startActivity(intent);
    }
}
