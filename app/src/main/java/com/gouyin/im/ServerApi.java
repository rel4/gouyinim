package com.gouyin.im;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.bean.RegiterBean;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UnicodeUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * * Created by pc on 2016/6/12.
 */

public class ServerApi {
    private static AppAPI mAppApi;

    public static AppAPI getAppAPI() {
        if (mAppApi == null) {
            Gson gson = new GsonBuilder().create();

            Interceptor mTokenInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    LogUtils.d(AppAPI.class, "addNetworkInterceptor : Response  code: " + response.code());
                    BufferedSource source = response.body().source();
                    source.request(Long.MAX_VALUE);
                    Buffer clone = source.buffer().clone();
                    LogUtils.d(AppAPI.class, "addNetworkInterceptor : Response  content: " + UnicodeUtils.decodeUnicode(clone.readUtf8()));
                    return response;
                }

            };
            // init okhttp 3 logger
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.d(AppAPI.class, "HttpLoggingInterceptor: " + message);
                }

            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            //401 Not Authorised
            Authenticator mAuthenticator = new Authenticator() {

                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    Request request = response.request();
                    LogUtils.d(AppAPI.class, "Authenticator : The Cookie is " + request.header("Cookie"));
                    LogUtils.d(AppAPI.class, "Authenticator : 访问网络地址: " + request.url().toString());
                    LogUtils.d(AppAPI.class, "Authenticator : 访问body : " + request.body().toString());
                    return request;
                }
            };

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    LogUtils.d(AppAPI.class, "addInterceptor : 访问request : " + chain.request().toString());
                    Response response = chain.proceed(chain.request());

                    LogUtils.d(AppAPI.class, "addInterceptor : Response  code: " + response.code());
                    BufferedSource source = response.body().source();
                    source.request(Long.MAX_VALUE);
                    Buffer clone = source.buffer().clone();
                    LogUtils.d(AppAPI.class, "addInterceptor : Response  content: " + UnicodeUtils.decodeUnicode(clone.readUtf8()));
                    return response;
                }
            };

            //OkHttpClient
            OkHttpClient httpClient = new OkHttpClient.Builder()
//                    .addInterceptor(logging)
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(false)
                    .authenticator(mAuthenticator)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .addNetworkInterceptor(mTokenInterceptor)
                    .build();
            //Retrofit.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppAPI.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
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
//        Observable<>
    }
}
