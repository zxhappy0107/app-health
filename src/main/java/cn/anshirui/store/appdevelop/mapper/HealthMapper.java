package cn.anshirui.store.appdevelop.mapper;

import cn.anshirui.store.appdevelop.entity.AdminBodyMsg;
import cn.anshirui.store.appdevelop.entity.AdminHealthMsg;
import cn.anshirui.store.appdevelop.entity.AdminSleepMsg;
import cn.anshirui.store.appdevelop.entity.WarnMessage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author zhangxuan
 * @Description //TODO 健康数据接口
 * @Date 15:28 2019/12/9
 * @Param
 * @return
 **/
@Mapper
@CacheNamespace(eviction = FifoCache.class)
@Component(value = "healthMapper")
public interface HealthMapper {

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户的风险数据
     * @Date 17:38 2019/12/16
     * @Param [userId]
     * @return java.lang.Double
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT SUM(risk) FROM u_risk WHERE user_id = #{user_id} AND time BETWEEN DATE_FORMAT(DATE_ADD(NOW(),INTERVAL -30 Day),'%Y-%m-%d') AND DATE_FORMAT(NOW(),'%Y-%m-%d') GROUP BY user_id")
    Double selectUserRisk(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 询用户当天睡眠数据
     * @Date 17:37 2019/12/16
     * @Param [userId]
     * @return cn.anshirui.store.appdevelop.entity.AdminSleepMsg
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT high_blood,sober_time,light_time,deep_time,dream_time,dream_count,spinintime,getuptime,breath_block,level_bed,level_time,step,blood_sugar FROM u_dream_data WHERE user_id = #{user_id} AND time = DATE_FORMAT(NOW(),'%Y-%m-%d') AND high_blood IS NOT NULL")
    AdminSleepMsg selectHealthMsg(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询健康数据是否存在
     * @Date 11:01 2019/12/27
     * @Param [userId]
     * @return java.lang.Long
     **/
    @Select("SELECT ud_id FROM u_dream_data WHERE user_id = #{user_id} AND time = DATE_FORMAT(NOW(),'%Y-%m-%d') LIMIT 1")
    Long selectHealthExist(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户当天设备基础数据
     * @Date 17:37 2019/12/16
     * @Param [userId]
     * @return cn.anshirui.store.appdevelop.entity.AdminHealthMsg
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT h_heartave,h_heartmax,h_heartmin,h_breathave,h_breathmax,h_breathmin,h_movenum,h_index FROM u_body_data WHERE user_id = #{user_id} AND h_datetime = DATE_FORMAT(NOW(),'%Y-%m-%d')")
    AdminHealthMsg selectHealthBas(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户的睡眠数据
     * @Date 17:36 2019/12/16
     * @Param [map]
     * @return cn.anshirui.store.appdevelop.entity.AdminSleepMsg
     **/
    @Select("SELECT high_blood,sober_time,light_time,deep_time,dream_time,dream_count,spinintime,getuptime,breath_block,level_bed,level_time,step,blood_sugar FROM u_dream_data WHERE user_id = #{user_id} AND time = #{time} AND high_blood IS NOT NULL")
    AdminSleepMsg selectHealthMsgMap(Map<String, String> map);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户基础健康数据
     * @Date 17:36 2019/12/16
     * @Param [map]
     * @return cn.anshirui.store.appdevelop.entity.AdminHealthMsg
     **/
    @Select("SELECT h_heartave,h_heartmax,h_heartmin,h_breathave,h_breathmax,h_breathmin,h_movenum,h_index FROM u_body_data WHERE user_id = #{user_id} AND h_datetime = #{time}")
    AdminHealthMsg selectHealthBasMap(Map<String, String> map);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询健康指数
     * @Date 17:36 2019/12/16
     * @Param [userId, time]
     * @return java.lang.Integer
     **/
    @Select("SELECT h_index FROM u_body_data WHERE user_id = #{user_id} AND h_datetime = #{h_datetime} LIMIT 1")
    Integer selectHealthIndex(@Param("user_id") int userId, @Param("h_datetime") String time);

    /**
     * @Author zhangxuan
     * @Description //TODO 更新血糖与步数
     * @Date 17:35 2019/12/16
     * @Param [userId, step, sugar]
     * @return void
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    @Update("<script>" +
            "UPDATE u_dream_data" +
            "<set>" +
            "     <if test = 'step != null'>" +
            "         step = #{step}," +
            "     </if>" +
            "     <if test = 'blood_sugar != null'>" +
            "         blood_sugar = #{blood_sugar}," +
            "     </if>" +
            "</set>" +
            "WHERE user_id = #{user_id} AND time = DATE_FORMAT(NOW(),'%Y-%m-%d')" +
            "</script>")
    void updateAdminStepBloodSugar(@Param("user_id") int userId, @Param("step") Integer step, @Param("blood_sugar") Double sugar);

    /**
     * @Author zhangxuan
     * @Description //TODO 添加用户的步数
     * @Date 17:39 2019/12/25
     * @Param [userId, step, sugar]
     * @return void
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    @Insert("INSERT INTO u_dream_data (user_id,time,step) VALUES (#{user_id},NOW(),#{step})")
    void insertSleepByStep(@Param("user_id") int userId, @Param("step") Integer step);

    /**
     * @Author zhangxuan
     * @Description //TODO 添加用户的健康状态
     * @Date 17:35 2019/12/16
     * @Param [bodyMsg]
     * @return void
     **/
    @Insert("<script>" +
            "INSERT INTO u_health_data" +
            "<trim prefix = '(' suffix= ')' suffixOverrides= ',' >" +
            "     <if test = 'user_id != null'>" +
            "         user_id," +
            "     </if>" +
            "     <if test = 'low_pressure != null'>" +
            "         low_pressure," +
            "     </if>" +
            "     <if test = 'high_pressure != null'>" +
            "         high_pressure," +
            "     </if>" +
            "     <if test = 'empty_sugar != null'>" +
            "         empty_sugar," +
            "     </if>" +
            "     <if test = 'dinner_sugar != null'>" +
            "         dinner_sugar," +
            "     </if>" +
            "     <if test = 'weight != null'>" +
            "         weight," +
            "     </if>" +
            "     <if test = 'bodyfat != null'>" +
            "         bodyfat," +
            "     </if>" +
            "     <if test = 'bodyheat != null'>" +
            "         bodyheat," +
            "     </if>" +
            "     time," +
            "</trim>" +
            "<trim prefix = ' VALUES (' suffix= ')' suffixOverrides= ',' >" +
            "     <if test = 'user_id != null'>" +
            "         #{user_id}," +
            "     </if>" +
            "     <if test = 'low_pressure != null'>" +
            "         #{low_pressure}," +
            "     </if>" +
            "     <if test = 'high_pressure != null'>" +
            "         #{high_pressure}," +
            "     </if>" +
            "     <if test = 'empty_sugar != null'>" +
            "         #{empty_sugar}," +
            "     </if>" +
            "     <if test = 'dinner_sugar != null'>" +
            "         #{dinner_sugar}," +
            "     </if>" +
            "     <if test = 'weight != null'>" +
            "         #{weight}," +
            "     </if>" +
            "     <if test = 'bodyfat != null'>" +
            "         #{bodyfat}," +
            "     </if>" +
            "     <if test = 'bodyheat != null'>" +
            "         #{bodyheat}," +
            "     </if>" +
            "     NOW()," +
            "</trim>" +
            "</script>"
    )
    void insertAdminBodyMsg(AdminBodyMsg bodyMsg);

    /**
     * @Author zhangxuan
     * @Description //TODO 更新用户健康状态
     * @Date 17:34 2019/12/16
     * @Param [bodyMsg]
     * @return void
     **/
    @Update("<script>" +
            "UPDATE u_health_data" +
            "<set>" +
            "     <if test = 'low_pressure != null'>" +
            "         low_pressure = #{low_pressure}," +
            "     </if>" +
            "     <if test = 'high_pressure != null'>" +
            "         high_pressure = #{high_pressure}," +
            "     </if>" +
            "     <if test = 'empty_sugar != null'>" +
            "         empty_sugar = #{empty_sugar}," +
            "     </if>" +
            "     <if test = 'dinner_sugar != null'>" +
            "         dinner_sugar = #{dinner_sugar}," +
            "     </if>" +
            "     <if test = 'weight != null'>" +
            "         weight = #{weight}," +
            "     </if>" +
            "     <if test = 'bodyfat != null'>" +
            "         bodyfat = #{bodyfat}," +
            "     </if>" +
            "     <if test = 'bodyheat != null'>" +
            "         bodyheat = #{bodyheat}," +
            "     </if>" +
            "</set>" +
            "WHERE user_id = #{user_id} AND time = DATE_FORMAT(NOW(),'%Y-%m-%d')" +
            "</script>"
    )
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    void updateAdminBodyMsg(AdminBodyMsg bodyMsg);

    /**
     * @Author zhangxuan
     * @Description //TODO 用户的当天健康数据
     * @Date 17:34 2019/12/16
     * @Param [userId]
     * @return cn.anshirui.store.appdevelop.entity.AdminBodyMsg
     **/
    @Select("SELECT low_pressure,high_pressure,empty_sugar,dinner_sugar,weight,bodyfat,bodyheat FROM u_health_data WHERE user_id = #{user_id} AND time = DATE_FORMAT(NOW(),'%Y-%m-%d')")
    AdminBodyMsg selectBodyToday(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 用户的健康数据查询时间
     * @Date 17:33 2019/12/16
     * @Param [userId, time]
     * @return cn.anshirui.store.appdevelop.entity.AdminBodyMsg
     **/
    @Select("SELECT low_pressure,high_pressure,empty_sugar,dinner_sugar,weight,bodyfat,bodyheat FROM u_health_data WHERE user_id = #{user_id} AND time = #{time}")
    AdminBodyMsg selectBodyTime(@Param("user_id") Integer userId, @Param("time") String time);

    /**
     * @Author zhangxuan
     * @Description //TODO 汇总用户风险数值
     * @Date 17:33 2019/12/16
     * @Param [userId, start, end, type]
     * @return java.lang.Integer
     **/
    @Select("<script>" +
            "SELECT COUNT(ud_id) FROM u_dream_data WHERE time BETWEEN #{start} AND #{end} AND user_id = #{user_id}" +
            "     <if test = 'type == 1'>" +
            "         AND high_blood > 0" +
            "     </if>" +
            "     <if test = 'type == 2'>" +
            "         AND high_blood = 0" +
            "     </if>" +
            "     <if test = 'type == 3'>" +
            "         AND breath_block > 0" +
            "     </if>" +
            "     <if test = 'type == 4'>" +
            "         AND breath_block = 0" +
            "     </if>" +
            "</script>"
    )
    Integer selectUnDay(@Param("user_id") Integer userId, @Param("start") String start, @Param("end") String end, @Param("type") int type);

    /**
     * @Author zhangxuan
     * @Description //TODO 用户的平均体温
     * @Date 17:33 2019/12/16
     * @Param [userId]
     * @return java.lang.Double
     **/
    @Select("SELECT (SUM(bodyheat) / COUNT(user_id)) FROM u_health_data WHERE user_id = #{user_id} AND bodyheat IS NOT NULL GROUP BY user_id")
    Double selectAveHeat(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户设备基础数据
     * @Date 17:32 2019/12/16
     * @Param [userId, table, start, end, stat, timename]
     * @return java.lang.Integer
     **/
    @Options(statementType = StatementType.STATEMENT)
    @Select("SELECT COUNT(${stat}) FROM ${tableName} WHERE user_id = ${user_id} AND ${timename} BETWEEN '${start}' AND '${end}' LIMIT 1")
    Integer selectHeart(@Param("user_id") Integer userId, @Param("tableName") String table,@Param("start") String start,@Param("end") String end,@Param("stat") String stat,@Param("timename") String timename);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询单次预警
     * @Date 11:20 2019/12/17
     * @Param [userId, table]
     * @return cn.anshirui.store.appdevelop.entity.WarnMessage
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT user_id,starttime,endtime,`value`,type FROM u_warn_data WHERE user_id = #{user_id} AND starttime " +
            "BETWEEN DATE_FORMAT(DATE_ADD(NOW(),INTERVAL -30 Day),'%Y-%m-%d') AND DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 1 Day),'%Y-%m-%d') " +
            "ORDER BY starttime DESC LIMIT 1")
    WarnMessage selectWarnOne(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询单次健康预警
     * @Date 10:13 2019/12/18
     * @Param [userId, type]
     * @return cn.anshirui.store.appdevelop.entity.WarnMessage
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT user_id,starttime,endtime,`value`,type FROM u_lentivirus_data WHERE user_id = #{user_id} " +
            "AND type = #{type} " +
            "AND starttime BETWEEN DATE_FORMAT(DATE_ADD(NOW(),INTERVAL -30 Day),'%Y-%m-%d') AND DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 1 Day),'%Y-%m-%d') " +
            "ORDER BY starttime DESC LIMIT 1")
    WarnMessage selectWarnHealthOne(@Param("user_id") Integer userId, @Param("type") int type);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询一般预警
     * @Date 16:36 2019/12/17
     * @Param [userId, type]
     * @return java.util.List<cn.anshirui.store.appdevelop.entity.WarnMessage>
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT user_id,starttime,endtime,`value`,type FROM u_warn_data WHERE user_id = #{user_id} AND  " +
            "type = #{type} " +
            "ORDER BY starttime DESC")
    List<WarnMessage> selectWarnMore(@Param("user_id") Integer userId, @Param("type") int type);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询健康预警
     * @Date 16:36 2019/12/17
     * @Param [userId, type]
     * @return java.util.List<cn.anshirui.store.appdevelop.entity.WarnMessage>
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT user_id,starttime,endtime,`value`,type FROM u_lentivirus_data WHERE user_id = #{user_id} AND  " +
            "type = #{type} " +
            "ORDER BY starttime DESC")
    List<WarnMessage> selectWarnHealthMore(@Param("user_id") Integer userId, @Param("type") int type);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询步数
     * @Date 9:54 2019/12/26
     * @Param [userId]
     * @return java.lang.Integer
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    @Select("SELECT step FROM u_dream_data WHERE user_id = #{user_id} AND time = DATE_FORMAT(NOW(),'%Y-%m-%d') LIMIT 1")
    Long selectUserStepToday(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 根据日期查询步数
     * @Date 17:00 2019/12/26
     * @Param [userId]
     * @return java.lang.Long
     **/
    @Select("SELECT step FROM u_dream_data WHERE user_id = #{user_id} AND time = #{time} LIMIT 1")
    Long selectUserStepDate(@Param("user_id") Integer userId, @Param("time") String date);

    /**
     * @Author zhangxuan
     * @Description //TODO
     * @Date 17:20 2019/12/26
     * @Param []
     * @return java.lang.Long
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    @Select("SELECT SUM(step) / COUNT(user_id) FROM u_dream_data WHERE user_id = #{user_id} AND time BETWEEN #{start} AND #{end} AND step IS NOT NULL GROUP BY user_id")
    Long selectUserStepDateAndDate(@Param("user_id") Integer userId, @Param("start") String start, @Param("end") String end);

}
