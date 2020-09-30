package com.cloud.example.clouddemo.common;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义验证码错误异常
 * AuthenticationException为shiro认证错误的异常，不同错误类型继承该异常即可
 */
public class CaptchaErrorException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

}
