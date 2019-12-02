package cn.anshirui.store.appdevelop.service.Impl;

import cn.anshirui.store.appdevelop.entity.AdminUsers;
import cn.anshirui.store.appdevelop.mapper.AdminMapper;
import cn.anshirui.store.appdevelop.service.LoginService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName 登录实现
 * @Description TODO
 * @Author zhangxuan
 * @Date 14:30
 * Version 1.0
 **/
@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AdminMapper adminMapper;

    private Map<String, Object> operateToKen(Map<String, Object> map, AdminUsers user, Integer userId) {
        //根据数据库的用户信息查询Token
        AdminUsers token = adminMapper.selectUserById(userId);
        //为生成Token准备
        String TokenStr = "";
        Date date = new Date();
//        int nowTime = (int) (date.getTime() / 1000);
        //生成Token
        TokenStr = creatToken(userId, date);
        if (null == token) {
            //第一次登陆
            token = new AdminUsers();
            token.setToken(TokenStr);
            token.setUser_logintime(date);
            token.setUser_id(userId);
            adminMapper.updateUser(token);
        }else{
            //登陆就更新Token信息
            TokenStr = creatToken(userId, date);
            token.setToken(TokenStr);
            token.setUser_logintime(date);
            adminMapper.updateUser(token);
        }
//        UserQueryForm queryForm = getUserInfo(user, TokenStr);
        /* 将用户信息存入session */
        /*SessionContext sessionContext = SessionContext.getInstance();
        HttpSession session = sessionContext.getSession();
        httpSession.setAttribute("userInfo", user);*/
        //返回Token信息给客户端
//        successful(map);
//        map.put("data", queryForm);
        return map;
    }

    /**
     * @Author zhangxuan
     * @Description //TODO 生成token
     * @Date 14:39 2019/11/29
     * @Param [userId, date]
     * @return java.lang.String
     **/
    private static String creatToken(Integer userId, Date date) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT") // 设置header
                .setHeaderParam("alg", "HS256").setIssuedAt(date) // 设置签发时间
                .setExpiration(new Date(date.getTime() + 1000 * 60 * 60)) /*过期时间*/
                .claim("userId",String.valueOf(userId) ) // 设置内容
                .setIssuer("zx")// 设置签发人
                .signWith(signatureAlgorithm, "签名"); // 签名，需要算法和key
        String jwt = builder.compact();
        return jwt;
    }

}
