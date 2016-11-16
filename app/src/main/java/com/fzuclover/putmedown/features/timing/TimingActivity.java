package com.fzuclover.putmedown.features.timing;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.model.RecordModel;
import com.fzuclover.putmedown.utils.LogUtil;
import com.fzuclover.putmedown.views.TickTockView;

import java.util.Calendar;


public class TimingActivity extends BaseActivity implements TimingContract.View , View.OnClickListener{

    private TickTockView mTickTockView;
    private Button mStopBtn;
    private int mTotalTime;
    private int mRemainingTime;
    private TimingPresenter mPresenter;
    private EditText mCommentEdt;
    private boolean mIsSuccess;
    private static final int TIMING_SUCCESS = 0;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
        init();
        mTotalTime = getIntent().getIntExtra("minute",20);
        LogUtil.d("minute", String.valueOf(mTotalTime));
        startTiming(mTotalTime, getIntent().getIntExtra("second", 0));

    }

    private void init(){
        mPresenter = new TimingPresenter(this);
        mIsSuccess = false;

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
                //计时成功时的动作
                mIsSuccess = true;
                mHandler.sendEmptyMessage(TIMING_SUCCESS);
                mPresenter.updateTimingRecord();
                mPresenter.saveTimedToday(TimingActivity.this, mTotalTime);
            }
        });

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case TIMING_SUCCESS:
                        mStopBtn.setText("SUCCESS");
                        break;
                    default:
                        break;
                }
            }
        };

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
                stopTimng();
                break;
            default:
                break;
        }
    }

    @Override
    public void startTiming(int totalTime, int second) {
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, totalTime);
        end.add(Calendar.SECOND, second);

        Calendar start = Calendar.getInstance();

        start.add(Calendar.MINUTE, 0);
        if (mTickTockView != null) {
            mTickTockView.start(start, end);
        }
    }

    @Override
    public void stopTimng() {
        if(mIsSuccess){
            finish();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            View view = getLayoutInflater().inflate(R.layout.dialog_stoptiming_comments, null);
            builder.setView(view);
            mCommentEdt = (EditText) view.findViewById(R.id.comment_edt);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(TextUtils.isEmpty(getFailComments())){
                       toastShort("请输入备注");
                    }else{
                        mPresenter.updateTimingRecord();
                        mTickTockView.stop();
                        finish();
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
    }

    @Override
    public String getFailComments() {
        if(mCommentEdt != null){
            String failComment = mCommentEdt.getText().toString();
            if(!TextUtils.isEmpty(failComment)){
                return failComment;
            }
        }
        return "";
    }

    @Override
    public boolean isSuccess() {
        return mIsSuccess;
    }

    @Override
    public int getRecordId() {
        return getIntent().getIntExtra("id", 0);
    }

    @Override
    public int getTotalTime() {
        return mTotalTime;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        stopTimng();
        return false;
    }

}
