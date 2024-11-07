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

/**
 * RB模板Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class RbTemplateServiceImpl extends ServiceImpl<RbTemplateMapper, RbTemplate> implements RbTemplateService {

	/**
	 * RB模板Mapper
	 */
	@Autowired
	RbTemplateMapper rbTemplateMapper;

	/**
	 * 当地场景Service
	 */
	@Autowired
	LocalSceneService localSceneService;

	/**
	 * 任务模板Service
	 */
	@Autowired
	MissionTemplateService missionTemplateService;

	/**
	 * 业务模板Service
	 */
	@Autowired
	private BusinessTemplateService businessTemplateService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 获取RB模板由业务id
	 *
	 * @param resourceId 资源id
	 * @return {@code RbTemplate }
	 */
	@Override
	public RbTemplate getRbTemplateByBusinessId(String resourceId) {

		RbTemplate template = rbTemplateMapper.selectByBusinessTemplateId(resourceId);
		if (template == null) {
			log.error("business template not found, businessId:{}", resourceId);
			throw new ResourceNotFoundException(resourceId);
		}

		return template;
	}

	/**
	 * 更新
	 *
	 * @param rbTemplateContext rb 模板上下文
	 * @param businessTemplate  业务模板
	 * @return {@code RbTemplate }
	 */
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

	/**
	 * 获取RB模板由业务id列表
	 *
	 * @param businessIds 业务id列表
	 * @return {@code List<RbTemplate> }
	 */
	@Override
	public List<RbTemplate> getRbTemplateByBusinessIds(List<String> businessIds) {
		return rbTemplateMapper.getRbTemplateByBusinessIds(businessIds);
	}

	/**
	 * 批量删除
	 *
	 * @param businessTemplateIds 业务模板id列表
	 */
	@Override
	public void batchDelete(List<String> businessTemplateIds) {
		businessTemplateService.batchDelete(businessTemplateIds);
	}

	/**
	 * 分页
	 *
	 * @param listQuery 列表查询
	 * @return {@code IPage<RbTemplateData> }
	 */
	@Override
	public IPage<RbTemplateData> page(ListQuery listQuery) {
		Page page = listQuery.toPageParam();
		if (listQuery.getIds() == null || listQuery.getIds().isEmpty()) {
			listQuery.setIds(businessTemplateService
					.selectAuthIdsByUserId(UserInfoContext.getUserContext().getUserId()));
		}


		return rbTemplateMapper.listRbTemplate(page, listQuery);
	}

	/**
	 * 获取任务模板id列表由角色配置
	 *
	 * @param roleConfig 角色配置
	 * @return {@code List<String> }
	 */
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
