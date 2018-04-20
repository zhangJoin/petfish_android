package com.gage.petfish_android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gage.petfish_android.R;
import com.gage.petfish_android.base.SimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhanglonglong on 2017/11/7.
 */

public class WelcomeActivity extends SimpleActivity {
    @BindView(R.id.btn_welcome)
    Button btnWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.btn_welcome)
    public void onClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

}
