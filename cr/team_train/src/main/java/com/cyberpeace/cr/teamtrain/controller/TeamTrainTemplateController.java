package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.model.template.*;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
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
	BusinessTemplateService businessTemplateService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<TeamTrainTemplateRsp>
			create(@RequestBody @Validated TeamTrainTemplateAddReq teamTrainTemplateAddReq) {
		TeamTrainTemplateContext teamTrainTemplateContext = BeanUtils.copy(teamTrainTemplateAddReq,
				TeamTrainTemplateContext.class);
		teamTrainTemplateContext
				.setRoleList(getRoleListFromReq(teamTrainTemplateAddReq.getRoleList()));
		TeamTemplate teamTemplate = teamTemplateService.create(teamTrainTemplateContext);

		return getTeamTemplate(teamTemplate.getResourceId());
	}

	private List<TeamTrainTemplateRole> getRoleListFromReq(List<RoleListReq> roleList) {
		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = Lists.newArrayList();

		for (RoleListReq roleListReq : roleList) {
			TeamTrainTemplateRole teamTrainTemplateRole = BeanUtils.deepCopy(roleListReq,
					TeamTrainTemplateRole.class);
			teamTrainTemplateRole
					.setMissionsScheme(JsonUtil.toJsonString(roleListReq.getMissionsScheme()));

			teamTrainTemplateRoleList.add(teamTrainTemplateRole);
		}
		return teamTrainTemplateRoleList;
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<TeamTrainTemplateRsp>
			update(@RequestBody @Validated TeamTrainTemplateModifyReq teamTrainTemplateModifyReq) {
		String resourceId = teamTrainTemplateModifyReq.getResourceId();
		TeamTemplate template = teamTemplateService.getById(resourceId);

		TeamTrainTemplateContext teamTrainTemplateContext = new TeamTrainTemplateContext();
		BeanUtils.copy(teamTrainTemplateModifyReq, TeamTrainTemplateContext.class);

		if (template == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());
		if (businessTemplate == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		teamTemplateService.updateTeamTemplate(teamTrainTemplateContext, businessTemplate);

		return getTeamTemplate(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<TeamTrainTemplateRsp> getTeamTemplate(String resource_id) {
		TeamTrainTemplateRsp data = teamTemplateService.getTeamTemplate(resource_id);

		return new BaseRsp<>(data);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		teamTemplateService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<TeamTrainTemplateRsp>> list(TeamTemplateReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		ListRsp<TeamTrainTemplateRsp> data = teamTemplateService.page(listQuery);

		return new BaseRsp<>(data);
	}

}
