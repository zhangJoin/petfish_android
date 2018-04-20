package com.gage.petfish_android.base.contract.fragmentC;

import com.gage.petfish_android.base.BasePresenter;
import com.gage.petfish_android.base.BaseView;
import com.gage.petfish_android.model.bean.ContactUsBean;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 */

public interface LineContract {
    interface View extends BaseView {
        void showContactUs(ContactUsBean obj);
    }

    interface Presenter extends BasePresenter<View> {
        void getContactUs();
    }

}
