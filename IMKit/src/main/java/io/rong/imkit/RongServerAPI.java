package io.rong.imkit;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jb on 2016/7/29.
 */
public class RongServerAPI {
    public static String baseUrl = "http://chatapi.yytbzs.cn:88/mimei/";
    public static String authcode;
    private static RongAPI mRongAPI;

    public static RongAPI getRongAPI() {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new RuntimeException("baseUrl is not null");
        }
        if (mRongAPI == null) {
            synchronized (RongServerAPI.class) {
                if (mRongAPI == null) {
                    // init okhttp 3 logger
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            RongLogUtils.e("RongServerAPI", "HttpLoggingInterceptor: " + message);
                        }

                    });
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .addInterceptor(logging)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .build();
                    mRongAPI = retrofit.create(RongAPI.class);

                }
            }
        }

        return mRongAPI;

    }

    public interface RongAPI {
        @FormUrlEncoded
        @POST("chat.php?action=chat")
        Observable<MsgBean> send(@Field("chat_type") String chatType,
                                 @Field("to_uid") String toUid,
                                 @Field("chat_content") String content,
                                 @Field("authcode") String authcode);
    }
}
