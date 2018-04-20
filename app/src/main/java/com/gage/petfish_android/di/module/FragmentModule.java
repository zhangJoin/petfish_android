package com.gage.petfish_android.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.gage.petfish_android.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    /**
     * @Scope用来标注依赖注入对象的适用范围。
     */
    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
