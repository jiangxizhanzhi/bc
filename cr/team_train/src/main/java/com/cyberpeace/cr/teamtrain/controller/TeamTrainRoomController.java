package com.cyberpeace.cr.teamtrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.room.ResourceIdReq;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomReq;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.model.room.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateReq;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
import com.cyberpeace.integratation.service.RemoteTopoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	RemoteTopoService sceneConfigService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<TeamRoomRsp> create(@RequestBody @Validated TeamTrainRoomReq teamTrainRoomReq) {
		TeamTrainRoomContext teamTrainRoomContext = BeanUtils.copy(teamTrainRoomReq,
				TeamTrainRoomContext.class);
		TeamTemplate teamTemplate = teamRoomService.create(teamTrainRoomContext);

		return getTeamRoom(teamTemplate.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<TeamRoomRsp>
			update(@RequestBody @Validated TeamTrainTemplateReq teamTrainTemplateReq) {

		TeamTrainTemplateContext teamTrainTemplateContext = new TeamTrainTemplateContext();
		BeanUtils.copy(teamTrainTemplateReq, TeamTrainTemplateContext.class);

		String resourceId = teamTrainTemplateContext.getResourceId();
		TeamTemplate template = teamTemplateService.getById(resourceId);

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		if (businessTemplate == null || template == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		teamRoomService.updateTeamRoom(teamTrainTemplateContext, businessTemplate);

		return getTeamRoom(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<TeamRoomRsp> getTeamRoom(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		TeamRoomRsp teamRoomRsp = teamRoomService.getTeamRoomRspByRoomId(resource_id);

		return new BaseRsp<TeamRoomRsp>(teamRoomRsp);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		teamRoomService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<TeamRoomRsp>> list(TeamRoomReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		ListRsp<TeamRoomRsp> data = teamRoomService.list(listQuery);

		return new BaseRsp<>(data);
	}

	@ApiOperation("修改团队训练状态")
	@PostMapping("/startEndTeam")
	public BaseRsp<Integer>
			changeTrainingStatus(@RequestBody @Validated ResourceIdReq resourceIdReq) {
		int status = teamRoomService.changeTrainingStatus(resourceIdReq.getResourceId());

		return new BaseRsp<>(status);
	}

	@ApiOperation("查询团队训练状态")
	@GetMapping("/startEndTeam")
	public BaseRsp<Integer> getTrainingStatus(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		Integer status = teamRoomService.getTrainingStatus(resource_id);

		return new BaseRsp<>(status);
	}
}
