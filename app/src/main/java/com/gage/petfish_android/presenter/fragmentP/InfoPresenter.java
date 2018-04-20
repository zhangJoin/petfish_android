package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.InfoContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 * ---日期----------维护人-----------
 * 2017/9/13       zuoyouming
 */

public class InfoPresenter extends RxPresenter<InfoContract.View> implements InfoContract.Presenter {
    private RestletHelper mRestletHelper;

    @Inject
    public InfoPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }

}
