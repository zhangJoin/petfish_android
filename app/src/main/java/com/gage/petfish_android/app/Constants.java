package com.gage.petfish_android.app;

import android.os.Environment;

import java.io.File;

/**
 * ---日期----------维护人-----------变更内容----------
 * 2017/6/5       zuoyouming         com.zunda.zozo.godcreator.app
 */

public class Constants {

    public static final String INFORMATION_TYPE="2";//资讯
    public static final String DISPLAY_TYPE="1";//展示
    public static final String BREED_TYPE="3";//养殖
    public static final String DISEASE_TYPE="4";//疾病预防
    public static final String ENTERPRISE_TYPE="5";//企业




    public static final String BUGLY_ID = "257700f3f8";

    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";

}
