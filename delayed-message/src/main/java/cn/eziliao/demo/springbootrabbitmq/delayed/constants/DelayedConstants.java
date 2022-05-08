package cn.eziliao.demo.springbootrabbitmq.delayed.constants;

/**
 * @author chengli
 * @date 2022/5/8 9:24
 */
public interface DelayedConstants {
    /**
     * 延时交换机类型
     */
    String DELAYED_TYPE = "x-delayed-message";

    /**
     * 延时交换机
     */
    String DELAYED_EXCHANGE = "DELAYED.EXCHANGE";

    /**
     * 延时队列
     */
    String DELAYED_QUEUE = "DELAYED.QUEUE";

    /**
     * 路由KEY
     */
    String DELAYED_ROUTING_KEY = "delayed.test.key";
}
