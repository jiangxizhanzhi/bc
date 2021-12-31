package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsListReq;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;

public interface CourseLearnStatisticsService{

	HistogramRes courseHistogram();

    HistogramRes directionHistogram();

    IPage<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page page, AuthContext authContext);

	IPage<AdminLessonStatisticsRes> LessonStatisticsList(LessonStatisticsListReq lessonStatisticsListReq, Page page);

	IPage<AdminLessonStatisticsDetailRes> LessonStatisticsDetail(String lessonId, Page page, PageReqBase pageReq);

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
