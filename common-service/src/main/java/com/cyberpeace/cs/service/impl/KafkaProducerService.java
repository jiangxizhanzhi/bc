package com.cyberpeace.cs.service.impl;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String topic = "topic_sn_report";

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public <T> void sendMessage(String key, T data) {
        String jsonString = JsonUtil.toJsonString(data);
        kafkaTemplate.send(topic, key, jsonString);
    }

    /**
     * 拓扑信息
     */
    public <T> void sendTopoMessage(T data) {
        sendMessage("topo", data);
    }

    /**
     * 工具记录信息
     */
    public <T> void sendToolMessage(T data) {
        sendMessage("tool", data);
    }

    /**
     * 迷雾角色信息
     */
    public <T> void sendRoleMessage(T data) {
        sendMessage("role", data);
    }

}
