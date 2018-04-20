package com.gage.petfish_android.widget.pop;

import android.app.Activity;
import android.content.Context;

import com.gage.petfish_android.R;


/**
 * ---日期----------维护人-----------
 * 2017/9/7       zuoyouming
 */

public class PopLoadingWindow extends BasePopupWindow {
    private static PopLoadingWindow popLoadingWindow;

    public PopLoadingWindow(Context mContext) {
        super(mContext);
    }

    public static PopLoadingWindow instance(Activity context) {
        popLoadingWindow = new PopLoadingWindow(context);
        popLoadingWindow.inflate(R.layout.view_loading_pop).setImg(R.id.loding_ImageView, R.drawable.loading);

        return popLoadingWindow;
    }

    public static void dismissPop() {
        if (popLoadingWindow != null && popLoadingWindow.isShowing()) {
            popLoadingWindow.dismiss();
        }
    }

}
