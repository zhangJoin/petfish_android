package com.gage.petfish_android.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.TabItemAdapter;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.base.contract.fragmentC.FishContract;
import com.gage.petfish_android.component.rxbus.RxBus;
import com.gage.petfish_android.model.bean.local.TabIndicator;
import com.gage.petfish_android.model.bean.local.Title;
import com.gage.petfish_android.presenter.fragmentP.FishPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * ---日期----------维护人-----------
 * 2017/11/3       zhanglonglong
 */

public class FishFragment extends BaseFragment<FishPresenter> implements FishContract.View {


    @BindView(R.id.tl_home)
    TabLayout tlHome;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    TabItemAdapter adapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new InformationFragment());
        add(new DisplayFragment());
        add(new BreedFragment());
        add(new DiseaseFragment());
    }};

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fish;
    }

    Title title;

    @Override
    protected void initEventAndData() {

        String[] tabItems = getResources().getStringArray(R.array.tab_item_home);
        List<String> tabTitles = Arrays.asList(tabItems);
        adapter = new TabItemAdapter(getChildFragmentManager(), tabTitles, fragmentList);
        vpHome.setAdapter(adapter);//给ViewPager设置适配器
        tlHome.setupWithViewPager(vpHome, true);//将TabLayout和ViewPager关联起来
        tlHome.setTabsFromPagerAdapter(adapter);
        tlHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title = new Title(tab.getText().toString());
                RxBus.getDefault().post(title);//用于切换main页面中toolbar标题显示
                if (TabIndicator.getInstance().flagTabIndicator != 1) {
                    if (title.content.equals("展示")) {
                        TabIndicator.getInstance().fishTabIndicator = 1;
                        TabIndicator.getInstance().flagTabIndicator = 2;
                    } else if (title.content.equals("养殖")) {
                        TabIndicator.getInstance().fishTabIndicator = 2;
                        TabIndicator.getInstance().flagTabIndicator = 2;
                    } else if (title.content.equals("疾病预防")) {
                        TabIndicator.getInstance().fishTabIndicator = 3;
                        TabIndicator.getInstance().flagTabIndicator = 2;
                    } else if (title.content.equals("资讯")) {
                        TabIndicator.getInstance().fishTabIndicator = 0;
                        TabIndicator.getInstance().flagTabIndicator = 2;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//        RxBus.getDefault().toDefaultFlowable(RxBusBaseMessage.class, RxCodeConstants.FISH_TAB_LAYOUT).subscribe(new Consumer<RxBusBaseMessage>() {
//            @Override
//            public void accept(@NonNull RxBusBaseMessage rxBusBaseMessage) throws Exception {
//                tlHome.getTabAt((Integer) rxBusBaseMessage.getObject()).select();
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tlHome.getTabAt(TabIndicator.getInstance().fishTabIndicator).select();//用于切换底部显示发送监听
        if (DisplayFragment.commonPopupWindow != null) {
            if (DisplayFragment.commonPopupWindow.isShowing()) {
                DisplayFragment.commonPopupWindow.dismiss();
            }
        }
        if (BreedFragment.commonPopupWindow != null) {
            if (BreedFragment.commonPopupWindow.isShowing()) {
                BreedFragment.commonPopupWindow.dismiss();
            }
        }
        if (DiseaseFragment.commonPopupWindow != null) {
            if (DiseaseFragment.commonPopupWindow.isShowing()) {
                DiseaseFragment.commonPopupWindow.dismiss();
            }
        }
    }

}
