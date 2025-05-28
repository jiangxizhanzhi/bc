/**
 * 这段代码定义了一个名为 `CourseLearnStatisticsService` 的接口，该接口包含多个方法，用于处理与课程学习统计相关的功能。以下是对代码的简要分析： 
 * ### 接口名称 
 * - `CourseLearnStatisticsService`: 课程学习统计服务接口 
 * ### 方法 
 * 1. **`courseHistogram(AuthContext authContext)`** 
 * - **功能**: 返回课程的直方图数据。 
 * - **参数**: 
 * - `AuthContext authContext`: 认证上下文对象。 
 * - **返回值**: `HistogramRes` 对象。 
 * 2. **`directionHistogram(AuthContext authContext)`** 
 * - **功能**: 返回方向的直方图数据。 
 * - **参数**: 
 * - `AuthContext authContext`: 认证上下文对象。 
 * - **返回值**: `HistogramRes` 对象。 
 * 3. **`courseStatisticsList(CourseContext context, Page page, AuthContext authContext)`** 
 * - **功能**: 获取课程统计列表。 
 * - **参数**: 
 * - `CourseContext context`: 课程上下文对象。 
 * - `Page page`: 分页对象。 
 * - `AuthContext authContext`: 认证上下文对象。 
 * - **返回值**: `ListRsp<AdminCourseStatisticsRes>` 对象。 
 * 4. **`LessonStatisticsList(String lessonStatisticsListReq, Page page, LessonStatisticsListReq pageReq)`** 
 * - **功能**: 获取课程统计列表。 
 * - **参数**: 
 * - `String lessonStatisticsListReq`: 课程统计请求体（字符串形式）。 
 * - `Page page`: 分页对象。 
 * - `LessonStatisticsListReq pageReq`: 分页请求体对象。 
 * - **返回值**: `ListRsp<AdminLessonStatisticsRes>` 对象。 
 * 5. **`LessonStatisticsDetail(String organizationId, String lessonId, String usernameLike, Page page, String sort)`** 
 * - **功能**: 获取课程统计详情。 
 * - **参数**: 
 * - `String organizationId`: 组织ID。 
 * - `String lessonId`: 课程ID。 
 * - `String usernameLike`: 用户名模糊匹配。 
 * - `Page page`: 分页对象。 
 * - `String sort`: 排序条件。 
 * - **返回值**: `ListRsp<AdminLessonStatisticsDetailRes>` 对象。 
 * ### 主要类和对象 
 * - `AuthContext`: 认证上下文对象。 
 * - `CourseContext`: 课程上下文对象。 
 * - `HistogramRes`: 直方图响应对象。 
 * - `AdminCourseStatisticsRes`: 管理员课程统计响应对象。 
 * - `AdminLessonStatisticsRes`: 管理员课程统计响应对象。 
 * - `AdminLessonStatisticsDetailRes`: 管理员课程统计详情响应对象。 
 * - `Page`: 分页对象。 
 * - `LessonStatisticsListReq`: 课程统计列表请求对象。 
 * - `ListRsp<T>`: 包含列表响应的对象。 
 * ### 总结 
 * 该接口主要提供了与课程学习统计相关的功能，包括课程直方图、方向直方图、课程统计列表及详情等。通过这些方法，可以获取不同维度的学习统计数据，并进行分页展示。每个方法都有详细的参数说明和返回类型，便于理解和使用。 
**/
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
