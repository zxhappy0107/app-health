package cn.anshirui.store.appdevelop.service.Impl;

import cn.anshirui.store.appdevelop.api.LentivirusCode;
import cn.anshirui.store.appdevelop.common.DatetimeUtils;
import cn.anshirui.store.appdevelop.common.SomeWay;
import cn.anshirui.store.appdevelop.common.StringUtils;
import cn.anshirui.store.appdevelop.entity.AdminRelation;
import cn.anshirui.store.appdevelop.entity.AdminSleepMsg;
import cn.anshirui.store.appdevelop.entity.AdminUsers;
import cn.anshirui.store.appdevelop.entity.WarnMessage;
import cn.anshirui.store.appdevelop.mapper.AdminMapper;
import cn.anshirui.store.appdevelop.mapper.HealthMapper;
import cn.anshirui.store.appdevelop.service.HealthService;
import cn.anshirui.store.appdevelop.service.RedisService;
import cn.anshirui.store.appdevelop.util.PageRequest;
import cn.anshirui.store.appdevelop.util.PageResult;
import cn.anshirui.store.appdevelop.util.PageUtils;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName 健康事物
 * @Description TODO
 * @Author zhangxuan
 * @Date 16:00
 * Version 1.0
 **/
@Service(value = "healthService")
public class HealthServiceImpl implements HealthService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private HealthMapper healthMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Map<String, Object> warnIndexShow(Integer userId) {
//        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> myMap = new HashMap<>();
        AdminUsers adminUsers = adminMapper.selectAdminAndIcon(userId);
        if (null == adminUsers){
            return null;
        }
        myMap.put("userId", null);
        myMap.put("name", "我的");
        myMap.put("phone", StringUtils.isPhoneFy(adminUsers.getUser_account()));
        myMap.put("icon", adminUsers.getUserIcon() != null ? adminUsers.getUserIcon().getIcon_web() : "null");
        List<Map<String, String>> myList = new ArrayList<>();
        int heart = 0;//高血压
        int breath = 0;//呼吸阻塞
        int vessel = 0;//心血管
//        for (int i = 0,j = 3; i < j; i++){
        for (int i = 0,j = 2; i < j; i++){
            Map<String, String> map = new HashMap<>();
            if (i == 0){
                WarnMessage my = healthMapper.selectWarnHealthOne(userId, 2);
                if (null != my){
                    map.put("icon", "h");//高血压
                    map.put("time", DatetimeUtils.date2string(my.getStarttime(), DatetimeUtils.YYYY_MM_DD));
                    map.put("wmsg", SomeWay.resultWarnHint(2, my.getStarttime(), my.getType(), null));
                    heart = 1;
                }
//                else {
//                    map.put("time", DateUtil.today());
//                    map.put("wmsg", "暂无预警信息，请您继续保持。");
//                }
            }else if (i == 1){
                WarnMessage myb = healthMapper.selectWarnHealthOne(userId, 1);
                if (null != myb){
                    map.put("icon", "b");//呼吸阻塞
                    map.put("time", DatetimeUtils.date2string(myb.getStarttime(), DatetimeUtils.YYYY_MM_DD));
                    map.put("wmsg", SomeWay.resultWarnHint(2, myb.getStarttime(), myb.getType(), myb.getValue()));
                    breath = 1;
                }
//                else {
//                    map.put("time", DateUtil.today());
//                    map.put("wmsg", "暂无预警信息，请您继续保持。");
//                }
            }
//            else {
//                WarnMessage myn = healthMapper.selectWarnOne(userId);//睡眠预警
//                if (null != myn){
//                    map.put("icon", "b");
//                    map.put("time", DatetimeUtils.date2string(myn.getStarttime(), DatetimeUtils.YYYY_MM_DD));
//                    map.put("wmsg", SomeWay.resultWarnHint(1, myn.getStarttime(), myn.getType(), null));
//                }
//                else {
//                    map.put("time", DateUtil.today());
//                    map.put("wmsg", "暂无预警信息，请您继续保持。");
//                }
//            }
            if (!map.isEmpty()){
                myList.add(map);
            }
        }
        myMap.put("hless", heart);
        myMap.put("bless", breath);
        myMap.put("vless", vessel);
        myMap.put("warn", myList);
//        resultList.add(myMap);
//        List<AdminRelation> relationList = adminMapper.selectByAuther(userId);
//        if (null != relationList && !relationList.isEmpty()){
//            for (AdminRelation relation: relationList) {
//                Map<String, Object> reMap = new HashMap<>();
//                AdminUsers reUser = adminMapper.selectAdminAndIcon(relation.getUrelation_id());
//                reMap.put("userId", relation.getUrelation_id());
//                reMap.put("name", relation.getUrelation_name() != null ? relation.getUrelation_name() : "暂无备注");
//                reMap.put("phone", StringUtils.isPhoneFy(reUser.getUser_account()));
//                reMap.put("icon", reUser.getUserIcon() != null ? reUser.getUserIcon().getIcon_web() : null);
//                List<Map<String, String>> reList = new ArrayList<>();
//                for (int i = 0,j = 3; i < j; i++){
//                    Map<String, String> map = new HashMap<>();
//                    if (i == 0){
//                        WarnMessage re = healthMapper.selectWarnHealthOne(relation.getUrelation_id(), 2);
//                        if (null != re){
//                            map.put("time", DatetimeUtils.date2string(re.getStarttime(), DatetimeUtils.YYYY_MM_DD));
//                            map.put("wmsg", SomeWay.resultWarnHint(2, re.getStarttime(), re.getType(), null));
//                        }else {
//                            map.put("time", DateUtil.today());
//                            map.put("wmsg", "暂无预警信息，请您继续保持。");
//                        }
//                    }else if (i == 1){
//                        WarnMessage reb = healthMapper.selectWarnHealthOne(relation.getUrelation_id(), 1);
//                        if (null != reb){
//                            map.put("time", DatetimeUtils.date2string(reb.getStarttime(), DatetimeUtils.YYYY_MM_DD));
//                            map.put("wmsg", SomeWay.resultWarnHint(2, reb.getStarttime(), reb.getType(), reb.getValue()));
//                        }else {
//                            map.put("time", DateUtil.today());
//                            map.put("wmsg", "暂无预警信息，请您继续保持。");
//                        }
//                    }else {
//                        WarnMessage ren = healthMapper.selectWarnOne(relation.getUrelation_id());
//                        if (null != ren){
//                            map.put("time", DatetimeUtils.date2string(ren.getStarttime(), DatetimeUtils.YYYY_MM_DD));
//                            map.put("wmsg", SomeWay.resultWarnHint(1, ren.getStarttime(), ren.getType(), null));
//                        }else {
//                            map.put("time", DateUtil.today());
//                            map.put("wmsg", "暂无预警信息，请您继续保持。");
//                        }
//                    }
//                    reList.add(map);
//                }
//                reMap.put("warn", reList);
//                resultList.add(reMap);
//            }
//        }
        return myMap;
    }

    @Override
    public Map<String, Object> warnHis(Integer userId, Integer type, Integer page) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(page);
        pageRequest.setPageSize(10);
        PageHelper.startPage(page, 10);
        List<WarnMessage> warnMessageList = healthMapper.selectWarnHealthMore(userId, type);
        PageInfo<WarnMessage> pageInfo = new PageInfo<>(warnMessageList);
        PageResult pageResult = PageUtils.getPageResult(pageRequest, pageInfo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("page", pageResult.getPageNum());
        resultMap.put("pages", pageResult.getPageSum());
        resultMap.put("count", pageResult.getCount());
        List<WarnMessage> list = (List<WarnMessage>) pageResult.getData();
        if (null != list && !list.isEmpty()){
            List<Map<String, Object>> reList = new ArrayList<>();
            for (WarnMessage warn: list) {
                Map<String, Object> map = new HashMap<>();
                map.put("total", LentivirusCode.getMsg(type));
                map.put("time", DatetimeUtils.date2string(warn.getStarttime(), DatetimeUtils.YYYY_MM_DD_HH_MM_SS));
                map.put("value", SomeWay.resultWarnHint(2, warn.getStarttime(), warn.getType(), warn.getValue()));
                reList.add(map);
            }
            resultMap.put("datalist", reList);
        }else {
            resultMap.put("datalist", new ArrayList<>());
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> relationShow(Integer userId) {
        List<AdminRelation> relationList = adminMapper.selectByAuther(userId);
        if (null != relationList && !relationList.isEmpty()){
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (AdminRelation relation: relationList) {
                Map<String, Object> map = new HashMap<>();
                map.put("reId", relation.getUrelation_id());
                map.put("value", (relation.getUrelation_name() != null ? relation.getUrelation_name() : "暂无备注") + " | 账号 " + StringUtils.isPhoneFy(adminMapper.selectAdminId(relation.getUrelation_id())));
                resultList.add(map);
            }
            return  resultList;
        }
        return null;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int relationInsert(Integer userId, String rePhone, String reName) {
        Integer id = adminMapper.selectUserIdByPhone(rePhone);
        if (id == null) {
            return 50003;
        }
        Long urId = adminMapper.selectRelation(userId, id);
        if (urId == null) {
            adminMapper.InsertRelation(userId, id, reName);
        }else {
            adminMapper.UpdateRelation(userId, id, reName);
        }
        return 200;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int relationDelete(Integer userId, Integer reId) {
        adminMapper.DeleteRelaion(userId, reId);
        return 200;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public synchronized int updateUserStep(Integer userId, Integer step) {
        System.out.println("用户id：" + userId + " " + step + "步数");
        Long sleepMsg = healthMapper.selectHealthExist(userId);
        if (null == sleepMsg){
            if (DatetimeUtils.compareTime(new Date(), DatetimeUtils.string2date(DateUtil.today() + " 10:00:00", DatetimeUtils.YYYY_MM_DD_HH_MM_SS))){
                healthMapper.insertSleepByStep(userId, step);
            }else {
                redisService.set(userId + "step", step, 36000L);
            }
        }else {
            healthMapper.updateAdminStepBloodSugar(userId, step, null);
        }
        return 200;
    }

}
