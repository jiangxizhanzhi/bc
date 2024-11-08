package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.context.ClassRoomPageQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.service.bo.*;

import java.util.List;

/**
 * 课堂Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface ClassroomService extends IService<Classroom> {

	/**
	 * 分页查询课时表信息
	 *
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 */
	List<ClassroomBo> selectList(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 * 获取用户签到信息列表
	 *
	 * @param page
	 * @param classroomId
	 * @return
	 */
	ListRsp<ClassroomMonitorBo> selectSignList(Page page, String classroomId);

	/**
	 * 课堂学习统计
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	ClassroomStatisticBo classroomStatistic(String classroomId, AuthContext authContext);

	/**
	 * 课堂练习统计
	 *
	 * @param classroomPracticeContext
	 * @param page                     分页
	 * @return
	 */
	ClassroomPracticeBo practiceStatistic(ClassroomPracticeContext classroomPracticeContext,
			Page page);

	/**
	 * 课堂练习统计
	 *
	 * @param classroomId
	 * @param lessonId
	 * @param userId
	 * @return
	 */
	ClassroomPracticeDetailBo practiceDetail(String classroomId, String lessonId, String userId);

	/**
	 * 课堂数据删除 课堂数据 场景数据 分组数据
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	Integer deleteClassroom(String classroomId, AuthContext authContext);

	/**
	 * 获取课堂聊天用户信息
	 *
	 * @param roomId
	 * @param userContext
	 * @return
	 */
	DashboardInfo getChatUsers(String roomId, UserInfo userContext);

	/**
	 * 训练计划导入
	 *
	 * @param realPath
	 * @param userInfo 用户信息
	 * @param cover    盖
	 * @return
	 */
	List<Classroom> importTrainPlan(String realPath, UserInfo userInfo, boolean cover);

	/**
	 * 训练计划导出
	 *
	 * @param userInfo 用户信息
	 * @param filePath 文件路径
	 */
	void exportTrainPlan(UserInfo userInfo, String filePath);

	/**
	 * 获取课堂聊天组信息
	 *
	 * @param roomId
	 * @param groupId
	 * @return
	 */
	List<DashboardUser> getChatGroupUsers(String roomId, String groupId);

	/**
	 * 获取中台组织删除消息时 同步删除课堂表中关联记录
	 *
	 * @param orgs 删除组织ids
	 */
	void removeClassrooms(List<String> orgs);

	/**
	 * 查询分页
	 *
	 * @param roomListQuery 房间列表查询
	 * @param authContext   auth 上下文
	 * @return {@code IPage<ClassroomBo> }
	 */
	IPage<ClassroomBo>  selectPage(ClassRoomPageQuery roomListQuery, AuthContext authContext);

	/**
	 * 查询id列表由计划id列表
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByPlanIds(List<String> ids);

	/**
	 * 查询临时任务id列表由用户
	 *
	 * @param userContext 用户上下文
	 * @return {@code List<String> }
	 */
	List<String> selectTempTaskIdsByUser(UserInfo userContext);
}
