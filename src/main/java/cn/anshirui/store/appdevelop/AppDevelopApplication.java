package cn.anshirui.store.appdevelop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableTransactionManagement
public class AppDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDevelopApplication.class, args);
    }

}
