package cn.anshirui.store.appdevelop.service;

import java.util.List;
import java.util.Map;

public interface HealthService {

    /**
     * @Author zhangxuan
     * @Description //TODO 健康首页
     * @Date 17:34 2019/12/17
     * @Param [userId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public Map<String, Object> warnIndexShow(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 历史预警
     * @Date 17:34 2019/12/17
     * @Param [userId, type]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public Map<String, Object> warnHis(Integer userId, Integer type, Integer page);

    /**
     * @Author zhangxuan
     * @Description //TODO 亲人列表
     * @Date 10:50 2019/12/18
     * @Param [userId, rePhone, reName]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String, Object>> relationShow(Integer userId);

    /**
     * @Author zhangxuan
     * @Description //TODO 添加亲人
     * @Date 10:50 2019/12/18
     * @Param [userId, reId]
     * @return int
     **/
    public int relationInsert(Integer userId, String rePhone, String reName);

    /**
     * @Author zhangxuan
     * @Description //TODO 删除亲人
     * @Date 10:50 2019/12/18
     * @Param [userId, reId]
     * @return int
     **/
    public int relationDelete(Integer userId, Integer reId);

    /**
     * @Author zhangxuan
     * @Description //TODO 更新步数
     * @Date 15:12 2019/12/27
     * @Param [userId, step]
     * @return int
     **/
    public int updateUserStep(Integer userId, Integer step);

}
