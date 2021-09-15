package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.suit.DeleteReq;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.cebase.model.crevent.CrEventAddInput;
import com.cyberpeace.cr.cebase.model.crevent.CrEventModifyInput;
import com.cyberpeace.cr.cebase.model.crevent.CrEventOutput;
import com.cyberpeace.cr.cebase.service.CrEventService;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.model.template.RoleListInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateAddInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateOutput;
import com.cyberpeace.cr.teamtrain.service.TeamTrainTemplateRoleService;
import com.cyberpeace.cr.teamtrain.service.TeamTrainTemplateService;
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
	TeamTrainTemplateService teamTrainTemplateService;

	@Autowired
	TeamTrainTemplateRoleService teamTrainTemplateRoleService;

	@Autowired
	CrEventService crEventService;

	@Autowired
	RemoteSceneConfigService sceneConfigService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<TeamTrainTemplateOutput>
			create(@RequestBody @Validated TeamTrainTemplateAddInput input) {
		TeamTrainTemplate entity = getEntityFromAddInput(input);

		teamTrainTemplateService.save(entity);

		return retrieve(entity.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<TeamTrainTemplateOutput>
			update(@RequestBody @Validated TeamTrainTemplateModifyInput input) {
		String resourceId = ObjUtil.getCertainFieldValue(input, "resourceId");

		TeamTrainTemplate originEntity = getOr404(resourceId);

		TeamTrainTemplate entity = getEntityFromModifyInput(input, originEntity);

		teamTrainTemplateService.updateById(entity);

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<TeamTrainTemplateOutput> retrieve(String resource_id) {
		TeamTrainTemplate t = getOr404(resource_id);
		return new BaseRsp<>(getOutputFromEntity(t));
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		teamTrainTemplateService.removeByIds(deleteReq.getIds());;
		return new BaseRsp<>();
	}

	protected TeamTrainTemplate getOr404(String resourceId) {
		TeamTrainTemplate template = teamTrainTemplateService.getById(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException("团队训练模板" + resourceId + "不存在");
		}
		return template;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	protected TeamTrainTemplate getEntityFromAddInput(TeamTrainTemplateAddInput input) {
		TeamTrainTemplate template = new TeamTrainTemplate();

		CrEventAddInput crEventAddInput = input.getBaseInfo();
		CrEvent crEvent = crEventService.create(crEventAddInput, CrEvent.CeType.TRAINING_TEAM);
		template.setCrEventId(crEvent.getResourceId());

		SceneConfig sceneConfig = input.getSceneConfigData();
		String sceneConfigId = sceneConfigService.create(sceneConfig);
		template.setSceneConfig(sceneConfigId);
		template.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));
		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = Lists.newArrayList();

		for (RoleListInput roleListInput : input.getRoleList()) {
			TeamTrainTemplateRole teamTrainTemplateRole = BeanUtils.deepCopy(roleListInput,
					TeamTrainTemplateRole.class);
			teamTrainTemplateRole.setCrEventId(crEvent.getResourceId());
			teamTrainTemplateRole
					.setMissionsScheme(JsonUtil.toJsonString(roleListInput.getMissionsScheme()));

			teamTrainTemplateRoleList.add(teamTrainTemplateRole);
		}
		teamTrainTemplateRoleService.saveBatch(teamTrainTemplateRoleList);

		// MissionScheme missionScheme = input.getMissionScheme();
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);

		return template;
	}

	protected TeamTrainTemplate getEntityFromModifyInput(TeamTrainTemplateModifyInput input,
			TeamTrainTemplate originEntity) {
		TeamTrainTemplate template = new TeamTrainTemplate();

		CrEventModifyInput crEventModifyInput = input.getCrEvent();
		crEventService.update(originEntity.getCrEventId(), crEventModifyInput);
		// 无需更新template的crEvent字段

		SceneConfig sceneConfig = input.getSceneConfigData();
		if (sceneConfig != null) {
			String sceneConfigId = sceneConfigService.create(sceneConfig);
			template.setSceneConfig(sceneConfigId);
		}

		// MissionScheme missionScheme = input.getMissionScheme();
		// if (missionScheme != null) {
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		// }

		return template;
	}

	protected TeamTrainTemplateOutput getOutputFromEntity(TeamTrainTemplate template) {
		TeamTrainTemplateOutput templateOutput = new TeamTrainTemplateOutput();

		CrEvent crEvent = crEventService.getOr404(template.getCrEventId());
		CrEventOutput crEventOutput = new CrEventOutput(crEvent);
		templateOutput.setCrEvent(crEventOutput);

		String sceneConfigId = template.getSceneConfig();
		SceneConfig sceneConfig = sceneConfigService.getById(sceneConfigId);
		templateOutput.setSceneConfig(sceneConfig);

		// String missionSchemeId = template.getMissionSchemeId();
		// MissionScheme missionScheme = missionSchemeService.getById(missionSchemeId);
		// templateOutput.setMissionScheme(missionScheme);

		return templateOutput;
	}
}
