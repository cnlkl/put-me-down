package com.fzuclover.putmedown.features.achievement;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.fzuclover.putmedown.features.achievement.adapters.AchievementViewPagerAdapter;
import com.fzuclover.putmedown.model.AchievementModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class AchievementActivity extends BaseActivity implements AchievementContract.View {

    private AchievementPresenter mPresenter;

    private PieChart mPieChart;

    private LineChart mDayTotalTimeLineChart;

    private LineChart mSuccessAndFailedLineChart;

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private LayoutInflater mInflater;

    private List<View> mViewList;

    private View mPieChartLayout;

    private View mLineChartLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        init();
    }

    private void init() {
        mPresenter = new AchievementPresenter(this);
        if(mPresenter.getTotalTime() == 0){
            toastShort("尚未生成记录，请先计时，次日即可查看");
        }

        //初始化tablayout
        mTabLayout = (TabLayout) findViewById(R.id.achievement_tab);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText("总计"));
        mTabLayout.addTab(mTabLayout.newTab().setText("最近7日"));

        mViewList = new ArrayList<View>();
        mInflater = LayoutInflater.from(this);

        //初始化piechart
        mPieChartLayout = mInflater.inflate(R.layout.page_piechart, null);
        mPieChart = (PieChart) mPieChartLayout.findViewById(R.id.pie_chart);
        initPieChart(mPieChart);

        //初始化linechart
        mLineChartLayout = mInflater.inflate(R.layout.page_linechart, null);
        mDayTotalTimeLineChart = (LineChart) mLineChartLayout.findViewById(R.id.day_total_time);
        mSuccessAndFailedLineChart = (LineChart) mLineChartLayout.findViewById(R.id.success_failed_times);
        initLineChart(mDayTotalTimeLineChart,1);
        initLineChart(mSuccessAndFailedLineChart,2);

        mViewList.add(mPieChartLayout);
        mViewList.add(mLineChartLayout);

        //初始化viewpager
        mViewPager = (ViewPager) findViewById(R.id.achievement_viewpager);
        AchievementViewPagerAdapter viewPagerAdapter = new AchievementViewPagerAdapter(mViewList, new String[]{"总计", "最近7日"});
        mViewPager.setAdapter(viewPagerAdapter);

        //tabLayout与viewpager关联
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private void initLineChart(LineChart lineChart,int count){
        // no description text
        lineChart.getDescription().setEnabled(false);

        // enable scaling and dragging
        lineChart.setScaleEnabled(false);

        // add data
        setLineChartData(lineChart, count);

        lineChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(13f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(13f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(false);


        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setTextSize(13f);
        leftAxis.setDrawTopYLabelEntry(true);
        leftAxis.setXOffset(10);
        lineChart.getAxisRight().setEnabled(false);
    }

    private void setLineChartData(LineChart lineChart, int count){
        if(count == 1){
            List<Entry> yVal = new ArrayList<Entry>();
            List<Integer> totalTimeList = mPresenter.getDayTotalTime();
            for (int i = 1; i < totalTimeList.size()+1; i++) {
                yVal.add(new Entry(i, totalTimeList.get(i-1)));
            }
            LineDataSet lineDataSet = new LineDataSet(yVal, "每日累计计时");

            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setColor(Color.parseColor("#5b9ef4"));
            lineDataSet.setCircleColor(Color.parseColor("#5b9ef4"));
            lineDataSet.setLineWidth(2f);
            lineDataSet.setCircleRadius(6f);
            lineDataSet.setHighLightColor(Color.parseColor("#5b9ef4"));
            lineDataSet.setDrawCircleHole(true);

            LineData data = new LineData(lineDataSet);
            data.setValueTextColor(Color.parseColor("#5b9ef4"));
            data.setValueTextSize(12f);

            // set data
            lineChart.setData(data);
        } else {
            List<Entry> successYVal = new ArrayList<Entry>();
            List<Entry> failedYVal = new ArrayList<Entry>();
            List<Integer> successTimes = mPresenter.getDaySuccessTimes();
            List<Integer> failedTimes = mPresenter.getDayFailedTimes();
            for (int i = 1; i < successTimes.size()+1; i++) {
                successYVal.add(new Entry(i, successTimes.get(i-1)));
            }
            for (int i = 1; i < failedTimes.size()+1; i++) {
                failedYVal.add(new Entry(i, failedTimes.get(i-1)));
            }

            LineDataSet successLineDataSet = new LineDataSet(successYVal, "每日成功次数");
            LineDataSet failedLineDataSet = new LineDataSet(failedYVal, "每日失败次数");

            successLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            successLineDataSet.setColor(Color.parseColor("#5b9ef4"));
            successLineDataSet.setCircleColor(Color.parseColor("#5b9ef4"));
            successLineDataSet.setLineWidth(2f);
            successLineDataSet.setCircleRadius(6f);
            successLineDataSet.setHighLightColor(Color.parseColor("#5b9ef4"));
            successLineDataSet.setDrawCircleHole(true);

            failedLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            failedLineDataSet.setColor(Color.parseColor("red"));
            failedLineDataSet.setCircleColor(Color.parseColor("red"));
            failedLineDataSet.setLineWidth(2f);
            failedLineDataSet.setCircleRadius(6f);
            failedLineDataSet.setHighLightColor(Color.parseColor("red"));
            failedLineDataSet.setDrawCircleHole(true);

            LineData data = new LineData(successLineDataSet, failedLineDataSet);
            data.setValueTextSize(12f);

            // set data
            lineChart.setData(data);

        }
    }

    private void initPieChart(PieChart mChart){
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(true);
        Description description = new Description();
        description.setText("成功/失败次数");
        description.setTextSize(10);
        mChart.setDescription(description);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText("累计计时\n" + mPresenter.getTotalTime() + "分钟");
        mChart.setCenterTextSize(16);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(180);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        setPieChartData(mPresenter.getAchievementPieDataSet(), mPieChart);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(13f);

    }

    private void setPieChartData(PieDataSet dataSet, PieChart mChart) {

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#5b9ef4"));
        colors.add(Color.parseColor("red"));
        dataSet.setColors(colors);

        //dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

}
