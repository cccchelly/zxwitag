package com.alex.witAg.presenter;

import android.util.Log;

import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.presenter.viewImpl.ISplashView;
import com.alex.witAg.utils.ShareUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public class SplashPresenter extends BasePresenter<ISplashView>{

    /**
     * 自动登录
     */
    public void autoLogin() {
        Log.i("==token==","token = "+ShareUtil.getToken());
        if (ShareUtil.hasToken()) {
            //弱化基础设置
           /* if (ShareUtil.isSetAndroidPass()) {   //完成基础设置
                getView().enterMain();
            }else {
                getView().toSetAccount();
            }*/
            getView().enterMain();
        }else {
            getView().setUrl();
        }
        /*addDisposable(Observable.just(true)
                .delay(3, TimeUnit.SECONDS)
                .doOnSubscribe(disposable -> countDown())
                .subscribe(isLogin -> {
                    if (isLogin) {
                        getView().enterMain();
                    } else {
                        getView().enterLogin();
                    }
                }));*/

    }

    /**
     * 登录倒计时
     */
    private void countDown() {
        addDisposable(Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
                .map(count -> (3 - count))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> getView().timeCountDown(count)));
    }
}
