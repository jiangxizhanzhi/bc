package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TaskMatrix;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationMissionRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.crbase.service.context.task.CrTaskRejectRsp;

/**
 * 靶场任务Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface CrTaskService {

	/**
	 * 根据id获取可执行任务
	 *
	 * @param taskId 可执行任务id
	 * @return 可执行任务
	 * @throws
	 * @see String
	 * @see CrTask
	 */
	CrTask getById(String taskId);

	/**
	 * 根据mission和user获取task
	 *
	 * @param missionId 任务id
	 * @param userId    用户id
	 * @return Task 实例
	 * @throws
	 * @see String
	 * @see String
	 * @see TaskContext
	 */
	TaskContext getByMissionAndUser(String missionId, String userId);

	/**
	 * 提交任务答案
	 *
	 * @param roomId    训练实例id
	 * @param missionId 任务id
	 * @param answer    答案
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	void submitMissions(String roomId, String missionId, String answer);

	/**
	 * 批量提交任务答案
	 *
	 * @param roomId     训练实例id
	 * @param missionIds 任务id列表
	 * @param answer     答案集合
	 * @return 任务总分
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Map<String, String>
	 * @see Integer
	 */
	Integer batchSubmitMissions(String roomId, List<String> missionIds, Map<String, String> answer);

	/**
	 * 根据roomId和user获取task
	 *
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return List<Task>  实例列表
	 * @throws
	 * @see String
	 * @see String
	 * @see List<TaskContext>
	 */
	List<TaskContext> getByRoomIdAndUser(String roomId, String userId);

	/**
	 * 根据roomId获取task
	 *
	 * @param roomId 训练实例id
	 * @return List<Task>  实例列表
	 * @throws
	 * @see String
	 * @see List<TaskContext>
	 */
	List<TaskContext> getTaskContextByRoomId(String roomId);

	/**
	 * 根据roomId,roleIds获取task
	 *
	 * @param roomId  训练实例id
	 * @param userIds 用户id列表
	 * @return List<Task>  实例列表
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<TaskContext>
	 */
	List<TaskContext> getTaskContextByRoomIdAndUsers(String roomId, List<String> userIds);

	/**
	 * 用户开始任务
	 *
	 * @param task 可执行任务
	 * @throws
	 * @see CrTask
	 */
	void startTask(CrTask task);

	/**
	 * 根据用户id和MissionId查出唯一对应的task
	 *
	 * @param userId    用户id
	 * @param missionId 任务id
	 * @return 唯一对应的task
	 * @throws
	 * @see String
	 * @see String
	 * @see CrTask
	 */
	CrTask getByUserIdAndMissionId(String userId, String missionId);

	/**
	 * 查询某房间某用户的task
	 *
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return Task列表
	 * @throws
	 * @see String
	 * @see String
	 * @see List<CrTask>
	 */
	List<CrTask> getByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 统计某用户在某房间的task总数
	 *
	 * @param roomId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	Integer countByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 查询某房间完成的task
	 *
	 * @param roomId
	 * @return
	 * @throws
	 * @see String
	 * @see List<CrTask>
	 */
	List<CrTask> getFinished(String roomId);

	/**
	 * 统计某房间完成的task数量
	 *
	 * @param roomId
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer countFinished(String roomId);

	/**
	 * 统计某房间未提交的task数量
	 *
	 * @param roomId
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer countUnsubmitted(String roomId);

	/**
	 * 统计某房间未完成的task数量
	 *
	 * @param roomId
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer countUnfinished(String roomId);

	/**
	 * 统计某用户在某房间完成的task数量
	 *
	 * @param roomId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	Integer countFinishedByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 查询某用户在某房间完成的task
	 *
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return Task列表
	 * @throws
	 * @see String
	 * @see String
	 * @see List<CrTask>
	 */
	List<CrTask> getFinishedByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 查询多个用户在某房间完成的task
	 *
	 * @param roomId  训练实例id
	 * @param userIds 用户id列表
	 * @return Task列表
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<CrTask>
	 */
	List<CrTask> getFinishedByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 查询某房间的task
	 *
	 * @param roomId 训练实例id
	 * @return 可执行任务列表
	 * @throws
	 * @see String
	 * @see List<CrTask>
	 */
	List<CrTask> getByRoomId(String roomId);

	/**
	 * 查询某角色在某房间的task
	 *
	 * @param roleId
	 * @return
	 * @throws
	 * @see String
	 * @see List<CrTask>
	 */
	List<CrTask> getByRole(String roleId);

	/**
	 * 查询多个角色在某房间的task
	 *
	 * @param roleIds 角色id列表
	 * @return Task列表
	 * @throws
	 * @see List<String>
	 * @see List<CrTask>
	 */
	List<CrTask> getByRoles(List<String> roleIds);

	/**
	 * 查询某房间多个mission对应的task
	 *
	 * @param roomId     房间id
	 * @param missionIds 任务id列表
	 * @return Task列表
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<CrTask>
	 */
	List<CrTask> getByMissions(String roomId, List<String> missionIds);

	/**
	 * 查询某房间多个用户提交的多个mission对应的task
	 *
	 * @param roomId     房间id
	 * @param userIds    用户id列表
	 * @param missionIds 任务id列表
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<String>
	 * @see List<CrTask>
	 */
	List<CrTask> getReportedByUserIdsAndMissions(String roomId, List<String> userIds,
			List<String> missionIds);

	/**
	 * 统计该房间多个用户未提交的task数量
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return 个数
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Integer
	 */
	Integer countUnreportedByUsers(String roomId, List<String> userIds);

	/**
	 * 判断某房间某mission是否下发
	 *
	 * @param missionId
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean isReleasedByMission(String missionId);

	/**
	 * 查询某房间多个用户拥有的task的Id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return Task的id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getIdsByUserIds(String roomId, List<String> userIds);

	/**
	 * 查询某房间某用户的task的id列表
	 *
	 * @param roomId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<String>
	 */
	List<String> getIdsByOptionalUserId(String roomId, String userId);

	/**
	 * 学员提交答案
	 *
	 * @param missionId       任务id
	 * @param answer          答案
	 * @param userId          用户id
	 * @param answerExtension 答案额外信息
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
	void submitAnswer(String missionId, String answer, String userId, String answerExtension);

	/**
	 * 教员、裁判提交人工评估
	 *
	 * @param taskSubmitId   任务评估id
	 * @param score          分数
	 * @param detail         细节
	 * @param reason         评分理由
	 * @param evaluateStatus 评估状态
	 * @param currentUserId  当前用户id
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Integer
	 * @see Integer
	 * @see String
	 */
	void submitEvaluation(String taskSubmitId, Integer score, String detail, Integer reason,
			Integer evaluateStatus, String currentUserId);

	/**
	 * 分页查询任务评估
	 *
	 * @param missionEvaluationQuery 分页查询体
	 * @return 任务评估
	 * @throws
	 * @see MissionEvaluationQuery
	 * @see IPage<MissionEvaluationContext>
	 */
	IPage<MissionEvaluationContext>
			getMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery);

	/**
	 * 推送任务评估的websocket消息
	 *
	 * @param businessId 业务id
	 * @param code       推送代号
	 * @throws
	 * @see String
	 * @see Integer
	 */
	void pushMissionWS(String businessId, Integer code);

	/**
	 * 向指定用户列表推送任务ws
	 *
	 * @param businessId 业务id
	 * @param userIds    用户id列表
	 * @param code       代号
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Integer
	 */
	void pushMissionWsToUsers(String businessId, List<String> userIds, Integer code);

	/**
	 * 推送训练状态的ws，暂停状态不允许答题
	 *
	 * @param businessId    业务id
	 * @param runningStatus 运行状态
	 * @throws
	 * @see String
	 * @see Integer
	 */
	void pushStatusWs(String businessId, Integer runningStatus);

	/**
	 * 分页查询红蓝任务评估
	 *
	 * @param missionEvaluationQuery 分页查询体
	 * @return 任务评估
	 * @throws
	 * @see MissionEvaluationQuery
	 * @see IPage<MissionEvaluationContext>
	 */
	IPage<MissionEvaluationContext>
			getRbMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery);

	/**
	 * 获取任务提交列表
	 *
	 * @param resourceId 可执行任务id
	 * @return 提交列表
	 * @throws
	 * @see String
	 * @see List<CrTaskSubmit>
	 */
	List<CrTaskSubmit> getOrderedSubmitsByTask(String resourceId);

	/**
	 * 客观题对错判断
	 *
	 * @param userAnswer    用户答案
	 * @param missionAnswer 任务答案
	 * @param missionType   任务类型
	 * @return bool
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see Boolean
	 */
	Boolean judgeObjective(String userAnswer, String missionAnswer, Integer missionType);

	/**
	 * 统计未完成任务
	 *
	 * @param roomId    训练实例id
	 * @param crRoleIds 角色id列表
	 * @return 未完成个数
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Integer
	 */
	Integer countUnfinishedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * 统计未提交任务个数
	 *
	 * @param roomId    训练实例id
	 * @param crRoleIds 角色id列表
	 * @return 未提交个数
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Integer
	 */
	Integer countUnsubmittedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * 统计完成的任务个数
	 *
	 * @param roomId    训练实例id
	 * @param crRoleIds 角色id列表
	 * @return 完成任务个数
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Integer
	 */
	Integer countFinishedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * 统计任务个数
	 *
	 * @param roomId    训练实例id
	 * @param crRoleIds 角色id列表
	 * @return 任务个数
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Integer
	 */
	Integer countByRoomIdAndRoleIds(String roomId, List<String> crRoleIds);

	/**
	 * 获取评估列表
	 *
	 * @param req 请求体
	 * @return {@code List<EvaluationListRsp>}
	 * @throws
	 * @see EvaluationListReq
	 * @see List<EvaluationListRsp>
	 */
	List<EvaluationListRsp> getEvaluationList(EvaluationListReq req);

	/**
	 * 通过id获取评估
	 *
	 * @param taskSubmitId 任务提交id
	 * @return {@code EvaluationMissionBo}
	 * @throws
	 * @see String
	 * @see EvaluationMissionRsp
	 */
	EvaluationMissionRsp getEvaluationById(String taskSubmitId);

	/**
	 * 获取任务矩阵排除时间成本
	 *
	 * @param resource_id 资源id
	 * @param userId      用户id
	 * @return {@code TaskMatrix }
	 * @throws
	 * @see String
	 * @see String
	 * @see TaskMatrix
	 */
	TaskMatrix getTaskMatrixExcludeTimeCost(String resource_id,String userId);

	/**
	 * 获取任务统计数据信息通过房间id
	 *
	 * @param roomId 房间id
	 * @return {@code TaskStatisticsInfo}
	 * @throws
	 * @see String
	 * @see TaskStatisticsInfo
	 */
	TaskStatisticsInfo getTaskStatisticsInfoByRoomId(String roomId);

	/**
	 * 获取任务回复由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code List<CrTaskRejectRsp> }
	 * @throws
	 * @see String
	 * @see List<CrTaskRejectRsp>
	 */
	List<CrTaskRejectRsp> getTaskRejctsByTaskId(String taskId);

	/**
	 * 提交答案
	 *
	 * @param missionId       任务id
	 * @param answer          答案
	 * @param userId          用户id
	 * @param answerExtension 答案外延
	 * @param remark          备注
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
	void submitAnswer(String missionId, String answer, String userId, String answerExtension,String remark);

	/**
	 * 提交任务
	 *
	 * @param roomId    房间id
	 * @param missionId 任务id
	 * @param answer    答案
	 * @param remark    备注
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 */
	void submitMissions(String roomId, String missionId, String answer,String remark);
}
