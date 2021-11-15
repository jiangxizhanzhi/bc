package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.MissionCompletion;

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
	 */
	void release(String missionId, String roleId, String currentUserId);

	/**
	 * mission category 列表
	 */
	List<MissionCategory> listCategories();

	/**
	 * mission完成数量统计
	 */
	Map<String, MissionCompletion> getMissionStatusData(String roomId);

	MissionCategory getCategoryById(String categoryId);

	/**
	 * 复制模板任务为靶场实例任务
	 * @return map{旧模板任务id：复制任务id}
	 */
	Map<String, String> copyScheme(List<String> missionTemplateIds, String userId, String roomId);

	/**
	 * 开放、关闭题解
	 */
	void openOrCloseWriteup(String missionId, Boolean showWriteup);
}
