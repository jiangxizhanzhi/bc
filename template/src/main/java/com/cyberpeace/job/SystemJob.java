package com.cyberpeace.job;

import com.cyberpeace.config.mq.RabbitMQConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 系统作业
 *
 * @author admin
 * @date 2024/11/12
 */
@Slf4j
@Component
public class SystemJob {

    /**
     * 连接工厂
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * 消息队列配置
     */
    @Autowired
    private RabbitMQConf rabbitMQConf;

    /**
     * 销毁查询
     */
    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void destroyQuery() {
        log.info("destroyQuery start");
        new RabbitAdmin(connectionFactory).deleteQueue(rabbitMQConf.getDeleteQueryName());
        log.info("destroyQuery end");
    }
}
