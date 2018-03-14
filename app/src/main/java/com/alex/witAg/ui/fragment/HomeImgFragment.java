package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.HomeImgPresenter;
import com.alex.witAg.presenter.viewImpl.IHomeImgView;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-14.
 */

public class HomeImgFragment extends BaseFragment<HomeImgPresenter,IHomeImgView> implements IHomeImgView{
    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_home_img;
    }

    @Override
    protected HomeImgPresenter initPresenter() {
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
