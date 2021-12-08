package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.StudentsTimeLineData;
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
	TeamRoomData getTeamRoomDataByRoomId(String resourceId);

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
	 * @param roomId
	 * @return
	 */
	Integer changeTrainingStatus(String roomId);

	/** 获取团队训练状态
	 * @param roomId
	 * @return
	 */
	Integer getTrainingStatus(String roomId);

	/** 通过businessInstanceId获取学员id
	 * @param roomId
	 * @return
	 */
	List<UserRole> getUserList(String roomId);

	/**教员获取时间线
	 * @param roomId
	 * @return
	 */
	TimeLineData getTimeLine(String roomId);

	/** 检查是否有教员权限
	 * @param roomId
	 * @param userId
	 */
	void checkTeacherPermission(String roomId, String userId);

	/** 检查团队训练是否是已启动状态
	 * @param roomId
	 */
	void checkRoomOpening(String roomId);

	/** 学员获取时间线
	 * @param roomId
	 * @return
	 */
	StudentsTimeLineData getStudentsTimeLine(String roomId);

	TeamRoom getByLocalSceneId(String localSceneId);

	TeamRoom getById(String roomId);

	/** 检查是否有学员权限
	 * @param roomId
	 * @param userId
	 */
	void checkStudentPermission(String roomId, String userId);
}
