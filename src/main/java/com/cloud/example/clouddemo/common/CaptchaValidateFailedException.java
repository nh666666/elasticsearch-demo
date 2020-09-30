package com.cloud.example.clouddemo.common;

public class CaptchaValidateFailedException extends RuntimeException {

    public CaptchaValidateFailedException(){

    }

    public CaptchaValidateFailedException(String message){
        super(message);
    }

    public CaptchaValidateFailedException(String message,Throwable cause){
        super(message,cause);
    }

}
