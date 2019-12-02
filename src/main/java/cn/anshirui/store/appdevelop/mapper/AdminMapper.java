package cn.anshirui.store.appdevelop.mapper;

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
    @Select("SELECT user_id,user_account,user_password,user_name,user_starttime,user_logintime,umid,token FROM u_users WHERE user_id = #{id}")
    AdminUsers selectUserById(@Param("id") int id);

    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE)
    @UpdateProvider(type = AdminMapperSql.class, method = "update")
    void updateUser(AdminUsers adminUsers);



}
