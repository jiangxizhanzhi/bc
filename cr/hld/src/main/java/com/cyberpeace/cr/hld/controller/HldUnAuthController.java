package com.cyberpeace.cr.hld.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.controller.req.TemplateReq;
import com.cyberpeace.cr.crbase.controller.rsp.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SceneConfigRsp;
import com.cyberpeace.cr.crbase.controller.rsp.StartSceneRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.hld.controller.req.HldDeleteReq;
import com.cyberpeace.cr.hld.controller.req.HldRoomCopyReq;
import com.cyberpeace.cr.hld.controller.req.HldSceneOpsReq;
import com.cyberpeace.cr.hld.controller.rsp.HldRoomIdRsp;
import com.cyberpeace.cr.hld.controller.rsp.HldTopoAssetsRsp;
import com.cyberpeace.cr.hld.controller.rsp.HldTopoRsp;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomSceneMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cr.hld.service.context.HldBaseInfo;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;
import com.cyberpeace.cr.hld.service.context.HldRoomSceneContext;
import com.cyberpeace.cr.hld.service.context.HldTaskContext;
import com.cyberpeace.integratation.model.mp.MpLogFile;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	private RemoteSceneService remoteSceneService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@ApiOperation("复制新增")
	@PostMapping("/copy")
	public BaseRsp<HldRoomIdRsp> copy(@RequestBody @Validated HldRoomCopyReq copyReq) {

		// 构造初始数据
		HldRoomContext hldRoomContext = copyToHldRoomContext(copyReq);

		// 执行创建
		HldRoom hldRoom = hldRoomService.create(hldRoomContext);

		return new BaseRsp<>(new HldRoomIdRsp(hldRoom.getResourceId()));
	}

	@ApiOperation("查询所有训练模板")
	@GetMapping("/listCrEvents")
	public BaseRsp<HldRoomIdRsp> listCrEvents(TemplateReq listReq) {
		BusinessTemplateQuery businessTemplateQuery = BeanUtils.copy(listReq,
				BusinessTemplateQuery.class);
		businessTemplateQuery.setSort("-cr_business_template.create_time");

		IPage<BusinessTemplateData> data = businessTemplateService
				.getAllList(businessTemplateQuery);

		List<String> userIds = Lists.newArrayList();
		for (BusinessTemplateData businessTemplateData : data.getRecords()) {
			userIds.add(businessTemplateData.getCreateUserId());
		}
		// Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);

		data.convert(businessTemplateData -> {
			BusinessTemplateRsp businessTemplateRsp = BeanUtils.copy(businessTemplateData,
					BusinessTemplateRsp.class);
			businessTemplateRsp.setDifficult(businessTemplateData.getDifficulty());
			// businessTemplateRsp
			// .setCreateUser(idNameMap.get(businessTemplateData.getCreateUserId()));
			businessTemplateRsp.setId(businessTemplateData.getResourceId());
			businessTemplateRsp.setType(businessTemplateData.getBusinessType());
			businessTemplateRsp.setSceneManagerId(businessTemplateData.getTopoId());
			businessTemplateRsp.setShare(businessTemplateData.getOperationAuthType());
			businessTemplateRsp.setShareTeachers(JsonUtil
					.toJsonObjectArray(businessTemplateData.getOperationAuthIds(), String.class));
			SceneConfigRsp sceneConfigInfo = new SceneConfigRsp();
			sceneConfigInfo.setNodeCount(businessTemplateData.getNodesNum());
			sceneConfigInfo
					.setHasScene(StringUtils.isNotBlank(businessTemplateData.getLocalSceneId()));
			businessTemplateRsp.setSceneConfigInfo(sceneConfigInfo);

			return businessTemplateRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	private HldRoomContext copyToHldRoomContext(HldRoomCopyReq copyReq) {

		// 提取基础信息
		HldRoom hldRoom = hldRoomService.getHldRoom(copyReq.getResourceId());
		setUserInfo(hldRoom.getCreateUserId());

		HldTaskContext hldTaskContext = hldRoomService.getHldTaskContext(copyReq.getTaskId());

		HldBaseInfo baseInfo = hldRoomService.getHldBaseInfo(hldTaskContext);

		SceneConfigData sceneConfigData = new SceneConfigData();
		sceneConfigData.setSceneConfig(hldSceneService.viewTopoNoAuth(copyReq.getResourceId())
				.getJSONObject("scene_config"));

		baseInfo.setSceneConfigData(sceneConfigData);

		// 构造初始数据
		HldRoomContext hldRoomContext = new HldRoomContext();
		hldRoomContext.setRoleConfig(hldRoom.getRoleConfig());
		hldRoomContext.setBaseInfo(baseInfo);
		hldRoomContext.setTaskId(copyReq.getTaskId());

		return hldRoomContext;
	}

	@ApiOperation("读取topo和角色资产")
	@GetMapping("/topoAssets")
	public BaseRsp<HldTopoAssetsRsp> getTopoAssets(@ApiParam(name = "resource_id",
			value = "HLD拓扑id", required = true) String resource_id) {

		// read scene data
		JSONObject sceneData = hldSceneService.viewSceneNoAuth(resource_id);
		JSONObject topoData = hldSceneService.viewTopoNoAuth(resource_id);

		HldTopoAssetsRsp rsp = new HldTopoAssetsRsp();

		rsp.setSceneData(sceneData);
		rsp.setTopoData(topoData);

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
		hldTopoRsp.setTopo(hldRoom.getTopo());
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
	@GetMapping("/mp_scene")
	public BaseRsp<List<HldRoomSceneContext>> getMpSceneId(@ApiParam(name = "resource_id",
			value = "HLD拓扑id", required = true) String resource_id) {

		List<HldRoomSceneContext> hldRoomSceneContexts = hldRoomSceneMapper
				.listSceneIds(resource_id);

		return new BaseRsp<>(hldRoomSceneContexts);
	}

	@ApiOperation("获取场景的日志文件")
	@GetMapping("/mp_scene_file")
	public BaseRsp<List<MpLogFile>> getMpSceneFiles(
			@ApiParam(name = "resource_id", value = "HLD拓扑id", required = true) String resource_id,
			@ApiParam(name = "path", value = "lujing") String path,
			@ApiParam(name = "file", value = "文件") String file) {

		List<MpLogFile> logFiles = remoteSceneService.getMpSceneFiles(resource_id,
				ConfigUtil.getContextProperty("cr.ftp.file.names"));

		log.info("日志列表为" + logFiles.toString());
		for (MpLogFile logFile : logFiles) {
			String roomId = hldRoomSceneMapper.selectByMpSceneId(resource_id).getHldRoomId();
			if (StringUtils.isNotEmpty(roomId)) {
				FtpUploadUtil.uploadFile("/sjk/" + roomId, logFile.getName(),
						ConfigUtil.getContextProperty("cr.ftp.file.path") + "/"
								+ logFile.getName());
			}
		}

		return new BaseRsp(logFiles);
	}

	private void setUserInfo(String userId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		UserInfoContext.setUserContext(userInfo);
	}
}
