package cn.anshirui.store.appdevelop.service.Impl;

import cn.anshirui.store.appdevelop.common.*;
import cn.anshirui.store.appdevelop.entity.AdminUserMain;
import cn.anshirui.store.appdevelop.entity.AdminUsers;
import cn.anshirui.store.appdevelop.entity.UserIcon;
import cn.anshirui.store.appdevelop.mapper.AdminMapper;
import cn.anshirui.store.appdevelop.mapper.IconMapper;
import cn.anshirui.store.appdevelop.mapper.IntegralMapper;
import cn.anshirui.store.appdevelop.service.MyServiec;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName 我的业务处理
 * @Description TODO
 * @Author zhangxuan
 * @Date 17:13
 * Version 1.0
 **/
@Service(value = "myService")
public class MyServiceImpl implements MyServiec {

    @Autowired
    private IntegralMapper integralMapper;

    @Autowired
    private IconMapper iconMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int iconUpload(Integer userId, String icon) {
        UserIcon userIcon = iconMapper.selectIconByUserId(userId);
        if (null == userIcon){
            String iconStr = PictureUtils.base64ByFile(icon);
            if (iconStr == null) {
                return 50005;
            }
            iconMapper.insertIcon(new UserIcon(null, userId, iconStr.split("&&")[0], iconStr.split("&&")[1]));
            return 200;
        }else{
            String iconStr = PictureUtils.base64ByFile(icon);
            if (iconStr == null) {
                return 50005;
            }
            iconMapper.updateIcon(new UserIcon(null, userId, iconStr.split("&&")[0], iconStr.split("&&")[1]));
            FileUtil.del(userIcon.getIcon_url());
            return 200;
        }
    }

