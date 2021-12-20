package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.TaskContext;

public interface CrTaskService {

	CrTask getById(String taskId);

	/**
	 * 根据mission和user获取task
	 * @param missionId mission_id
	 * @param userId user_id
	 * @return Task 实例
	 */
	TaskContext getByMissionAndUser(String missionId, String userId);

	/** 提交任务答案
	 * @param roomId
	 * @param missionId
	 * @param answer
	 */
	void submitMissions(String roomId, String missionId, String answer);

	/**
	 * 根据roomId和user获取task
	 * @param roomId 
	 * @param userId 
	 * @return List<Task>  实例列表
	 */
	List<TaskContext> getByRoomIdAndUser(String roomId, String userId);

	/**
	 * 用户开始任务
	 * @param task
	 */
	void startTask(CrTask task);

	/**
	 * 根据用户id和MissionId查出唯一对应的task
	 * @param userId
	 * @param missionId
	 * @return
	 */
	CrTask getByUserIdAndMissionId(String userId, String missionId);

	/**
	 * 查询某房间某用户的task
	 * @param userId
	 * @param roomId
	 * @return
	 */
	List<CrTask> getByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 统计某用户在某房间的task总数
	 * @param userId
	 * @param roomId
	 * @return
	 */
	Integer countByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 查询某房间完成的task
	 * @param roomId
	 * @return
	 */
	List<CrTask> getFinished(String roomId);

	/**
	 * 统计某房间完成的task数量
	 * @param roomId
	 * @return
	 */
	Integer countFinished(String roomId);

	/**
	 * 统计某房间未提交的task数量
	 * @param roomId
	 * @return
	 */
	Integer countUnsubmitted(String roomId);

	/**
	 * 统计某房间未完成的task数量
	 * @param roomId
	 * @return
	 */
	Integer countUnfinished(String roomId);

	/**
	 * 统计某用户在某房间完成的task数量
	 * @param roomId
	 * @param userId
	 * @return
	 */
	Integer countFinishedByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 查询某用户在某房间完成的task
	 * @param roomId
	 * @param userId
	 * @return
	 */
	List<CrTask> getFinishedByRoomIdAndUserId(String roomId, String userId);

	List<CrTask> getFinishedByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 查询某房间的task
	 * @param roomId
	 * @return
	 */
	List<CrTask> getByRoomId(String roomId);

	/**
	 * 查询某角色在某房间的task
	 * @param roleId
	 * @return
	 */
	List<CrTask> getByRole(String roleId);

	/**
	 * 查询多个角色在某房间的task
	 * @param roleIds
	 * @return
	 */
	List<CrTask> getByRoles(List<String> roleIds);

	/**
	 * 查询某房间多个mission对应的task
	 * @param roomId
	 * @param missionIds
	 * @return
	 */
	List<CrTask> getByMissions(String roomId, List<String> missionIds);

	/**
	 * 查询某房间多个用户提交的多个mission对应的task
	 * @param roomId
	 * @param userIds
	 * @param missionIds
	 * @return
	 */
	List<CrTask> getReportedByUserIdsAndMissions(String roomId, List<String> userIds,
			List<String> missionIds);

	/**
	 * 统计该房间多个用户未提交的task数量
	 * @param roomId 
	 * @param userIds
	 * @return
	 */
	Integer countUnreportedByUsers(String roomId, List<String> userIds);

	/**
	 * 判断某房间某mission是否下发
	 * @param missionId
	 * @return
	 */
	Boolean isReleasedByMission(String missionId);

	/**
	 * 查询某房间多个用户拥有的task的Id列表
	 * @param roomId 
	 * @param userIds
	 * @return
	 */
	List<String> getIdsByUserIds(String roomId, List<String> userIds);

	/**
	 * 查询某房间某用户的task的id列表
	 * @param roomId 
	 * @param userId
	 * @return
	 */
	List<String> getIdsByOptionalUserId(String roomId, String userId);

	/**
	 * 学员提交答案
	 * @param missionId
	 * @param answer
	 * @param userId
	 * @param answerExtension
	 */
	void submitAnswer(String missionId, String answer, String userId, String answerExtension);

	/**
	 * 教员、裁判提交人工评估
	 * @param taskSubmitId
	 * @param score
	 * @param detail
	 * @param reason
	 * @param evaluateStatus
	 * @param currentUserId
	 */
	void submitEvaluation(String taskSubmitId, Integer score, String detail, Integer reason,
			Integer evaluateStatus, String currentUserId);

	IPage<MissionEvaluationContext>
			getMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery);

	/**
	 * 推送任务评估的websocket消息
	 */
	void pushMissionWS(String businessId, Integer code);

}
