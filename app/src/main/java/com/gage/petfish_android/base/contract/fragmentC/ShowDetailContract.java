package com.gage.petfish_android.base.contract.fragmentC;

import com.gage.petfish_android.base.BasePresenter;
import com.gage.petfish_android.base.BaseView;
import com.gage.petfish_android.model.bean.FishShow;

/**
 * ---日期----------维护人-----------
 * 2017/11/14       zuoyouming
 */

public interface ShowDetailContract {

    interface View extends BaseView {
        void showDetail(FishShow fishShow);
        void showLoading();
        void hideLoading();
    }
    interface Presenter extends BasePresenter<View> {
        void showDetail(String type, String seqId);
    }
}
