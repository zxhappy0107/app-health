package cn.anshirui.store.appdevelop.service;

import java.util.Map;

public interface LoginService {

    /**
     * @Author zhangxuan
     * @Description //TODO 登录
     * @Date 11:12 2019/12/19
     * @Param [username, pass]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> login(String username, String pass) throws Exception;

    /**
     * @Author zhangxuan
     * @Description //TODO web登录
     * @Date 15:13 2019/12/26
     * @Param [username, pass, web]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> webLogin(String username, String pass, String web) throws Exception;

    /**
     * @Author zhangxuan
     * @Description //TODO 注册
     * @Date 11:12 2019/12/19
     * @Param [phone, pass]
     * @return int
     **/
    public int register(String phone, String pass) throws Exception;

    /**
     * @Author zhangxuan
     * @Description //TODO 判断用户是否存在
     * @Date 11:12 2019/12/19
     * @Param [phone]
     * @return boolean
     **/
    public boolean judgeUserExist(String phone);

    /**
     * @Author zhangxuan
     * @Description //TODO 判断用户id是否存在
     * @Date 11:12 2019/12/19
     * @Param [user_id]
     * @return boolean
     **/
    public boolean judgeUserExist(Integer user_id);

    /**
     * @Author zhangxuan
     * @Description //TODO 发送验证码
     * @Date 11:13 2019/12/19
     * @Param [phone, exe]
     * @return int
     **/
    public int sendCode(String phone, boolean exe);

    /**
     * @Author zhangxuan
     * @Description //TODO 获取版本号
     * @Date 11:13 2019/12/19
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public Map<String, String> getVersion();

    /**
     * @Author zhangxuan
     * @Description //TODO 更新用户密码
     * @Date 11:13 2019/12/19
     * @Param [username, password]
     * @return int
     **/
    public int updatePassword(String username, String password);

}
