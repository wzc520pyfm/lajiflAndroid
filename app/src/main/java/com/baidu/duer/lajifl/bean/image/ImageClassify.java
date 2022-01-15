package com.baidu.duer.lajifl.bean.image;

import java.util.ArrayList;

/**
 * 图形识别接口返回信息
 * @param <T> 接口返回数据
 */
public class ImageClassify<T> {
    // 识别结果条数
    private int result_num;
    // 识别结果
    private ArrayList<T> result;
    // 标识id
    private long log_id;

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public ArrayList<T> getResult() {
        return result;
    }

    public void setResult(ArrayList<T> result) {
        this.result = result;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    @Override
    public String toString() {
        return "ImageClassify{" +
                "result_num=" + result_num +
                ", result=" + result +
                ", log_id=" + log_id +
                '}';
    }
}

