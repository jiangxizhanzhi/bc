package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.MissionMapping;

/**
 * @author yinqingrong
 */
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {

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

	@Override
	public List<Mission> batchGet(List<String> resourceIds) {
		return missionMapper.batchSelect(resourceIds);
	}

	@Override
	public List<Mission> batchGetByRoom(String roomId) {
		return missionMapper.batchSelectByRoom(roomId);
	}

	@Override
	public void updateShowWriteup(String missionId, Boolean showWriteup) {
		Mission mission = missionMapper.selectById(missionId);
		mission.setShowWriteup(showWriteup);
		missionMapper.updateById(mission);
	}

	@Override
	public boolean isObjectiveMission(Mission mission) {


		return Mission.MissionType.getOBJECTIVE_TYPE_LIST().contains(mission.getType());
	}

	@Override
	public MissionMapping getMissionStatusData(String roomId) {
		// todo
		return null;
	}
}
