package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;

/**
 * 团队模板Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see TeamTemplateService
 * @since 1.0.0
 */
@Service
public class TeamTemplateServiceImpl extends ServiceImpl<TeamTemplateMapper, TeamTemplate> implements TeamTemplateService {

	/**
	 * 业务模板Service
	 *
	 * @see BusinessTemplateService
	 */
	@Autowired
	BusinessTemplateService businessTemplateService;

	/**
	 * 业务模板Mapper
	 *
	 * @see BusinessTemplateMapper
	 */
	@Autowired
	BusinessTemplateMapper businessTemplateMapper;

	/**
	 * 团队模板Mapper
	 *
	 * @see TeamTemplateMapper
	 */
	@Autowired
	private TeamTemplateMapper teamTemplateMapper;

	/**
	 * 任务模板Service
	 *
	 * @see MissionTemplateService
	 */
	@Autowired
	private MissionTemplateService missionTemplateService;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 当地场景Service
	 *
	 * @see LocalSceneService
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 分页
	 *
	 * @param listQuery 列表查询
	 * @return {@code IPage<TeamTemplateData> }
	 * @throws
	 * @see ListQuery
	 * @see IPage<TeamTemplateData>
	 */
	@Override
	public IPage<TeamTemplateData> page(ListQuery listQuery) {
		Page page = listQuery.toPageParam();
		if (listQuery.getIds() == null || listQuery.getIds().isEmpty()) {
			listQuery.setIds(businessTemplateService
					.selectAuthIdsByUserId(UserInfoContext.getUserContext().getUserId()));
		}

        return teamTemplateMapper.listTeamTemplate(page, listQuery);
	}

	/**
	 * 创造
	 *
	 * @param teamTrainTemplateContext 团队训练模板上下文
	 * @return {@code TeamTemplate }
	 * @throws
	 * @see TeamTrainTemplateContext
	 * @see TeamTemplate
	 */
	@Override
	public TeamTemplate create(TeamTrainTemplateContext teamTrainTemplateContext) {
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfo crEventAddInput = teamTrainTemplateContext.getBaseInfo();

		BusinessTemplate businessTemplate = getBusinessTemplateFromBaseInfoInput(crEventAddInput);

		SceneConfigData topoData = teamTrainTemplateContext.getBaseInfo().getSceneConfigData();
		JSONObject topoJson = new JSONObject();
		topoJson.put("scene_config", topoData.getSceneConfig());
		String topoId = localSceneService.saveTopo(topoJson,
				LocalScene.BusinessCode.TEAM_TRAINING.getCode());

		// 保存基础模板
		businessTemplate.setTopoId(topoId);
		businessTemplate.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		businessTemplate.setOperationAuthIds("[]");
		businessTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		// 保存团队训练模板
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(crEventAddInput.getPreSkill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(crEventAddInput.getTrainSkill()));
		teamTemplate.setSubType(crEventAddInput.getCategory());
		teamTemplate.setPeopleNumber(crEventAddInput.getPeopleNumber());
		teamTemplate.setDurationTime(crEventAddInput.getDurationTime());

		teamTemplate.setTimeLineConfig(
				JsonUtil.toJsonString(teamTrainTemplateContext.getTimeLineConfig()));

		teamTemplate.setRoleConfig(JsonUtil.toJsonString(teamTrainTemplateContext.getRoleList()));

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.save(businessTemplate);
				teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());

				save(teamTemplate);

			}
		});

		return teamTemplate;
	}

	/**
	 * 获取业务模板从基础信息输入
	 *
	 * @param baseInfo 基地信息
	 * @return {@code BusinessTemplate }
	 * @throws
	 * @see BaseInfo
	 * @see BusinessTemplate
	 */
	private BusinessTemplate getBusinessTemplateFromBaseInfoInput(BaseInfo baseInfo) {
		BusinessTemplate businessTemplate = new BusinessTemplate();
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());
		businessTemplate.setBusinessType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());

		return businessTemplate;
	}

	/**
	 * 更新团队模板
	 *
	 * @param input            输入
	 * @param businessTemplate 业务模板
	 * @return {@code TeamTemplate }
	 * @throws
	 * @see TeamTrainTemplateContext
	 * @see BusinessTemplate
	 * @see TeamTemplate
	 */
	@Override
	public TeamTemplate updateTeamTemplate(TeamTrainTemplateContext input,
			BusinessTemplate businessTemplate) {
		TeamTemplate teamTemplate = teamTemplateMapper.getByBusinessId(input.getResourceId());

		BaseInfo baseInfo = input.getBaseInfo();

		SceneConfigData sceneConfig = input.getBaseInfo().getSceneConfigData();

		businessTemplate.setResourceId(businessTemplate.getResourceId());
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());

		if (sceneConfig != null) {
			SceneConfigData topoData = input.getBaseInfo().getSceneConfigData();
			JSONObject topoJson = new JSONObject();
			topoJson.put("scene_config", topoData.getSceneConfig());
			localSceneService.saveTopo(topoJson, businessTemplate.getTopoId());
		}

		// 更新团队训练模板
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		teamTemplate.setSubType(baseInfo.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));
		teamTemplate.setDurationTime(baseInfo.getDurationTime());
		teamTemplate.setPeopleNumber(baseInfo.getPeopleNumber());
		teamTemplate.setRoleConfig(input.getRoleList());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				businessTemplateService.updateById(businessTemplate);

				updateById(teamTemplate);

			}
		});

		return teamTemplate;
	}

	/**
	 * 获取由模板业务id
	 *
	 * @param resourceId 资源id
	 * @return {@code TeamTemplate }
	 * @throws
	 * @see String
	 * @see TeamTemplate
	 */
	@Override
	public TeamTemplate getByTemplateBusinessId(String resourceId) {
		TeamTemplate template = teamTemplateMapper.getByBusinessId(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		return template;
	}

	/**
	 * 批量删除
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @throws
	 * @see List<String>
	 */
	@Override
	public void batchDelete(List<String> businessTemplateIds) {
		businessTemplateService.batchDelete(businessTemplateIds);
	}

	/**
	 * 获取团队模板由业务id列表
	 *
	 * @param businessIds 业务id列表
	 * @return {@code List<TeamTemplate> }
	 * @throws
	 * @see List<String>
	 * @see List<TeamTemplate>
	 */
	@Override
	public List<TeamTemplate> getTeamTemplateByBusinessIds(List<String> businessIds) {
		return teamTemplateMapper.getTeamTemplateByBusinessIds(businessIds);
	}

	/**
	 * 删除由业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean removeByBusinessTemplateId(String businessTemplateId) {
		return teamTemplateMapper.deleteByBusinessTemplateId(businessTemplateId);
	}

	/**
	 * 获取任务模板id列表由角色配置
	 *
	 * @param roleConfig 角色配置
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	@Override
	public List<String> getMissionTemplateIdsByRoleConfig(String roleConfig) {
		List<CrRoleConfig> roles = JsonUtil.toJsonObjectArray(roleConfig, CrRoleConfig.class);
		return CrCommonUtil.getMissionTemplateIds(roles);
	}

}
