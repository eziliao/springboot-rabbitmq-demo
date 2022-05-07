package cn.eziliao.demo.springbootrabbitmq.confirm.producer.service;

import cn.eziliao.demo.springbootrabbitmq.confirm.producer.domain.MqMessage;

/**
 * 消息表
 *
 * @author chengli
 */
public interface MqMessageService {
    /**
     * 新增消息表
     *
     * @param mqMessage 消息表
     */
    void addMqMessage(MqMessage mqMessage);

    /**
     * 修改消息表
     *
     * @param mqMessage 消息表
     */
    void updateMqMessage(MqMessage mqMessage);
}
