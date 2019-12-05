package cn.anshirui.store.appdevelop.common;

import cn.anshirui.store.appdevelop.AppDevelopApplication;
import cn.anshirui.store.appdevelop.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName
 * @Description TODO
 * @Author zhangxuan
 * @Date 11:18
 * Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppDevelopApplication.class})
public class JedisTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void test01(){
        System.out.println(redisService.get("phone"));
//        redisService.remove("code17791379355");
    }

}
