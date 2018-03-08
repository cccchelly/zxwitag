package com.alex.witAg.presenter;

import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.presenter.viewImpl.ISplashView;

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
        addDisposable(Observable.just(true)
                .delay(3, TimeUnit.SECONDS)
                .doOnSubscribe(disposable -> countDown())
                .subscribe(isLogin -> {
                    if (isLogin) {
                        getView().enterMain();
                    } else {
                        getView().enterLogin();
                    }
                }));

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
