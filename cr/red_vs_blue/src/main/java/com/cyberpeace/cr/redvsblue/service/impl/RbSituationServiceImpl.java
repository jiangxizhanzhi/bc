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

/**
 * 红蓝对抗态势Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Service
public class RbSituationServiceImpl implements RbSituationService {

	/**
	 * 红蓝对抗房间Mapper
	 */
	@Autowired
	private RbRoomMapper rbRoomMapper;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 红蓝对抗房间Service
	 */
	@Autowired
	private RbRoomService rbRoomService;

	/**
	 * 远程场景Service
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 靶场岗位Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 获取全部红蓝对抗态势
	 *
	 * @return {@code List<RbSituation> }
	 */
	@Override
	public List<RbSituation> getAllRbSituation() {
		return rbRoomMapper.selectAllRunning();
	}

	/**
	 * 节点映射
	 */
	public Map<String, String> nodeMap = new HashMap<>();

	/**
	 * 场景id
	 */
	public String sceneId;

	/**
	 * 中台场景id
	 */
	public String mpSceneId;

	/**
	 * 获取红蓝对抗服务器由资源id
	 *
	 * @param resourceId 资源id
	 * @return {@code List<RbSituationServer> }
	 */
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
		if (localScene == null) {
			log.error("localScene id null, must start [{}] scene", room.getName());
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}

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

	/**
	 * 获取场景id
	 *
	 * @return {@code String }
	 */
	@Override
	public String getSceneId() {
		return sceneId;
	}

	/**
	 * 获取节点映射
	 *
	 * @return {@code Map<String, String> }
	 */
	@Override
	public Map<String, String> getNodeMap() {
		return nodeMap;
	}

	/**
	 * 获取中台场景id
	 *
	 * @return {@code String }
	 */
	@Override
	public String getMpSceneId() {
		return mpSceneId;
	}

	/**
	 * 再设置id
	 */
	@Override
	public void reSetId() {
		mpSceneId = null;
		sceneId = null;
	}
}
