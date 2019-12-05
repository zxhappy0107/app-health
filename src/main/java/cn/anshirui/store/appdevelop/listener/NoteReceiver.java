package cn.anshirui.store.appdevelop.listener;

import cn.anshirui.store.appdevelop.note.SMSClientUtils;
import cn.anshirui.store.appdevelop.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName 短信监听
 * @Description TODO
 * @Author zhangxuan
 * @Date 16:53
 * Version 1.0
 **/
@Component
@RabbitListener(queues = "NoteQueue")
public class NoteReceiver {

    private static final Logger log = LoggerFactory.getLogger("短信发送：");

    @Autowired
    private RedisService redisService;

    @RabbitHandler
    public void process(String phone){
        String code = SMSClientUtils.SendSMSCode(phone);
        redisService.set("code" + phone, code, 300L);
        log.info("号码 ：" + phone + " 短信已发送");
    }

}
