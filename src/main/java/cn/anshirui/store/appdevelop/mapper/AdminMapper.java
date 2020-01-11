package cn.anshirui.store.appdevelop.mapper;

import cn.anshirui.store.appdevelop.entity.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author zx
 * @Title AdminMapper
 * @Description
 * @data 2019/11/27 14:55
 */
@Mapper
@CacheNamespace(eviction = LruCache.class, flushInterval = 60000L, size = 1024, readWrite = true)
@Component(value = "adminMapper")
public interface AdminMapper {

    /**
     * @Author zhangxuan
     * @Description //根据id查询用户
     * @Date 15:45 2019/11/27
     * @Param [id]
     * @return cn.anshirui.store.appdevelop.entity.AdminUsers
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 6000)
    @Select("SELECT user_id,user_account,user_password,user_name,umid FROM u_users WHERE user_id = #{id}")
    AdminUsers selectUserById(@Param("id") int id);

    /**
     * @Author zhangxuan
     * @Description //TODO 根据账户查询用户
     * @Date 11:30 2019/12/5
     * @Param [username]
     * @return cn.anshirui.store.appdevelop.entity.AdminUsers
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, timeout = 6000)
    @Select("SELECT user_id,user_account,user_password,user_name,umid FROM u_users WHERE user_account = #{user_account}")
    AdminUsers selectUserByUserAccount(@Param("user_account") String username);

    /**
     * @Author zhangxuan
     * @Description //TODO 更新用户登录信息
     * @Date 11:19 2019/12/16
     * @Param [adminUsers]
     * @return void
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    @UpdateProvider(type = AdminMapperSql.class, method = "update")
    void updateUser(AdminUsers adminUsers);

    /**
     * @Author zhangxuan
     * @Description //TODO 通过手机号查询用户id
     * @Date 11:20 2019/12/16
     * @Param [user_account]
     * @return java.lang.Integer
     **/
    @Select("SELECT user_id FROM u_users WHERE user_account = #{user_account}")
    Integer selectUserIdByPhone(@Param("user_account") String user_account);

    /**
     * @Author zhangxuan
     * @Description //TODO 更新用户基本信息
     * @Date 11:20 2019/12/16
     * @Param [adminUserMain]
     * @return void
     **/
    @Update("<script>"
            + "UPDATE u_usermain "
            + "<set>"
            + "     <if test = 'user_name != null'>"
            + "         user_name = #{user_name},"
            + "     </if>"
            + "     <if test = 'user_sex != null'>"
            + "         user_sex = #{user_sex},"
            + "     </if>"
            + "     <if test = 'user_birthday != null'>"
            + "         user_birthday = #{user_birthday},"
            + "     </if>"
            + "     <if test = 'user_id_type != null'>"
            + "         user_id_type = #{user_id_type},"
            + "     </if>"
            + "     <if test = 'user_id != null'>"
            + "         user_id = #{user_id},"
            + "     </if>"
            + "     <if test = 'user_height != null'>"
            + "         user_height = #{user_height},"
            + "     </if>"
            + "     <if test = 'user_weight != null'>"
            + "         user_weight = #{user_weight},"
            + "     </if>"
            + "     <if test = 'user_weight_type != null'>"
            + "         user_weight_type = #{user_weight_type},"
            + "     </if>"
            + "     <if test = 'user_marr != null'>"
            + "         user_marr = #{user_marr},"
            + "     </if>"
            + "     <if test = 'user_cul != null'>"
            + "         user_cul = #{user_cul},"
            + "     </if>"
            + "     <if test = 'user_occ != null'>"
            + "         user_occ = #{user_occ},"
            + "     </if>"
            + "     <if test = 'user_ins != null'>"
            + "         user_ins = #{user_ins},"
            + "     </if>"
            + "     <if test = 'user_urname != null'>"
            + "         user_urname = #{user_urname},"
            + "     </if>"
            + "     <if test = 'user_urphone != null'>"
            + "         user_urphone = #{user_urphone},"
            + "     </if>"
            + "</set>"
            + "WHERE umid = #{umid}"
            + "</script>"
    )
    @Options(useCache = true,flushCache = Options.FlushCachePolicy.TRUE)
    void updateUserMain(AdminUserMain adminUserMain);

