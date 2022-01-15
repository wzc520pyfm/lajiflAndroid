package com.baidu.duer.lajifl.bean.oauth;

/**
 * 申请百度身份令牌接口返回信息
 *  - 关键是获取access_token
 */
public class Oauth {
    private String refresh_token;
    private int expires_in;
    private String session_key;
    // access_token---使用百度图像识别所必须的身份令牌
    private String access_token;
    private String scope;
    private String session_secret;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSession_secret() {
        return session_secret;
    }

    public void setSession_secret(String session_secret) {
        this.session_secret = session_secret;
    }

    @Override
    public String toString() {
        return "Oauth{" +
                "refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", session_key='" + session_key + '\'' +
                ", access_token='" + access_token + '\'' +
                ", scope='" + scope + '\'' +
                ", session_secret='" + session_secret + '\'' +
                '}';
    }
}
