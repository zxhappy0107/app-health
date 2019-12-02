package cn.anshirui.store.appdevelop.controller;

import cn.anshirui.store.appdevelop.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public Map<String, Object> toLogin(){
        Map<String, Object> resultMap = new HashMap<>();
        return  resultMap;
    }

}
