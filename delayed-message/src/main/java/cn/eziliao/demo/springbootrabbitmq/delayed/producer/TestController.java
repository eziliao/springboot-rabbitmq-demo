package cn.eziliao.demo.springbootrabbitmq.delayed.producer;

import cn.eziliao.demo.springbootrabbitmq.delayed.constants.DelayedConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengli
 */
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send/{delay}/{message}")
    public String sendMessage(@PathVariable Integer delay, @PathVariable String message) {
        log.info("发送延时消息，延时：{}，消息内容：{}", delay, message);
        rabbitTemplate.convertAndSend(DelayedConstants.DELAYED_EXCHANGE, DelayedConstants.DELAYED_ROUTING_KEY, message, m -> {
            MessageProperties messageProperties = m.getMessageProperties();
            messageProperties.setDelay(delay);//单位：ms
            return m;
        });
        return "发送成功";
    }
}
