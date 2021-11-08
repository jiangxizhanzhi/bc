package com.cyberpeace.cr.crbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.MissonService;

/**
 * @author yinqingrong
 */
@Service
public class MissonServiceImpl implements MissonService {

	@Autowired
	private MissionMapper missionMapper;

	@Override
	public void addMission(Mission mission) {
		missionMapper.insert(mission);
	}

	@Override
	public Mission getById(String missionId) {
		return missionMapper.selectById(missionId);
	}
}
