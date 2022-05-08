package cn.eziliao.demo.springbootrabbitmq.delayed.consumer;

import cn.eziliao.demo.springbootrabbitmq.delayed.constants.DelayedConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
    @RabbitListener(queues = DelayedConstants.DELAYED_QUEUE)
    public void consume(@Payload String message, @Headers Map<String, Object> headers, Channel channel) {
        log.info("收到延时消息：{}", message);
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
