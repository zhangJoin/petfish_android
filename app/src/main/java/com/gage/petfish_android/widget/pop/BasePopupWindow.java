package com.gage.petfish_android.widget.pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gage.petfish_android.app.App;

/**
 * ---日期----------维护人-----------变更内容----------
 * 2017/6/21       zuoyouming         com.gage.applyschool.widget
 */

public class BasePopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    private boolean isShowing;

    private Context mContext;
    public View popView;

    public BasePopupWindow(Context mContext) {
        this.mContext = mContext;
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true); //true:触摸popupwindow以外界面,消失
        this.setBackgroundDrawable(new ColorDrawable());
        this.setLayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    }


    /**
     * 输入布局Id 填充布局
     *
     * @param layoutId
     * @return
     */
    public BasePopupWindow inflate(int layoutId) {
        if (mContext != null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            popView = inflater.inflate(layoutId, null);
            this.setContentView(popView);
        } else {
            Throwable throwable = new Throwable("===========BasePopupWindow.java:mContext-不能为空=============");
            throwable.printStackTrace();
        }
        return this;
    }

    public BasePopupWindow inflate(View view) {
        popView = view;
        this.setContentView(view);
        return this;
    }

    /**
     * 封装findViewById
     *
     * @param viewId 控件Id
     * @param <T>
     * @return
     */
    private <T extends View> T getView(int viewId) {
        T tView = null;
        if (popView != null) {
            tView = (T) popView.findViewById(viewId);
        } else {
            Throwable throwable = new Throwable("============BasePopupWindow.java:popView-不能为空============");
            throwable.printStackTrace();
        }
        return tView;
    }

    /**
     * 填充内容
     *
     * @param viewId 控件Id
     * @param strId  字符串Id
     * @return
     */
    public BasePopupWindow setText(int viewId, int strId) {
        String str = App.getInstance().getString(strId);
        return setText(viewId, str);
    }

    public BasePopupWindow setText(int viewId, CharSequence str) {
        TextView mTextView = getView(viewId);
        if (mTextView != null) {
            mTextView.setText(str);
        } else {
            Throwable throwable = new Throwable("=============BasePopupWindow.java:查找不到相应控件===========");
            throwable.printStackTrace();
        }
        return this;
    }

    //    public BasePopupWindow setImg(int viewId, int imgId) {
//        ImageView mImageView = getView(viewId);
//        mImageView.setImageResource(imgId);
//        return this;
//    }
    public BasePopupWindow setImg(int viewId, int imgId) {
        ImageView mImageView = getView(viewId);
        Glide.with(mContext).load(imgId).into(mImageView);
        return this;
    }

    /**
     * 设置监听
     *
     * @param viewId
     * @param onPopClickListener
     * @return
     */
    public BasePopupWindow setViewClickListener(int viewId, final OnPopClickListener onPopClickListener) {
        View mView = getView(viewId);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPopClickListener == null)
                    return;
                onPopClickListener.onPopClick();
                BasePopupWindow.this.dismiss();
            }
        });
        return this;
    }

    public interface OnPopClickListener {
        void onPopClick();
    }

    /**
     * 设置pop的填充方式-1.WRAP_CONTENT  2.MATCH_PARENT
     *
     * @param widthParam
     * @param heightParam
     */
    public BasePopupWindow setLayoutParams(int widthParam, int heightParam) {
        this.setWidth(widthParam);
        this.setHeight(heightParam);
        return this;
    }


    /**
     * 解决部分机型(华为),showAsDropDown()方法失效的问题
     *
     * @param mView
     */
    public void showAsDropDownDebug(View mView) {

        int[] locations = new int[2];
        mView.getLocationOnScreen(locations);
        DisplayMetrics displayMetrics = mContext.getApplicationContext()
                .getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        int height = screenHeight - mView.getHeight() - locations[1];
        this.setHeight(height);
        this.showAsDropDown(mView);
    }

    /**
     * popupwindow隐藏后调用此方法
     */
    @Override
    public void onDismiss() {
        mContext = null;
    }
    /**
     * pop在界面上居中显示
     *
     * @param v
     */
    @Deprecated
    public void showPop(View v) {
        if (!isShowing) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                this.showAtLocation(v, Gravity.CENTER, 0, 0);
            } else {
                this.showAsDropDown(v);
            }
            isShowing = true;
        } else {
            this.dismiss();
            isShowing = false;
        }
    }

    /**
     * 显示指定View的下面
     *
     * @param v
     */
    @SuppressLint("NewApi")
    @Deprecated
    public void showPopAsDown(View v) {
        if (!isShowing) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                this.showAsDropDown(v, 0, 0, Gravity.CENTER);
            } else {
                this.showAsDropDown(v);
            }
            isShowing = true;
        } else {
            this.dismiss();
            isShowing = false;
        }
    }

    /**
     * 显示在指定控件下方
     * @param v
     * @param gravity : Gravity.CENTER ...
     */
    @SuppressLint("NewApi")
    @Deprecated
    public void showPopAsDown(View v,int gravity) {
        if (!isShowing) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                this.showAsDropDown(v, 0, 0, gravity);
            } else {
                this.showAsDropDown(v);
            }
            isShowing = true;
        } else {
            this.dismiss();
            isShowing = false;
        }
    }

    /**
     * 显示指定ParentView的相对位置
     *
     * @param parentView
     * @param gravity
     */
    @Deprecated
    public void showPopAsLocation(View parentView, int gravity) {
        if (!isShowing) {
            this.showAtLocation(parentView, gravity,0,0);
            isShowing = true;
        } else {
            this.dismiss();
            isShowing = false;
        }
    }

    /**
     * 显示指定ParentView的相对位置
     *
     * @param parentView
     * @param gravity
     * @param x
     * @param y
     */
    @Deprecated
    public void showPopAsLocation(View parentView, int gravity, int x, int y) {
        if (!isShowing) {
            this.showAtLocation(parentView, gravity, x, y);
            isShowing = true;
        } else {
            this.dismiss();
            isShowing = false;
        }
    }


}
