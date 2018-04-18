package com.alex.witAg.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.BindPhonePresenter;
import com.alex.witAg.presenter.viewImpl.IBindPhoneView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.umeng.analytics.MobclickAgent;

@Route(path = AppContants.ARouterUrl.BIND_PHONE_ACTIVITY)
public class BindPhoneActivity extends BaseActivity<BindPhonePresenter,IBindPhoneView> implements IBindPhoneView {

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        EditText edtPhone = (EditText) findViewById(R.id.bind_phone_edt_input_phone);
        EditText edtCode = (EditText)findViewById(R.id.bind_phone_edt_input_code);
        TextView tvSendCode = (TextView) findViewById(R.id.bind_phone_tv_send_code);
        TextView tvSure = (TextView)findViewById(R.id.bind_phone_tv_sure);
        TextView tvCancle = (TextView) findViewById(R.id.bind_phone_tv_cancle);
        tvSendCode.setOnClickListener(v -> {
            getPresenter().sendCode(edtPhone.getText().toString());
        });
        tvSure.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString();
            String code = edtCode.getText().toString();
            getPresenter().bindPhone(phone,code);
        });
        tvCancle.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected BindPhonePresenter initPresenter() {
        return new BindPhonePresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void finishActivity() {
        onBackPressed();
    }

}
