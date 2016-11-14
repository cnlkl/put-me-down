package com.fzuclover.putmedown.businessmodule.timingrecord;

import com.fzuclover.putmedown.view.pmdtimeline.FreeTimeLineElement;

import java.util.List;

/**
 * Created by lkl on 2016/11/4.
 */

public interface TimingRecordContract {

    interface View {

    }

    interface Presenter {
        List<FreeTimeLineElement> getElements();
    }

}
