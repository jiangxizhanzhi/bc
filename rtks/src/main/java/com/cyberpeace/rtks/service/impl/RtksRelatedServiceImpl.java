package com.cyberpeace.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.rtks.dao.mapper.SkillMissionRelationMapper;

@Service
public class RtksRelatedServiceImpl implements RtksRelatedService {

	@Autowired
	private SkillMissionRelationMapper skillMissionRelationMapper;

	@Override
	public void deleteSkillMissionRelation(List<String> missionTemplateIds) {
		if (!missionTemplateIds.isEmpty()) {
			skillMissionRelationMapper.batchDeleteByMissionTemplateIds(missionTemplateIds);
		}
	}
}
