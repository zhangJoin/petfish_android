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
import com.gage.petfish_android.base.contract.fragmentC.InfoDetailContract;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.presenter.fragmentP.InfoDetailPresenter;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.widget.pop.PopLoadingWindow;

import butterknife.BindView;

public class InfoDetailActivity extends BaseActivity<InfoDetailPresenter> implements InfoDetailContract.View {
    @BindView(R.id.common_title)
    TextView commontitle;
    @BindView(R.id.common_back)
    ImageView commonback;
    @BindView(R.id.iv_share)
    Button ivShare;
    @BindView(R.id.tv_info_title)
    TextView tvInfoTitle;
    @BindView(R.id.tv_info_date)
    TextView tvInfoDate;
    @BindView(R.id.tv_info_content)
    TextView tvInfoContent;
    @BindView(R.id.gv_infodetail)
    GridView gvInfodetail;
    @BindView(R.id.activity_info_detail)
    LinearLayout activityInfoDetail;
    PopLoadingWindow popLoadingWindow;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_info_detail;
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
        popLoadingWindow = new PopLoadingWindow(this);
        Intent intent = getIntent();
        if (intent != null) {
            if (SystemUtil.isNetworkConnected()) {
                mPresenter.infoDetail(intent.getStringExtra("type"), intent.getStringExtra("seqId"));
            } else {
                ToastUtil.show(getString(R.string.interneterror));
            }

        }

    }

    @Override
    public void showInfo(FishShow show) {
        if (show != null) {
            if (tvInfoTitle != null && tvInfoDate != null && tvInfoContent != null && gvInfodetail != null) {
                tvInfoTitle.setText(show.getName());
                tvInfoDate.setText(show.getDate().substring(0, 16));
                tvInfoContent.setText(Html.fromHtml(show.getContent()));
                if (show.getListTP() != null) {
//                tvpicture.setText(R.string.picture);
//                tvpicture.setTextColor(this.getResources().getColor(R.color.text_black_color));
                    ShowDetailAdapter adapter = new ShowDetailAdapter(show.getListTP(), this);
                    gvInfodetail.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void showLoading() {
        popLoadingWindow.showAtLocation(activityInfoDetail, Gravity.CENTER, 0, 0);
    }

    @Override
    public void hideLoading() {
        popLoadingWindow.dismissPop();
    }
}
