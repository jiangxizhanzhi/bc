package com.cyberpeace.cs.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.req.scene.SceneNodeReq;
import com.cyberpeace.cs.controller.req.scene.SceneReq;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "场景管理器相关接口")
@RestController
@RequestMapping("/localScene")
@RequiresAuthentication
@Slf4j
public class LocalSceneController {

	@Autowired
	LocalSceneService localSceneService;

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
