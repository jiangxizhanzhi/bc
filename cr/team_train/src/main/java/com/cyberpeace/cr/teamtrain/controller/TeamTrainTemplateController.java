package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTemplateListReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateAddReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateModifyReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamTrainTemplateRoleRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamTrainTemplateRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.RoleListContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateRole;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private MissionService missionService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	LocalSceneService localSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp create(
			@RequestBody @Validated TeamTrainTemplateAddReq<AddTemplateMission> teamTrainTemplateAddReq) {
		TeamTrainTemplateContext teamTrainTemplateContext = BeanUtils.copy(teamTrainTemplateAddReq,
				TeamTrainTemplateContext.class);
		teamTrainTemplateContext.setRoleList(req2DbJson(teamTrainTemplateAddReq));
		teamTrainTemplateContext.getBaseInfo()
				.setType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());

		businessTemplateService.addTemplate(teamTrainTemplateContext);

		return new BaseRsp<>();
	}

	@ApiOperation("修改")
	@PatchMapping()
	public BaseRsp<TeamTrainTemplateRsp> update(
			@RequestBody @Validated TeamTrainTemplateModifyReq<AddTemplateMission> teamTrainTemplateModifyReq) {
		String resourceId = teamTrainTemplateModifyReq.getResourceId();
		TeamTemplate template = teamTemplateService.getByTemplateBusinessId(resourceId);

		TeamTrainTemplateContext teamTrainTemplateContext = BeanUtils
				.copy(teamTrainTemplateModifyReq, TeamTrainTemplateContext.class);
		teamTrainTemplateContext.setRoleList(req2DbJson(teamTrainTemplateModifyReq));

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil.toJsonObjectArray(
				teamTrainTemplateContext.getRoleList(), TeamTrainTemplateRole.class);

		// 校验任务id是否满足要求
		for (RoleListContext<AddTemplateMission> roleListContext : teamTrainTemplateModifyReq
				.getRoleList()) {
			ColorMission<AddTemplateMission> missionsScheme = roleListContext.getMissionsScheme();
			missionService.validateMission(missionsScheme);
		}

		if (template == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		teamTrainTemplateContext.setRoleList(JsonUtil.arrayToString(teamTrainTemplateRoleList));

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
		businessTemplateService.checkPermission(resource_id,
				UserInfoContext.getUserContext().getUserId());

		TeamTemplate template = teamTemplateService.getByTemplateBusinessId(resource_id);

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(template, businessTemplate);

		String roleString = template.getRoleConfig();
		List<TeamTrainTemplateRoleRsp> teamTrainTemplateRoleRsps = JsonUtil
				.toJsonObjectArray(roleString, TeamTrainTemplateRoleRsp.class);
		int index = 1;
		for (TeamTrainTemplateRoleRsp teamTrainTemplateRoleRsp : teamTrainTemplateRoleRsps) {
			teamTrainTemplateRoleRsp.setId(template.getResourceId() + "_" + index++);
			for (MissionSchemeObject<AddTemplateMission> schemeData : teamTrainTemplateRoleRsp
					.getMissionsScheme().getSchemeData()) {
				missionService.getMissionContent(schemeData);
			}
		}
		baseInfoRsp.setSceneConfigData(localSceneService.getTopoData(businessTemplate.getTopoId()));
		String sceneManagerId = "";
		LocalScene localScene = localSceneService
				.getByBusinessInstanceId(businessTemplate.getResourceId());
		if (localScene != null) {
			sceneManagerId = localScene.getResourceId();
		}
		baseInfoRsp.setSceneManagerId(sceneManagerId);

		TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();

		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setPeopleNumber(baseInfoRsp.getPeopleNumber());
		teamTrainTemplateRsp.setResourceId(template.getResourceId());

		teamTrainTemplateRsp.setRoleList(teamTrainTemplateRoleRsps);
		teamTrainTemplateRsp.setTimeLineConfig(template.getTimeLineConfig());

		try {
			String json = new ObjectMapper().writeValueAsString(teamTrainTemplateRsp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return new BaseRsp(teamTrainTemplateRsp);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		List<TeamTemplate> teamTemplates = teamTemplateService
				.getTeamTemplateByBusinessIds(deleteReq.getIds());
		List<String> teamTemplateIds = teamTemplates.stream().map(TeamTemplate::getResourceId)
				.distinct().collect(Collectors.toList());

		teamTemplateService.batchDelete(deleteReq.getIds(), teamTemplateIds);

		return new BaseRsp<>();
	}

	@ApiOperation("前台列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<TeamTrainTemplateRsp>> list(TeamTemplateListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		// 默认时间排序
		listQuery.setSort("-cr_business_template.create_time");

		IPage<TeamTemplateData> data = teamTemplateService.page(listQuery);
		// 训练场景拓扑节点数量获取
		Map<String, TopoDigest> topologyNodesStats = remoteTopoService.batchGetTopoDigest(
				data.getRecords().stream().map(teamTemplateData -> teamTemplateData.getTopoId())
						.collect(Collectors.toList()));

		data.convert(teamTemplateData -> getTeamTrainTemplateOutputByBaseInfoData(teamTemplateData,
				topologyNodesStats));

		return new BaseRsp(data);
	}

	public TeamTrainTemplateRsp getTeamTrainTemplateOutputByBaseInfoData(
			TeamTemplateData teamTemplateData, Map<String, TopoDigest> topologyNodesStats) {

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(teamTemplateData);

		// 获取角色列表
		String roleString = teamTemplateData.getRoleConfig();

		List<BaseRole> teamTrainTemplateRoleList = JsonUtil.toJsonObjectArray(roleString,
				BaseRole.class);
		teamTrainTemplateRoleList
				.forEach(role -> role.setRoleGroup(CrRole.TeamRoleGroupCode.STUDENT.getCode()));

		BaseRole baseRole = new BaseRole();
		baseRole.setName(CrRole.TeamRoleGroupCode.TEACHER.getDesc());
		baseRole.setRoleGroup(CrRole.TeamRoleGroupCode.TEACHER.getCode());

		teamTrainTemplateRoleList.add(0, baseRole);

		// 设置返回对象
		TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();
		teamTrainTemplateRsp.setNodesCount(
				topologyNodesStats.get(teamTemplateData.getTopoId()).getNodeCounts());
		teamTrainTemplateRsp.setResourceId(teamTemplateData.getResourceId());
		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setRoleStructure(teamTrainTemplateRoleList);

		return teamTrainTemplateRsp;
	}

	/**
	 * 过滤前端输入的 req 的 roleList
	 * @param req 
	 * @return
	 */
	private String req2DbJson(TeamTrainTemplateReq req) {
		SimplePropertyPreFilter missionFilter = new SimplePropertyPreFilter(
				AddTemplateMission.class, "resourceId");
		SimplePropertyPreFilter schemeFilter = new SimplePropertyPreFilter(
				MissionSchemeObject.class, "stage", "missions");
		SimplePropertyPreFilter roleFilter = new SimplePropertyPreFilter(RoleListContext.class,
				"name", "assetNodes", "visibleNodes", "missionsScheme");
		return JSONObject.toJSONString(req.getRoleList(),
				new SimplePropertyPreFilter[] { missionFilter, schemeFilter, roleFilter });
	}

}
