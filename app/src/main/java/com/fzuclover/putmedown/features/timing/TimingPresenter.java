package com.fzuclover.putmedown.features.timing;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fzuclover.putmedown.model.IRecordModel;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by lkl on 2016/11/4.
 */

public class TimingPresenter implements TimingContract.Presenter {

    private TimingContract.View mView;
    private IRecordModel mRecordModel;

    public TimingPresenter(TimingContract.View view, IRecordModel recordModel){
        mView = view;
        mRecordModel = recordModel;
    }

    @Override
    public void saveTimedToday(Context context, int time) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = sharedPreferences.getString("date", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(new Date());
        if(date.equals(dateNow)) {
            editor.putInt("timed_today", (sharedPreferences.getInt("timed_today", 0) + time));
            editor.commit();
        }else {
            editor.putInt("timed_today", time);
            editor.putString("date", dateNow);
            editor.commit();
        }
    }

    @Override
    public void updateTimingRecord(Boolean isSuccess, int id) {
        String failComment = mView.getFailComments();
        mRecordModel.updateTimingRecord(isSuccess, id, failComment);
    }
}
