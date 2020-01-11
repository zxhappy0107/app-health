package cn.anshirui.store.appdevelop.service;

import cn.anshirui.store.appdevelop.entity.AdminUserMain;
import cn.anshirui.store.appdevelop.entity.UserIcon;

import java.text.ParseException;
import java.util.Map;

public interface MyServiec {

    /**
     * @Author zhangxuan
     * @Description //TODO 图片上传
     * @Date 11:08 2019/12/6
     * @Param [userId, icon]
     * @return int
     **/
    public int iconUpload(Integer userId, String icon);

    /**
     * @Author zhangxuan
     * @Description //TODO 我的首页
     * @Date 15:14 2019/12/16
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> myIndexShow(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 健康档案
     * @Date 16:26 2019/12/16
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> healthRecord(Integer userId) throws Exception;

    /**
     * @Author zhangxuan
     * @Description //TODO 更新用户基础资料
     * @Date 14:41 2019/12/18
     * @Param [adminUserMain, userId]
     * @return int
     **/
    public int updateUserMain(AdminUserMain adminUserMain) throws Exception;

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户基础信息
     * @Date 14:58 2019/12/18
     * @Param [userId]
     * @return cn.anshirui.store.appdevelop.entity.AdminUserMain
     **/
    public AdminUserMain selectUserMain(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 用户签到
     * @Date 15:14 2019/12/20
     * @Param [userId]
     * @return int
     **/
    public int punchCardUser(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 用户当月的签到记录
     * @Date 9:55 2019/12/28
     * @Param [userId, time]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> userPunchRecord(Integer userId, String time);

    /**
     * @Author zhangxuan
     * @Description //TODO 用户绑定设备信息
     * @Date 16:05 2019/12/20
     * @Param [userId]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public Map<String, String> userBindEqp(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 绑定设备
     * @Date 16:06 2019/12/20
     * @Param [userId, eqpNum]
     * @return int
     **/
    public int bindEqpUser(Integer userId, String eqpNum);

    /**
     * @Author zhangxuan
     * @Description //TODO 解绑
     * @Date 16:45 2019/12/20
     * @Param [userId]
     * @return int
     **/
    public int delBindUser(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户剩余积分
     * @Date 10:06 2019/12/30
     * @Param [userId]
     * @return java.lang.Integer
     **/
    Integer selectUserIntegral(Integer userId);

}
