package cn.eziliao.demo.springbootrabbitmq.confirm.consumer.consume;

import cn.eziliao.demo.springbootrabbitmq.confirm.common.constants.MqConstants;
import cn.eziliao.demo.springbootrabbitmq.confirm.common.constants.TestQueueConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消费者
 *
 * @author chengli
 */
@Component
@Slf4j
public class TestConsumer {
    // 这里也声明了队列，主要是为了防止在Rabbitmq中没有或者没有先启动生产者，导致没有队列而报错问题。
    // 如果确认声明了，这里就没必要写这么多，也可以通过使用元注解 @RabbitListener 自定义注解简化。
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = TestQueueConstants.TEST_QUEUE, durable = "true",
            arguments = {@Argument(name = "x-dead-letter-exchange", value = MqConstants.DEAD_EXCHANGE)}),
            key = TestQueueConstants.TEST_QUEUE, exchange = @Exchange(value = MqConstants.RELIABLE_EXCHANGE,
            arguments = {@Argument(name = "alternate-exchange", value = MqConstants.BACKUP_EXCHANGE)}))})
    public void consume(@Payload String message, @Headers Map<String, Object> headers, Channel channel) {
        log.info("收到消息：{}", message);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            // 确认消费
            this.ack(channel, deliveryTag);
        } catch (Exception e) {
            this.nack(channel, deliveryTag, true);
        }
    }

    /**
     * 消费确认
     *
     * @param channel
     * @param deliveryTag
     */
    private void ack(Channel channel, Long deliveryTag) {
        try {
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("消息消费ACK失败", e);
        }
    }

    /**
     * Nack并直接放入死信
     *
     * @param channel
     * @param deliveryTag
     */
    private void nack(Channel channel, Long deliveryTag, boolean requeue) {
        try {
            channel.basicNack(deliveryTag, false, requeue);
        } catch (Exception e) {
            log.error("消息消费NAck失败", e);
        }
    }
}
