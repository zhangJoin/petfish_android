package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.BreedDetailContract;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.model.http.RestletHelper;
import com.gage.petfish_android.util.RxUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 *
 * Created by zll on 2017/11/24.
 */

public class BreedDetailPresenter extends RxPresenter<BreedDetailContract.View> implements BreedDetailContract.Presenter {
    private RestletHelper mRestletHelper;

    @Inject
    public BreedDetailPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }

    @Override
    public void showDetail(String type, String seqId) {
        mView.showLoading();
        Disposable disposable = mRestletHelper.contractUrl(Contact.GETDETAILED)
                .setParams("type",type)
                .setParams("seqId",seqId)
                .post()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if(o!=null){
                            String result = o.toString();
                            JSONObject jsonObject=new JSONObject(result);
                            JSONArray jsonArray=jsonObject.getJSONArray("list");
                            FishShow fishShow = new FishShow();
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                fishShow.setName(object.getString("title"));
                                fishShow.setDate(object.getString("updatedate"));
                                fishShow.setContent(object.getString("content"));
                            }
                            JSONArray listTP = jsonObject.getJSONArray("listTP");
                            List<Object> list =new ArrayList<Object>();
                            if(listTP!=null){
                                for (int j=0;j<listTP.length();j++){
                                    list.add(listTP.get(j));
                                }
                            }
                            fishShow.setListTP(list);
                            if(mView!=null){
                                mView.showDetail(fishShow);
                                mView.hideLoading();
                            }
                        }
                    }
                });
        //将subscribe添加进统一管理,避免内存泄漏
        addSubscrebe(disposable);
    }
}
