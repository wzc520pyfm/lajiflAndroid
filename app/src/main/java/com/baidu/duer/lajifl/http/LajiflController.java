package com.baidu.duer.lajifl.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.duer.lajifl.R;
import com.baidu.duer.lajifl.api.ImageClassifyService;
import com.baidu.duer.lajifl.api.LajiflService;
import com.baidu.duer.lajifl.bean.image.ImageClassify;
import com.baidu.duer.lajifl.bean.image.Result;
import com.baidu.duer.lajifl.bean.lajifl.Data;
import com.baidu.duer.lajifl.bean.lajifl.Description;
import com.baidu.duer.lajifl.bean.lajifl.Lajifl;
import com.baidu.duer.lajifl.utils.SharedUtil;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 垃圾分类网络请求
 */
public class LajiflController {
    // 垃圾分类
    public static Lajifl<Data<Description>> getLajifl(Context context, String TAG, String m) throws IOException {
        // 构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                // 设置网络请求BaseUrl地址
                .baseUrl("https://api.muxiaoguo.cn/")
                // 设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 创建网络请求接口对象实例
        LajiflService lajiflService = retrofit.create(LajiflService.class);
        // 对发送请求进行封装
        Call<Lajifl<Data<Description>>> dataCall = lajiflService.getLajifl(context.getString(R.string.api_key),m);
        // 同步请求
        Response<Lajifl<Data<Description>>> data = dataCall.execute();
        Log.d(TAG, "请求成功：" + data);
        // 解析数据
        Lajifl<Data<Description>> body = data.body();
        assert body != null;
        Data<Description> resData = body.getData();
        Description description = resData.getDescription();
        return body;
    }
}
