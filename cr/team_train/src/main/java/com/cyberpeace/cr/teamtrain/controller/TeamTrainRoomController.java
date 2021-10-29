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
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.teamtrain.dao.entity.*;
import com.cyberpeace.cr.teamtrain.model.room.RoomRole;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomOutput;
import com.cyberpeace.cr.teamtrain.model.room.TeamTrainRoomInput;
import com.cyberpeace.cr.teamtrain.model.template.BaseInfoInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTemplateReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateOutput;
import com.cyberpeace.cr.teamtrain.service.BusinessTemplateService;
import com.cyberpeace.cr.teamtrain.service.CrRoleService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.service.RemoteSceneConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "团队训练实例")
@RestController
@RequestMapping("/cr/team_train/room")
public class TeamTrainRoomController {

	@Autowired
	TeamTemplateService teamTemplateService;

	@Autowired
	TeamRoomService teamRoomService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	RemoteSceneConfigService sceneConfigService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@ApiOperation("新增")
	@PostMapping()
	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	public BaseRsp<TeamRoomOutput> create(@RequestBody @Validated TeamTrainRoomInput input) {
		TeamTemplate entity = getEntityFromAddInput(input);

		return retrieve(entity.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<TeamRoomOutput>
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
	public BaseRsp<TeamRoomOutput> retrieve(String resource_id) {
		TeamRoomOutput t = getTeamRoom(resource_id);
		return new BaseRsp<TeamRoomOutput>(t);
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

	protected TeamRoomOutput getTeamRoom(String resourceId) {
		TeamRoomOutput teamRoomOutput = teamRoomService.getTeamRoomOutputById(resourceId);
		if (teamRoomOutput == null) {
			throw new ResourceNotFoundException("团队训练" + resourceId + "不存在");
		}

		return teamRoomOutput;
	}

	public TeamTemplate getEntityFromAddInput(TeamTrainRoomInput input) {

		TeamTemplate template = teamTemplateService.getById(input.getTemplate());
		if (template == null) {
			throw new ResourceNotFoundException("团队训练模板" + input.getTemplate() + "不存在");
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		TeamRoom teamRoom = new TeamRoom(input, template, businessTemplate);

		teamRoomService.save(teamRoom);
		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
				.toJsonObjectArray(template.getRoleConfig(), TeamTrainTemplateRole.class);
		List<RoomRole> users = input.getRoleConfig();
		int index = 0;
		for (TeamTrainTemplateRole teamTrainTemplateRole : teamTrainTemplateRoleList) {

			CrRole crRole = BeanUtils.deepCopy(teamTrainTemplateRole, CrRole.class);
			// crRole.setBusinessInstanceId();
			crRole.setUserIds(JsonUtil.toJsonString(users.get(index++).getUsers()));
			teamTrainTemplateRole.getResourceId();
			JsonUtil.toJsonString(teamTrainTemplateRoleList);
			crRole.setBusinessInstanceId(teamRoom.getResourceId());
			crRole.setBusinessType(CrEvent.CeType.TRAINING_TEAM);
			crRoleService.save(crRole);

			// teamTrainTemplateRoleList.add(teamTrainTemplateRole);
		}
		int r = 5 / 0;
		// teamTemplate.setRoleConfig(JsonUtil.toJsonString(teamTrainTemplateRoleList));

		// 保存任务信息
		// MissionScheme missionScheme = input.getMissionScheme();
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		// teamTemplateService.save(template);

		return template;
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
