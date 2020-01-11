package cn.anshirui.store.appdevelop.repository;

import java.lang.annotation.*;

/**
 * @author zx
 * @Title WebLog
 * @Description
 * @data 2019/11/27 16:36
 */
@Retention(RetentionPolicy.RUNTIME)//运行时使用该注解
@Target({ElementType.METHOD})//定义为作用于方法上
@Documented//注解是否将包含在 JavaDoc 中
public @interface WebLog {
    
    /**
     * @Author zhangxuan
     * @Description //TODO 日志描述信息
     * @Date 16:37 2019/11/27
     * @Param []
     * @return java.lang.String
     **/
    String description() default "";

}
