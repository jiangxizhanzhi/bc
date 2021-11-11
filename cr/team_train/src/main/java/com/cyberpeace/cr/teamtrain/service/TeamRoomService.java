package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cr.teamtrain.service.context.TimeLineData;

/**
 *
 */
public interface TeamRoomService extends IService<TeamRoom> {

	/** 通过id获取团队训练
	 * @param resourceId
	 * @return
	 */
	TeamRoomData getTeamRoomRspByRoomId(String resourceId);

	/** 创建实例
	 * @param teamTrainRoomContext
	 * @return
	 */
	TeamRoom create(TeamTrainRoomContext teamTrainRoomContext);

	/** 更新实例
	 * @param teamTrainRoomContext
	 * @param teamRoom
	 * @return
	 */
	void updateTeamRoom(TeamTrainRoomContext teamTrainRoomContext, TeamRoom teamRoom);

	/** 列表查询团队训练
	 * @param listQuery
	 * @return
	 */
	IPage<TeamRoomData> list(ListQuery listQuery);

	/** 修改团队训练状态
	 * @param resourceId
	 * @return
	 */
	Integer changeTrainingStatus(String resourceId);

	/** 获取团队训练状态
	 * @param resourceId
	 * @return
	 */
	Integer getTrainingStatus(String resourceId);

	/** 通过businessInstanceId获取学员id
	 * @param resourceId
	 * @return
	 */
	List<UserRole> getUserList(String resourceId);

	/**教员获取时间线
	 * @param resourceId
	 * @return
	 */
	TimeLineData getTimeLine(String resourceId);

	/** 获取学习指南
	 * @param resourceId
	 * @return
	 */
	String getTrainingManual(String resourceId);

	/** 检查是否有教员权限
	 * @param trainingTeam
	 * @param userId
	 */
	void checkTeacherPermission(String trainingTeam, String userId);

	/** 检查团队训练是否已启动
	 * @param trainingTeamId
	 */
	void checkRoomOpening(String trainingTeamId);

	/** 学员获取时间线
	 * @param resourceId
	 * @return
	 */
	TimeLineData getStudentsTimeLine(String resourceId);
}
