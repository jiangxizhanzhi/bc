package com.cyberpeace.cr.teamtrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.template.TeamTemplateReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateAddReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateRsp;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;

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
			create(@RequestBody @Validated TeamTrainTemplateAddReq input) {
		TeamTemplate teamTemplate = teamTemplateService.create(input);

		return getTeamTemplate(teamTemplate.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<TeamTrainTemplateRsp>
			update(@RequestBody @Validated TeamTrainTemplateModifyReq input) {
		String resourceId = input.getResourceId();
		TeamTemplate template = teamTemplateService.getById(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());
		if (businessTemplate == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		teamTemplateService.updateTeamTemplate(input, businessTemplate);

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
		ListRsp<TeamTrainTemplateRsp> data = teamTemplateService.page(listReq);

		return new BaseRsp<>(data);
	}

}
