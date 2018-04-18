package com.alex.witAg.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alex.witAg.App;
import com.alex.witAg.utils.CaptureTaskUtil;
import com.alex.witAg.utils.SerialInforStrUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.TimeUtils;

import java.text.SimpleDateFormat;


/**
 * Created by Administrator on 2018-03-28.
 */

public class CaptureService extends Service {
    public static String action = "capture_action";
    private static final String TAG = CaptureService.class.getName();
    Handler mHandler = new Handler();
                  Runnable r = new Runnable() {
                  @Override
                   public void run() {

                      App.setIsTaskRun(true);
                      Log.i(TAG, App.getIsTaskRun()+" ----startCaptureTask");
                      //每隔一段时间循环执行run方法
                      CaptureTaskUtil captureTaskUtil = new CaptureTaskUtil();
                      captureTaskUtil.initDevice(App.getAppContext());    //初始化串口设备
                      if (captureTaskUtil.openCapture()) { //打开摄像头
                          captureTaskUtil.login();  //登录摄像头

                          //  1--2--0
                          String statue = App.getDeviceStatue();
                          if (TextUtils.equals(statue, "0")) {
                              captureTaskUtil.send(SerialInforStrUtil.getRiseStr());  // 1
                              if (isStatueChanged(SerialInforStrUtil.getRiseStr())) {
                                  captureTaskUtil.capture();
                                  sleepAfterCap();
                              }
                              captureTaskUtil.send(SerialInforStrUtil.getDeclineStr());  //2
                              if (isStatueChanged(SerialInforStrUtil.getDeclineStr())) {
                                  captureTaskUtil.capture();
                              }
                          } else if (TextUtils.equals(statue, "1")) {
                              captureTaskUtil.capture();
                              sleepAfterCap();
                              captureTaskUtil.send(SerialInforStrUtil.getDeclineStr());  // 2
                              if (isStatueChanged(SerialInforStrUtil.getDeclineStr())) {
                                  captureTaskUtil.capture();
                              }
                          } else if (TextUtils.equals(statue, "2")) {
                              captureTaskUtil.capture();
                              sleepAfterCap();
                              captureTaskUtil.send(SerialInforStrUtil.getRiseStr());  // 1
                              if (isStatueChanged(SerialInforStrUtil.getRiseStr())) {
                                  captureTaskUtil.capture();
                              }
                          }
                          App.setIsTaskRun(false);
                      }
                      captureTaskUtil.clossCapture(); //关闭摄像头
                      Log.i(TAG, App.getIsTaskRun()+" ----completeCaptureTask");
                    mHandler.postDelayed(this, ShareUtil.getTaskTime());}
                };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "--------->onCreate: ");
        new Thread(() -> {
            while (true) {
               String taskTime =  ShareUtil.getStartTaskTime();
               String nowTime =  TimeUtils.millis2String(System.currentTimeMillis(),new SimpleDateFormat("HH:mm"));
               //Log.i(TAG,"time1="+taskTime+"---time2="+nowTime);
                if (TextUtils.equals(taskTime,nowTime)) {
                    mHandler.postDelayed(r, 0);//发送请求开始执行
                    break;
                }else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "--------->onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "--------->onDestroy: ");
        super.onDestroy();
    }
    boolean isStatueChanged(String nextStatus){
        boolean isChange = false;
        String next = "";
            for (int i=1; i<10;i++){    //查询状态是否改变  若状态未改变休眠一秒继续查询
                Log.i(TAG,"sta="+App.getDeviceStatue()+",s="+nextStatus);
                if (!TextUtils.equals(nextStatus,"{sta:"+App.getDeviceStatue()+"}")){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    isChange = true;
                }
            }
            return isChange;
    }
    void sleepAfterCap(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
