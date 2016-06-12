package com.gouyin.im;


import com.gouyin.im.bean.GoodSelectBaen;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 *  * Created by pc on 2016/6/12.
 */

public class ServerApi {
    private static AppAPI mAppApi;

    public static AppAPI getAppAPI() {
        if (mAppApi == null) {
            OkHttpClient httpClient = new OkHttpClient();
            httpClient.setConnectTimeout(10, TimeUnit.MINUTES);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppAPI.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            mAppApi = retrofit.create(AppAPI.class);
        }
        return mAppApi;
    }

    public interface AppAPI {
        String baseUrl = "http://db.immiao.com/";

        @POST("/a.php")
        Observable<GoodSelectBaen> login(
                @Query("usernname") String username,
                @Query("pwd") String password
        );
    }
}
