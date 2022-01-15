package com.baidu.duer.lajifl.api;


import com.baidu.duer.lajifl.bean.oauth.Oauth;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 申请百度身份令牌接口
 *  - 前置URL为: https://aip.baidubce.com/
 * @params String grant_type 必须参数，固定值为client_credentials
 * @params String client_id 必须参数，应用的API Key
 * @params String client_secret 必须参数，应用的Secret Key
 */
public interface OauthService {
    @GET("oauth/2.0/token")
    Call<Oauth> getOauth(@Query("grant_type") String grant_type, @Query("client_id") String client_id, @Query("client_secret") String client_secret);
}
