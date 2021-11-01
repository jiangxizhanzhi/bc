package com.cyberpeace.cr.teamtrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomReq;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomRes;
import com.cyberpeace.cr.teamtrain.model.room.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyInput;
import com.cyberpeace.cr.teamtrain.service.CrRoleService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.integratation.service.RemoteSceneConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "团队训练实例")
@RestController
@RequestMapping("/cr/team_train/room")
public class TeamTrainRoomController {

	@Autowired
	TeamRoomService teamRoomService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	TeamTemplateService teamTemplateService;

	@Autowired
	RemoteSceneConfigService sceneConfigService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@ApiOperation("新增")
	@PostMapping()
	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	public BaseRsp<TeamRoomRes> create(@RequestBody @Validated TeamTrainRoomReq input) {
		TeamTemplate entity = teamRoomService.create(input);

		return retrieve(entity.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<TeamRoomRes> update(@RequestBody @Validated TeamTrainTemplateModifyInput input) {
		String resourceId = ObjUtil.getCertainFieldValue(input, "resourceId");
		TeamTemplate template = teamTemplateService.getById(resourceId);

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		if (businessTemplate == null || template == null) {
			throw new ResourceNotFoundException("团队训练模板" + resourceId + "不存在");
		}
		TeamTemplate entity = teamRoomService.updateTeamRoom(input, businessTemplate);

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<TeamRoomRes> retrieve(String resource_id) {
		TeamRoomRes t = getTeamRoom(resource_id);

		return new BaseRsp<TeamRoomRes>(t);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		teamTemplateService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<TeamRoomRes>> list(TeamRoomReq listReq) {
		ListRsp<TeamRoomRes> data = teamRoomService.list(listReq);

		return new BaseRsp<>(data);
	}

	protected TeamRoomRes getTeamRoom(String resourceId) {
		TeamRoomRes teamRoomRes = teamRoomService.getTeamRoomOutputById(resourceId);
		if (teamRoomRes == null) {
			throw new ResourceNotFoundException("团队训练" + resourceId + "不存在");
		}

		return teamRoomRes;
	}

}
