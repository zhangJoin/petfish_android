package com.gage.petfish_android.ui;

import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.CompanyDetailAdapter;
import com.gage.petfish_android.adapter.ShowDetailAdapter;
import com.gage.petfish_android.base.BaseActivity;
import com.gage.petfish_android.base.contract.fragmentC.CompanyDetailContract;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.model.bean.ProdunctInfo;
import com.gage.petfish_android.presenter.fragmentP.CompanyDetailPresenter;
import com.gage.petfish_android.ui.view.MyGridView;
import com.gage.petfish_android.util.FontUtil;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.widget.pop.PopLoadingWindow;

import java.util.List;

import butterknife.BindView;

public class CompanyDetailActivity extends BaseActivity<CompanyDetailPresenter> implements CompanyDetailContract.View {

    @BindView(R.id.common_title)
    TextView commontitle;
    @BindView(R.id.common_back)
    ImageView commonback;
    @BindView(R.id.tv_company_title)
    TextView tvCompanyTitle;
    @BindView(R.id.tv_company_date)
    TextView tvCompanyDate;
    @BindView(R.id.tv_company_intro)
    TextView tvCompanyIntro;
    @BindView(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @BindView(R.id.tv_company_phone)
    TextView tvCompanyPhone;
    @BindView(R.id.tv_company_type)
    TextView tvCompanyType;
    @BindView(R.id.tv_company_price)
    TextView tvCompanyPrice;
    @BindView(R.id.gv_company_detail)
    MyGridView gvCompanyDetail;
    @BindView(R.id.tv_company_contactPeople)
    TextView tvCompanyContactPeople;
    @BindView(R.id.tv_company_intro_image)
    TextView tvCompanyIntroImage;
    @BindView(R.id.ll_company_detail)
    LinearLayout llCompanyDetail;
    @BindView(R.id.gv_company_image)
    GridView gvCompanyImage;
    PopLoadingWindow popLoadingWindow;

    @Override
    protected int getLayout() {
        return R.layout.activity_company_detail;
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
    public void showInfo(final FishShow show, String maxPrice, String minPrice) {
        if (show != null) {
            if (tvCompanyTitle != null && tvCompanyDate != null && tvCompanyIntro != null && tvCompanyAddress != null && tvCompanyPhone != null && tvCompanyType != null && tvCompanyContactPeople != null && tvCompanyIntroImage != null && gvCompanyDetail != null && gvCompanyImage != null) {
                tvCompanyTitle.setText(show.getName());
                tvCompanyDate.setText(show.getDate().substring(0, 16));
                String str = "企业简介: " + show.getContent();
                tvCompanyIntro.setText(FontUtil.changeTextColor(str, 0, 5, "#323232"));
                tvCompanyAddress.setText(show.getAddress());
                tvCompanyPhone.setText(show.getPhone());
                tvCompanyPrice.setText("￥" + minPrice + " - " + "￥" + maxPrice);
                tvCompanyContactPeople.setText(show.getContactPeople());
                if (show.getFishList() != null) {
                    StringBuffer oriString = new StringBuffer();
                    List<ProdunctInfo> listP = show.getFishList();

                    for (int i = 0; i < listP.size(); i++) {
                        if (i == 0) {
                            oriString = oriString.append(listP.get(0).getpName().trim().toString());
                        } else {
                            oriString = oriString.append(",").append(listP.get(i).getpName().trim().toString());
                        }
                    }
                    tvCompanyType.setText(oriString.toString());
                }
                if (show.getFishList() != null) {
                    tvCompanyIntroImage.setVisibility(View.VISIBLE);
                    CompanyDetailAdapter adapter = new CompanyDetailAdapter(show.getFishList(), this);
                    gvCompanyDetail.setAdapter(adapter);
                    gvCompanyDetail.setOnItemClickListener(new MyGridView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(CompanyDetailActivity.this, ShowDetailActivity.class);
                            intent.putExtra("seqId", show.getFishList().get(position).getSeqId());
                            intent.putExtra("type", "1");
                            startActivity(intent);
                        }
                    });
                }
                if (show.getListTP().size() != 0) {
                    gvCompanyImage.setVisibility(View.VISIBLE);
                    ShowDetailAdapter adapter = new ShowDetailAdapter(show.getListTP(), this);
                    gvCompanyImage.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void showLoading() {
        popLoadingWindow.showAtLocation(llCompanyDetail, Gravity.CENTER, 0, 0);
    }

    @Override
    public void hideLoading() {
        popLoadingWindow.dismissPop();
    }

}
