package com.gage.petfish_android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.BaseListPageAdapter;
import com.gage.petfish_android.adapter.DisplayAdapter;
import com.gage.petfish_android.adapter.PopAdapter;
import com.gage.petfish_android.app.Constants;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.base.contract.fragmentC.DisplayContract;
import com.gage.petfish_android.model.bean.DisplayInfo;
import com.gage.petfish_android.model.bean.VarieInfo;
import com.gage.petfish_android.model.bean.local.TabIndicator;
import com.gage.petfish_android.presenter.fragmentP.DisplayPresenter;
import com.gage.petfish_android.ui.ShowDetailActivity;
import com.gage.petfish_android.util.GsonUtils;
import com.gage.petfish_android.util.HttpUtils;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.view.RefreshLoadListView;
import com.gage.petfish_android.widget.pop.BasePopupWindow;

import org.restlet.data.Form;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanglonglong on 2017/11/8.
 * 展示页面
 */

public class DisplayFragment extends BaseFragment<DisplayPresenter> implements DisplayContract.View, AdapterView.OnItemClickListener, RefreshLoadListView.OnRefreshListener {

    @BindView(R.id.tv_display)
    TextView tvDisplay;
    @BindView(R.id.ll_display)
    LinearLayout llDisplay;
    @BindView(R.id.rv_display)
    RefreshLoadListView rvDisplay;
    @BindView(R.id.tv_show_display)
    TextView tvShowDisplay;
    //    private int mType = 0;
    private String seqId;
    private int pageNum = 1;
    private int totalPageNum;
    private DisplayAdapter adapter;
    private DisplayInfo displayInfo;
    private String textShow;
    public static BasePopupWindow commonPopupWindow;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                displayInfo = (DisplayInfo) msg.getData().getSerializable("displayInfo");
                if (displayInfo.list != null) {
                    if (displayInfo.list.size() > 0) {
                        if (rvDisplay != null) {
                            rvDisplay.setVisibility(View.VISIBLE);
                            tvShowDisplay.setVisibility(View.GONE);
                        }
                    } else {
                        rvDisplay.setVisibility(View.GONE);
                        tvShowDisplay.setVisibility(View.VISIBLE);
                    }
                }
                int totalCount = displayInfo.totalCount;

                System.out.println(88%7);
                if ((totalCount % 10) == 0) {
                    totalPageNum = totalCount / 10;
                } else {
                    totalPageNum = (totalCount / 10) + 1;
                }
                if (adapter.getPage() == 1) {
                    adapter.clearList();
                }
                List<DisplayInfo.DisplayBean> list = displayInfo.list;
                adapter.addList(list);
                adapter.setTotalPage(totalPageNum);
                if (adapter.getPage() == adapter.getTotalPage() || totalPageNum == 0) {
                    rvDisplay.onLoadCompile();
                } else {
                    rvDisplay.onRefreshCompile();
                }
            } else if (msg.what == 2) {
                VarieInfo varieInfo = (VarieInfo) msg.getData().getSerializable("varieInfo");
                VarieInfo.TypeListBean bean = new VarieInfo.TypeListBean();
                bean.seqId = "";
                bean.name = "全部";
                varieInfo.TypeList.add(0, bean);
                popSelectMenu(varieInfo.TypeList);
            }
        }
    };

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_display;
    }

    @Override
    protected void initEventAndData() {
        adapter = new DisplayAdapter(getActivity());
        rvDisplay.setAdapter(adapter);
        rvDisplay.setOnItemClickListener(this);
        rvDisplay.setOnRefreshListener(this);
        tvDisplay.setText(textShow==null?"全部":textShow);
        initRecyclerView(pageNum,seqId);
    }

    private void initRecyclerView(final int page,final String seqId) {
        if (SystemUtil.isNetworkConnected()) {
            adapter.setPage(page);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form mForm = new Form();
                    mForm.set("type", Constants.DISPLAY_TYPE);
                    mForm.set("pageNum", String.valueOf(page));
                    mForm.set("seqId", seqId);
                    String result = HttpUtils.post(Contact.GETINFORNATION, mForm);
                    DisplayInfo displayInfo = GsonUtils.json2Bean(result, DisplayInfo.class);
                    handMessage(1, "displayInfo", displayInfo);
                }
            }).start();
        } else {
            ToastUtil.show(getString(R.string.interneterror));
        }
    }

