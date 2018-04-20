package com.gage.petfish_android.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * ---日期----------维护人-----------
 * 2017/9/25       zuoyouming
 */

public class ClockView extends View {
    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

    }
}
