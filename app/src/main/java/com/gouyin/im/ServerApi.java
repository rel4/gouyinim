package com.gouyin.im;


import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.bean.LoginBean;
import com.gouyin.im.bean.RegiterBean;
import com.gouyin.im.bean.RongyunBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.center.model.DefaultDynamicModel;
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
import retrofit2.http.GET;
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
                    .addInterceptor(logging)
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(false)
                    .authenticator(mAuthenticator)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .addNetworkInterceptor(mTokenInterceptor)
                    .build();
            //Retrofit.
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
        //        String baseUrl = "http://10.10.11.120:91/mimei/web/public/index.php/index/";
        String baseUrl = "http://mimei.cntttt.com:88/public/index.php/index/";

        @FormUrlEncoded
        @POST("User/login")
        Observable<LoginBean> login(
                @Field("name") String username,
                @Field("pwd") String password
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

        /**
         * 精选数据
         *
         * @param type
         * @param page
         * @return
         */
        @FormUrlEncoded
        @POST("Userrec/get_list_jingxuan")
        Observable<GoodSelectBaen> getGoodSelect(@Field("type") String type,
                                                 @Field("page") int page);

        /**
         * 个人动态信息列表
         *
         * @param uid
         * @param authcode
         * @return
         */
        @GET("User/user_detail_addon1")
        Observable<UserInfoDetailBean> getUserInfoDetail(@Query("uid") String uid,
                                                         @Query("authcode") String authcode);

        /**
         * 个人动态列表
         *
         * @param userId
         * @param authcode
         * @param page
         * @return
         */
        @GET("Latest/get_latest_list")
        Observable<UserInfoListBean> getPersonDynamincList(@Query("uid") String userId,
                                                           @Query("authcode") String authcode,
                                                           @Query("page") int page);

        /**
         * 动态评论列表
         *
         * @param id
         * @param page
         * @param authcode
         * @return
         */
        @GET("Latest/get_latest_comment")
        Observable<CommentDataListBean> getCommentList(@Query("lid") String id,
                                                       @Query("page") int page,
                                                       @Query("authcode") String authcode);

        /**
         * 动态发布
         *
         * @param type
         * @param content
         * @param pics
         * @param videos
         * @param address
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Latest/insert_latest")
        Observable<BaseBean> sendAllDefaultDynamic(@Field("type") String type,
                                                   @Field("title") String content,
                                                   @Field("img") String pics,
                                                   @Field("video") String videos,
                                                   @Field("address") String address,
                                                   @Field("authcode") String authcode);

        /**
         * 打赏
         *
         * @param playType
         * @param uid
         * @param money
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Reward/pub_reward")
        Observable<DefaultDataBean> aliPay(@Field("pay_type") String playType,
                                           @Field("to_uid") String uid,
                                           @Field("money") String money,
                                           @Field("authcode") String authcode);

        @GET("Rong/get_token")
        Observable<RongyunBean> getRongyunKey(@Query("authcode") String authcode);
    }
}

