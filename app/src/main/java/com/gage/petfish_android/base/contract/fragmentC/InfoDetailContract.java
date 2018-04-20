package com.gage.petfish_android.base.contract.fragmentC;

import com.gage.petfish_android.base.BasePresenter;
import com.gage.petfish_android.base.BaseView;
import com.gage.petfish_android.model.bean.FishShow;

/**
 * ---日期----------维护人-----------
 * 2017/11/8       zuoyouming
 */

public interface InfoDetailContract {
    interface View extends BaseView {
        void showInfo(FishShow show);
        void showLoading();
        void hideLoading();
    }

    interface Presenter extends BasePresenter<View> {
        void infoDetail(String type, String seqId);
    }
}
