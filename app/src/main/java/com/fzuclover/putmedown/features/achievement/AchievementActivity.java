package com.fzuclover.putmedown.features.achievement;

import android.graphics.Color;
import android.os.Bundle;

import com.fzuclover.putmedown.BaseActivity;
import com.fzuclover.putmedown.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class AchievementActivity extends BaseActivity implements AchievementContract.View {

    private AchievementPresenter mPresenter;

    private PieChart mPieChart;
    private BarChart mTotalBarChart;
    private BarChart mSuccessBarChart;
    private BarChart mFailedBarChart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        init();
    }

    private void init() {
        mPresenter = new AchievementPresenter(this);

        mPieChart = (PieChart) findViewById(R.id.pie_chart);
        initPieChart(mPieChart);

        mTotalBarChart = (BarChart) findViewById(R.id.total_time_bar_chart);
        mSuccessBarChart = (BarChart) findViewById(R.id.success_bar_chart);
        mFailedBarChart = (BarChart) findViewById(R.id.failed_bar_chart);
        initBarChart(mTotalBarChart, mPresenter.getTotalTimeBarDataSet());
        initBarChart(mSuccessBarChart, mPresenter.getSuccessBarDataSet());
        initBarChart(mFailedBarChart, mPresenter.getFailedBarDataSet());

    }

    private void initBarChart(BarChart barChart, BarDataSet barDataSet){

        barChart.getDescription().setEnabled(false);

        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(13);
        xAxis.setLabelRotationAngle(20);

        barChart.getAxisRight().setEnabled(false);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawGridLines(true);
        yAxis.setDrawZeroLine(false);
        yAxis.setTextSize(13);

        // add a nice and smooth animation
        barChart.animateY(2500);
        barChart.getLegend().setEnabled(false);
        barDataSet.setColors(ColorTemplate.getHoloBlue());
        barDataSet.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.setFitBars(true);

        barChart.invalidate();
    }

    private void initPieChart(PieChart mChart){
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText("成功/失败次数");

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        setData(2, 100,mPresenter.getAchievementPieDataSet(), mPieChart);

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
        mChart.setEntryLabelTextSize(12f);

    }

    private void setData(int count, float range,PieDataSet dataSet, PieChart mChart) {

        float mult = range;
        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

}
