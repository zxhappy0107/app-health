package cn.anshirui.store.appdevelop.api;

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
    MSG_ERR(50004, "输入信息有误");

    public int code;

    public String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
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
