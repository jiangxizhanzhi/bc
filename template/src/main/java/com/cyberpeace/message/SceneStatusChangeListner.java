package com.cyberpeace.message;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.constant.RabbitmqQueueName;

import lombok.extern.slf4j.Slf4j;

/**
 * 处理场景状态发生变化的消息
 */
@Slf4j
@Component
@RabbitListener(queues = { RabbitmqQueueName.SCENE_STATUS_CHANGE})
public class SceneStatusChangeListner {

    @RabbitHandler
    public void consume(String message) {

    }
}
