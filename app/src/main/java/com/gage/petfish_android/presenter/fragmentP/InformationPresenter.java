package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.InformationContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 *
 * Created by zhanglonglong on 2017/11/8.
 */

public class InformationPresenter extends RxPresenter<InformationContract.View> implements InformationContract.Presenter {
    private  RestletHelper mRestletHelper;

    @Inject
    public InformationPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }
    @Override
    public void getInformation(String type, String page) {
//        mView.showLoading();    //加载中
//        if (SystemUtil.isNetworkConnected()) {
//            InformationFragment.adapter.setPage(Integer.parseInt(page));
//            Disposable disposable = mRestletHelper.contractUrl(Contact.GETINFORNATION)
//                    .setParams("type", type)
//                    .setParams("pageNum",page)
//                    .post()
//                    .compose(RxUtil.rxSchedulerHelper())
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(@NonNull Object o) throws Exception {
//                            if (o != null) {
//                                String result = o.toString();
//                                JSONArray jsonArray = new JSONArray(result);
//                                FishInfo info = null;
//                                List<FishInfo> list = new ArrayList<FishInfo>();
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    info = new FishInfo();
//                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                    info.setName(jsonObject.getString("title"));
////                                info.setSeqId(jsonObject.getString("seqId"));
//                                    info.setDate(jsonObject.getString("updatetime"));
//                                    list.add(info);
//                                }
//                                mView.showInformation(list);
//                            } else {
//                                mView.showError();
//                            }
//                            mView.hideLoading();
//                        }
//                    });
//
//            //将subscribe添加进统一管理,避免内存泄漏
//            addSubscrebe(disposable);
//        } else {
//            mView.showError();
//        }
//
    }
}
