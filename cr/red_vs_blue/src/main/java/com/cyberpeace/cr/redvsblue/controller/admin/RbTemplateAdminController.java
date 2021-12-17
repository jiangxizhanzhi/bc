package com.cyberpeace.cr.redvsblue.controller.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.AddRbTemplateMission;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateAddReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateModifyReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateRoleRsp;
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
	private LocalSceneService localSceneService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp create(
			@RequestBody @Validated RbTemplateAddReq<AddRbTemplateMission> rbTemplateAddReq) {

		RbTemplateContext rbTemplateContext = BeanUtils.copy(rbTemplateAddReq,
				RbTemplateContext.class);

		JSONObject roleJsonObject = new JSONObject();
		roleJsonObject.put("redRoles", rbTemplateAddReq.getRedRoles());
		roleJsonObject.put("blueRoles", rbTemplateAddReq.getBlueRoles());

		rbTemplateContext.setRoleList(JsonUtil.toJsonString(roleJsonObject));
		rbTemplateContext.getBaseInfo()
				.setType(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());

		businessTemplateService.addTemplate(rbTemplateContext);

		return new BaseRsp<>();
	}

	@ApiOperation("修改")
	@PatchMapping()
	public BaseRsp update(
			@RequestBody @Validated RbTemplateModifyReq<AddRbTemplateMission> rbTemplateModifyReq) {
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
		roleJsonObject.put("redRoles", rbTemplateModifyReq.getRedRoles());
		roleJsonObject.put("blueRoles", rbTemplateModifyReq.getBlueRoles());

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
		RbTemplate template = rbTemplateService.getRbTemplateByBusinessId(resource_id);

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(template, businessTemplate);

		String roleString = template.getRoleConfig();
		JSONObject roleObject = JsonUtil.toJsonObject(roleString, JSONObject.class);
		List<RbTemplateRoleRsp> redRoleRsps = JsonUtil
				.toJsonObjectArray(roleObject.get("redRoles").toString(), RbTemplateRoleRsp.class);
		List<RbTemplateRoleRsp> blueRoleRsps = JsonUtil
				.toJsonObjectArray(roleObject.get("blueRoles").toString(), RbTemplateRoleRsp.class);
		baseInfoRsp.setSceneConfigData(localSceneService.getTopoData(businessTemplate.getTopoId()));
		String sceneManagerId = "";
		LocalScene localScene = localSceneService
				.getByBusinessInstanceId(businessTemplate.getResourceId());
		if (localScene != null) {
			sceneManagerId = localScene.getResourceId();
		}
		baseInfoRsp.setSceneManagerId(sceneManagerId);

		RbTemplateRsp teamTrainTemplateRsp = new RbTemplateRsp();

		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setResourceId(template.getResourceId());

		teamTrainTemplateRsp.setRedRoles(redRoleRsps);
		teamTrainTemplateRsp.setBlueRoles(blueRoleRsps);

		return new BaseRsp<>(teamTrainTemplateRsp);
	}

}
