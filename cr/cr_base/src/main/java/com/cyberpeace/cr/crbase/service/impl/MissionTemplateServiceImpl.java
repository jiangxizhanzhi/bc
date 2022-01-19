package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.MissionTemplateMapper;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigBO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MissionTemplateServiceImpl extends ServiceImpl<MissionTemplateMapper, MissionTemplate> implements MissionTemplateService {

	@Autowired
	MissionTemplateMapper missionTemplateMapper;

	@Override
	public List<MissionTemplate> batchGetByMissionSchemeJson(String schemeJson) {
		List<String> missionTemplateIds = getIdsByMissionsSchemeJson(schemeJson);
		if (missionTemplateIds.size() == 0) {
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
		missionTemplateMapper.deleteBatchIds(missionTemplateIds);
	}

	@Override
	public List<String> getIdsByRoleConfig(List<CrRoleConfigBO> roles) {
		List<String> ids = Lists.newArrayList();
		roles.forEach(role -> role.getMissionsScheme().getSchemeData().forEach(
				stage -> stage.getMissions().forEach(mission -> ids.add(mission.getResourceId()))));

		return ids;
	}

	private List<String> getIdsByMissionsSchemeJson(String schemeJson) {
		String reg = String.format(PatternConstant.RESOURCE_ID,
				CrCommonConstants.GENERATED_UUID_LENGTH);
		return LocalStringUtil.getMatchStr(schemeJson, reg, 1);
	}
}
