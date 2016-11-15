package com.fzuclover.putmedown.features.timingrecord;

import com.fzuclover.putmedown.views.pmdtimeline.FreeTimeLineElement;

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
