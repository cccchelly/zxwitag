package com.alex.witAg.presenter.viewImpl;

import com.alex.witAg.base.IBaseView;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public interface ISplashView extends IBaseView{

    void enterLogin();

    void enterMain();

    void timeCountDown(long time);
}
