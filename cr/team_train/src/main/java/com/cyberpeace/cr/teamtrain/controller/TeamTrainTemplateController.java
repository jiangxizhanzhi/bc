package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
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
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTopoService;

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

	@Autowired
	LocalSceneService localSceneService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrCommonService crCommonService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private RtksRelatedService rtksRelatedService;

	@Autowired
	private RemoteSceneService remoteSceneService;
	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN)
	public BaseRsp create(@RequestBody @Validated TeamTrainTemplateAddReq addReq) {
		// set type
		addReq.getBaseInfo().setType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		// create team template
		TeamTemplate teamTemplate = reqToTeamTemplate(addReq, new TeamTemplate());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// save business template
				String businessTemplateId = businessTemplateService
						.addTemplate(addReq.getBaseInfo());
				// save team template
				teamTemplate.setBusinessTemplateId(businessTemplateId);
				teamTemplateService.save(teamTemplate);
				// update related missionTemplates
				businessTemplateService.updateMissionTemplateRelation(businessTemplateId, "",
						teamTemplate.getRoleConfig());
			}
		});

		String teamTrainTemplateName = addReq.getBaseInfo().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.TEAM_TRAIN,
				OperationType.CREATE, teamTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.CREATE,
						LogContent.TEAM_TRAIN_TEMPLATE.getMsg(), teamTrainTemplateName));

		return new BaseRsp<>();
	}

	@ApiOperation("修改")
	@PatchMapping()
	@RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN)
	public BaseRsp update(@RequestBody @Validated TeamTrainTemplateModifyReq modifyReq) {
		String resourceId = modifyReq.getResourceId();
		TeamTemplate teamTemplate = teamTemplateService.getByTemplateBusinessId(resourceId);
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		if (businessTemplate == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		// update
		String oldRoleConfig = teamTemplate.getRoleConfig();
		reqToTeamTemplate(modifyReq, teamTemplate);

		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.updateTemplate(businessTemplate, modifyReq.getBaseInfo());
				teamTemplateService.updateById(teamTemplate);
				// delete&update related missionTemplates
				businessTemplateService.updateMissionTemplateRelation(resourceId, oldRoleConfig,
						teamTemplate.getRoleConfig());
			}
		});

		String teamTrainTemplateName = modifyReq.getBaseInfo().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.TEAM_TRAIN,
				OperationType.UPDATE, teamTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.UPDATE,
						LogContent.TEAM_TRAIN_TEMPLATE.getMsg(), teamTrainTemplateName));

		return new BaseRsp<>();
	}

	@ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(value = { MenuConstant.CMS_CR_TEAM_TRAIN, MenuConstant.WEB_CR_TEAM_TRAIN },
		logical = Logical.OR)
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

		// 如果场景信息不为null，提取logo参数（默认为空字符串）
		if (baseInfoRsp.getSceneConfigData() != null) {
			baseInfoRsp.setTopo(baseInfoRsp.getSceneConfigData().getString("logo"));
		}

		TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();

		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setPeopleNumber(baseInfoRsp.getPeopleNumber());
		teamTrainTemplateRsp.setResourceId(template.getResourceId());

		teamTrainTemplateRsp.setRoleList(teamTrainTemplateRoleRsps);
		teamTrainTemplateRsp.setTimeLineConfig(template.getTimeLineConfig());
		for (TeamTrainTemplateRoleRsp teamTrainTemplateRoleRsp : teamTrainTemplateRsp.getRoleList()) {
			teamTrainTemplateRoleRsp.initAssetMap();
		}
		return new BaseRsp(teamTrainTemplateRsp);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN)
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		List<BusinessTemplate> businessTemplateList = businessTemplateService
				.listByIds(deleteReq.getIds());

		teamTemplateService.batchDelete(deleteReq.getIds());

		List<String> teamTrainTemplateName = StreamApiUtils.getColumn(businessTemplateList,
				BusinessTemplate::getName);

		if (businessTemplateList != null) {
			remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.TEAM_TRAIN,
					OperationType.DELETE,
					LogContent.BATCH_DELETE_FORMAT.format(LogContent.TEAM_TRAIN_TEMPLATE.getMsg()),
					LogContent.CR_FORMAT.format(OperationType.DELETE,
							LogContent.TEAM_TRAIN_TEMPLATE.getMsg(),
							teamTrainTemplateName.toString()));
		} ;

		return new BaseRsp<>();
	}

	@ApiOperation("前台列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
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
		// get attack engage relations
		List<String> businessTemplateIds = data.getRecords().stream()
				.map(TeamTemplateData::getBusinessTemplateId).collect(Collectors.toList());
		Map<String, List<NameCount>> attackRelCount = rtksRelatedService
				.countAttackRel(businessTemplateIds);
		Map<String, List<NameCount>> icsRelCount = rtksRelatedService
				.countIcsRel(businessTemplateIds);
		Map<String, List<NameCount>> engageRelCount = rtksRelatedService
				.countEngageRel(businessTemplateIds);

		data.convert(teamTemplateData -> getTeamTrainTemplateOutputByBaseInfoData(teamTemplateData,
				topologyNodesStats, attackRelCount, icsRelCount, engageRelCount));

		return new BaseRsp(data);
	}

	public TeamTrainTemplateRsp getTeamTrainTemplateOutputByBaseInfoData(
			TeamTemplateData teamTemplateData, Map<String, TopoDigest> topologyNodesStats,
			Map<String, List<NameCount>> attackRelCount, Map<String, List<NameCount>> icsRelCount,
			Map<String, List<NameCount>> engageRelCount) {

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(teamTemplateData);

		// 获取角色列表
		String roleString = teamTemplateData.getRoleConfig();

		List<BaseRole> teamTrainTemplateRoleList = JsonUtil.toJsonObjectArray(roleString,
				BaseRole.class);
		teamTrainTemplateRoleList
				.forEach(role -> role.setRoleGroup(CrRole.TeamRoleGroup.STUDENT.getCode()));

		BaseRole baseRole = new BaseRole();
		baseRole.setName(CrRole.TeamRoleGroup.TEACHER.getDesc());
		baseRole.setRoleGroup(CrRole.TeamRoleGroup.TEACHER.getCode());

		teamTrainTemplateRoleList.add(0, baseRole);

		// 设置返回对象
		TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();
		teamTrainTemplateRsp.setNodesCount(
				topologyNodesStats.get(teamTemplateData.getTopoId()).getNodeCounts());
		teamTrainTemplateRsp.setResourceOccupiedNums(
				topologyNodesStats.get(teamTemplateData.getTopoId()).getResourceOccupiedNums());
		teamTrainTemplateRsp.setResourceId(teamTemplateData.getResourceId());

		teamTrainTemplateRsp.setBusinessTemplateId(teamTemplateData.getBusinessTemplateId());

		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setRoleStructure(teamTrainTemplateRoleList);

		// set attack engage relation
		teamTrainTemplateRsp.setIsAttackRelated(
				attackRelCount.containsKey(teamTemplateData.getBusinessTemplateId()));
		teamTrainTemplateRsp.setAttackCount(attackRelCount
				.getOrDefault(teamTemplateData.getBusinessTemplateId(), Lists.newArrayList()));
		teamTrainTemplateRsp
				.setIsIcsRelated(icsRelCount.containsKey(teamTemplateData.getBusinessTemplateId()));
		teamTrainTemplateRsp.setIcsCount(icsRelCount
				.getOrDefault(teamTemplateData.getBusinessTemplateId(), Lists.newArrayList()));
		teamTrainTemplateRsp.setIsEngageRelated(
				engageRelCount.containsKey(teamTemplateData.getBusinessTemplateId()));
		teamTrainTemplateRsp.setEngageCount(engageRelCount
				.getOrDefault(teamTemplateData.getBusinessTemplateId(), Lists.newArrayList()));

		return teamTrainTemplateRsp;
	}

	private TeamTemplate reqToTeamTemplate(TeamTrainTemplateReq req, TeamTemplate teamTemplate) {

		List<CrRoleConfig> roleConfigs = req.getRoleList();
		BaseInfo baseInfo = req.getBaseInfo();
		// 校验
		crCommonService.validateRoleConfig(roleConfigs);

		// 校验 tag
		teamTemplate.setPreSkills(CrCommonUtil.handleTrainingTag(baseInfo.getPreSkill()));
		teamTemplate.setTrainSkills(CrCommonUtil.handleTrainingTag(baseInfo.getTrainSkill()));

		teamTemplate.setSubType(baseInfo.getCategory());
		teamTemplate.setPeopleNumber(baseInfo.getPeopleNumber());
		teamTemplate.setDurationTime(baseInfo.getDurationTime());
		teamTemplate.setRoleConfig(CrCommonUtil.roleConfig2DbJson(roleConfigs));
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(req.getTimeLineConfig()));

		return teamTemplate;
	}

}
