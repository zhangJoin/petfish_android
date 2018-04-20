package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.OtherDetailContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 *
 * Created by Administrator on 2017/11/24.
 */

public class OtherDetailPresenter extends RxPresenter<OtherDetailContract.View> implements OtherDetailContract.Presenter {
    private RestletHelper mRestletHelper;

    @Inject
    public OtherDetailPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }

}
