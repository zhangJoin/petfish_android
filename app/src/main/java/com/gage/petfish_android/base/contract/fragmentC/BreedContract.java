package com.gage.petfish_android.base.contract.fragmentC;

import com.gage.petfish_android.base.BasePresenter;
import com.gage.petfish_android.base.BaseView;

/**
 * Created by Administrator on 2017/11/8.
 */

public interface BreedContract {
    interface View extends BaseView {

    }
    interface Presenter extends BasePresenter<View> {

    }
}

