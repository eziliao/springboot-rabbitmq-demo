package cn.eziliao.demo.springbootrabbitmq.confirm.producer.service.impl;

import cn.eziliao.demo.springbootrabbitmq.confirm.producer.service.MessageSendService;
import cn.eziliao.demo.springbootrabbitmq.confirm.producer.service.MqMessageService;
import cn.eziliao.demo.springbootrabbitmq.confirm.producer.domain.MqMessage;
import cn.eziliao.demo.springbootrabbitmq.confirm.common.constants.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 消息发送服务
 *
 * @author chengli
 */
@Service
@Slf4j
public class MessageSendServiceImpl implements MessageSendService {
    @Autowired
    private MqMessageService mqMessageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param routingKey 路由KEY
     * @param message    消息
     */
    @Override
    public void sendMessage(String routingKey, String message) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        log.info("发送消息----消息ID：{} routingKey：{} message：{}", correlationData.getId(), routingKey, message);
        this.saveReliableMessage(routingKey, message, correlationData);
        rabbitTemplate.convertAndSend(MqConstants.RELIABLE_EXCHANGE, routingKey, message, correlationData);
        log.info("发送消息----消息ID：{} 结束", correlationData.getId());
    }

    /**
     * 保存消息
     *
     * @param routingKey
     * @param message
     * @param correlationData
     */
    private void saveReliableMessage(String routingKey, String message, CorrelationData correlationData) {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setMessageId(correlationData.getId());
        mqMessage.setRoutingKey(routingKey);
        mqMessage.setMessageData(message);
        mqMessage.setStatus(MqConstants.Status.SENDING.toString());
        mqMessage.setCreateTime(new Date());
        mqMessageService.addMqMessage(mqMessage);
    }
}
