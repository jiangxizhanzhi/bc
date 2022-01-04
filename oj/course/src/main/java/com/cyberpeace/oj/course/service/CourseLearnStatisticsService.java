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

public interface CourseLearnStatisticsService{

	HistogramRes courseHistogram();

    HistogramRes directionHistogram();

	ListRsp<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page page, AuthContext authContext);

	ListRsp<AdminLessonStatisticsRes> LessonStatisticsList(String lessonStatisticsListReq, Page page, LessonStatisticsListReq pageReq);

	ListRsp<AdminLessonStatisticsDetailRes> LessonStatisticsDetail(String organizationId, String lessonId, Page page);

	/**
	 * 根据课程编号用户编号查询课程目录
	 * @param courseId 课程编号
	 * @param authContext
	 * @return
	 */
//	LessonConfigBo getLessonConfig(String courseId, AuthContext authContext);

//	/**
//	 * 更新课时配置
//	 * @param configBo
//	 * @return
//	 */
//	LessonConfigBo updateLessonConfig(LessonConfigBo configBo);
//
//	/**
//	 * 根据课时数据 组装课程目录数据
//	 * @param configContext
//	 * @return
//	 */
//	LessonConfig packageLessonConfig(AddLessonConfigContext configContext);
}
