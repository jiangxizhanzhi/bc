package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RbTimeLineData;

public interface RbRoomService extends IService<RbRoom> {

	/**
	 * 获取训练状态
	 * @param roomId 红蓝对抗实例id
	 * @return 训练状态
	 */
	Integer getTrainingStatus(String roomId);

	/**
	 * 根据ids批量获取
	 * @param roomIds
	 * @return
	 */
	List<RbRoom> getByIds(List<String> roomIds);

	/**
	 * 获取所有红蓝房间
	 * @return
	 */
	List<RbRoom> getAll();

	/** 列表查询红蓝对抗实例
	 * @param listQuery 分页查询体
	 * @return 红蓝对抗实例
	 */
	IPage<RbRoomData> list(ListQuery listQuery);

	/**
	 * 获取当前红蓝房间数量
	 * @return
	 */
	Integer countAll();

	/**
	 * 创建红蓝对抗实例
	 * @param rbRoomContext 红蓝对抗数据
	 * @return 红蓝对抗实例
	 */
	RbRoom create(RbRoomContext rbRoomContext);

	/**
	 * 获取红蓝对抗实例数据
	 * @param resourceId 红蓝对抗实例id
	 * @return 红蓝对抗实例数据
	 */
	RbRoomData getRbRoomDataByRoomId(String resourceId);

	/**
	 * 获取聊天面板信息
	 * @param crRole 角色
	 * @param resourceId 红蓝对抗实例id
	 * @param userId 用户id
	 * @return 聊天面板信息
	 */
	DashboardInfo getDashboardInfo(CrRole crRole, String resourceId, String userId);

	/**
	 * 更新红蓝对抗实例
	 * @param rbRoomContext 红蓝对抗数据
	 * @param rbRoom 红蓝对抗实例
	 */
	void updateRbRoom(RbRoomContext rbRoomContext, RbRoom rbRoom);

	/**
	 * 校验导演权限
	 * @param roomId 红蓝对抗实例id
	 * @param userId 用户id
	 */
	void checkDirectorPermission(String roomId, String userId);

	/**
	 * 校验裁判权限
	 * @param roomId 红蓝对抗实例id
	 * @param userId 用户id
	 */
	void checkRefereePermission(String roomId, String userId);

	/**
	 * 校验训练是否运行
	 * @param roomId 红蓝对抗实例id
	 */
	void checkRoomOpening(String roomId);

	/**
	 * 校验学员权限
	 * @param roomId 红蓝对抗实例id
	 * @param userId 用户id
	 */
	void checkStudentPermission(String roomId, String userId);

	/**
	 * 获取用户列表
	 * @param roomId 红蓝对抗实例id
	 * @return 相关用户列表
	 */
	List<UserRole> getUserList(String roomId);

	/**
	 * 获取时间线数据
	 * @param roomId 红蓝对抗实例id
	 * @return 时间线数据
	 */
	RbTimeLineData getMissionTimeline(String roomId);

	/**
	 * 校验用户权限
	 * @param roomId 红蓝对抗实例id
	 * @param userId 用户id
	 * @return 用户在红蓝对抗实例中的角色
	 */
	CrRole checkRoomPermission(String roomId, String userId);

	/**
	 * 校验是否有操作场景的权限
	 * @param roomId 红蓝对抗实例id
	 * @param userId 用户id
	 */
	void checkTeacherPermission(String roomId, String userId);

	/**
	 * 获取聊天面板用户列表
	 * @param roleId 角色id
	 * @param roomId 红蓝对抗实例id
	 * @return 聊天面板用户列表
	 */
	List<DashboardUser> getMembersInfo(String roleId, String roomId);

	/**
	 * 校验实例是否未关闭
	 * @param roomId 红蓝对抗实例id
	 */
	void checkRoomUnclosed(String roomId);

	/**
	 * 获取学生角色
	 *
	 * @param roomId 训练id
	 * @return {@code List<CrRole>}
	 */
	List<CrRole> getStudentsRoles(String roomId);

	/**
	 * 获取推荐训练时长
	 *
	 * @param roomId 房间id
	 * @return {@code Integer}
	 */
	Integer getDuration(String roomId);

	/**
	 * 获取或异常
	 *
	 * @param roomId 房间id
	 * @return {@code RbRoom}
	 */
	RbRoom getOrExceptionById(String roomId);

	/**
	 * 获取复盘时间轴
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TimeLineData}
	 */
	TimeLineData getReplayTimeline(String roomId, String userId);
}
