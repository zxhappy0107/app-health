package cn.anshirui.store.appdevelop.service.Impl;

import cn.anshirui.store.appdevelop.common.KeyWord;
import cn.anshirui.store.appdevelop.common.PassUtils;
import cn.anshirui.store.appdevelop.entity.AdminUserMain;
import cn.anshirui.store.appdevelop.entity.AdminUsers;
import cn.anshirui.store.appdevelop.entity.Version;
import cn.anshirui.store.appdevelop.mapper.AdminMapper;
import cn.anshirui.store.appdevelop.note.SMSClientUtils;
import cn.anshirui.store.appdevelop.service.LoginService;
import cn.anshirui.store.appdevelop.service.RedisService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisService redis;

    public Map<String, Object> login(String username, String pass) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        //根据数据库的用户信息查询Token
        AdminUsers user = adminMapper.selectUserByUserAccount(username);
        if (null == user){
            resultMap.put("code", 50003);
            return resultMap;
        }
        if (!PassUtils.getMD5Str(pass).equals(user.getUser_password())){
            resultMap.put("code", 50007);
            return resultMap;
        }
        //为生成Token准备
        String TokenStr = "";
        Date date = new Date();
        //生成Token
        TokenStr = creatToken(user.getUser_id(), date);
        redis.set("user" + user.getUser_id(),TokenStr,15 * 86400L);//一天时间过期
        AdminUsers adminUsers = new AdminUsers();
        adminUsers.setUser_id(user.getUser_id());
        adminUsers.setUser_logintime(new Date());
        adminMapper.updateUser(adminUsers);
        /* 将用户信息存入session */
        /*SessionContext sessionContext = SessionContext.getInstance();
        HttpSession session = sessionContext.getSession();
        httpSession.setAttribute("userInfo", user);*/
        resultMap.put("code", 200);
        resultMap.put("userId", user.getUser_id());
        resultMap.put("token", TokenStr);
        return resultMap;
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, Object> webLogin(String username, String pass, String web) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        //根据数据库的用户信息查询Token
        AdminUsers user = adminMapper.selectUserByUserAccount(username);
        if (null == user){
            resultMap.put("code", 50003);
            return resultMap;
        }
        if (!PassUtils.getMD5Str(pass).equals(user.getUser_password())){
            resultMap.put("code", 50007);
            return resultMap;
        }
        resultMap.put("code", 200);
        resultMap.put("userId", user.getUser_id());
        adminMapper.insertUserLogger(user.getUser_id(), "web登录");
        return resultMap;
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
                .setExpiration(new Date(date.getTime() + 1000 * 60 * 60 * 24 * 15)) /*过期时间 毫秒*/
                .claim("userId",String.valueOf(userId) ) // 设置内容
                .setIssuer("zx")// 设置签发人
                .signWith(signatureAlgorithm, KeyWord.LOGIN_KEY); // 签名，需要算法和key
        String jwt = builder.compact();
        return jwt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int register(String phone, String pass) throws Exception {
        if (null != adminMapper.selectUserIdByPhone(phone)){
            return 50001;
        }
        AdminUserMain main = new AdminUserMain();
        main.setUser_name("用户_" + phone.substring(phone.length() - 4, phone.length()));
        adminMapper.insertUserMain(main);
        AdminUsers users = new AdminUsers();
        users.setUser_account(phone);
        users.setUser_password(PassUtils.getMD5Str(pass));
        users.setUser_starttime(new Date());
        users.setUmid(main.getUmid());
        users.setUser_name("用户_" + phone.substring(phone.length() - 4, phone.length()));
        adminMapper.insertUser(users);
        return 200;
    }

    @Override
    public boolean judgeUserExist(String phone) {
        if (null != adminMapper.selectUserIdByPhone(phone)){
            return true;
        }
        return false;
    }

    @Override
    public boolean judgeUserExist(Integer user_id) {
        if (null != user_id){
            if (null != adminMapper.selectUserById(user_id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int sendCode(String phone, boolean exe) {
        boolean user = adminMapper.selectUserIdByPhone(phone) != null ? true : false;
        if (exe){
            if (user){
                return 50001;
            }
            rabbitTemplate.convertAndSend("NoteExchange", "NoteRouting", phone);
            return 200;
        }else{
            if (!user){
                return 50003;
            }
            rabbitTemplate.convertAndSend("NoteExchange", "NoteRouting", phone);
            return 200;
        }
    }

    @Override
    public Map<String, String> getVersion() {
        Map<String, String> resultMap = new HashMap<>();
        List<Version> list = adminMapper.selectVersion();
        for (Version von : list) {
            if (von.getSystem().equals("android")){
                resultMap.put("android", von.getVersion());
                resultMap.put("url", KeyWord.LOCATION + von.getUrl());
            }else {
                resultMap.put("ios", von.getVersion());
            }
        }
        return resultMap;
    }

    @Override
    public int updatePassword(String username, String password) {
        Integer userId = adminMapper.selectUserIdByPhone(username);
        if (null == userId){
            return 50003;
        }
        AdminUsers adminUsers = new AdminUsers();
        adminUsers.setUser_id(userId);
        adminUsers.setUser_password(password);
        adminMapper.updateUser(adminUsers);
        return 200;
    }
}
