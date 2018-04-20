package com.gage.petfish_android.di.component;


import com.gage.petfish_android.app.App;
import com.gage.petfish_android.di.module.AppModule;
import com.gage.petfish_android.model.http.RestletHelper;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

//    RetrofitHelper retrofitHelper();  //提供http的帮助类

      RestletHelper restletHelper();

//    RealmHelper realmHelper();    //提供数据库帮助类

}
