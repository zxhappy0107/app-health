package cn.anshirui.store.appdevelop.interceptor;

import cn.anshirui.store.appdevelop.entity.AdminUsers;
import cn.anshirui.store.appdevelop.mapper.AdminMapper;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *@ClassName 登录拦截
 *@Description TODO
 *@Author zhangxuan
 *@Date 10:19
 *Version 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminMapper adminMapper;

    //提供查询
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {}
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {}
    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        //此处为不需要登录的接口放行
        if (arg0.getRequestURI().contains("/login") || arg0.getRequestURI().contains("/register") || arg0.getRequestURI().contains("/error") || arg0.getRequestURI().contains("/static")) {
            return true;
        }
        //权限路径拦截
        //PrintWriter resultWriter = arg1.getOutputStream();
        // TODO: 有时候用PrintWriter 回报 getWriter() has already been called for this response
        //换成ServletOutputStream就OK了
        arg1.setCharacterEncoding("UTF-8");
        arg1.setContentType("text/html;charset=utf-8");
        ServletOutputStream resultWriter = arg1.getOutputStream();
        final String headerToken=arg0.getHeader("token");
        //判断请求信息
        if(null==headerToken||headerToken.trim().equals("")){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","401");
            jsonObject.put("msg","登录信息已过期，请重新登录");
            resultWriter.write(jsonObject.toString().getBytes());
            resultWriter.flush();
            resultWriter.close();
            return false;
        }
        //解析Token信息
        try {
            Claims claims = Jwts.parser().setSigningKey("preRead").parseClaimsJws(headerToken).getBody();
            String tokenUserId=(String)claims.get("userId");
            Integer iTokenUserId = Integer.parseInt(tokenUserId);
            //根据客户Token查找数据库Token
            AdminUsers myToken= adminMapper.selectUserById(iTokenUserId);

            //数据库没有Token记录
            if(null==myToken) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status","401");
                jsonObject.put("msg","请重新登录");
                resultWriter.write(jsonObject.toString().getBytes());
                resultWriter.flush();
                resultWriter.close();
                return false;
            }
            //数据库Token与客户Token比较
            if( !headerToken.equals(myToken.getToken()) ){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status","401");
                jsonObject.put("msg","账号已在异地登录，请重新登录");
                resultWriter.write(jsonObject.toString().getBytes());
                resultWriter.flush();
                resultWriter.close();
                return false;
            }
            //判断Token过期
            Date tokenDate= claims.getExpiration();
            int overTime=(int)(new Date().getTime()-tokenDate.getTime())/1000;
            if(overTime>60*60*24*3){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status","401");
                jsonObject.put("msg","登录已过期，请重新登录");
                resultWriter.write(jsonObject.toString().getBytes());
                resultWriter.flush();
                resultWriter.close();
                return false;
            }

        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","401");
            jsonObject.put("msg","登录信息有误，请重新登录");
            resultWriter.write(jsonObject.toString().getBytes());
            resultWriter.flush();
            resultWriter.close();
            return false;
        }
        //最后才放行
        return true;
    }

}
