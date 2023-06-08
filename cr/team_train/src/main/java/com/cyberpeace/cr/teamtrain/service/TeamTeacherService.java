package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cs.service.context.IdNameType;

public interface TeamTeacherService {

	/**
	 * 获取评分列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<StudentScoreListRsp>}
	 */
	List<StudentScoreListRsp> getScoreList(String roomId);

	/**
	 * 获取角色列表，用以评估列表角色下拉单
	 *
	 * @param roomId 房间id
	 * @return {@code List<IdNameType>}
	 */
	List<IdNameType> getRoleList(String roomId);

	/**
	 * 获取复盘时间轴
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TimeLineData}
	 */
	TimeLineData getReplayTimeline(String roomId, String userId);

	/**
	 * 获取任务统计数据信息
	 *
	 * @param roomId 房间id
	 * @return {@code TaskStatisticsInfo}
	 */
	TaskStatisticsInfo getTaskStatisticsInfo(String roomId);

	/**
	 * 检查训练是否满足复盘条件
	 * 训练已结束
	 * 场景已关闭
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean}
	 */
	String getReplaySceneId(String roomId);
}
