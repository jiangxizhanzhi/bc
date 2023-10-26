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
 * @author SN
 */
@Service
@Slf4j
public class MissionTemplateServiceImpl extends ServiceImpl<MissionTemplateMapper, MissionTemplate> implements MissionTemplateService {

	@Autowired
	MissionTemplateMapper missionTemplateMapper;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	RtksRelatedService rtksRelatedService;

	@Override
	public List<MissionTemplate> batchGetByMissionSchemeJson(String schemeJson) {
		List<String> missionTemplateIds = getIdsByMissionsSchemeJson(schemeJson);
		if (missionTemplateIds.isEmpty()) {
			return Lists.newArrayList();
		}
		return missionTemplateMapper.selectBatchIds(missionTemplateIds);
	}

	@Override
	public Map<String, MissionTemplate> getMappingByIds(List<String> templateIds) {
		if (templateIds.isEmpty()) {
			return Maps.newHashMap();
		}
		return missionTemplateMapper.selectBatchIds(templateIds).stream().collect(Collectors
				.toMap(MissionTemplate::getResourceId, missionTemplate -> missionTemplate));
	}

	@Override
	public Map<String, MissionTemplate> getMappingBySchemeJson(String schemeJson) {
		List<String> templateIds = getIdsByMissionsSchemeJson(schemeJson);
		return getMappingByIds(templateIds);
	}

	@Override
	public void batchInsert(List<MissionTemplate> missionTemplateList) {
		if (missionTemplateList.isEmpty()) {
			return;
		}
		missionTemplateMapper.batchInsert(missionTemplateList);
	}

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

	@Override
	public void batchUpdateBusinessTemplateId(String businessTemplateId,
			List<String> missionTemplateIds) {
		if (missionTemplateIds == null || missionTemplateIds.isEmpty()) {
			return;
		}
		missionTemplateMapper.batchUpdateBusinessTemplateId(businessTemplateId, missionTemplateIds);
	}

	@Override
	public List<String> getIdsByMissionsSchemeJson(String schemeJson) {
		String reg = String.format(PatternConstant.RESOURCE_ID,
				CrCommonConstants.GENERATED_UUID_LENGTH);
		return LocalStringUtil.getMatchStr(schemeJson, reg, 1);
	}

	@Override
	public Map<String, MissionTemplateRes> mapWithBusiness(List<String> missionTemplateIds) {
		Map<String, MissionTemplateRes> mapping = Maps.newHashMap();
		if (missionTemplateIds.isEmpty()) {
			return mapping;
		}
		List<MissionTemplateRes> missionTemplateResList = missionTemplateMapper
				.listWithBusiness(missionTemplateIds);
		missionTemplateResList.forEach((missionTemplateRes) -> mapping
				.put(missionTemplateRes.getResourceId(), missionTemplateRes));
		return mapping;
	}

	@Override
	public List<String> getIdsByBusinessTemplateId(String businessTemplateId) {
		return missionTemplateMapper.selectByBusinessTemplateId(businessTemplateId);
	}

	@Override
	public Map<String, MissionTemplate> getMappingBySchemeJsonList(List<String> schemeJsonList) {
		List<String> templateIds = new ArrayList<>();
		for (String schemeJson : schemeJsonList) {
			templateIds.addAll(getIdsByMissionsSchemeJson(schemeJson));
		}
		return getMappingByIds(templateIds);
	}

	@Override
	public List<MissionTemplate> getBatchByIds(Collection<String> missionTemplateIds) {
		if (CollectionUtils.isEmpty(missionTemplateIds)) {
			return Lists.newArrayList();
		}
		return missionTemplateMapper.selectBatchIds(missionTemplateIds);
	}
}
