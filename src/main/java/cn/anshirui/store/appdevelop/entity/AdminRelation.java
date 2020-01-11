package cn.anshirui.store.appdevelop.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName 亲情关联实体
 * @Description TODO
 * @Author zhangxuan
 * @Date 16:17
 * Version 1.0
 **/
@Data
public class AdminRelation implements Serializable {

    private Long ur_id;

    private Integer user_id;

    private Integer urelation_id;

    private String relation;

    private String urelation_name;

}
