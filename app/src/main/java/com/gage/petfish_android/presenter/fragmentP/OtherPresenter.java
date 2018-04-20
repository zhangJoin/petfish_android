package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.OtherContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 */


public class OtherPresenter extends RxPresenter<OtherContract.View> implements OtherContract.Presenter {

    private RestletHelper mRestletHelper;

    @Inject
    public OtherPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }
}
