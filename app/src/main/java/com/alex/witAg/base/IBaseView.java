package com.alex.witAg.base;

import io.reactivex.disposables.Disposable;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public interface IBaseView extends BaseMvpView{

    /**
     * 数据页面
     */
    void showDataView();

    /**
     * 空数据页面
     */
    void showEmptyView();

    /**
     * 错误页面
     */
    void showErrorView();

    /**
     * loading页面
     * @param showText
     */
    void showLoadingView(String showText);

    /**
     * 取消loading
     */
    void dissmissLoadingView();

    /**
     * 处理异常错误
     * @param msg
     */
    void showException(String msg);

    /**
     *
     * @param disposable
     */
    void addDisposable(Disposable disposable);
}
