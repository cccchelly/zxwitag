package com.alex.witAg.http.loading;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import com.orhanobut.dialogplus.DialogPlus;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-24.
 */

public class NetLoadingHelper implements NetLoadingInterface {

    private DialogPlus    mDialogPlus;
    private LoadingHolder mLoadingHolder;
    private int showNum = 0;//保证有show才去dissmiss

    public NetLoadingHelper(Context context) {
        mLoadingHolder = new LoadingHolder();
        mDialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(mLoadingHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(Color.TRANSPARENT)
                .setOverlayBackgroundResource(Color.TRANSPARENT)
                .create();
    }

    @Override
    public void showLoadingView() {
        mDialogPlus.show();
    }

    @Override
    public void showLoadingView(String msg) {

        mLoadingHolder.setTextTip(msg);
        mDialogPlus.show();
        showNum++;
    }

    @Override
    public void dissLoadingView() {

        if (showNum > 0) {
            if (mDialogPlus != null) {
                mDialogPlus.dismiss();
                showNum--;
            }
        }
    }

    @Override
    public void releaseView() {
        dissLoadingView();
        showNum = 0;
        mLoadingHolder = null;
        mDialogPlus = null;
    }
}