    /**
     * @Author zhangxuan
     * @Description //TODO 添加用户基本信息
     * @Date 11:20 2019/12/16
     * @Param [adminUserMain]
     * @return int
     **/
    @Options(useGeneratedKeys = true, keyColumn = "umid", keyProperty = "umid")
    @Insert("<script>"
            + "INSERT INTO u_usermain"
            + "<trim prefix = '(' suffix= ')' suffixOverrides= ',' >"
            + "     <if test = 'user_name != null'>"
            + "         user_name,"
            + "     </if>"
            + "     <if test = 'user_sex != null'>"
            + "         user_sex,"
            + "     </if>"
            + "     <if test = 'user_birthday != null'>"
            + "         user_birthday,"
            + "     </if>"
            + "     <if test = 'user_id_type != null'>"
            + "         user_id_type,"
            + "     </if>"
            + "     <if test = 'user_id != null'>"
            + "         user_id,"
            + "     </if>"
            + "     <if test = 'user_height != null'>"
            + "         user_height,"
            + "     </if>"
            + "     <if test = 'user_weight != null'>"
            + "         user_weight,"
            + "     </if>"
            + "     <if test = 'user_weight_type != null'>"
            + "         user_weight_type,"
            + "     </if>"
            + "     <if test = 'user_marr != null'>"
            + "         user_marr,"
            + "     </if>"
            + "     <if test = 'user_cul != null'>"
            + "         user_cul,"
            + "     </if>"
            + "     <if test = 'user_occ != null'>"
            + "         user_occ,"
            + "     </if>"
            + "     <if test = 'user_ins != null'>"
            + "         user_ins,"
            + "     </if>"
            + "     <if test = 'user_urname != null'>"
            + "         user_urname,"
            + "     </if>"
            + "     <if test = 'user_urphone != null'>"
            + "         user_urphone,"
            + "     </if>"
            + "</trim>"
            + "<trim prefix = 'VALUES (' suffix = ')' suffixOverrides = ',' >"
            + "     <if test = 'user_name != null'>"
            + "         #{user_name},"
            + "     </if>"
            + "     <if test = 'user_sex != null'>"
            + "         #{user_sex},"
            + "     </if>"
            + "     <if test = 'user_birthday != null'>"
            + "         #{user_birthday},"
            + "     </if>"
            + "     <if test = 'user_id_type != null'>"
            + "         #{user_id_type},"
            + "     </if>"
            + "     <if test = 'user_id != null'>"
            + "         #{user_id},"
            + "     </if>"
            + "     <if test = 'user_height != null'>"
            + "         #{user_height},"
            + "     </if>"
            + "     <if test = 'user_weight != null'>"
            + "         #{user_weight},"
            + "     </if>"
            + "     <if test = 'user_weight_type != null'>"
            + "         #{user_weight_type},"
            + "     </if>"
            + "     <if test = 'user_marr != null'>"
            + "         #{user_marr},"
            + "     </if>"
            + "     <if test = 'user_cul != null'>"
            + "         #{user_cul},"
            + "     </if>"
            + "     <if test = 'user_occ != null'>"
            + "         #{user_occ},"
            + "     </if>"
            + "     <if test = 'user_ins != null'>"
            + "         #{user_ins},"
            + "     </if>"
            + "     <if test = 'user_urname != null'>"
            + "         #{user_urname},"
            + "     </if>"
            + "     <if test = 'user_urphone != null'>"
            + "         #{user_urphone},"
            + "     </if>"
            + "</trim>"
            + "</script>"
    )
    int insertUserMain(AdminUserMain adminUserMain);

