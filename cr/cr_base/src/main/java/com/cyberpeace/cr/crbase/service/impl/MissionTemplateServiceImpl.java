package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务模板服务impl
 *
 * @author SN
 * @date 2023/05/06
 */
@Service
@Slf4j
public class MissionTemplateServiceImpl extends ServiceImpl<MissionTemplateMapper, MissionTemplate> implements MissionTemplateService {

	@Autowired
	MissionTemplateMapper missionTemplateMapper;

	@Autowired
	TransactionTemplate transactionTemplate;

	/**
	 * 通过任务计划json批量获取
	 *
	 * @param schemeJson 计划json
	 * @return {@link List}<{@link MissionTemplate}>
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
	 * 通过id获取映射
	 *
	 * @param templateIds 模板id
	 * @return {@link Map}<{@link String}, {@link MissionTemplate}>
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
	 * 通过计划json获取映射
	 *
	 * @param schemeJson 计划json
	 * @return {@link Map}<{@link String}, {@link MissionTemplate}>
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
	 * @param missionTemplateIds 任务模板id
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
			}
		});

	}

	/**
	 * 批量更新业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @param missionTemplateIds 任务模板id
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
	 * 通过任务计划json获取id
	 *
	 * @param schemeJson 计划json
	 * @return {@link List}<{@link String}>
	 */
	@Override
	public List<String> getIdsByMissionsSchemeJson(String schemeJson) {
		String reg = String.format(PatternConstant.RESOURCE_ID,
				CrCommonConstants.GENERATED_UUID_LENGTH);
		return LocalStringUtil.getMatchStr(schemeJson, reg, 1);
	}

	/**
	 * 业务映射
	 *
	 * @param missionTemplateIds 任务模板id
	 * @return {@link Map}<{@link String}, {@link MissionTemplateRes}>
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

	@Override
	public List<String> getIdsByBusinessTemplateId(String businessTemplateId) {
		return missionTemplateMapper.selectByBusinessTemplateId(businessTemplateId);
	}

	/**
	 * 通过计划json列表获取映射
	 *
	 * @param schemeJsonList 计划列表json
	 * @return {@link Map}<{@link String}, {@link MissionTemplate}>
	 */
	@Override
	public Map<String, MissionTemplate> getMappingBySchemeJsonList(List<String> schemeJsonList) {
		List<String> templateIds = new ArrayList<>();
		for (String schemeJson : schemeJsonList) {
			templateIds.addAll(getIdsByMissionsSchemeJson(schemeJson));
		}
		return getMappingByIds(templateIds);
	}
}
