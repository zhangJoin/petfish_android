package com.gage.petfish_android.util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * Created by zhanglonglong on 2017/11/17.
 */

public class FontUtil {
    /**
     *更改指定字体颜色
     * @param content
     * @param start 开始角标
     * @param end 结束角标
     * @param color
     */
    public static SpannableStringBuilder changeTextColor(String content,int start,int end,String color){
        SpannableStringBuilder style = new SpannableStringBuilder(content);
        style.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

}
