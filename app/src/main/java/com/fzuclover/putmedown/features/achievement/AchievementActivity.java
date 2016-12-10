package com.fzuclover.putmedown.features.achievement;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.model.bean.Achievement;
import com.fzuclover.putmedown.model.bean.DayAchievement;
import com.fzuclover.putmedown.model.bean.Timescore;
import com.fzuclover.putmedown.views.Charts;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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
    private List<DayAchievement> mdayAchievement;
    private TextView mTotalTime;
    private TextView mTotalTravel;
    private TextView mAchievePlace;
    private Charts mDayAchivementChart;

    private LineChart mChart;//新增图表
    //存放图表显示的数据

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
        mTotalSuccessTv = (TextView) findViewById(R.id.activity_totaltravel);
        mTotalFailedTv = (TextView) findViewById(R.id.activity_toplace);

        Achievement achievement = mPresenter.getAchievement();
        mTotalTimeTv.setText(String.valueOf(achievement.getTotalTime()));
        mTotalFailedTv.setText(String.valueOf(achievement.getTotalFailed()));
        mTotalSuccessTv.setText(String.valueOf(achievement.getTotalSuccess()));

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

        mDayAchivementChart = (Charts) findViewById(R.id.activity_DayAchievement_charts);
        mTotalTravel = (TextView) findViewById(R.id.activity_totaltravel);
        mTotalTime = (TextView) findViewById(R.id.activity_totaltime);
        mAchievePlace = (TextView) findViewById(R.id.activity_toplace);
        mdayAchievement = mPresenter.getDayAchievements();
        mtotalAchievement = mPresenter.getAchievement();


    }

    @Override
    public void toShow() {

        int i;

        mDayAchivementChart.setData(achieveData);
        mDayAchivementChart.setTargetData(targetData);

        mTotalTime.setText(mtotalAchievement.getTotalTime() + "");

    }

    private void linesChartSetData() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Timescore> result = realm.where(Timescore.class).findAll();
        RealmLineDataSet<Timescore> set = new RealmLineDataSet<Timescore>(result, "xValue", "yValue");

        //图表属性设置
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setLabel("Realm LineDataSet");
        set.setDrawCircleHole(false);
        set.setColor(ColorTemplate.rgb("#FF5722"));
        set.setCircleColor(ColorTemplate.rgb("#FF5722"));
        set.setLineWidth(1.8f);
        set.setCircleRadius(3.6f);


        //放置图表显示数据
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        LineData data = new LineData(dataSets);


        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Achievement Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
