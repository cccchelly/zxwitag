package com.alex.witAg.presenter;

import android.text.TextUtils;

import com.alex.witAg.App;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.GetTokenBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.ISetComUrlView;
import com.alex.witAg.utils.AppMsgUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.PUT;

/**
 * Created by Administrator on 2018-04-11.
 */

public class SetCompanyUrlPresenter extends BasePresenter<ISetComUrlView> {

    public void setUrl(String url){
        if (TextUtils.isEmpty(url)){
            ToastUtils.showToast("请输入地址");
        }else {
            //获取token
            //成功
            AppDataManager.getInstence(Net.BASE)
                    .getToken(AppMsgUtil.getIMEI(App.getAppContext()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<GetTokenBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<GetTokenBean> response) {

                        }
                    });

            String token = "token";

            //判断是否设置站点信息设备密码等
            if (ShareUtil.isCompanyUserBind()){
                ShareUtil.saveToken(token); //存储token
                getView().finishActivity();
            }else {
                getView().toSetAccount(token);
            }


        }
    }

}
