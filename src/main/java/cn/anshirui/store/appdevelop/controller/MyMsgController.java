package cn.anshirui.store.appdevelop.controller;

import cn.anshirui.store.appdevelop.common.KeyWord;
import cn.anshirui.store.appdevelop.common.StringUtils;
import cn.anshirui.store.appdevelop.entity.AdminUserMain;
import cn.anshirui.store.appdevelop.entity.Response;
import cn.anshirui.store.appdevelop.entity.ResponseResult;
import cn.anshirui.store.appdevelop.repository.WebLog;
import cn.anshirui.store.appdevelop.service.MyServiec;
import cn.hutool.json.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static cn.anshirui.store.appdevelop.common.StringUtils.*;
/**
 * @ClassName 我的模块接口
 * @Description TODO
 * @Author zhangxuan
 * @Date 17:09
 * Version 1.0
 **/
@RestController
@RequestMapping(value = "mymsg")
public class MyMsgController {

    private Logger log = LoggerFactory.getLogger(MyMsgController.class);

    @Autowired
    private MyServiec myServiec;

    /**
     * @Author zhangxuan
     * @Description //TODO 上传图片
     * @Date 12:09 2019/12/6
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog(description = "图片上传")
    @RequestMapping(value = "/upicon.info", method = RequestMethod.POST)
    public ResponseResult iconUpload(
            @RequestBody JSONObject jsonObject
            ){
        try {
            Integer userId = jsonObject.getInt("userId");
            String icon = jsonObject.getStr("icon");
            if (null != userId && isNotEmpty(icon)){
                int result = myServiec.iconUpload(userId, icon);
                return Response.makeRsp(result);
            }
            return Response.makeRsp(50004);
        }catch (Exception e){
            log.error("upload icon is error", e);
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
            log.error("my index is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 健康档案
     * @Date 16:37 2019/12/16
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> healthRecord(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId != null){
                return Response.makeOKRsp(myServiec.healthRecord(userId));
            }else {
                return Response.makeRsp(50004);
            }
        }catch (Exception e){
            log.error("health record is error", e);
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
            log.error("update user main message is error", e);
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
            log.error("select user main message is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 查询用户设备绑定信息
     * @Date 16:38 2019/12/20
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.String>>
     **/
    @WebLog
    @RequestMapping(value = "/bindmsg", method = RequestMethod.POST)
    public ResponseResult<Map<String, String>> selectUserBindEqp(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            Integer type = jsonObject.getInt("type");
            if (userId == null || type == null) {
                return Response.makeRsp(50004);
            }else {
                Map<String, String> resultMap = myServiec.userBindEqp(userId);
                if (resultMap.get("eqptype").equals("0")){
                    return  Response.makeRsp(50015);
                }else if (resultMap.get("eqptype").equals(type + "")){
                    resultMap.remove("eqptype");
                    return Response.makeOKRsp(resultMap);
                }else {
                    if (type == 1){
                        return  Response.makeRsp(50010);
                    }else{
                        return  Response.makeRsp(50011);
                    }
                }
            }
        }catch (Exception e){
            log.error("select user bind eqp message is error", e);
            return Response.makeRsp(500);
        }
    };

    /**
     * @Author zhangxuan
     * @Description //TODO 绑定设备
     * @Date 16:41 2019/12/20
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public ResponseResult bindEqp(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            String eqpNum = jsonObject.getStr("eqpnum");
            Integer type = jsonObject.getInt("type");
            if (userId == null || StringUtils.isEmpty(eqpNum) || type == null) {
                return Response.makeRsp(50004);
            }else {
                if (!eqpNum.substring(3, 4).equals(type + "")){
                    if (type == 1){
                        return Response.makeRsp(50013);
                    }else {
                        return Response.makeRsp(50014);
                    }
                }
                int code = myServiec.bindEqpUser(userId, eqpNum);
                if (code == 200){
                    return Response.makeRsp(200, "绑定成功");
                }
                return Response.makeRsp(code);
            }
        }catch (Exception e){
            log.error("insert user bind eqp message is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 解绑
     * @Date 16:47 2019/12/20
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "/delbind", method = RequestMethod.POST)
    public ResponseResult delBind(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else {
                int code = myServiec.delBindUser(userId);
                if (code == 200){
                    return Response.makeRsp(200, "解绑成功");
                }
                return Response.makeRsp(code);
            }
        }catch (Exception e){
            log.error("select user bind eqp message is error", e);
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
            log.error("user punch card is error", e);
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
            log.error("sign history is error", e);
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
            log.error("select user integral is error", e);
            return Response.makeRsp(500);
        }
    }

}
