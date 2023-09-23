package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
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
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigRb;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateContext;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateData;
import com.cyberpeace.cs.service.LocalSceneService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbTemplateServiceImpl extends ServiceImpl<RbTemplateMapper, RbTemplate> implements RbTemplateService {

	@Autowired
	RbTemplateMapper rbTemplateMapper;

	@Autowired
	LocalSceneService localSceneService;

	@Autowired
	MissionTemplateService missionTemplateService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public RbTemplate getRbTemplateByBusinessId(String resourceId) {

		RbTemplate template = rbTemplateMapper.selectByBusinessTemplateId(resourceId);
		if (template == null) {
			log.error("business template not found, businessId:{}", resourceId);
			throw new ResourceNotFoundException(resourceId);
		}

		return template;
	}

	@Override
	public RbTemplate update(RbTemplateContext rbTemplateContext,
			BusinessTemplate businessTemplate) {
		RbTemplate rbTemplate = rbTemplateMapper
				.selectByBusinessTemplateId(rbTemplateContext.getResourceId());

		BaseInfo baseInfo = rbTemplateContext.getBaseInfo();

		SceneConfigData sceneConfig = rbTemplateContext.getBaseInfo().getSceneConfigData();

		businessTemplate.setResourceId(businessTemplate.getResourceId());
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());

		if (sceneConfig != null) {
			SceneConfigData topoData = rbTemplateContext.getBaseInfo().getSceneConfigData();
			JSONObject topoJson = new JSONObject();
			topoJson.put("scene_config", topoData.getSceneConfig());
			localSceneService.saveTopo(topoJson, businessTemplate.getTopoId());
		}

		// 更新红蓝对抗模板
		rbTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		rbTemplate.setPreSkill(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		rbTemplate.setTrainSkill(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		rbTemplate.setSubType(baseInfo.getCategory());

		rbTemplate.setDurationTime(baseInfo.getDurationTime());
		rbTemplate.setRoleConfig(JsonUtil.toJsonString(rbTemplateContext.getRoleList()));
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				businessTemplateService.updateById(businessTemplate);

				updateById(rbTemplate);

			}
		});

		return rbTemplate;
	}

	@Override
	public List<RbTemplate> getRbTemplateByBusinessIds(List<String> businessIds) {
		return rbTemplateMapper.getRbTemplateByBusinessIds(businessIds);
	}

	@Override
	public void batchDelete(List<String> businessTemplateIds) {
		businessTemplateService.batchDelete(businessTemplateIds);
	}

	@Override
	public IPage<RbTemplateData> page(ListQuery listQuery) {
		Page<RbTemplateData> page = listQuery.toPageParam();
		if (listQuery.getIds() == null || listQuery.getIds().isEmpty()) {
			listQuery.setIds(businessTemplateService
					.selectAuthIdsByUserId(UserInfoContext.getUserContext().getUserId()));
		}


		return rbTemplateMapper.listRbTemplate(page, listQuery);
	}

	@Override
	public List<String> getMissionTemplateIdsByRoleConfig(String roleConfig) {
		CrRoleConfigRb crRoleConfigRb = JsonUtil.toJsonObject(roleConfig, CrRoleConfigRb.class);
		List<String> missionTemplateIds = Lists.newArrayList();
		missionTemplateIds
				.addAll(CrCommonUtil.getMissionTemplateIds((crRoleConfigRb.getRedRoles())));
		missionTemplateIds
				.addAll(CrCommonUtil.getMissionTemplateIds((crRoleConfigRb.getBlueRoles())));

		return missionTemplateIds;
	}
}
