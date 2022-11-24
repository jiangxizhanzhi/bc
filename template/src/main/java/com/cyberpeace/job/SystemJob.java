//package com.cyberpeace.job;
//
//import com.cyberpeace.config.mq.RabbitMQConf;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class SystemJob {
//
//    @Autowired
//    private ConnectionFactory connectionFactory;
//
//    @Autowired
//    private RabbitMQConf rabbitMQConf;
//
//    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
//    public void destroyQuery() {
//        log.info("destroyQuery start");
//        new RabbitAdmin(connectionFactory).deleteQueue(rabbitMQConf.getDeleteQueryName());
//        log.info("destroyQuery end");
//    }
//}