//    public void setType(int mType) {
//        this.mType = mType;
//    }
//
//    public void setTitle(String mTitle) {
//        this.mTitle = mTitle;
//    }

    @OnClick(R.id.ll_display)
    public void onClick(View view) {
        getSearchMc();
    }

    /**
     * 获取观赏鱼展示列表数据
     */
    private void getSearchMc() {
        if (SystemUtil.isNetworkConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = HttpUtils.post(Contact.SEARCHMC, null);
                    VarieInfo varieInfo = GsonUtils.json2Bean(result, VarieInfo.class);
                    handMessage(2, "varieInfo", varieInfo);
                }
            }).start();
        } else {
            ToastUtil.show(getString(R.string.interneterror));
        }

    }

    private void handMessage(int flag, String str, Object obj) {
        Message message = new Message();
        message.what = flag;
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(str, (Serializable) obj);
        message.setData(mBundle);
        mHandler.sendMessage(message);
    }


    /**
     * 展示种类选择
     */
    private void popSelectMenu(List<VarieInfo.TypeListBean> date) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View mView = inflater.inflate(R.layout.view_pop_listview, null);
        ListView lv_pop_menu = (ListView) mView.findViewById(R.id.lv_pop_menu);
        int measuredWidth = llDisplay.getMeasuredWidth();
        ViewGroup.LayoutParams layoutParams = lv_pop_menu.getLayoutParams();
        layoutParams.width = measuredWidth;
        lv_pop_menu.setLayoutParams(layoutParams);
        final PopAdapter mPopAdapter = new PopAdapter(date, getActivity());

        lv_pop_menu.setAdapter(mPopAdapter);
        for (int i = 0; i < date.size(); i++) {
            if (tvDisplay.getText().toString().trim().equals(date.get(i).name)) {
                mPopAdapter.setSeclection(i);
                mPopAdapter.notifyDataSetChanged();
            }
        }

        commonPopupWindow = new BasePopupWindow(getActivity());

        commonPopupWindow
                .inflate(mView)
                .setLayoutParams(BasePopupWindow.WRAP_CONTENT,
                        SystemUtil.getWindowHeight(getActivity()) / 2)
                .showPopAsDown(llDisplay, Gravity.LEFT);
        lv_pop_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopAdapter.setSeclection(position);
                mPopAdapter.notifyDataSetChanged();
                VarieInfo.TypeListBean bean = (VarieInfo.TypeListBean) mPopAdapter.getItem(position);
                textShow=bean.name;
                tvDisplay.setText(bean.name);
                commonPopupWindow.dismiss();
                seqId=bean.seqId;
                getSearchFish(seqId);
            }
        });
    }

    /**
     * 根据条件查询
     */
    private void getSearchFish(final String seqId) {
        pageNum=1;
        initRecyclerView(pageNum,seqId);
//        if (SystemUtil.isNetworkConnected()) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Form mForm = new Form();
//                    mForm.set("seqId", seqId);
//                    String result = HttpUtils.post(Contact.GETSEARCHFISH, mForm);
//                    DisplayInfo displayInfo = GsonUtils.json2Bean(result, DisplayInfo.class);
//                    handMessage(1, "displayInfo", displayInfo);
//                }
//            }).start();
//        } else {
//            ToastUtil.show(getString(R.string.interneterror));
//        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == adapter.getList().size() + 1) {
//			Toast.makeText(NewsActivity.this, "已经在最底了", Toast.LENGTH_LONG).show();
        } else {
            TabIndicator.getInstance().fishTabIndicator = 1;
            Intent intent = new Intent(getActivity(), ShowDetailActivity.class);
            intent.putExtra("seqId", adapter.getItem(position - 1).sqlId);
            intent.putExtra("type", "1");
            startActivity(intent);
        }
    }

    @Override
    public void onDownPullRefresh(RefreshLoadListView v, BaseListPageAdapter adapter) {
        pageNum=1;
        initRecyclerView(pageNum,seqId);
    }

    @Override
    public void onLoadingMore(RefreshLoadListView v, BaseListPageAdapter adapter) {
        pageNum++;
        if (pageNum <= totalPageNum) {
            initRecyclerView(pageNum,seqId);
        } else {
            pageNum=1;
            List<DisplayInfo.DisplayBean> list = adapter.getList();
            adapter.setList(list);
            rvDisplay.onLoadCompile();
        }
    }@Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNum=1;
    }

}
