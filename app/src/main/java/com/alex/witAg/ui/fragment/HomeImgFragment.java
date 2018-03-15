package com.alex.witAg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.HomeImgPresenter;
import com.alex.witAg.presenter.viewImpl.IHomeImgView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-14.
 */

public class HomeImgFragment extends BaseFragment<HomeImgPresenter, IHomeImgView> implements IHomeImgView {
    @BindView(R.id.sdv_big)
    SimpleDraweeView mSdvBig;
    @BindView(R.id.tv_date)
    TextView         mTvDate;
    @BindView(R.id.ic_left)
    ImageView        mIcLeft;
    @BindView(R.id.img_recyclerView)
    RecyclerView     mImgRecyclerView;
    @BindView(R.id.ic_right)
    ImageView        mIcRight;
    @BindView(R.id.info_recyclerView)
    RecyclerView     mInfoRecyclerView;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {

        initRecyclerView();
    }

    private void initRecyclerView() {

        ImageAdapter imageAdapter = new ImageAdapter();
        mImgRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mImgRecyclerView.setAdapter(imageAdapter);

        InfoAdapter infoAdapter = new InfoAdapter();
        mInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mInfoRecyclerView.setAdapter(infoAdapter);

        ArrayList<String> images = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            images.add("" + i);
        }
        imageAdapter.setNewData(images);
        infoAdapter.setNewData(images);




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


    @OnClick({R.id.ic_left, R.id.ic_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_left:
                break;
            case R.id.ic_right:
                break;
        }
    }

    class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ImageAdapter() {
            super(R.layout.item_img);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    class InfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public InfoAdapter() {
            super(R.layout.item_info);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }


}
