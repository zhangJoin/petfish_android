package com.gage.petfish_android.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.gage.petfish_android.app.App;


/**
 * ---日期----------维护人-----------变更内容----------
 * 2017/2/12       zozo          zozo
 */

public class SPUtil {


    private static final String SP_NAME = "applySchool";


    private static SharedPreferences getSharedPreferences() {
        return App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }


    public static int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    public static void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public static long getLong(String key, long defValue) {
        return getSharedPreferences().getLong(key, defValue);
    }

    public static void putLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).apply();
    }

    public static float getFloat(String key, float defValue) {
        return getSharedPreferences().getFloat(key, defValue);
    }

    public static void putFloat(String key, float value) {
        getSharedPreferences().edit().putFloat(key, value).apply();
    }


    //================内容存储查询===========================

//    1.teacherNumber           教师uid
//    2.password                密码
//    3.phoneId                 设备唯一id
//    4.versionCode4Server      服务器版本号

}
