package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.cr.cebase.service.CrEventService;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.teamtrain.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.model.template.*;
import com.cyberpeace.cr.teamtrain.service.BusinessTemplateService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.TeamTrainTemplateRoleService;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.service.RemoteSceneConfigService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "团队训练模板")
@RestController
@RequestMapping("/cr/team_train/template")
public class TeamTrainTemplateController {

	@Autowired
	TeamTemplateService teamTemplateService;

	@Autowired
	TeamTrainTemplateRoleService teamTrainTemplateRoleService;

	@Autowired
	CrEventService crEventService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	RemoteSceneConfigService sceneConfigService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<TeamTrainTemplateOutput>
			create(@RequestBody @Validated TeamTrainTemplateAddInput input) {
		TeamTemplate entity = getEntityFromAddInput(input);

		return retrieve(entity.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<TeamTrainTemplateOutput>
			update(@RequestBody @Validated TeamTrainTemplateModifyInput input) {
		String resourceId = ObjUtil.getCertainFieldValue(input, "resourceId");
		TeamTemplate template = teamTemplateService.getById(resourceId);
		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());
		if (businessTemplate == null || template == null) {
			throw new ResourceNotFoundException("团队训练模板" + resourceId + "不存在");
		}
		TeamTemplate entity = getEntityFromModifyInput(input, businessTemplate);

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<TeamTrainTemplateOutput> retrieve(String resource_id) {
		TeamTrainTemplateOutput t = getOr404(resource_id);
		return new BaseRsp<>(t);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		teamTemplateService.removeByIds(deleteReq.getIds());;
		return new BaseRsp<>();
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<TeamTrainTemplateOutput>> list(TeamTemplateReq listReq) {
		ListRsp<TeamTrainTemplateOutput> data = teamTemplateService.page(listReq);

		return new BaseRsp<>(data);
	}


	protected TeamTrainTemplateOutput getOr404(String resourceId) {
		TeamTrainTemplateOutput teamTrainTemplateOutput = new TeamTrainTemplateOutput();
		TeamTemplate template = teamTemplateService.getById(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException("团队训练模板" + resourceId + "不存在");
		}
		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		BaseInfoOutput baseInfoOutput = new BaseInfoOutput(template, businessTemplate);
		String roleString = template.getRoleConfig();

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
				.toJsonObjectArray(roleString, TeamTrainTemplateRole.class);
		teamTrainTemplateOutput.setBaseInfo(baseInfoOutput);
		teamTrainTemplateOutput.setRoleList(teamTrainTemplateRoleList);

		teamTrainTemplateOutput.setTimeLineConfig(template.getTimeLineConfig());

		return teamTrainTemplateOutput;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	public TeamTemplate getEntityFromAddInput(TeamTrainTemplateAddInput input) {
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfoInput crEventAddInput = input.getBaseInfo();
		BusinessTemplate businessTemplate = new BusinessTemplate(crEventAddInput);

		// 拓扑场景id保存获取
		SceneConfig sceneConfig = input.getSceneConfigData();
		String sceneConfigId = sceneConfigService.create(sceneConfig);

		// 保存基础模板
		businessTemplate.setTopoId(sceneConfigId);
		businessTemplate.setCreateUser(UserInfoContext.getUserContext().getUserId());

		businessTemplateService.save(businessTemplate);
		// BusinessTemplate businessTemplate = crEventService.create(crEventAddInput,
		// CrEvent.CeType.TRAINING_TEAM);

		// 保存团队训练模板
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(crEventAddInput.getPre_skill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(crEventAddInput.getTrain_skill()));
		teamTemplate.setSubType(crEventAddInput.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = Lists.newArrayList();

		for (RoleListInput roleListInput : input.getRoleList()) {
			TeamTrainTemplateRole teamTrainTemplateRole = BeanUtils.deepCopy(roleListInput,
					TeamTrainTemplateRole.class);
			teamTrainTemplateRole.setCrEventId(businessTemplate.getResourceId());
			teamTrainTemplateRole
					.setMissionsScheme(JsonUtil.toJsonString(roleListInput.getMissionsScheme()));

			teamTrainTemplateRoleList.add(teamTrainTemplateRole);
		}
		teamTemplate.setRoleConfig(JsonUtil.toJsonString(teamTrainTemplateRoleList));

		// 保存任务信息
		// MissionScheme missionScheme = input.getMissionScheme();
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		teamTemplateService.save(teamTemplate);

		return teamTemplate;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	public TeamTemplate getEntityFromModifyInput(TeamTrainTemplateModifyInput input,
			BusinessTemplate businessTemplate) {
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfoInput baseInfoInput = input.getBaseInfo();

		SceneConfig sceneConfig = input.getSceneConfigData();

		businessTemplate.setTopoId("");
		businessTemplate.setCreateUser(UserInfoContext.getUserContext().getUserId());
		businessTemplate.setResourceId(businessTemplate.getResourceId());
		businessTemplate.setName(baseInfoInput.getName());
		businessTemplate.setDescription(baseInfoInput.getDescription());
		businessTemplate.setDifficulty(baseInfoInput.getDifficult());

		if (sceneConfig != null) {
			String sceneConfigId = sceneConfigService.create(sceneConfig);
			businessTemplate.setTopoId(sceneConfigId);
		}

		businessTemplateService.updateById(businessTemplate);

		// 保存团队训练模板
		teamTemplate.setResourceId(input.getResourceId());
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(baseInfoInput.getPre_skill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfoInput.getTrain_skill()));
		teamTemplate.setSubType(baseInfoInput.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));
		teamTemplate.setDurationTime(baseInfoInput.getDurationTime());
		teamTemplate.setPeopleNumber(baseInfoInput.getPeopleNumber());

		teamTemplateService.updateById(teamTemplate);
		// MissionScheme missionScheme = input.getMissionScheme();
		// if (missionScheme != null) {
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		// }

		return teamTemplate;
	}

}
