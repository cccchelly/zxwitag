package com.alex.witAg.presenter;

import android.text.TextUtils;

import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.presenter.viewImpl.IsetAccountView;
import com.alex.witAg.utils.ShareUtil;
import com.alex.witAg.utils.ToastUtils;

/**
 * Created by Administrator on 2018-04-11.
 */

public class SetAccountPresenter extends BasePresenter<IsetAccountView> {
    //设置设备密码
    public void setAccount(String zhandianName, String jigou, String personName, String personPhone, String position, String pass) {
        /*if (TextUtils.isEmpty(zhandianName)|| TextUtils.isEmpty(jigou)||
                TextUtils.isEmpty(personName)||TextUtils.isEmpty(personPhone)||
                TextUtils.isEmpty(position)||TextUtils.isEmpty(pass)){
            ToastUtils.showToast("请完善信息");
        }else {*/
            //上传信息到服务器
            //上传成功
            ToastUtils.showToast("设置成功");
            ShareUtil.saveAndroidPass(pass);
            getView().finishActivity();

        //}
    }
}
