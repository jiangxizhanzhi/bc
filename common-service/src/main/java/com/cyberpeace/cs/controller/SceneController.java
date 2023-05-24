package com.cyberpeace.cs.controller;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cs.controller.req.NodeSnapShotReq;
import com.cyberpeace.cs.controller.req.NodeSnapshotDeleteReq;
import com.cyberpeace.cs.controller.req.scene.NodeOpsReq;
import com.cyberpeace.cs.controller.req.scene.NodeUpdateReq;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.CreateSnapshotContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> nodeSnapshot(@Valid @RequestBody NodeSnapShotReq snapShotReq) {

		snapShotReq.setSnapshotName(snapShotReq.getSnapshotName().trim());
		LocalScene localScene = localSceneMapper.selectBySceneId(snapShotReq.getSceneId());
		if (null == localScene) {
			log.error("LocalScene not found! mp_scene_id=[{}]", snapShotReq.getSceneId());
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		Boolean result = localSceneService.nodeSnapshot(localScene,
				BeanUtils.copy(snapShotReq, CreateSnapshotContext.class));
		return new BaseRsp(result);
	}

	@DeleteMapping("/node/snapshot")
	@ApiOperation("场景节点快照删除")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> snapshotDelete(@Valid @RequestBody NodeSnapshotDeleteReq deleteReq) {

		Boolean result = localSceneService.deleteSnapshot(deleteReq.getSceneId(),
				deleteReq.getNodeId(), deleteReq.getSnapshotId());
		return new BaseRsp<>(result);
	}

	@GetMapping("/node/snapshot")
	@ApiOperation("场景节点快照路径")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	@ApiImplicitParams({ @ApiImplicitParam(name = "scene_id", value = "场景编号", required = true),
			@ApiImplicitParam(name = "id", value = "镜像编号", required = true) })
	public BaseRsp<JSONArray> snapshotPath(@RequestParam("scene_id") String sceneId,
			@RequestParam("id") String imageId) {

		JSONArray result = localSceneService.snapshotHistory(sceneId, imageId);
		return new BaseRsp<>(result);
	}

	@PostMapping("/node/update")
	@ApiOperation("节点属性变更接口")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> updateNode(@Valid @RequestBody NodeUpdateReq req) {
		Boolean result = localSceneService.updateNode(req.getMpSceneId(), req.getUpdate());
		return new BaseRsp<>(result);
	}

	@DeleteMapping("/node/delete")
	@ApiOperation("动态删除单个节点")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> deleteNode(@Valid @RequestBody NodeOpsReq req) {
		Boolean result = localSceneService.deleteNode(req.getMpSceneId(), req.getNodeId());
		return new BaseRsp<>(result);
	}

	@PostMapping("/node/start")
	@ApiOperation("单个节点开机")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> startNode(@Valid @RequestBody NodeOpsReq req) {
		Boolean result = localSceneService.startNode(req.getMpSceneId(), req.getNodeId());
		return new BaseRsp<>(result);
	}


	@PostMapping("/node/stop")
	@ApiOperation("单个节点关机")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> stopNode(@Valid @RequestBody NodeOpsReq req) {
		Boolean result = localSceneService.stopNode(req.getMpSceneId(), req.getNodeId());
		return new BaseRsp<>(result);
	}


	@PostMapping("/node/restart")
	@ApiOperation("单个节点重启")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> restartNode(@Valid @RequestBody NodeOpsReq req) {
		Boolean result = localSceneService.restartNodeBySceneId(req.getMpSceneId(), req.getNodeId());
		return new BaseRsp<>(result);
	}

}
