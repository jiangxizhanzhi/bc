package com.cyberpeace.cs.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cs.controller.req.scene.SceneNodeReq;
import com.cyberpeace.cs.controller.req.scene.SceneReq;
import com.cyberpeace.cs.controller.rsp.CrSceneRetrieveRsp;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.impl.SceneHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SN
 */
@Api(tags = "场景管理器相关接口")
@RestController
@RequestMapping("/localScene")
@RequiresAuthentication
@Slf4j
public class LocalSceneController {

	@Autowired
	LocalSceneService localSceneService;

	@Autowired
	@Qualifier("BasicSceneHandler")
	private SceneHandler sceneHandler;

	@Autowired
	TransactionTemplate transactionTemplate;

	@ApiOperation("读取场景")
	@GetMapping("")
	public BaseRsp<CrSceneRetrieveRsp> retrieve(@RequestParam("id") String sceneManagerId,
			@RequestParam(value = "role_id", required = false) String roleId) {
		JSONObject sceneData = null;
		String topoId = null;
		JSONObject topoData = null;

		LocalScene localScene = localSceneService.getById(sceneManagerId);
		SceneHandler handler = sceneHandler.getHandler(localScene.getBusinessCode());
		String roomId = handler.getRoomIdBySceneManagerId(sceneManagerId);
		String userId = UserInfoContext.getUserContext().getUserId();

		String sceneId = localScene.getMpSceneId();
		if (StringUtils.isNotBlank(sceneId)) {
			sceneData = localSceneService.getSceneData(localScene, userId);
			if (sceneData != null) {
				handler.filterVisibleTopo(sceneData, localScene, userId, roleId);
			} else {
				log.warn("fail to get scene_data with scene_id [{}], this column will be set empty",
						sceneId);
				localSceneService.updateMpSceneId(localScene, "");
			}
		}
		if (sceneData == null) {
			topoId = localScene.getTopoId();
		}

		if (StringUtils.isNotBlank(topoId)) {
			topoData = localSceneService.getTopoData(localScene);
			handler.filterVisibleTopo(topoData, localScene, userId, roleId);
		}

		CrSceneRetrieveRsp rsp = new CrSceneRetrieveRsp();
		rsp.setSceneManagerId(sceneManagerId);
		rsp.setRoomId(roomId);
		rsp.setSceneData(sceneData);
		rsp.setTopoData(topoData);

		return new BaseRsp<>(rsp);
	}

	@ApiOperation("启动场景")
	@PostMapping("")
	public BaseRsp startScene(@RequestBody SceneReq sceneReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		localSceneService.startScene(sceneReq.getLocalSceneId(), userId);
		return new BaseRsp();
	}

	@ApiOperation("关闭场景")
	@DeleteMapping("")
	public BaseRsp stopScene(@RequestBody SceneReq sceneReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		localSceneService.stopScene(sceneReq.getLocalSceneId(), userId);
		return new BaseRsp();
	}

	@ApiOperation("暂停场景")
	@PostMapping("/pause")
	public BaseRsp pauseScene(@RequestBody SceneReq sceneReq) {
		localSceneService.pauseScene(sceneReq.getLocalSceneId());
		return new BaseRsp();
	}

	@ApiOperation("（从暂停）恢复场景")
	@PostMapping("/unpause")
	public BaseRsp unpauseScene(@RequestBody SceneReq sceneReq) {
		localSceneService.unPauseScene(sceneReq.getLocalSceneId());
		return new BaseRsp();
	}

	@ApiOperation("重启节点")
	@PostMapping("/restartNode")
	public BaseRsp restartNode(@RequestBody SceneNodeReq sceneNodeReq) {
		localSceneService.restartNode(sceneNodeReq.getLocalSceneId(), sceneNodeReq.getNodeId());
		return new BaseRsp();
	}

	@ApiOperation("重建节点")
	@PostMapping("/recreateNode")
	public BaseRsp recreateNode(@RequestBody SceneNodeReq sceneNodeReq) {
		localSceneService.recreateNode(sceneNodeReq.getLocalSceneId(), sceneNodeReq.getNodeId());
		return new BaseRsp();
	}

}
