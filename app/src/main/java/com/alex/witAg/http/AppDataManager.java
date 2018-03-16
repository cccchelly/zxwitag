package com.alex.witAg.http;

import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.HomeBean;
import com.alex.witAg.http.cache.AppCache;
import com.alex.witAg.http.cache.IAcache;
import com.alex.witAg.http.network.IApi;
import com.alex.witAg.http.network.Net;

import io.reactivex.Observable;

/**
 * Created by dth
 * Des: 统一的数据管理类
 * Date: 2018-01-23.
 */

public final class AppDataManager implements IDataManager{


    private static AppDataManager mAppDataManager;
    private final  IApi           mIApi;
    private final AppCache mAppCache;

    private AppDataManager() {
        mIApi = Net.getInstence().create();
        mAppCache = AppCache.getInstence();
    }

    public static AppDataManager getInstence() {
        if (mAppDataManager == null) {
            synchronized (AppDataManager.class) {
                if (mAppDataManager == null) {
                    mAppDataManager = new AppDataManager();
                }
            }
        }
        return mAppDataManager;
    }

    @Override
    public IApi getApi() {
        return mIApi;
    }

    @Override
    public IAcache getAcache() {
        return mAppCache;
    }

    @Override
    public Observable<BaseResponse<HomeBean>> getHomePageData() {
        return mIApi.getHomePageData();
    }
}
