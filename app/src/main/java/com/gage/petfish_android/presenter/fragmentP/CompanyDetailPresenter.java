package com.gage.petfish_android.presenter.fragmentP;

import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.RxPresenter;
import com.gage.petfish_android.base.contract.fragmentC.CompanyDetailContract;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.model.bean.ProdunctInfo;
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
 * ---日期----------维护人-----------
 * 2017/11/15       wangxiaoyu
 */
public class CompanyDetailPresenter extends RxPresenter<CompanyDetailContract.View> implements CompanyDetailContract.Presenter {
    private RestletHelper mRestletHelper;

    @Inject
    public CompanyDetailPresenter(RestletHelper mRestletHelper) {
        this.mRestletHelper = mRestletHelper;
    }

    @Override
    public void showDetail(String type, String seqId) {
        mView.showLoading();
        Disposable disposable = mRestletHelper.contractUrl(Contact.GETDETAILED)
                .setParams("type", type)
                .setParams("seqId", seqId)
                .post()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (o != null) {
                            String result = o.toString();
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            FishShow fishShow = new FishShow();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                fishShow.setName(object.getString("name"));
                                fishShow.setAddress(object.getString("address"));
                                fishShow.setDate(object.getString("updatedate"));
                                fishShow.setContent(object.getString("introduction"));
                                fishShow.setPhone(object.getString("contactPhone"));
                                fishShow.setContactPeople(object.getString("contact"));
                            }
                            JSONArray listTP = jsonObject.getJSONArray("listTP");
                            List<Object> list = new ArrayList<>();
                            if (listTP != null) {
                                for (int j = 0; j < listTP.length(); j++) {
                                    list.add(listTP.get(j));
                                }
                            }
                            JSONArray fishName = jsonObject.getJSONArray("fishName");
                            ProdunctInfo info = null;
                            List<ProdunctInfo> listP = new ArrayList<>();
                            if (fishName != null) {
                                for (int i = 0; i < fishName.length(); i++) {
                                    info = new ProdunctInfo();
                                    JSONObject object = fishName.getJSONObject(i);
                                    info.setpName(object.getString("name"));
                                    info.setSeqId(object.getString("sqlId"));
                                    info.setSarverFileName(object.getString("sarverFileName"));
                                    listP.add(info);
                                }
                            }
                            String maxPrice = jsonObject.getString("maxCeilingPrice");
                            String minPrice = jsonObject.getString("minFloorPrice");
                            fishShow.setFishList(listP);
                            fishShow.setListTP(list);
                            if(mView!=null){
                                mView.showInfo(fishShow, maxPrice, minPrice);
                                mView.hideLoading();
                            }

                        }

                    }
                });
        addSubscrebe(disposable);
    }
}
