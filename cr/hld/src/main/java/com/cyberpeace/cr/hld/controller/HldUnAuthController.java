package com.cyberpeace.cr.hld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.StartSceneRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.hld.controller.req.HldDeleteReq;
import com.cyberpeace.cr.hld.controller.req.HldSceneOpsReq;
import com.cyberpeace.cr.hld.controller.rsp.HldTopoAssetsRsp;
import com.cyberpeace.cr.hld.controller.rsp.HldTopoRsp;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomSceneMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cr.hld.service.context.HldRoomSceneContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "HLD拓扑（非认证）")
@Slf4j
@RestController
@RequestMapping("/cr/hld/")
public class HldUnAuthController {

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private HldSceneService hldSceneService;

	@Autowired
	private HldRoomService hldRoomService;

	@Autowired
	private HldRoomMapper hldRoomMapper;

	@Autowired
	private HldRoomSceneMapper hldRoomSceneMapper;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@ApiOperation("读取topo和角色资产")
	@GetMapping("/topoAssets")
	public BaseRsp<HldTopoAssetsRsp> getTopoAssets(@ApiParam(name = "resource_id",
			value = "HLD拓扑id", required = true) String resource_id) {

		// read scene data
		JSONObject sceneData = hldSceneService.viewSceneNoAuth(resource_id);
		if (sceneData == null) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		HldTopoAssetsRsp rsp = new HldTopoAssetsRsp();
		rsp.setSceneData(sceneData);

		// read role assets
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(resource_id);
		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole role : crRoles) {
			if (role.isHldStudent()) {
				CrRoleConfig config = JsonUtil.toJsonObject(role.getNodes(), CrRoleConfig.class);
				config.setName(role.getName());
				config.setId(role.getResourceId());

				if (role.isHldBlueStudent()) {
					rsp.getBlueRole().add(config);
				} else {
					rsp.getRedRoles().add(config);
				}
			}
		}
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<HldTopoRsp> get(
			@ApiParam(name = "resource_id", value = "HLD拓扑id", required = true) String resource_id,
			@ApiParam(name = "user_id", value = "用户id", required = false) String user_id) {

		// 获取当前用户角色
		CrRole userRole = hldRoomService.getUserRole(resource_id, user_id);
		SceneViewContext context = hldSceneService.viewScene(resource_id, userRole);

		// 构造返回数据
		HldRoom hldRoom = hldRoomService.getHldRoom(resource_id);
		HldTopoRsp hldTopoRsp = BeanUtils.copy(context, HldTopoRsp.class);
		hldTopoRsp.setStatus(hldRoom.getStatus());
		hldTopoRsp.setTaskName(hldRoom.getTaskName());
		hldTopoRsp.setUserRole(hldRoomService.getHldRoleName(userRole.getName()));

		return new BaseRsp<>(hldTopoRsp);
	}

	@ApiOperation("启动场景")
	@PostMapping("")
	public BaseRsp startScene(@RequestBody HldSceneOpsReq hldSceneOpsReq) {

		setUserInfo(hldSceneOpsReq.getUserId());
		String sceneId = hldSceneService.startScene(hldSceneOpsReq.getResourceId(),
				hldSceneOpsReq.getUserId());

		return new BaseRsp(new StartSceneRsp(sceneId));
	}

	@ApiOperation("停止场景")
	@DeleteMapping("")
	public BaseRsp stopScene(@RequestBody HldSceneOpsReq hldSceneOpsReq) {

		setUserInfo(hldSceneOpsReq.getUserId());
		Boolean result = hldSceneService.stopScene(hldSceneOpsReq.getResourceId(), true);

		return new BaseRsp(result);
	}

	@ApiOperation("暂停场景")
	@PostMapping("/pause")
	public BaseRsp<Boolean> pauseScene(@RequestBody HldSceneOpsReq hldSceneOpsReq) {

		setUserInfo(hldSceneOpsReq.getUserId());
		Boolean ret = hldSceneService.pauseScene(hldSceneOpsReq.getResourceId());

		return new BaseRsp<>(ret);
	}

	@ApiOperation("从暂停中恢复场景")
	@PostMapping("/unpause")
	public BaseRsp<Boolean> unpauseScene(@RequestBody HldSceneOpsReq hldSceneOpsReq) {

		setUserInfo(hldSceneOpsReq.getUserId());
		Boolean ret = hldSceneService.unpauseScene(hldSceneOpsReq.getResourceId());

		return new BaseRsp<>(ret);
	}

	@ApiOperation("删除场景")
	@DeleteMapping("/delete")
	public BaseRsp<String> batchDelete(@RequestBody @Validated HldDeleteReq deleteReq) {

		setUserInfo(deleteReq.getUserId());

		// 校验待删除房间是否存在
		for (String roomId : deleteReq.getIds()) {
			hldRoomService.getHldRoom(roomId);
		}

		List<String> businessTemplateIds = hldRoomMapper
				.listBusinessTemplateIds(deleteReq.getIds());
		businessTemplateService.batchDelete(businessTemplateIds);

		return new BaseRsp<>();
	}

	@ApiOperation("获取SN场景id")
	@GetMapping("/mp_scene_id")
	public BaseRsp<List<HldRoomSceneContext>> getMpSceneId(@ApiParam(name = "resource_id",
			value = "HLD拓扑id", required = true) String resource_id) {

		List<HldRoomSceneContext> hldRoomSceneContexts = hldRoomSceneMapper
				.listSceneIds(resource_id);

		return new BaseRsp<>(hldRoomSceneContexts);
	}

	private void setUserInfo(String userId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		UserInfoContext.setUserContext(userInfo);
	}
}