    @Override
    public Map<String, Object> myIndexShow(Integer userId) {
        AdminUsers adminUsers = adminMapper.selectAdminAndIcon(userId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("icon", adminUsers.getUserIcon() != null ? KeyWord.LOCATION + adminUsers.getUserIcon().getIcon_web() : "null");
        resultMap.put("name", adminUsers.getUser_name());
        resultMap.put("id", StringUtils.isPhoneFy(adminUsers.getUser_account()));
        Integer balance = integralMapper.selectUserBalance(userId);
        resultMap.put("discounts", 0);
        resultMap.put("integral", balance != null ? balance : 0);
        resultMap.put("collect", 0);
        return  resultMap;
    }

    @Override
    public Map<String, Object> healthRecord(Integer userId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        AdminUserMain adminUserMain = adminMapper.selectUserMsg(adminMapper.selectUserById(userId).getUmid());
        if (StringUtils.isNotEmpty(adminUserMain.getUser_birthday())){
            resultMap.put("age", StringUtils.getAgeByBirth(DatetimeUtils.string2date(adminUserMain.getUser_birthday(), "yyyy-MM-dd"))  + "岁");
        }else {
            resultMap.put("age", 0 + "岁");
        }
        if (StringUtils.isNotEmpty(adminUserMain.getUser_sex())){
            resultMap.put("sex", adminUserMain.getUser_sex());
        }else {
            resultMap.put("sex", "无");
        }
        int record = SomeWay.degree(adminUserMain);
        resultMap.put("degree", record);
        resultMap.put("perfect", record == 100 ? "已完善" : "未完善");
        resultMap.put("healfect", "未完善");
        return resultMap;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int updateUserMain(AdminUserMain adminUserMain) throws Exception {
        adminMapper.updateUserMain(adminUserMain);
        int record = SomeWay.degree(adminUserMain);
        if (record == 100){
            if (adminMapper.selectMainJuage(adminUserMain.getUmid()) == 0){
                adminMapper.updateMainJuage(adminUserMain.getUmid(), 1);
                Integer userId = adminMapper.selectUserIdByUmId(adminUserMain.getUmid());
                integralMapper.insertUserPoint(userId, 50);
            }
        }
        return 200;
    }

    @Override
    public AdminUserMain selectUserMain(Integer userId) {
        AdminUserMain adminUserMain = adminMapper.selectUserMainById(userId);
        if (StringUtils.isEmpty(adminUserMain.getUser_sex())){
            adminUserMain.setUser_sex("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_birthday())){
            adminUserMain.setUser_birthday("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_id_type())){
            adminUserMain.setUser_id_type("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_id())){
            adminUserMain.setUser_id("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_height())){
            adminUserMain.setUser_height("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_weight())){
            adminUserMain.setUser_weight("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_weight_type())){
            adminUserMain.setUser_weight_type("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_marr())){
            adminUserMain.setUser_marr("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_cul())){
            adminUserMain.setUser_cul("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_occ())){
            adminUserMain.setUser_occ("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_ins())){
            adminUserMain.setUser_ins("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_urname())){
            adminUserMain.setUser_urname("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_urphone())){
            adminUserMain.setUser_urphone("null");
        }
        if (StringUtils.isEmpty(adminUserMain.getUser_name())){
            adminUserMain.setUser_name("null");
        }
        return adminUserMain;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public synchronized int punchCardUser(Integer userId) {
        Long record = adminMapper.selectPunchToday(userId);
        if (record == null) {
            adminMapper.punchCard(userId);
            return 200;
        }
        return 50006;
    }

    @Override
    public Map<String, Object> userPunchRecord(Integer userId, String time) {
        Map<String, Object> resultMap = new HashMap<>();
        String[] times = time.split("-");
        int month = DatetimeUtils.reMonthDay(Integer.valueOf(times[0]), Integer.valueOf(times[1]));
        List<Map<Integer, Integer>> signList = new ArrayList<>();
        for (int i = 1; i <= month; i++){
            Map<Integer, Integer> map = new HashMap<>();
            map.put(i , 0);
            signList.add(map);
        }
        Integer monthSum = adminMapper.punchIntrgraByTime(userId , times[0] + "-" + times[1] + "-01", times[0] + "-" + times[1] + "-" + month);
        Integer sum = adminMapper.punchIntegralSum(userId);
        resultMap.put("msum", monthSum != null ? monthSum : 0);
        resultMap.put("asum", sum != null ? sum : 0);
        List<Integer> monthSignList = adminMapper.selectPunchCardByMonth(userId , times[0] + "-" + times[1] + "-01", times[0] + "-" + times[1] + "-" + month);
        if (null != monthSignList && !monthSignList.isEmpty()){
            for (Integer sign: monthSignList) {
                signList.remove(sign - 1);
                Map<Integer, Integer> map = new HashMap<>();
                map.put(sign , 1);
                signList.add(sign - 1, map);
            }
            resultMap.put("signlist", signList);
        }else {
            resultMap.put("signlist", signList);
        }
        return resultMap;
    }

    @Override
    public Map<String, String> userBindEqp(Integer userId) {
        Map<String, String> resultMap = new HashMap<>();
        String eqpNum = adminMapper.selectEqpBindByUserId(userId);
        if (StringUtils.isNotEmpty(eqpNum)){
            resultMap.put("eqpnum", eqpNum);
            resultMap.put("eqptype", eqpNum.substring(3, 4));
        }else {
            resultMap.put("eqpnum", "null");
            resultMap.put("eqptype", "0");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public synchronized int bindEqpUser(Integer userId, String eqpNum) {
        if (adminMapper.selectEqpBindByUserId(userId) == null){
            Integer eu_id = adminMapper.selectEqpIdByEqpNum(eqpNum);
            if (eu_id == null) {
                return 50009;
            }
            if (adminMapper.selectEqpBindByEqpNum(eqpNum) == null){
                adminMapper.insertUserEqp(eu_id, userId);
                return 200;
            }else {
                return 50008;
            }
        }
        return 50012;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int delBindUser(Integer userId) {
        adminMapper.deleteUserBindMsg(userId);
        return 200;
    }

    @Override
    public Integer selectUserIntegral(Integer userId) {
        Integer integral = integralMapper.selectUserBalance(userId);
        if (integral == null || integral < 0){
            return 0;
        }
        return integral;
    }


}
