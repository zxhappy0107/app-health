package cn.anshirui.store.appdevelop.api;

import lombok.Getter;

public enum ResultCode {

    // 成功
    SUCCESS(200, "success"),

    // 失败
    FAIL(400, "fail"),

    // 未认证（签名错误）
    UNAUTHORIZED(401, "登陆异常"),

    // 接口不存在
    NOT_FOUND(404, "接口不存在"),

    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500, "内部错误"),

    //用户已存在
    USER_EXIST(50001, "用户已存在"),

    //验证码有误
    CODE_FAIL(50002, "验证码有误"),

    //用户不存在
    USER_UNEXIST(50003, "用户不存在"),

    //信息数据有误
    MSG_ERR(50004, "输入信息有误"),

    //图片信息异常
    ICON_FAIL(50005, "图片上传失败"),

    //已签到
    SIGN_FAIL(50006, "今日已签到"),

    //登录名或密码错误
    USER_LOGIN_ERROR(50007, "登录名或密码错误"),

    //当前设备已被绑定
    EQP_BIND_FAIL(50008, "当前设备已被绑定"),

    //设备编码不存在
    EQP_ID_FAIL(50009, "设备编码不存在"),

    //您已绑定WIFI设备
    BIND_WIFI(50010, "您已绑定WIFI设备"),

    //您已绑定4G设备
    BING_4G(50011, "您已绑定4G设备"),

    //您已绑定同等设备
    USER_BINDS(50012, "您已绑定同等设备"),

    //请在4G页面绑定设备
    BING_NO_4G(50013, "请在4G页面绑定设备"),

    //请在WIFI页面绑定设备
    BING_NO_WIFI(50014, "请在WIFI页面绑定设备"),

    //请暂时没有绑定任何设备
    BING_NO(50015, "请暂时没有绑定任何设备");

    @Getter
    public int code;

    @Getter
    public String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String msg(int code){
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode() == code){
                return resultCode.getMsg();
            }
        }
        return "内部错误";
    }

}
