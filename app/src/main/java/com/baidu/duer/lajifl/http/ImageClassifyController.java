package com.baidu.duer.lajifl.http;

import android.content.Context;
import android.util.Log;

import com.baidu.duer.lajifl.api.ImageClassifyService;
import com.baidu.duer.lajifl.bean.image.ImageClassify;
import com.baidu.duer.lajifl.bean.image.Result;
import com.baidu.duer.lajifl.utils.SharedUtil;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 图像识别网络请求
 */
public class ImageClassifyController {

    // 图像识别
    public static ArrayList<Result> postImageClassify(Context context, String TAG, String image64)  {
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
        ImageClassifyService imageClassifyService = retrofit.create(ImageClassifyService.class);
        // 对发送请求进行封装
        Call<ImageClassify<Result>> dataCall = imageClassifyService.postImageClassify(sharedUtil.readShared("access_token",""),image64);
        // 同步请求
        Response<ImageClassify<Result>> data = null;
        try {
            data = dataCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "请求成功：" + data);
        // 解析数据
        ImageClassify<Result> body = data.body();
        assert body != null;
        ArrayList<Result> result = body.getResult();
        return result;
    }
}
