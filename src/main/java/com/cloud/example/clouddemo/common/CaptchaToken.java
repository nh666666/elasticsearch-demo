package com.cloud.example.clouddemo.common;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CaptchaToken extends UsernamePasswordToken {

    //序列化ID
    private static final long serialVersionUID = -1L;
    //验证码
    private String captchaCode;
    private String userType;
    private LoginType loginType;

    /**
     * 构造函数
     * 用户名和密码是登录必须的,因此构造函数中包含两个字段
     */
    public CaptchaToken(String username,String password,String captchaCode, String userType, LoginType loginType) {
        super(username,password,false,"");
        this.captchaCode = captchaCode;
        this.userType = userType;
        this.loginType = loginType;
    }

    /**
     * 获取验证码
     */
    public String getCaptchaCode() {
        return captchaCode;
    }

    public String getUserType() {
        return userType;
    }

    public LoginType getLoginType() {
        return loginType;
    }
}
