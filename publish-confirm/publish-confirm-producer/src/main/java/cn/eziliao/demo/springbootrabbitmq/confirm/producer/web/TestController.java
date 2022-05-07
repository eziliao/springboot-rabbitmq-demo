package cn.eziliao.demo.springbootrabbitmq.confirm.producer.web;

import cn.eziliao.demo.springbootrabbitmq.confirm.producer.service.MessageSendService;
import cn.eziliao.demo.springbootrabbitmq.confirm.common.constants.TestQueueConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengli
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private MessageSendService messageSendService;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        messageSendService.sendMessage(TestQueueConstants.TEST_QUEUE, message);
        return "发送成功";
    }
}
