package com.cloud.example.clouddemo.common;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义验证码为空异常
 */
public class CaptchaEmptyException extends AuthenticationException {
    private static final long serialVersionUID = 1L;
}
