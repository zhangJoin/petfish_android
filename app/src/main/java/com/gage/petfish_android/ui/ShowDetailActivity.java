package com.gage.petfish_android.ui;

import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.ShowDetailAdapter;
import com.gage.petfish_android.base.BaseActivity;
import com.gage.petfish_android.base.contract.fragmentC.ShowDetailContract;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.presenter.fragmentP.ShowDetailPresenter;
import com.gage.petfish_android.util.FontUtil;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.widget.pop.PopLoadingWindow;

import butterknife.BindView;

public class ShowDetailActivity extends BaseActivity<ShowDetailPresenter> implements ShowDetailContract.View {
    @BindView(R.id.common_title)
    TextView commontitle;
    @BindView(R.id.common_back)
    ImageView commonback;
    @BindView(R.id.tv_name)
    TextView tvname;
    @BindView(R.id.tv_detail_price)
    TextView tv_detail_price;
    @BindView(R.id.tv_hibit)
    TextView tvhibit;
    @BindView(R.id.tv_introduction)
    TextView tvintroduction;
    @BindView(R.id.tv_methods)
    TextView tvmethods;
    @BindView(R.id.tv_variety)
    TextView tvvariety;
    @BindView(R.id.tv_picture)
    TextView tvpicture;
    @BindView(R.id.gv_showdetail)
    GridView gvdetail;
    @BindView(R.id.rela_show)
    RelativeLayout relaShow;
    PopLoadingWindow popLoadingWindow;

    @Override
    protected int getLayout() {
        return R.layout.activity_show_detail;
    }

    @Override
    protected void initEventAndData() {
        commontitle.setText("观赏鱼展示");
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
        popLoadingWindow = new PopLoadingWindow(this);

        Intent intent = getIntent();
        if (intent != null) {
            if (SystemUtil.isNetworkConnected()) {
                mPresenter.showDetail(intent.getStringExtra("type"), intent.getStringExtra("seqId"));
            } else {
                ToastUtil.show(getString(R.string.interneterror));
            }
        }

    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    public void showDetail(FishShow fishShow) {
        if (fishShow != null) {
            if (tvname != null && tv_detail_price != null && tvhibit != null && tvintroduction != null && tvmethods != null && tvvariety != null && tvpicture != null && gvdetail != null) {
                tvname.setText(fishShow.getName());
                tv_detail_price.setText(FontUtil.changeTextColor(getString(R.string.floorprice) + "￥" + fishShow.getFloorPrice() + " - " + "￥" + fishShow.getCeilingPrice(), 0, 5, "#323232"));
                tvhibit.setText(FontUtil.changeTextColor(getString(R.string.habit) + fishShow.getContent(), 0, 5, "#323232"));
                tvintroduction.setText(FontUtil.changeTextColor(getString(R.string.introduction) + fishShow.getIntroduction(), 0, 5, "#323232"));
                tvmethods.setText(FontUtil.changeTextColor(getString(R.string.methods) + fishShow.getMethod(), 0, 5, "#323232"));
                tvvariety.setText(FontUtil.changeTextColor(getString(R.string.variety) + fishShow.getpName(), 0, 5, "#323232"));
                if (fishShow.getListTP().size() != 0) {
                    tvpicture.setText(R.string.picture);
                    tvpicture.setTextColor(this.getResources().getColor(R.color.text_black_color));
                    ShowDetailAdapter adapter = new ShowDetailAdapter(fishShow.getListTP(), this);
                    gvdetail.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void showLoading() {
        popLoadingWindow.showAsDropDown(relaShow, Gravity.CENTER, 0, 0);
    }

    @Override
    public void hideLoading() {
        popLoadingWindow.dismissPop();
    }
}
