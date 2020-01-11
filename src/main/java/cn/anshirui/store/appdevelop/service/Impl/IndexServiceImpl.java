package cn.anshirui.store.appdevelop.service.Impl;

import cn.anshirui.store.appdevelop.common.*;
import cn.anshirui.store.appdevelop.entity.AdminBodyMsg;
import cn.anshirui.store.appdevelop.entity.AdminHealthMsg;
import cn.anshirui.store.appdevelop.entity.AdminSleepMsg;
import cn.anshirui.store.appdevelop.entity.AdminUsers;
import cn.anshirui.store.appdevelop.mapper.AdminMapper;
import cn.anshirui.store.appdevelop.mapper.HealthMapper;
import cn.anshirui.store.appdevelop.service.IndexService;
import cn.anshirui.store.appdevelop.service.RedisService;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @ClassName 首页事物
 * @Description TODO
 * @Author zhangxuan
 * @Date 10:01
 * Version 1.0
 **/
@Service("indexService")
public class IndexServiceImpl implements IndexService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private HealthMapper healthMapper;

    @Autowired
    private RedisService redis;

    @Override
    @Transactional
    public Map<String, Object> indexShowMsg(Integer userId) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("upload", "step");
        AdminUsers adminUsers = adminMapper.selectAdminAndIcon(userId);
        Map<String, Object> head = new LinkedHashMap<>();
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> foot = new LinkedHashMap<>();
        if (null != adminUsers){
            head.put("head_name", adminUsers.getUser_name() != null ? adminUsers.getUser_name() : StringUtils.isPhoneFy(adminUsers.getUser_account()));
            head.put("head_time", DateUtil.today());
            if (null != adminUsers.getUserIcon()){
                head.put("head_image", KeyWord.LOCATION + adminUsers.getUserIcon().getIcon_web());
            }else{
                head.put("head_image", "null");
            }
        }else {
            head.put("head_name", "null");
            head.put("head_image", "null");
            head.put("head_time", DateUtil.today());
        }
        Double risk = healthMapper.selectUserRisk(userId);
        if (risk != null) {
            if (risk < 50){
                body.put("body_type", "健康");
                body.put("body_prob", (int)(risk / 2) + "%");
                body.put("body_value", (int)(risk / 2));
            }else if (risk >= 50 && risk < 80){
                body.put("body_type", "尚可");
                body.put("body_prob", 25 + (int)((risk - 50) / 1.2) + "%");
                body.put("body_value", 25 + (int)((risk - 50) / 1.2));
            }else if (risk >= 80 && risk < 150){
                body.put("body_type", "亚健康");
                body.put("body_prob", 50 + (int)((risk - 80) / 2.8) + "%");
                body.put("body_value", 50 + (int)((risk - 80) / 2.8));
            }else{
                body.put("body_type", "高风险");
                body.put("body_prob", risk >= 200 ? 100 : 75 + (int)((risk - 150) / 2) + "%");
                body.put("body_value", risk >= 200 ? 100 : 75 + (int)((risk - 150) / 2));
            }

            body.put("body_dream", 0);
        }else{
            body.put("body_type", "未知");
            body.put("body_prob", "未知");
            body.put("body_value", 0);
            body.put("body_high", 0);
            body.put("body_dream", 0);
        }
        AdminHealthMsg health = healthMapper.selectHealthBas(userId);
        AdminSleepMsg sleep = healthMapper.selectHealthMsg(userId);
        if (null != health && null != sleep){
            body.put("body_high", sleep.getHigh_blood() == 0 ? 0 : 100);
            foot.put("foot_step", sleep.getStep() != null ? sleep.getStep() : 0);
            int sleep_time = sleep.getLight_time() + sleep.getDeep_time() + sleep.getDream_time();
            foot.put("foot_time_hour", (sleep_time / 60));
            foot.put("foot_time_min", (sleep_time % 60));
            if (sleep_time > 420 && sleep_time < 540){
                body.put("body_dream", 100);
                foot.put("foot_time_type", "睡眠正常");
            }else if (sleep_time >= 540){
                body.put("body_dream", 60);
                foot.put("foot_time_type", "睡眠过长");
            }else{
                body.put("body_dream", 30);
                foot.put("foot_time_type", "睡眠不足");
            }
            if (health.getH_heartmin() < 60){
                foot.put("foot_heart", "过缓");
                foot.put("foot_heart_value", health.getH_heartmin());
            }else if (health.getH_heartmax() > 100){
                foot.put("foot_heart", "过快");
                foot.put("foot_heart_value", health.getH_heartmax());
            }else{
                foot.put("foot_heart", "正常");
                foot.put("foot_heart_value", health.getH_heartave());
            }
            if (health.getH_breathmin() < 12){
                foot.put("foot_breath", "过缓");
            }else if (health.getH_breathmax() > 20){
                foot.put("foot_breath", "过快");
            }else{
                foot.put("foot_breath", "正常");
            }
            foot.put("foot_bp", sleep.getHigh_blood() == 0 ? "正常" : "异常");
            Double glu = sleep.getBlood_sugar();
            if (glu == null) {
                foot.put("goot_glu", "未检测");
            }else{
                if (glu <= 9.4){
                    foot.put("goot_glu", "正常");
                }else{
                    foot.put("goot_glu", "过高");
                }
            }
            foot.put("health_index", health.getH_index());
        }else{
            foot.put("foot_step", 0);
            foot.put("foot_time_hour", 0);
            foot.put("foot_time_min", 0);
            foot.put("foot_heart", "未知");
            foot.put("foot_heart_value", 0);
            foot.put("foot_breath", "未知");
            foot.put("foot_bp", "未知");
            foot.put("goot_glu", "未知");
            foot.put("health_index", 0);
        }
        resultMap.put("head", head);
        resultMap.put("body", body);
        resultMap.put("foot", foot);
        return resultMap;
    }

    @Override
    public Map<String, Object> indexSleepMsg(Integer userId) {
        Map<String, Object> resultMap = new HashMap<>();
        String eqpNum = adminMapper.selectAdminEqpNum(userId);
        if (eqpNum == null) {
            resultMap.put("eqp_online", "2");//为绑定设备
            resultMap.put("eqp_type", "2");//未知
        }else {
            String online = (String) redis.get("juage" + eqpNum);
            if (StringUtils.isNotEmpty(online)){
                resultMap.put("eqp_online", "1");//设备在线
                if (online.equals("1")) {
                    resultMap.put("eqp_type", "1");//在床
                }else{
                    resultMap.put("eqp_type", "0");//离床
                }
            }else{
                resultMap.put("eqp_online", "0");//设备离线
                resultMap.put("eqp_type", "2");
            }
        }
        AdminSleepMsg sleepMsg = healthMapper.selectHealthMsg(userId);
        resultMap.put("sleep_hour", 0);
        resultMap.put("sleep_min", 0);
        resultMap.put("sleep_type", 0);
        if (sleepMsg != null) {
            Integer sleeptime = sleepMsg.getLight_time() + sleepMsg.getDeep_time() + sleepMsg.getDream_time();
            resultMap.put("sleep_hour", sleeptime / 60);
            resultMap.put("sleep_min", sleeptime % 60);
            if (sleeptime < 300){
                resultMap.put("sleep_type", sleeptime / 12);
            }else if (sleeptime >= 300 && sleeptime <= 420){
                int value = (int)((sleeptime - 300) / 4.8);
                resultMap.put("sleep_type", 26 + value > 50 ? 50 : 26 + value);
            }else if (sleeptime > 420 && sleeptime < 600){
                int value = (int)((sleeptime - 420) / 7.2);
                resultMap.put("sleep_type", 51 + value > 75 ? 75 : 51 + value);
            }else {
                resultMap.put("sleep_type", sleeptime > 720 ? 100 : 76 + (int)((sleeptime - 420) / 12) > 100 ? 100 : 76 + (int)((sleeptime - 420) / 12));
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> indexSleepDay(Integer userId, String time) {
        AdminHealthMsg healthMsg = null;;
        AdminSleepMsg sleepMsg = null;
        String day = null;
        if (StringUtils.isNotEmpty(time)) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", userId + "");
            map.put("time", time);
            healthMsg = healthMapper.selectHealthBasMap(map);
            sleepMsg = healthMapper.selectHealthMsgMap(map);
            day = time;
        }else{
            healthMsg = healthMapper.selectHealthBas(userId);
            sleepMsg = healthMapper.selectHealthMsg(userId);
            day = DateUtil.today();
            time = DateUtil.today();
        }
        if (sleepMsg != null && healthMsg != null) {
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("date", day);
            //睡眠分析
            Map<String, Object> analyze = new HashMap<>();
            int s = sleepMsg.getSober_time();
            int l = sleepMsg.getLight_time();
            int d = sleepMsg.getDeep_time();
            int dr = sleepMsg.getDream_time();
            int drn = sleepMsg.getDream_count();
            int sleep = l + d + dr;
            int sum = s + l + d + dr;
            long inTimes = (sleepMsg.getGetuptime().getTime() -
                    sleepMsg.getSpinintime().getTime()) / (1000 * 60);
            analyze.put("sober", s >= 60 ? (s / 60) + "h" + (s % 60) + "min" : (s % 60) + "min");
            analyze.put("light", l >= 60 ? (l / 60) + "h" + (l % 60) + "min" : (l % 60) + "min");
            analyze.put("deep", d >= 60 ? (d / 60) + "h" + (d % 60) + "min" : (d % 60) + "min");
            analyze.put("dream", dr >= 60 ? (dr / 60) + "h" + (dr % 60) + "min" : (dr % 60) + "min");
            int sT = (int) ((s / (double) sum) * 100);
            int lT = (int) ((l / (double) sum) * 100);
            int dT = (int) ((d / (double) sum) * 100);
            int drT = (int) ((dr / (double) sum) * 100);
            if ((sT + lT + dT + drT) < 100) {
                sT += 100 - (sT + lT + dT + drT);
            } else if ((sT + lT + dT + drT) > 100) {
                sT -= (sT + lT + dT + drT) - 100;
            }
            analyze.put("sober_ratio", sT);
            analyze.put("light_ratio", lT);
            analyze.put("deep_ratio", dT);
            analyze.put("dream_ratio", drT);
            resultMap.put("sleep_analyze", analyze);
            //睡眠详情
            List<Map<String, String>> sleeppart = new ArrayList<>();
            String beforeDay = DatetimeUtils.reduceDay(time, -1);
            for (int i = 0; i < 9; i++) {
                Map<String, String> map = new HashMap<>();
                if (i == 0) {
                    map.put("left_top", "上床时间  " + DatetimeUtils.date2string(sleepMsg.getSpinintime(), "HH 点 mm 分"));
                    map.put("left_down", "参考值：20-23 点");
                    if (DatetimeUtils.compareTime(sleepMsg.getSpinintime(),
                            DatetimeUtils.string2date(beforeDay + " 23:00:00", DatetimeUtils.YYYY_MM_DD_HH_MM_SS))) {
                        map.put("right", "偏晚");
                    } else {
                        map.put("right", "正常");
                    }
                }else if (i == 1){
                    map.put("left_down", "参考值：06-08 点");
                    if (DatetimeUtils.compareTime(sleepMsg.getGetuptime(),
                            DatetimeUtils.string2date(time + " 08:00:00", DatetimeUtils.YYYY_MM_DD_HH_MM_SS))) {
                        map.put("left_top", "起床时间  08 点 00 分");
                    } else {
                        map.put("left_top", "起床时间  " + DatetimeUtils.date2string(sleepMsg.getGetuptime(), "HH 点 mm 分"));
                    }
                    if (DatetimeUtils.compareTime(sleepMsg.getGetuptime(),
                            DatetimeUtils.string2date(time + " 06:00:00", DatetimeUtils.YYYY_MM_DD_HH_MM_SS))) {
                        map.put("right", "正常");
                    } else {
                        map.put("right", "偏早");
                    }
                }else if (i == 2){
                    map.put("left_top", "夜间睡眠 "+ (sleep / 60) + " 小时 " + (sleep % 60) + " 分");
                    map.put("left_down", "参数值：6-10小时");
                    if (sleep < 360){
                        map.put("right", "偏早");
                    }else if (sleep > 600){
                        map.put("right", "偏晚");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 3){
                    map.put("left_top", "深睡比例 "+ dT + "%");
                    map.put("left_down", "参数值：20-60%");
                    if (dT < 20){
                        map.put("right", "偏少");
                    }else if (dT > 60){
                        map.put("right", "偏多");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 4){
                    map.put("left_top", "浅睡比例 "+ lT + "%");
                    map.put("left_down", "参数值：< 55%");
                    if (lT > 55){
                        map.put("right", "偏多");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 5){
                    map.put("left_top", "做梦次数 "+ drn + "次");
                    map.put("left_down", "参数值：0-2 次");
                    if (drn > 2){
                        map.put("right", "偏多");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 6){
                    map.put("left_top", "清醒次数 "+ sleepMsg.getLevel_bed() + "次");
                    map.put("left_down", "参数值：0-2 次");
                    if (drn > 2){
                        map.put("right", "偏多");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 7){
                    map.put("left_down", "参数值：70-100 分");
                    if (healthMsg.getH_heartmax() > 100){
                        map.put("left_top", "心率质量 70分");
                        map.put("right", "过快");
                    }else if (healthMsg.getH_heartmin() < 60){
                        map.put("left_top", "心率质量 70分");
                        map.put("right", "过缓");
                    }else{
                        map.put("left_top", "心率质量 90分");
                        map.put("right", "正常");
                    }
                }else if (i == 8){
                    map.put("left_down", "参数值：12-20 分");
                    if (healthMsg.getH_breathmax() > 20){
                        map.put("left_top", "呼吸质量 70分");
                        map.put("right", "过快");
                    }else if (healthMsg.getH_breathmin() < 12){
                        map.put("left_top", "呼吸质量 70分");
                        map.put("right", "过缓");
                    }else{
                        map.put("left_top", "呼吸质量 90分");
                        map.put("right", "正常");
                    }
                }
                sleeppart.add(map);
            }
            resultMap.put("sleeppart", sleeppart);
            return  resultMap;
        }else {
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("date", day);
            Map<String, Object> analyze = new HashMap<>();
            analyze.put("sober", "0h0min");
            analyze.put("light", "0h0min");
            analyze.put("deep", "0h0min");
            analyze.put("dream", "0h0min");
            analyze.put("sober_ratio", 0);
            analyze.put("light_ratio", 0);
            analyze.put("deep_ratio", 0);
            analyze.put("dream_ratio", 0);
            resultMap.put("sleep_analyze", analyze);
            //睡眠详情
            List<Map<String, String>> sleeppart = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                Map<String, String> map = new HashMap<>();
                if (i == 0) {
                    map.put("left_top", "上床时间 未知");
                    map.put("left_down", "参考值：20-23 点");
                    map.put("right", "未知");
                }else if (i == 1){
                    map.put("left_down", "参考值：06-08 点");
                    map.put("left_top", "起床时间 未知");
                    map.put("right", "未知");
                }else if (i == 2){
                    map.put("left_top", "夜间睡眠 未知");
                    map.put("left_down", "参数值：6-10小时");
                    map.put("right", "未知");
                }else if (i == 3){
                    map.put("left_top", "深睡比例 未知");
                    map.put("left_down", "参数值：20-60%");
                    map.put("right", "未知");
                }else if (i == 4){
                    map.put("left_top", "浅睡比例 未知");
                    map.put("left_down", "参数值：< 55%");
                    map.put("right", "未知");
                }else if (i == 5){
                    map.put("left_top", "做梦次数 未知");
                    map.put("left_down", "参数值：0-2 次");
                    map.put("right", "未知");
                }else if (i == 6){
                    map.put("left_top", "清醒次数 未知");
                    map.put("left_down", "参数值：0-2 次");
                    map.put("right", "未知");
                }else if (i == 7){
                    map.put("left_down", "参数值：70-100 分");
                    map.put("left_top", "心率质量 未知");
                    map.put("right", "未知");
                }else if (i == 8){
                    map.put("left_down", "参数值：12-20 分");
                    map.put("left_top", "呼吸质量 未知");
                    map.put("right", "未知");
                }
                sleeppart.add(map);
            }
            resultMap.put("sleeppart", sleeppart);
            return resultMap;
        }
    }

    @Override
    public Map<String, Object> webIndexSleepDay(Integer userId, String time) {
        AdminHealthMsg healthMsg = null;;
        AdminSleepMsg sleepMsg = null;
        String day = null;
        if (StringUtils.isNotEmpty(time)) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", userId + "");
            map.put("time", time);
            healthMsg = healthMapper.selectHealthBasMap(map);
            sleepMsg = healthMapper.selectHealthMsgMap(map);
            day = time;
        }else{
            healthMsg = healthMapper.selectHealthBas(userId);
            sleepMsg = healthMapper.selectHealthMsg(userId);
            day = DateUtil.today();
            time = DateUtil.today();
        }
        if (sleepMsg != null && healthMsg != null) {
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("date", day);
            //睡眠分析
            int s = sleepMsg.getSober_time();
            int l = sleepMsg.getLight_time();
            int d = sleepMsg.getDeep_time();
            int dr = sleepMsg.getDream_time();
            int drn = sleepMsg.getDream_count();
            int sleep = l + d + dr;
            int sum = s + l + d + dr;
            long inTimes = (sleepMsg.getGetuptime().getTime() -
                    sleepMsg.getSpinintime().getTime()) / (1000 * 60);
            Map<String, Object> analyze = new LinkedHashMap<>();
            analyze.put("sober", s >= 60 ? (s / 60) + "h" + (s % 60) + "min" : (s % 60) + "min");
            analyze.put("light", l >= 60 ? (l / 60) + "h" + (l % 60) + "min" : (l % 60) + "min");
            analyze.put("deep", d >= 60 ? (d / 60) + "h" + (d % 60) + "min" : (d % 60) + "min");
            analyze.put("dream", dr >= 60 ? (dr / 60) + "h" + (dr % 60) + "min" : (dr % 60) + "min");
            int sT = (int) ((s / (double) sum) * 100);
            int lT = (int) ((l / (double) sum) * 100);
            int dT = (int) ((d / (double) sum) * 100);
            int drT = (int) ((dr / (double) sum) * 100);
            if ((sT + lT + dT + drT) < 100) {
                sT += 100 - (sT + lT + dT + drT);
            } else if ((sT + lT + dT + drT) > 100) {
                sT -= (sT + lT + dT + drT) - 100;
            }
            String[] scales = {"深睡", "浅睡", "轻睡", "做梦"};
            analyze.put("sleep_list", scales);
            List<Map<String, Object>> sleepScaleList = new ArrayList<>();
            for (int i = 0,j = 4; i < j; i++){
                Map<String, Object> map = new HashMap<>();
                switch (i){
                    case 0:
                        map.put("name", "深睡");
                        map.put("value", dT);
                        break;
                    case 1:
                        map.put("name", "浅睡");
                        map.put("value", lT);
                        break;
                    case 2:
                        map.put("name", "轻睡");
                        map.put("value", sT);
                        break;
                    case 3:
                        map.put("name", "做梦");
                        map.put("value", drT);
                        break;
                }
                sleepScaleList.add(map);
            }
            analyze.put("sleep_scale", sleepScaleList);
            resultMap.put("sleep_analyze", analyze);
            //睡眠详情
            List<Map<String, String>> sleeppart = new ArrayList<>();
            String beforeDay = DatetimeUtils.reduceDay(time, -1);
            for (int i = 0; i < 9; i++) {
                Map<String, String> map = new HashMap<>();
                if (i == 0) {
                    map.put("left_top", "上床时间  " + DatetimeUtils.date2string(sleepMsg.getSpinintime(), "HH 点 mm 分"));
                    map.put("left_down", "参考值：20-23 点");
                    if (DatetimeUtils.compareTime(sleepMsg.getSpinintime(),
                            DatetimeUtils.string2date(beforeDay + " 23:00:00", DatetimeUtils.YYYY_MM_DD_HH_MM_SS))) {
                        map.put("right", "偏晚");
                    } else {
                        map.put("right", "正常");
                    }
                }else if (i == 1){
                    map.put("left_down", "参考值：06-08 点");
                    if (DatetimeUtils.compareTime(sleepMsg.getGetuptime(),
                            DatetimeUtils.string2date(time + " 08:00:00", DatetimeUtils.YYYY_MM_DD_HH_MM_SS))) {
                        map.put("left_top", "起床时间  08 点 00 分");
                    } else {
                        map.put("left_top", "起床时间  " + DatetimeUtils.date2string(sleepMsg.getGetuptime(), "HH 点 mm 分"));
                    }
                    if (DatetimeUtils.compareTime(sleepMsg.getGetuptime(),
                            DatetimeUtils.string2date(time + " 06:00:00", DatetimeUtils.YYYY_MM_DD_HH_MM_SS))) {
                        map.put("right", "正常");
                    } else {
                        map.put("right", "偏早");
                    }
                }else if (i == 2){
                    map.put("left_top", "夜间睡眠 "+ (sleep / 60) + " 小时 " + (sleep % 60) + " 分");
                    map.put("left_down", "参数值：6-10小时");
                    if (sleep < 360){
                        map.put("right", "偏早");
                    }else if (sleep > 600){
                        map.put("right", "偏晚");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 3){
                    map.put("left_top", "深睡比例 "+ dT + "%");
                    map.put("left_down", "参数值：20-60%");
                    if (dT < 20){
                        map.put("right", "偏少");
                    }else if (dT > 60){
                        map.put("right", "偏多");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 4){
                    map.put("left_top", "浅睡比例 "+ lT + "%");
                    map.put("left_down", "参数值：< 55%");
                    if (lT > 55){
                        map.put("right", "偏多");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 5){
                    map.put("left_top", "做梦次数 "+ drn + "次");
                    map.put("left_down", "参数值：0-2 次");
                    if (drn > 2){
                        map.put("right", "偏多");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 6){
                    map.put("left_top", "清醒次数 "+ sleepMsg.getLevel_bed() + "次");
                    map.put("left_down", "参数值：0-2 次");
                    if (drn > 2){
                        map.put("right", "偏多");
                    }else{
                        map.put("right", "正常");
                    }
                }else if (i == 7){
                    map.put("left_down", "参数值：70-100 分");
                    if (healthMsg.getH_heartmax() > 100){
                        map.put("left_top", "心率质量 70分");
                        map.put("right", "过快");
                    }else if (healthMsg.getH_heartmin() < 60){
                        map.put("left_top", "心率质量 70分");
                        map.put("right", "过缓");
                    }else{
                        map.put("left_top", "心率质量 90分");
                        map.put("right", "正常");
                    }
                }else if (i == 8){
                    map.put("left_down", "参数值：12-20 分");
                    if (healthMsg.getH_breathmax() > 20){
                        map.put("left_top", "呼吸质量 70分");
                        map.put("right", "过快");
                    }else if (healthMsg.getH_breathmin() < 12){
                        map.put("left_top", "呼吸质量 70分");
                        map.put("right", "过缓");
                    }else{
                        map.put("left_top", "呼吸质量 90分");
                        map.put("right", "正常");
                    }
                }
                sleeppart.add(map);
            }
            resultMap.put("sleeppart", sleeppart);
            return  resultMap;
        }else {
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("date", day);
            Map<String, Object> analyze = new LinkedHashMap<>();
            analyze.put("sober", "0h0min");
            analyze.put("light", "0h0min");
            analyze.put("deep", "0h0min");
            analyze.put("dream", "0h0min");
            String[] scales = {"深睡", "浅睡", "轻睡", "做梦"};
            analyze.put("sleep_list", scales);
            List<Map<String, Object>> sleepScaleList = new ArrayList<>();
            for (int i = 0,j = 4; i < j; i++){
                Map<String, Object> map = new HashMap<>();
                switch (i){
                    case 0:
                        map.put("name", "深睡");
                        map.put("value", 0);
                        break;
                    case 1:
                        map.put("name", "浅睡");
                        map.put("value", 0);
                        break;
                    case 2:
                        map.put("name", "轻睡");
                        map.put("value", 0);
                        break;
                    case 3:
                        map.put("name", "做梦");
                        map.put("value", 0);
                        break;
                }
                sleepScaleList.add(map);
            }
            analyze.put("sleep_scale", sleepScaleList);
            resultMap.put("sleep_analyze", analyze);
            //睡眠详情
            List<Map<String, String>> sleeppart = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                Map<String, String> map = new HashMap<>();
                if (i == 0) {
                    map.put("left_top", "上床时间 未知");
                    map.put("left_down", "参考值：20-23 点");
                    map.put("right", "未知");
                }else if (i == 1){
                    map.put("left_down", "参考值：06-08 点");
                    map.put("left_top", "起床时间 未知");
                    map.put("right", "未知");
                }else if (i == 2){
                    map.put("left_top", "夜间睡眠 未知");
                    map.put("left_down", "参数值：6-10小时");
                    map.put("right", "未知");
                }else if (i == 3){
                    map.put("left_top", "深睡比例 未知");
                    map.put("left_down", "参数值：20-60%");
                    map.put("right", "未知");
                }else if (i == 4){
                    map.put("left_top", "浅睡比例 未知");
                    map.put("left_down", "参数值：< 55%");
                    map.put("right", "未知");
                }else if (i == 5){
                    map.put("left_top", "做梦次数 未知");
                    map.put("left_down", "参数值：0-2 次");
                    map.put("right", "未知");
                }else if (i == 6){
                    map.put("left_top", "清醒次数 未知");
                    map.put("left_down", "参数值：0-2 次");
                    map.put("right", "未知");
                }else if (i == 7){
                    map.put("left_down", "参数值：70-100 分");
                    map.put("left_top", "心率质量 未知");
                    map.put("right", "未知");
                }else if (i == 8){
                    map.put("left_down", "参数值：12-20 分");
                    map.put("left_top", "呼吸质量 未知");
                    map.put("right", "未知");
                }
                sleeppart.add(map);
            }
            resultMap.put("sleeppart", sleeppart);
            return resultMap;
        }
    }

    @Override
    public Map<String, Object> indexSleepWeek(Integer userId) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        String time = DatetimeUtils.date2string(new Date(), "E@@yyyy-MM-dd");
        List<String> lastWeek = DatetimeUtils.weekSE(time.split("@@")[0], time.split("@@")[1]);
        List<String> toLastWeek = DatetimeUtils.weekSE("星期一", lastWeek.get(0));
        List<Map<String, Object>> lenth = new ArrayList<>();
        List<Map<String, Object>> dream = new ArrayList<>();
        List<Map<String, Object>> spin = new ArrayList<>();
        List<Map<String, Object>> getup = new ArrayList<>();
        int dreamCount = 0;//上周睡眠天数
        long timeDiff = 0;//上周睡眠差值
        int gradeAver = 0;//平均分
        int inBedTime = 0;//上周在床时间
        int lastDreamCount = 0;//上上周睡眠天数
        int lastTimeDiff = 0;//上上周睡眠天数
        int lastInBedTime = 0;//上上周在床时间
        //上上周睡眠时间
        for (String string : toLastWeek) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", userId + "");
            map.put("time", string);
            AdminSleepMsg disperse = healthMapper.selectHealthMsgMap(map);
            if (disperse != null) {
                lastDreamCount++;
                long norTime = DatetimeUtils.string2date(string + " 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
                lastTimeDiff += norTime - disperse.getSpinintime().getTime();
                if (DatetimeUtils.compareTime(disperse.getGetuptime(),
                        DatetimeUtils.string2date(string + " 08:00:00", "yyyy-MM-dd HH:mm:ss"))) {
                    lastInBedTime += (int)((DatetimeUtils.string2date(string + " 08:00:00", "yyyy-MM-dd HH:mm:ss").getTime() - disperse.getSpinintime().getTime())
                            / (60 * 1000));
                }else {
                    lastInBedTime += (int)((disperse.getGetuptime().getTime() - disperse.getSpinintime().getTime())
                            / (60 * 1000));
                }
            }
        }
        //上周睡眠时间
        String aver = null;
        for (String string : lastWeek) {
            Map<String, Object> dreamLenth = new HashMap<>();
            Map<String, Object> dreamresult = new HashMap<>();
            Map<String, Object> spinresult = new HashMap<>();
            Map<String, Object> getresult = new HashMap<>();
            String xAxe = DatetimeUtils.transform(string, "yyyy-MM-dd", "MM.dd");
            dreamLenth.put("xAxe", xAxe);
            dreamresult.put("xAxe", xAxe);
            spinresult.put("xAxe", xAxe);
            getresult.put("xAxe", xAxe);
            Integer value = healthMapper.selectHealthIndex(userId, string);
            if (value != null) {
                gradeAver += value;
                dreamresult.put("value", String.valueOf(value));
            }else {
                dreamresult.put("value", "0");
            }
            dream.add(dreamresult);
            Map<String, String> map = new HashMap<>();
            map.put("user_id", userId + "");
            map.put("time", string);
            AdminSleepMsg disperse = healthMapper.selectHealthMsgMap(map);
            if (disperse != null) {
                dreamCount++;
                long norTime = DatetimeUtils.string2date(string + " 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
                timeDiff += norTime - disperse.getSpinintime().getTime();
                String spinTime = DatetimeUtils.date2string(disperse.getSpinintime(), "HH");
                spinresult.put("value", spinTime);
                spin.add(spinresult);
                String getupTime = DatetimeUtils.date2string(disperse.getGetuptime(), "HH");
                if (DatetimeUtils.compareTime(disperse.getGetuptime(),
                        DatetimeUtils.string2date(string + " 08:00:00", "yyyy-MM-dd HH:mm:ss"))) {
                    int timeLenth = (int)((DatetimeUtils.string2date(string + " 08:00:00", "yyyy-MM-dd HH:mm:ss").getTime() - disperse.getSpinintime().getTime())
                            / (60 * 1000));
                    inBedTime += timeLenth;
                    dreamLenth.put("value", (timeLenth / 60));
                    getresult.put("value", 8);
                }else {
                    if (DatetimeUtils.compareTime(disperse.getGetuptime(),
                            DatetimeUtils.string2date(string + " 00:00:00", "yyyy-MM-dd HH:mm:ss"))) {
                        getresult.put("value", getupTime);
                    } else {
                        getresult.put("value", 0);
                    }
                    int timeLenth = (int)((disperse.getGetuptime().getTime() - disperse.getSpinintime().getTime())
                            / (60 * 1000));
                    inBedTime += timeLenth;
                    dreamLenth.put("value", (timeLenth / 60));
                }
                getup.add(getresult);
                lenth.add(dreamLenth);
            }else {
                dreamLenth.put("value", 0);
                spinresult.put("value", 0);
                getresult.put("value", 0);
                spin.add(spinresult);
                getup.add(getresult);
                lenth.add(dreamLenth);
            }
        }
        if (dreamCount > 0 && gradeAver > 0) {
            timeDiff = timeDiff / dreamCount;
            long dreamTime = DatetimeUtils.string2date("00:00:00", "HH:mm:ss").getTime();
            if (timeDiff > 0) {
                aver = DatetimeUtils.date2string(new Date(dreamTime - timeDiff), "HH:mm");
            }else {
                aver = DatetimeUtils.date2string(new Date(dreamTime + timeDiff), "HH:mm");
            }
            resultMap.put("weekAver", aver);
            resultMap.put("weekGradeAver", String.valueOf(gradeAver / dreamCount));
        }else {
            resultMap.put("weekAver", "00:00");
            resultMap.put("weekGradeAver", "0");
        }
        if (inBedTime > 0) {
            inBedTime = inBedTime / dreamCount;
            if (inBedTime > 60) {
                resultMap.put("weekInBed", (inBedTime / 60) + "h" + (inBedTime % 60) + "min");
            }else {
                resultMap.put("weekInBed", "0h" + (inBedTime % 60) + "min");
            }
        }else {
            resultMap.put("weekInBed", "0h0min");
        }
        resultMap.put("tWeekIn", inBedTime > 0?"本周" + (inBedTime * dreamCount / 60) + "小时":"本周0小时");
        resultMap.put("lWeekIn", lastInBedTime > 0?"上周" + (lastInBedTime * lastDreamCount / 60) + "小时":"上周0小时");
        resultMap.put("tInBed", aver != null ?"本周" +  aver : "本周00:00");
        if (inBedTime == 0 || lastInBedTime == 0) {
            resultMap.put("conTL", 0);
            resultMap.put("conInTL", "未知");
            resultMap.put("lInBed", "上周00:00");
        }else {
            String lastAver = null;
            lastTimeDiff = lastTimeDiff / lastDreamCount;
            long dreamTime = DatetimeUtils.string2date("00:00:00", "HH:mm:ss").getTime();
            if (timeDiff > 0) {
                lastAver = DatetimeUtils.date2string(new Date(dreamTime - lastTimeDiff), "HH:mm");
            }else {
                lastAver = DatetimeUtils.date2string(new Date(dreamTime + lastTimeDiff), "HH:mm");
            }
            resultMap.put("lInBed", lastAver != null ?"上周" +  lastAver : "上周00:00");
            resultMap.put("conTL", Math.abs((lastInBedTime * lastDreamCount / 60) - (inBedTime * dreamCount / 60)));
            resultMap.put("conInTL", Math.abs((DatetimeUtils.string2date(aver, "HH:mm").getTime() -
                    DatetimeUtils.string2date(lastAver, "HH:mm").getTime())) / (1000 * 60) <= 30 ? "稳定" : "波动");
        }
        resultMap.put("dreamLenth", lenth);
        resultMap.put("dreamValue", dream);
        resultMap.put("spinTime", spin);
        resultMap.put("getupTime", getup);
        resultMap.put("time", "(" + lastWeek.get(0) + " - " + lastWeek.get(6) + ")");
        return resultMap;
    }

    @Override
    public Map<String, Object> webIndexSleepWeek(Integer userId) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        String time = DatetimeUtils.date2string(new Date(), "E@@yyyy-MM-dd");
        List<String> lastWeek = DatetimeUtils.weekSE(time.split("@@")[0], time.split("@@")[1]);
        List<String> toLastWeek = DatetimeUtils.weekSE("星期一", lastWeek.get(0));
        int dreamCount = 0;//上周睡眠天数
        long timeDiff = 0;//上周睡眠差值
        int gradeAver = 0;//平均分
        int inBedTime = 0;//上周在床时间
        int lastDreamCount = 0;//上上周睡眠天数
        int lastTimeDiff = 0;//上上周睡眠天数
        int lastInBedTime = 0;//上上周在床时间
        //上上周睡眠时间
        for (String string : toLastWeek) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", userId + "");
            map.put("time", string);
            AdminSleepMsg disperse = healthMapper.selectHealthMsgMap(map);
            if (disperse != null) {
                lastDreamCount++;
                long norTime = DatetimeUtils.string2date(string + " 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
                lastTimeDiff += norTime - disperse.getSpinintime().getTime();
                if (DatetimeUtils.compareTime(disperse.getGetuptime(),
                        DatetimeUtils.string2date(string + " 08:00:00", "yyyy-MM-dd HH:mm:ss"))) {
                    lastInBedTime += (int)((DatetimeUtils.string2date(string + " 08:00:00", "yyyy-MM-dd HH:mm:ss").getTime() - disperse.getSpinintime().getTime())
                            / (60 * 1000));
                }else {
                    lastInBedTime += (int)((disperse.getGetuptime().getTime() - disperse.getSpinintime().getTime())
                            / (60 * 1000));
                }
            }
        }
        //上周睡眠时间
        String aver = null;
        List<String> weekList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        List<Integer> dreamLenth = new ArrayList<>();//睡眠时长
        List<Integer> dreamresult = new ArrayList<>();//睡眠评分
        List<Integer> spinresult = new ArrayList<>();//上床时间
        List<Integer> getresult = new ArrayList<>();//起床时间
        for (String string : lastWeek) {
            weekList.add(DatetimeUtils.transform(string, "yyyy-MM-dd", "E").split("星期")[1]);
            dateList.add(DatetimeUtils.transform(string, "yyyy-MM-dd", "MM/dd"));
            Integer value = healthMapper.selectHealthIndex(userId, string);
            if (value != null) {
                gradeAver += value;
                dreamresult.add(value);
            }else {
                dreamresult.add(0);
            }
            Map<String, String> map = new HashMap<>();
            map.put("user_id", userId + "");
            map.put("time", string);
            AdminSleepMsg disperse = healthMapper.selectHealthMsgMap(map);
            if (disperse != null) {
                dreamCount++;
                long norTime = DatetimeUtils.string2date(string + " 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
                timeDiff += norTime - disperse.getSpinintime().getTime();
                String spinTime = DatetimeUtils.date2string(disperse.getSpinintime(), "HH");
                spinresult.add(Integer.valueOf(spinTime));
                String getupTime = DatetimeUtils.date2string(disperse.getGetuptime(), "HH");
                if (DatetimeUtils.compareTime(disperse.getGetuptime(),
                        DatetimeUtils.string2date(string + " 08:00:00", "yyyy-MM-dd HH:mm:ss"))) {
                    int timeLenth = (int)((DatetimeUtils.string2date(string + " 08:00:00", "yyyy-MM-dd HH:mm:ss").getTime() - disperse.getSpinintime().getTime())
                            / (60 * 1000));
                    inBedTime += timeLenth;
                    dreamLenth.add(timeLenth / 60);
                    getresult.add(8);
                }else {
                    if (DatetimeUtils.compareTime(disperse.getGetuptime(),
                            DatetimeUtils.string2date(string + " 00:00:00", "yyyy-MM-dd HH:mm:ss"))) {
                        getresult.add(Integer.valueOf(getupTime));
                    } else {
                        getresult.add(0);
                    }
                    int timeLenth = (int)((disperse.getGetuptime().getTime() - disperse.getSpinintime().getTime())
                            / (60 * 1000));
                    inBedTime += timeLenth;
                    dreamLenth.add(timeLenth / 60);
                }
            }else {
                dreamLenth.add(0);
                spinresult.add(0);
                getresult.add(0);
            }
        }
        resultMap.put("weeklist", weekList);
        resultMap.put("datalist", dateList);
        resultMap.put("indexlist", dreamresult);
        resultMap.put("lenthlist", dreamLenth);
        resultMap.put("spinlist", spinresult);
        resultMap.put("getlist", getresult);
        if (dreamCount > 0 && gradeAver > 0) {
            timeDiff = timeDiff / dreamCount;
            long dreamTime = DatetimeUtils.string2date("00:00:00", "HH:mm:ss").getTime();
            if (timeDiff > 0) {
                aver = DatetimeUtils.date2string(new Date(dreamTime - timeDiff), "HH:mm");
            }else {
                aver = DatetimeUtils.date2string(new Date(dreamTime + timeDiff), "HH:mm");
            }
            resultMap.put("weekAver", aver);
            resultMap.put("weekGradeAver", String.valueOf(gradeAver / dreamCount));
        }else {
            resultMap.put("weekAver", "00:00");
            resultMap.put("weekGradeAver", "0");
        }
        if (inBedTime > 0) {
            inBedTime = inBedTime / dreamCount;
            if (inBedTime > 60) {
                resultMap.put("weekInBed", (inBedTime / 60) + "h" + (inBedTime % 60) + "min");
            }else {
                resultMap.put("weekInBed", "0h" + (inBedTime % 60) + "min");
            }
        }else {
            resultMap.put("weekInBed", "0h0min");
        }
        resultMap.put("tWeekIn", inBedTime > 0?"本周" + (inBedTime * dreamCount / 60) + "小时":"本周0小时");
        resultMap.put("lWeekIn", lastInBedTime > 0?"上周" + (lastInBedTime * lastDreamCount / 60) + "小时":"上周0小时");
        resultMap.put("tInBed", aver != null ?"本周" +  aver : "本周00:00");
        if (inBedTime == 0 || lastInBedTime == 0) {
            resultMap.put("conTL", 0);
            resultMap.put("conInTL", "未知");
            resultMap.put("lInBed", "上周00:00");
        }else {
            String lastAver = null;
            lastTimeDiff = lastTimeDiff / lastDreamCount;
            long dreamTime = DatetimeUtils.string2date("00:00:00", "HH:mm:ss").getTime();
            if (timeDiff > 0) {
                lastAver = DatetimeUtils.date2string(new Date(dreamTime - lastTimeDiff), "HH:mm");
            }else {
                lastAver = DatetimeUtils.date2string(new Date(dreamTime + lastTimeDiff), "HH:mm");
            }
            resultMap.put("lInBed", lastAver != null ?"上周" +  lastAver : "上周00:00");
            resultMap.put("conTL", Math.abs((lastInBedTime * lastDreamCount / 60) - (inBedTime * dreamCount / 60)));
            resultMap.put("conInTL", Math.abs((DatetimeUtils.string2date(aver, "HH:mm").getTime() -
                    DatetimeUtils.string2date(lastAver, "HH:mm").getTime())) / (1000 * 60) <= 30 ? "稳定" : "波动");
        }
        resultMap.put("time", "(" + lastWeek.get(0) + " - " + lastWeek.get(6) + ")");
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateAdminBodyMsg(AdminBodyMsg body) {
        AdminBodyMsg adminBodyMsg = healthMapper.selectBodyToday(body.getUser_id());
        if (adminBodyMsg == null) {
            healthMapper.insertAdminBodyMsg(body);
        }else{
            healthMapper.updateAdminBodyMsg(body);
        }
    }

    @Override
    public Map<String, Object> weekHealthRisk(Integer userId) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        String time = DatetimeUtils.date2string(new Date(), "E@@yyyy-MM-dd");
        String sixDay = DatetimeUtils.reduceDay(time.split("@@")[1], -6);
        List<String> lastWeek = DatetimeUtils.weekSE(time.split("@@")[0], time.split("@@")[1]);
        List<String> toLastWeek = DatetimeUtils.weekSE("星期一", lastWeek.get(0));
        resultMap.put("time", lastWeek.get(0) + " - " + lastWeek.get(lastWeek.size() - 1));
        int lasth = 0;
        int lastb = 0;
        int blasth = 0;
        int blastb = 0;
        List<Map<String, Object>> healthGrade = new ArrayList<>();
        List<Map<String, Object>> breathContent = new ArrayList<>();
        List<Map<String, Object>> lbreathContent = new ArrayList<>();
        Map<String, String> high = new LinkedHashMap<>();
        Map<String, String> breath = new LinkedHashMap<>();
        for (int i = 0; i < lastWeek.size(); i++) {
            Map<String, Object> grede = new LinkedHashMap<>();
            Map<String, Object> content = new LinkedHashMap<>();
            Map<String, Object> lcontent = new LinkedHashMap<>();
            String x = DatetimeUtils.transform(lastWeek.get(i), "yyyy-MM-dd", "MM.dd");
            grede.put("xAxe", x);
            content.put("xAxe", x);
            lcontent.put("xAxe", x);
            high.put("h" + (i + 1) + "1", x);
            high.put("h" + (i + 1) + "2", "2");
            high.put("h" + (i + 1) + "3", "2");
            breath.put("b" + (i + 1) + "1", x);
            breath.put("b" + (i + 1) + "2", "2");
            breath.put("b" + (i + 1) + "3", "2");
            Integer g = healthMapper.selectHealthIndex(userId, lastWeek.get(i));
            if (g != null) {
                grede.put("value", g);
            } else {
                grede.put("value", 0);
            }
            Map<String, String> map = new HashMap<>();
            map.put("user_id", userId + "");
            map.put("time", lastWeek.get(i));
            AdminSleepMsg disperse = healthMapper.selectHealthMsgMap(map);
            if (disperse != null) {
                breath.put("b" + (i + 1) + "2", "1");
                if (disperse.getHigh_blood() == 1) {
                    lasth++;
                    high.put("h" + (i + 1) + "2", "0");
                } else {
                    high.put("h" + (i + 1) + "2", "1");
                }
                if (disperse.getBreath_block() > 0) {
                    lastb++;
                }
                content.put("value", disperse.getBreath_block());
            } else {
                content.put("value", 0);
            }
            map.put("time", toLastWeek.get(i));
            AdminSleepMsg lDisperse = healthMapper.selectHealthMsgMap(map);
            if (lDisperse != null && lDisperse.getHigh_blood() != null) {
                breath.put("b" + (i + 1) + "3", "1");
                if (lDisperse.getHigh_blood() == 1) {
                    blasth++;
                    high.put("h" + (i + 1) + "3", "0");
                } else {
                    high.put("h" + (i + 1) + "3", "1");
                }
                if (lDisperse.getBreath_block() > 0) {
                    blastb++;
                }
                lcontent.put("value", lDisperse.getBreath_block());
            } else {
                lcontent.put("value", 0);
            }
            healthGrade.add(grede);
            breathContent.add(content);
            lbreathContent.add(lcontent);
        }
        resultMap.put("healthList", healthGrade);
        List<Map<String, String>> contentList = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            Map<String, String> msg = new HashMap<>();
            if (j == 0) {
                msg.put("title", "血压大数据");
                msg.put("content", String.valueOf(Math.abs(lasth - blasth)));
                msg.put("value", "上周异常" + blasth + "天 本周异常" + lasth + "天");
            } else if (j == 1) {
                msg.put("title", "呼吸大数据");
                msg.put("content", String.valueOf(Math.abs(lastb - blastb)));
                msg.put("value", "上周异常" + blastb + "天 本周异常" + lastb + "天");
            } else if (j == 2) {
                msg.put("title", "心血管大数据");
                msg.put("content", "0");
                msg.put("value", "上周异常0天 本周异常0天");
            }
            contentList.add(msg);
        }
        resultMap.put("content", contentList);
        resultMap.put("breathCon", breathContent);
        resultMap.put("lbreathCon", lbreathContent);
        resultMap.put("hcon", high);
        resultMap.put("bcon", breath);
        return resultMap;
    }

    @Override
    public Map<String, Object> monthHealthRisk(Integer userId) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Map<String, String> month = DatetimeUtils.returnFirstLastMonth(1);
        int year = Integer.valueOf(DatetimeUtils.transform(month.get("first"), "yyyy-MM-dd", "yyyy"));
        int monthday = Integer.valueOf(DatetimeUtils.transform(month.get("first"), "yyyy-MM-dd", "MM"));
        int days = DatetimeUtils.reMonthDay(year, monthday);
        String today = month.get("first");
        resultMap.put("time", DatetimeUtils.transform(today, "yyyy-MM-dd", "yyyy年MM月"));
        String sixtyDay = DatetimeUtils.reduceDay(DateUtil.today(), -29);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            Map<String, Object> result = new HashMap<>();
            if (i == days) {
                result.put("xAxe", "日期");
                result.put("value", 0);
                list.add(result);
                break;
            }
            result.put("xAxe", DatetimeUtils.transform(today, "yyyy-MM-dd", "dd"));
            Integer grade = healthMapper.selectHealthIndex(userId, today);
            if (grade != null) {
                result.put("value", grade);
            }else {
                result.put("value", 0);
            }
            list.add(result);
            today = DatetimeUtils.reduceDay(today, +1);
        }
        resultMap.put("monthList", list);
        Integer weekNOH = healthMapper.selectUnDay(userId, month.get("first"), month.get("last"), 1);
        Integer weekOKH = healthMapper.selectUnDay(userId, month.get("first"), month.get("last"), 2);
        Integer weekNOB = healthMapper.selectUnDay(userId, month.get("first"), month.get("last"), 3);
        Integer weekOKB = healthMapper.selectUnDay(userId, month.get("first"), month.get("last"), 4);
        Map<String, String> lmonth = DatetimeUtils.returnFirstLastMonth(2);
        Integer lweekNOH = healthMapper.selectUnDay(userId, lmonth.get("first"), lmonth.get("last"), 1);
        Integer lweekOKH = healthMapper.selectUnDay(userId, lmonth.get("first"), lmonth.get("last"), 2);
        Integer lweekNOB = healthMapper.selectUnDay(userId, lmonth.get("first"), lmonth.get("last"), 3);
        Integer lweekOKB = healthMapper.selectUnDay(userId, lmonth.get("first"), lmonth.get("last"), 4);
        List<Map<String, Object>> yh = new ArrayList<>();
        List<Map<String, Object>> nh = new ArrayList<>();
        List<Map<String, Object>> yb = new ArrayList<>();
        List<Map<String, Object>> nb = new ArrayList<>();
        List<Map<String, Object>> ya = new ArrayList<>();
        List<Map<String, Object>> na = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> yhm = new HashMap<>();
            Map<String, Object> nhm = new HashMap<>();
            Map<String, Object> ybm = new HashMap<>();
            Map<String, Object> nbm = new HashMap<>();
            Map<String, Object> yam = new HashMap<>();
            Map<String, Object> nam = new HashMap<>();
            if (i == 0) {
                yhm.put("xAxe", "上月");
                yhm.put("value", lweekOKH);
                nhm.put("xAxe", "上月");
                nhm.put("value", lweekNOH);
                ybm.put("xAxe", "上月");
                ybm.put("value", lweekOKB);
                nbm.put("xAxe", "上月");
                nbm.put("value", lweekNOB);
                yam.put("xAxe", "上月");
                yam.put("value", lweekOKH + lweekNOH);
                nam.put("xAxe", "上月");
                nam.put("value", "0");
                yh.add(yhm);
                nh.add(nhm);
                yb.add(ybm);
                nb.add(nbm);
                ya.add(yam);
                na.add(nam);
            }else if (i == 1) {
                yhm.put("xAxe", "本月");
                yhm.put("value", weekOKH);
                nhm.put("xAxe", "本月");
                nhm.put("value", weekNOH);
                ybm.put("xAxe", "本月");
                ybm.put("value", weekOKB);
                nbm.put("xAxe", "本月");
                nbm.put("value", weekNOB);
                yam.put("xAxe", "本月");
                yam.put("value", weekOKH + weekNOH);
                nam.put("xAxe", "本月");
                nam.put("value", "0");
                yh.add(yhm);
                nh.add(nhm);
                yb.add(ybm);
                nb.add(nbm);
                ya.add(yam);
                na.add(nam);
            }
        }
        resultMap.put("norH", yh);
        resultMap.put("offH", nh);
        resultMap.put("norB", yb);
        resultMap.put("offB", nb);
        resultMap.put("norA", ya);
        resultMap.put("offA", na);
        return resultMap;
    }

    @Override
    public Map<String, Integer> blookPressureToday(Integer userId) {
        Map<String, Integer> resultMap = new HashMap<>();
        AdminBodyMsg bodyMsg = healthMapper.selectBodyToday(userId);
        if (bodyMsg == null || null == bodyMsg.getLow_pressure() || null == bodyMsg.getHigh_pressure()) {
            resultMap.put("low", 0);
            resultMap.put("high", 0);
            resultMap.put("value", 0);
        }else {
            resultMap.put("low", bodyMsg.getLow_pressure());
            resultMap.put("high", bodyMsg.getHigh_pressure());
            boolean lowb = false;
            boolean highb = false;
            if (bodyMsg.getLow_pressure() >= 60 && bodyMsg.getLow_pressure() <= 90){
                lowb = true;
            }
            if (bodyMsg.getHigh_pressure() >= 90 && bodyMsg.getHigh_pressure() <= 140){
                highb = true;
            }
            if (bodyMsg.getLow_pressure() < 60){
                resultMap.put("value", 8);
            }else if (bodyMsg.getHigh_pressure() > 140 && bodyMsg.getHigh_pressure() <= 149){
                resultMap.put("value", 20);
            }else if (bodyMsg.getHigh_pressure() > 149 && bodyMsg.getHigh_pressure() <= 159){
                resultMap.put("value", 28);
            }else if (bodyMsg.getHigh_pressure() > 159 && bodyMsg.getHigh_pressure() <= 169){
                resultMap.put("value", 36);
            }else if (lowb && highb){
                resultMap.put("value", 12);
            }else if (!lowb && highb){
                resultMap.put("value", 22);
            }else if (lowb && !highb){
                resultMap.put("value", 22);
            }else {
                resultMap.put("value", 44);
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> blookSugarToday(Integer userId) {
        Map<String, Object> resultMap = new HashMap<>();
        AdminBodyMsg bodyMsg = healthMapper.selectBodyToday(userId);
        if (bodyMsg == null || null == bodyMsg.getEmpty_sugar() || null == bodyMsg.getDinner_sugar()) {
            resultMap.put("empty", 0);
            resultMap.put("dinner", 0);
            resultMap.put("value", 0);
        }else {
            resultMap.put("empty", bodyMsg.getEmpty_sugar());
            resultMap.put("dinner", bodyMsg.getDinner_sugar());
            if (bodyMsg.getEmpty_sugar() <= 3.9 && bodyMsg.getDinner_sugar() >= 6.1){
                int value = (int) (bodyMsg.getEmpty_sugar() / 0.088);
                resultMap.put("value", 26 + value > 50 ? 50 : 26 + value);
            }else if (bodyMsg.getEmpty_sugar() < 3.9){
                resultMap.put("value", (int) (bodyMsg.getEmpty_sugar() / 0.152));
            }else if (bodyMsg.getEmpty_sugar() > 6.1 && bodyMsg.getEmpty_sugar() <= 7){
                int value = (int) ((bodyMsg.getEmpty_sugar() - 6.1) / 0.036);
                resultMap.put("value", 51 + value > 75 ? 75 : 51 + value);
            }else if (bodyMsg.getEmpty_sugar() > 7){
                resultMap.put("value", bodyMsg.getEmpty_sugar() >= 8 ? 100 : (76 + (int)((bodyMsg.getEmpty_sugar() - 7) / 0.04) > 100 ? 100 : 76 + (int)((bodyMsg.getEmpty_sugar() - 7) / 0.04)));
            }else if (bodyMsg.getDinner_sugar() < 6.7){
                resultMap.put("value", (int)(bodyMsg.getDinner_sugar() / 0.264));
            }else if (bodyMsg.getDinner_sugar() >= 6.7 && bodyMsg.getDinner_sugar() <= 9.4){
                int value = (int)((bodyMsg.getDinner_sugar() - 6.7) / 0.108);
                resultMap.put("value", 26 + value > 50 ? 50 : 26 + value);
            }else if (bodyMsg.getDinner_sugar() < 11){
                int value = (int)((bodyMsg.getDinner_sugar() - 9.4) / 0.064);
                resultMap.put("value", 51 + value > 75 ? 75 : 51 + value);
            }else {
                resultMap.put("value", bodyMsg.getDinner_sugar() > 12 ? 100 : (76 + (int)((bodyMsg.getDinner_sugar() - 11) / 0.04)) > 100 ? 100 : 76 + (int)((bodyMsg.getDinner_sugar() - 11) / 0.04));
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> weightToday(Integer userId) {
        Map<String, Object> resultMap = new HashMap<>();
        AdminBodyMsg bodyMsg = healthMapper.selectBodyToday(userId);
        if (null == bodyMsg){
            resultMap.put("weight", 0);
            resultMap.put("fat", 0);
            resultMap.put("value", 0);
        }else {
            resultMap.put("weight", bodyMsg.getWeight() != null ? bodyMsg.getWeight() : 0);
            resultMap.put("fat", bodyMsg.getBodyfat() != null ? bodyMsg.getBodyfat() : 0);
            resultMap.put("value", 0);
        }
        return  resultMap;
    }

    @Override
    public Map<String, Object> heatToday(Integer userId) {
        Map<String, Object> resultMap = new HashMap<>();
        AdminBodyMsg bodyMsg = healthMapper.selectBodyToday(userId);
        if (bodyMsg == null || null == bodyMsg.getBodyheat()) {
            resultMap.put("heat", 0);
            resultMap.put("aveheat", 0);
            resultMap.put("value", 0);
        }else {
            DecimalFormat decimal = new DecimalFormat("0.0");
            resultMap.put("heat", bodyMsg.getBodyheat());
            resultMap.put("aveheat", decimal.format(healthMapper.selectAveHeat(userId)));
            if (bodyMsg.getBodyheat() < 36.1 && bodyMsg.getBodyheat() >= 35){
                int value = (int)((bodyMsg.getBodyheat() - 35) / 0.055);
                resultMap.put("value", 21 + value > 40 ? 40 : 21 + value);
            }else if (bodyMsg.getBodyheat() >= 36.1 && bodyMsg.getBodyheat() < 37.2){
                int value = (int)((bodyMsg.getBodyheat() - 36.1) / 0.055);
                resultMap.put("value", 41 + value > 60 ? 60 : 41 + value);
            }else if (bodyMsg.getBodyheat() < 35){
                resultMap.put("value", (int)(bodyMsg.getBodyheat() / 1.75));
            }else if (bodyMsg.getBodyheat() < 38){
                int value = (int)((bodyMsg.getBodyheat() - 37.2) / 0.04);
                resultMap.put("value", 61 + value > 80 ? 80 : 61 + value);
            }else {
                resultMap.put("value", bodyMsg.getBodyheat() >= 39 ? 100 : (int)(81 + (int)((bodyMsg.getBodyheat() - 38) / 0.05)) > 100 ? 100 : (int)(81 + (int)((bodyMsg.getBodyheat() - 38) / 0.05)));
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> heartToday(Integer userId) {
        Map<String, Integer> resultMap = new HashMap<>();
        String eqpNum = adminMapper.selectAdminEqpNum(userId);
        if (StringUtils.isNotEmpty(eqpNum)) {
            String juage = (String) redis.get("juage" + eqpNum);
            if (StringUtils.isNotEmpty(juage) && juage.equals("1")){
                Integer hr = null;
                String eqpStart = (String) redis.get("eqpstart@@" + eqpNum);
                if (StringUtils.isNotEmpty(eqpStart)){
                    Date start = DatetimeUtils.string2date(eqpStart, DatetimeUtils.YYYY_MM_DD_HH_MM);
                    long eqpTimeDiff = DateUtil.between(start, new Date(), DateUnit.MINUTE);
                    if (eqpTimeDiff <= 10) {
                        String sumData = Fdata.getme().getFakeData((int)eqpTimeDiff);
                        if (sumData != null) {
                            hr = Integer.valueOf(sumData.split("@@")[0]);
                        }
                    }
                }
                if (hr == null){
                    String endtime = DatetimeUtils.date2string(new Date(), "yyyy-MM-dd HH:mm");
                    endtime = DatetimeUtils.changeMin(endtime + ":00", "yyyy-MM-dd HH:mm:ss", -10, "yyyy-MM-dd HH:mm:ss");
                    String starttime = DatetimeUtils.changeMin(endtime, "yyyy-MM-dd HH:mm:ss", -1, "yyyy-MM-dd HH:mm");
                    Integer heart = healthMapper.selectHeart(1, "hr_sdata_" + (userId % 10), starttime + ":00", starttime + ":59", "hr_id", "hr_time");
                    if (null != heart) {
                        hr = heart;
                    }else {
                        hr = 0;
                    }
                }
                if (null != hr){
                    resultMap.put("heart", hr);
                    if (hr < 60){
                        resultMap.put("value", (int)(hr / 2.4));
                    }else if (hr >= 60 && hr <= 100){
                        int value = (int) ((hr - 60) / 1.6);
                        resultMap.put("value", 26 + value > 51 ? 50 : 26 + value);
                    }else if (hr < 120){
                        int value = (int) ((hr - 100) / 0.8);
                        resultMap.put("value", 51 + value > 76 ? 75 : 51 + value);
                    }else {
                        resultMap.put("value", hr > 140 ? 100 : 76 + (int) ((hr - 120) / 0.8) > 120 ? 100 : 76 + (int) ((hr - 120) / 0.8));
                    }
                }else {
                    resultMap.put("heart", 0);
                    resultMap.put("value", 0);
                }
            }else {
                resultMap.put("heart", 0);
                resultMap.put("value", 0);
            }
        }else {
            resultMap.put("heart", 0);
            resultMap.put("value", 0);
        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> breathToday(Integer userId) {
        Map<String, Integer> resultMap = new HashMap<>();
        String eqpNum = adminMapper.selectAdminEqpNum(userId);
        if (StringUtils.isNotEmpty(eqpNum)) {
            String juage = (String) redis.get("juage" + eqpNum);
            if (StringUtils.isNotEmpty(juage) && juage.equals("1")){
                Integer bt = null;
                String eqpStart = (String) redis.get("eqpstart@@" + eqpNum);
                if (StringUtils.isNotEmpty(eqpStart)){
                    Date start = DatetimeUtils.string2date(eqpStart, DatetimeUtils.YYYY_MM_DD_HH_MM);
                    long eqpTimeDiff = DateUtil.between(start, new Date(), DateUnit.MINUTE);
                    if (eqpTimeDiff <= 10) {
                        String sumData = Fdata.getme().getFakeData((int)eqpTimeDiff);
                        if (sumData != null) {
                            bt = Integer.valueOf(sumData.split("@@")[1]);
                        }
                    }
                }
                if (bt == null){
                    String endtime = DatetimeUtils.date2string(new Date(), "yyyy-MM-dd HH:mm");
                    endtime = DatetimeUtils.changeMin(endtime, "yyyy-MM-dd HH:mm:ss", -10, "yyyy-MM-dd HH:mm:ss");
                    String starttime = DatetimeUtils.changeMin(endtime, "yyyy-MM-dd HH:mm:ss", -1, "yyyy-MM-dd HH:mm");
                    Integer breath = healthMapper.selectHeart(1, "bt_sdata_" + (userId % 10), starttime + ":00", starttime + ":59", "bt_id", "bt_time");
                    if (null != breath) {
                        bt = breath;
                    }else {
                        bt = 0;
                    }
                }
                if (null != bt){
                    resultMap.put("breath", bt);
                    if (bt < 12){
                        resultMap.put("value", (int)(bt / 0.48));
                    }else if (bt >= 12 && bt <= 20){
                        int value = (int) ((bt - 12) / 0.32);
                        resultMap.put("value", 26 + value > 51 ? 50 : 26 + value);
                    }else if (bt < 30){
                        int value = (int) ((bt - 20) / 0.4);
                        resultMap.put("value", 51 + value > 76 ? 75 : 51 + value);
                    }else {
                        resultMap.put("value", bt > 40 ? 100 : 76 + (int) ((bt - 30) / 0.4) > 40 ? 100 : 76 + (int) ((bt - 30) / 0.4));
                    }
                }else {
                    resultMap.put("breath", 0);
                    resultMap.put("value", 0);
                }
            }else {
                resultMap.put("breath", 0);
                resultMap.put("value", 0);
            }
        }else {
            resultMap.put("breath", 0);
            resultMap.put("value", 0);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> stepToday(Integer userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Long step = healthMapper.selectUserStepToday(userId);
        if (null == step){
            Long jedisStep = (Long) redis.get(userId + "step");
            if (jedisStep != null){
                resultMap.put("step", jedisStep);
                resultMap.put("expend", StringUtils.getCalorieByStep(jedisStep));
                if (jedisStep < 5000){
                    resultMap.put("hint", "走路-散步 120分钟 （约510千卡）");
                    resultMap.put("value", (int)(jedisStep / 200));
                }else if (jedisStep >= 5000 && jedisStep < 8000){
                    resultMap.put("hint", "骑车-单车 120分钟 （约830千卡）");
                    resultMap.put("value", 26 + (int)((jedisStep - 5000) / 120) > 50 ? 50 : 26 + (int)((jedisStep - 5000) / 120));
                }else if (jedisStep >= 8000 && jedisStep < 10000){
                    resultMap.put("hint", "运动-游泳 120分钟 （约1100千卡）");
                    resultMap.put("value", 51 + (int)((jedisStep - 8000) / 120) > 75 ? 75 : 51 + (int)((jedisStep - 8000) / 120));
                }else {
                    resultMap.put("hint", "跑步-慢跑 120分钟 （约1310千卡）");
                    resultMap.put("value", jedisStep >= 20000 ? 100 : 76 + (int)((jedisStep - 10000) / 400) > 100 ? 100 : 76 + (int)((jedisStep - 10000) / 400));
                }
            }else {
                resultMap.put("step", 0);
                resultMap.put("expend", 0);
                resultMap.put("hint", "走路-散步 120分钟 （约510千卡）");
                resultMap.put("value", 0);
            }
        }else {
            resultMap.put("step", step);
            resultMap.put("expend", StringUtils.getCalorieByStep(step));
            if (step < 5000){
                resultMap.put("hint", "走路-散步 120分钟 （约510千卡）");
                resultMap.put("value", (int)(step / 200));
            }else if (step >= 5000 && step < 8000){
                resultMap.put("hint", "骑车-单车 120分钟 （约830千卡）");
                resultMap.put("value", 26 + (int)((step - 5000) / 120) > 50 ? 50 : 26 + (int)((step - 5000) / 120));
            }else if (step >= 8000 && step < 10000){
                resultMap.put("hint", "运动-游泳 120分钟 （约1100千卡）");
                resultMap.put("value", 51 + (int)((step - 8000) / 120) > 75 ? 75 : 51 + (int)((step - 8000) / 120));
            }else {
                resultMap.put("hint", "跑步-慢跑 120分钟 （约1310千卡）");
                resultMap.put("value", step >= 20000 ? 100 : 76 + (int)((step - 10000) / 400) > 100 ? 100 : 76 + (int)((step - 10000) / 400));
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> stepUserDay(Integer userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Long steps = 0L;
        Long step = healthMapper.selectUserStepToday(userId);
        if (null == step) {
            Long jedisStep = (Long) redis.get(userId + "step");
            if (jedisStep != null) {
                steps = jedisStep;
            }
        }else {
            steps = step;
        }
        resultMap.put("time", DateUtil.today());
        resultMap.put("step", steps);
        List<Map<String, Object>> anaList = new ArrayList<>();
        for (int i = 0,j = 25; i < j; i++){
            Map<String, Object> map = new HashMap<>();
            if (i == 24){
                map.put("xAxe", 0 + "时");
            }else {
                map.put("xAxe", i + "时");
            }
            map.put("value", 0);
            anaList.add(map);
        }
        resultMap.put("analyze", anaList);
        List<String> week = DatetimeUtils.weekRun();
        long sum = 0L;
        int count = 0;
        List<Map<String, Object>> runList = new ArrayList<>();
        for (String time: week) {
            Map<String, Object> map = new HashMap<>();
            map.put("xAxe", time.split("&&")[1]);
            Long dateStep = healthMapper.selectUserStepDate(userId, time.split("&&")[0]);
            if (null != dateStep){
                sum += dateStep;
                count++;
            }
            map.put("value", dateStep != null ? dateStep : 0);
            runList.add(map);
        }
        if (count == 0){
            count = 1;
        }
        resultMap.put("ave", sum / count);
        resultMap.put("steplist", runList);
        return resultMap;
    }

    @Override
    public Map<String, Object> stepUserWeek(Integer userId) {
        Map<String, Object> resultMap = new HashMap<>();
        Long steps = 0L;
        resultMap.put("time", DatetimeUtils.transform(DatetimeUtils.reduceDay(DateUtil.today(), -7), "yyyy-MM-dd", "yyyy年MM月dd日") + "～" +  DatetimeUtils.transform(DatetimeUtils.reduceDay(DateUtil.today(), -1), "yyyy-MM-dd", "dd日"));
        List<String> week = DatetimeUtils.weekRun();
        long sum = 0L;
        int count = 0;
        List<Map<String, Object>> runList = new ArrayList<>();
        for (String time: week) {
            Map<String, Object> map = new HashMap<>();
            map.put("xAxe", "周" + time.split("&&")[1]);
            Long dateStep = healthMapper.selectUserStepDate(userId, time.split("&&")[0]);
            if (null != dateStep){
                sum += dateStep;
                count++;
            }
            map.put("value", dateStep != null ? dateStep : 0);
            runList.add(map);
        }
        if (count == 0){
            count = 1;
        }
        resultMap.put("ave", sum / count);
        resultMap.put("steplist", runList);
        Long thisWeek = healthMapper.selectUserStepDateAndDate(userId, DatetimeUtils.getWeekStart(), DateUtil.today());
        Long lastWeek = healthMapper.selectUserStepDateAndDate(userId, DatetimeUtils.reduceDay(DateUtil.today(), -7), DatetimeUtils.reduceDay(DateUtil.today(), -1));
        long tweek = 0L;
        long lweek = 0L;
        if (null != thisWeek){
            tweek = thisWeek;
        }
        if (null != lastWeek){
            lweek = lastWeek;
        }
        if (tweek > lweek){
            resultMap.put("des", "本周您一天完成的步数比上周多");
        }else if (lweek > tweek){
            resultMap.put("des", "上周您一天完成的步数比本周多");
        }else {
            resultMap.put("des", "本周您一天完成的步数与上周相同");
        }
        resultMap.put("thisweek", tweek);
        resultMap.put("lastweek", lweek);
        return resultMap;
    }

}
