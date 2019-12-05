package cn.anshirui.store.appdevelop.controller;

import cn.anshirui.store.appdevelop.common.PassUtils;
import cn.anshirui.store.appdevelop.common.StringUtils;
import cn.anshirui.store.appdevelop.entity.Response;
import cn.anshirui.store.appdevelop.entity.ResponseResult;
import cn.anshirui.store.appdevelop.note.SMSClientUtils;
import cn.anshirui.store.appdevelop.repository.WebLog;
import cn.anshirui.store.appdevelop.service.LoginService;
import cn.anshirui.store.appdevelop.service.RedisService;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static cn.anshirui.store.appdevelop.common.StringUtils.*;

/**
 * @ClassName 免验证接口
 * @Description TODO
 * @Author zhangxuan
 * @Date 17:08
 * Version 1.0
 **/

@RestController
@RequestMapping(value = "exe")
public class ExemptController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    private Logger log = LoggerFactory.getLogger(ExemptController.class);

    /**
     * @Author zhangxuan
     * @Description //TODO 发送验证啊
     * @Date 10:44 2019/12/5
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @RequestMapping(value = "/send.info", method = RequestMethod.POST)
    public ResponseResult sendCode(
            @RequestBody JSONObject jsonObject
            ){
        try{
            String phone = jsonObject.getStr("phone");//手机号码
            Boolean exe = jsonObject.getBool("exe");//1 需要验证用户是否存在 0不需要
            if (isNotEmpty(phone) && null != exe){
                if (isMobile(phone)){
                    return new Response().makeRsp(loginService.sendCode(phone, exe));
                }
            }
            return new Response().makeRsp(50004);
        }catch (Exception e){
            log.error("send note is error", e);
            return new Response().makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 注册
     * @Date 10:44 2019/12/5
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult
     **/
    @RequestMapping(value = "/res.info", method = RequestMethod.POST)
    public ResponseResult register(
            @RequestBody JSONObject jsonObject
            ){
        try{
            String phone = jsonObject.getStr("phone");//手机号码
            String code = jsonObject.getStr("code");//验证码
            String pass = jsonObject.getStr("pass");//密码
            String msg_id = (String) redisService.get("code" + phone);//二级验证码
            if (isNotEmpty(phone) && isNotEmpty(pass) && isNotEmpty(code) && isNotEmpty(msg_id)){
                if (SMSClientUtils.SendValidSMSCode(msg_id, code)){
                    int result = loginService.register(phone, PassUtils.getMD5Str(pass));
                    return new Response().makeRsp(result);
                }else{
                    return new Response().makeRsp(50002);
                }
            }
            return new Response().makeRsp(50004);
        }catch (Exception e){
            log.error("register is error", e);
            return new Response().makeRsp(500);
        }
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 登录
     * @Date 14:57 2019/12/5
     * @Param [jsonObject]
     * @return cn.anshirui.store.appdevelop.entity.ResponseResult<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @RequestMapping(value = "/login.info", method = RequestMethod.POST)
    public ResponseResult<Map<String, Object>> login(
            @RequestBody JSONObject jsonObject
    ){
        try{
            String username = jsonObject.getStr("username");
            String password = jsonObject.getStr("password");
            if (isNotEmpty(username) && isNotEmpty(password)){
                Map<String, Object> map = loginService.login(username, password);
                int code = (int) map.get("code");
                map.remove("code");
                return new Response().makeOKRsp(map);
            }
            return new Response().makeRsp(50004);
        }catch (Exception e){
            log.error("login is error", e);
            return new Response().makeRsp(500);
        }
    }

}
