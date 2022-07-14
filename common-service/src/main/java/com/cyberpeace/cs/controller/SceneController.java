package com.cyberpeace.cs.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.req.NodeSnapShotReq;

import io.swagger.annotations.Api;

@Api(tags = "场景操作相关api")
@Slf4j
@RestController
@RequestMapping("/cs/scene")
@RequiresAuthentication
public class SceneController {

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@PostMapping("/node/snapshot")
	@ApiOperation("场景节点打快照")
	public BaseRsp nodeSnapshot(@RequestBody NodeSnapShotReq snapShotReq) {

		LocalScene localScene = localSceneMapper.selectBySceneId(snapShotReq.getSceneId());
		if (null == localScene) {
			log.error("LocalScene not found! mp_scene_id=[{}]", snapShotReq.getSceneId());
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		Boolean result = localSceneService.nodeSnapshot(localScene, snapShotReq.getNodeId(),
				snapShotReq.getSnapshotName());
		return new BaseRsp(result);
	}

}
