package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.IdAndBool;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.service.bo.ScreenRecordByNode;
import com.cyberpeace.oj.course.controller.req.ClassroomMonitorReq;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.context.ClassRoomPageQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.course.service.bo.classroom.ClassroomStatisticsBo;

public interface ClassroomService extends IService<Classroom> {

	/**
	 * 新建课堂表
	 * @param classroom
	 * @param authContext
	 * @return
	 */
	Classroom create(Classroom classroom, AuthContext authContext);

	/**
	 * 前台课程表数据查询
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 */
	List<TimeScheduleBo> selectTimeList(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 * 分页查询课时表信息
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 */
	List<ClassroomBo> selectList(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 * 获取用户签到信息列表
	 * @param page
	 * @param classroomId
	 * @return
	 */
	ListRsp<ClassroomMonitorBo> selectSignList(Page page, String classroomId);

	/**
	 * 获取用户签到信息列表
	 * @param classroomId
	 * @return
	 */
	PageReportBo selectClassroomReport(String classroomId, String userName, Page page);

	/**
	 * 课堂学习统计
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	ClassroomStatisticBo classroomStatistic(String classroomId, AuthContext authContext);

	/**
	 * 课堂练习统计
	 * @param classroomPracticeContext
	 * @return
	 */
	ClassroomPracticeBo practiceStatistic(ClassroomPracticeContext classroomPracticeContext,
			Page page);

	/**
	 * 课堂练习统计
	 * @param classroomId
	 * @param lessonId
	 * @param userId
	 * @return
	 */
	ClassroomPracticeDetailBo practiceDetail(String classroomId, String lessonId, String userId);

	/**
	 * 课堂数据删除 课堂数据 场景数据 分组数据
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	Integer deleteClassroom(String classroomId, AuthContext authContext);

	/**
	 * 课堂表更新
	 * @param classroom
	 * @param authContext
	 * @return
	 */
	Boolean updateClassroom(Classroom classroom, AuthContext authContext);

	/**
	 * 获取课堂聊天用户信息
	 * @param roomId
	 * @param userContext
	 * @return
	 */
	DashboardInfo getChatUsers(String roomId, UserInfo userContext);

	/**
	 * 训练计划导入
	 * @param realPath
	 * @return
	 */
	List<Classroom> importTrainPlan(String realPath, UserInfo userInfo, boolean cover);

	/**
	 * 训练计划导出
	 * @return 导出文件路径
	 */
	void exportTrainPlan(UserInfo userInfo, String filePath);

	/**
	 * 获取课堂聊天组信息
	 * @param roomId
	 * @param groupId
	 * @return
	 */
	List<DashboardUser> getChatGroupUsers(String roomId, String groupId);

	/**
	 * 获取中台组织删除消息时 同步删除课堂表中关联记录
	 * @param orgs 删除组织ids
	 */
	void removeClassrooms(List<String> orgs);

	IPage<ClassroomBo>  selectPage(ClassRoomPageQuery roomListQuery, AuthContext authContext);

	List<String> selectIdsByPlanIds(List<String> ids);

	String exportReport(String classroomId);

	List<String> selectTempTaskIdsByUser(UserInfo userContext);

	Boolean getRecordingButton(String roomId);

	void updateRecordingButton(IdAndBool req);

	ClassroomStatisticsBo getStatistics(String roomId);

    ListRsp<ClassroomReportBo> selectClassroomReport(ClassroomMonitorReq reportReq);
}
