package com.gage.petfish_android.base.contract.fragmentC;

import com.gage.petfish_android.base.BasePresenter;
import com.gage.petfish_android.base.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public interface InformationContract {
    interface View extends BaseView {
         void showInformation(List list);
    }
    interface Presenter extends BasePresenter<View> {
          void getInformation(String type,String page);
    }
}
