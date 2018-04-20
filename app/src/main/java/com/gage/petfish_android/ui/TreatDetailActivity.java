package com.gage.petfish_android.ui;


import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.ShowDetailAdapter;
import com.gage.petfish_android.base.BaseActivity;
import com.gage.petfish_android.base.contract.fragmentC.TreatDetailContract;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.presenter.fragmentP.TreatDetailPresenter;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.widget.pop.PopLoadingWindow;

import butterknife.BindView;

public class TreatDetailActivity extends BaseActivity<TreatDetailPresenter> implements TreatDetailContract.View {

    @BindView(R.id.common_title)
    TextView commontitle;
    @BindView(R.id.common_back)
    ImageView commonback;
    @BindView(R.id.iv_share)
    Button ivShare;
    @BindView(R.id.tv_treat_title)
    TextView tvTreatTitle;
    @BindView(R.id.tv_treat_date)
    TextView tvTreatDate;
    @BindView(R.id.tv_treat_content)
    TextView tvTreatContent;
    @BindView(R.id.gv_treat_detail)
    GridView gvTreatDetail;
    @BindView(R.id.ll_treat_detail)
    LinearLayout llTreatDetail;
    PopLoadingWindow popLoadingWindow;

    @Override
    protected int getLayout() {
        return R.layout.activity_treat_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        commontitle.setText("详情查看");
        commonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
        Intent intent = getIntent();
        popLoadingWindow = new PopLoadingWindow(this);
        if (intent != null) {
            if (SystemUtil.isNetworkConnected()) {
                mPresenter.showDetail(intent.getStringExtra("type"), intent.getStringExtra("seqId"));
            } else {
                ToastUtil.show(getString(R.string.interneterror));
            }

        }

    }

    @Override
    public void showDetail(FishShow fishShow) {
        if (fishShow != null) {
            if (tvTreatTitle != null && tvTreatDate != null && tvTreatContent != null && gvTreatDetail != null) {
                tvTreatTitle.setText(fishShow.getName());
                tvTreatDate.setText(fishShow.getDate().substring(0, 16));
                tvTreatContent.setText(Html.fromHtml(fishShow.getContent()));
                if (fishShow.getListTP() != null) {
//                tvpicture.setText(R.string.picture);
//                tvpicture.setTextColor(this.getResources().getColor(R.color.text_black_color));
                    ShowDetailAdapter adapter = new ShowDetailAdapter(fishShow.getListTP(), this);
                    gvTreatDetail.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void showLoading() {
        popLoadingWindow.showAtLocation(llTreatDetail, Gravity.CENTER, 0, 0);
    }

    @Override
    public void hideLoading() {
        popLoadingWindow.dismissPop();
    }

}
