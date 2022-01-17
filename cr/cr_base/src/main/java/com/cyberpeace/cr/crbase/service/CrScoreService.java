package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistics;

/**
 *
 */
public interface CrScoreService extends IService<CrScore> {

	/** 获取学员的成绩
	 * @param roomId
	 * @param userId
	 * @return
	 */
	CrScore getByRoomIdAndUser(String roomId, String userId);

	/**获取训练实战任务统计信息
	 * @param roomId
	 * @param studentId
	 * @return (已完成，未下发，已下发)
	 */

	/** 获取任务统计
	 * @param roomId
	 * @param crRoleList
	 * @return
	 */
	MissionsStatistics getMissionsStatistics(String roomId, List<CrRole> crRoleList);

	List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

	Map<String, Integer> getMissionsCategory(String roomId, List<CrRole> crRoleList);
}
