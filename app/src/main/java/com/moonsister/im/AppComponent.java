package com.moonsister.im;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pc on 2016/6/12.
 */
@Singleton
@Component(modules = {AppModule.class, ServerApiModule.class})
public interface AppComponent {
    Application getApplication();

    ServerApi getServerApi();
}
