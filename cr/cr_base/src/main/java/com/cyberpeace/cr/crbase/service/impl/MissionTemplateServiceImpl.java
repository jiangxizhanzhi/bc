package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.MissionTemplateMapper;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务模板Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class MissionTemplateServiceImpl extends ServiceImpl<MissionTemplateMapper, MissionTemplate> implements MissionTemplateService {

	/**
	 * 任务模板Mapper
	 */
	@Autowired
	MissionTemplateMapper missionTemplateMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	TransactionTemplate transactionTemplate;

	/**
	 * RTKS相关Service
	 */
	@Autowired
	RtksRelatedService rtksRelatedService;

	/**
	 * 批量获取由任务方案JSON 格式
	 *
	 * @param schemeJson 方案 JSON
	 * @return {@code List<MissionTemplate> }
	 */
	@Override
	public List<MissionTemplate> batchGetByMissionSchemeJson(String schemeJson) {
		List<String> missionTemplateIds = getIdsByMissionsSchemeJson(schemeJson);
		if (missionTemplateIds.size() == 0) {
			return Lists.newArrayList();
		}
		return missionTemplateMapper.selectBatchIds(missionTemplateIds);
	}

	/**
	 * 获取映射由id列表
	 *
	 * @param templateIds 模板id列表
	 * @return {@code Map<String, MissionTemplate> }
	 */
	@Override
	public Map<String, MissionTemplate> getMappingByIds(List<String> templateIds) {
		if (templateIds.isEmpty()) {
			return Maps.newHashMap();
		}
		return missionTemplateMapper.selectBatchIds(templateIds).stream().collect(Collectors
				.toMap(MissionTemplate::getResourceId, missionTemplate -> missionTemplate));
	}

	/**
	 * 获取映射由方案JSON 格式
	 *
	 * @param schemeJson 方案 JSON
	 * @return {@code Map<String, MissionTemplate> }
	 */
	@Override
	public Map<String, MissionTemplate> getMappingBySchemeJson(String schemeJson) {
		List<String> templateIds = getIdsByMissionsSchemeJson(schemeJson);
		return getMappingByIds(templateIds);
	}

	/**
	 * 批量插入
	 *
	 * @param missionTemplateList 任务模板列表
	 */
	@Override
	public void batchInsert(List<MissionTemplate> missionTemplateList) {
		if (missionTemplateList.isEmpty()) {
			return;
		}
		missionTemplateMapper.batchInsert(missionTemplateList);
	}

	/**
	 * 批量删除
	 *
	 * @param missionTemplateIds 任务模板id列表
	 */
	@Override
	public void batchDelete(List<String> missionTemplateIds) {
		if (missionTemplateIds.isEmpty()) {
			return;
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				missionTemplateMapper.deleteBatchIds(missionTemplateIds);
				rtksRelatedService.deleteSkillMissionRelation(missionTemplateIds);
			}
		});

	}

	/**
	 * 批量更新业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @param missionTemplateIds 任务模板id列表
	 */
	@Override
	public void batchUpdateBusinessTemplateId(String businessTemplateId,
			List<String> missionTemplateIds) {
		if (missionTemplateIds == null || missionTemplateIds.isEmpty()) {
			return;
		}
		missionTemplateMapper.batchUpdateBusinessTemplateId(businessTemplateId, missionTemplateIds);
	}

	/**
	 * 获取id列表由任务方案JSON 格式
	 *
	 * @param schemeJson 方案 JSON
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getIdsByMissionsSchemeJson(String schemeJson) {
		String reg = String.format(PatternConstant.RESOURCE_ID,
				CrCommonConstants.GENERATED_UUID_LENGTH);
		return LocalStringUtil.getMatchStr(schemeJson, reg, 1);
	}

	/**
	 * 地图跟业务
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code Map<String, MissionTemplateRes> }
	 */
	@Override
	public Map<String, MissionTemplateRes> mapWithBusiness(List<String> missionTemplateIds) {
		Map<String, MissionTemplateRes> mapping = Maps.newHashMap();
		if (missionTemplateIds.isEmpty()) {
			return mapping;
		}
		List<MissionTemplateRes> missionTemplateResList = missionTemplateMapper
				.listWithBusiness(missionTemplateIds);
		missionTemplateResList.forEach((missionTemplateRes) -> {
			mapping.put(missionTemplateRes.getResourceId(), missionTemplateRes);
		});
		return mapping;
	}

	/**
	 * 获取id列表由业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getIdsByBusinessTemplateId(String businessTemplateId) {
		return missionTemplateMapper.selectByBusinessTemplateId(businessTemplateId);
	}

	/**
	 * 获取映射由方案JSON 格式列表
	 *
	 * @param schemeJsonList scheme JSON 列表
	 * @return {@code Map<String, MissionTemplate> }
	 */
	@Override
	public Map<String, MissionTemplate> getMappingBySchemeJsonList(List<String> schemeJsonList) {
		List<String> templateIds = new ArrayList<>();
		for (String schemeJson : schemeJsonList) {
			templateIds.addAll(getIdsByMissionsSchemeJson(schemeJson));
		}
		return getMappingByIds(templateIds);
	}

	/**
	 * 获取批量由id列表
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<MissionTemplate> }
	 */
	@Override
	public List<MissionTemplate> getBatchByIds(Collection<String> missionTemplateIds) {
		if (CollectionUtils.isEmpty(missionTemplateIds)) {
			return Lists.newArrayList();
		}
		return missionTemplateMapper.selectBatchIds(missionTemplateIds);
	}
}
