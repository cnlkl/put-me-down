package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fzuclover.putmedown.model.IRecordModel;
import com.fzuclover.putmedown.model.IUserModel;

/**
 * Created by lkl on 2016/11/4.
 */

public class TotalTimingTodayPresenter implements TotalTimingTodayContract.Presenter {

    private TotalTimingTodayContract.View mView;
    private IRecordModel mRecordModel;
    private IUserModel mUserModel;

    public TotalTimingTodayPresenter(TotalTimingTodayContract.View view, IRecordModel recordModel,
                                     IUserModel userModel){
        mView = view;
        mRecordModel = recordModel;
        mUserModel = userModel;
    }

    @Override
    public void saveTargetTime(Context context, int targetTime) {
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt("target_time",targetTime);
        editor.commit();
    }

    @Override
    public int getTargetTime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("target_time",180);
    }


}
