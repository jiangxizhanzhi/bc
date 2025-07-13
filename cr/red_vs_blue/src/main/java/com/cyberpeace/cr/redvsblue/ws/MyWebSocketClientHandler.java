package com.cyberpeace.cr.redvsblue.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.service.CrUniteBusinessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;

@Slf4j
@Component
public class MyWebSocketClientHandler implements WebSocketHandler {

    @Autowired
    private CrUniteBusinessTaskService crUniteBusinessTaskService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket连接已建立");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        try {
            log.info("收到服务器消息: " + message.getPayload());
            // 将消息转换为JSON对象
            String payload = (String) message.getPayload();
//        WebSocketMessagePayload payloadObj = objectMapper.readValue(payload, WebSocketMessagePayload.class);
            JSONObject jsonObject = JSON.parseObject(payload);
            // 根据type字段调用不同的方法
//        int type = payloadObj.getType();
            String function = jsonObject.getString("Function");
            String data = jsonObject.getString("Data");
            switch (function) {
//                设备下发
                case "Interaction1":
                    log.info("Interaction1 data:{}",data);
                    crUniteBusinessTaskService.saveUniteBusinessTopo(data);
                    break;
                case "AttackData":
//                    七种类型攻击
                    log.info("AttackData data:{}",data);
                    crUniteBusinessTaskService.attack(data);
                    break;
                case "UniteFlowData":
//                    流量指令攻击
                    log.info("UniteFlowData data:{}",data);
                    crUniteBusinessTaskService.flowStart(data);
                case "UniteStartData":
//                    联合场景启动
                    log.info("UniteStartData data:{}",data);
                    crUniteBusinessTaskService.start(data);
                    break;
                case "UniteStopData":
//                    联合场景停止
                    log.info("UniteStopData data:{}",data);
                    crUniteBusinessTaskService.stop(data);
                    break;
//                    链路通信信息
                default:
                    log.warn("未知的320消息类型: " + function);
                    break;
            }
        } catch (Exception e) {
            log.error("320消息解析出错,message:{}", message.getPayload());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("传输错误: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("WebSocket连接已关闭，状态码: " + closeStatus.getCode() + ", 原因: " + closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false; // 不支持分片消息
    }
}