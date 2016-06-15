package com.gouyin.im;


import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.bean.RegiterBean;
import com.gouyin.im.utils.LogUtils;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * * Created by pc on 2016/6/12.
 */

public class ServerApi {
    private static AppAPI mAppApi;

    public static AppAPI getAppAPI() {
        if (mAppApi == null) {
            OkHttpClient httpClient = new OkHttpClient();

            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    LogUtils.e(AppAPI.class, "The Cookie is " + request.header("Cookie"));
                    LogUtils.e(AppAPI.class, "访问网络地址: " + request.urlString());
                    return chain.proceed(chain.request());
                }
            });
            httpClient.setConnectTimeout(4, TimeUnit.MINUTES);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppAPI.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient)
                    .build();

            mAppApi = retrofit.create(AppAPI.class);
        }
        return mAppApi;
    }

    public interface AppAPI {
        String baseUrl = "http://10.10.11.120:91/mimei/web/public/index.php/index/";

        @POST("/a.php")
        Observable<GoodSelectBaen> login(
                @Query("usernname") String username,
                @Query("pwd") String password
        );

        /**
         * 获取验证码
         *
         * @param mobile
         * @return
         */
        @FormUrlEncoded
        @POST("User/register_send_mobile_code")
        Observable<BaseBean> sendSecurityCode(@Field("mobile") String mobile);

        /**
         * 验证验证码
         *
         * @param mobile
         * @param code
         * @return
         */
        @FormUrlEncoded
        @POST("User/register_step1")
        Observable<RegiterBean> verifySecurityCode(@Field("mobile") String mobile,
                                                   @Field("code") String code);

        /**
         * 注册完成
         *
         * @param face
         * @param sex
         * @param pwd

         * @return
         */
        @FormUrlEncoded
        @POST("User/register_step2")
        Observable<BaseBean> regiterLogin(@Field("face") String face,
                                          @Field("sex") String sex,
                                          @Field("pwd") String pwd,
                                          @Field("mobileauth") String mobileauth);
    }
}
