package com.cyberpeace.cr.hld.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HldSceneEventListener extends MessageListener {

	Boolean DIFFERENTIATE_ROLES = false;

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	@Autowired
	private HldRoomService hldRoomService;

	@Autowired
	private HldSceneService hldSceneService;

	@Autowired
	private CrEventListenerHelper crEventListenerHelper;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.HLD.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		HldRoom room = hldRoomService.getById(businessId);
		if (room != null) {
			room.setStatus(HldRoom.StatusCode.ENDED.getCode());
			hldSceneService.clearLocal(room);
		}
		return true;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return true;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		if (DIFFERENTIATE_ROLES) {
			return pushElementDataByUserRole(data, businessId);
		} else {
			pushDataToWeb(data, businessId);
			return true;
		}
	}

	/**
	 * 区分角色推送节点消息
	 * @param data
	 * @param businessId
	 */
	private Boolean pushElementDataByUserRole(String data, String businessId) {

		// 查询角色数据
		List<CrRole> roles = crRoleService.getRolesByRoom(businessId,
				BusinessTemplate.BusinessType.HLD.getCode());
		// parse message
		WsSceneReportBO wsSceneReportBO = JsonUtil.toJsonObject(data, WsSceneReportBO.class);
		String nodeId = wsSceneReportBO.getData().getData().getJSONObject("data")
				.getJSONObject("scene_element").getString("id");
		for (CrRole role : roles) {
			String filteredData = filterDataByRole(role, data, nodeId);
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

	/**
	 * 过滤 ws 推送的节点消息
	 * @param role
	 * @param data
	 * @param nodeId
	 * @return
	 */
	private String filterDataByRole(CrRole role, String data, String nodeId) {
		WsSceneReportBO report;

		// is student
		if (role.isHldStudent()) {
			CrRoleNodesData nodesData = role.getNodeData();
			// filter visible nodes
			List<String> visibleNodeIds = StreamApiUtils.map(nodesData.getVisibleNodes(),
					CrRoleNode::getId);
			if (!visibleNodeIds.contains(nodeId)) {
				// 不推送
				return null;
			}
			// filter asset nodes
			List<String> assetNodeIds = StreamApiUtils.map(nodesData.getAssetNodes(),
					CrRoleNode::getId);
			if (!assetNodeIds.contains(nodeId)) {
				// remove ports & access modes
				report = JsonUtil.toJsonObject(data, WsSceneReportBO.class);
				JSONObject coreData = report.getData().getData().getJSONObject("data");
				coreData.getJSONObject("scene_element").put("ports", Lists.newArrayList());
				coreData.put("scene_element_port_plugin_data", new JSONObject());

				return JsonUtil.toJsonString(report);
			}
		}

		// 教员不过滤
		return data;
	}

}