    /**
     * @Author zhangxuan
     * @Description //TODO 添加用户
     * @Date 11:21 2019/12/16
     * @Param [adminUsers]
     * @return void
     **/
    @Insert("<script>"
            + "INSERT INTO u_users"
            + "(user_account,user_password,user_starttime,umid,user_name)"
            + "VALUES"
            + "(#{user_account},#{user_password},#{user_starttime},#{umid},#{user_name})"
            + "</script>"
    )
    void insertUser(AdminUsers adminUsers);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户基础信息
     * @Date 11:21 2019/12/16
     * @Param [umid]
     * @return cn.anshirui.store.appdevelop.entity.AdminUserMain
     **/
    @Select("SELECT user_name,user_sex,user_birthday,user_id_type,user_id,user_height,user_weight,user_weight_type,user_marr,user_cul,user_occ,user_ins,user_urname,user_urphone FROM u_usermain WHERE umid = #{umid}")
    AdminUserMain selectUserMsg(Integer umid);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户及头像
     * @Date 11:21 2019/12/16
     * @Param [userId]
     * @return cn.anshirui.store.appdevelop.entity.AdminUsers
     **/
    @Select("SELECT user_id,user_account,user_starttime,user_name FROM u_users WHERE user_id = #{user_id}")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Results({
            @Result(property = "userIcon", column = "user_id", javaType = UserIcon.class,
                    one = @One(select = "cn.anshirui.store.appdevelop.mapper.IconMapper.selectIconByUserId", fetchType = FetchType.EAGER))
    })
    AdminUsers selectAdminAndIcon(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户绑定设备编号
     * @Date 11:22 2019/12/16
     * @Param [userId]
     * @return java.lang.String
     **/
    @Select("SELECT enm.eqp_number FROM e_number_message enm LEFT JOIN u_eqpuser ue ON enm.eqp_id = ue.eu_id WHERE ue.user_id = #{user_id}")
    String selectAdminEqpNum(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 根据id查询手机
     * @Date 10:47 2019/12/18
     * @Param []
     * @return java.lang.String
     **/
    @Select("SELECT user_account FROM u_users WHERE user_id = #{user_id}")
    String selectAdminId(@Param("user_id") Integer user_id);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询亲人
     * @Date 10:38 2019/12/18
     * @Param [userId]
     * @return java.util.List<cn.anshirui.store.appdevelop.entity.AdminRelation>
     **/
    @Select("SELECT urelation_id,urelation_name FROM u_relation WHERE user_id = #{user_id}")
    List<AdminRelation> selectByAuther(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 添加亲人
     * @Date 10:32 2019/12/18
     * @Param [userId, relaiotnId, rename]
     * @return void
     **/
    @Insert("INSERT INTO u_relation (user_id,urelation_id,urelation_name) VALUES (#{user_id},#{urelation_id},#{urelation_name})")
    void InsertRelation(@Param("user_id") Integer userId, @Param("urelation_id") Integer relaiotnId, @Param("urelation_name") String rename);

    /**
     * @Author zhangxuan
     * @Description //TODO 更新亲人
     * @Date 10:32 2019/12/18
     * @Param [userId, relaiotnId, rename]
     * @return void
     **/
    @Update("UPDATE u_relation SET urelation_name = #{urelation_name} WHERE user_id = #{user_id} AND urelation_id = #{urelation_id}")
    void UpdateRelation(@Param("user_id") Integer userId, @Param("urelation_id") Integer relaiotnId, @Param("urelation_name") String rename);

    /**
     * @Author zhangxuan
     * @Description //TODO 删除亲人
     * @Date 10:38 2019/12/18
     * @Param [userId, relaiotnId]
     * @return void
     **/
    @Delete("DELETE FROM u_relation WHERE user_id = #{user_id} AND urelation_id = #{urelation_id}")
    void DeleteRelaion(@Param("user_id") Integer userId, @Param("urelation_id") Integer relaiotnId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询亲人
     * @Date 11:27 2019/12/18
     * @Param [userId, relaiotnId]
     * @return java.lang.Long
     **/
    @Select("SELECT ur_id FROM u_relation WHERE user_id = #{user_id} AND urelation_id = #{urelation_id}")
    Long selectRelation(@Param("user_id") Integer userId, @Param("urelation_id") Integer relaiotnId);

    /**
     * @Author zhangxuan
     * @Description //TODO 
     * @Date 15:11 2019/12/18
     * @Param [userId]
     * @return cn.anshirui.store.appdevelop.entity.AdminUserMain
     **/
    @Options(useCache = true,flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT um.umid,um.user_name,um.user_sex,um.user_birthday,um.user_id_type,um.user_id,um.user_height,um.user_weight,um.user_weight_type,um.user_marr,um.user_cul,um.user_occ,um.user_ins,um.user_urname,um.user_urphone FROM u_usermain um LEFT JOIN u_users uu ON um.umid = uu.umid WHERE uu.user_id = #{user_id}")
    AdminUserMain selectUserMainById(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 获取版本号
     * @Date 17:22 2019/12/18
     * @Param []
     * @return java.util.List<cn.anshirui.store.appdevelop.entity.Version>
     **/
    @Options(useCache = true,flushCache = Options.FlushCachePolicy.FALSE, timeout = 86400)
    @Select("SELECT system,version,url FROM u_version")
    List<Version> selectVersion();

    /**
     * @Author zhangxuan
     * @Description //TODO 查询资料填写完成度
     * @Date 9:35 2019/12/19
     * @Param [umid]
     * @return java.lang.Integer
     **/
    @Select("SELECT getinter FROM u_usermain WHERE umid = #{umid}")
    Integer selectMainJuage(@Param("umid") Integer umid);

    /**
     * @Author zhangxuan
     * @Description //TODO 修改资料完成度
     * @Date 9:35 2019/12/19
     * @Param [umid, getinter]
     * @return void
     **/
    @Update("UPDATE u_usermain SET getinter = #{getinter} WHERE umid = #{umid}")
    void updateMainJuage(@Param("umid") Integer umid,@Param("getinter") Integer getinter);

    /**
     * @Author zhangxuan
     * @Description //TODO 通过信息id查询用户id
     * @Date 9:35 2019/12/19
     * @Param [umid]
     * @return java.lang.Integer
     **/
    @Select("SELECT uu.user_id FROM u_users uu LEFT JOIN u_usermain um ON uu.umid = um.umid WHERE um.umid = #{umid}")
    Integer selectUserIdByUmId(@Param("umid") Integer umid);

    /**
     * @Author zhangxuan
     * @Description //TODO 签到
     * @Date 15:04 2019/12/20
     * @Param [userId]
     * @return void
     **/
    @Insert("INSERT INTO u_sign (user_id,sign_time,grade) VALUES (#{user_id},NOW(),50)")
    void punchCard(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 获取签到积分的总数
     * @Date 15:05 2019/12/20
     * @Param [userId]
     * @return java.lang.Integer
     **/
    @Options(useCache = true,flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT SUM(grade) from u_sign WHERE user_id = #{user_id} GROUP BY user_id")
    Integer punchIntegralSum(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 某一时间段内的签到积分
     * @Date 9:59 2019/12/28
     * @Param [userId, start, end]
     * @return java.lang.Integer
     **/
    @Options(useCache = true,flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT SUM(grade) from u_sign WHERE user_id = #{user_id} AND sign_time BETWEEN #{start} AND #{end} GROUP BY user_id")
    Integer punchIntrgraByTime(@Param("user_id") Integer userId, @Param("start") String start, @Param("end") String end);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询当前的打卡记录
     * @Date 15:11 2019/12/20
     * @Param [userId]
     * @return java.lang.Long
     **/
    @Options(useCache = true,flushCache = Options.FlushCachePolicy.FALSE, timeout = 7200)
    @Select("SELECT u_signid FROM u_sign WHERE user_id = 1 AND sign_time = DATE_FORMAT(NOW(),'%Y-%m-%d') LIMIT 1")
    Long selectPunchToday(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 某一时间的签到日期
     * @Date 9:56 2019/12/28
     * @Param [userId, start, end]
     * @return java.util.List<java.lang.Integer>
     **/
    @Options(useCache = true)
    @Select("SELECT DATE_FORMAT(sign_time,'%d') FROM u_sign WHERE user_id = #{user_id} AND sign_time BETWEEN #{start} AND #{end} ORDER BY sign_time ASC")
    List<Integer> selectPunchCardByMonth(@Param("user_id") Integer userId, @Param("start") String start, @Param("end") String end);

    /**
     * @Author zhangxuan
     * @Description //TODO 根据设备编号查询设备id
     * @Date 15:49 2019/12/20
     * @Param [eqpNum]
     * @return java.lang.Integer
     **/
    @Select("SELECT eqp_id FROM e_number_message WHERE eqp_number = #{eqp_number}")
    Integer selectEqpIdByEqpNum(@Param("eqp_number") String eqpNum);

    /**
     * @Author zhangxuan
     * @Description //TODO 绑定设备
     * @Date 15:52 2019/12/20
     * @Param [eqpId设备id, userId]
     * @return void
     **/
    @Insert("INSERT INTO u_eqpuser (eu_id,user_id,eid,bind_time) VALUES (#{eu_id},#{user_id},1,NOW())")
    void insertUserEqp(@Param("eu_id") Integer eqpId, @Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询设备绑定用户
     * @Date 15:59 2019/12/20
     * @Param [eqpNum]
     * @return java.lang.Integer
     **/
    @Select("SELECT ue.user_id FROM u_eqpuser ue LEFT JOIN e_number_message enm ON ue.eu_id = enm.eqp_id WHERE enm.eqp_number = #{eqp_number}")
    Integer selectEqpBindByEqpNum(@Param("eqp_number") String eqpNum);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户绑定设备
     * @Date 15:59 2019/12/20
     * @Param [userId]
     * @return java.lang.String
     **/
    @Select("SELECT enm.eqp_number FROM u_eqpuser ue LEFT JOIN e_number_message enm ON ue.eu_id = enm.eqp_id WHERE ue.user_id = #{user_id}")
    String selectEqpBindByUserId(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 解绑
     * @Date 16:43 2019/12/20
     * @Param [userId]
     * @return void
     **/
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("DELETE FROM u_eqpuser WHERE user_id = #{user_id}")
    void deleteUserBindMsg(@Param("user_id") Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO web登录记录
     * @Date 15:20 2019/12/26
     * @Param [userId, describe, ip]
     * @return void
     **/
    @Insert("INSERT INTO u_logger (user_id,`describe`,time) VALUES (#{user_id},#{describe},NOW())")
    void insertUserLogger(@Param("user_id") Integer userId, @Param("describe") String describe);

}
