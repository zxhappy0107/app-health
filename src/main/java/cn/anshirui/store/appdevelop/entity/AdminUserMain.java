package cn.anshirui.store.appdevelop.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName 用户的基础信息
 * @Description TODO
 * @Author zhangxuan
 * @Date 11:15
 * Version 1.0
 **/
@Data
public class AdminUserMain implements Serializable {

    private Integer umid;

    private String user_name;

    private String user_sex;

    private String user_birthday;

    private Integer user_id_type;

    private String user_id;

    private Integer user_height;

    private Double user_weight;

    private Integer user_weight_type;

    private Integer user_marr;

    private Integer user_cul;

    private String user_occ;

    private Integer user_ins;

}
