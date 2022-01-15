package com.baidu.duer.lajifl.bean.lajifl;

/**
 * 垃圾分类接口返回信息
 * @param <T> 接口返回数据
 */
public class Lajifl<T> {
    // 状态码 -- 成功时是 字符串200, 识别时是int
    private String code;
    // 返回信息--成功则是 success
    private String msg;
    // 返回数据
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Lajifl{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
