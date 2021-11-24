package com.cyberpeace.cr.crbase.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.controller.rsp.CrSceneRetrieveRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNode;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.dao.entity.LocalSceneManager;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.SceneManagerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SN
 */
@Api(tags = "场景管理器相关接口")
@RestController
@RequestMapping("/cr/scene_manager")
@RequiresAuthentication
@Slf4j
public class SceneManagerController {

	@Autowired
	SceneManagerService sceneManagerService;

	@Autowired
	@Qualifier("businessTemplateHandler")
	private BusinessTemplateHandler templateHandler;

	@ApiOperation("读取场景")
	@GetMapping("/scene")
	public BaseRsp<CrSceneRetrieveRsp> retrieve(@RequestParam("id") String sceneManagerId,
			@RequestParam(value = "role_id", required = false) String roleId) {
		JSONObject sceneData = null;
		String topoId = null;
		JSONObject topoData = null;

		LocalSceneManager sceneManager = sceneManagerService.getById(sceneManagerId);
		BusinessTemplateHandler handler = templateHandler.getHandler(sceneManager.getAppId());
		String roomId = handler.getRoomIdBySceneManagerId(sceneManagerId);
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRoleNodesData nodes = handler.getNodes(userId, roomId, roleId);

		String sceneId = sceneManager.getSceneId();
		if (StringUtils.isNotBlank(sceneId)) {
			sceneData = sceneManagerService.getSceneData(sceneManager, userId);
			if (sceneData != null) {
				filterVisibleTopo(sceneData, nodes);
			} else {
				log.warn("fail to get scene_data with scene_id 【" + sceneId
						+ "】, this column will be set empty");
				sceneManagerService.updateScene(sceneManager, "");
			}
		}
		if (sceneData == null) {
			topoId = sceneManager.getTopoId();
		}

		if (StringUtils.isNotBlank(topoId)) {
			topoData = sceneManagerService.getTopoData(sceneManager);
			filterVisibleTopo(topoData, nodes);
		}

		CrSceneRetrieveRsp rsp = new CrSceneRetrieveRsp();
		rsp.setSceneManagerId(sceneManagerId);
		rsp.setRoomId(roomId);
		rsp.setSceneData(sceneData);
		rsp.setTopoData(topoData);

		return new BaseRsp<>(rsp);
	}

	private void filterVisibleTopo(JSONObject sceneData, CrRoleNodesData nodes) {
		// nodes为null代表所有节点可见
		if (nodes == null) {
			return;
		}

		List<String> visibleNodeIds = nodes.getVisibleNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		JSONArray elements = sceneData.getJSONObject("topology").getJSONArray("elements");
		int i = 0;
		while (i < elements.size()) {
			if (!visibleNodeIds.contains(elements.getJSONObject(i).getString("id"))) {
				elements.remove(i);
			} else {
				i++;
			}
		}
	}

}
