package com.gage.petfish_android.ui;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.base.SimpleActivity;
import com.gage.petfish_android.component.rxbus.RxBus;
import com.gage.petfish_android.component.rxbus.RxBusBaseMessage;
import com.gage.petfish_android.component.rxbus.RxCodeConstants;
import com.gage.petfish_android.model.bean.local.TabItem;
import com.gage.petfish_android.model.bean.local.Title;
import com.gage.petfish_android.ui.fragment.EnterpriseFragment;
import com.gage.petfish_android.ui.fragment.FishFragment;
import com.gage.petfish_android.ui.fragment.HomeFragment;
import com.gage.petfish_android.ui.fragment.LineFragment;
import com.gage.petfish_android.ui.fragment.OtherFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends SimpleActivity {


//    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;
//    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<TabItem> mTableItemList;
    private FragmentTabHost fragmentTabHost;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        tvTitleToolbar= (TextView) findViewById(R.id.tv_title_toolbar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        initTabData();
        initTabHost();
        initListener();
    }

    private void initListener() {

        setToolBar(toolbar, tvTitleToolbar, "首页");

        RxBus.getDefault().toRxSchedulerFlowable(Title.class, new Consumer<Title>() {
            @Override
            public void accept(@NonNull Title title) throws Exception {
                if (!"".equals(title.content)) {
                    if (tvTitleToolbar != null) {
                        tvTitleToolbar.setText(title.content);
                    }

                }

            }
        });

        RxBus.getDefault().toDefaultFlowable(RxBusBaseMessage.class, RxCodeConstants.MAIN_TAB_HOST).subscribe(new Consumer<RxBusBaseMessage>() {
            @Override
            public void accept(@NonNull RxBusBaseMessage rxBusBaseMessage) throws Exception {
                fragmentTabHost.setCurrentTab((Integer) rxBusBaseMessage.getObject());
            }
        });

    }

    private void initTabData() {
        mTableItemList = new ArrayList<>();
        mTableItemList.add(new TabItem(this, R.mipmap.icon_home_g, R.mipmap.icon_home_b, R.string.text_main_home, HomeFragment.class));
        mTableItemList.add(new TabItem(this, R.mipmap.icon_gsy_g, R.mipmap.icon_gsy_b, R.string.text_main_fish, FishFragment.class));
        mTableItemList.add(new TabItem(this, R.mipmap.icon_qy_g, R.mipmap.icon_qy_b, R.string.text_main_enterprise, EnterpriseFragment.class));
        mTableItemList.add(new TabItem(this, R.mipmap.icon_zb_g, R.mipmap.icon_zb_b, R.string.text_main_other, OtherFragment.class));
        mTableItemList.add(new TabItem(this, R.mipmap.icon_dh_g, R.mipmap.icon_dh_b, R.string.text_main_line, LineFragment.class));

    }

    private void initTabHost() {
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < mTableItemList.size(); i++) {
            TabItem tabItem = mTableItemList.get(i);
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleStr()).setIndicator(tabItem.getView());
            fragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), null);

            //给Tab按钮设置背景
//            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);

            if (i == 0) {
                tabItem.setChecked(true);
            }
        }


        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //用于优化显示
//                String fish = getResources().getString(R.string.text_main_fish);
//                if (!fish.equals(tabId))
                if ("周边产品".equals(tabId)) {
                    tvTitleToolbar.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                } else {
//                    if (toolbar != null) {
                        toolbar.setVisibility(View.VISIBLE);
                        tvTitleToolbar.setVisibility(View.VISIBLE);
                        tvTitleToolbar.setText(tabId);
//                    }
                }

//                tvTitleToolbar.setText(tabId);

                for (int i = 0; i < mTableItemList.size(); i++) {
                    TabItem tabitem = mTableItemList.get(i);
                    if (tabId.equals(tabitem.getTitleStr())) {
                        tabitem.setChecked(true);
                    } else {
                        tabitem.setChecked(false);
                    }
                }
            }
        });


    }
}
