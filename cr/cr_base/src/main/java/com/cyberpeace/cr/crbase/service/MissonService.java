package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

public interface MissonService {

	/**
	 * 新增任务
	 * @param mission
	 */
	public void addMission(Mission mission);

	public Mission getById(String missionId);
}
