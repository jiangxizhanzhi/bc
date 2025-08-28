package com.cyberpeace.cr.redvsblue.ws;

import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import java.io.IOException;

@Service
@Slf4j
public class WebSocketClientService {

    @Autowired
    private WebSocketClient webSocketClient; // 注入WebSocket客户端

    @Autowired
    private MyWebSocketClientHandler handler;

    private static WebSocketSession SESSION;

    public static WebSocketSession getSession() {
        return SESSION;
    }

    public static void sendMessage(String message) {
        if (SESSION != null && SESSION.isOpen()) {
            try {
                SESSION.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void connectToServer() {
        // 指定服务器URL，例如 ws://localhost:8080/my-websocket
        String url = SystemPropUtil.getProp("WS_URL");
        if (url == null || url.isEmpty()) {
            url = "ws://192.168.3.67:1234";
        }
        try {
            // 连接到服务器并获取会话对象
            SESSION = webSocketClient.doHandshake(handler, url).get();
            log.info("320 WebSocket连接成功");

        } catch (Exception e) {
            log.error("320 WebSocket error, url[{}], error msg {}", url, e.getMessage());
        }
    }

    // 定期检查连接状态并尝试重连
    @Scheduled(fixedRate = 60 * 1000) // 每60秒检查一次连接状态。可以根据实际需求调整时间间隔。
    public void checkAndReconnect() {
        if (SESSION == null || !SESSION.isOpen()) { // 如果session为null或已关闭，则尝试重新连接。
            log.info("320 WebSocket连接已断开，尝试重新连接...");
            connectToServer(); // 调用connectToServer方法重新连接。
        }
    }
}