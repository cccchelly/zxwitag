package com.alex.witAg.http.network;

import android.util.Log;

import com.alex.witAg.AppContants;
import com.alex.witAg.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public class Net {

    private static Net mNet;
    private static Retrofit mRetrofit;

    private Net() {

        OkHttpClient okHttpClient = provideOkHttpClient(new CustomInterceptor());

        mRetrofit = provideRetrofit(okHttpClient);

    }

    public static Net getInstence() {
        if (mNet == null) {
            synchronized (Net.class) {
                if (mNet == null) {
                    mNet = new Net();
                }
            }
        }
        return mNet;
    }

    public IApi create() {

        return mRetrofit.create(IApi.class);
    }

    private Retrofit provideRetrofit(OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(AppContants.API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient provideOkHttpClient(CustomInterceptor customInterceptor) {
        // TODO: 17-8-30
        // 1. 基本授权
        // 2. 基础Url设置
        // 3. 重试方案 For https://github.com/airbnb/okreplay library, recording and replaying server responses.
        // 4. ...
        // 5. 添加 log 打印

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(AppContants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(AppContants.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(AppContants.READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(customInterceptor)
                .addInterceptor((new HttpLoggingInterceptor()
                        .setLevel(BuildConfig.DEBUG || Log.isLoggable("OkHttp", Log.VERBOSE) ?
                                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)));
        return builder.build();
    }
}
