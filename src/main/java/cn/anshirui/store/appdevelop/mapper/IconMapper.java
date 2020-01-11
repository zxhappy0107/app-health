package cn.anshirui.store.appdevelop.mapper;

import cn.anshirui.store.appdevelop.entity.UserIcon;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName 图片处理
 * @Description TODO
 * @Author zhangxuan
 * @Date 10:27
 * Version 1.0
 **/
@Mapper
@CacheNamespaceRef(value = AdminMapper.class)
@Component(value = "iconMapper")
public interface IconMapper {

    /**
     * @Author zhangxuan
     * @Description //TODO 添加头像
     * @Date 17:38 2019/12/16
     * @Param [icon]
     * @return void
     **/
    @Insert("INSERT INTO u_icon (user_id,icon_url,icon_web) VALUES (#{user_id},#{icon_url},#{icon_web})")
    void insertIcon(UserIcon icon);

    /**
     * @Author zhangxuan
     * @Description //TODO 更新头像
     * @Date 17:38 2019/12/16
     * @Param [icon]
     * @return void
     **/
    @Update("UPDATE u_icon SET icon_url = #{icon_url},icon_web = #{icon_web} WHERE user_id = #{user_id}")
    void updateIcon(UserIcon icon);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询头像
     * @Date 17:38 2019/12/16
     * @Param [userId]
     * @return cn.anshirui.store.appdevelop.entity.UserIcon
     **/
    @Select("SELECT icon_url,icon_web FROM u_icon WHERE user_id = #{user_id}")
    UserIcon selectIconByUserId(@Param("user_id") int userId);

}
