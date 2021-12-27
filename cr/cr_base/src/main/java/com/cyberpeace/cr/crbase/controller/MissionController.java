package com.cyberpeace.cr.crbase.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.*;
import com.cyberpeace.cr.crbase.controller.rsp.MissionCategoryRes;
import com.cyberpeace.cr.crbase.controller.rsp.MissionSchemeTemplateRsp;
import com.cyberpeace.cr.crbase.controller.rsp.MissionTemplateRsp;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;
import com.cyberpeace.cr.crbase.dao.entity.MissionSchemeTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeTemplateConfig;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "任务相关接口")
@RestController
@RequestMapping("/cr/admin/mission")
@RequiresAuthentication
public class MissionController {

	@Autowired
	private MissionService missionService;

	@Autowired
	private RemoteUserService remoteUserService;

	@ApiOperation("获取任务")
	@GetMapping("/missions")
	public BaseRsp<MissionTemplateRsp>
			getMission(@RequestParam("resource_id") @Validated String resourceId) {
		MissionTemplate missionTemplate = missionService.getByMissionTemplateId(resourceId);
		if (missionTemplate == null) {
			return new BaseRsp<>(null);
		}

		MissionTemplateRsp missionTemplateRsp = BeanUtils.copy(missionTemplate,
				MissionTemplateRsp.class);
		missionTemplateRsp.setSkillPoints(
				JsonUtil.toJsonObjectArray(missionTemplate.getSkillPoints(), Integer.class));
		missionTemplateRsp.setCategory(missionTemplate.getCategoryId());

		return new BaseRsp<>(missionTemplateRsp);
	}

	@ApiOperation("新增任务")
	@PostMapping("/missions")
	public BaseRsp<MissionTemplate>
			addMission(@RequestBody @Validated MissionTemplateReq missionTemplateReq) {
		MissionTemplate toAddMissionTemplate = requestToMissionTemplate(missionTemplateReq);

		MissionTemplate missionTemplate = missionService.addMissionTemplate(toAddMissionTemplate);

		return new BaseRsp(missionTemplate);
	}

