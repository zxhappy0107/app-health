package cn.anshirui.store.appdevelop.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 *@ClassName 用户实体类
 *@Description TODO
 *@Author zhangxuan
 *@Date 2019-11-15 10:00
 *Version 1.0
 **/
@Data
public class AdminUsers implements Serializable {

    private Integer user_id;

    private String user_account;

    private String user_password;

    private String user_name;

    private Date user_starttime;

    private Date user_logintime;

    private Integer umid;

}
