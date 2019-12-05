package cn.anshirui.store.appdevelop.mapper;

import cn.anshirui.store.appdevelop.entity.AdminUserMain;
import cn.anshirui.store.appdevelop.entity.AdminUsers;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.Date;

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
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 6000)
    @Select("SELECT user_id,user_account,user_password,user_name,umid FROM u_users WHERE user_account = #{user_account}")
    AdminUsers selectUserByUserAccount(@Param("user_account") String username);

    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    @UpdateProvider(type = AdminMapperSql.class, method = "update")
    void updateUser(AdminUsers adminUsers);

    @Select("SELECT user_id FROM u_users WHERE user_account = #{user_account}")
    Integer selectUserIdByPhone(@Param("user_account") String user_account);

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
            + "</set>"
            + "WHERE umid = #{umid}"
            + "</script>"
    )
    void updateUserMain(AdminUserMain adminUserMain);

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
            + "</trim>"
            + "</script>"
    )
    int insertUserMain(AdminUserMain adminUserMain);

    @Insert("<script>"
            + "INSERT INTO u_users"
            + "(user_account,user_password,user_starttime,umid,user_name)"
            + "VALUES"
            + "(#{user_account},#{user_password},#{user_starttime},#{umid},#{user_name})"
            + "</script>"
    )
    void insertUser(AdminUsers adminUsers);

}
