package cn.eziliao.demo.springbootrabbitmq.confirm.producer.service;

/**
 * 消息发送服务
 *
 * @author chengli
 */
public interface MessageSendService {
    /**
     * 发送消息
     *
     * @param routingKey 路由KEY
     * @param message    消息
     */
    void sendMessage(String routingKey, String message);
}
