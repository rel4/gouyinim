package com.gouyin.im;

import android.app.Application;

import com.gouyin.im.utils.ConfigUtils;

/**
 * Created by pc on 2016/6/3.
 */
public class ApplicationConfig extends Application {
    AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        ConfigUtils.getInstance().setApplicationContext(this);
        appComponent =DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .serverApiModule(new ServerApiModule(new ServerApi()))
                .build();
    }
    public AppComponent getAppComponent(){

        return  appComponent;
    }

}
