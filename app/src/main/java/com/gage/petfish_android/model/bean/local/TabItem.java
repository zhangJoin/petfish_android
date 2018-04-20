package com.gage.petfish_android.model.bean.local;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gage.petfish_android.R;


/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 */

public class TabItem {

    private Activity mContext;
    private int imageNormal;
    private int imageChecked;
    private int title;
    private String titleStr;
    public Class<? extends Fragment> fragmentClass;
    public View view;
    public ImageView mImageView;
    public TextView mTextView;

    public TabItem(Activity mContext, int imageNormal, int imageChecked, int title, Class<? extends Fragment> fragmentClass) {
        this.mContext = mContext;
        this.imageNormal = imageNormal;
        this.imageChecked = imageChecked;
        this.title = title;
        this.fragmentClass = fragmentClass;
    }

    public int getImageNormal() {
        return imageNormal;
    }

    public int getImageChecked() {
        return imageChecked;
    }

    public int getTitle() {
       return title;
    }

    public String getTitleStr() {
        if (title == 0) {
            return "";
        }
        if (TextUtils.isEmpty(titleStr)) {
            titleStr = mContext.getString(title);
        }
        return titleStr;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    public View getView() {
        if (this.view == null) {
            this.view = mContext.getLayoutInflater().inflate(R.layout.view_tab_main, null);
            this.mImageView = (ImageView) this.view.findViewById(R.id.iv_tab_image);
            this.mTextView = (TextView) this.view.findViewById(R.id.tv_tab_text);
            if (this.title == 0) {
                this.mTextView.setVisibility(View.GONE);
            } else {
                this.mTextView.setVisibility(View.VISIBLE);
                this.mTextView.setText(getTitleStr());
            }
            this.mImageView.setImageResource(imageNormal);
        }
        return this.view;
    }


    //切换tab的方法
    public void setChecked(boolean isChecked) {
        if (mImageView != null) {
            if (isChecked) {
                mImageView.setImageResource(imageChecked);
            } else {
                mImageView.setImageResource(imageNormal);
            }
        }
        if (mTextView != null && title != 0) {
            if (isChecked) {
                mTextView.setTextColor(0xff0076ca);
            } else {
                mTextView.setTextColor(0xff666666);
            }
        }
    }
}
