package com.cyberpeace.cr.teamtrain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
import com.cyberpeace.integratation.model.SceneConfig;

/**
 *
 */
@Service
public class TeamTemplateServiceImpl extends ServiceImpl<TeamTemplateMapper, TeamTemplate> implements TeamTemplateService {

	@Autowired
	private TeamTemplateMapper teamTemplateMapper;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public IPage<TeamTemplateData> page(ListQuery listQuery) {
		Page page = listQuery.toPageParam();

		listQuery.setIds(businessTemplateService
				.selectAuthIdsByUserId(UserInfoContext.getUserContext().getUserId()));

		IPage<TeamTemplateData> data = teamTemplateMapper.listTeamTemplate(page, listQuery);

		return data;
	}

	@Override
	public TeamTemplate create(TeamTrainTemplateContext teamTrainTemplateContext) {
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfo crEventAddInput = teamTrainTemplateContext.getBaseInfo();

		BusinessTemplate businessTemplate = getBusinessTemplateFromBaseInfoInput(crEventAddInput);

		// 拓扑场景id保存获取
		SceneConfig sceneConfig = teamTrainTemplateContext.getSceneConfigData();
		String sceneConfigId = "";
		// "todo场景";

		// 保存基础模板
		businessTemplate.setTopoId(sceneConfigId);
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
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfo baseInfo = input.getBaseInfo();

		SceneConfig sceneConfig = input.getSceneConfigData();

		businessTemplate.setTopoId("");
		businessTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		businessTemplate.setResourceId(businessTemplate.getResourceId());
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());

		if (sceneConfig != null) {
			String sceneConfigId = "更新场景todo";
			businessTemplate.setTopoId(sceneConfigId);
		}

		// 更新团队训练模板
		teamTemplate.setResourceId(input.getResourceId());
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		teamTemplate.setSubType(baseInfo.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));
		teamTemplate.setDurationTime(baseInfo.getDurationTime());
		teamTemplate.setPeopleNumber(baseInfo.getPeopleNumber());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				businessTemplateService.updateById(businessTemplate);

				updateById(teamTemplate);

				// todo更新任务

			}
		});

		return teamTemplate;
	}

	@Override
	public TeamTemplate getTeamTemplate(String resourceId) {
		TeamTemplate template = getById(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		return template;
	}

}
