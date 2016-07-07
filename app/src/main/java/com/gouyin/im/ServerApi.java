package com.gouyin.im;


import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.CardInfoBean;
import com.gouyin.im.bean.CertificationStatusBean;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.GetMoneyBean;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.bean.LoginBean;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.bean.RegiterBean;
import com.gouyin.im.bean.RongyunBean;
import com.gouyin.im.bean.TiXinrRecordBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UnicodeUtils;
import com.gouyin.im.utils.gson.GsonConverterFactory;

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
    private static String TAG = "ServerApi";

    public static AppAPI getAppAPI() {
        if (mAppApi == null) {

            Interceptor mTokenInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    LogUtils.d(TAG, "addNetworkInterceptor : Response  code: " + response.code());
                    BufferedSource source = response.body().source();
                    source.request(Long.MAX_VALUE);
                    Buffer clone = source.buffer().clone();
                    LogUtils.d(TAG, "addNetworkInterceptor : Response  content: " + UnicodeUtils.decodeUnicode(clone.readUtf8()));
                    return response;
                }

            };
            // init okhttp 3 logger
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.e(TAG, "HttpLoggingInterceptor: " + message);
                }

            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            //401 Not Authorised
            Authenticator mAuthenticator = new Authenticator() {

                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    Request request = response.request();
                    LogUtils.d(AppAPI.class, "Authenticator : The Cookie is " + request.header("Cookie"));
                    LogUtils.e(TAG, "Authenticator : 访问网络地址: " + request.url().toString());
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
        String baseUrl = "http://2.yytbzs.cn:88/public/index.php/index/";
//        String baseUrl = "http://mimei.cntttt.com:88/public/index.php/index/";

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
        @GET("Userrec/get_list_jingxuan")
        Observable<GoodSelectBaen> getGoodSelect(@Query("type") String type,
                                                 @Query("page") int page);

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
        @GET("Latestcomment/get_latest_comment")
        Observable<CommentDataListBean> getCommentList(@Query("lid") String id,
                                                       @Query("page") int page,
                                                       @Query("authcode") String authcode);

        /**
         * 动态发布
         *
         * @param type
         * @param content
         * @param address
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Latest/insert_latest")
        Observable<BaseBean> sendAllDefaultDynamic(@Field("type") String type,
                                                   @Field("title") String title,
                                                   @Field("contents") String content,
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
        Observable<DefaultDataBean> getredPacketIndent(@Field("pay_type") String playType,
                                                       @Field("to_uid") String uid,
                                                       @Field("money") String money,
                                                       @Field("authcode") String authcode);

        /**
         * 送花
         *
         * @param type
         * @param uid
         * @param money
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Flower/pub_flower")
        Observable<DefaultDataBean> getFlowerIndent(@Field("pay_type") String type,
                                                    @Field("to_uid") String uid,
                                                    @Field("money") String money,
                                                    @Field("authcode") String authcode);

        /**
         * 获取token
         *
         * @param authcode
         * @return
         */
        @GET("Rong/get_token")
        Observable<RongyunBean> getRongyunKey(@Query("authcode") String authcode);

        /**
         * 朋友圈
         *
         * @param authcode
         * @param page
         * @return
         */
        @GET("Latest/get_latests_friends")
        Observable<UserInfoListBean> loadPersonDynamic(@Query("authcode") String authcode,
                                                       @Query("page") int page);

        /**
         * 获取个人的信息
         *
         * @param authcode
         * @return
         */
        @GET("User/user_detail_addon1_center")
        Observable<UserInfoDetailBean> loadPersonInfo(@Query("authcode") String authcode);

        /**
         * 动态红包支付
         *
         * @param id
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Latestbuy/latest_buy")
        Observable<DefaultDataBean> redPacketPay(@Field("latest_id") String id,
                                                 @Field("authcode") String authcode);

        /**
         * @param id
         * @param authcode
         * @return
         */
        @GET("Latest/get_latest_pay")
        Observable<PayRedPacketPicsBean> getPayDynamicPic(@Query("latest_id") String id,
                                                          @Query("authcode") String authcode);

        /**
         * @param address1
         * @param address2
         * @param height
         * @param sexid
         * @param nike
         * @param loadFile
         * @param serialize
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Apply/pub")
        Observable<DefaultDataBean> sendAllRzInfo(@Field("province") String address1,
                                                  @Field("city") String address2,
                                                  @Field("height") String height,
                                                  @Field("sex") String sexid,
                                                  @Field("nickname") String nike,
                                                  @Field("face") String loadFile,
                                                  @Field("apply_image") String serialize,
                                                  @Field("authcode") String authcode);

        /**
         * 获取认证状态
         *
         * @param authcode
         * @return
         */
        @GET("Apply/get_apply_status")
        Observable<CertificationStatusBean> getCertificationStatus(@Query("authcode") String authcode);

        /**
         * 提现列表
         *
         * @param authcode
         * @return
         */
        @GET("Withdraw/get_withdraw_list")
        Observable<TiXinrRecordBean> getTixinRecord(@Query("authcode") String authcode);

        @GET("Withdraw/get_withdraw_money")
        Observable<DefaultDataBean> getEnableMoney(@Query("authcode") String authcode);

        /**
         * 提现基本信息
         *
         * @param authcode
         * @return
         */
        @GET("Account/get_latest_info")
        Observable<GetMoneyBean> getGetmoney(@Query("authcode") String authcode);

        /**
         * 添加银行卡
         *
         * @param cardNumber
         * @param username
         * @param cardType
         * @param bankname
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Account/add")
        Observable<DefaultDataBean> getsubmitAccount(@Field("bank_no") String cardNumber,
                                                     @Field("name") String username,
                                                     @Field("type") String cardType,
                                                     @Field("bank_name") String bankname,
                                                     @Field("authcode") String authcode);

        /**
         * 提现
         *
         * @param number
         * @param money
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Withdraw/add")
        Observable<DefaultDataBean> getTiXianReason(@Field("account_id") String number,
                                                    @Field("money") int money,
                                                    @Field("authcode") String authcode);

        /**
         * y银行卡列表
         *
         * @param authcode
         * @return
         */
        @GET("Account/get_list")
        Observable<CardInfoBean> getCardInfo(@Query("authcode") String authcode);

        /**
         * 关注
         *
         * @param uid
         * @param type     1关注操作，2取消关注操作
         * @param authcode
         * @return
         */
        @FormUrlEncoded
        @POST("Follow/follow_act")
        Observable<DefaultDataBean> getWacthAction(@Field("touid") String uid,
                                                   @Field("type") String type,
                                                   @Field("authcode") String authcode);
    }
}

