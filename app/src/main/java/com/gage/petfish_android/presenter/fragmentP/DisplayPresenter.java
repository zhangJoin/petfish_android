package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.DisplayContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 *
 * Created by zll on 2017/11/8.
 */

public class DisplayPresenter extends RxPresenter<DisplayContract.View> implements DisplayContract.Presenter {
    private RestletHelper mRestletHelper;

    @Inject
    public DisplayPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }
}
