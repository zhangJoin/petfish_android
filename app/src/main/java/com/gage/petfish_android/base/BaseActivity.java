package com.gage.petfish_android.base;

import com.gage.petfish_android.app.App;
import com.gage.petfish_android.di.component.ActivityComponent;
import com.gage.petfish_android.di.component.DaggerActivityComponent;
import com.gage.petfish_android.di.module.ActivityModule;

import javax.inject.Inject;

/**
 * MVP activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {

    @Inject
    protected T mPresenter;

    protected ActivityComponent getActivityComponent(){
        return  DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }

    protected abstract void initInject();

}