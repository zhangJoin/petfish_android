package com.gage.petfish_android.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.ShowDetailAdapter;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.BaseActivity;
import com.gage.petfish_android.base.contract.fragmentC.OtherDetailContract;
import com.gage.petfish_android.component.ImageLoader;
import com.gage.petfish_android.model.bean.OtherDetailInfo;
import com.gage.petfish_android.presenter.fragmentP.OtherDetailPresenter;
import com.gage.petfish_android.util.FontUtil;
import com.gage.petfish_android.util.HttpUtils;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.widget.pop.PopLoadingWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhanglonglong on 2017/11/24.
 */

public class OtherDetailActivity extends BaseActivity<OtherDetailPresenter> implements OtherDetailContract.View {
    @BindView(R.id.common_back)
    ImageView commonBack;
    @BindView(R.id.common_title)
    TextView commonTitle;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.tv_other_name)
    TextView tvOtherName;
    @BindView(R.id.tv_other_price)
    TextView tvOtherPrice;
    @BindView(R.id.tv_other_type)
    TextView tvOtherType;
    @BindView(R.id.tv_other_material)
    TextView tvOtherMaterial;
    @BindView(R.id.tv_other_intro)
    TextView tvOtherIntro;
    @BindView(R.id.tv_other_intro_iamge)
    TextView tvOtherIntroImage;
    @BindView(R.id.gv_other_detail)
    GridView gvOtherDetail;
    @BindView(R.id.ll_line)
    LinearLayout llLine;
    private OtherDetailInfo otherDetailInfo;
    PopLoadingWindow popLoadingWindow;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                popLoadingWindow.dismissPop();
                otherDetailInfo = (OtherDetailInfo) msg.getData().getSerializable("otherDetailInfo");
                if (tvOtherName != null) {
                    tvOtherName.setText(otherDetailInfo.getName());
                }
                if (tvOtherPrice != null) {
                    tvOtherPrice.setText("￥" + otherDetailInfo.getPrice());
                }
                if (tvOtherType != null) {
                    tvOtherType.setText(otherDetailInfo.getfName());
                }
                if (tvOtherMaterial != null) {
                    tvOtherMaterial.setText(otherDetailInfo.getcName());
                }

                String str = getString(R.string.productIntro) + otherDetailInfo.getIntroduction();
                if (TextUtils.isEmpty(otherDetailInfo.getSarverFileName())) {

                    ivOther.setVisibility(View.GONE);
                } else {
                    if (ivOther != null) {
                        ImageLoader.loadImage(OtherDetailActivity.this, Contact.HOST + otherDetailInfo.getSarverFileName(), ivOther);
                    }
                }
                if (tvOtherIntro != null) {
                    tvOtherIntro.setText(FontUtil.changeTextColor(str, 0, 5, "#323232"));
                }
                if (otherDetailInfo.getList().size() != 0) {
                    if (gvOtherDetail != null) {
                        tvOtherIntroImage.setVisibility(View.VISIBLE);
                        ShowDetailAdapter adapter = new ShowDetailAdapter(otherDetailInfo.getList(), OtherDetailActivity.this);
                        gvOtherDetail.setAdapter(adapter);
                    }
                }
            }
        }
    };

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commonTitle.setText("详情查看");
        commonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
        popLoadingWindow = new PopLoadingWindow(this);
        Intent intent = getIntent();
        if (intent != null) {
            if (SystemUtil.isNetworkConnected()) {
                popLoadingWindow.showAtLocation(llLine, Gravity.CENTER, 0, 0);
                getDetailInfo(intent.getStringExtra("seqId"));
            } else {
                ToastUtil.show(getString(R.string.interneterror));
            }
        }

    }

    private void getDetailInfo(final String seqId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Form parameters = new Form();
                parameters.set("seqId", seqId);
                String result = HttpUtils.post(Contact.GETPRODUCTDETAIL, parameters);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    OtherDetailInfo fishShow = new OtherDetailInfo();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        fishShow.setName(object.getString("name"));
                        fishShow.setPrice(object.getDouble("price"));
                        fishShow.setcName(object.getString("cName"));
                        fishShow.setIntroduction(object.getString("introduction"));
                        fishShow.setfName(object.getString("fName"));
                        fishShow.setSarverFileName(object.getString("sarverFileName"));
                    }
                    JSONArray listTP = jsonObject.getJSONArray("listTP");
                    List<Object> list = new ArrayList<Object>();
                    for (int j = 0; j < listTP.length(); j++) {
                        list.add(listTP.get(j));
                    }
                    fishShow.setList(list);
                    Message message = new Message();
                    message.what = 1;
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("otherDetailInfo", (Serializable) fishShow);
                    message.setData(mBundle);
                    mHandler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_other_detail;
    }

}
