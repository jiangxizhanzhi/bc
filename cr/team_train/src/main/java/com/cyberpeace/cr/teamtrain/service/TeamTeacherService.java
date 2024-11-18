package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cs.service.context.IdNameType;

/**
 * 团队教员Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface TeamTeacherService {

	/**
	 * 获取评分列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<StudentScoreListRsp>}
	 * @throws
	 * @see String
	 * @see List<StudentScoreListRsp>
	 */
	List<StudentScoreListRsp> getScoreList(String roomId);

	/**
	 * 获取角色列表，用以评估列表角色下拉单
	 *
	 * @param roomId 房间id
	 * @return {@code List<IdNameType>}
	 * @throws
	 * @see String
	 * @see List<IdNameType>
	 */
	List<IdNameType> getRoleList(String roomId);

	/**
	 * 获取复盘时间轴
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TimeLineData}
	 * @throws
	 * @see String
	 * @see String
	 * @see TimeLineData
	 */
	TimeLineData getReplayTimeline(String roomId, String userId);

	/**
	 * 获取任务统计数据信息
	 *
	 * @param roomId 房间id
	 * @return {@code TaskStatisticsInfo}
	 * @throws
	 * @see String
	 * @see TaskStatisticsInfo
	 */
	TaskStatisticsInfo getTaskStatisticsInfo(String roomId);

	/**
	 * 检查训练是否满足复盘条件
	 * 训练已结束
	 * 场景已关闭
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean}
	 * @throws
	 * @see String
	 * @see String
	 */
	String getReplaySceneId(String roomId);

	/**
	 * 学员提交任务后通知教员
	 *
	 * @param missionId 任务id
	 * @throws
	 * @see String
	 */
	void notifySubmission(String missionId);
}
