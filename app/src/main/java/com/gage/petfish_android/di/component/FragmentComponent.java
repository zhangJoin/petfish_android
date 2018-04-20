package com.gage.petfish_android.di.component;

import android.app.Activity;

import com.gage.petfish_android.di.module.FragmentModule;
import com.gage.petfish_android.di.scope.FragmentScope;
import com.gage.petfish_android.ui.fragment.BreedFragment;
import com.gage.petfish_android.ui.fragment.DiseaseFragment;
import com.gage.petfish_android.ui.fragment.DisplayFragment;
import com.gage.petfish_android.ui.fragment.EnterpriseFragment;
import com.gage.petfish_android.ui.fragment.FishFragment;
import com.gage.petfish_android.ui.fragment.HomeFragment;
import com.gage.petfish_android.ui.fragment.InfoFragment;
import com.gage.petfish_android.ui.fragment.InformationFragment;
import com.gage.petfish_android.ui.fragment.LineFragment;
import com.gage.petfish_android.ui.fragment.OtherFragment;

import dagger.Component;


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();


    void inject(FishFragment fishFragment);

    void inject(InfoFragment infoFragment);

    void inject(HomeFragment homeFragment);

    void inject(EnterpriseFragment enterpriseFragment);

    void inject(OtherFragment otherFragment);

    void inject(LineFragment lineFragment);

    void inject(InformationFragment informationFragment);

    void inject(BreedFragment breedFragment);

    void inject(DiseaseFragment diseaseFragment);

    void inject(DisplayFragment displayFragment);


}
