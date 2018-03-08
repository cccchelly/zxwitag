package com.alex.witAg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.SplashPresenter;
import com.alex.witAg.presenter.viewImpl.ISplashView;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Locale;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public class SplashActivity extends BaseActivity<SplashPresenter,ISplashView> implements ISplashView {
    @BindView(R.id.tv_time)
    TextView mTvTime;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

        getPresenter().autoLogin();
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void enterLogin() {

        ARouter.getInstance().build(AppContants.ARouterUrl.LOGIN_ACTIVITY)
                .navigation();
        finish();
    }

    @Override
    public void enterMain() {

        ARouter.getInstance().build(AppContants.ARouterUrl.MAIN_ACTIVITY)
                .navigation();
        finish();
    }

    @Override
    public void timeCountDown(long time) {

        mTvTime.setText(String.format(Locale.getDefault(),"wait %d Seconds", time));
    }

}
