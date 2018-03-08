package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.SettingPresenter;
import com.alex.witAg.presenter.viewImpl.ISettingView;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public class SettingFragment extends BaseFragment<SettingPresenter,ISettingView> implements ISettingView {

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }
}
