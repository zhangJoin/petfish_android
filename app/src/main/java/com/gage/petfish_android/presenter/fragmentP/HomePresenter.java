package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.HomeContract;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Inject;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 */


public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    private RestletHelper mRestletHelper;

    @Inject
    public HomePresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }

//    @Override
//    public void show(final String type) {
//        mView.showLoading();    //加载中
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String result = mRestletHelper.contractUrl(Contact.GETINFORNATION)
//                        .setParams("type", type)
//                        .post();
//                List<FishInfo> list=new ArrayList<FishInfo>();
//                JSONArray jsonArray= null;
//                try {
//                    jsonArray = new JSONArray(result);
//                    FishInfo fishInfo=null;
//                    for (int i = 0; i <jsonArray.length() ; i++) {
//                        fishInfo=new FishInfo();
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        fishInfo.setName(jsonObject.getString("hibit"));
//                        fishInfo.setDate(jsonObject.getString("name"));
//                        list.add(fishInfo);
//                    }
//                    mView.getSuccess(list);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

//        Disposable disposable = mRestletHelper.contractUrl(Contact.GETINFORNATION)
//                .setParams("type",type)
//                .post()
//                .compose(RxUtil.rxSchedulerHelper())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(@NonNull Object o) throws Exception {
//
//                        mView.hideLoading();
//                    }
//                });

        //将subscribe添加进统一管理,避免内存泄漏
//    }
//    @Override
//    public void show1(final String type) {
//        mView.showLoading();    //加载中
//        Disposable disposable1 = mRestletHelper.contractUrl(Contact.GETINFORNATION)
//                .setParams("type", type1)
//                .post()
//                .compose(RxUtil.rxSchedulerHelper())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(@NonNull Object o) throws Exception {
//
//                        mView.hideLoading();
//                    }
//                });

        //将subscribe添加进统一管理,避免内存泄漏
//        addSubscrebe(disposable1);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String result = mRestletHelper.contractUrl(Contact.GETINFORNATION)
//                        .setParams("type", type)
//                        .post();
//                List<FishInfo> list=new ArrayList<FishInfo>();
//                JSONArray jsonArray= null;
//                try {
//                    jsonArray = new JSONArray(result);
//                    FishInfo fishInfo=null;
//                    for (int i = 0; i <jsonArray.length() ; i++) {
//                        fishInfo=new FishInfo();
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        fishInfo.setName(jsonObject.getString("title"));
//                        fishInfo.setDate(jsonObject.getString("updatetime"));
//                        list.add(fishInfo);
//                    }
//                    mView.getinformationSuccess(list);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}
