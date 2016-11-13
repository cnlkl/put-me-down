package com.fzuclover.putmedown.businessmodule.timing;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fzuclover.putmedown.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by lkl on 2016/11/4.
 */

public class TimingPresenter implements TimingContract.Presenter {

    public TimingPresenter(){}

    @Override
    public void saveTimedToday(Context context, int time) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = sharedPreferences.getString("date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)) {
            editor.putInt("timed_today", (sharedPreferences.getInt("timed_today", 0) + time));
        }else {
            editor.putInt("timed_today", time);
            editor.putString("date", dateNow);
            editor.commit();
        }
    }
}
