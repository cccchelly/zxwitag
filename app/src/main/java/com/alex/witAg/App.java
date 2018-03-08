package com.alex.witAg;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public class App extends MultiDexApplication {

    private static Context mAppContext;

    public static Context getAppContext() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();

        initLogger();
        initARouter();
        initFresco();
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy
                .newBuilder()
                .tag(AppContants.APP_TAG)
                .build()
        ) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }

        ARouter.init(this);
    }

    private void initFresco() {
        Fresco.initialize(this);
    }


}
