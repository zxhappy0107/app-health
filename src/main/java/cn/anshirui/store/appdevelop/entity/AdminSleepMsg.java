package cn.anshirui.store.appdevelop.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName 用户的睡眠信息
 * @Description TODO
 * @Author zhangxuan
 * @Date 17:22
 * Version 1.0
 **/
@Data
@ToString
public class AdminSleepMsg implements Serializable {

    private Long ud_id;//ID

    private Integer user_id;//用户ID

    private Date time;//记录时间

    private Integer high_blood;//高血压

    private Integer sober_time;//轻睡

    private Integer light_time;//浅睡

    private Integer deep_time;//深睡

    private Integer dream_time;//做梦

    private Integer dream_count;//做梦次数

    private Integer breakpoint;

    private Date spinintime;//上床

    private Date getuptime;//起床

    private Integer breath_block;//呼吸阻塞

    private Integer level_bed;//离床

    private Integer level_time;//离床时间

    private Double risk;//风险指数

    private Integer step;//步数

    private Double blood_sugar;//血糖

    public Integer getSleepTime() {
        if (null != getSober_time() && null != getLight_time() && null != getDeep_time() && null != getDream_time()) {
            return Integer.valueOf(getSober_time()) + Integer.valueOf(getLight_time()) + Integer.valueOf(getDeep_time()) + Integer.valueOf(getDream_time());
        }
        return 0;
    }

}
