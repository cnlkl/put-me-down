package com.fzuclover.putmedown.businessmodule.totaltimingtoday;

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
    public void logout() {

    }
}
