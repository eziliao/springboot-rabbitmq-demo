package cn.eziliao.demo.springbootrabbitmq.confirm.producer.domain;

import java.util.Date;
import lombok.Data;

/**
 * 消息表
 *
 * @author chengli
 */
@Data
public class MqMessage {
	/**
	 * 消息ID
	 */
	private String messageId;
	/**
	 * 路由KEY
	 */
	private String routingKey;
	/**
	 * 消息数据
	 */
	private String messageData;
	/**
	 * 消息状态 SENDING SUCCESS FAILURE
	 */
	private String status;
	/**
	 * 失败原因
	 */
	private String failureReason;
	/**
	 * 重试次数
	 */
	private Integer retryCount;
	/**
	 * 下一次重试时间
	 */
	private Date retryNextTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
