package cn.eziliao.demo.springbootrabbitmq.confirm.common.constants;

/**
 * 消息常量
 *
 * @author chengli
 */
public interface MqConstants {
    /**
     * 可靠交换机
     */
    String RELIABLE_EXCHANGE = "RELIABLE.EXCHANGE";

    /**
     * 备份交换机
     */
    String BACKUP_EXCHANGE = "RELIABLE.BACKUP.EXCHANGE";

    /**
     * 备份队列
     */
    String BACKUP_QUEUE = "RELIABLE.BACKUP.QUEUE";

    /**
     * 死信交换机
     */
    String DEAD_EXCHANGE = "RELIABLE.DEAD.EXCHANGE";

    /**
     * 死信队列
     */
    String DEAD_QUEUE = "RELIABLE.DEAD.QUEUE";

    /**
     * 消息状态
     */
    enum Status {
        /**
         * 正在投递
         */
        SENDING,

        /**
         * 投递成功
         */
        SUCCESS,

        /**
         * 投递失败
         */
        FAILURE;

        @Override
        public String toString() {
            return this.name().toUpperCase();
        }
    }
}
