package com.alex.witAg.base;

import android.text.TextUtils;
import android.widget.Toast;

import com.alex.witAg.App;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {

    private IBaseView mIBaseView;

    public BaseObserver(IBaseView iBaseView) {
        mIBaseView = iBaseView;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mIBaseView.addDisposable(d);
    }

    @Override
    public void onNext(@NonNull T response) {

        mIBaseView.showDataView();
        Logger.d("onNext: %s", response);
        switch (response.getCode()) {
            case BaseResponse.RESULT_CODE_SUCCESS:
                onSuccess(response);
                break;
            case BaseResponse.RESULT_CODE_TOKEN_EXPIRED:
                break;
            default:
                onDataFailure(response);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {

        //异常处理，需要自己实现
        Logger.e("onError: "+e.toString());
        mIBaseView.dissmissLoadingView();
        handleError(e,mIBaseView);
    }

    @Override
    public void onComplete() {

        mIBaseView.dissmissLoadingView();
    }

    public abstract void onSuccess(T response);

    /**
     * 对api返回的错误状态的处理 需要时自己实现
     * @param response
     */
    protected void onDataFailure(T response) {
        String msg = response.getMsg();
        Logger.w("request data but get failure:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            //            mBaseMvpView.showException(response.getMsg());
            Toast.makeText(App.getAppContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
            //            mBaseMvpView.showException("未知错误");
            Toast.makeText(App.getAppContext(), "未知错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 按照通用规则解析和处理数据请求时发生的错误。这个方法在执行支付等非标准的REST请求时很有用。
     */
    public static void handleError(Throwable throwable, IBaseView iBaseView) {
        if (throwable == null) {
            Toast.makeText(App.getAppContext(), "未知错误", Toast.LENGTH_SHORT).show();
            return;
        }
        //分为以下几类问题：网络连接，数据解析，客户端出错【空指针等】，服务器内部错误
        if (throwable instanceof SocketTimeoutException
                || throwable instanceof ConnectException
                || throwable instanceof UnknownHostException
                || throwable instanceof IOException) {
            Toast.makeText(App.getAppContext(), "网络异常", Toast.LENGTH_SHORT).show();
            iBaseView.showErrorView();
        } else if ((throwable instanceof JsonSyntaxException) || (throwable instanceof
                NumberFormatException) || (throwable instanceof MalformedJsonException)) {
            Toast.makeText(App.getAppContext(), "数据解析异常", Toast.LENGTH_SHORT).show();
        } else if ((throwable instanceof HttpException)) {
            Toast.makeText(App.getAppContext(), "服务器错误"+((HttpException) throwable).code(), Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof NullPointerException) {
            Toast.makeText(App.getAppContext(), "客户端异常"+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(App.getAppContext(), "未知错误", Toast.LENGTH_SHORT).show();
        }
    }
}
