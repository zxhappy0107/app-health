package cn.anshirui.store.appdevelop.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName 预警信息
 * @Description TODO
 * @Author zhangxuan
 * @Date 11:00
 * Version 1.0
 **/
@Data
public class WarnMessage implements Serializable {

    private Integer user_id;

    private Date starttime;

    private Date endtime;

    private Integer value;

    private Integer type;

}
