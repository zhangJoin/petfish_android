package com.gage.petfish_android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.HomeAdapter;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.base.contract.fragmentC.HomeContract;
import com.gage.petfish_android.component.rxbus.RxBus;
import com.gage.petfish_android.component.rxbus.RxCodeConstants;
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.model.bean.local.TabIndicator;
import com.gage.petfish_android.presenter.fragmentP.HomePresenter;
import com.gage.petfish_android.ui.BreedDetailActivity;
import com.gage.petfish_android.ui.CompanyDetailActivity;
import com.gage.petfish_android.ui.InfoDetailActivity;
import com.gage.petfish_android.ui.ShowDetailActivity;
import com.gage.petfish_android.ui.TreatDetailActivity;
import com.gage.petfish_android.util.HttpUtils;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ---日期----------维护人-----------
 * 2017/11/3       wangxiaoyu
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {


    //    @BindView(R.id.lv_show)
    ListView lvshow;
    //    @BindView(R.id.lv_information)
    ListView lvinformation;
    //    @BindView(lv_breed)
    ListView lvbreed;
    //    @BindView(R.id.lv_treat)
    ListView lvtreat;
    //    @BindView(R.id.lv_company)
    ListView lvcompany;
    //    @BindView(R.id.iv_show)
    ImageView iv_show;
    //    @BindView(R.id.iv_info)
    ImageView iv_info;
    //    @BindView(R.id.iv_breed)
    ImageView iv_breed;
    //    @BindView(R.id.iv_treat)
    ImageView iv_treat;
    //    @BindView(R.id.iv_company)
    ImageView iv_company;
    //    @BindView(R.id.ll_show)
    LinearLayout llshow;
    //    @BindView(R.id.ll_info)
    LinearLayout llinfo;
    //    @BindView(R.id.ll_breed)
    LinearLayout llbreed;
    //    @BindView(R.id.ll_treat)
    LinearLayout lltreat;
    //    @BindView(R.id.ll_company)
    LinearLayout llcompany;
    //    @BindView(R.id.scrollView)
    ScrollView scrollView;
    //    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<FishShow> list;
    private List<FishShow> listinfo, listinfo1, listinfo2, listinfo3, listinfo4;

    public final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            mSwipeRefreshLayout.setRefreshing(false);
            if (msg.what == 1) {
                listinfo = (List<FishShow>) msg.getData().getSerializable("list");
                if (listinfo.size() != 0) {
                    HomeAdapter adapter = new HomeAdapter(getActivity(), listinfo, "1");
                    lvshow.setAdapter(adapter);
                }
            } else if (msg.what == 2) {
                listinfo1 = (List<FishShow>) msg.getData().getSerializable("list");
                if (listinfo1.size() != 0) {
                    HomeAdapter adapter1 = new HomeAdapter(getActivity(), listinfo1, "2");
                    lvinformation.setAdapter(adapter1);
                }
            } else if (msg.what == 3) {
                listinfo2 = (List<FishShow>) msg.getData().getSerializable("list");
                if (listinfo2.size() != 0) {
                    HomeAdapter adapter2 = new HomeAdapter(getActivity(), listinfo2, "3");
                    lvbreed.setAdapter(adapter2);
                }

            } else if (msg.what == 4) {
                listinfo3 = (List<FishShow>) msg.getData().getSerializable("list");
                if (listinfo3.size() != 0) {
                    HomeAdapter adapter3 = new HomeAdapter(getActivity(), listinfo3, "4");
                    lvtreat.setAdapter(adapter3);
                }
            } else if (msg.what == 5) {
                listinfo4 = (List<FishShow>) msg.getData().getSerializable("list");
                if (listinfo4.size() != 0) {
                    HomeAdapter adapter4 = new HomeAdapter(getActivity(), listinfo4, "5");
                    lvcompany.setAdapter(adapter4);
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
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {
        initView();
        initDatas();
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initDatas();
//            }
//        });
        iv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int H = llshow.getTop();
                scrollView.smoothScrollTo(0, H);
            }
        });
        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int H = llinfo.getTop();
                scrollView.smoothScrollTo(0, H);
            }
        });
        iv_breed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int H = llbreed.getTop();
                scrollView.smoothScrollTo(0, H);
            }
        });
        iv_treat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int H = lltreat.getTop();
                scrollView.smoothScrollTo(0, H);
            }
        });
        iv_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int H = llcompany.getTop();
                scrollView.smoothScrollTo(0, H);
            }
        });

        llshow.setOnClickListener(new View.OnClickListener() {//展示
            @Override
            public void onClick(View v) {
                RxBus.getDefault().defaultPost(RxCodeConstants.MAIN_TAB_HOST, 4 == 1 ? 2 : 1);
                //改变FishFragment中的TableLayout
                if (1 != 4) {
                    TabIndicator.getInstance().flagTabIndicator = 1;
                    TabIndicator.getInstance().fishTabIndicator = 1;
                }

            }
        });
        llinfo.setOnClickListener(new View.OnClickListener() {//资讯
            @Override
            public void onClick(View v) {
                RxBus.getDefault().defaultPost(RxCodeConstants.MAIN_TAB_HOST, 4 == 0 ? 2 : 1);
                //改变FishFragment中的TableLayout
                if (0 != 4) {
                    TabIndicator.getInstance().flagTabIndicator = 1;
                    TabIndicator.getInstance().fishTabIndicator = 0;
                }

            }
        });
        llbreed.setOnClickListener(new View.OnClickListener() {//养殖
            @Override
            public void onClick(View v) {
                RxBus.getDefault().defaultPost(RxCodeConstants.MAIN_TAB_HOST, 4 == 2 ? 2 : 1);
                //改变FishFragment中的TableLayout
                if (2 != 4) {
                    TabIndicator.getInstance().flagTabIndicator = 1;
                    TabIndicator.getInstance().fishTabIndicator = 2;
                }

            }
        });
        lltreat.setOnClickListener(new View.OnClickListener() {//疾病预防
            @Override
            public void onClick(View v) {
                RxBus.getDefault().defaultPost(RxCodeConstants.MAIN_TAB_HOST, 4 == 3 ? 2 : 1);
                //改变FishFragment中的TableLayout
                if (3 != 4) {
                    TabIndicator.getInstance().flagTabIndicator = 1;
                    TabIndicator.getInstance().fishTabIndicator = 3;
                }

            }
        });
        llcompany.setOnClickListener(new View.OnClickListener() {//疾病预防
            @Override
            public void onClick(View v) {
                RxBus.getDefault().defaultPost(RxCodeConstants.MAIN_TAB_HOST, 4 == 4 ? 2 : 1);

            }
        });

        lvshow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ShowDetailActivity.class);
                intent.putExtra("seqId", listinfo.get(i).getSeqId());
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        });
        lvinformation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra("seqId", listinfo1.get(i).getSeqId());
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });
        lvbreed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), BreedDetailActivity.class);
                intent.putExtra("seqId", listinfo2.get(i).getSeqId());
                intent.putExtra("type", "3");
                startActivity(intent);
            }
        });
        lvtreat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TreatDetailActivity.class);
                intent.putExtra("seqId", listinfo3.get(i).getSeqId());
                intent.putExtra("type", "4");
                startActivity(intent);
            }
        });
        lvcompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), CompanyDetailActivity.class);
                intent.putExtra("seqId", listinfo4.get(i).getSeqId());
                intent.putExtra("type", "5");
                startActivity(intent);
            }
        });
    }

    private void initDatas() {
        if (SystemUtil.isNetworkConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form form = new Form();
                    form.set("type", "1");
                    form.set("pageNum", String.valueOf(0));
                    form.set("seqId", "");
                    try {
                        String result = HttpUtils.post(Contact.GETINFORNATION, form);
                        JSONObject jsonObject = new JSONObject(result);
                        jsonObject.getString("totalCount");
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
//                        JSONArray jsonArray = new JSONArray(result);
                        FishShow fishShow = null;
                        list = new ArrayList<FishShow>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            fishShow = new FishShow();
                            JSONObject object = jsonArray.getJSONObject(i);
                            fishShow.setName(object.getString("name"));
                            fishShow.setSeqId(object.getString("sqlId"));
                            fishShow.setContent(object.getString("habit"));
                            fishShow.setImageUrl(object.getString("sarverFileName"));
                            list.add(fishShow);
                        }
                        handMessage(1, "list", list);
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form form = new Form();
                    form.set("type", "2");
                    form.set("pageNum", String.valueOf(0));
                    try {
                        String result = HttpUtils.post(Contact.GETINFORNATION, form);
                        JSONObject jsonObject = new JSONObject(result);
                        jsonObject.getString("totalCount");
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        FishShow fishShow = null;
                        list = new ArrayList<FishShow>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            fishShow = new FishShow();
                            JSONObject object = jsonArray.getJSONObject(i);
                            fishShow.setName(object.getString("title"));
                            fishShow.setSeqId(object.getString("seqId"));
                            fishShow.setDate(object.getString("updatedate"));
                            fishShow.setImageUrl(object.getString("sarverFileName"));
                            list.add(fishShow);
                        }
                        handMessage(2, "list", list);
                    } catch (JSONException e) {
//                        e.printStackTrace();
                    }
                }
            }).start();
            //观赏鱼养殖 type=3
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form form = new Form();
                    form.set("type", "3");
                    form.set("pageNum", String.valueOf(0));
                    form.set("str1", "");
                    form.set("str2", "");
                    try {
                        String result = HttpUtils.post(Contact.GETINFORNATION, form);
                        JSONObject jsonObject = new JSONObject(result);
                        jsonObject.getString("totalCount");
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        FishShow fishShow = null;
                        list = new ArrayList<FishShow>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            fishShow = new FishShow();
                            JSONObject object = jsonArray.getJSONObject(i);
                            fishShow.setName(object.getString("title"));
                            fishShow.setSeqId(object.getString("seqId"));
                            fishShow.setDate(object.getString("updatedate"));
                            fishShow.setImageUrl(object.getString("sarverFileName"));
                            list.add(fishShow);
                        }
                        handMessage(3, "list", list);
                    } catch (JSONException e) {
//                        ToastUtil.shortShow(getString(R.string.interneterror));
                    }
                }
            }).start();
            //观赏鱼疾病预防 type=4
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form form = new Form();
                    form.set("type", "4");
                    form.set("pageNum", String.valueOf(0));
                    form.set("str1", "");
                    form.set("str2", "");
                    try {
                        String result = HttpUtils.post(Contact.GETINFORNATION, form);
                        JSONObject jsonObject = new JSONObject(result);
                        jsonObject.getString("totalCount");
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        FishShow fishShow = null;
                        list = new ArrayList<FishShow>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            fishShow = new FishShow();
                            JSONObject object = jsonArray.getJSONObject(i);
                            fishShow.setName(object.getString("title"));
                            fishShow.setSeqId(object.getString("seqId"));
                            fishShow.setDate(object.getString("updatedate"));
                            fishShow.setImageUrl(object.getString("sarverFileName"));
                            list.add(fishShow);
                        }
                        handMessage(4, "list", list);
                    } catch (JSONException e) {
//                        ToastUtil.shortShow(getString(R.string.interneterror));
                    }
                }
            }).start();
            //观赏鱼企业 type=5
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Form form = new Form();
                    form.set("type", "5");
                    form.set("pageNum", String.valueOf(0));
                    try {
                        String result = HttpUtils.post(Contact.GETINFORNATION, form);
                        JSONObject jsonObject = new JSONObject(result);
                        jsonObject.getString("totalCount");
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
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
                        handMessage(5, "list", list);
                    } catch (JSONException e) {
//                        ToastUtil.shortShow(getString(R.string.interneterror));
                    }
                }
            }).start();
        } else {
            ToastUtil.shortShow(getString(R.string.interneterror));
        }

    }

    private void initView() {
        lvshow = (ListView) mView.findViewById(R.id.lv_show);
        lvinformation = (ListView) mView.findViewById(R.id.lv_information);
        lvbreed = (ListView) mView.findViewById(R.id.lv_breed);
        lvtreat = (ListView) mView.findViewById(R.id.lv_treat);
        lvcompany = (ListView) mView.findViewById(R.id.lv_company);
        iv_show = (ImageView) mView.findViewById(R.id.iv_show);
        iv_info = (ImageView) mView.findViewById(R.id.iv_info);
        iv_breed = (ImageView) mView.findViewById(R.id.iv_breed);
        iv_treat = (ImageView) mView.findViewById(R.id.iv_treat);
        iv_company = (ImageView) mView.findViewById(R.id.iv_company);
        llshow = (LinearLayout) mView.findViewById(R.id.ll_show);
        llinfo = (LinearLayout) mView.findViewById(R.id.ll_info);
        llbreed = (LinearLayout) mView.findViewById(R.id.ll_breed);
        lltreat = (LinearLayout) mView.findViewById(R.id.ll_treat);
        llcompany = (LinearLayout) mView.findViewById(R.id.ll_company);
        scrollView = (ScrollView) mView.findViewById(R.id.scrollView);
//        mSwipeRefreshLayout= (SwipeRefreshLayout) mView.findViewById(R.id.swipeLayout);
    }

    private void handMessage(int i, String str, List<FishShow> listP) {
        Message message = new Message();
        message.what = i;
        Bundle bundle = new Bundle();
        bundle.putSerializable(str, (Serializable) listP);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
