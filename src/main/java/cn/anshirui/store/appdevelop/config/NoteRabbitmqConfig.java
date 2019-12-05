package cn.anshirui.store.appdevelop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName 短信消息队列
 * @Description TODO
 * @Author zhangxuan
 * @Date 15:57
 * Version 1.0
 **/
@Configuration
public class NoteRabbitmqConfig {

    @Bean
    public Queue NoteQueue(){
        return new Queue("NoteQueue", false);//是否持久化
    }

    @Bean
    DirectExchange NoteExchang(){
        return new DirectExchange("NoteExchange");
    }

    @Bean
    Binding bingingDirect(){
        return BindingBuilder.bind(NoteQueue()).to(NoteExchang()).with("NoteRouting");
    }

}
