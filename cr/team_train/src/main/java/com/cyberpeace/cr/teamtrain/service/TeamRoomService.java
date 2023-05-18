package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.context.StudentsTimeLineData;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cr.teamtrain.service.context.TimeLineData;

/**
 *
 */
public interface TeamRoomService extends IService<TeamRoom> {

	/** 通过id获取团队训练
	 * @param resourceId 训练实例id
	 * @return 团队训练实例数据
	 */
	TeamRoomData getTeamRoomDataByRoomId(String resourceId);

	/** 创建实例
	 * @param teamTrainRoomContext 团队训练实例数据
	 * @return 团队训练实例
	 */
	TeamRoom create(TeamTrainRoomContext teamTrainRoomContext);

	/** 更新实例
	 * @param teamTrainRoomContext 团队训练实例数据
	 * @param teamRoom 目标团队实例
	 */
	void updateTeamRoom(TeamTrainRoomContext teamTrainRoomContext, TeamRoom teamRoom);

	/** 列表查询团队训练
	 * @param listQuery 分页查询体
	 * @return 团队训练实例分页数据
	 */
	IPage<TeamRoomData> list(ListQuery listQuery);

	/** 修改团队训练状态
	 * @param roomId 团队训练实例id
	 * @return 团队训练实例状态
	 */
	Integer changeTrainingStatus(String roomId);

	/** 获取团队训练状态
	 * @param roomId 团队训练实例id
	 * @return 团队训练实例状态
	 */
	Integer getTrainingStatus(String roomId);

	/** 通过businessInstanceId获取学员id
	 * @param roomId 团队训练实例id
	 * @return 学员列表
	 */
	List<UserRole> getUserList(String roomId);

	/**教员获取时间线
	 * @param roomId 团队训练实例id
	 * @return 教员时间线数据
	 */
	TimeLineData getTimeLine(String roomId);

	/** 检查是否有教员权限
	 * @param roomId 团队训练实例id
	 * @param userId 用户id
	 */
	void checkTeacherPermission(String roomId, String userId);

	/** 检查团队训练是否是已启动状态
	 * @param roomId 团队训练实例id
	 */
	void checkRoomOpening(String roomId);

	/** 学员获取时间线
	 * @param roomId 团队训练实例id
	 * @return 学员时间线数据
	 */
	StudentsTimeLineData getStudentsTimeLine(String roomId);

	TeamRoom getByLocalSceneId(String localSceneId);

	/**
	 * 根据id获取团队训练实例
	 * @param roomId 团队训练实例id
	 * @return 团队训练实例
	 */
	TeamRoom getById(String roomId);

	/** 检查是否有学员权限
	 * @param roomId 团队训练实例id
	 * @param userId 用户id
	 */
	void checkStudentPermission(String roomId, String userId);

	/**
	 * 获取学员角色
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrRole>}
	 */
	List<CrRole> getStudentsRoles(String roomId);

	/**
	 * 获取评估列表
	 *
	 * @param req 请求体
	 * @return {@code List<EvaluationListRsp>}
	 */
	List<EvaluationListRsp> getEvaluationList(EvaluationListReq req);
}
