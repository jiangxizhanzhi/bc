package com.cyberpeace.cr.redvsblue.service.impl;

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
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbSceneEventListener extends MessageListener {

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private RbSceneService rbSceneService;

	@Autowired
	private CrEventListenerHelper crEventListenerHelper;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.RED_VS_BLUE.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		RbRoom room = rbRoomService.getById(businessId);
		rbSceneService.clearLocal(room);
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
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		// parse message
		WsSceneReportBO wsSceneReportBO = JsonUtil.toJsonObject(data, WsSceneReportBO.class);
		JSONObject jsonData = wsSceneReportBO.getData().getData().getJSONObject("data");
		// 区分场景节点消息推送和节点登录信息推送
		if (jsonData.containsKey("call_action")) {
			for (CrRole role : roles) {
				// 只推送给白方
				if (role.isRvbStaff()) {
					pushDataToWebByUserIds(data, businessId, role.getUserIdList());
				}
			}
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
		WebSocketMessageContext messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		webSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 推送消息给某个角色的所有用户
	 */
	private void pushDataToWebByUserIds(String data, String businessId, List<String> userIds) {
		WebSocketMessageContext messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		webSocketHandler.pushMessageToUsers(messageContext, userIds);
	}
}
