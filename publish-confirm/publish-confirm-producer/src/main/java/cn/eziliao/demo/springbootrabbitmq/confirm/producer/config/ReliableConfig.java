package cn.eziliao.demo.springbootrabbitmq.confirm.producer.config;

import cn.eziliao.demo.springbootrabbitmq.confirm.common.constants.MqConstants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息确认配置
 *
 * @author chengli
 */
@Configuration
public class ReliableConfig {
    /**
     * 可靠交换机
     *
     * @return
     */
    @Bean
    public DirectExchange reliableExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("alternate-exchange", MqConstants.BACKUP_EXCHANGE);

        return new DirectExchange(MqConstants.RELIABLE_EXCHANGE, true, false, arguments);
    }

    /**
     * 备份交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange(MqConstants.BACKUP_EXCHANGE, true, false);
    }

    /**
     * 备份队列
     *
     * @return
     */
    @Bean
    public Queue backupQueue() {
        return QueueBuilder.durable(MqConstants.BACKUP_QUEUE).build();
    }

    /**
     * 绑定备份队列
     *
     * @param backupQueue
     * @param backupExchange
     * @return
     */
    @Bean
    public Binding bindingBackupQueueToExchange(@Qualifier("backupQueue") Queue backupQueue,
                                                @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange deadExchange() {
        return new FanoutExchange(MqConstants.DEAD_EXCHANGE, true, false);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(MqConstants.DEAD_QUEUE).build();
    }


    /**
     * 绑定死信队列
     *
     * @param deadQueue
     * @param deadExchange
     * @return
     */
    @Bean
    public Binding bindingDeadQueueToExchange(@Qualifier("deadQueue") Queue deadQueue,
                                              @Qualifier("deadExchange") FanoutExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange);
    }
}
