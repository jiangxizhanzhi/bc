package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsListReq;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;

/**
 * 课程学习统计学Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface CourseLearnStatisticsService {

	/**
	 * 课程直方图
	 *
	 * @param authContext auth 上下文
	 * @return {@code HistogramRes }
	 * @throws
	 * @see AuthContext
	 * @see HistogramRes
	 */
	HistogramRes courseHistogram(AuthContext authContext);

	/**
	 * 方向直方图
	 *
	 * @param authContext auth 上下文
	 * @return {@code HistogramRes }
	 * @throws
	 * @see AuthContext
	 * @see HistogramRes
	 */
	HistogramRes directionHistogram(AuthContext authContext);

	/**
	 * 课程统计列表
	 *
	 * @param context     上下文
	 * @param page        分页
	 * @param authContext auth 上下文
	 * @return {@code ListRsp<AdminCourseStatisticsRes> }
	 * @throws
	 * @see CourseContext
	 * @see Page
	 * @see AuthContext
	 * @see ListRsp<AdminCourseStatisticsRes>
	 */
	ListRsp<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page page,
			AuthContext authContext);

	/**
	 * 课程统计列表
	 *
	 * @param lessonStatisticsListReq 课统计学列表请求体
	 * @param page                    分页
	 * @param pageReq                 分页请求体
	 * @return {@code ListRsp<AdminLessonStatisticsRes> }
	 * @throws
	 * @see String
	 * @see Page
	 * @see LessonStatisticsListReq
	 * @see ListRsp<AdminLessonStatisticsRes>
	 */
	ListRsp<AdminLessonStatisticsRes> LessonStatisticsList(String lessonStatisticsListReq,
			Page page, LessonStatisticsListReq pageReq);

	/**
	 * 课统计学详情
	 *
	 * @param organizationId 组织id
	 * @param lessonId       课id
	 * @param usernameLike   username like
	 * @param page           分页
	 * @param sort           排序
	 * @return {@code ListRsp<AdminLessonStatisticsDetailRes> }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Page
	 * @see String
	 * @see ListRsp<AdminLessonStatisticsDetailRes>
	 */
	ListRsp<AdminLessonStatisticsDetailRes> LessonStatisticsDetail(String organizationId,
			String lessonId, String usernameLike, Page page, String sort);

}
