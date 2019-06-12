package com.zhkj.nettyserver.util.token;

/**
 * Des:token
 * ClassName: Token
 * Author: dengyi
 * Date: 2019-06-12 11:43
 */
public class Token  {
    //秘钥
    private String token;
    private Object principal;
    private Object credentials;


    public Token() {
        this("", "", "");
    }

    public Token(String token) {
        this(token, token, token);
    }

    public Token(String token, Object principal, Object credentials) {
        this.token = token;
        this.principal = principal;
        this.credentials = credentials;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
