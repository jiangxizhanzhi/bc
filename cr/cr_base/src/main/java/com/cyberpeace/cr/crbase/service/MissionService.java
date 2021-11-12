package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.MissionMapping;

public interface MissionService extends IService<Mission> {

	void addMission(Mission mission);

	Mission getById(String missionId);

	List<Mission> batchGet(List<String> resourceIds);

	List<Mission> batchGetByRoom(String roomId);

	void updateShowWriteup(String missionId, Boolean showWriteup);

	Boolean isObjectiveMission(Mission mission);

	void release(String missionId, String roleId, String currentUserId);

	/**
	 * @param roomId
	 * @return
	 */
	MissionMapping getMissionStatusData(String roomId);

}
