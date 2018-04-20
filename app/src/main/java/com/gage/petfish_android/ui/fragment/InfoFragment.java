package com.gage.petfish_android.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.base.contract.fragmentC.InfoContract;
import com.gage.petfish_android.presenter.fragmentP.InfoPresenter;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * ---日期----------维护人-----------
 * 2017/9/13       zuoyouming
 */

public class InfoFragment extends BaseFragment<InfoPresenter> implements InfoContract.View {


    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    @BindView(R.id.bga_refresh)
    BGARefreshLayout bgaRefresh;

    private int mType = 0;
    private String mTitle;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void initEventAndData() {
        initRecyclerView();
        initBGARefresh(bgaRefresh);
    }

    private void initRecyclerView() {
        //测试数据

//        List<FishInfo> fishInfos = new ArrayList<>();
//
//        FishInfo fishInfo = new FishInfo();
//        fishInfo.title = "标题";
//        fishInfo.date = "20170912 11:00";
//        fishInfos.add(fishInfo);
//        fishInfos.add(fishInfo);
//        fishInfos.add(fishInfo);
//        fishInfos.add(fishInfo);
//
//
//
//        rvInfo.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,true));
//        FishInfoAdapter fishInfoAdapter = new FishInfoAdapter(rvInfo);
//        rvInfo.setAdapter(fishInfoAdapter);
//
//        fishInfoAdapter.setData(fishInfos);

    }


    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        super.onBGARefreshLayoutBeginRefreshing(refreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return super.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
    }

}
