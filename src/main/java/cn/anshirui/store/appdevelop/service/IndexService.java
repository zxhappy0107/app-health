package cn.anshirui.store.appdevelop.service;

import cn.anshirui.store.appdevelop.entity.AdminBodyMsg;
import javafx.beans.binding.ObjectBinding;
import org.apache.ibatis.annotations.Insert;

import java.util.Map;

public interface IndexService {

    /**
     * @Author zhangxuan
     * @Description //TODO 首页信息
     * @Date 16:15 2019/12/12
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> indexShowMsg(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 睡眠信息
     * @Date 16:16 2019/12/12
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> indexSleepMsg(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 日睡眠信息
     * @Date 16:16 2019/12/12
     * @Param [userId, time]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> indexSleepDay(Integer userId, String time);

    /**
     * @Author zhangxuan
     * @Description //TODO web日睡眠信息
     * @Date 15:23 2019/12/30
     * @Param [userId, time]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> webIndexSleepDay(Integer userId, String time);

    /**
     * @Author zhangxuan
     * @Description //TODO 周睡眠信息
     * @Date 16:16 2019/12/12
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> indexSleepWeek(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO web周睡眠信息
     * @Date 15:55 2019/12/30
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> webIndexSleepWeek(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 更新用户身体信息
     * @Date 16:15 2019/12/12
     * @Param [userId, type]
     * @return int
     **/
    public void updateAdminBodyMsg(AdminBodyMsg body);

    /**
     * @Author zhangxuan
     * @Description //TODO 周健康风险
     * @Date 9:56 2019/12/13
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> weekHealthRisk(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 月健康风险
     * @Date 9:56 2019/12/13
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> monthHealthRisk(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 血压查询
     * @Date 10:07 2019/12/15
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/
    Map<String, Integer> blookPressureToday(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 血糖查询
     * @Date 11:04 2019/12/15
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> blookSugarToday(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 体重查询
     * @Date 11:04 2019/12/15
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> weightToday(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 温度查询
     * @Date 11:05 2019/12/15
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> heatToday(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 心率查询
     * @Date 9:47 2019/12/26
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/
    Map<String, Integer> heartToday(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 呼吸查询
     * @Date 9:47 2019/12/26
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/
    Map<String, Integer> breathToday(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 步数查询
     * @Date 9:49 2019/12/26
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> stepToday(Integer userId);
    
    /**
     * @Author zhangxuan
     * @Description //TODO 运动日分析
     * @Date 15:36 2019/12/26
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> stepUserDay(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 运动周分析
     * @Date 15:36 2019/12/26
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> stepUserWeek(Integer userId);

}
