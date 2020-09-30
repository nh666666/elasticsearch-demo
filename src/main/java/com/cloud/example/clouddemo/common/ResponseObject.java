package com.cloud.example.clouddemo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("返回值")
public class ResponseObject<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码 0=成功,1失败")
    private Integer code;
    @ApiModelProperty(value = "状态码描述")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public ResponseObject() {
    }
    /**
     * 默认操作成功
     * @param data
     */
    public ResponseObject(T data) {
        this.data = data;
        this.message = ResponseCodeEnum.SUCCESS.message;
        this.code = ResponseCodeEnum.SUCCESS.code;
    }
    public ResponseObject(T data, ResponseCodeEnum responseCodeEnum) {
        this.data = data;
        this.message = responseCodeEnum.getMessage();
        this.code = responseCodeEnum.getCode();
    }

    public ResponseObject(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 响应Code枚举。后续根据业务需要增加。
     */
    public enum ResponseCodeEnum {

        SUCCESS(0, "操作成功！"),
        ERROR(1, "操作失败！"),

        /**
         * 登录失败，请输入正确的账号或密码！
         */
        LOGIN_FAILED(10000, "登录失败，请输入正确的账号或密码！"),

        /**
         * 当请求中未携带Cookie时返回此消息。
         * 或
         * 当请求中Token等其他参数值校验失败时返回。
         */
        UNAUTHORIZED_STATUS(10001, "非法操作，请求未授权请重新认证！"),


        /**
         * 当请求中未携带必要的认证请求头或请求参数时，返回该响应码。
         * 常用于验证北向接口请求是否合法。
         */
        ILLEGAL_REQUEST(10002, "请求非法，缺少必要的请求信息！"),


        /**
         * 成功登陆
         */
        LOGIN_SUCCESSFUL(10003, "恭喜您已成功登录！"),


        /**
         * 验证码验证失败或验证码已经过期。
         */
        CAPTCHA_VALIDATE_FAILED(10004, "验证码校验失败或该验证码已过期！"),


        /**
         * SRS配置更新失败时，响应该消息。
         */
        SRS_CONFIG_WRITE_FAILED(10005, "操作失败，更新流媒体服务器配置时发生异常，请联系管理员！"),


        /**
         * 当根据用户id未找到用户记录时，返回该错误信息！
         */
        USER_NOT_FOUND(10006, "用户不存在，未找到用户信息！"),


        /**
         * 注册账号失败错误信息。
         */
        USER_REGISTER_FAILED(10007, "账号注册失败，请重试！"),


        /**
         * 当在Redis或其他会话存储库中会找到用户会话时返回该错误。
         * 该响应码一般不返回到页面。
         */
        USER_SESSION_NOT_FOUND(10008, "会话已过期，未能找到用户会话信息！"),


        /**
         * 未查询到应用信息。
         */
        CAN_NOT_FIND_APPLICATION(10009, "找不到应用信息"),


        /**
         * 账户已锁定。
         */
        ACCOUNT_LOCKED(10010, "账户已锁定！"),


        /**
         * 账户已禁用。
         */
        ACCOUNT_DISABLED(10011, "账户已禁用！"),


        /**
         * 流禁用后操作时提示此信息。
         */
        STREAM_DISABLED(10012, "操作失败，当前流已经被禁用，请启用后重试！"),


        /**
         * 当前空间下拉流任务设置的分发配置已存在
         */
        STREAM_INGEST_TASK_EXISTED(10013, "当前空间下拉流任务设置的分发配置已存在，请更换App Name或Stream Name"),


        /**
         * 当前应用下，空间名已存在
         */
        ZONE_NAME_EXISTED(10014, "当前空间名称已存在，请更换名称。"),


        /**
         * 域名已存在
         */
        ZONE_DOMAIN_EXISTED(10015, "当前空间域名已存在，请更换。"),


        /**
         * 空间不存在
         */
        ZONE_DONT_EXISTED(10016, "操作失败，空间不存在。"),


        /**
         * 空间的回调不存在
         */
        ZONE_CALLBACK_DONT_EXISTED(10017, "操作失败，回调地址不存在。"),


        /**
         * 空间录制任务不存在
         */
        ZONE_DVR_DONT_EXISTED(10018, "操作失败，录制任务不存在。"),


        /**
         * 本地配置，不存在对应的vhost
         */
        VHOST_SRS_DONT_EXISTED(10019, "操作失败，配置不存在该vhost，请重新输入。"),


        /**
         * 本地配置，该子配置已存在
         */
        VHOST_SUBCONFIG_EXISTED(10020, "操作失败，该子配置已存在。"),


        /**
         * 本地配置，该子配置已存在
         */
        TIME_SETTING_ERROR(10021, "时间设置错误，请输入正确的时间范围。"),

        /**
         * 当短信发送失败时跑出该异常。
         */
        SMS_SEND_FAILED(10022, "短信发送失败，请重试。"),


        /**
         * 当邮件发送失败时跑出该异常。
         */
        EMAIL_SEND_FAILED(10023, "邮件发送失败，请重试。"),


        /**
         * 文件上传失败，请重试。
         */
        FILE_UPLOAD_FAILED(10024, "上传失败，操作时发生错误，请重试！"),


        /**
         * 文件下载失败，请重试。
         */
        FILE_DOWNLOAD_FAILED(10025, "文件下载失败，操作时发生错误，请重试！"),

        DEVICElIST_NOT_FOUND(10026, "没有找到设备列表信息"),

        DEVICEDETAIL_NOT_FOUND(10027, "查询设备详情失败"),

        DEVICE_DELETE_FAIL(10028, "删除设备失败"),

        DEVICE_ENABLE_FAIL(10029, "修改设备启用禁用状态失败"),

        GATEWAY_NOT_EXIST(10030, "选择的网关不存在"),

        GATEWAY_NOT_SELECT(10031, "该产品协议类型为通过网关接入，设备创建未选择接入网关信息"),

        NAME_USER_EXSIST(10032, "该用户下已存在该名称"),

        DEVICESN_USER_EXSIST(10033, "该用户下已存在该设备SN"),

        CHILDDEVICE_NOT_EXSIST(10034, "当前设备无子设备信息"),

        UPWARD_NOT_EXSIST(10035, "该设备无上行设备"),

        DOWNWARD_NOT_EXSIST(10036, "该设备无下行设备"),

        DEVICE_NOT_EXSIST(10037, "该应用和产品下无设备信息"),

        SEARIAL_NOT_EXSIST(10038, "设备序列号不存在"),

        GATEWAY_INTERFACE_NOT_SET(10039, "当前网关未配置接口信息"),

        DEVICEID_NOT_EXSIST(10040, "设备ID不存在"),

        /**
         * 所有未被catch的异常，都会返回这个响应码。
         */

        INTERNAL_SERVER_ERROR(10500, "服务器内部错误，请联系管理员！"),

        /**
         * 服务超时（服务挂掉）
         */
        SERVICE_TIMEOUT(10501, "服务响应超时"),

        /**
         * 用户访问接口超过限制次数
         */
        USER_EXCEEDING_THE_LIMIT(10503, "已超过接口的访问限定次数，请联系客服");

        private int code;
        private String message;

        ResponseCodeEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
