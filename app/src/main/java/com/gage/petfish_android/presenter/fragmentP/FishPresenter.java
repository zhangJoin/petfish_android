package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.FishContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 */


public class FishPresenter extends RxPresenter<FishContract.View> implements FishContract.Presenter {

    private RestletHelper mRestletHelper;

    @Inject
    public FishPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }
}
