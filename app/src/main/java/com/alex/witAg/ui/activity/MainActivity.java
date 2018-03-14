package com.alex.witAg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.bean.HomeBean;
import com.alex.witAg.presenter.MainPresenter;
import com.alex.witAg.presenter.viewImpl.IMainView;
import com.alex.witAg.ui.fragment.AboutFragment;
import com.alex.witAg.ui.fragment.ControlFragment;
import com.alex.witAg.ui.fragment.DataFragment;
import com.alex.witAg.ui.fragment.HomeFragment;
import com.alex.witAg.ui.fragment.SettingFragment;
import com.alex.witAg.view.LeftTabView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

@Route(path = AppContants.ARouterUrl.MAIN_ACTIVITY/*, extras = AppContants.LOGIN_INTERCEPTOR*/)
public class MainActivity extends BaseActivity<MainPresenter, IMainView> implements IMainView {


    @BindView(R.id.left_tab_view)
    LeftTabView mLeftTabView;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    private HomeFragment    mHomeFragment;
    private SettingFragment mSettingFragment;
    private DataFragment    mDataFragment;
    private ControlFragment mControlFragment;
    private AboutFragment mAboutFragment;


    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

        initFragment(savedInstanceState);

    }

    private void initListener() {
        mLeftTabView.setOnSelectedChangeListener((view, position) -> {
            Logger.d("position:  " +position);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
                //首页
                case 0:
                    transaction.hide(mDataFragment)
                            .hide(mSettingFragment)
                            .hide(mControlFragment)
                            .hide(mAboutFragment)
                            .show(mHomeFragment)
                            .commitAllowingStateLoss();
                    break;
                //实时数据
                case 1:

                    transaction.hide(mHomeFragment)
                            .hide(mSettingFragment)
                            .hide(mControlFragment)
                            .hide(mAboutFragment)
                            .show(mDataFragment)
                            .commitAllowingStateLoss();
                    break;
                //系统设置
                case 2:
                    transaction.hide(mHomeFragment)
                            .hide(mDataFragment)
                            .hide(mControlFragment)
                            .hide(mAboutFragment)
                            .show(mSettingFragment)
                            .commitAllowingStateLoss();
                    break;
                //手动控制
                case 3:
                    transaction.hide(mHomeFragment)
                            .hide(mDataFragment)
                            .hide(mSettingFragment)
                            .hide(mAboutFragment)
                            .show(mControlFragment)
                            .commitAllowingStateLoss();
                    break;
                //关于我们
                case 4:
                    transaction.hide(mHomeFragment)
                            .hide(mDataFragment)
                            .hide(mSettingFragment)
                            .hide(mControlFragment)
                            .show(mAboutFragment)
                            .commitAllowingStateLoss();
                    break;
                default:
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {//内存重启
            mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
            mSettingFragment = (SettingFragment) getSupportFragmentManager().findFragmentByTag("SettingFragment");
            mDataFragment = (DataFragment) getSupportFragmentManager().findFragmentByTag("DataFragment");
            mControlFragment = (ControlFragment) getSupportFragmentManager().findFragmentByTag("ControlFragment");
            mAboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentByTag("AboutFragment");

            currentTabPosition = savedInstanceState.getInt(AppContants.HOME_CURRENT_TAB_POSITION);
        } else {
            mHomeFragment = new HomeFragment();
            mSettingFragment = new SettingFragment();
            mDataFragment = new DataFragment();
            mControlFragment = new ControlFragment();
            mAboutFragment = new AboutFragment();

            transaction.add(R.id.fl_container, mHomeFragment, "HomeFragment");
            transaction.add(R.id.fl_container, mSettingFragment, "SettingFragment");
            transaction.add(R.id.fl_container, mDataFragment, "DataFragment");
            transaction.add(R.id.fl_container, mControlFragment, "ControlFragment");
            transaction.add(R.id.fl_container, mAboutFragment, "AboutFragment");
        }
        transaction.commit();

        initListener();
        mLeftTabView.setSelectPosition(currentTabPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //内存重启前保存位置
        Logger.e("onSaveInstanceState进来了1");
        if (mLeftTabView != null) {
            Logger.e("onSaveInstanceState进来了2");
            outState.putInt(AppContants.HOME_CURRENT_TAB_POSITION, mLeftTabView.getSelectPosition());
        }
    }
    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


    @Override
    public void onSuccess(HomeBean homeBean) {

    }

}
