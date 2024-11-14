package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.model.ws.WebSocketConnectionContext;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cs.service.impl.ChatWebSocketHandler;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 团队训练聊天处理器实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Component("teamTrainChatHandlerImpl")
public class TeamTrainChatHandlerImpl extends ChatWebSocketHandler {

    /**
     * 靶场岗位Service
     */
    @Autowired
    private CrRoleService crRoleService;

    /**
     * 声明消息类型
     *
     * @return {@code Integer }
     */
    @Override
    public Integer declareMessageType() {
        return BusinessType.TRAINING_TEAM.getCode();
    }

    /**
     * 检查在业务
     *
     * @param webSocketConnectionContext WebSocket连接上下文
     * @return {@code Boolean }
     */
    @Override
    public Boolean checkInBusiness(WebSocketConnectionContext webSocketConnectionContext) {
        String userId = webSocketConnectionContext.getUserId();
        String businessId = webSocketConnectionContext.getOriginParams()
                .get(JsonKeyConstant.BUSINESS_ID);

        List<CrRole> rolesByRoom = crRoleService.getRolesByRoom(businessId, declareMessageType());
        List<String> userIds = Lists.newArrayList();

        for (CrRole role : rolesByRoom) {
            List<String> roleUserIds = role.getUserIdList();
            userIds.addAll(roleUserIds);
        }

        return userIds.contains(userId);
    }
}
