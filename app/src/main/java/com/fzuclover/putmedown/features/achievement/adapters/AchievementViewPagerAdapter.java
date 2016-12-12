package com.fzuclover.putmedown.features.achievement.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lkl on 2016/12/12.
 */

public class AchievementViewPagerAdapter extends PagerAdapter {

    private List<View> mViewList;

    private String[] mTitle;

    public AchievementViewPagerAdapter(List<View> mViewList, String[] title) {
        this.mViewList = mViewList;
        mTitle = title;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
