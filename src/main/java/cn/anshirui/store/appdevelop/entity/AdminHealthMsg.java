package cn.anshirui.store.appdevelop.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName 基础健康信息
 * @Description TODO
 * @Author zhangxuan
 * @Date 16:14
 * Version 1.0
 **/
@Data
public class AdminHealthMsg implements Serializable {

    private Long ub_id;//ID

    private Integer user_id;//用户ID

    private Date h_datetime;//录入时间

    private Integer h_heartave;//心率平均

    private Integer h_heartmax;//心率最大

    private Integer h_heartmin;//心率最小

    private Integer h_breathave;//呼吸平均

    private Integer h_breathmax;//呼吸最大

    private Integer h_breathmin;//呼吸最小

    private Integer h_movenum;//体动

    private Integer h_index;//得分

}
