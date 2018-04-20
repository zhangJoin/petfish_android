package com.gage.petfish_android.base.contract.fragmentC;

import com.gage.petfish_android.base.BasePresenter;
import com.gage.petfish_android.base.BaseView;
import com.gage.petfish_android.model.bean.FishShow;

/**
 * Created by zhanglonglong on 2017/11/24.
 */

public interface BreedDetailContract {
    interface View extends BaseView {
        void showDetail(FishShow fishShow);
        void showLoading();
        void hideLoading();
    }
    interface Presenter extends BasePresenter<BreedDetailContract.View> {
        void showDetail(String type, String seqId);
    }
}
