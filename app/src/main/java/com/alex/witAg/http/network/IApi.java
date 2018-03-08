package com.alex.witAg.http.network;

import com.alex.witAg.base.BaseResponse;
import com.alex.witAg.bean.HomeBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by dth
 * Des:所有后台api在此申明
 * Date: 2018-01-23.
 */

public interface IApi {

    //Retrofit的Url组合规则
      //baseUrl                             //和URL有关的注解中提供的值     //最后api的结果url
//    http://localhost:4567/path/to/other/	/post	                    http://localhost:4567/post
//    http://localhost:4567/path/to/other/	post	                    http://localhost:4567/path/to/other/post
//    http://localhost:4567/path/to/other/	https://github.com/ikidou	https://github.com/ikidou
//    如果你在注解中提供的url是完整的url，则url将作为请求的url。
//    如果你在注解中提供的url是不完整的url，且不以 / 开头，则请求的url为baseUrl+注解中提供的值
//    如果你在注解中提供的url是不完整的url，且以 / 开头，则请求的url为baseUrl的主机部分+注解中提供的值
    
    /**
     * 首页数据
     * @return
     */
    @POST("/app/goods/shopIndex")
    Observable<BaseResponse<HomeBean>> getHomePageData();
}
