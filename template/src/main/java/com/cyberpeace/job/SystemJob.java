package com.cyberpeace.job;

import com.cyberpeace.config.mq.RabbitMQConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 系统工作
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Component
public class SystemJob {

    /**
     * 连接工厂
     *
     * @see ConnectionFactory
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * 兔子 mqconf
     *
     * @see RabbitMQConf
     */
    @Autowired
    private RabbitMQConf rabbitMQConf;

    /**
     * 销毁查询
     *
     * @throws
     */
    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void destroyQuery() {
        log.info("destroyQuery start");
        new RabbitAdmin(connectionFactory).deleteQueue(rabbitMQConf.getDeleteQueryName());
        log.info("destroyQuery end");
    }
}
