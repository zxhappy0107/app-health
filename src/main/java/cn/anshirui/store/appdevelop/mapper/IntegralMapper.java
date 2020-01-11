package cn.anshirui.store.appdevelop.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@CacheNamespaceRef(value = AdminMapper.class)
@Component(value = "integralMapper")
public interface IntegralMapper {

    /**
     * @Author zhangxuan
     * @Description //TODO 查询总获得
     * @Date 16:41 2019/12/5
     * @Param [userId]
     * @return java.lang.Integer
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE,timeout = 1200)
    @Select("SELECT SUM(point) FROM u_points_record WHERE user_id = #{user_id} GROUP BY user_id")
    Integer selectUserPoint(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询总使用
     * @Date . 2019/12/5
     * @Param [userId]
     * @return java.lang.Integer
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE,timeout = 1200)
    @Select("SELECT SUM(used) FROM u_points_record WHERE user_id = #{user_id} GROUP BY user_id")
    Integer selectUserUsed(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询积分余额
     * @Date 16:41 2019/12/5
     * @Param [userId]
     * @return java.lang.Integer
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE,timeout = 1200)
    @Select("SELECT (SUM(point) - SUM(used)) FROM u_points_record WHERE user_id = #{user_id} GROUP BY user_id")
    Integer selectUserBalance(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户收藏
     * @Date 16:54 2019/12/5
     * @Param []
     * @return java.lang.Integer
     **/
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE,timeout = 1200)
    @Select("SELECT SUM(art_id) FROM u_user_arts WHERE user_id = #{user_id} GROUP BY user_id;")
    Integer selectUserArts(@Param("user_id") int userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 添加积分
     * @Date 17:28 2019/12/18
     * @Param [userId, point]
     * @return void
     **/
    @Insert("INSERT INTO u_points_record (user_id,point,expire,type) VALUES (#{user_id},#{point},NOW(),1)")
    void insertUserPoint(@Param("user_id") Integer userId, @Param("point") Integer point);

}
