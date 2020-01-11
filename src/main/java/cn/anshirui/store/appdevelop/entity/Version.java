package cn.anshirui.store.appdevelop.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName 版本号
 * @Description TODO
 * @Author zhangxuan
 * @Date 15:58
 * Version 1.0
 **/
@Data
public class Version implements Serializable {

    private String system;

    private String version;

    private String url;

}
