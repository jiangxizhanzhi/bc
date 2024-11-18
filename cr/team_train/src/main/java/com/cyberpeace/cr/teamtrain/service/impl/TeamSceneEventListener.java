package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 团队场景事件听者
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MessageListener
 * @since 1.0.0
 */
@Slf4j
@Service
public class TeamSceneEventListener extends MessageListener {

    /**
     * 我的ws处理程序
     *
     * @see MyWebSocketHandler
     */
    @Autowired
	private MyWebSocketHandler myWebSocketHandler;

    /**
     * 团队房间Service
     *
     * @see TeamRoomService
     */
    @Autowired
	private TeamRoomService teamRoomService;

    /**
     * 团队场景Service
     *
     * @see TeamSceneService
     */
    @Autowired
	private TeamSceneService teamSceneService;

    /**
     * 靶场角色Service
     *
     * @see CrRoleService
     */
    @Autowired
	private CrRoleService crRoleService;

    /**
     * 靶场事件听者助手
     *
     * @see CrEventListenerHelper
     */
    @Autowired
	private CrEventListenerHelper crEventListenerHelper;

    /**
     * 声明消息类型
     *
     * @return {@code Integer }
     * @throws
     * @see Integer
     */
    @Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.TEAM_TRAINING.getCode();
	}

    /**
     * 上场景删除
     *
     * @param data       数据
     * @param businessId 业务id
     * @param context    上下文
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see String
     * @see MqContext
     * @see Boolean
     */
    @Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		TeamRoom room = teamRoomService.getById(businessId);

		teamSceneService.clearLocal(room);

		return true;
	}

    /**
     * 上场景开始过程
     *
     * @param data       数据
     * @param businessId 业务id
     * @param context    上下文
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see String
     * @see MqContext
     * @see Boolean
     */
    @Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);

		return true;
	}

    /**
     * 上场景元素
     *
     * @param data       数据
     * @param businessId 业务id
     * @param context    上下文
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see String
     * @see MqContext
     * @see Boolean
     */
    @Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		// get roles
		List<CrRole> roles = crRoleService.getRolesByRoom(businessId,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		// parse message
		WsSceneReportBO wsSceneReportBO = JsonUtil.toJsonObject(data, WsSceneReportBO.class);
		JSONObject jsonData = wsSceneReportBO.getData().getData();
		// 区分场景节点消息推送和节点登录信息推送
		if (jsonData.containsKey("call_action")) {
			pushDataToWeb(data, businessId);
			return true;
		}
		String nodeId = jsonData.getJSONObject("scene_element").getString("id");
		for (CrRole role : roles) {
			String filteredData = crEventListenerHelper.filterDataByRole(role, data, nodeId);
			if (filteredData == null) {
				continue;
			}
			pushDataToWebByUserIds(filteredData, businessId, role.getUserIdList());
		}
		return true;
	}

    /**
     * 场景消息统一推送
     *
     * @param data
     * @param businessId 业务id
     * @throws
     * @see String
     * @see String
     */
    private void pushDataToWeb(String data, String businessId) {
		WebSocketMessageContext messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		myWebSocketHandler.pushMessage(messageContext, true);
	}

    /**
     * 推送消息给某个角色的所有用户
     *
     * @param data       数据
     * @param businessId 业务id
     * @param userIds    用户id列表
     * @throws
     * @see String
     * @see String
     * @see List<String>
     */
    private void pushDataToWebByUserIds(String data, String businessId, List<String> userIds) {
		WebSocketMessageContext messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		myWebSocketHandler.pushMessageToUsers(messageContext, userIds);
	}
}
