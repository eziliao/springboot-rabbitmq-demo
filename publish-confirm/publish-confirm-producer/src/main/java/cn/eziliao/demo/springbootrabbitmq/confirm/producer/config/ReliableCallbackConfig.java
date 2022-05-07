package cn.eziliao.demo.springbootrabbitmq.confirm.producer.config;

import cn.eziliao.demo.springbootrabbitmq.confirm.producer.domain.MqMessage;
import cn.eziliao.demo.springbootrabbitmq.confirm.producer.service.MqMessageService;
import cn.eziliao.demo.springbootrabbitmq.confirm.common.constants.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 消息确认配置
 *
 * @author chengli
 */
@Configuration
@Slf4j
public class ReliableCallbackConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MqMessageService mqMessageService;

    private final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        if (correlationData != null) {
            log.info("消息投递确认---消息ID：{} 投递状态：{} 原因：{}", correlationData.getId(), ack, cause);
            try {
                MqMessage mqMessage = new MqMessage();
                mqMessage.setMessageId(correlationData.getId());
                if (ack) {
                    mqMessage.setStatus(MqConstants.Status.SUCCESS.toString());
                } else {
                    mqMessage.setStatus(MqConstants.Status.FAILURE.toString());
                    ZonedDateTime zonedDateTime = LocalDateTime.now().plusSeconds(10).atZone(ZoneId.systemDefault());// 失败的每 10 秒重新投递一次
                    mqMessage.setRetryNextTime(Date.from(zonedDateTime.toInstant()));
                }
                mqMessage.setUpdateTime(new Date());
                mqMessage.setFailureReason(cause);
                mqMessageService.updateMqMessage(mqMessage);
                log.info("消息投递确认---更改消息状态成功");
            } catch (Exception e) {
                log.error("消息投递确认---更改消息状态失败", e);
            }
        }
    };

    @PostConstruct
    public void initConfirmCallback() {
        rabbitTemplate.setConfirmCallback(confirmCallback);
    }
}
