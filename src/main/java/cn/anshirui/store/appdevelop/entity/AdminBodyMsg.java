package cn.anshirui.store.appdevelop.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName 用户的身体信息
 * @Description TODO
 * @Author zhangxuan
 * @Date 14:50
 * Version 1.0
 **/
@Data
@ToString
public class AdminBodyMsg implements Serializable {

    private Long u_healid;//id

    private Integer user_id;//用户id

    private Integer low_pressure;//低压

    private Integer high_pressure;//高压

    private Double empty_sugar;//空腹血糖

    private Double dinner_sugar;//餐后血糖

    private Double weight;//体重

    private Integer bodyfat;//体脂

    private Double bodyheat;//体温

    private Date time;//时间

}
