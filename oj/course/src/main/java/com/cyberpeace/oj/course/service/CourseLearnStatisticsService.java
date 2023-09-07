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

public interface CourseLearnStatisticsService {

	HistogramRes courseHistogram(AuthContext authContext);

	HistogramRes lessonHistogram(AuthContext authContext);

	HistogramRes directionHistogram(AuthContext authContext);

	ListRsp<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page page,
			AuthContext authContext);

	ListRsp<AdminLessonStatisticsRes> LessonStatisticsList(String lessonStatisticsListReq,
			Page page, LessonStatisticsListReq pageReq);

	ListRsp<AdminLessonStatisticsDetailRes> LessonStatisticsDetail(String organizationId,
			String lessonId, String usernameLike, Page page, String sort);

}
