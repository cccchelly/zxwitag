package com.alex.witAg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseActivity;
import com.alex.witAg.presenter.SetAccountPresenter;
import com.alex.witAg.presenter.viewImpl.IsetAccountView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = AppContants.ARouterUrl.SET_ACCOUNT_ACTIVITY)
public class SetAccountActivity extends BaseActivity<SetAccountPresenter, IsetAccountView> implements IsetAccountView {
    @BindView(R.id.set_account_edt_zhandian_name)
    EditText mEdtZhandianName;
    @BindView(R.id.set_account_edt_jigou)
    EditText mEdtJigou;
    @BindView(R.id.set_account_edt_person_name)
    EditText mEdtPersonName;
    @BindView(R.id.set_account_edt_person_phone)
    EditText mEdtPersonPhone;
    @BindView(R.id.set_account_edt_position)
    EditText mEdtPosition;
    @BindView(R.id.set_account_edt_pass)
    EditText mEdtPass;
    @BindView(R.id.set_account_tv_sure)
    TextView mTvSure;
    @BindView(R.id.set_account_tv_cancle)
    TextView mTvCancle;
    private String TAG = SetAccountActivity.class.getName();

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
    }



    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_set_account;
    }

    @Override
    protected SetAccountPresenter initPresenter() {
        return new SetAccountPresenter();
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
       finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.set_account_tv_sure, R.id.set_account_tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_account_tv_sure:
                String zhandianName = mEdtZhandianName.getText().toString();
                String jigou = mEdtJigou.getText().toString();
                String personName = mEdtPersonName.getText().toString();
                String personPhone = mEdtPersonPhone.getText().toString();
                String position = mEdtPosition.getText().toString();
                String pass = mEdtPass.getText().toString();
                getPresenter().setAccount(zhandianName,jigou,personName,personPhone,position,pass);
                break;
            case R.id.set_account_tv_cancle:
                onBackPressed();
                break;
        }
    }

}
