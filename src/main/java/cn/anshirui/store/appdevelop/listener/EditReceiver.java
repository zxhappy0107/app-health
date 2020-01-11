package cn.anshirui.store.appdevelop.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName 信息发布
 * @Description TODO
 * @Author zhangxuan
 * @Date 17:18
 * Version 1.0
 **/
@Component
@RabbitListener(queues = "EditQueue")
public class EditReceiver {

    private static final Logger log = LoggerFactory.getLogger("信息发布：");

    @RabbitHandler
    public void process(Map<String, Object> resultMap){
        System.out.println("EditQueue接收到的信息：" + resultMap.toString());
    }

}
