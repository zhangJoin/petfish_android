package com.gage.petfish_android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.BaseListPageAdapter;
import com.gage.petfish_android.adapter.EnterpriseAdapter;
import com.gage.petfish_android.app.Constants;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.model.bean.FishInfo;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.presenter.fragmentP.EnterprisePresenter;
import com.gage.petfish_android.ui.CompanyDetailActivity;
import com.gage.petfish_android.util.HttpUtils;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.view.RefreshLoadListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 */

public class EnterpriseFragment extends BaseFragment<EnterprisePresenter> implements AdapterView.OnItemClickListener,RefreshLoadListView.OnRefreshListener {

    @BindView(R.id.rv_enterprise)
    RefreshLoadListView rvInformation;
    private int mType = 0;
    private String mTitle;
    private int pageNum = 1;
    private int totalPageNum;
    private List<FishShow> list;
    public static EnterpriseAdapter adapter;
    private List<FishShow> listInfo;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                listInfo =(List<FishShow>) msg.getData().getSerializable("list");
                if (listInfo!= null) {
                    if (listInfo.size() > 0) {
                        rvInformation.setVisibility(View.VISIBLE);
                    } else {
                        rvInformation.setVisibility(View.GONE);
                    }
                }
                int totalCount = msg.getData().getInt("totalCount");
                if ((totalCount % 10) == 0) {
                    totalPageNum = totalCount / 10;
                } else {
                    totalPageNum = (totalCount / 10) + 1;
                }
                if (adapter.getPage() == 1) {
                    adapter.clearList();
                }
                adapter.addList(listInfo);
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
        return R.layout.fragment_enterprise;
    }

    @Override
    protected void initEventAndData() {
        adapter = new EnterpriseAdapter(getActivity());
        rvInformation.setAdapter(adapter);
        rvInformation.setOnItemClickListener(this);
        rvInformation.setOnRefreshListener(this);
        if(SystemUtil.isNetworkConnected()){
            initRecyclerView(pageNum);
        }else{
            ToastUtil.show(getString(R.string.interneterror));
        }
    }
    private void initRecyclerView(final int page) {
        adapter.setPage(page);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Form form = new Form();
                form.set("type", Constants.ENTERPRISE_TYPE);
                form.set("pageNum", String.valueOf(page));
                String result = HttpUtils.post(Contact.GETINFORNATION, form);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int totalCount = jsonObject.getInt("totalCount");
                    JSONArray jsonArray=jsonObject.getJSONArray("list");
                    FishShow fishShow = null;
                    list = new ArrayList<FishShow>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        fishShow = new FishShow();
                        JSONObject object = jsonArray.getJSONObject(i);
                        fishShow.setName(object.getString("name"));
                        fishShow.setSeqId(object.getString("seqid"));
                        fishShow.setAddress(object.getString("address"));
                        fishShow.setPhone(object.getString("contactPhone"));
                        fishShow.setImageUrl(object.getString("sarverFileName"));
                        list.add(fishShow);
                    }
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) list);
                    bundle.putInt("totalCount",totalCount);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        if(position==adapter.getList().size()+1){
//			Toast.makeText(NewsActivity.this, "已经在最底了", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(getActivity(), CompanyDetailActivity.class);
            intent.putExtra("seqId",adapter.getItem(position-1).getSeqId());
            intent.putExtra("type", "5");
            startActivity(intent);
        }
    }

    @Override
    public void onDownPullRefresh(RefreshLoadListView v, @SuppressWarnings("rawtypes") BaseListPageAdapter adapter) {
//        if (adapter.getTotalPage() == totalPageNum) {
//            List<FishInfo> list = adapter.getList();
//            adapter.setList(list);
//            rvInformation.onRefreshCompile();
//        }
        pageNum=1;
        initRecyclerView(pageNum);
    }

    @Override
    public void onLoadingMore(RefreshLoadListView v, @SuppressWarnings("rawtypes") BaseListPageAdapter adapter) {
        pageNum++;
        if (pageNum <= totalPageNum) {
            if(SystemUtil.isNetworkConnected()){
                initRecyclerView(pageNum);
            }else{
                ToastUtil.show(getString(R.string.interneterror));
            }
        } else {
            pageNum=1;
            List<FishInfo> list = adapter.getList();
            adapter.clearList();
            adapter.setList(list);
            rvInformation.onLoadCompile();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNum=1;
        adapter.clearList();
    }
}
