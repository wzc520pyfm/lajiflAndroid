package com.baidu.duer.lajifl.api;

import com.baidu.duer.lajifl.bean.lajifl.Data;
import com.baidu.duer.lajifl.bean.lajifl.Description;
import com.baidu.duer.lajifl.bean.lajifl.Lajifl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 垃圾分类接口
 *  - 前置URL为: https://api.muxiaoguo.cn/
 * @params String api_key @query 必须参数，身份令牌
 * @params String m @query 必须参数，物品名
 */
public interface LajiflService<D> {
    @GET("api/lajifl")
    Call<Lajifl<Data<Description>>> getLajifl(@Query("api_key") String apiKey, @Query("m") String m);
}
