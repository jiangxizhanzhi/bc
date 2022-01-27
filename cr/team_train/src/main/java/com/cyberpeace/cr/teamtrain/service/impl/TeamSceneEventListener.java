package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
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

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.TEAM_TRAINING.getCode();
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
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		// parse message
		WsSceneReportBO wsSceneReportBO = JsonUtil.toJsonObject(data, WsSceneReportBO.class);
		String nodeId = wsSceneReportBO.getData().getData().getJSONObject("data")
				.getJSONObject("scene_element").getString("id");
		WsSceneReportBO report = null;
		for (CrRole role : roles) {
			// is student,
			if (!role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getCode())) {
				CrRoleNodesData nodesData = JsonUtil.toJsonObject(role.getNodes(),
						CrRoleNodesData.class);
				// filter visible nodes
				List<String> visibleNodeIds = StreamApiUtils.map(nodesData.getVisibleNodes(),
						CrRoleNode::getId);
				if (!visibleNodeIds.contains(nodeId)) {
					continue;
				}
				// filter asset nodes
				List<String> assetNodeIds = StreamApiUtils.map(nodesData.getAssetNodes(),
						CrRoleNode::getId);
				if (!assetNodeIds.contains(nodeId)) {
					report = JsonUtil.toJsonObject(data, WsSceneReportBO.class);
					JSONObject coreData = report.getData().getData().getJSONObject("data");
					coreData.getJSONObject("scene_element").put("ports", Lists.newArrayList());
					coreData.put("scene_element_port_plugin_data", new JSONObject());
				}
			} else {
				// 教员不过滤
				report = wsSceneReportBO;
			}
			// push by role
			String filteredData = JsonUtil.toJsonString(report);
			List<String> userIds = JSONObject.parseArray(role.getUserIds(), String.class);
			pushDataToWebByUserIds(filteredData, businessId, userIds);
		}
		return true;
	}

	/**
	 * 场景和节点的状态变更消息统一推送
	 * @param data
	 * @param businessId
	 */
	private void pushDataToWeb(String data, String businessId) {
		WebSocketMessageContext messageContext = makeContext(data, businessId);
		myWebSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 推送消息给某个角色的所有用户
	 */
	private void pushDataToWebByUserIds(String data, String businessId, List<String> userIds) {
		WebSocketMessageContext messageContext = makeContext(data, businessId);
		myWebSocketHandler.pushMessageToUsers(messageContext, userIds);
	}

	private WebSocketMessageContext makeContext(String data, String businessId) {
		// 构建需要向页面推送的消息上下文
		CrSceneWsMessageIdentifier identifier = new CrSceneWsMessageIdentifier();
		identifier.setPath(WsPath.CR_SCENE);
		identifier.setBusiness_id(businessId);
		WebSocketMessageContext messageContext = WebSocketMessageContext.builder().data(data)
				.messageIdentifier(identifier).build();
		return messageContext;
	}
}
