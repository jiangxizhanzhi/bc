package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamScore;
import com.cyberpeace.cr.teamtrain.service.context.MissionsStatistics;
import com.cyberpeace.cr.teamtrain.service.context.TaskCounter;

/**
 *
 */
public interface TeamScoreService extends IService<TeamScore> {

	/** 获取学员的成绩
	 * @param roomId
	 * @param userId
	 * @return
	 */
	TeamScore getByRoomIdAndUser(String roomId, String userId);

	/**获取团队训练实战任务统计信息
	 * @param roomId
	 * @param studentId
	 * @return (已完成，未下发，已下发)
	 */
	TaskCounter getTaskCounter(String roomId, String studentId);

	/** 获取任务统计
	 * @param roomId
	 * @return
	 */
	MissionsStatistics getMissionsStatistics(String roomId);
}
