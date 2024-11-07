package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.cache.CrRoleCache;
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

/**
 * 靶场事件听者助手
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class CrEventListenerHelper {

	/**
	 * 过滤 ws 推送的节点消息
	 *
	 * @param role
	 * @param data
	 * @param nodeId
	 * @return
	 */
	public String filterDataByRole(CrRole role, String data, String nodeId) {
		WsSceneReportBO report;
		// is student
		if (role.isRvbStudent() || role.isTeamStudent()) {
			CrRoleNodesData nodesData = role.getNodeData();
			// filter visible nodes
			CrRole roleOld = null;
			try {
				roleOld = (CrRole) CrRoleCache.cache.get(role.getResourceId() + nodeId);
			} catch (Exception e) {
				// 异常不处理
			}
			CrRoleNodesData nodesDataOld = roleOld == null ? new CrRoleNodesData()
					: roleOld.getNodeData();
			List<String> visibleNodeIds = StreamApiUtils.map(nodesData.getVisibleNodes(),
					CrRoleNode::getId);
			if (!visibleNodeIds.contains(nodeId)) {
				// filter visible nodes
				List<String> oldVisibleNodeIds = StreamApiUtils.map(nodesDataOld.getVisibleNodes(),
						CrRoleNode::getId);
				if (!oldVisibleNodeIds.contains(nodeId)) {
					// 不推送
					return null;
				}
			}
			// filter asset nodes
			List<String> assetNodeIds = StreamApiUtils.map(nodesData.getAssetNodes(),
					CrRoleNode::getId);
			if (!assetNodeIds.contains(nodeId)) {
				List<String> oldAssetNodeIds = StreamApiUtils.map(nodesDataOld.getAssetNodes(),
						CrRoleNode::getId);
				// remove ports & access modes
				if (!oldAssetNodeIds.contains(nodeId)) {
					report = JsonUtil.toJsonObject(data, WsSceneReportBO.class);
					JSONObject coreData = report.getData().getData();
					coreData.getJSONObject("scene_element").put("ports", Lists.newArrayList());
					coreData.put("scene_element_port_plugin_data", new JSONObject());
					return JsonUtil.toJsonString(report);
				}
			}
		}
		// 教员不过滤, 学员拥有完整权限（战争迷雾和资产）不过滤
		return data;
	}

	/**
	 * 构建需要向页面推送的消息上下文
	 *
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
}
