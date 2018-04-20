package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.BreedContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 *
 * Created by zll on 2017/11/8.
 */

public class BreedPresenter extends RxPresenter<BreedContract.View> implements BreedContract.Presenter {
    private RestletHelper mRestletHelper;

    @Inject
    public BreedPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }

}
