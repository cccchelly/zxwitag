package com.alex.witAg.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.witAg.App;
import com.alex.witAg.R;
import com.alex.witAg.adapter.DeviceAdapter;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.ControlPresenter;
import com.alex.witAg.presenter.DataPresenter;
import com.alex.witAg.presenter.LoginPresenter;
import com.alex.witAg.presenter.viewImpl.IControlView;
import com.alex.witAg.ui.test.CrashUtil;
import com.alex.witAg.ui.test.PlaySurfaceView;
import com.alex.witAg.ui.test.jna.HCNetSDKJNAInstance;
import com.alex.witAg.utils.CapturePostUtil;
import com.alex.witAg.utils.CaptureTaskUtil;
import com.alex.witAg.utils.DensityUtil;
import com.alex.witAg.utils.DevicesLoginUtil;
import com.alex.witAg.utils.FileUtils;
import com.alex.witAg.utils.SerialInforStrUtil;
import com.alex.witAg.utils.TimeUtils;
import com.alex.witAg.utils.ToastUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.INT_PTR;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_JPEGPARA;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.RealDataCallBack;
import com.hikvision.netsdk.StdDataCallBack;
import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.logger.Logger;

import org.MediaPlayer.PlayM4.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class ControlFragment extends BaseFragment<ControlPresenter, IControlView>  implements IControlView{
    @BindView(R.id.tv_rise)
    TextView mTvRise;
    @BindView(R.id.tv_decline)
    TextView mTvDecline;
    @BindView(R.id.tv_take_photo)
    TextView mTvTakePhoto;
    @BindView(R.id.ic_reset)
    TextView mIcReset;
    @BindView(R.id.tv_serial)
    TextView mTvSearal;
    @BindView(R.id.et_serial_info)
    EditText mEtSerialInfo;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {
        getPresenter().initDevice(getActivity());
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_control;
    }

    @Override
    protected ControlPresenter initPresenter() {
        return new ControlPresenter();
    }

    @Override
    protected void onRetryListener() {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void onDestroy() {
       getPresenter().destoryDevice();
        super.onDestroy();
    }

    @OnClick({R.id.tv_rise, R.id.tv_decline, R.id.tv_take_photo, R.id.ic_reset, R.id.tv_serial})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_rise:
               /* if (TextUtils.isEmpty(getSerialInfo())) {
                    showSerialInfoEmpty();
                    return;
                }*/
                getPresenter().send(SerialInforStrUtil.getRiseStr());  //1
                break;
            case R.id.tv_decline:
                getPresenter().send(SerialInforStrUtil.getDeclineStr()); //2
                break;
            case R.id.tv_take_photo:
                //拍照

                if (!App.getIsTaskRun()) {
                   /* if (App.isIsNeedReLogin()){
                        getPresenter().login();
                    }*/
                    if (getPresenter().openCapture()) {
                        getPresenter().login();
                        getPresenter().capture();
                        getPresenter().clossCapture();
                    }
                /*Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.big);
                    FileUtils.saveContentToSdcard("aaaaa.jpg",FileUtils.Bitmap2Bytes(bitmap));
                    File fileNew  = FileUtils.getFileFromSdcard("aaaaa.jpg");
                    String deviceStatue = "";
                    if (TextUtils.equals(App.getDeviceStatue(),"1")){
                        deviceStatue = "-A";
                    }else if (TextUtils.equals(App.getDeviceStatue(),"2")){
                        deviceStatue = "-B";
                    }else {
                        deviceStatue = "-O";
                    }
                    String picName = TimeUtils.millis2String(System.currentTimeMillis(),
                            new SimpleDateFormat("yyyyMMddHHmmss"))+deviceStatue+".jpg";
                    CapturePostUtil.postPic(fileNew,picName);*/
                }else {
                    ToastUtils.showToast("定时拍照进行中，请稍后手动操作");
                }
                break;
            case R.id.ic_reset:
                getPresenter().send(SerialInforStrUtil.getResetStr()); //0
                break;
            case R.id.tv_serial:
                //getPresenter().getDeviceList();
                break;
        }
    }




    @Override
    public String getSerialInfo() {
        return mEtSerialInfo.getText().toString();
    }

    @Override
    public void showSerialInfoEmpty() {
        ToastUtils.showToast("发送数据不能为空！");
    }

    @Override
    public Activity getACtivity() {
        return getActivity();
    }

    @Override
    public void showDialog(DeviceAdapter mDeviceAdapter,SerialPortManager mSerialPortManager) {
        DialogPlus.newDialog(getContext())
                .setContentHolder(new ListHolder())
                .setAdapter(mDeviceAdapter)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOverlayBackgroundResource(Color.TRANSPARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(DensityUtil.dip2px(600))
                .setOnItemClickListener((dialog, item, view1, position) -> {
                    mSerialPortManager.closeSerialPort();
                    Device mDevice = mDeviceAdapter.getItem(position);
                    getPresenter().openDevice(mDevice);
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    @Override
    public void showOpenMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showCapture(Bitmap bitmap) {
        DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.activity_show_pic))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOverlayBackgroundResource(Color.TRANSPARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        dialogPlus.show();
        ImageView imageView = (ImageView) dialogPlus.getHolderView().findViewById(R.id.show_pic_img);
        imageView.setOnClickListener(v -> dialogPlus.dismiss());
        imageView.setImageBitmap(bitmap);
    }

}
