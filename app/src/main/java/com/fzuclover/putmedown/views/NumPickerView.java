package com.fzuclover.putmedown.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by lkl on 2016/11/11.
 */

public class NumPickerView extends NumberPicker{
    public NumPickerView(Context context) {
        super(context);
    }


    public NumPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        updateView(child);
    }

    private void updateView(View view) {
        if(view instanceof EditText) {
            ((EditText) view).setTextSize(20);
            ((EditText) view).setFocusable(false);
        }
    }
}
