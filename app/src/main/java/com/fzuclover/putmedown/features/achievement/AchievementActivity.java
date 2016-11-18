package com.fzuclover.putmedown.features.achievement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.model.AchievementModel;
import com.fzuclover.putmedown.model.IAchievementModel;
import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.utils.LogUtil;
import com.fzuclover.putmedown.views.Charts;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends BaseActivity implements AchievementContract.View {
    /*
        todo 在此处声明私有变量
        如：TextView mTotalTime;
     */

    private TextView mTotalTimeTv;

    private TextView mTotalSuccessTv;

    private TextView mTotalFailedTv;

    //调用presenter的方法获取数据
    private AchievementContract.Presenter mPresenter;
    private Achievement mtotalAchievement;
    private List<DayAchievement>  mdayAchievement;
    private TextView mTotalTime;
    private TextView mTotalTravel;
    private TextView mAchievePlace;
    private Charts mDayAchivementChart;

    //存放图表显示的数据

    private int[] achieveData={400,400,400,400,400,400,400,400,400,400};
    private int[] targetData={180,180,180,180,180,180,180,180,180,180};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
         init();

        //传送数据接口
        toShow();
    }

    private void init(){

        //todo 在此处做一些变量的初始化工作
        mPresenter = new AchievementPresenter(this);

        mTotalTimeTv = (TextView) findViewById(R.id.activity_totaltime);
        mTotalSuccessTv = (TextView) findViewById(R.id.activity_totaltravel);
        mTotalFailedTv = (TextView) findViewById(R.id.activity_toplace);

        Achievement achievement = mPresenter.getAchievement();
        mTotalTimeTv.setText(String.valueOf(achievement.getTotalTime()));
        mTotalFailedTv.setText(String.valueOf(achievement.getTotalFailed()));
        mTotalSuccessTv.setText(String.valueOf(achievement.getTotalSuccess()));

        List<DayAchievement> dayAchievements= mPresenter.getDayAchievements();

        for (DayAchievement dayAchievement : dayAchievements){
            int i = 0, j = 0;

            achieveData[i] = dayAchievement.getTotal_time();
            targetData[j] = dayAchievement.getSucces_times();
            if(i == 9){
                break;
            }

            i++;
            j++;
        }

        mDayAchivementChart=(Charts)findViewById(R.id.activity_DayAchievement_charts);
        mTotalTravel=(TextView)findViewById(R.id.activity_totaltravel);
        mTotalTime=(TextView)findViewById(R.id.activity_totaltime);
        mAchievePlace=(TextView)findViewById(R.id.activity_toplace);
        mdayAchievement=mPresenter.getDayAchievements();
        mtotalAchievement=mPresenter.getAchievement();



    }

    @Override
    public void toShow() {

        int i;


        mDayAchivementChart.setData(achieveData);
        mDayAchivementChart.setTargetData(targetData);

        mTotalTime.setText(mtotalAchievement.getTotalTime()+"");

    }
}
