package com.baidu.duer.lajifl.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.duer.lajifl.R;
import com.baidu.duer.lajifl.api.OauthService;
import com.baidu.duer.lajifl.bean.oauth.Oauth;
import com.baidu.duer.lajifl.utils.SharedUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 获取百度身份令牌的网络请求
 */
public class AccessTokenController {

    // 获取access_token
    public static void getAccessToken(Context context, String TAG) {
        SharedUtil sharedUtil;
        // 获取共享参数工具类单例
        sharedUtil = SharedUtil.getInstance(context);
        // 构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                // 设置网络请求BaseUrl地址
                .baseUrl("https://aip.baidubce.com/")
                // 设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 创建网络请求接口对象实例
        OauthService oauthService = retrofit.create(OauthService.class);
        // 对发送请求进行封装
        Call<Oauth> dataCall = oauthService.getOauth(context.getString(R.string.grant_type), context.getString(R.string.client_id), context.getString(R.string.client_secret));
        // 异步请求
        dataCall.enqueue(new Callback<Oauth>() {
            // 请求成功回调
            @Override
            public void onResponse(Call<Oauth> call, Response<Oauth> response) {
                Log.d(TAG, "回调成功：" + response.toString());
                Toast.makeText(context, "getOauth回调成功:异步执行", Toast.LENGTH_SHORT).show();
                // 解析数据
                Oauth body = response.body();
                if(body == null) return;
                // 将access_token存进共享参数
                sharedUtil.writeShared("access_token", body.getAccess_token());
            }
            //请求失败回调
            @Override
            public void onFailure(Call<Oauth> call, Throwable t) {
                Log.e(TAG, "回调失败：" + t.getMessage() + "," + t.toString());
                Toast.makeText(context, "回调失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
