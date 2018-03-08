package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.ControlPresenter;
import com.alex.witAg.presenter.viewImpl.IControlView;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class ControlFragment extends BaseFragment<ControlPresenter,IControlView> implements IControlView {
    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_control;
    }

    @Override
    protected ControlPresenter initPresenter() {
        return null;
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }
}
