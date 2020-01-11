package cn.anshirui.store.appdevelop.api;

import lombok.Getter;

/**
 * @Author zhangxuan
 * @Description //TODO 健康预警
 * @Date 10:54 2019/12/17
 * @Param
 * @return
 **/
public enum LentivirusCode {

    BREANTH_FAIL(1 , "呼吸大数据异常"),

    HEART_FAIL(2 , "血压大数据异常");

    @Getter
    private int type;

    @Getter
    private String msg;

    private LentivirusCode (int type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public static String getMsg(int type){
        for (LentivirusCode len: LentivirusCode.values()) {
            if (type == len.getType()){
                return len.getMsg();
            }
        }
        return "未知";
    }

}
