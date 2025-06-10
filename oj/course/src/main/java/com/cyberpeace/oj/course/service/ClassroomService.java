package com.cyberpeace.oj.course.service;

import java.util.List;

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

/**
 * 课堂Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see IService
 * @since 1.0.0
 */
public interface ClassroomService extends IService<Classroom> {

	/**
	 * 新建课堂表
	 *
	 * @param classroom
	 * @param authContext
	 * @return
	 * @throws
	 * @see Classroom
	 * @see AuthContext
	 * @see Classroom
	 */
	Classroom create(Classroom classroom, AuthContext authContext);

	/**
	 * 前台课程表数据查询
	 *
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 * @throws
	 * @see ClassRoomListQuery
	 * @see AuthContext
	 * @see List<TimeScheduleBo>
	 */
	List<TimeScheduleBo> selectTimeList(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 * 前台课程表数据查询- 仅课程数据
	 *
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 * @throws
	 * @see ClassRoomListQuery
	 * @see AuthContext
	 * @see List<ClassRoomWebBo>
	 */
    List<ClassRoomWebBo> selectTimeListOnlyCourse(ClassRoomListQuery roomListQuery,
                                                  AuthContext authContext);

    /**
	 * 分页查询课时表信息
	 *
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 * @throws
	 * @see ClassRoomListQuery
	 * @see AuthContext
	 * @see List<ClassroomBo>
	 */
	List<ClassroomBo> selectList(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 * 获取用户签到信息列表
	 *
	 * @param page
	 * @param classroomId
	 * @return
	 * @throws
	 * @see Page<ClassroomMonitorBo>
	 * @see String
	 * @see ListRsp<ClassroomMonitorBo>
	 */
	ListRsp<ClassroomMonitorBo> selectSignList(Page<ClassroomMonitorBo> page, String classroomId);

	/**
	 * 获取用户签到信息列表
	 *
	 * @param classroomId
	 * @param userName    用户名称
	 * @param page        分页
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Page<Object>
	 * @see PageReportBo
	 */
	PageReportBo selectClassroomReport(String classroomId, String userName, Page<Object> page);

	/**
	 * 课堂学习统计
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see ClassroomStatisticBo
	 */
	ClassroomStatisticBo classroomStatistic(String classroomId, AuthContext authContext);

	/**
	 * 课堂练习统计
	 *
	 * @param classroomPracticeContext
	 * @param page                     分页
	 * @return
	 * @throws
	 * @see ClassroomPracticeContext
	 * @see Page<PracticePaperEvaluateBo>
	 * @see ClassroomPracticeBo
	 */
	ClassroomPracticeBo practiceStatistic(ClassroomPracticeContext classroomPracticeContext,
			Page<PracticePaperEvaluateBo> page);

	/**
	 * 课堂练习统计
	 *
	 * @param classroomId
	 * @param lessonId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see ClassroomPracticeDetailBo
	 */
	ClassroomPracticeDetailBo practiceDetail(String classroomId, String lessonId, String userId);

	/**
	 * 课堂数据删除 课堂数据 场景数据 分组数据
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Integer
	 */
	Integer deleteClassroom(String classroomId, AuthContext authContext);

	/**
	 * 课堂表更新
	 *
	 * @param classroom
	 * @param authContext
	 * @return
	 * @throws
	 * @see Classroom
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean updateClassroom(Classroom classroom, AuthContext authContext);

	/**
	 * 获取课堂聊天用户信息
	 *
	 * @param roomId
	 * @param userContext
	 * @return
	 * @throws
	 * @see String
	 * @see UserInfo
	 * @see DashboardInfo
	 */
	DashboardInfo getChatUsers(String roomId, UserInfo userContext);

	/**
	 * 训练计划导入
	 *
	 * @param realPath
	 * @param userInfo 用户信息
	 * @param cover    盖
	 * @return
	 * @throws
	 * @see String
	 * @see UserInfo
	 * @see boolean
	 * @see List<Classroom>
	 */
	List<Classroom> importTrainPlan(String realPath, UserInfo userInfo, boolean cover);

	/**
	 * 训练计划导出
	 *
	 * @param userInfo 用户信息
	 * @param filePath 文件路径
	 * @throws
	 * @see UserInfo
	 * @see String
	 */
	void exportTrainPlan(UserInfo userInfo, String filePath);

	/**
	 * 获取课堂聊天组信息
	 *
	 * @param roomId
	 * @param groupId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<DashboardUser>
	 */
	List<DashboardUser> getChatGroupUsers(String roomId, String groupId);

	/**
	 * 获取中台组织删除消息时 同步删除课堂表中关联记录
	 *
	 * @param orgs 删除组织ids
	 * @throws
	 * @see List<String>
	 */
	void removeClassrooms(List<String> orgs);

	/**
	 * 查询分页
	 *
	 * @param roomListQuery 房间列表查询
	 * @param authContext   auth 上下文
	 * @return {@code IPage<ClassroomBo> }
	 * @throws
	 * @see ClassRoomPageQuery
	 * @see AuthContext
	 * @see IPage<ClassroomBo>
	 */
	IPage<ClassroomBo>  selectPage(ClassRoomPageQuery roomListQuery, AuthContext authContext);

	/**
	 * 查询id列表由计划id列表
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> selectIdsByPlanIds(List<String> ids);

	/**
	 * 导出报告
	 *
	 * @param classroomId 课堂id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	String exportReport(String classroomId);

	/**
	 * 查询临时任务id列表由用户
	 *
	 * @param userContext 用户上下文
	 * @return {@code List<String> }
	 * @throws
	 * @see UserInfo
	 * @see List<String>
	 */
	List<String> selectTempTaskIdsByUser(UserInfo userContext);
}
