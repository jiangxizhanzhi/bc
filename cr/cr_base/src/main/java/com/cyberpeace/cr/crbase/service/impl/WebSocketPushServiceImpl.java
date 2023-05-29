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

@Service
public class WebSocketPushServiceImpl implements WebSocketPushService {
    @Autowired
    private MyWebSocketHandler webSocketHandler;

    @Override
    public void notice(List<String> userList, Integer code,String businessId,String url) {
        sendWsData(new TaskWsData(code), businessId,userList,url);
    }

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
