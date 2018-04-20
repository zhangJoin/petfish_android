package com.gage.petfish_android.model.bean.local;

/**
 * ---日期----------维护人-----------
 * 2017/9/14       zuoyouming
 */

public class TabIndicator {
    public int mainTabIndicator;
    public int fishTabIndicator;
    public int flagTabIndicator;
    private static TabIndicator tabIndicator;

    private TabIndicator() {
    }

    public static TabIndicator getInstance() {
        if (tabIndicator == null) {
            tabIndicator = new TabIndicator();
        }
        return tabIndicator;
    }


}
