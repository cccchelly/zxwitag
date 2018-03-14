package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.HomeLinePresenter;
import com.alex.witAg.presenter.viewImpl.IHomeLineView;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-14.
 */

public class HomeLineFragment extends BaseFragment<HomeLinePresenter,IHomeLineView> implements IHomeLineView {
    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_home_line;
    }

    @Override
    protected HomeLinePresenter initPresenter() {
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
