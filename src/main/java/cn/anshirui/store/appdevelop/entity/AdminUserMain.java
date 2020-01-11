package cn.anshirui.store.appdevelop.entity;

import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;

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

    private String user_id_type;

    private String user_id;

    private String user_height;

    private String user_weight;

    private String user_weight_type;

    private String user_marr;

    private String user_cul;

    private String user_occ;

    private String user_ins;

    private String user_urname;

    private String user_urphone;

}