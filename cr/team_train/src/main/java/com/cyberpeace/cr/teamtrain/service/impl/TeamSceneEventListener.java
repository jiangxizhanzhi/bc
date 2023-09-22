package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeamSceneEventListener extends MessageListener {

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private TeamSceneService teamSceneService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrEventListenerHelper crEventListenerHelper;

	@Override
	public Integer declareMessageType() {
		return BusinessCode.TEAM_TRAINING.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		TeamRoom room = teamRoomService.getById(businessId);

		teamSceneService.clearLocal(room);

		return true;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);

		return true;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		// get roles
		List<CrRole> roles = crRoleService.getRolesByRoom(businessId,
				BusinessType.TRAINING_TEAM.getCode());
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
	 * @param data
	 * @param businessId
	 */
	private void pushDataToWeb(String data, String businessId) {
		WebSocketMessageContext<CrSceneWsMessageIdentifier,String> messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		myWebSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 推送消息给某个角色的所有用户
	 */
	private void pushDataToWebByUserIds(String data, String businessId, List<String> userIds) {
		WebSocketMessageContext<CrSceneWsMessageIdentifier,String> messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		myWebSocketHandler.pushMessageToUsers(messageContext, userIds);
	}
}
