package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.MissionSituation;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.MissionSituationMapper;
import com.cyberpeace.cr.crbase.service.MissionSituationService;
import com.cyberpeace.cr.crbase.service.context.resource.IndustrySituationConfig;
import com.cyberpeace.cr.crbase.service.context.resource.IndustrySituationTask;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MissionSituationServiceImpl extends ServiceImpl<MissionSituationMapper, MissionSituation> implements MissionSituationService {

	@Override
	public void batchCreate(IndustrySituationConfig situationConfig,
			List<MissionTemplate> missionTemplateList) {
		Map<String, String> nameIdMap = StreamApiUtils.toMap(missionTemplateList,
				MissionTemplate::getTitle, MissionTemplate::getResourceId);
		List<MissionSituation> situationList = Lists.newArrayList();
		for (IndustrySituationTask task : situationConfig.getTask()) {
			MissionSituation situation = new MissionSituation();
			situation.setName(task.getName());
			String id = nameIdMap.get(situation.getName());
			if (null == id) {
				continue;
			}
			situation.setMissionTemplateId(id);
			situation.setTsId(task.getTs_id());
			situation.setTsType(task.getTs_type());
			situation.setTsDesc(task.getTs_desc());
			situation.setTargetDevice(JsonUtil.arrayToString(task.getTarget_device()));
			situation.setSourceDevice(task.getSource_device());
			situation.setArea(task.getArea());
			situation.setExtraEffect(task.getExtra_effect());

			situationList.add(situation);
		}
		this.saveBatch(situationList);
	}

	@Override
	public List<MissionSituation> getBatchByIds(List<String> situationIds) {
		if (CollectionUtils.isEmpty(situationIds)) {
			return Lists.newArrayList();
		}
		return this.getBaseMapper().selectBatchIds(situationIds);
	}
}
