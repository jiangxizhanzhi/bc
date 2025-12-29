package com.cyberpeace.cr.teamtrain.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.utils.JsonCommon;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.teamtrain.controller.req.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
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
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.enums.TeamRoleGroup;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
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
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

/**
 * 团队训练模板Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "团队训练模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/team_train/template")
public class TeamTrainTemplateController {

	/**
	 * 团队模板Service
	 */
	@Autowired
	TeamTemplateService teamTemplateService;

	/**
	 * 业务模板Service
	 */
	@Autowired
	BusinessTemplateService businessTemplateService;

	/**
	 * 当地场景Service
	 */
	@Autowired
	LocalSceneService localSceneService;

	/**
	 * 任务Service
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * 任务模板Service
	 */
	@Autowired
	private MissionTemplateService missionTemplateService;

	/**
	 * 靶场常用Service
	 */
	@Autowired
	private CrCommonService crCommonService;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 远程日志Service
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * RTKS相关Service
	 */
	@Autowired
	private RtksRelatedService rtksRelatedService;

	/**
	 * 远程场景Service
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private SingleTemplateService singleTemplateService;

	@ApiOperation("复制为单兵训练")
	@PostMapping("/copy_to_single_train")
	@RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN)
	public BaseRsp<Void> copyToSingleTrain(@Valid @RequestBody TeamTrainTemplateCopyReq req) {
		String resourceId = req.getResourceId();
		if (StringUtils.isNotEmpty(resourceId)) {
			SingleTemplate singleTemplate = singleTemplateService.getByBusinessTemplateId(resourceId + "_single");
			if (Objects.nonNull(singleTemplate)) {
				throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "关联的单兵训练已发布");
			}
		}

		//1. 团队模板
		TeamTemplate template = teamTemplateService.getByTemplateBusinessId(resourceId);

		//2. 通用业务模板
		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());


		BusinessTemplate copiedBusinessTemplate = new BusinessTemplate();
		BeanUtils.copyProperties(businessTemplate, copiedBusinessTemplate);
		copiedBusinessTemplate.setResourceId(businessTemplate.getResourceId() + "_single");
		copiedBusinessTemplate.setName(businessTemplate.getName());
		copiedBusinessTemplate.setId(null);
		copiedBusinessTemplate.setCreateTime(null);
		copiedBusinessTemplate.setUpdateTime(null);
		// 单兵训练
		copiedBusinessTemplate.setBusinessType(1);
		copiedBusinessTemplate.setXdTemplateCategory(0);
		copiedBusinessTemplate.setXdTemplateType(0);

		SingleTemplate singleTemplate = new SingleTemplate();
		BeanUtils.copyProperties(template, singleTemplate);
		singleTemplate.setResourceId(template.getResourceId() + "_single");
		singleTemplate.setCreateTime(null);
		singleTemplate.setUpdateTime(null);
		singleTemplate.setSubType(1);

		String roleString = template.getRoleConfig();
		//3.任务阶段信息
		List<TeamTrainTemplateRoleRsp> teamTrainTemplateRoleRsps;
		if (StringUtils.isEmpty(roleString)) {
			teamTrainTemplateRoleRsps = Lists.newArrayList();
		} else {
			teamTrainTemplateRoleRsps = JsonCommon.snakeParse(roleString,
					new TypeReference<List<TeamTrainTemplateRoleRsp>>() {
					});
		}
		ColorMission<AddTemplateMission> colorMission = new ColorMission<AddTemplateMission>();
		colorMission.setTemplate(1);
		colorMission.setColor(1);
		if (CollectionUtils.isNotEmpty(teamTrainTemplateRoleRsps)) {
			if (Objects.nonNull(teamTrainTemplateRoleRsps.get(0).getMissionsScheme())) {
				colorMission.setTemplate(teamTrainTemplateRoleRsps.get(0).getMissionsScheme().getTemplate());
				colorMission.setColor(teamTrainTemplateRoleRsps.get(0).getMissionsScheme().getColor());
			}
			List<MissionSchemeObject<AddTemplateMission>> schemeData = teamTrainTemplateRoleRsps.stream().map(TeamTrainTemplateRoleRsp::getMissionsScheme)
					.filter(Objects::nonNull).filter(res -> Objects.nonNull(res.getSchemeData()))
					.flatMap(res -> res.getSchemeData().stream()).collect(Collectors.toList());
			colorMission.setSchemeData(schemeData);
		}
		singleTemplate.setMissionsScheme(JsonCommon.snakeStringify(colorMission));



		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// save business template
				businessTemplateService.save(copiedBusinessTemplate);

				// save single template
				singleTemplate.setBusinessTemplateId(copiedBusinessTemplate.getResourceId());
				singleTemplateService.save(singleTemplate);
			}
		});

		return new BaseRsp<>();
    }


    @ApiOperation("复制")
    @PostMapping("/copy")
    @RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN)
    public BaseRsp<Void> copy(@Valid @RequestBody TeamTrainTemplateCopyReq teamTrainTemplateCopyReq) {

        String resourceId = teamTrainTemplateCopyReq.getResourceId();
        //1. 团队模板
        TeamTemplate template = teamTemplateService.getByTemplateBusinessId(resourceId);

        //2. 通用业务模板
        BusinessTemplate businessTemplate = businessTemplateService
                .getById(template.getBusinessTemplateId());

        // 5.topo信息
        String csceneTopoId = businessTemplate.getTopoId();
        String copiedCsceneTopoId = localSceneService.copyTopo(csceneTopoId, businessTemplate.getName()+"_复制");

        //6. 清空localScene相关信息
        BusinessTemplate copiedBusinessTemplate = new BusinessTemplate();
        BeanUtils.copyProperties(businessTemplate, copiedBusinessTemplate);
        copiedBusinessTemplate.setLocalSceneId(null);
        copiedBusinessTemplate.setResourceId(null);
        copiedBusinessTemplate.setName(businessTemplate.getName() + "_复制");
        copiedBusinessTemplate.setTopoId(copiedCsceneTopoId);
        copiedBusinessTemplate.setId(null);
        copiedBusinessTemplate.setCreateTime(null);
        copiedBusinessTemplate.setUpdateTime(null);

        TeamTemplate copiedTeamTemplate = new TeamTemplate();
        BeanUtils.copyProperties(template, copiedTeamTemplate);
        copiedTeamTemplate.setId(null);
        copiedTeamTemplate.setResourceId(null);
        copiedTeamTemplate.setCreateTime(null);
        copiedTeamTemplate.setUpdateTime(null);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // save business template
                businessTemplateService.save(copiedBusinessTemplate);

                // save team template
                copiedTeamTemplate.setBusinessTemplateId(copiedBusinessTemplate.getResourceId());
                String roleString = template.getRoleConfig();
                //3.任务阶段信息
                List<TeamTrainTemplateRoleRsp> teamTrainTemplateRoleRsps = JsonUtil
                        .toJsonObjectArray(roleString, TeamTrainTemplateRoleRsp.class);
                for (TeamTrainTemplateRoleRsp teamTrainTemplateRoleRsp : teamTrainTemplateRoleRsps) {
                    for (MissionSchemeObject<AddTemplateMission> schemeDatum : teamTrainTemplateRoleRsp.getMissionsScheme().getSchemeData()) {
                        for (AddTemplateMission mission : schemeDatum.getMissions()) {
                            MissionTemplate missionTemplate = missionTemplateService.getById(mission.getResourceId());
                            if (Objects.nonNull(missionTemplate)) {
                                MissionTemplate copiedMission = new MissionTemplate();
                                BeanUtils.copyProperties(missionTemplate, copiedMission);
                                copiedMission.setTemplateId(null);
                                copiedMission.setId(null);
                                copiedMission.setCreateTime(null);
                                copiedMission.setUpdateTime(null);
                                copiedMission.setResourceId(null);
                                copiedMission.setBusinessTemplateId(copiedBusinessTemplate.getResourceId());
                                missionTemplateService.save(copiedMission);
                                mission.setResourceId(copiedMission.getResourceId());
                            } else {
                                throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
                            }
                        }
                    }
                }
                copiedTeamTemplate.setRoleConfig(JsonUtil.toJsonString(teamTrainTemplateRoleRsps));
                teamTemplateService.save(copiedTeamTemplate);
            }
        });

        return new BaseRsp<>();
    }


	/**
	 * 创建
	 *
	 * @param addReq 新增请求体
	 * @return {@code BaseRsp<Void> }
	 */
	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN)
	public BaseRsp<Void> create(@RequestBody @Validated TeamTrainTemplateAddReq addReq) {
		// set type
		addReq.getBaseInfo().setType(BusinessType.TRAINING_TEAM.getCode());
        if (StringUtils.isEmpty(addReq.getBaseInfo().getLogo())) {
            addReq.getBaseInfo().setLogo(addReq.getBaseInfo().getSceneConfigData().getLogo());
        }
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

	/**
	 * 更新
	 *
	 * @param modifyReq 修改请求体
	 * @return {@code BaseRsp<Void> }
	 */
	@ApiOperation("修改")
	@PatchMapping()
	@RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN)
	public BaseRsp<Void> update(@RequestBody @Validated TeamTrainTemplateModifyReq modifyReq) {
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

	/**
	 * 获取团队模板
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<TeamTrainTemplateRsp> }
	 */
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
		// get mission template ids
		List<String> missionIdsAll = Lists.newArrayList();
		for (TeamTrainTemplateRoleRsp teamTrainTemplateRoleRsp : teamTrainTemplateRoleRsps) {
			teamTrainTemplateRoleRsp.setId(template.getResourceId() + "_" + index++);
			for (MissionSchemeObject<AddTemplateMission> schemeData : teamTrainTemplateRoleRsp
					.getMissionsScheme().getSchemeData()) {
				List<String> missionIds = StreamApiUtils.map(schemeData.getMissions(),
						AddTemplateMission::getResourceId);
				missionIdsAll.addAll(missionIds);
			}
		}
		// get mission templates
		List<MissionTemplate> missions = missionTemplateService.getBatchByIds(missionIdsAll);
		Map<String, MissionTemplate> missionMap = StreamApiUtils.toMap(missions,
				MissionTemplate::getResourceId, pc -> pc);
		// set desc and title
		for (TeamTrainTemplateRoleRsp teamTrainTemplateRoleRsp : teamTrainTemplateRoleRsps) {
			for (MissionSchemeObject<AddTemplateMission> schemeData : teamTrainTemplateRoleRsp
					.getMissionsScheme().getSchemeData()) {
				for (AddTemplateMission mission : schemeData.getMissions()) {
					MissionTemplate missionTemplate = missionMap.get(mission.getResourceId());
					if (null != missionTemplate) {
						mission.setDescription(missionTemplate.getDescription());
						mission.setTitle(missionTemplate.getTitle());
					}
				}
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
		teamTrainTemplateRsp.setXdTemplateType(businessTemplate.getXdTemplateType());
		teamTrainTemplateRsp.setXdTemplateCategory(businessTemplate.getXdTemplateCategory());
		for (TeamTrainTemplateRoleRsp teamTrainTemplateRoleRsp : teamTrainTemplateRsp.getRoleList()) {
			teamTrainTemplateRoleRsp.initAssetMap();
		}
		return new BaseRsp<>(teamTrainTemplateRsp);
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN)
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		teamTemplateService.batchDelete(deleteReq.getIds());
		return new BaseRsp<>();
	}

	/**
	 * 列表
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<IPage<TeamTemplateData>> }
	 */
	@ApiOperation("前台列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<IPage<TeamTemplateData>> list(TeamTemplateListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		// 默认时间排序
		listQuery.setSort("cr_business_template.name");

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

		return new BaseRsp<>(data);
	}

	/**
	 * 获取团队训练模板输出由基础信息数据
	 *
	 * @param teamTemplateData   团队模板数据
	 * @param topologyNodesStats 拓扑节点统计
	 * @param attackRelCount     攻击关联计数
	 * @param icsRelCount        集成电路关联计数
	 * @param engageRelCount     Engage关联计数
	 * @return {@code TeamTrainTemplateRsp }
	 */
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
				.forEach(role -> role.setRoleGroup(TeamRoleGroup.STUDENT.getCode()));

		BaseRole baseRole = new BaseRole();
		baseRole.setName(TeamRoleGroup.TEACHER.getDesc());
		baseRole.setRoleGroup(TeamRoleGroup.TEACHER.getCode());

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

	/**
	 * 请求体自团队模板
	 *
	 * @param req          请求体
	 * @param teamTemplate 团队模板
	 * @return {@code TeamTemplate }
	 */
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
