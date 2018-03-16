package com.alex.witAg.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.witAg.R;
import com.alex.witAg.adapter.DeviceAdapter;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.ControlPresenter;
import com.alex.witAg.presenter.viewImpl.IControlView;
import com.alex.witAg.utils.DensityUtil;
import com.alex.witAg.utils.ToastUtils;
import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class ControlFragment extends BaseFragment<ControlPresenter, IControlView> implements IControlView, OnOpenSerialPortListener {
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
    private SerialPortManager mSerialPortManager;
    public static final String TAG = ControlFragment.class.getName();
    private ArrayList<Device> mDevices;
    private Device mDevice;
    private DeviceAdapter mDeviceAdapter;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        mDevices = serialPortFinder.getDevices();
        if(mDevices == null || mDevices.size() == 0) return;
        mDevice = mDevices.get(0);
        mSerialPortManager = new SerialPortManager();
        Logger.d("device: ",mDevices);
        mDeviceAdapter = new DeviceAdapter(getContext(), mDevices);


    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_control;
    }

    @Override
    protected ControlPresenter initPresenter() {
        return null;
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
        if (mSerialPortManager != null) {
            mSerialPortManager.closeSerialPort();
            mSerialPortManager = null;
        }
        super.onDestroy();
    }

    @OnClick({R.id.tv_rise, R.id.tv_decline, R.id.tv_take_photo, R.id.ic_reset,R.id.tv_serial})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_rise:
                String data = mEtSerialInfo.getText().toString();
                if (TextUtils.isEmpty(data)) {
                    ToastUtils.showToast("发送数据不能为空！");
                    return;
                }
                send(data);
                break;
            case R.id.tv_decline:
//                send(data);
                break;
            case R.id.tv_take_photo:
//                send(data);
                break;
            case R.id.ic_reset:
//                send(data);
                break;
            case R.id.tv_serial:
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
                            mDevice = mDeviceAdapter.getItem(position);
                            boolean result = openDevice(mDevice);

                            dialog.dismiss();
                        })
                        .create()
                        .show();
                break;
        }
    }

    @Override
    public void onSuccess(File device) {

        ToastUtils.showToast(String.format("串口 [%s] 打开成功", device.getPath()));
    }

    @Override
    public void onFail(File device, Status status) {

        switch (status) {
            case NO_READ_WRITE_PERMISSION:
                ToastUtils.showToast(device.getPath()+ "--- 没有读写权限");
                break;
            case OPEN_FAIL:
            default:
                ToastUtils.showToast(device.getPath()+ "--- 串口打开失败");
                break;
        }
    }

    public boolean openDevice(Device device) {
        // 打开串口
        boolean openSerialPort = mSerialPortManager.setOnOpenSerialPortListener(this)
                .setOnSerialPortDataListener(new OnSerialPortDataListener() {
                    @Override
                    public void onDataReceived(byte[] bytes) {
                        Log.i(TAG, "onDataReceived [ byte[] ]: " + Arrays.toString(bytes));
                        Log.i(TAG, "onDataReceived [ String ]: " + new String(bytes));
                        final byte[] finalBytes = bytes;
                        getActivity().runOnUiThread(() -> ToastUtils.showToast(String.format("接收\n%s", new String(finalBytes))));
                    }

                    @Override
                    public void onDataSent(byte[] bytes) {
                        Log.i(TAG, "onDataSent [ byte[] ]: " + Arrays.toString(bytes));
                        Log.i(TAG, "onDataSent [ String ]: " + new String(bytes));
                        final byte[] finalBytes = bytes;
                        getActivity().runOnUiThread(() -> ToastUtils.showToast(String.format("发送\n%s", new String(finalBytes))));
                    }
                })
                .openSerialPort(device.getFile(), 9600);

        Log.i(TAG, "onCreate: openSerialPort = " + openSerialPort);

        return openSerialPort;
    }

    /**
     * 发送数据
     *
     *
     */
    public void send(String data) {
        if (null == data) {
            return;
        }
        if (TextUtils.isEmpty(data)) {
            Log.i(TAG, "onSend: 发送内容为 null");
            return;
        }

        byte[] sendContentBytes = data.getBytes();

        boolean sendBytes = mSerialPortManager.sendBytes(sendContentBytes);
        Log.i(TAG, "onSend: sendBytes = " + sendBytes);
        ToastUtils.showToast(sendBytes ? "发送成功" : "发送失败");
    }

}
