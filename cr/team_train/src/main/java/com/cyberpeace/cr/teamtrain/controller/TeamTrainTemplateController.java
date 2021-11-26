package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTemplateListReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateAddReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateModifyReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamTrainTemplateRoleRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamTrainTemplateRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "团队训练模板")
@RestController
@RequiresAuthentication
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
				.setRoleList(JsonUtil.toJsonString(teamTrainTemplateAddReq.getRoleList()));

		businessTemplateService.addTemplate(teamTrainTemplateContext);

		return new BaseRsp<>();
	}

	@ApiOperation("修改")
	@PatchMapping()
	public BaseRsp<TeamTrainTemplateRsp>
			update(@RequestBody @Validated TeamTrainTemplateModifyReq teamTrainTemplateModifyReq) {
		String resourceId = teamTrainTemplateModifyReq.getResourceId();
		TeamTemplate template = teamTemplateService.getById(resourceId);

		TeamTrainTemplateContext teamTrainTemplateContext = BeanUtils
				.copy(teamTrainTemplateModifyReq, TeamTrainTemplateContext.class);

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
		TeamTemplate template = teamTemplateService.getTeamTemplateByBusinessId(resource_id);

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(template, businessTemplate);

		String roleString = template.getRoleConfig();
		List<TeamTrainTemplateRoleRsp> teamTrainTemplateRoleRsps = JsonUtil
				.toJsonObjectArray(roleString, TeamTrainTemplateRoleRsp.class);

		TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();

		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setPeopleNumber(baseInfoRsp.getPeopleNumber());

		teamTrainTemplateRsp.setRoleList(teamTrainTemplateRoleRsps);
		teamTrainTemplateRsp.setTimeLineConfig(template.getTimeLineConfig());

		return new BaseRsp<>(teamTrainTemplateRsp);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		List<TeamTemplate> teamTemplates = teamTemplateService
				.getTeamTemplateByBusinessIds(deleteReq.getIds());
		List<String> roomIds = teamTemplates.stream().map(TeamTemplate::getResourceId).distinct()
				.collect(Collectors.toList());

		teamTemplateService.batchDelete(deleteReq.getIds(), roomIds);

		return new BaseRsp<>();
	}

	@ApiOperation("前台列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<TeamTrainTemplateRsp>> list(TeamTemplateListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);

		IPage<TeamTemplateData> data = teamTemplateService.page(listQuery);

		data.convert(
				teamTemplateData -> getTeamTrainTemplateOutputByBaseInfoData(teamTemplateData));

		return new BaseRsp(data);
	}

	public TeamTrainTemplateRsp
			getTeamTrainTemplateOutputByBaseInfoData(TeamTemplateData teamTemplateData) {
		TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(teamTemplateData);

		teamTrainTemplateRsp.setResourceId(teamTemplateData.getResourceId());
		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		String roleString = teamTemplateData.getRoleConfig();

		List<BaseRole> teamTrainTemplateRoleList = JsonUtil.toJsonObjectArray(roleString,
				BaseRole.class);
		BaseRole baseRole = new BaseRole();
		baseRole.setName("教员");
		baseRole.setRoleGroup(CrRole.TeamRoleGroupCode.TEACHER.getValue());

		teamTrainTemplateRoleList.add(0, baseRole);
		teamTrainTemplateRsp.setRoleStructure(teamTrainTemplateRoleList);

		return teamTrainTemplateRsp;
	}

}
