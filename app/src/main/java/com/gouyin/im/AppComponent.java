package com.gouyin.im;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by pc on 2016/6/12.
 */
@Singleton
@Component(modules = {AppModule.class, ServerApiModule.class})
public interface AppComponent {
    Application getApplication();

    ServerApi getServerApi();
}
