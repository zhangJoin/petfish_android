package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.DiseaseContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 *
 * Created by zll on 2017/11/8.
 */

public class DiseasePresenter extends RxPresenter<DiseaseContract.View> implements DiseaseContract.Presenter {
    private RestletHelper mRestletHelper;

    @Inject
    public DiseasePresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }

}