	@ApiOperation("后台更新任务")
	@PutMapping("/missions")
	public BaseRsp<MissionTemplate>
			updateMissionTemplate(@RequestBody @Validated MissionTemplateModifyReq modifyReq) {
		if (StringUtils.isBlank(modifyReq.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		MissionTemplate toUpdateMissionTemplate = requestToMissionTemplate(modifyReq);
		missionService.updateMissionTemplate(toUpdateMissionTemplate);
		MissionTemplate ret = missionService
				.getByMissionTemplateId(toUpdateMissionTemplate.getResourceId());

		return new BaseRsp(ret);
	}

	private MissionTemplate requestToMissionTemplate(MissionTemplateReq missionTemplateReq) {
		MissionTemplate missionTemplate = BeanUtils.copy(missionTemplateReq, MissionTemplate.class);

		missionTemplate.setCategoryId(missionTemplateReq.getCategory());
		missionTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		missionTemplate.setSkillPoints(JSON.toJSONString(missionTemplateReq.getSkillPoints()));
		missionTemplate.setResourceId(IDGeneratorUtil.getID());
		missionTemplate.setAttachment(getAttachments(missionTemplate.getWriteUp()));

		return missionTemplate;
	}

	private MissionTemplate requestToMissionTemplate(MissionTemplateModifyReq modifyReq) {
		MissionTemplate missionTemplate = BeanUtils.copy(modifyReq, MissionTemplate.class);

		missionTemplate.setCategoryId(modifyReq.getCategory());
		missionTemplate.setSkillPoints(JSON.toJSONString(modifyReq.getSkillPoints()));
		missionTemplate.setAttachment(getAttachments(missionTemplate.getWriteUp()));

		return missionTemplate;
	}

	private String getAttachments(String writeup) {
		if (writeup == null || writeup.length() < CrCommonConstants.GENERATED_UUID_LENGTH) {
			return CommonConstant.EMPTY_LIST_JSON;
		}
		String imagePath = AttachmentPath.UPLOAD_IMAGE.getOriginDesc();
		String regex = String.format("!\\[[^]]*]\\(.*%s([0-9a-z]{%d})\\.(?:jpg|jpeg|png|bmp|webp|gif)\\)",
				imagePath, CrCommonConstants.GENERATED_UUID_LENGTH);
		List<String> imageIds = LocalStringUtil.getMatchStr(writeup, regex, 1);

		return JsonUtil.arrayToString(imageIds);
	}

	@ApiOperation("前台更新任务")
	@PutMapping("/mission_update")
	public BaseRsp<Mission>
			updateMissions(@RequestBody @Validated MissionModifyReq missionModifyReq) {
		if (StringUtils.isBlank(missionModifyReq.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		Mission toUpdateMission = requestToMission(missionModifyReq);
		missionService.updateById(toUpdateMission);

		return new BaseRsp();
	}

	private Mission requestToMission(MissionModifyReq missionModifyReq) {
		Mission mission = missionService.getById(missionModifyReq.getResourceId());
		if (StringUtils.isNotBlank(missionModifyReq.getTitle())) {
			mission.setTitle(missionModifyReq.getTitle());
		}
		if ((missionModifyReq.getScore() != null)) {
			mission.setScore(missionModifyReq.getScore());
		}
		if (missionModifyReq.getSkillPoints() != null) {
			mission.setSkillPoints(missionModifyReq.getSkillPoints());
		}

		return mission;
	}

	@ApiOperation("获取任务类型")
	@GetMapping("/missions/get_category/")
	public BaseRsp<List<MissionCategory>> listCategories() {

		List<MissionCategory> categories = missionService.listCategories();
		List<MissionCategoryRes> data = BeanUtils.deepCopyList(categories,
				MissionCategoryRes.class);

		return new BaseRsp(data);
	}

	@ApiOperation("创建任务方案")
	@PostMapping("/combat_techs")
	public BaseRsp<?>
			createSchemeTemplate(@RequestBody @Validated MissionSchemeTemplateReq templateReq) {
		MissionSchemeTemplate schemeTemplate = requestToSchemeTemplate(templateReq);
		missionService.addSchemeTemplate(schemeTemplate);
		return new BaseRsp<>();
	}

	private MissionSchemeTemplate requestToSchemeTemplate(MissionSchemeTemplateReq templateReq) {
		MissionSchemeTemplate schemeTemplate = BeanUtils.copy(templateReq,
				MissionSchemeTemplate.class);
		schemeTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		schemeTemplate.setTemplateConfig(JSON.toJSONString(new MissionSchemeTemplateConfig(
				templateReq.getTemplate(), templateReq.getColor())));
		schemeTemplate.setTemplateData(JSON.toJSONString(templateReq.getCombatConfig()));

		return schemeTemplate;
	}

	@ApiOperation("批量删除任务方案")
	@DeleteMapping("/combat_techs/batch_destroy/")
	public BaseRsp<?> batchDeleteSchemeTemplate(@RequestBody @Validated BatchDeleteReq deleteReq) {
		missionService.batchDeleteSchemeTemplate(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("编辑任务方案")
	@PutMapping("/combat_techs")
	public BaseRsp<?> editSchemeTemplate(
			@RequestBody @Validated MissionSchemeTemplateModifyReq templateModifyReq) {
		MissionSchemeTemplate toUpdateSchemeTemplate = requestToSchemeTemplate(templateModifyReq);
		missionService.updateMissionSchemeTemplateById(toUpdateSchemeTemplate);

		return new BaseRsp<>();
	}

	private MissionSchemeTemplate
			requestToSchemeTemplate(MissionSchemeTemplateModifyReq templateModifyReq) {
		if (StringUtils.isBlank(templateModifyReq.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		MissionSchemeTemplate schemeTemplate = missionService
				.getSchemeTemplateById(templateModifyReq.getResourceId());
		if (StringUtils.isNotBlank(templateModifyReq.getTitle())) {
			schemeTemplate.setTitle(templateModifyReq.getTitle());
		}
		if (StringUtils.isNotBlank(templateModifyReq.getDescription())) {
			schemeTemplate.setDescription(templateModifyReq.getDescription());
		}
		if (templateModifyReq.getTemplate() != null || templateModifyReq.getColor() != null) {
			MissionSchemeTemplateConfig config = JsonUtil.toJsonObject(
					schemeTemplate.getTemplateConfig(), MissionSchemeTemplateConfig.class);

			Integer template = templateModifyReq.getTemplate() == null ? config.getTemplate()
					: templateModifyReq.getTemplate();

			Integer color = templateModifyReq.getColor() == null ? config.getColor()
					: templateModifyReq.getColor();

			schemeTemplate.setTemplateConfig(
					JSON.toJSONString(new MissionSchemeTemplateConfig(template, color)));
		}
		if (templateModifyReq.getCombatConfig() != null) {
			schemeTemplate.setTemplateData(JSON.toJSONString(templateModifyReq.getCombatConfig()));
		}
		return schemeTemplate;
	}

	@ApiOperation("分页查询任务方案")
	@GetMapping("/combat_techs/list")
	public BaseRsp<List<MissionSchemeTemplateRsp>>
			listSchemeTemplate(@Validated PageReqBase pageReqBase) {
		ListRsp<MissionSchemeTemplateRsp> schemeTemplateListRsp = missionService
				.pageSchemeTemplateRsp(pageReqBase);
		List<String> userIds = Lists.newArrayList();
		for (MissionSchemeTemplateRsp rsp : schemeTemplateListRsp.getList()) {
			userIds.add(rsp.getCreateUser());
		}
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);

		for (MissionSchemeTemplateRsp rsp : schemeTemplateListRsp.getList()) {
			rsp.setRepName(idNameMap.get(rsp.getCreateUser()));
		}
		return new BaseRsp(schemeTemplateListRsp);
	}

	@ApiOperation("查询单个任务方案")
	@GetMapping("/combat_techs")
	public BaseRsp<MissionSchemeTemplateRsp> getSchemeTemplate(@ApiParam(name = "resource_id",
			value = "任务方案编号") @RequestParam("resource_id") @Valid @NotBlank String resourceId) {
		MissionSchemeTemplateRsp templateRsp = missionService.getSchemeTemplateRspById(resourceId);
		templateRsp.setRepName(remoteUserService.getById(templateRsp.getCreateUser()).getName());
		return new BaseRsp(templateRsp);
	}
}
