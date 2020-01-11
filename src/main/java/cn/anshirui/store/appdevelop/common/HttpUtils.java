package cn.anshirui.store.appdevelop.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName 网络工具
 * @Description TODO
 * @Author zhangxuan
 * @Date 15:57
 * Version 1.0
 **/
public class HttpUtils {

   /**
    * @Author zhangxuan
    * @Description //TODO 取登录用户的IP地址
    * @Date 15:26 2019/12/26
    * @Param [request]
    * @return java.lang.String
    **/
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
