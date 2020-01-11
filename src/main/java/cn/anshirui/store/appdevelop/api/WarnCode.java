package cn.anshirui.store.appdevelop.api;

import lombok.Getter;

/**
 * @Author zhangxuan
 * @Description //TODO 一般预警
 * @Date 10:53 2019/12/17
 * @Param
 * @return
 **/
public enum WarnCode {

    LEAVE_BED(1 , "离床"),

    NO_SLEEP(2, "没有睡"),

    SLEEP_LATE(3, "睡眠过晚"),

    DEEP_LACK(4, "深睡不足"),

    SLEEP_SHORT(5, "睡眠过短"),

    LEAVE_MORE(6, "离床过多");

    @Getter
    private int type;

    @Getter
    private String msg;

    private WarnCode(int type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public static String getMsg(int type){
        for (WarnCode code: WarnCode.values()) {
            if (type == code.getType()){
                return code.getMsg();
            }
        }
        return "未知";
    }

}
