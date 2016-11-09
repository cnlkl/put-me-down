package com.fzuclover.putmedown.businessmodule.timing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.view.TickTockVIew;

import java.util.Calendar;

public class TimingActivity extends BaseActivity implements TimingContract.Presenter , View.OnClickListener{

    private TickTockVIew mTickTockView;
    private Button mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
        init();
    }

    private void init(){
        mStopBtn = (Button) findViewById(R.id.stop_timing_btn);
        mStopBtn.setOnClickListener(this);

        mTickTockView = (TickTockVIew) findViewById(R.id.count_down_view);
        mTickTockView.setOnTickListener(new TickTockVIew.OnTickListener() {
            @Override
            public String getText(long timeRemainingInMillis) {
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
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Todo 封装到presenter层
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, 5);
        end.add(Calendar.SECOND, 0);

        Calendar start = Calendar.getInstance();

        start.add(Calendar.MINUTE, 0);
        if (mTickTockView != null) {
            mTickTockView.start(start, end);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTickTockView.stop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.stop_timing_btn:
                //todo 设置停止计时按钮的事件到presenter
                break;
            default:
                break;
        }
    }
}
