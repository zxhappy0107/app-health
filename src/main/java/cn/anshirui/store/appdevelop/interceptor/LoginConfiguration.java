package cn.anshirui.store.appdevelop.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *@ClassName 拦截器配置
 *@Description TODO
 *@Author zhangxuan
 *@Date 10:48
 *Version 1.0
 **/
@Component
public class LoginConfiguration implements WebMvcConfigurer {

    /**
     * @Author zhangxuan
     * @Description //TODO 注入登录拦截器
     * @Date 10:50 2019/11/29
     * @Param []
     * @return cn.anshirui.store.appdevelop.interceptor.LoginInterceptor
     **/
    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
    
    /**
     * @Author zhangxuan
     * @Description //TODO 配置生成器：添加一个拦截器，拦截路径为login以后的路径
     * @Date 10:51 2019/11/29
     * @Param [registry]
     * @return void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login", "/register", "/static");
    }

}
