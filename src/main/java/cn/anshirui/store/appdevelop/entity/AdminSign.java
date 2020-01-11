package cn.anshirui.store.appdevelop.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName 用户签到
 * @Description TODO
 * @Author zhangxuan
 * @Date 16:56
 * Version 1.0
 **/
@Data
public class AdminSign implements Serializable {

    private Long u_signid;

    private Integer user_id;

    private String sign_time;

    private Integer grade;

}
