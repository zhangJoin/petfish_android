package com.gage.petfish_android.ui.fragment;

//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.BaseListPageAdapter;
import com.gage.petfish_android.adapter.InformationAdapter;
import com.gage.petfish_android.app.Constants;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.model.bean.FishInfo;
import com.gage.petfish_android.model.bean.local.InformationInfo;
import com.gage.petfish_android.model.bean.local.TabIndicator;
import com.gage.petfish_android.presenter.fragmentP.InformationPresenter;
import com.gage.petfish_android.ui.InfoDetailActivity;
import com.gage.petfish_android.util.GsonUtils;
import com.gage.petfish_android.util.HttpUtils;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.view.RefreshLoadListView;
import com.gage.petfish_android.widget.pop.PopLoadingWindow;

import org.restlet.data.Form;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 *
 * Created by zll on 2017/11/8
 */

public class InformationFragment extends BaseFragment<InformationPresenter> implements AdapterView.OnItemClickListener, RefreshLoadListView.OnRefreshListener {
    @BindView(R.id.rv_information)
    RefreshLoadListView rvInformation;
    private int mType = 0;
    private String mTitle;
    private int pageNum = 1;
    private int totalPageNum;
    public static InformationAdapter adapter;
    private InformationInfo informationInfo;
    private PopLoadingWindow mPopLoadingWindow;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                informationInfo = (InformationInfo) msg.getData().getSerializable("informationInfo");
                if (informationInfo.list != null) {
                    if (informationInfo.list.size() > 0) {
                        rvInformation.setVisibility(View.VISIBLE);
                    } else {
                        rvInformation.setVisibility(View.GONE);
                    }
                }
                int totalCount =informationInfo.totalCount;
                if ((totalCount % 10) == 0) {
                    totalPageNum = totalCount / 10;
                } else {
                    totalPageNum = (totalCount / 10) + 1;
                }
                if (adapter.getPage() == 1) {
                    adapter.clearList();
                }
                List<InformationInfo.ListBean> list = informationInfo.list;
                adapter.addList(list);
                adapter.setTotalPage(totalPageNum);
                if (adapter.getPage() == adapter.getTotalPage() || totalPageNum == 0) {
                        rvInformation.onLoadCompile();
                } else {
                    rvInformation.onRefreshCompile();
                }
            }
        }
    };

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    protected void initEventAndData() {
        adapter = new InformationAdapter(getActivity());
        rvInformation.setAdapter(adapter);
        rvInformation.setOnItemClickListener(this);
        rvInformation.setOnRefreshListener(this);
        initRecyclerView(pageNum);
    }

    private void initRecyclerView(final int page) {
        if(SystemUtil.isNetworkConnected()){
            adapter.setPage(page);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form mForm = new Form();
                    mForm.set("type", Constants.INFORMATION_TYPE);
                    mForm.set("pageNum", String.valueOf(page));
                    String result = HttpUtils.post(Contact.GETINFORNATION, mForm);
                    InformationInfo informationInfo = GsonUtils.json2Bean(result, InformationInfo.class);
                    Message message = new Message();
                    message.what = 1;
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("informationInfo", (Serializable) informationInfo);
                    message.setData(mBundle);
                    mHandler.sendMessage(message);

                }
            }).start();
        }else{
            ToastUtil.show(getString(R.string.interneterror));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == adapter.getList().size() + 1) {
//			Toast.makeText(NewsActivity.this, "已经在最底了", Toast.LENGTH_LONG).show();
        } else {
            TabIndicator.getInstance().fishTabIndicator = 0;
            Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
            intent.putExtra("seqId", adapter.getItem(position-1).seqId);
            intent.putExtra("type", "2");
            startActivity(intent);
        }
    }
    @Override
    public void onDownPullRefresh(RefreshLoadListView v, BaseListPageAdapter adapter) {
//        if (adapter.getTotalPage() == totalPageNum) {
//            List<FishInfo> list = adapter.getList();
//            adapter.setList(list);
//            rvInformation.onRefreshCompile();
//        }
        pageNum=1;
        initRecyclerView(pageNum);
    }

    @Override
    public void onLoadingMore(RefreshLoadListView v, BaseListPageAdapter adapter) {
        pageNum++;
        if (pageNum <= totalPageNum) {
            initRecyclerView(pageNum);
        } else {
            pageNum=1;
            List<FishInfo> list = adapter.getList();
            adapter.setList(list);
            rvInformation.onLoadCompile();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNum=1;
    }
}
