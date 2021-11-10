package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

public interface MissionService {
	
	void addMission(Mission mission);

	Mission getById(String missionId);

	List<Mission> batchGet(List<String> resourceIds);
}
