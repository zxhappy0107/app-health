package cn.anshirui.store.appdevelop.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName 用户积分
 * @Description TODO
 * @Author zhangxuan
 * @Date 16:23
 * Version 1.0
 **/
@Data
public class UserIntegral implements Serializable {

    private Long pr_id;//ID

    private Integer user_id;//用户id

    private Integer point;//获取积分

    private Integer used;//使用积分

    private Date expire;//产生时间

}
