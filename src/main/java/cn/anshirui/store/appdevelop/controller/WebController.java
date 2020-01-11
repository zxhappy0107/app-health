package cn.anshirui.store.appdevelop.controller;

import cn.anshirui.store.appdevelop.common.StringUtils;
import cn.anshirui.store.appdevelop.entity.AdminUserMain;
import cn.anshirui.store.appdevelop.entity.Response;
import cn.anshirui.store.appdevelop.entity.ResponseResult;
import cn.anshirui.store.appdevelop.repository.WebLog;
import cn.anshirui.store.appdevelop.service.IndexService;
import cn.anshirui.store.appdevelop.service.MyServiec;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName web端接口实现
 * @Description TODO
 * @Author zhangxuan
 * @Date 11:01
 * Version 1.0
 **/
@RestController
@RequestMapping(value = "web")
public class WebController {

    private Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private IndexService indexService;

    @Autowired
    private MyServiec myServiec;

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
            log.error("web index is error", e);
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
            log.error("web health is error", e);
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
                return Response.makeOKRsp(indexService.webIndexSleepDay(userId, time));
            }
            return Response.makeRsp(50004);
        }catch (Exception e){
            log.error("web health day is error", e);
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
                return Response.makeOKRsp(indexService.webIndexSleepWeek(userId));
            }
            return Response.makeRsp(50004);
        }catch (Exception e){
            log.error("web health day is error", e);
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
            log.error("web blook pressure is error", e);
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
            log.error("web breath is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 我的首页展示
     * @Date 15:35 2019/12/16
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/myindex", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> myindexShow(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId != null){
                return Response.makeOKRsp(myServiec.myIndexShow(userId));
            }else {
                return Response.makeRsp(50004);
            }
        }catch (Exception e){
            log.error("web my index is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户基础信息
     * @Date 15:01 2019/12/18
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<cn.anshirui.store.appdevelop.entity.AdminUserMain>
     **/
    @WebLog
    @RequestMapping(value = "/usermsg", method = RequestMethod.POST)
    public ResponseResult<AdminUserMain> userMainMsg(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else {
                return Response.makeOKRsp(myServiec.selectUserMain(userId));
            }
        }catch (Exception e){
            log.error("web select user main message is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 更新用户基础信息
     * @Date 14:45 2019/12/18
     * @Param [adminUserMain, jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "/upmain", method = RequestMethod.POST)
    public ResponseResult updateMainMsg(
            @RequestBody AdminUserMain adminUserMain
    ){
        try{
            return Response.makeRsp(myServiec.updateUserMain(adminUserMain));
        }catch (Exception e){
            log.error("web update user main message is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 签到
     * @Date 10:16 2019/12/28
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "/punch", method = RequestMethod.POST)
    public ResponseResult punchCard(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null){
                return Response.makeRsp(50004);
            }
            int code = myServiec.punchCardUser(userId);
            if (code == 200){
                return Response.makeRsp(200, "签到成功");
            }else {
                return Response.makeRsp(code);
            }
        }catch (Exception e){
            log.error("web user punch card is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 签到记录
     * @Date 10:19 2019/12/28
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/hispunch", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> punchHis(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            String time = jsonObject.getStr("time");
            if (userId == null || StringUtils.isEmpty(time)){
                return Response.makeRsp(50004);
            }
            return Response.makeOKRsp(myServiec.userPunchRecord(userId, time));
        }catch (Exception e){
            log.error("web sign history is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户剩余积分
     * @Date 10:10 2019/12/30
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.lang.Integer>
     **/
    @WebLog
    @RequestMapping(value = "/sltban", method = RequestMethod.POST)
    public ResponseResult<Integer> userBanance(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null){
                return Response.makeRsp(50004);
            }
            return Response.makeOKRsp(myServiec.selectUserIntegral(userId));
        }catch (Exception e){
            log.error("web select user integral is error", e);
            return Response.makeRsp(500);
        }
    }

}
