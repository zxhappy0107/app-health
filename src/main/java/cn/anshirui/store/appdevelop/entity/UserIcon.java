package cn.anshirui.store.appdevelop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName 用户头像
 * @Description TODO
 * @Author zhangxuan
 * @Date 9:56
 * Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIcon implements Serializable {

    private Integer icon_id;//图片id

    private Integer user_id;//用户id

    private String icon_url;//本地地址

    private String icon_web;//网络地址

}
