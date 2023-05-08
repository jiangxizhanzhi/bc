package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.TaskContext;

/**
 * cr任务服务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface CrTaskService {

	/**
	 * 根据id获取可执行任务
	 * @param taskId 可执行任务id
	 * @return 可执行任务
	 */
	CrTask getById(String taskId);

	/**
	 * 根据mission和user获取task
	 * @param missionId 任务id
	 * @param userId 用户id
	 * @return Task 实例
	 */
	TaskContext getByMissionAndUser(String missionId, String userId);

	/** 提交任务答案
	 * @param roomId 训练实例id
	 * @param missionId 任务id
	 * @param answer 答案
	 */
	void submitMissions(String roomId, String missionId, String answer);

	/**
	 * 批量提交任务答案
	 * @param roomId 训练实例id
	 * @param missionIds 任务id列表
	 * @param answer 答案集合
	 * @return 任务总分
	 */
	Integer batchSubmitMissions(String roomId, List<String> missionIds, Map<String, String> answer);

	/**
	 * 根据roomId和user获取task
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return List<Task>  实例列表
	 */
	List<TaskContext> getByRoomIdAndUser(String roomId, String userId);

	/**
	 * 根据roomId获取task
	 * @param roomId 训练实例id
	 * @return List<Task>  实例列表
	 */
	List<TaskContext> getTaskContextByRoomId(String roomId);

	/**
	 * 根据roomId,roleIds获取task
	 * @param roomId 训练实例id
	 * @param userIds 用户id列表
	 * @return List<Task>  实例列表
	 */
	List<TaskContext> getTaskContextByRoomIdAndUsers(String roomId, List<String> userIds);

	/**
	 * 用户开始任务
	 * @param task 可执行任务
	 */
	void startTask(CrTask task);

	/**
	 * 根据用户id和MissionId查出唯一对应的task
	 * @param userId 用户id
	 * @param missionId 任务id
	 * @return 唯一对应的task
	 */
	CrTask getByUserIdAndMissionId(String userId, String missionId);

	/**
	 * 查询某房间某用户的task
	 * @param userId 用户id
	 * @param roomId 训练实例id
	 * @return Task列表
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
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return Task列表
	 */
	List<CrTask> getFinishedByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 查询多个用户在某房间完成的task
	 * @param roomId 训练实例id
	 * @param userIds 用户id列表
	 * @return Task列表
	 */
	List<CrTask> getFinishedByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 查询某房间的task
	 * @param roomId 训练实例id
	 * @return 可执行任务列表
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
	 * @param roleIds 角色id列表
	 * @return Task列表
	 */
	List<CrTask> getByRoles(List<String> roleIds);

	/**
	 * 查询某房间多个mission对应的task
	 * @param roomId 房间id
	 * @param missionIds 任务id列表
	 * @return Task列表
	 */
	List<CrTask> getByMissions(String roomId, List<String> missionIds);

	/**
	 * 查询某房间多个用户提交的多个mission对应的task
	 * @param roomId 房间id
	 * @param userIds 用户id列表
	 * @param missionIds 任务id列表
	 * @return
	 */
	List<CrTask> getReportedByUserIdsAndMissions(String roomId, List<String> userIds,
			List<String> missionIds);

	/**
	 * 统计该房间多个用户未提交的task数量
	 * @param roomId 房间id
	 * @param userIds 用户id列表
	 * @return 个数
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
	 * @param roomId 房间id
	 * @param userIds 用户id列表
	 * @return Task的id列表
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
	 * @param missionId 任务id
	 * @param answer 答案
	 * @param userId 用户id
	 * @param answerExtension 答案额外信息
	 */
	void submitAnswer(String missionId, String answer, String userId, String answerExtension);

	/**
	 * 教员、裁判提交人工评估
	 * @param taskSubmitId 任务评估id
	 * @param score 分数
	 * @param detail 细节
	 * @param reason 评分理由
	 * @param evaluateStatus 评估状态
	 * @param currentUserId 当前用户id
	 */
	void submitEvaluation(String taskSubmitId, Integer score, String detail, Integer reason,
			Integer evaluateStatus, String currentUserId);

	/**
	 * 分页查询任务评估
	 * @param missionEvaluationQuery 分页查询体
	 * @return 任务评估
	 */
	IPage<MissionEvaluationContext>
			getMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery);

	/**
	 * 推送任务评估的websocket消息
	 * @param businessId 业务id
	 * @param code 推送代号
	 */
	void pushMissionWS(String businessId, Integer code);

	/**
	 * 推送训练状态的ws，暂停状态不允许答题
	 * @param businessId 业务id
	 * @param runningStatus 运行状态
	 */
	void pushStatusWs(String businessId, Integer runningStatus);

	/**
	 * 分页查询红蓝任务评估
	 * @param missionEvaluationQuery 分页查询体
	 * @return 任务评估
	 */
	IPage<MissionEvaluationContext>
			getRbMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery);

	/**
	 * 获取任务提交列表
	 * @param resourceId 可执行任务id
	 * @return 提交列表
	 */
	List<CrTaskSubmit> getOrderedSubmitsByTask(String resourceId);

	/**
	 * 客观题对错判断
	 * @return bool
	 */
	Boolean isCorrectByObjective(String userAnswer, String missionAnswer, Integer missionType);

	/**
	 * 统计未完成任务
	 * @param roomId 训练实例id
	 * @param crRoleIds 角色id列表
	 * @return 未完成个数
	 */
	Integer countUnfinishedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * 统计未提交任务个数
	 * @param roomId 训练实例id
	 * @param crRoleIds 角色id列表
	 * @return 未提交个数
	 */
	Integer countUnsubmittedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * 统计完成的任务个数
	 * @param roomId 训练实例id
	 * @param crRoleIds 角色id列表
	 * @return 完成任务个数
	 */
	Integer countFinishedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * 统计任务个数
	 * @param roomId 训练实例id
	 * @param crRoleIds 角色id列表
	 * @return 任务个数
	 */
	Integer countByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);
}
