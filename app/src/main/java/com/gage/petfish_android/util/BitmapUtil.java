package com.gage.petfish_android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import com.gage.petfish_android.app.App;

/**
 * ---日期----------维护人-----------
 * 2017/9/25       zuoyouming
 * 1.图片剪裁为圆形边框
 */

public class BitmapUtil {
    
//    public static BitmapUtil build(){
//        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        return this;
//    }

    public static Bitmap clipCircleBitmap(int drawableRes) {
        Bitmap bitmap = ((BitmapDrawable) App.getInstance().getResources().getDrawable(drawableRes)).getBitmap();

        return null;
    }

    public static Bitmap clipCircleBitmap(Bitmap mBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return null;
    }

}
