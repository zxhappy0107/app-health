package cn.anshirui.store.appdevelop.controller;

import cn.anshirui.store.appdevelop.entity.Response;
import cn.anshirui.store.appdevelop.entity.ResponseResult;
import cn.anshirui.store.appdevelop.repository.WebLog;
import cn.anshirui.store.appdevelop.service.HealthService;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName 健康
 * @Description TODO
 * @Author zhangxuan
 * @Date 15:47
 * Version 1.0
 **/
@RestController
@RequestMapping(value = "/heal")
public class HealthController {

    @Autowired
    private HealthService healthService;

    private static Logger log = LoggerFactory.getLogger(HealthController.class);

    /**
     * @Author zhangxuan
     * @Description //TODO 健康首页
     * @Date 17:12 2019/12/17
     * @Param []
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     **/
    @WebLog
    @RequestMapping(value = "inhealth", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> healthIndexShow(
            @RequestBody JSONObject jsonObject
            ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId != null){
                return Response.makeOKRsp(healthService.warnIndexShow(userId));
            }else {
                return Response.makeRsp(50004);
            }
        }catch (Exception e){
            log.error("health index is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 历史预警
     * @Date 17:56 2019/12/17
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @WebLog
    @RequestMapping(value = "hiswarn", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> historyWarn(
            @RequestBody JSONObject jsonObject
    ){
        try {
            Integer userId = jsonObject.getInt("userId");
            Integer page = jsonObject.getInt("page");
            Integer type = jsonObject.getInt("type");
            if (null == userId || null == page || null == type){
                return Response.makeRsp(50004);
            }else {
                return Response.makeOKRsp(healthService.warnHis(userId, type, page));
            }
        }catch (Exception e){
            log.error("history warn is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 亲人列表
     * @Date 11:00 2019/12/18
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     **/
    @WebLog
    @RequestMapping(value = "relist", method = RequestMethod.POST)
    public ResponseResult<List<Map<String, Object>>> relationList(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            if (userId == null) {
                return Response.makeRsp(50004);
            }else {
                return Response.makeOKRsp(healthService.relationShow(userId));
            }
        }catch (Exception e){
            log.error("relation list is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 添加亲人
     * @Date 11:13 2019/12/18
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "inrela", method = RequestMethod.POST)
    public ResponseResult relationInsert(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            String rePhone = jsonObject.getStr("phone");
            String reName = jsonObject.getStr("name");
            return Response.makeRsp(healthService.relationInsert(userId, rePhone, reName));
        }catch (Exception e){
            log.error("relation insert is error", e);
            return Response.makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 删除亲人
     * @Date 11:13 2019/12/18
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @WebLog
    @RequestMapping(value = "derela", method = RequestMethod.POST)
    public ResponseResult relationDlete(
            @RequestBody JSONObject jsonObject
    ){
        try{
            Integer userId = jsonObject.getInt("userId");
            Integer reId = jsonObject.getInt("reId");
            return Response.makeRsp(healthService.relationDelete(userId, reId));
        }catch (Exception e){
            log.error("relation delete is error", e);
            return Response.makeRsp(500);
        }
    }

}
