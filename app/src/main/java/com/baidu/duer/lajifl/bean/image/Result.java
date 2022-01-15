package com.baidu.duer.lajifl.bean.image;

/**
 * 图形识别接口返回数据(result部分)
 */
public class Result {
    // 关键字
    private String keyword;
    // 识别相似度
    private float score;
    // 归属类别
    private String root;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getRoo() {
        return root;
    }

    public void setRoo(String root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "Result{" +
                "keyword='" + keyword + '\'' +
                ", score=" + score +
                ", roo='" + root + '\'' +
                '}';
    }
}