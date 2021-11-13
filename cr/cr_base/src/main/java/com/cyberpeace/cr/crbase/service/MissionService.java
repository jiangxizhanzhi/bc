package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.MissionMapping;

public interface MissionService extends IService<Mission> {

	void addMission(Mission mission);

	void addMissionTemplate(MissionTemplate missionTemplate);

	void updateMissionTemplate(MissionTemplate missionTemplate);

	Mission getById(String missionId);

	List<Mission> batchGet(List<String> resourceIds);

	List<Mission> batchGetByRoom(String roomId);

	void updateShowWriteup(String missionId, Boolean showWriteup);

	/**
	 * 下发任务
	 * @param missionId
	 * @param roleId
	 * @param currentUserId
	 */
	void release(String missionId, String roleId, String currentUserId);

	/**
	 * mission category 列表
	 * @return
	 */
	List<MissionCategory> listCategories();

	/**
	 * @param roomId
	 * @return
	 */
	MissionMapping getMissionStatusData(String roomId);

	MissionCategory getCategoryById(String categoryId);

	Map<String, String> copyScheme(List<String> missionTemplateIds, String userId, String roomId);

	void openOrCloseWriteup(String missionId, Boolean showWriteup);
}
