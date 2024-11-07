package com.cyberpeace.cr.crbase.service.impl;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.service.WebSocketPushService;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWsIdentifier;
import com.cyberpeace.cr.crbase.service.context.mission.TaskWsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 蹼插座推Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class WebSocketPushServiceImpl implements WebSocketPushService {
    /**
     * Web 套接字处理程序
     */
    @Autowired
    private MyWebSocketHandler webSocketHandler;

    /**
     * 通知
     *
     * @param userList   用户列表
     * @param code       代号
     * @param businessId 业务id
     * @param url        网址
     */
    @Override
    public void notice(List<String> userList, Integer code,String businessId,String url) {
        sendWsData(new TaskWsData(code), businessId,userList,url);
    }

    /**
     * 发送 WS 数据
     *
     * @param data       数据
     * @param businessId 业务id
     * @param userIds    用户id列表
     * @param url        网址
     */
    private <T> void sendWsData(T data, String businessId,List<String> userIds,String url) {
        MissionWsIdentifier identifier = new MissionWsIdentifier();
        identifier.setBusinessId(businessId);
        identifier.setUserId(UserInfoContext.getUserContext().getUserId());
        identifier.setPath(url);

        WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
                .builder().data(data).messageIdentifier(identifier).build();
        webSocketHandler.pushMessageToUsers(messageContext, userIds);
    }
}
