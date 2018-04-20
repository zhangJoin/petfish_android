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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.OtherAdapter;
import com.gage.petfish_android.adapter.SearchAdapter;
import com.gage.petfish_android.adapter.SearchVarietyAdapter;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.base.contract.fragmentC.OtherContract;
import com.gage.petfish_android.model.bean.OtherInfo;
import com.gage.petfish_android.model.bean.VarieInfo;
import com.gage.petfish_android.presenter.fragmentP.OtherPresenter;
import com.gage.petfish_android.ui.OtherDetailActivity;
import com.gage.petfish_android.ui.view.MyGridView;
import com.gage.petfish_android.util.DateUtil;
import com.gage.petfish_android.util.GsonUtils;
import com.gage.petfish_android.util.HttpUtils;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.widget.pop.BasePopupWindow;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.restlet.data.Form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zhanglonglong
 * 周边产品
 */

public class OtherFragment extends BaseFragment<OtherPresenter> implements OtherContract.View, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {


    @BindView(R.id.iv_search_other)
    ImageView ivSearchOther;
    @BindView(R.id.gv_other)
    PullToRefreshGridView gvOther;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.rl_display)
    RelativeLayout rlDisplay;
    //    @BindView(R.id.tv_foot)
//    TextView tvFoot;
    private OtherAdapter adapter;
    private int pageNum = 1;
    private int totalPageNum;
    private VarieInfo varieInfo;
    private OtherInfo otherInfo;

    private Handler mHandler = new Handler() {

        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                otherInfo = (OtherInfo) msg.getData().get("otherInfo");

                if (otherInfo.list.size() != 0) {
                    tvShow.setVisibility(View.GONE);
                    gvOther.setVisibility(View.VISIBLE);
                    int totalCount = otherInfo.totalCount;
                    if (totalCount <= 10) {
                        gvOther.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                        tvFoot.setVisibility(View.VISIBLE);
                    } else {
                        gvOther.setMode(PullToRefreshBase.Mode.BOTH);
                    }
                    if ((totalCount % 10) == 0) {
                        totalPageNum = totalCount / 10;
                    } else {
                        totalPageNum = (totalCount / 10) + 1;
                    }
                    if (adapter.getPage() == 1) {
                        adapter.clearList();
                    }
                    adapter.addList(otherInfo.list);
                    adapter.setTotalPage(totalPageNum);
                    if (adapter.getPage() == adapter.getTotalPage()
                            || totalPageNum == 0) {
                        gvOther.onRefreshComplete();
                        if (pageNum > 1) {
                            gvOther.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                            ToastUtil.bottomShow("已经在最底了");
//                            tvFoot.setVisibility(View.VISIBLE);
//                            gvOther.addView(tvFoot,adapter.getList().size());
                        }
                    } else {
                        gvOther.onRefreshComplete();
                    }
                } else {
                    tvShow.setVisibility(View.VISIBLE);
                    gvOther.setVisibility(View.GONE);
                }

            } else if (msg.what == 2) {
                varieInfo = (VarieInfo) msg.getData().getSerializable("varieInfo");

                popSelectMenu(varieInfo);
            }
        }

    };

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other;
    }

    private Boolean isLastRow = false;

    @Override
    protected void initEventAndData() {
        adapter = new OtherAdapter(getActivity());
        gvOther.setAdapter(adapter);
        gvOther.setOnItemClickListener(this);
        // 设置gridview的模式
        gvOther.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout headerLayout = gvOther.getLoadingLayoutProxy(true, false);
        headerLayout.setReleaseLabel("松开刷新...");

        //设置底部刷新文字
//        ILoadingLayout footLayout = gvOther.getLoadingLayoutProxy(false, true);
//        footLayout.setPullLabel("正在加载更多");
//        footLayout.setRefreshingLabel("正在疯刷新数据...");
//        footLayout.setReleaseLabel("松开完成刷新...");
//        footLayout.setLoadingDrawable();
        gvOther.setOnRefreshListener(this);
        initRecyclerView(pageNum, str1, str2);
//        gvOther.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if(isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
//                    isLastRow = false;
//                    ToastUtil.bottomShow("已经在最底了");
//                }
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 10) {
//                    System.out.print(view.getLastVisiblePosition());
//                    isLastRow = true;
//                }
//            }
//        });
    }

    /**
     * 查询数据
     *
     * @param page
     */
    private void initRecyclerView(final int page, final String str1, final String str2) {
        if (SystemUtil.isNetworkConnected()) {
            adapter.setPage(page);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form mForm = new Form();
                    mForm.set("pageNum", String.valueOf(page));
                    mForm.set("str1", str1);
                    mForm.set("str2", str2);
                    String result = HttpUtils.post(Contact.GETPATFISHPRODUCT, mForm);
                    OtherInfo otherInfo = GsonUtils.json2Bean(result, OtherInfo.class);
                    handMessage(1, "otherInfo", otherInfo);
                }
            }).start();
        } else {
            ToastUtil.show(getString(R.string.interneterror));
        }

    }

    @OnClick(R.id.iv_search_other)
    public void onClick(View v) {
        if (SystemUtil.isNetworkConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = HttpUtils.post(Contact.SEARCHZBCP, null);
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
     * item点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == adapter.getList().size() + 1) {
//			Toast.makeText(NewsActivity.this, "已经在最底了", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(getActivity(), OtherDetailActivity.class);
            intent.putExtra("seqId", adapter.getItem(position).sqlId);
            startActivity(intent);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//        if (adapter.getTotalPage() == totalPageNum) {
//            List<OtherInfo.OtherBean> list = adapter.getList();
//            adapter.setList(list);
//            gvOther.onRefreshComplete();
//        }
        pageNum = 1;
        initRecyclerView(pageNum, str1, str2);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageNum++;
        if (pageNum <= totalPageNum) {
            initRecyclerView(pageNum, str1, str2);
        } else {
            pageNum = 1;
            List<OtherInfo.OtherBean> list = adapter.getList();
            adapter.setList(list);
            gvOther.onRefreshComplete();
            ToastUtil.bottomShow("已经在最底了");
        }
    }

    private String varietyStr, materialStr;
    private SearchVarietyAdapter mAdapter;
    private SearchAdapter mSearchAdapter;
    private List<String> vaList = new ArrayList<>();//用于存储已选的分类
    private List<String> maList = new ArrayList<>();//用于存储已选的品种
    private BasePopupWindow commonPopupWindow;

    /**
     * 展示周边显示选择
     */
    private void popSelectMenu(final VarieInfo varieInfo) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.view_pop_gridview, null);
        final MyGridView gv_material = (MyGridView) mView.findViewById(R.id.gv_material);
        final MyGridView gv_variety = (MyGridView) mView.findViewById(R.id.gv_variety);
        LinearLayout ll_pop_material = (LinearLayout) mView.findViewById(R.id.ll_pop_material);
        LinearLayout ll_pop_variety = (LinearLayout) mView.findViewById(R.id.ll_pop_variety);
        final TextView varietyAll = (TextView) mView.findViewById(R.id.tv_pop_variety_all);
        final TextView materialAll = (TextView) mView.findViewById(R.id.tv_pop_material_all);
        Button btnSearch = (Button) mView.findViewById(R.id.btn_search);
        Button btnReset = (Button) mView.findViewById(R.id.btn_reset);
        setLayoutParams(ll_pop_variety);
        setLayoutParams(ll_pop_material);
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
        } else {
            commonPopupWindow = new BasePopupWindow(getActivity());
        }

        commonPopupWindow
                .inflate(mView)
                .setLayoutParams(BasePopupWindow.MATCH_PARENT,
                        SystemUtil.getWindowHeight(getActivity()) - rlDisplay.getHeight() - 50)
                .showPopAsDown(rlDisplay, Gravity.LEFT);

        mAdapter = new SearchVarietyAdapter(varieInfo.TypeList, getActivity());
        gv_variety.setAdapter(mAdapter);
        if (vaList.size() != 0) {
            varietyAll.setTextColor(getResources().getColor(R.color.text_normal_color));
            for (int i = 0; i < vaList.size(); i++) {
                for (int j = 0; j < varieInfo.TypeList.size(); j++) {
                    if (vaList.get(i).equals(varieInfo.TypeList.get(j).seqId)) {
                        SearchVarietyAdapter.getIsSelected().put(j, true);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

        gv_variety.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                varietyAll.setTextColor(getResources().getColor(R.color.text_normal_color));
                varietyStr = varieInfo.TypeList.get(position).seqId;
                TextView tv = (TextView) view.findViewById(R.id.tv_item_variety);
                if (SearchVarietyAdapter.getIsSelected().get(position) == false) {
                    tv.setSelected(true);
                    SearchVarietyAdapter.getIsSelected().put(position, true);
                    vaList.add(varietyStr);
                } else {
                    tv.setSelected(false);
                    SearchVarietyAdapter.getIsSelected().put(position, false);
                    vaList.remove(varietyStr);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        mSearchAdapter = new SearchAdapter(varieInfo.MaterialList, getActivity());
        gv_material.setAdapter(mSearchAdapter);
        if (maList.size() != 0) {
            materialAll.setTextColor(getResources().getColor(R.color.text_normal_color));
            for (int i = 0; i < maList.size(); i++) {
                for (int j = 0; j < varieInfo.MaterialList.size(); j++) {
                    if (maList.get(i).equals(varieInfo.MaterialList.get(j).seqId)) {
                        SearchAdapter.getIsSelected().put(j, true);
                        mSearchAdapter.notifyDataSetChanged();
                    }

                }
            }
        }
        gv_material.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                materialAll.setTextColor(getResources().getColor(R.color.text_normal_color));
                materialStr = varieInfo.MaterialList.get(position).seqId;
                TextView tv = (TextView) view.findViewById(R.id.tv_item_material1);
                if (SearchAdapter.getIsSelected().get(position) == false) {
                    tv.setSelected(true);
                    mSearchAdapter.getIsSelected().put(position, true);
                    maList.add(materialStr);
                } else {
                    tv.setSelected(false);
                    mSearchAdapter.getIsSelected().put(position, false);
                    maList.remove(materialStr);
                }
                mSearchAdapter.notifyDataSetChanged();
            }
        });
        varietyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaList.clear();
                varietyAll.setTextColor(getResources().getColor(R.color.themeColor));
                mAdapter = new SearchVarietyAdapter(varieInfo.TypeList, getActivity());
                gv_variety.setAdapter(mAdapter);
            }
        });
        materialAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maList.clear();
                materialAll.setTextColor(getResources().getColor(R.color.themeColor));
                mSearchAdapter = new SearchAdapter(varieInfo.MaterialList, getActivity());
                gv_material.setAdapter(mSearchAdapter);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonPopupWindow.dismiss();
                getSearchProduct();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaList.clear();
                maList.clear();
                varietyAll.setTextColor(getResources().getColor(R.color.themeColor));
                materialAll.setTextColor(getResources().getColor(R.color.themeColor));
                mSearchAdapter = new SearchAdapter(varieInfo.MaterialList, getActivity());
                gv_material.setAdapter(mSearchAdapter);
                mAdapter = new SearchVarietyAdapter(varieInfo.TypeList, getActivity());
                gv_variety.setAdapter(mAdapter);
            }
        });
    }

    /**
     * 根据条件进行查询
     */
    private String str1, str2;

    private void getSearchProduct() {
        adapter.clearList();
        if (vaList.size() != 0) {
            str1 = DateUtil.splitString(vaList);
        } else {
            str1 = "";
        }
        if (maList.size() != 0) {
            str2 = DateUtil.splitString(maList);
        } else {
            str2 = "";
        }
//        if (SystemUtil.isNetworkConnected()) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Form mForm = new Form();
//                    mForm.set("str1", str1);
//                    mForm.set("str2", str2);
//                    String result = HttpUtils.post(Contact.GETSEARCHERPRODUCT, mForm);
//                    OtherInfo otherInfo = GsonUtils.json2Bean(result, OtherInfo.class);
//                    handMessage(1, "otherInfo", otherInfo);
//                }
//            }).start();
//        } else {
//            ToastUtil.show(getString(R.string.interneterror));
//        }
        pageNum = 1;
        initRecyclerView(pageNum, str1, str2);

    }

    private void setLayoutParams(View mView) {
        int measuredWidth = SystemUtil.getWindowWidth(getActivity());
        ViewGroup.LayoutParams layoutParams = mView.getLayoutParams();
        layoutParams.width = measuredWidth;
        mView.setLayoutParams(layoutParams);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNum = 1;
        maList.clear();
        vaList.clear();
        str1 = null;
        str2 = null;

    }
}
