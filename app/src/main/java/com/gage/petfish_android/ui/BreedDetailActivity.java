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
import com.gage.petfish_android.base.contract.fragmentC.BreedDetailContract;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.presenter.fragmentP.BreedDetailPresenter;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.widget.pop.PopLoadingWindow;

import butterknife.BindView;

/**
 * Created by zhanglonglong on 2017/11/24.
 */

public class BreedDetailActivity extends BaseActivity<BreedDetailPresenter> implements BreedDetailContract.View {
    @BindView(R.id.common_title)
    TextView commontitle;
    @BindView(R.id.common_back)
    ImageView commonback;
    @BindView(R.id.iv_share)
    Button ivShare;
    @BindView(R.id.tv_breed_detail_title)
    TextView tvBreedDetailTitle;
    @BindView(R.id.tv_breed_detail_date)
    TextView tvBreedDate;
    @BindView(R.id.tv_breed_detail_content)
    TextView tvBreedDetailContent;
    @BindView(R.id.gv_breed_detail)
    GridView gvBreedDetail;
    @BindView(R.id.activity_breed_detail)
    LinearLayout activityBreedDetail;
    PopLoadingWindow popLoadingWindow;

    @Override
    protected int getLayout() {
        return R.layout.activity_breed;
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
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showDetail(FishShow fishShow) {
        if (fishShow != null) {
            if (tvBreedDetailTitle != null && tvBreedDate != null && tvBreedDetailContent != null && gvBreedDetail != null) {
                tvBreedDetailTitle.setText(fishShow.getName());
                tvBreedDate.setText(fishShow.getDate().substring(0, 16));
                tvBreedDetailContent.setText(Html.fromHtml(fishShow.getContent()));
                if (fishShow.getListTP() != null) {
//                tvpicture.setText(R.string.picture);
//                tvpicture.setTextColor(this.getResources().getColor(R.color.text_black_color));
                    ShowDetailAdapter adapter = new ShowDetailAdapter(fishShow.getListTP(), this);
                    gvBreedDetail.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void showLoading() {
        popLoadingWindow.showAtLocation(activityBreedDetail, Gravity.CENTER, 0, 0);
    }

    @Override
    public void hideLoading() {
        popLoadingWindow.dismissPop();
    }
}
