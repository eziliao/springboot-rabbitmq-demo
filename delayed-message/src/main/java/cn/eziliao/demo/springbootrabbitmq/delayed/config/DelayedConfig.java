package cn.eziliao.demo.springbootrabbitmq.delayed.config;

import cn.eziliao.demo.springbootrabbitmq.delayed.constants.DelayedConstants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengli
 * @date 2022/5/8 9:26
 */
@Configuration
public class DelayedConfig {
    /**
     * 延时交换机
     *
     * @return
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");

        return new CustomExchange(DelayedConstants.DELAYED_EXCHANGE, DelayedConstants.DELAYED_TYPE,
                true, false, arguments);
    }

    /**
     * 延时队列
     *
     * @return
     */
    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(DelayedConstants.DELAYED_QUEUE).build();
    }

    /**
     * 绑定队列与交换机
     *
     * @param delayedExchange
     * @param delayedQueue
     * @return
     */
    @Bean
    public Binding delayedQueueBinding(@Qualifier("delayedExchange") CustomExchange delayedExchange,
                                       @Qualifier("delayedQueue") Queue delayedQueue) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DelayedConstants.DELAYED_ROUTING_KEY).noargs();
    }
}
