package com.cyberpeace.cr.crbase.service.impl;

import java.sql.Time;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cyberpeace.cr.crbase.dao.entity.CrRoomDeviceApply;
import com.cyberpeace.cr.crbase.service.CrRoomDeviceApplyService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cs.service.context.WsSceneReportBO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrEventListenerHelper {

	@Autowired
	private CrRoomDeviceApplyService crRoomDeviceApplyService;
	/**
	 * 过滤 ws 推送的节点消息
	 * @param role
	 * @param data
	 * @param nodeId
	 * @return
	 */
	public String filterDataByRole(CrRole role, String data, String nodeId) {
		WsSceneReportBO report;
		// is student
		if (role.isRvbStudent() || role.isTeamStudent()) {
			data = filterApplyDevice(data, role, null);

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
		// 教员不过滤, 学员拥有完整权限（战争迷雾和资产）不过滤
		return data;
	}

	/**
	 * 构建需要向页面推送的消息上下文
	 * @param data 
	 * @param businessId
	 * @return
	 */
	public WebSocketMessageContext makeContext(String data, String businessId) {
		// 构建需要向页面推送的消息上下文
		CrSceneWsMessageIdentifier identifier = new CrSceneWsMessageIdentifier();
		identifier.setPath(WsPath.CR_SCENE);
		identifier.setBusiness_id(businessId);
		return WebSocketMessageContext.builder().data(data).messageIdentifier(identifier).build();
	}

	public String filterApplyDevice(String data, CrRole role, String businessId) {
		JSONObject dataObject = JsonUtil.toJsonObject(data, JSONObject.class);

		if (dataObject.getJSONObject("data").getString("type").equals("scene_element")) {
			JSONObject elementCpCloud = dataObject.getJSONObject("data")
					.getJSONObject("data")
					.getJSONObject("scene_element_plugin_data")
					.getJSONObject("cpcloud");

			if (elementCpCloud.getString("image_type").equals("entity")) {
				CrRoomDeviceApply crRoomDeviceApply = crRoomDeviceApplyService.lambdaQuery()
						.eq(CrRoomDeviceApply::getRoomId, role.getRoomId())
						.eq(CrRoomDeviceApply::getDeviceId, elementCpCloud.getString("image_uuid"))
						.eq(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.AGREE.getCode())
						.one();

				if (crRoomDeviceApply == null) {
					JSONArray ports = dataObject.getJSONObject("data")
							.getJSONObject("data")
							.getJSONObject("scene_element")
							.getJSONArray("ports");

					for (int i = 0; i < ports.size(); i++) {
						ports.getJSONObject(i).put("access_modes", Lists.newArrayList());
						ports.getJSONObject(i).remove("ip");
						dataObject.getJSONObject("data")
								.getJSONObject("data")
								.getJSONObject("scene_element_port_plugin_data")
								.getJSONObject("cpcloud")
								.remove(ports.getJSONObject(i).getString("id"));
					}
				}
				new Thread(() -> {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (crRoomDeviceApply == null) {
						String deviceId = dataObject.getJSONObject("data")
								.getJSONObject("data")
								.getJSONObject("scene_element").getString("id");
						String deviceOriginId = elementCpCloud.getString("image_uuid");
						String roomId = StringUtils.isEmpty(businessId) ? role.getRoomId() : businessId;
						crRoomDeviceApplyService.pushSceneData(deviceId, deviceOriginId, roomId, role.getUserIdList());
					} else {
						crRoomDeviceApplyService.pushSceneData(crRoomDeviceApply);
					}
				}).start();
			}
		} else {
			return data;
		}

		return JsonUtil.toJsonString(dataObject);
	}
}
