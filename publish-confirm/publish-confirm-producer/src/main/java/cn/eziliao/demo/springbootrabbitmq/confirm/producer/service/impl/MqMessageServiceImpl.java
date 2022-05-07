package cn.eziliao.demo.springbootrabbitmq.confirm.producer.service.impl;

import cn.eziliao.demo.springbootrabbitmq.confirm.producer.service.MqMessageService;
import cn.eziliao.demo.springbootrabbitmq.confirm.producer.domain.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息表
 *
 * @author chengli
 */
@Service
@Slf4j
public class MqMessageServiceImpl implements MqMessageService {
    /**
     * 新增消息表
     *
     * @param mqMessage 消息表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMqMessage(MqMessage mqMessage) {
        log.info("消息入库成功");
    }

    /**
     * 修改消息表
     *
     * @param mqMessage 消息表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMqMessage(MqMessage mqMessage) {
        log.info("消息修改成功");
    }
}
