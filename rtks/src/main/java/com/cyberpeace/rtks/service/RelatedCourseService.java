package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.res.RelatedCourse;

public interface RelatedCourseService {

	/**通过课程id字符串，获取所有课程名称和类型,如果课程存在删除情况，返回true
	 *
	 * @param userInfo
	 * @param courses 课程列表字符串,relatedCourseList 要返回的列表
	 * @param processBos
	 * @param related_course_type
	 * @return 是否存在删除的课程
	 */

	boolean setCourseResult(UserInfo userInfo, String courses,
			List<RelatedCourse> relatedCourseList, List<CourseStudyProcessBo> processBos,
			String related_course_type);
}
