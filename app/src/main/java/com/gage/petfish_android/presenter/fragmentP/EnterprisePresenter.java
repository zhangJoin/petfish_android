package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.EnterpriseContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 */


public class EnterprisePresenter extends RxPresenter<EnterpriseContract.View> implements EnterpriseContract.Presenter {

    private RestletHelper mRestletHelper;

    @Inject
    public EnterprisePresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }
}
