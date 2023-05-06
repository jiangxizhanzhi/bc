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
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.CreateSnapshotContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 场景控制器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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

	/**
	 * 节点快照
	 *
	 * @param snapShotReq 乱射点播
	 * @return {@link BaseRsp}<{@link Boolean}>
	 */
	@PostMapping("/node/snapshot")
	@ApiOperation("场景节点打快照")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSES, MenuConstant.CMS_PRACTICE_OPERATION,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_TEAM_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> nodeSnapshot(@Valid @RequestBody NodeSnapShotReq snapShotReq) {

		LocalScene localScene = localSceneMapper.selectBySceneId(snapShotReq.getSceneId());
		if (null == localScene) {
			log.error("LocalScene not found! mp_scene_id=[{}]", snapShotReq.getSceneId());
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		Boolean result = localSceneService.nodeSnapshot(localScene,
				BeanUtils.copy(snapShotReq, CreateSnapshotContext.class));
		return new BaseRsp(result);
	}

	/**
	 * 删除快照
	 *
	 * @param deleteReq 删除请求
	 * @return {@link BaseRsp}<{@link Boolean}>
	 */
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

	/**
	 * 快照路径
	 *
	 * @param sceneId 场景id
	 * @param imageId 形象标识
	 * @return {@link BaseRsp}<{@link JSONArray}>
	 */
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

}
