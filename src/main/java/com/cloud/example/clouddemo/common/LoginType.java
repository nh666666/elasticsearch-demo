package com.cloud.example.clouddemo.common;
public enum  LoginType {
    PASSWORD("password"), // 密码登录
    NOPASSWD("nopassword"); // 免密登录

    private String name;// 状态值

    private LoginType(String name) {
        this.name = name;
    }
    public String getName () {
        return name;
    }
}