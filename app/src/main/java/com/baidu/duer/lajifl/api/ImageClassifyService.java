package com.baidu.duer.lajifl.api;


import com.baidu.duer.lajifl.bean.image.ImageClassify;
import com.baidu.duer.lajifl.bean.image.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 图像识别接口
 *  - 前置URL为: https://aip.baidubce.com/
 * @params String access_token @query 必须参数，身份令牌
 * @params String image @Field 必须参数，去掉文件头的图像base64编码
 */
public interface ImageClassifyService<R> {
    @FormUrlEncoded
    @POST("rest/2.0/image-classify/v2/advanced_general")
    Call<ImageClassify<Result>> postImageClassify(@Query("access_token") String accessToken, @Field("image") String image64);
}
