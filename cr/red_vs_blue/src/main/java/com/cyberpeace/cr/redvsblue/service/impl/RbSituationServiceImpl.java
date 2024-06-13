package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSituationService;
import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import com.cyberpeace.cr.redvsblue.service.context.RbSituationServer;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RbSituationServiceImpl implements RbSituationService {

	@Autowired
	private RbRoomMapper rbRoomMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public List<RbSituation> getAllRbSituation() {
		return rbRoomMapper.selectAllRunning();
	}

	public Map<String, String> nodeMap = new HashMap<>();

	public String sceneId;

	public String mpSceneId;

	@Override
	public List<RbSituationServer> getRbServerByResourceId(String resourceId) {
		nodeMap.clear();
		List<RbSituationServer> rbSituationServerList = new ArrayList<>();
		resourceId = resourceId.substring(0, resourceId.length() - 1);
		RbRoom room = rbRoomService.getById(resourceId);
		if (room == null) {
			log.error("room is null, room id:{}", resourceId);
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}
		LocalScene localScene = localSceneService.getById(room.getLocalSceneId());

		JSONObject scene = remoteSceneService.getById(localScene.getMpSceneId(), null);
		List<JSONObject> elements = scene.getJSONObject("scene").getJSONObject("topology")
				.getObject("elements", List.class);
		for (JSONObject jsonObject : elements) {
			RbSituationServer rbSituationServer = new RbSituationServer();
			rbSituationServer.setServerId(jsonObject.getString("id"));
			rbSituationServer.setServerName(jsonObject.getString("name"));
			rbSituationServer.setNetworkId("network-1");
			rbSituationServer.setNetworkName("默认网络");
			rbSituationServerList.add(rbSituationServer);
		}
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(room.getResourceId());
		roleQuery.setRoleGroup(RbRoleGroup.RED.getCode());
		List<CrRole> redRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole redRole : redRoleList) {
			List<JSONObject> visibleNodes = JSON.parseObject(redRole.getNodes())
					.getObject("visibleNodes", List.class);
			for (JSONObject visibleNode : visibleNodes) {
				String nodeId = visibleNode.getString("id");
				for (RbSituationServer rbSituationServer : rbSituationServerList) {
					if (rbSituationServer.getServerId().equals(nodeId)) {
						rbSituationServer.setRole("red");
						nodeMap.put(nodeId, "red");
					}
				}
			}
		}
		roleQuery.setRoleGroup(RbRoleGroup.BLUE.getCode());
		List<CrRole> blueRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole blueRole : blueRoleList) {
			List<JSONObject> visibleNodes = JSON.parseObject(blueRole.getNodes())
					.getObject("visibleNodes", List.class);
			for (JSONObject visibleNode : visibleNodes) {
				String nodeId = visibleNode.getString("id");
				for (RbSituationServer rbSituationServer : rbSituationServerList) {
					if (rbSituationServer.getServerId().equals(nodeId)) {
						rbSituationServer.setRole("blue");
						nodeMap.put(nodeId, "blue");
					}
				}
			}
		}
		mpSceneId = localScene.getMpSceneId();
		sceneId = resourceId;
		return rbSituationServerList;
	}

	@Override
	public String getSceneId() {
		return sceneId;
	}

	@Override
	public Map<String, String> getNodeMap() {
		return nodeMap;
	}

	@Override
	public String getMpSceneId() {
		return mpSceneId;
	}

	@Override
	public void reSetId() {
		mpSceneId = null;
		sceneId = null;
	}
}
