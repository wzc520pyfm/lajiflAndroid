package com.baidu.duer.lajifl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.duer.lajifl.bean.image.Result;
import com.baidu.duer.lajifl.bean.lajifl.Data;
import com.baidu.duer.lajifl.bean.lajifl.Description;
import com.baidu.duer.lajifl.bean.lajifl.Lajifl;
import com.baidu.duer.lajifl.http.AccessTokenController;
import com.baidu.duer.lajifl.task.LajiflTask;
import com.baidu.duer.lajifl.utils.ImageUtil;
import com.baidu.duer.lajifl.utils.SharedUtil;
import com.wang.avi.AVLoadingIndicatorView;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements LajiflTask.OnGameListener {

    // ButterKnife 注入
    @BindView(R.id.takephotoTV)
    Button takephotoTV;
    @BindView(R.id.imageIV)
    ImageView imageIV;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.imageResult)
    ImageView imResult;
    @BindView(R.id.textViewResult)
    TextView tvResult;
    // 唯一标识
    final private String TAG = "MainActivity";
    // 上下文
    private Context context;
    // 图形识别结果
    private ArrayList<Result> imageResult;
    // 标识
    private final int CAMERA_REQUEST = 8888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        // 关闭加载动画--在线程处理时打开
        avi.smoothToHide();
        // 获取共享参数工具类单例
        // 共享参数工具类
        SharedUtil sharedUtil = SharedUtil.getInstance(context);
        // 获取图像处理工具类单列
        // 图像处理工具类
        ImageUtil imageUtil = ImageUtil.getInstance(context);

        // 如果共享参数中没有access_token则先获取access_token
        if(sharedUtil.readShared("access_token", "").equals("")) {
            AccessTokenController.getAccessToken(context,TAG);
        }

    }
    // 拍照点击事件处理
    @OnClick(R.id.takephotoTV)
    public void onClick() {
        // 清空识别结果
        imResult.setImageDrawable(null);
        tvResult.setText(null);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    // 拍照完成后将照片显示在imageView上并执行垃圾分类
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null) return;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageIV.setImageBitmap(photo);

            // 启动垃圾分类线程
            assert photo != null;
            startTask(photo);

        }
    }

    // 启动垃圾分类线程
    private void startTask(Bitmap photo) {
        // 创建垃圾分类线程
        LajiflTask asyncTask = new LajiflTask(context);
        // 设置垃圾分类线程加载监听器
        asyncTask.setOnGameListener(this);
        // 把垃圾分类线程加入到处理队列
        asyncTask.execute(photo);
    }

    // 在线程处理结束时触发
    @Override
    public void onGetLajiflInfo(Lajifl<Data<Description>> lajiflInfo, String result) {
        // 关闭加载动画
        avi.smoothToHide();
        // 允许点击按钮
        takephotoTV.setEnabled(true);
        if(lajiflInfo != null) {
            // 将垃圾分类结果显示处理
            Log.d(TAG,"垃圾分类结果为:" + lajiflInfo.getData().getType() + "  " + lajiflInfo.getData().getName());
//        Toast.makeText(context, result+"执行成功"+"垃圾分类结果为:" + lajiflInfo.getData().getType() + "  " + lajiflInfo.getData().getName(), Toast.LENGTH_SHORT).show();
            // 如果有分类结果则显示
            if(lajiflInfo.getData().getType() != null) {
                if(lajiflInfo.getData().getType().equals("干垃圾") || lajiflInfo.getData().getType().equals("装修垃圾") || lajiflInfo.getData().getType().equals("大件垃圾") || lajiflInfo.getData().getType().equals("不属于日常生活垃圾")) {
                    // 创建一个dialog对话框,用于显示垃圾分类结果
//                showDialog(R.color.ganlaji,R.drawable.ic_xml_ganlaji, lajiflInfo.getData().getType(), lajiflInfo.getData().getName());
                    showDialog(R.color.ganlaji,R.mipmap.ic_qitalaji_png, "其他垃圾", lajiflInfo.getData().getName());
                }
                if(lajiflInfo.getData().getType().equals("湿垃圾")) {
                    showDialog(R.color.shilaji,R.mipmap.ic_shilaji_png, "厨余垃圾", lajiflInfo.getData().getName());
                }
                if(lajiflInfo.getData().getType().equals("有害垃圾")) {
                    showDialog(R.color.youhailaji,R.mipmap.ic_youhailaji_png, lajiflInfo.getData().getType(), lajiflInfo.getData().getName());
                }
                if(lajiflInfo.getData().getType().equals("可回收垃圾")) {
                    showDialog(R.color.huishoulaji,R.mipmap.ic_huishoulaji_png, lajiflInfo.getData().getType(), lajiflInfo.getData().getName());
                }
                // 如果木小果垃圾分类key过期会直接闪退
            } else {
                // 无分类结果则提示用户未识别成功
                showDialog(R.color.colorGray,R.drawable.ic_xml_bad, "再试一次吧", "垃圾分类太难,我不会了");
            }
        } else {
            // 处理出错时显示
            showDialog(R.color.colorGray,R.drawable.ic_xml_bad, "出错了", "十分抱歉, 请联系管理员");
        }
    }

    // 在线程处理取消时触发
    @Override
    public void onCancel(String result) {
        Log.d(TAG,result+"取消");
        Toast.makeText(context, result+"取消", Toast.LENGTH_SHORT).show();
    }

    // 在线程处理过程中更新进度时触发
    @Override
    public void onUpdate(String request, int progress, int sub_progress) {

    }

    // 在线程处理开始时触发
    @Override
    public void onBegin(String request) {
        // 开启加载动画
        avi.smoothToShow();
        // 禁止点击按钮
        takephotoTV.setEnabled(false);
        Log.d(TAG,request+"开始");
        Toast.makeText(context, request+"开始", Toast.LENGTH_SHORT).show();
    }

    // 创建dialog对话框
    private void showDialog(int colorId, int iconId, String title, String message) {
        new LovelyStandardDialog(context, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(colorId) // 主体背景色
                .setButtonsColorRes(R.color.colorPrimaryDark) // 按钮颜色
                .setIcon(iconId) // icon
                .configureView(rootView -> { // 设置icon大小(实现自适应)
                    ImageView iconImage;
                    iconImage = (ImageView) rootView.findViewById(R.id.ld_icon);
                    ViewGroup.LayoutParams lp = iconImage.getLayoutParams();
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    iconImage.setLayoutParams(lp);
                    iconImage.setAdjustViewBounds(true);
                })
                .setTitle(title) // 标题
                .configureTitleView(titles -> titles.setTextSize(26)) // 设置标题文本大小
                .setMessage(message) // 文本内容
                .configureMessageView(messages -> messages.setTextSize(20)) // 设置文本大小
                .setPositiveButton(android.R.string.ok, v -> {
                    if(title.equals("厨余垃圾")) {
                        imResult.setImageResource(R.drawable.shilajitong);
                    }
                    if(title.equals("其他垃圾")) {
                        imResult.setImageResource(R.drawable.ganlajitong);
                    }
                    if(title.equals("有害垃圾")) {
                        imResult.setImageResource(R.drawable.youhailajitong);
                    }
                    if(title.equals("可回收垃圾")) {
                        imResult.setImageResource(R.drawable.huishoulajitong);
                    }
                    tvResult.setText(String.format("%s\n%s", title, message));
                    tvResult.setTextSize(20);
                }) // 确认按钮监听器
                .configureView(rootView -> { // 设置确认按钮文本大小
                    Button positiveButton;
                    positiveButton = (Button)rootView.findViewById(R.id.ld_btn_yes); // 可以通过id获取lovelyDialog的所有view对象(具体id可通过源码查找)
                    positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                })
                .setNegativeButton(android.R.string.no, null) // 取消按钮监听器
                .configureView(rootView -> { // 设置取消按钮文本大小
                    Button positiveButton;
                    positiveButton = (Button)rootView.findViewById(R.id.ld_btn_no);
                    positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                })
                .show(); // 显示dialog
    }

}