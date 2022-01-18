package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleRspBO;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateAddReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateModifyReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "红蓝对抗模板后台")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/admin/red_vs_blue/red_vs_blue_templates/")
public class RbTemplateAdminController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RbTemplateService rbTemplateService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private LocalSceneService localSceneService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp create(@RequestBody @Validated RbTemplateAddReq rbTemplateAddReq) {

		RbTemplateContext rbTemplateContext = BeanUtils.copy(rbTemplateAddReq,
				RbTemplateContext.class);

		JSONObject roleJsonObject = new JSONObject();
		roleJsonObject.put("red_roles", rbTemplateAddReq.getRedRoles());
		roleJsonObject.put("blue_roles", rbTemplateAddReq.getBlueRoles());

		rbTemplateContext.setRoleList(JsonUtil.toJsonString(roleJsonObject));
		rbTemplateContext.getBaseInfo()
				.setType(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());

		businessTemplateService.addTemplate(rbTemplateContext);

		return new BaseRsp<>();
	}

	@ApiOperation("修改")
	@PatchMapping()
	public BaseRsp update(@RequestBody @Validated RbTemplateModifyReq rbTemplateModifyReq) {
		String resourceId = rbTemplateModifyReq.getResourceId();
		RbTemplate template = rbTemplateService.getRbTemplateByBusinessId(resourceId);

		if (template == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());
		if (businessTemplate == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		RbTemplateContext rbTemplateContext = BeanUtils.copy(rbTemplateModifyReq,
				RbTemplateContext.class);

		JSONObject roleJsonObject = new JSONObject();
		roleJsonObject.put("red_roles", rbTemplateModifyReq.getRedRoles());
		roleJsonObject.put("blue_roles", rbTemplateModifyReq.getBlueRoles());

		rbTemplateModifyReq.getRedRoles().forEach(jsonObject -> {
			missionService.missionsSchemeValidator(jsonObject.getJSONObject("missions_scheme"));
		});
		rbTemplateModifyReq.getBlueRoles().forEach(jsonObject -> {
			missionService.missionsSchemeValidator(jsonObject.getJSONObject("missions_scheme"));
		});

		rbTemplateContext.setRoleList(JsonUtil.toJsonString(roleJsonObject));
		rbTemplateContext.getBaseInfo()
				.setType(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());

		rbTemplateService.update(rbTemplateContext, businessTemplate);

		return getRbTemplate(resourceId);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		List<RbTemplate> rbTemplates = rbTemplateService
				.getRbTemplateByBusinessIds(deleteReq.getIds());
		List<String> roomIds = rbTemplates.stream().map(RbTemplate::getResourceId).distinct()
				.collect(Collectors.toList());

		rbTemplateService.batchDelete(deleteReq.getIds(), roomIds);

		return new BaseRsp<>();
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<RbTemplateRsp> getRbTemplate(String resource_id) {

		businessTemplateService.checkPermission(resource_id,
				UserInfoContext.getUserContext().getUserId());
		RbTemplate template = rbTemplateService.getRbTemplateByBusinessId(resource_id);

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		// 获取红蓝角色
		String roleString = template.getRoleConfig();
		JSONObject roleObject = JsonUtil.toJsonObject(roleString, JSONObject.class);

		Map<String, MissionTemplate> mapping = missionTemplateService
				.getMappingBySchemeJson(roleString);
		List<JSONObject> redRoleRsps = getRoles(roleObject.getJSONArray("red_roles"), mapping);
		List<JSONObject> blueRoleRsps = getRoles(roleObject.getJSONArray("blue_roles"), mapping);

		// 获取基本信息
		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(template, businessTemplate);

		baseInfoRsp.setSceneConfigData(localSceneService.getTopoData(businessTemplate.getTopoId()));
		String sceneManagerId = "";
		LocalScene localScene = localSceneService
				.getByBusinessInstanceId(businessTemplate.getResourceId());
		if (localScene != null) {
			sceneManagerId = localScene.getResourceId();
		}
		baseInfoRsp.setSceneManagerId(sceneManagerId);

		// 获取返回对象
		RbTemplateRsp teamTrainTemplateRsp = new RbTemplateRsp();

		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setResourceId(template.getResourceId());
		teamTrainTemplateRsp.setRedRoles(redRoleRsps);
		teamTrainTemplateRsp.setBlueRoles(blueRoleRsps);

		return new BaseRsp<>(teamTrainTemplateRsp);
	}

	private List<JSONObject> getRoles(JSONArray roleArray, Map<String, MissionTemplate> mapping) {
		List<JSONObject> result = Lists.newArrayList();
		// 逐角色逐阶段读取任务信息
		roleArray.forEach(role -> {
			CrRoleRspBO roleRsp = ((JSONObject) role).toJavaObject(CrRoleRspBO.class);
			roleRsp.setId(IDGeneratorUtil.getID());
			roleRsp.getMissionsScheme().getSchemeData()
					.forEach(datum -> datum.getMissions().forEach(mission -> {
						MissionTemplate template = mapping.get(mission.getResourceId());
						mission.setTitle(template.getTitle());
						mission.setDescription(template.getDescription());
					}));
			// 转为蛇形
			result.add(JsonUtil.java2JsonObject(roleRsp, PropertyNamingStrategy.SnakeCase));
		});
		return result;
	}

}
