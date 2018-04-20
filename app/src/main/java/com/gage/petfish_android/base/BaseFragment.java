package com.gage.petfish_android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gage.petfish_android.app.App;
import com.gage.petfish_android.di.component.DaggerFragmentComponent;
import com.gage.petfish_android.di.component.FragmentComponent;
import com.gage.petfish_android.di.module.FragmentModule;

import javax.inject.Inject;

/**
 * MVP Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView {

    @Inject
    protected T mPresenter;

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroyView();
    }

    protected abstract void initInject();
}