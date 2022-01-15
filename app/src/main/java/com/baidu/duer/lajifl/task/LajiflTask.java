package com.baidu.duer.lajifl.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;


import com.baidu.duer.lajifl.bean.image.Result;
import com.baidu.duer.lajifl.bean.lajifl.Data;
import com.baidu.duer.lajifl.bean.lajifl.Description;
import com.baidu.duer.lajifl.bean.lajifl.Lajifl;
import com.baidu.duer.lajifl.http.AccessTokenController;
import com.baidu.duer.lajifl.http.ImageClassifyController;
import com.baidu.duer.lajifl.http.LajiflController;
import com.baidu.duer.lajifl.utils.ImageUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 垃圾分类线程
 */
public class LajiflTask extends AsyncTask<Bitmap,Integer, Lajifl<Data<Description>>> {
    // 唯一标识
    private final static String TAG="LajiflTask";
    // 任务标识
    private String lajifl = "垃圾分类任务";
    //上下文(使用了注解来忽略语法检测) -- 这不是一个好的做法,如此使用context会导致context泄露,应尽量避免在线程中使用context对象(试想一下,UI视图已经销毁了但是asyncTask仍在使用context会发生什么?这将导致context不能被正常回收!)
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LajiflTask(Context context) {
        super();
        this.context = context;
    }

    // 后台执行线程---params对应异步任务参数的第一个参数即传入参数,可以是自定义的类型
    protected Lajifl<Data<Description>> doInBackground(Bitmap... params) {

        // 获取参数
        Bitmap photo = params[0];
        // 执行图像识别接口, 并将识别结果保存在imageResult
        ArrayList<Result> imageResult = ImageClassifyController.postImageClassify(context, TAG, ImageUtil.byte2Base64(ImageUtil.bitmap2Byte(photo)));
        if(imageResult == null) {
            // 如果返回数据为空, 可能是token过期,重新获取一次token
            AccessTokenController.getAccessToken(context,TAG);
        }
        Lajifl<Data<Description>> result = new Lajifl<>();
        // 如果图像识别处理返回值不为空则执行垃圾分类
        assert imageResult != null;
        for(int i = 0; i < imageResult.size(); i++) {
            try {
                result = LajiflController.getLajifl(context,TAG, imageResult.get(i).getKeyword());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(result.getCode().equals("200")) {
                break;
            }
            try {
                result = LajiflController.getLajifl(context,TAG, imageResult.get(i).getRoo().split("-")[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(result.getCode().equals("200")) {
                break;
            }
        }
        return result;
    }

    //准备启动线程
    protected void onPreExecute(){
        //触发监听器的开始事件
        mListener.onBegin(lajifl);
    }
    //线程在通报处理进展
    protected void onProgressUpdate(Integer... values){
        //触发监听器的进度更新事件
        mListener.onUpdate(lajifl,values[0],0);
    }

    //线程已经完成处理
    protected void onPostExecute(Lajifl<Data<Description>> lajiflInfo){
        //HTTP调用完毕,触发监听器的得到信息事件
        mListener.onGetLajiflInfo(lajiflInfo,lajifl);
    }
    //线程已经取消
    protected void onCancelled(String result){
        //触发监听器的取消事件
        mListener.onCancel(result);
    }

    //声明一个请求信息的监听器对象
    private OnGameListener mListener;

    //设置查询详细地址的监听器
    public void setOnGameListener(OnGameListener listener){
        mListener=listener;
    }

    //定义一个请求信息的监听器接口
    public interface OnGameListener{//此接口在主activity中实现
        //在线程结束时触发
        void onGetLajiflInfo(Lajifl<Data<Description>> lajiflInfo,String result);
        //在线程处理取消时触发
        void onCancel(String result);
        //在线程处理过程中更新进度时触发
        void onUpdate(String request,int progress,int sub_progress);
        //在线程处理开始时触发
        void onBegin(String request);
    }
}
