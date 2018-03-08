package com.alex.witAg;

/**
 * Created by dth
 * Des:
 * Date: 2018-01-23.
 */

public interface AppContants {

    String API_BASE_URL     = "http://jht.meishifulu.cn/";
    int    CONNECT_TIME_OUT = 15;
    int    WRITE_TIME_OUT   = 15;
    int    READ_TIME_OUT    = 15;
    String APP_TAG          = "ParkYun";
    int LOGIN_INTERCEPTOR = 401;//通过ARouter拦截登录常量
    String HOME_CURRENT_TAB_POSITION = "home_current_tab_position";


    interface ARouterUrl{

        String SPLASH_ACTIVITY = "/foundation/splash";
        String MAIN_ACTIVITY = "/foundation/main";
        String LOGIN_ACTIVITY = "/foundation/login";
    }
}
