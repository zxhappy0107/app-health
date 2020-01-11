package cn.anshirui.store.appdevelop.controller;

import cn.anshirui.store.appdevelop.entity.AdminBodyMsg;
import cn.anshirui.store.appdevelop.entity.Response;
import cn.anshirui.store.appdevelop.entity.ResponseResult;
import cn.anshirui.store.appdevelop.mapper.AdminMapper;
import cn.anshirui.store.appdevelop.repository.WebLog;
import cn.anshirui.store.appdevelop.service.IndexService;
import cn.anshirui.store.appdevelop.service.LoginService;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName 登录API
 *@Description TODO
 *@Author zhangxuan
 *@Date 10:56
 *Version 1.0
 **/

@RestController
@RequestMapping(value = "index")
public class IndexController {

    private Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    /**
     * @Author zhangxuan
     * @Description //TODO 首页展示
     * @Date 16:40 2019/12/9
     * @Param []
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> mainWeb(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (null != userId){
                return Response.makeOKRsp(indexService.indexShowMsg(userId));
            }
            return Response.makeRsp(50004);
        }catch (Exception e){
            log.error("index is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 睡眠展示
     * @Date 14:59 2019/12/11
     * @Param []
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/healthed", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> healthWeb(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (null != userId){
                return Response.makeOKRsp(indexService.indexSleepMsg(userId));
            }
            return Response.makeRsp(50004);
        }catch (Exception e){
            log.error("health is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 睡眠日分析
     * @Date 17:15 2019/12/11
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/dayheal", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> healthDayWeb(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            String time = jsonObject.getStr("time");
            if (null != userId){
                return Response.makeOKRsp(indexService.indexSleepDay(userId, time));
            }
            return Response.makeRsp(50004);
        }catch (Exception e){
            log.error("health day is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 睡眠周分析
     * @Date 10:23 2019/12/12
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/weekheal", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> healthWeekWeb(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (null != userId){
                return Response.makeOKRsp(indexService.indexSleepWeek(userId));
            }
            return Response.makeRsp(50004);
        }catch (Exception e){
            log.error("health day is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 更新血压信息
     * @Date 17:10 2019/12/12
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "/updatebp", method = RequestMethod.POST)
    public ResponseResult updateBloodPressure(
        @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            Integer low = jsonObject.getInt("low");
            Integer high = jsonObject.getInt("high");
            if (null == userId || null == low || null == high){
                return Response.makeRsp(50004);
            }else{
                AdminBodyMsg bodyMsg = new AdminBodyMsg();
                bodyMsg.setLow_pressure(low);
                bodyMsg.setHigh_pressure(high);
                bodyMsg.setUser_id(userId);
                indexService.updateAdminBodyMsg(bodyMsg);
                return Response.makeOKRsp();
            }
        }catch (Exception e){
            log.error("update blood pressure day is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 更新血糖信息
     * @Date 17:11 2019/12/12
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "/updatebs", method = RequestMethod.POST)
    public ResponseResult updateBloodSugar(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            Double emtry = jsonObject.getDouble("emtry");
            Double dinner = jsonObject.getDouble("dinner");
            if (null == userId || null == emtry || null == dinner){
                return Response.makeRsp(50004);
            }else{
                AdminBodyMsg bodyMsg = new AdminBodyMsg();
                bodyMsg.setEmpty_sugar(emtry);
                bodyMsg.setDinner_sugar(dinner);
                bodyMsg.setUser_id(userId);
                indexService.updateAdminBodyMsg(bodyMsg);
                return Response.makeOKRsp();
            }
        }catch (Exception e){
            log.error("update blood sugar day is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 更新体重信息
     * @Date 17:13 2019/12/12
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "/updatewgt", method = RequestMethod.POST)
    public ResponseResult updateWeight(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            Double weight = jsonObject.getDouble("weight");
            Integer fat = jsonObject.getInt("fat");
            if (null == userId || null == weight || null == fat){
                return Response.makeRsp(50004);
            }else{
                AdminBodyMsg bodyMsg = new AdminBodyMsg();
                bodyMsg.setWeight(weight);
                bodyMsg.setBodyfat(fat);
                bodyMsg.setUser_id(userId);
                indexService.updateAdminBodyMsg(bodyMsg);
                return Response.makeOKRsp();
            }
        }catch (Exception e){
            log.error("update weight day is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 更新体温信息
     * @Date 17:15 2019/12/12
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "/updateheat", method = RequestMethod.POST)
    public ResponseResult updateHeat(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            Double heat = jsonObject.getDouble("heat");
            if (null == userId || null == heat){
                return Response.makeRsp(50004);
            }else{
                AdminBodyMsg bodyMsg = new AdminBodyMsg();
                bodyMsg.setBodyheat(heat);
                bodyMsg.setUser_id(userId);
                indexService.updateAdminBodyMsg(bodyMsg);
                return Response.makeOKRsp();
            }
        }catch (Exception e){
            log.error("update body heat day is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 周健康风险
     * @Date 10:19 2019/12/13
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/weekrisk", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> weekHealthRisk(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.weekHealthRisk(userId));
            }
        }catch (Exception e){
            log.error("week health risk is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 月健康风险
     * @Date 16:40 2019/12/13
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/monthrisk", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> monthHealthRisk(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.monthHealthRisk(userId));
            }
        }catch (Exception e){
            log.error("week health risk is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 血压查询
     * @Date 11:50 2019/12/15
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Integer>>
     **/
    @WebLog
    @RequestMapping(value = "/bkpre", method = RequestMethod.POST)
    public ResponseResult<Map<String, Integer>> sltBlookPressure(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.blookPressureToday(userId));
            }
        }catch (Exception e){
            log.error("blook pressure is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 血糖查询
     * @Date 11:55 2019/12/15
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/bdsar", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> sltBloodSugar(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.blookSugarToday(userId));
            }
        }catch (Exception e){
            log.error("blook sugar is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 体重查询
     * @Date 11:55 2019/12/15
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/wght", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> sltWeight(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.weightToday(userId));
            }
        }catch (Exception e){
            log.error("weight is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 体温查询
     * @Date 12:04 2019/12/15
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/bdheat", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> sltBodyheat(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.heatToday(userId));
            }
        }catch (Exception e){
            log.error("heat is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 心率查询
     * @Date 10:57 2019/12/16
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Integer>>
     **/
    @WebLog
    @RequestMapping(value = "/heartslt", method = RequestMethod.POST)
    public ResponseResult<Map<String, Integer>> sltHeart(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.heartToday(userId));
            }
        }catch (Exception e){
            log.error("heart is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 呼吸查询
     * @Date 10:57 2019/12/16
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Integer>>
     **/
    @WebLog
    @RequestMapping(value = "/breathslt", method = RequestMethod.POST)
    public ResponseResult<Map<String, Integer>> sltBreath(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.breathToday(userId));
            }
        }catch (Exception e){
            log.error("breath is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 运动查询
     * @Date 10:28 2019/12/26
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/stepslt", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> userStepSelect(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.stepToday(userId));
            }
        }catch (Exception e){
            log.error("user step is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 运动日分析
     * @Date 17:06 2019/12/26
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/runday", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> runDay(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.stepUserDay(userId));
            }
        }catch (Exception e){
            log.error("user step day is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 运动周分析
     * @Date 17:53 2019/12/26
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/runweek", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> runWeek(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else{
                return Response.makeOKRsp(indexService.stepUserWeek(userId));
            }
        }catch (Exception e){
            log.error("user step week is error", e);
            return Response.makeRsp(500);
        }
    }

}
