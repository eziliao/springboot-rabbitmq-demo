package cn.eziliao.demo.springbootrabbitmq.confirm.producer.config;

import cn.eziliao.demo.springbootrabbitmq.confirm.common.constants.MqConstants;
import cn.eziliao.demo.springbootrabbitmq.confirm.common.constants.TestQueueConstants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengli
 */
@Configuration
public class TestQueueConfig {
    @Bean
    public Queue testQueue() {
        Map<String, Object> queueArguments = new HashMap<>();
        queueArguments.put("x-dead-letter-exchange", MqConstants.DEAD_EXCHANGE);
        return QueueBuilder.durable(TestQueueConstants.TEST_QUEUE).withArguments(queueArguments).build();
    }

    @Bean
    public Binding bindingTestQueue(@Qualifier("reliableExchange") DirectExchange reliableExchange) {
        return BindingBuilder.bind(testQueue()).to(reliableExchange).with(TestQueueConstants.TEST_QUEUE);
    }
}
