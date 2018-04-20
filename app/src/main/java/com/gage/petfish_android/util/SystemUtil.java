package com.gage.petfish_android.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.gage.petfish_android.app.App;
import com.gage.petfish_android.app.Constants;
import com.gage.petfish_android.model.bean.VersionBean4Local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by codeest on 2016/8/4.
 */
public class SystemUtil {


    /**
     * 检查WIFI是否连接
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo != null;
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     */
    public static boolean isMobileNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileNetworkInfo != null;
    }

    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        return connectivityManager.getActiveNetworkInfo() != null;
        try {
            ConnectivityManager manger = (ConnectivityManager) App.getInstance().getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manger.getActiveNetworkInfo();
            // return (info!=null && info.isConnected());//
            if (info != null) {
                return info.isConnected();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 保存文字到剪贴板
     *
     * @param context
     * @param text
     */
    public static void copyToClipBoard(Context context, String text) {
        ClipData clipData = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            clipData = ClipData.newPlainText("url", text);
            ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            manager.setPrimaryClip(clipData);
            ToastUtil.shortShow("已复制到剪贴板");
        }
    }

    /**
     * 保存图片到本地
     *
     * @param context
     * @param url
     * @param bitmap
     */
    public static Uri saveBitmapToFile(Context context, String url, Bitmap bitmap, View container, boolean isShare) {
        String fileName = url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")) + ".png";
        File fileDir = new File(Constants.PATH_SDCARD);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File imageFile = new File(fileDir, fileName);
        Uri uri = Uri.fromFile(imageFile);
        if (isShare && imageFile.exists()) {
            return uri;
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            boolean isCompress = bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            if (isCompress) {
                SnackbarUtil.showShort(container, "保存妹纸成功n(*≧▽≦*)n");
            } else {
                SnackbarUtil.showShort(container, "保存妹纸失败ヽ(≧Д≦)ノ");
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            SnackbarUtil.showShort(container, "保存妹纸失败ヽ(≧Д≦)ノ");
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return uri;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dp2px(float dpValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static void getDeviceInfo() {

        String model = Build.MODEL; //手机型号 设备名称
        String brand = Build.BRAND;   //设备品牌
        String device = Build.DEVICE; //设备驱动名称
        String fingerprint = Build.FINGERPRINT; //设备的唯一标识
        String id = Build.ID;     //设备版本号
        String product = Build.PRODUCT; //整个产品的名称
        String user = Build.USER;  //设备用户名 基本上都是 android-build
        String manufacturer = Build.MANUFACTURER;  //设备制造商
        int sdkInt = Build.VERSION.SDK_INT;  //系统的API级别数字显示--主要用于判断Android 系统

//        System.out.println("==============="+"model"+model+"brand"+brand+"device"+device+"fingerprint"+fingerprint+"id"+id+"product"+product+"user"+user+"manufacturer"+manufacturer+"sdkInt"+sdkInt);

        // model            MI NOTE LTE
        // brand            Xiaomi
        // device           virgo
        // fingerprint      Xiaomi/virgo/virgo:6.0.1/MMB29M/7.5.25:user/release-keys
        // id               MMB29M
        // product          virgo
        // user             builder
        // manufacturer     Xiaomi
        // sdkInt           23
    }

//    public static String getDeviceId() {
//
//        TelephonyManager TelephonyMgr = (TelephonyManager) App.getInstance().getSystemService(TELEPHONY_SERVICE);
//        return TelephonyMgr.getDeviceId();
//
//    }

    public static VersionBean4Local getVersionInfo() {
        VersionBean4Local versionBean = null;

        try {
            PackageManager pm = App.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(App.getInstance().getPackageName(), PackageManager.GET_ACTIVITIES);
            int versionCode = pi.versionCode;
            String versionName = pi.versionName;

            versionBean = new VersionBean4Local();
            versionBean.versionName = versionName;
            versionBean.versionCode = versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionBean;
    }

    /**
     * 获取屏幕的高
     *
     * @param c
     * @return
     */
    public static int getWindowHeight(Context c) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenHeigh = dm.heightPixels;
        return screenHeigh;

    }

    /**
     * 获取屏幕的宽
     *
     * @param c
     * @return
     */
    public static int getWindowWidth(Context c) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;

    }
}
