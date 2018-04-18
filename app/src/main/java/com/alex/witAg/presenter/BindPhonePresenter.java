package com.alex.witAg.presenter;

import android.text.TextUtils;

import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.BindPhoneResponseBean;
import com.alex.witAg.bean.SendSmsResponseBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.IBindPhoneView;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-04-11.
 */

public class BindPhonePresenter extends BasePresenter<IBindPhoneView> {
    //发送验证码
    public void sendCode(String phone){
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showToast("请填写手机号码");
        }else {
            AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                    .sendSms(ShareUtil.getToken(),phone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<SendSmsResponseBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<SendSmsResponseBean> response) {

                        }
                    });
            //....................

        }

    }

    //绑定手机号
    public void bindPhone(String phone, String code) {
        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(code)){
            ToastUtils.showToast("请输入手机号或验证码");
        }else {
            AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                    .bindPhone(ShareUtil.getToken(),phone,code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<BindPhoneResponseBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<BindPhoneResponseBean> response) {

                        }
                    });
            //............
            getView().finishActivity();
        }
    }
}
