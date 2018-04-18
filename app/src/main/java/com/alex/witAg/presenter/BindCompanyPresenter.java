package com.alex.witAg.presenter;

import android.text.TextUtils;
import android.widget.TextView;

import com.alex.witAg.App;
import com.alex.witAg.base.BaseObserver;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.BindComRequestBean;
import com.alex.witAg.bean.BindComResponseBean;
import com.alex.witAg.http.AppDataManager;
import com.alex.witAg.http.network.Net;
import com.alex.witAg.presenter.viewImpl.IBindCompanyView;
import com.alex.witAg.utils.AppMsgUtil;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-04-11.
 */

public class BindCompanyPresenter extends BasePresenter<IBindCompanyView> {
    public void bind(String accountName, String address, String maintain, String name, String tel, String pass, String token) {
        if (TextUtils.isEmpty(accountName)||TextUtils.isEmpty(address)||TextUtils.isEmpty(maintain)||TextUtils.isEmpty(name)||TextUtils.isEmpty(tel)
                ||TextUtils.isEmpty(pass)){
            ToastUtils.showToast("请输入完整信息");
        }else {
            BindComRequestBean requestBean = new BindComRequestBean();
            requestBean.setToken(token);
            requestBean.setImei(AppMsgUtil.getIMEI(App.getAppContext()));
            requestBean.setAccountName(accountName);
            requestBean.setAddress(address);
            requestBean.setDeviceId(0);
            requestBean.setMaintain(maintain);
            requestBean.setName(name);
            requestBean.setPhone(tel);
            requestBean.setPassword(pass);
            requestBean.setLatitude("1111111");
            requestBean.setLongitude("2222222");
            requestBean.setPhotoInterval(1);
            requestBean.setPhotoMark(1);
            requestBean.setPhotoQuality(1);
            requestBean.setPhotoStart("12:00");

            AppDataManager.getInstence(Net.URL_KIND_COMPANY)
                    .bindCompany(requestBean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<BindComResponseBean>>() {
                        @Override
                        public void onSuccess(BaseResponse<BindComResponseBean> response) {

                        }
                    });

            //绑定  --  成功
            ShareUtil.saveCompanyUser(accountName);
            ShareUtil.saveToken(token);
            getView().toSplash();
        }
    }
}
