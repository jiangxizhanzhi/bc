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
 *
 */
@Service
public class TeamTemplateServiceImpl extends ServiceImpl<TeamTemplateMapper, TeamTemplate> implements TeamTemplateService {

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private TeamTemplateMapper teamTemplateMapper;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private LocalSceneService localSceneService;

	@Override
	public IPage<TeamTemplateData> page(ListQuery listQuery) {
		Page page = listQuery.toPageParam();
		if (listQuery.getIds() == null || listQuery.getIds().isEmpty()) {
			listQuery.setIds(businessTemplateService
					.selectAuthIdsByUserId(UserInfoContext.getUserContext().getUserId()));
		}

        return teamTemplateMapper.listTeamTemplate(page, listQuery);
	}

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

	private BusinessTemplate getBusinessTemplateFromBaseInfoInput(BaseInfo baseInfo) {
		BusinessTemplate businessTemplate = new BusinessTemplate();
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());
		businessTemplate.setBusinessType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());

		return businessTemplate;
	}

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

	@Override
	public TeamTemplate getByTemplateBusinessId(String resourceId) {
		TeamTemplate template = teamTemplateMapper.getByBusinessId(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		return template;
	}

	@Override
	public void batchDelete(List<String> businessTemplateIds) {
		businessTemplateService.batchDelete(businessTemplateIds);
	}

	@Override
	public List<TeamTemplate> getTeamTemplateByBusinessIds(List<String> businessIds) {
		return teamTemplateMapper.getTeamTemplateByBusinessIds(businessIds);
	}

	@Override
	public Boolean removeByBusinessTemplateId(String businessTemplateId) {
		return teamTemplateMapper.deleteByBusinessTemplateId(businessTemplateId);
	}

	@Override
	public List<String> getMissionTemplateIdsByRoleConfig(String roleConfig) {
		List<CrRoleConfig> roles = JsonUtil.toJsonObjectArray(roleConfig, CrRoleConfig.class);
		return CrCommonUtil.getMissionTemplateIds(roles);
	}

}
