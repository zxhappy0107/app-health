package cn.anshirui.store.appdevelop.mapper;

import cn.anshirui.store.appdevelop.entity.AdminUsers;

/**
 *@ClassName 用户sql语句处理
 *@Description TODO
 *@Author zhangxuan
 *@Date 16:47
 *Version 1.0
 **/
public class AdminMapperSql {

    /**
     * @Author zhangxuan
     * @Description //TODO 更新用户信息
     * @Date 16:51 2019/11/28
     * @Param [adminUsers]
     * @return java.lang.String
     **/
    public String update(AdminUsers adminUsers){
        if (null == adminUsers){
            return  null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE u_users SET");
        if (null != adminUsers.getUser_name()){
            builder.append(" user_name = #{user_name}");
        }
        if (null != adminUsers.getUser_logintime()){
            builder.append(" user_logintime = #{user_logintime},");
        }
        if (null != adminUsers.getToken()){
            builder.append("token = #{token}");
        }
        builder.append(" WHERE user_id = #{user_id}");
        return builder.toString();
    }

}
