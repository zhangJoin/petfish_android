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
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.BaseListPageAdapter;
import com.gage.petfish_android.adapter.BreedAdapter;
import com.gage.petfish_android.adapter.QualityAdapter;
import com.gage.petfish_android.adapter.QualityFishAdapter;
import com.gage.petfish_android.app.Constants;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.base.contract.fragmentC.DiseaseContract;
import com.gage.petfish_android.model.bean.BreedInfo;
import com.gage.petfish_android.model.bean.QualityInfo;
import com.gage.petfish_android.model.bean.local.TabIndicator;
import com.gage.petfish_android.presenter.fragmentP.DiseasePresenter;
import com.gage.petfish_android.ui.TreatDetailActivity;
import com.gage.petfish_android.ui.view.MyGridView;
import com.gage.petfish_android.util.DateUtil;
import com.gage.petfish_android.util.GsonUtils;
import com.gage.petfish_android.util.HttpUtils;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;
import com.gage.petfish_android.view.RefreshLoadListView;
import com.gage.petfish_android.widget.pop.BasePopupWindow;

import org.restlet.data.Form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 疾病预防页面
 * Created by zhanglonglong on 2017/11/8.
 */

public class DiseaseFragment extends BaseFragment<DiseasePresenter> implements DiseaseContract.View, AdapterView.OnItemClickListener, RefreshLoadListView.OnRefreshListener {
    @BindView(R.id.tv_disease_show)
    TextView tvDiseaseShow;
    @BindView(R.id.ll_disease)
    LinearLayout llDisease;
    @BindView(R.id.rv_disease)
    RefreshLoadListView rvDisease;
    @BindView(R.id.iv_disease)
    ImageView ivDisease;
    @BindView(R.id.tv_show_disease)
    TextView tvShowDisease;
    private BreedAdapter adapter;
    private int pageNum = 1;
    private int totalPageNum;
    private QualityInfo qualityInfo;
    private BreedInfo breedInfo;
    public static BasePopupWindow commonPopupWindow;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                breedInfo = (BreedInfo) msg.getData().getSerializable("breedInfo");
                if (breedInfo.list != null) {
                    if (breedInfo.list.size() > 0) {
                        if (rvDisease != null) {
                            rvDisease.setVisibility(View.VISIBLE);
                            tvShowDisease.setVisibility(View.GONE);
                        }
                    } else {
                        rvDisease.setVisibility(View.GONE);
                        tvShowDisease.setVisibility(View.VISIBLE);
                    }
                }
                int totalCount = breedInfo.totalCount;
                if ((totalCount % 10) == 0) {
                    totalPageNum = totalCount / 10;
                } else {
                    totalPageNum = (totalCount / 10) + 1;
                }
                if (adapter.getPage() == 1) {
                    adapter.clearList();
                }
                List<BreedInfo.BreedBean> list = breedInfo.list;
                adapter.addList(list);
                adapter.setTotalPage(totalPageNum);
                if (adapter.getPage() == adapter.getTotalPage() || totalPageNum == 0) {
                    rvDisease.onLoadCompile();
                } else {
                    rvDisease.onRefreshCompile();
                }
            } else if (msg.what == 2) {
                qualityInfo = (QualityInfo) msg.getData().getSerializable("qualityInfo");
                popSelectMenu(qualityInfo);
            }
        }
    };

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_disease;
    }

    @Override
    protected void initEventAndData() {
        adapter = new BreedAdapter(getActivity());
        rvDisease.setAdapter(adapter);
        rvDisease.setOnItemClickListener(this);
        rvDisease.setOnRefreshListener(this);
        if (vaNameList.size() != 0) {
            tvDiseaseShow.setText(nameList.size() == 0 ? DateUtil.splitString(vaNameList) : (DateUtil.splitString(vaNameList) + "," + DateUtil.splitString(nameList)));
        } else {
            tvDiseaseShow.setText(nameList.size() == 0 ? "全部" : DateUtil.splitString(nameList));
        }
        initRecyclerView(pageNum, str1, str2);
    }

    private void initRecyclerView(final int page, final String str1, final String str2) {
        if (SystemUtil.isNetworkConnected()) {
            adapter.setPage(page);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form mForm = new Form();
                    mForm.set("type", Constants.DISEASE_TYPE);
                    mForm.set("pageNum", String.valueOf(page));
                    mForm.set("str1", str1);
                    mForm.set("str2", str2);
                    String result = HttpUtils.post(Contact.GETINFORNATION, mForm);
                    BreedInfo breedInfo = GsonUtils.json2Bean(result, BreedInfo.class);
                    Message message = new Message();
                    message.what = 1;
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("breedInfo", (Serializable) breedInfo);
                    message.setData(mBundle);
                    mHandler.sendMessage(message);
                }
            }).start();
        } else {
            ToastUtil.show(getString(R.string.interneterror));
        }
    }

    @OnClick(R.id.ll_disease)
    public void onClick(View mView) {
        if (SystemUtil.isNetworkConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = HttpUtils.post(Contact.SEARCHPZY, null);
                    QualityInfo qualityInfo = GsonUtils.json2Bean(result, QualityInfo.class);
                    handMessage(2, "qualityInfo", qualityInfo);
                }
            }).start();
        } else {
            ToastUtil.show(getString(R.string.interneterror));
        }
    }

    private String varietyStr, nameStr;
    private QualityAdapter mAdapter;
    private QualityFishAdapter mFishAdapter;
    private List<String> vaList = new ArrayList<>();//用于存储已选的品种分类
    private List<String> naList = new ArrayList<>();//用于存储已选的鱼名称
    private List<String> nameList = new ArrayList<>();//用于存储已选的名称显示
    private List<String> vaNameList = new ArrayList<>();//用于存储已选的名称显示

    /**
     * 展示养殖显示选择
     */
    private void popSelectMenu(final QualityInfo qualityInfo) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.view_pop_gridview, null);
        final MyGridView gv_material = (MyGridView) mView.findViewById(R.id.gv_material);
        final MyGridView gv_variety = (MyGridView) mView.findViewById(R.id.gv_variety);
        TextView tvPop1 = (TextView) mView.findViewById(R.id.tv_pop1);
        TextView tvPop2 = (TextView) mView.findViewById(R.id.tv_pop2);
        tvPop1.setText("品种分类");
        tvPop2.setText("观赏鱼名称");
        LinearLayout ll_pop_material = (LinearLayout) mView.findViewById(R.id.ll_pop_material);
        LinearLayout ll_pop_variety = (LinearLayout) mView.findViewById(R.id.ll_pop_variety);
        final TextView varietyAll = (TextView) mView.findViewById(R.id.tv_pop_variety_all);
        final TextView materialAll = (TextView) mView.findViewById(R.id.tv_pop_material_all);
        Button btnSearch = (Button) mView.findViewById(R.id.btn_search);
        Button btnReset = (Button) mView.findViewById(R.id.btn_reset);
        setLayoutParams(ll_pop_variety);
        setLayoutParams(ll_pop_material);
        if(commonPopupWindow!=null&&commonPopupWindow.isShowing()){
        }else{
            commonPopupWindow = new BasePopupWindow(getActivity());
        }
        commonPopupWindow
                .inflate(mView)
                .setLayoutParams(BasePopupWindow.MATCH_PARENT,
                        SystemUtil.getWindowHeight(getActivity()) - 50 - ivDisease.getWidth() * 3)
                .showPopAsDown(llDisease, Gravity.LEFT);
        mAdapter = new QualityAdapter(qualityInfo.TypeList, getContext());
        gv_variety.setAdapter(mAdapter);
        if (vaList.size() != 0) {
            varietyAll.setTextColor(getResources().getColor(R.color.text_normal_color));
            for (int i = 0; i < vaList.size(); i++) {
                for (int j = 0; j < qualityInfo.TypeList.size(); j++) {
                    if (vaList.get(i).equals(qualityInfo.TypeList.get(j).seqId)) {
                        QualityAdapter.getIsSelected().put(j, true);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

        gv_variety.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                varietyAll.setTextColor(getResources().getColor(R.color.text_normal_color));
                varietyStr = qualityInfo.TypeList.get(position).seqId;
                String name = qualityInfo.TypeList.get(position).name;
                TextView tv = (TextView) view.findViewById(R.id.tv_item_variety);
                if (QualityAdapter.getIsSelected().get(position) == false) {
                    tv.setSelected(true);
                    QualityAdapter.getIsSelected().put(position, true);
                    vaList.add(varietyStr);
                    vaNameList.add(name);
                } else {
                    tv.setSelected(false);
                    QualityAdapter.getIsSelected().put(position, false);
                    vaList.remove(varietyStr);
                    vaNameList.remove(name);

                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mFishAdapter = new QualityFishAdapter(qualityInfo.NameList, getContext());
        gv_material.setAdapter(mFishAdapter);
        if (naList.size() != 0) {
            materialAll.setTextColor(getResources().getColor(R.color.text_normal_color));
            for (int i = 0; i < naList.size(); i++) {
                for (int j = 0; j < qualityInfo.NameList.size(); j++) {
                    if (naList.get(i).equals(qualityInfo.NameList.get(j).seqId)) {
                        QualityFishAdapter.getIsSelected().put(j, true);
                        mFishAdapter.notifyDataSetChanged();
                    }

                }
            }
        }
        gv_material.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                materialAll.setTextColor(getResources().getColor(R.color.text_normal_color));
                nameStr = qualityInfo.NameList.get(position).seqId;
                String name = qualityInfo.NameList.get(position).name;
                TextView tv = (TextView) view.findViewById(R.id.tv_item_material1);
                if (QualityFishAdapter.getIsSelected().get(position) == false) {
                    tv.setSelected(true);
                    mFishAdapter.getIsSelected().put(position, true);
                    naList.add(nameStr);
                    nameList.add(name);
                } else {
                    tv.setSelected(false);
                    mFishAdapter.getIsSelected().put(position, false);
                    naList.remove(nameStr);
                    nameList.remove(name);
                }
                mFishAdapter.notifyDataSetChanged();
            }
        });
        varietyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaList.clear();
                vaNameList.clear();
                varietyAll.setTextColor(getResources().getColor(R.color.themeColor));
                mAdapter = new QualityAdapter(qualityInfo.TypeList, getActivity());
                gv_variety.setAdapter(mAdapter);
            }
        });
        materialAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naList.clear();
                nameList.clear();
                materialAll.setTextColor(getResources().getColor(R.color.themeColor));
                mFishAdapter = new QualityFishAdapter(qualityInfo.NameList, getActivity());
                gv_material.setAdapter(mFishAdapter);
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
                naList.clear();
                nameList.clear();
                vaNameList.clear();
                varietyAll.setTextColor(getResources().getColor(R.color.themeColor));
                materialAll.setTextColor(getResources().getColor(R.color.themeColor));
                mFishAdapter = new QualityFishAdapter(qualityInfo.NameList, getActivity());
                gv_material.setAdapter(mFishAdapter);
                mAdapter = new QualityAdapter(qualityInfo.TypeList, getActivity());
                gv_variety.setAdapter(mAdapter);
            }
        });
    }

    /**
     * 根据条件进行查询
     */
    private String str1, str2;

    private void getSearchProduct() {
        if (vaList.size() != 0) {
            str1 = DateUtil.splitString(vaList);
        } else {
            str1 = "";
        }
        if (naList.size() != 0) {
            str2 = DateUtil.splitString(naList);
        } else {
            str2 = "";
        }
        if (vaNameList.size() != 0) {
            tvDiseaseShow.setText(nameList.size() == 0 ? DateUtil.splitString(vaNameList) : (DateUtil.splitString(vaNameList) + "," + DateUtil.splitString(nameList)));
        } else {
            tvDiseaseShow.setText(nameList.size() == 0 ? "全部" : DateUtil.splitString(nameList));
        }
//        if (SystemUtil.isNetworkConnected()) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Form mForm = new Form();
//                    mForm.set("flag", "03");
//                    mForm.set("str1", str1);
//                    mForm.set("str2", str2);
//                    String result = HttpUtils.post(Contact.GETSEARCHLIST, mForm);
//                    BreedInfo breedInfo = GsonUtils.json2Bean(result, BreedInfo.class);
//                    handMessage(1, "breedInfo", breedInfo);
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

    private void handMessage(int flag, String str, Object obj) {
        Message message = new Message();
        message.what = flag;
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(str, (Serializable) obj);
        message.setData(mBundle);
        mHandler.sendMessage(message);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == adapter.getList().size() + 1) {
//			Toast.makeText(NewsActivity.this, "已经在最底了", Toast.LENGTH_LONG).show();
        } else {
            TabIndicator.getInstance().fishTabIndicator = 3;
            Intent intent = new Intent(getActivity(), TreatDetailActivity.class);
            intent.putExtra("seqId", adapter.getItem(position - 1).seqId);
            intent.putExtra("type", "4");
            startActivity(intent);
        }
    }

    @Override
    public void onDownPullRefresh(RefreshLoadListView v, BaseListPageAdapter adapter) {
//        if (adapter.getTotalPage() == totalPageNum) {
//            List<BreedInfo.BreedBean> list = adapter.getList();
//            adapter.setList(list);
//            rvDisease.onRefreshCompile();
//        }
        pageNum=1;
        initRecyclerView(pageNum,str1,str2);
    }

    @Override
    public void onLoadingMore(RefreshLoadListView v, BaseListPageAdapter adapter) {
        pageNum++;
        if (pageNum <= totalPageNum) {
            initRecyclerView(pageNum, str1, str2);
        } else {
            pageNum=1;
            List<BreedInfo.BreedBean> list = adapter.getList();
            adapter.setList(list);
            rvDisease.onLoadCompile();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNum=1;
    }

}

