package com.cyberpeace.cr.teamtrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.cyberpeace.cr.teamtrain.model.template.TeamTemplateReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateAddInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateOutput;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.TeamTrainTemplateRoleService;
import com.cyberpeace.integratation.service.RemoteSceneConfigService;

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
	BusinessTemplateService businessTemplateService;

	@Autowired
	RemoteSceneConfigService sceneConfigService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<TeamTrainTemplateOutput>
			create(@RequestBody @Validated TeamTrainTemplateAddInput input) {
		TeamTemplate entity = teamTemplateService.create(input);

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
		TeamTemplate entity = teamTemplateService.updateTeamTemplate(input, businessTemplate);

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<TeamTrainTemplateOutput> retrieve(String resource_id) {
		TeamTrainTemplateOutput t = teamTemplateService.getTeamTemplate(resource_id);
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

}
