package com.alex.witAg.presenter;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.alex.witAg.AppContants;
import com.alex.witAg.base.BasePresenter;
import com.alex.witAg.presenter.viewImpl.ILoginView;
import com.alex.witAg.ui.activity.LoginActivity;
import com.alex.witAg.ui.test.PlaySurfaceView;
import com.alex.witAg.utils.ShareUtil;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.RealDataCallBack;
import com.hikvision.netsdk.StdDataCallBack;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-25.
 */

public class LoginPresenter extends BasePresenter<ILoginView>{
    private final String TAG = LoginActivity.class.getName();
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private StdDataCallBack cbf = null;
    private RealDataCallBack rdf = null;
    private SurfaceView m_osurfaceView = null;
    private static PlaySurfaceView[] playView = new PlaySurfaceView[4];

    public void login(){
        try {
                // login on the device
                ShareUtil.saveLoginId(loginDevice());
                if (ShareUtil.getLoginId() < 0) {
                    Log.e(TAG, "This device logins failed!");
                    return;
                } else {
                    Log.e(TAG, "m_iLogID=" + ShareUtil.getLoginId());
                }
                // get instance of exception callback and set
                ExceptionCallBack oexceptionCbf = getExceptiongCbf();
                if (oexceptionCbf == null) {
                    Log.e(TAG, "ExceptionCallBack object is failed!");
                    return;
                }

                if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(
                        oexceptionCbf)) {
                    Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
                    return;
                }

                //                m_oLoginBtn.setText("Logout");
                Log.i(TAG,
                        "Login sucess ****************************1***************************");
                getView().back();
        } catch (Exception err) {
            Log.e(TAG, "error: " + err.toString());
        }
    }

    private int loginDevice() {
        int iLogID = -1;
        if (!initeSdk()) {
            return iLogID;
        }
        iLogID = loginNormalDevice();
        return iLogID;
    }

    private int loginNormalDevice() {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        String strIP = ShareUtil.getIp();//IP地址
        int nPort = ShareUtil.getPort();//端口号
        String strUser = ShareUtil.getUser();//用户名
        String strPsd = ShareUtil.getPass();//密码
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort,
                strUser, strPsd, m_oNetDvrDeviceInfoV30);
        if (iLogID < 0) {
            Log.e(TAG, "NET_DVR_Login is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        Log.i(TAG,(m_oNetDvrDeviceInfoV30.byChanNum)+"");
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
            //m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            ShareUtil.saveChannel(m_oNetDvrDeviceInfoV30.byChanNum);
        } else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
            //m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            ShareUtil.saveChannel(m_oNetDvrDeviceInfoV30.byIPChanNum
                    + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256);
        }

        if (ShareUtil.getChannel() > 1) {
            ChangeSingleSurFace(false);
        } else {
            ChangeSingleSurFace(true);
        }
        Log.i(TAG, "NET_DVR_Login is Successful!");

        return iLogID;
    }

    private ExceptionCallBack getExceptiongCbf() {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }

    /**
     * @fn initeSdk
     */
    private boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
                true);
        return true;
    }

    private void ChangeSingleSurFace(boolean bSingle) {
        DisplayMetrics metric = new DisplayMetrics();
        getView().getContext().getWindowManager().getDefaultDisplay().getMetrics(metric);

        for (int i = 0; i < 4; i++) {
            if (playView[i] == null) {
                playView[i] = new PlaySurfaceView(getView().getContext());
                playView[i].setParam(metric.widthPixels);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.bottomMargin = playView[i].getM_iHeight() - (i / 2)
                        * playView[i].getM_iHeight();
                params.leftMargin = (i % 2) * playView[i].getM_iWidth();
                params.gravity = Gravity.BOTTOM | Gravity.LEFT;
                getView().getContext().addContentView(playView[i], params);
                playView[i].setVisibility(View.INVISIBLE);

            }
        }

        if (bSingle) {
            // ��·ֻ��ʾ����1
            for (int i = 0; i < 4; ++i) {
                playView[i].setVisibility(View.INVISIBLE);
            }
            playView[0].setParam(metric.widthPixels * 2);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = playView[3].getM_iHeight() - (3 / 2)
                    * playView[3].getM_iHeight();
            //            params.bottomMargin = 0;
            params.leftMargin = 0;
            // params.
            params.gravity = Gravity.BOTTOM | Gravity.LEFT;
            playView[0].setLayoutParams(params);
            playView[0].setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < 4; ++i) {
                playView[i].setVisibility(View.VISIBLE);
            }

            playView[0].setParam(metric.widthPixels);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = playView[0].getM_iHeight() - (0 / 2)
                    * playView[0].getM_iHeight();
            params.leftMargin = (0 % 2) * playView[0].getM_iWidth();
            params.gravity = Gravity.BOTTOM | Gravity.LEFT;
            playView[0].setLayoutParams(params);
        }

    }

}
