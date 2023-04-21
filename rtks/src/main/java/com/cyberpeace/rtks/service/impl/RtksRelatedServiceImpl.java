package com.cyberpeace.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.rtks.dao.mapper.AttackIcsMapper;
import com.cyberpeace.rtks.dao.mapper.AttackMapper;
import com.cyberpeace.rtks.dao.mapper.EngageMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMissionRelationMapper;

@Service
public class RtksRelatedServiceImpl implements RtksRelatedService {

	@Autowired
	private SkillMissionRelationMapper skillMissionRelationMapper;

	@Autowired
	private AttackMapper attackMapper;

	@Autowired
	private AttackIcsMapper attackIcsMapper;

	@Autowired
	private EngageMapper engageMapper;

	@Override
	public void deleteSkillMissionRelation(List<String> missionTemplateIds) {
		if (!missionTemplateIds.isEmpty()) {
			skillMissionRelationMapper.batchDeleteByMissionTemplateIds(missionTemplateIds);
		}
	}

	@Override
	public void clearAttackRel(List<String> businessTemplateIds) {
		if (businessTemplateIds == null || businessTemplateIds.isEmpty()) {
			return;
		}
		attackMapper.clearRel(businessTemplateIds);
		attackIcsMapper.clearRel(businessTemplateIds);
	}

	@Override
	public void clearEngageRel(List<String> businessTemplateIds) {
		if (businessTemplateIds == null || businessTemplateIds.isEmpty()) {
			return;
		}
		engageMapper.clearRel(businessTemplateIds);
	}
}
