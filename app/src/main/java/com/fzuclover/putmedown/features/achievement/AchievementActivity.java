package com.fzuclover.putmedown.features.achievement;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.model.bean.Timescore;
import com.fzuclover.putmedown.views.Charts;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AchievementActivity extends BaseActivity implements AchievementContract.View {
    /*
        todo 在此处声明私有变量
        如：TextView mTotalTime;
     */
    //调用presenter的方法获取数据
    private AchievementContract.Presenter mPresenter;
    private Achievement mtotalAchievement;
    private List<DayAchievement> mdayAchievement;
    private TextView mTotalTimeTv;
    private TextView mTotalTravelTv;
    private TextView mAchievePlaceTv;
    private ImageView mAchievePlaceImageIv;
    private Charts mDayAchivementChart;

    private BarChart mChart;//新增图表
    //存放图表显示的数据

    private Realm realm ;//BarChart数据IO

    private int mtotalTime;

    private int[] achieveData = {400, 400, 400, 400, 400, 400, 400, 400, 400, 400};
    private int[] targetData = {180, 180, 180, 180, 180, 180, 180, 180, 180, 180};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        init();

        //传送数据接口
        toShow();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {

        //todo 在此处做一些变量的初始化工作
        mPresenter = new AchievementPresenter(this);

        mTotalTimeTv = (TextView) findViewById(R.id.activity_totaltime);
        mTotalTravelTv = (TextView) findViewById(R.id.activity_totaltravel);
        mAchievePlaceTv= (TextView) findViewById(R.id.activity_toplace);
        mAchievePlaceImageIv=(ImageView)findViewById(R.id.sceneImage);


        Achievement achievement = mPresenter.getAchievement();

        //mtotalTime=achievement.getTotalTime();
        mtotalTime=240;
        mTotalTimeTv.setText(String.valueOf( mtotalTime)+" 分钟");
        mTotalTravelTv .setText(String.valueOf(parseTravel(mtotalTime))+" KM");
        mAchievePlaceTv.setText(timeToachieveplace(mtotalTime));

        List<DayAchievement> dayAchievements = mPresenter.getDayAchievements();


        for (DayAchievement dayAchievement : dayAchievements) {
            int i = 0, j = 0;

            achieveData[i] = dayAchievement.getTotal_time();
            targetData[j] = dayAchievement.getSucces_times();
            if (i == 9) {
                break;
            }

            i++;
            j++;
        }
        mChart = (BarChart) findViewById(R.id.activity_DayAchievement_charts);
        //mDayAchivementChart = (Charts) findViewById(R.id.activity_DayAchievement_charts);



        mdayAchievement = mPresenter.getDayAchievements();
        mtotalAchievement = mPresenter.getAchievement();


    }

    @Override
    public void toShow() {

        int i;

      //        mDayAchivementChart.setData(achieveData);
      //      mDayAchivementChart.setTargetData(targetData);

       //  mTotalTime.setText(mtotalAchievement.getTotalTime() + "");

      //  BarChartSetData();

    }

    private void BarChartSetData() {

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();//BarChart数据IO
        //存放数据
        Timescore score1 = new Timescore(100, 0f, "Peter");
        realm.copyToRealm(score1);
        Timescore score2 = new Timescore(110, 1f, "Lisa");
        realm.copyToRealm(score2);
        Timescore score3 = new  Timescore(130, 2f, "Dennis");
        realm.copyToRealm(score3);
        Timescore score4 = new  Timescore(70, 3f, "Luke");
        realm.copyToRealm(score4);
        Timescore score5 = new  Timescore(80, 4f, "Sarah");
        realm.copyToRealm(score5);

        realm.commitTransaction();

        //为了显示数据，需将realm中的数据查询出来
        RealmResults< Timescore> results = realm.where( Timescore.class).findAll();

    /*   AxisValueFormatter formatter = new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return results.get((int) value).getNum();
            }

            @Override
            public int getDecimalDigits() { return 0; }
        };*/
        RealmBarDataSet< Timescore>  dataSet = new RealmBarDataSet< Timescore>(results, "scoreNr", "totalScore");

        ArrayList<IBarDataSet> dataSetList = new ArrayList<IBarDataSet>();
        dataSetList.add(dataSet); // add the dataset

         // create a data object with the dataset list
        BarData data = new BarData(dataSetList);
        // additional data styling...
        mChart.setData(data);
        mChart.invalidate();

    }

//将时间映射为路程
    public int parseTravel(int time)
    {
        int timeTotravel;
        timeTotravel=time/60*10;
        return  timeTotravel;

    }
//将时间映射为路程并设置图片
    public String timeToachieveplace(int time)
    {
        int timeTotravel2;
        timeTotravel2=time/60*10;
        if(timeTotravel2>=2725)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.achangbaishan));
            return "长白山";
        }
        else if(timeTotravel2>=2675)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.aqinghaihu));
            return "青海湖";
        }

        else if(timeTotravel2>=2202)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.ajiuzhaigou));
            return  "九寨沟";
        }
        else if(timeTotravel2>=2136)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.ayungangshiku));
            return  "云冈石窟";
        }
        else if(timeTotravel2>=1822)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.ahuanghehukoupubu));
            return "黄河壶口瀑布";
        }
        else if(timeTotravel2>=1704)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.ahuanghehukoupubu));
            return "黄果树瀑布";
        }
        else if(timeTotravel2>=756)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.awuzhen));
            return "乌镇";
        }
        else if(timeTotravel2>=682)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.alushan));
            return "庐山";
        }
        else if(timeTotravel2>=568)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.ahuangshan));
            return "黄山";
        }
        else  if(timeTotravel2>=272)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.awuyishan));
            return "武夷山";
        }

        else if(timeTotravel2>=26)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.afuzhousenlingonngyuan));
            return "福州森林公园";
        }
        else if(timeTotravel2>=13)
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.asanfangqixiang));
            return "三坊七巷";
        }
        else
        {
            mAchievePlaceImageIv.setImageDrawable(getResources().getDrawable(R.mipmap.aqinghaihu));
            return "福州大学";
        }
    }

}
