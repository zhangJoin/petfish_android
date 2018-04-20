package com.gage.petfish_android.di.component;

import android.app.Activity;

import com.gage.petfish_android.di.module.ActivityModule;
import com.gage.petfish_android.di.scope.ActivityScope;
import com.gage.petfish_android.ui.BreedDetailActivity;
import com.gage.petfish_android.ui.CompanyDetailActivity;
import com.gage.petfish_android.ui.InfoDetailActivity;
import com.gage.petfish_android.ui.OtherDetailActivity;
import com.gage.petfish_android.ui.ShowDetailActivity;
import com.gage.petfish_android.ui.TreatDetailActivity;
import com.gage.petfish_android.ui.WelcomeActivity;

import dagger.Component;


@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(WelcomeActivity welcomeActivity);

    void inject(ShowDetailActivity showDetailActivity);

    void inject(InfoDetailActivity infoDetailActivity);

    void inject(CompanyDetailActivity companyDetailActivity);

    void inject(BreedDetailActivity breedDetailActivity);

    void inject(TreatDetailActivity treatDetailActivity);

    void inject(OtherDetailActivity otherDetailActivity);
}
