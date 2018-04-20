package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.LineContract;
import com.gage.petfish_android.model.bean.ContactUsBean;
import com.gage.petfish_android.model.http.RestletHelper;
import com.gage.petfish_android.util.RxUtil;
import com.gage.petfish_android.util.SystemUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 */


public class LinePresenter extends RxPresenter<LineContract.View> implements LineContract.Presenter {

    private RestletHelper mRestletHelper;

    @Inject
    public LinePresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }

    @Override
    public void getContactUs() {
//        mView.showLoading();    //加载中
        if(SystemUtil.isNetworkConnected()){
            Disposable disposable = mRestletHelper.contractUrl(Contact.GETCONTACTUS)
                    .setParams("","")
                    .post()
                    .compose(RxUtil.rxSchedulerHelper())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(@NonNull Object o) throws Exception {
                            if(o!=null){
                                String result = o.toString();
                                JSONArray mJSONArray=new JSONArray(result);
                                final ContactUsBean mContactUsBean=new ContactUsBean();
                                JSONObject jsonObject = mJSONArray.getJSONObject(0);
                                mContactUsBean.setEmail(jsonObject.getString("email"));
                                mContactUsBean.setAddress(jsonObject.getString("address"));
                                mContactUsBean.setEnterpriseIntroduction(jsonObject.getString("enterpriseIntroduction"));
                                mContactUsBean.setImageUrl(jsonObject.getString("sarverFileName"));
                                mContactUsBean.setName(jsonObject.getString("name"));
                                mContactUsBean.setTel(jsonObject.getString("tel"));
                                mView.showContactUs(mContactUsBean);
                            }else{
//                                mView.showError();
                            }
//                            mView.hideLoading();
                        }
                    });

            //将subscribe添加进统一管理,避免内存泄漏
            addSubscrebe(disposable);
        }else{
//            mView.showError();
        }

    }
}
