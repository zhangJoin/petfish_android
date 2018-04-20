package com.gage.petfish_android.di.module;


import com.gage.petfish_android.app.App;
import com.gage.petfish_android.model.http.RestletHelper;
import com.gage.petfish_android.model.http.api.HttpHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    /**
     * @Sigleton被注解的对象，在App中是单例存在的！
     * @Provider在@Module注解的类中，使用@Provider注解，说明提供依赖注入的具体对象
     */
    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }


    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RestletHelper restletHelper) {
        return restletHelper;
    }
}
