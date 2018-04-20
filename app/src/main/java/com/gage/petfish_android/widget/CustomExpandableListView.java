package com.gage.petfish_android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ---日期----------维护人-----------
 * 2017/11/7       zuoyouming
 */

public class CustomExpandableListView extends ListView {
    public CustomExpandableListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomExpandableListView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
