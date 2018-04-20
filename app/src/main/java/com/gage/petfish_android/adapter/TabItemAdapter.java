package com.gage.petfish_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ---日期----------维护人-----------
 * 2017/9/13       zuoyouming
 */
public class TabItemAdapter extends FragmentPagerAdapter {
    private static final java.lang.String TAB_TAG = "@fish@";
    private List<String> tabTitles;
//    InformationFragment informationFragment;
//    DisplayFragment displayFragment;
//    BreedFragment breedFragment;
//    DiseaseFragment diseaseFragment;
    private List<Fragment>list;
    public TabItemAdapter(FragmentManager fm, List<String> tabTitles,List<Fragment>list) {
        super(fm);
        this.tabTitles = tabTitles;
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
//        String[] title = tabTitles.get(position).split(TAB_TAG);
//        if (title[0].equals("资讯")) {
//            if(informationFragment==null){
//                informationFragment = new InformationFragment();
//            }
////            mFragment.setType(Integer.parseInt(title[1]));
////            mFragment.setTitle(title[0]);
//            return informationFragment;
//
//        } else if (title[0].equals("展示")) {
//            if(displayFragment==null){
//                displayFragment = new DisplayFragment();
//            }
//
////            mFragment.setType(Integer.parseInt(title[1]));
////            mFragment.setTitle(title[0]);
//            return displayFragment;
//
//        } else if (title[0].equals("养殖")) {
//            if(breedFragment==null){
//                breedFragment= new BreedFragment();
//            }
////            mFragment.setType(Integer.parseInt(title[1]));
////            mFragment.setTitle(title[0]);
//            return breedFragment;
//        }else if(title[0].equals("疾病预防")){
//            if(diseaseFragment==null){
//                diseaseFragment = new DiseaseFragment();
//            }
//
////            mFragment.setType(Integer.parseInt(title[1]));
////            mFragment.setTitle(title[0]);
//            return diseaseFragment;
//        }
//        return null;
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position).split(TAB_TAG)[0];
    }
}
