package cn.anshirui.store.appdevelop.repository;

import java.lang.annotation.*;

/**
 * @author zx
 * @Title WebLog
 * @Description
 * @data 2019/11/27 16:36
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
@Documented
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
