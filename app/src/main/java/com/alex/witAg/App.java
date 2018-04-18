package com.alex.witAg;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.multidex.MultiDexApplication;

import com.alex.witAg.ui.test.CrashUtil;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.commonsdk.UMConfigure;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

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
        LitePal.initialize(mAppContext);
        CrashUtil.getInstance().init(mAppContext);
        initUmeng();
    }

    private void initUmeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:友盟 app key
         * 参数3:友盟 channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this,AppContants.UMENG_APP_KEY, "Umeng", UMConfigure.DEVICE_TYPE_BOX, AppContants.UMENG_SECERT);
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

    private static  int iLogId = -1; // return by NET_DVR_Login_v30
    private static int iChanNum = 0; // channel number
    private static int iStartChan = 0;
    private static String deviceStatue = "";  //串口设备状态码
    private static String deviceError = "";  //串口设备错误码
    private static String deviceBatvol = "";  //串口电池电压
    private static String deviceSunvol = "";  //串口太阳能电压

    private static String captureCamSta = "";//摄像头开关状态
    private static String captureHignSta = "";//绿板高度状态
    private static String captureErrorSta = "";//摄像头错误状态

    private static  boolean isTaskRun = false;
    private static boolean isNeedReLogin = false;  //是否需要重新登录（修改登录相机参数时需要）

    public static boolean isIsNeedReLogin() {
        return isNeedReLogin;
    }

    public static void setIsNeedReLogin(boolean isNeedReLogin) {
        App.isNeedReLogin = isNeedReLogin;
    }

    public static Context getmAppContext() {
        return mAppContext;
    }

    public static void setmAppContext(Context mAppContext) {
        App.mAppContext = mAppContext;
    }

    public static boolean getIsTaskRun() {
        return isTaskRun;
    }

    public static void setIsTaskRun(boolean isTaskRun) {
        App.isTaskRun = isTaskRun;
    }

    public static int getiLogId() {
        return iLogId;
    }

    public static void setiLogId(int iLogId) {
        App.iLogId = iLogId;
    }

    public static int getiChanNum() {
        return iChanNum;
    }

    public static void setiChanNum(int iChanNum) {
        App.iChanNum = iChanNum;
    }

    public static int getiStartChan() {
        return iStartChan;
    }

    public static void setiStartChan(int iStartChan) {
        App.iStartChan = iStartChan;
    }

    public static String getDeviceStatue() {
        return deviceStatue;
    }

    public static void setDeviceStatue(String deviceStatue) {
        App.deviceStatue = deviceStatue;
    }

    public static String getDeviceError() {
        return deviceError;
    }

    public static void setDeviceError(String deviceError) {
        App.deviceError = deviceError;
    }

    public static String getDeviceBatvol() {
        return deviceBatvol;
    }

    public static void setDeviceBatvol(String deviceBatvol) {
        App.deviceBatvol = deviceBatvol;
    }

    public static String getDeviceSunvol() {
        return deviceSunvol;
    }

    public static void setDeviceSunvol(String deviceSunvol) {
        App.deviceSunvol = deviceSunvol;
    }

    public static String getCaptureCamSta() {
        return captureCamSta;
    }

    public static void setCaptureCamSta(String captureCamSta) {
        App.captureCamSta = captureCamSta;
    }

    public static String getCaptureHignSta() {
        return captureHignSta;
    }

    public static void setCaptureHignSta(String captureHignSta) {
        App.captureHignSta = captureHignSta;
    }

    public static String getCaptureErrorSta() {
        return captureErrorSta;
    }

    public static void setCaptureErrorSta(String captureErrorSta) {
        App.captureErrorSta = captureErrorSta;
    }
}
