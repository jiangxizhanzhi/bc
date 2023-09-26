package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.controller.res.SituationTimeRes;
import com.cyberpeace.situation.dao.entity.SituationTime;
import com.cyberpeace.situation.dao.mapper.SituationTimeMapper;
import com.cyberpeace.situation.service.SituationTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SituationTimeServiceImpl implements SituationTimeService {

	@Autowired
	private SituationTimeMapper situationTimeMapper;

	@Override
	public SituationTimeRes getTimeManagement(String topoId) {
		valid(topoId);
		SituationTime situationTime =situationTimeMapper.selectByTopoId(topoId);
		handleSituationTime(situationTime);
		SituationTimeRes situationTimeRes = BeanUtils.copy(situationTime, SituationTimeRes.class);
		situationTimeRes.setCombatTime(TimeUtil.format1(situationTime.getCombatTime()));
		situationTimeRes.setAstronomicalTime(TimeUtil.format1(situationTime.getAstronomicalTime()));
		return situationTimeRes;
	}

	@Override
	public void valid(String topoId) {
		SituationTime situationTime = situationTimeMapper.selectByTopoId(topoId);
		if (situationTime == null) {
			situationTime = new SituationTime();
			situationTime.setTopoId(topoId);
			situationTimeMapper.insert(situationTime);
		}
	}

	@Override
	public String getRunTime(String topoId) {
		valid(topoId);
		SituationTime situationTime = situationTimeMapper.selectByTopoId(topoId);
		Integer runTime = situationTime.getRunTime();
//		HashMap<String, Integer> map = new HashMap<>();
//		map.put("minute", runTime / 60 % 60);
//		map.put("hour", runTime / (60 * 60) % 24);
		String runTimeString = "已运行 " + (runTime / (60 * 60 * 24)) + "天" + (runTime / (60 * 60) % 24) + "小时" + (runTime / 60 % 60) + "分";
		return runTimeString;
	}

	@Override
	public Boolean addRunTime(String topoId, Integer time) {
		SituationTime situationTime = situationTimeMapper.selectByTopoId(topoId);
		situationTime.setRunTime(situationTime.getRunTime() + time);
		situationTimeMapper.updateById(situationTime);
		return Boolean.TRUE;
	}

	@Override
	public void handleSituationTime(SituationTime situationTime) {
		Date astronomicalTime = new Date(situationTime.getAstronomicalTime().getTime() + System.currentTimeMillis() - situationTime.getUpdateTime().getTime());
		situationTime.setAstronomicalTime(astronomicalTime);
		if (situationTime.getStatus().equals(SituationTime.Status.UNOPENED.getCode())) {
			Date combatTime = new Date(situationTime.getCombatTime().getTime() + System.currentTimeMillis() - situationTime.getUpdateTime().getTime());
			situationTime.setCombatTime(combatTime);
		} else if (situationTime.getStatus().equals(SituationTime.Status.RUNNING.getCode())) {
			Date combatTime = new Date(situationTime.getCombatTime().getTime() + (System.currentTimeMillis() - situationTime.getUpdateTime().getTime()) * situationTime.getRate());
			situationTime.setCombatTime(combatTime);
		}
	}
}
