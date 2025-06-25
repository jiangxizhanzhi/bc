package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.controller.res.TrainPlanLessonExerciseStatusRsp;
import com.cyberpeace.oj.course.controller.res.TrainPlanLessonReportStatusRsp;
import com.cyberpeace.oj.course.controller.res.TrainPlanLessonRsp;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.service.bo.ClassRoomExperimentReportBo;
import com.cyberpeace.oj.course.service.bo.ClassroomPracticeWebBo;

/**
 * 课堂Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {

	/**
	 * 根据组织和课时查询课堂信息
	 *
	 * @param orgId
	 * @param lessonId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Classroom
	 */
	Classroom selectByOrgAndLesson(@Param("orgId") String orgId,
			@Param("lessonId") String lessonId);

	/**
	 * 查询课程表数据
	 *
	 * @param listQuery
	 * @return
	 * @throws
	 * @see ClassRoomListQuery
	 * @see List<Classroom>
	 */
	List<Classroom> selectListByListQuery(@Param("listQuery") ClassRoomListQuery listQuery);

	/**
	 * 查询课程表数据
	 *
	 * @param listQuery
	 * @return
	 * @throws
	 * @see ClassRoomListQuery
	 * @see List<Classroom>
	 */
	List<Classroom> selectAdminList(@Param("listQuery") ClassRoomListQuery listQuery);

	/**
	 * 课程表数据查询 查询时间范围内 自己创建的 或者组织下的
	 *
	 * @param courseIds
	 * @param roomListQuery
	 * @return
	 * @throws
	 * @see List<String>
	 * @see ClassRoomListQuery
	 * @see List<Classroom>
	 */
	List<Classroom> selectListByCourse(@Param("courseIds") List<String> courseIds,
			@Param("listQuery") ClassRoomListQuery roomListQuery);

	/**
	 * 课程表数据查询 用户课程表
	 *
	 * @param roomListQuery
	 * @return
	 * @throws
	 * @see ClassRoomListQuery
	 * @see List<Classroom>
	 */
	List<Classroom> selectListLesson(@Param("listQuery") ClassRoomListQuery roomListQuery);

	/**
	 * 课程表删除
	 *
	 * @param lessonIds 课时id列表
	 * @return {@code List<Classroom> }
	 * @throws
	 * @see List<String>
	 * @see List<Classroom>
	 */
	List<Classroom> selectByLesson(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 查询该组织的课堂表
	 *
	 * @param orgId
	 * @return
	 * @throws
	 * @see String
	 * @see List<Classroom>
	 */
	List<Classroom> selectByOrgId(@Param("orgId") String orgId);

	/**
	 * 根据计划查询房间
	 *
	 * @param planId
	 * @return
	 * @throws
	 * @see String
	 * @see List<Classroom>
	 */
	List<Classroom> selectByPlan(@Param("planId") String planId);

	/**
	 * 根据ids删除房间
	 *
	 * @param orgIds 组织id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteByOrgIds(@Param("orgIds") List<String> orgIds);

	/**
	 * 分页
	 *
	 * @param listQuery   列表查询
	 * @param userId      用户id
	 * @param trainPlanId 训练计划id
	 * @param childrenOrg 子级组织
	 * @param isAdmin     是否管理
	 * @param courseIds   课程id列表
	 * @return {@code IPage<Classroom> }
	 * @throws
	 * @see Page<Classroom>
	 * @see String
	 * @see String
	 * @see List<String>
	 * @see boolean
	 * @see List<String>
	 * @see IPage<Classroom>
	 */
	IPage<Classroom> page(Page<Classroom> listQuery, String userId, String trainPlanId, List<String> childrenOrg, boolean isAdmin, List<String> courseIds);

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
	 * 查询由用户计划
	 *
	 * @param userId 用户id
	 * @param planId 计划id
	 * @return {@code List<Classroom> }
	 * @throws
	 * @see String
	 * @see String
	 * @see List<Classroom>
	 */
	List<Classroom> selectByUserPlan(String userId, String planId);

	/**
	 * 查询临时任务id列表由用户
	 *
	 * @param userId      用户id
	 * @param isSuperUser 是否超用户
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see boolean
	 * @see List<String>
	 */
	List<String> selectTempTaskIdsByUser(String userId, boolean isSuperUser);


	List<TrainPlanLessonRsp> selectListByIds(List<String> roomIds);

	/**
	 * 课后练习 状态统计
	 * @param lessonIds
	 * @return
	 */
	List<TrainPlanLessonExerciseStatusRsp> selectLessonExerciseStatus(List<String> lessonIds, String userId);

	/**
	 * 实验课报告 状态统计
	 * @param lessonIds
	 * @return
	 */
	List<TrainPlanLessonReportStatusRsp> selectLessonReportStatus(List<String> lessonIds, String userId);


	IPage<ClassRoomExperimentReportBo> selectPageReports(Page<ClassRoomExperimentReportBo> pageReqBase, UserInfo userInfo, List<String> courseIds);

	IPage<ClassroomPracticeWebBo> classroomPracticeWebIPage(
			@Param("pageParam") Page<ClassroomPracticeWebBo> pageParam,
			@Param("userInfo") UserInfo userInfo, @Param("courseIds") List<String> courseIds);
}
