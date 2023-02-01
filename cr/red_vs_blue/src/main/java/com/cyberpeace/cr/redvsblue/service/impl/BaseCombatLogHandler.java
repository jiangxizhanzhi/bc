package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.service.context.NodeAscription;
import com.cyberpeace.cr.redvsblue.service.context.SingleNodeAscription;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseCombatLogHandler extends RbCombatLog {

	private Map<String, String> initData;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	public BaseCombatLogHandler(Map<String, String> initMap) {
		this.initData = initMap;

		if (initMap.isEmpty()) {
			setAttr();
		}
	}

	public void setAttr() {
		;
	}

	public String getDestIp() {
		return initData.get("agent_ip");
	}

	public String getSourceIp() {
		if (initData.get("src_ip") != null) {
			return initData.get("src_ip");
		} else {
			return initData.get("detail.src_ip");
		}
	}

	public NodeAscription getNodeAscription() {


		return null;
	}

	public Integer getDestGroup() {
		List<Integer> group = getNodeAscription().getAscription(getDestIp());
		if (group.size() != 1) {
			throw new BusinessException(CrBusinessErrorCode.DEST_GROUP_MISSING);
		} else {
			return group.get(0);
		}
	}

//		public void getDestName(){
//			JSONObject elementInfo = getNodeAscription().get("node_data");
//		}
//
//		public void getSourceName(){
//
//		}

	public NodeAscription getNodeAscription(List<String> nodeIpList) {
		NodeAscription nodeAscription = new NodeAscription();
		// 默认同时只会启动一个场景
		LocalScene localScene = localSceneMapper.selectOne();
		if (localScene == null) {
			return nodeAscription;
		}
		List<CrRole> crRoleList = crRoleMapper.getRolesByRoom(localScene.getResourceId(),
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		List<JSONObject> nodeInfoList = remoteSceneService.getAllElements(localScene.getMpSceneId());
		List<SingleNodeAscription> singleNodeAscriptionList = new ArrayList<>();
		Map<String, String> nodeData = new HashMap<>();
		for (String nodeIp : nodeIpList) {
			List<Integer> ascription = new ArrayList<>();
			SingleNodeAscription singleNodeAscription = new SingleNodeAscription(nodeIp, ascription);
			for (JSONObject nodeInfo : nodeInfoList) {
				List<JSONObject> ports = nodeInfo.getObject("ports", List.class);
				for (JSONObject port : ports) {
					if (port.getString("ip")
							.equals(nodeIp.trim())) {

						for (CrRole crRole : crRoleList) {
							List<String> visibleNodes = JsonUtil.toJsonObjectArray(crRole.getNodes(), String.class);
							if (visibleNodes.contains(nodeInfo.getString("id"))) {
								ascription.add(crRole.getRoleGroup());
								nodeData.put(nodeIp, nodeInfo.getString("name"));
							}
						}
					}
				}
			}
			singleNodeAscriptionList.add(singleNodeAscription);
		}
		nodeAscription.setNodeAscriptionList(singleNodeAscriptionList);
		nodeAscription.setNodeData(nodeData);
		nodeAscription.setSceneId(localScene.getResourceId());
		return nodeAscription;
	}


}

