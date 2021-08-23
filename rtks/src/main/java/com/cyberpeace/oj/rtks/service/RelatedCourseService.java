package com.cyberpeace.oj.rtks.service;

import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;

import java.util.List;

public interface RelatedCourseService {

	/**通过课程id字符串，获取所有课程名称和类型,如果课程存在删除情况，返回true
	 * @param courses
	 * @return List<RelatedCourse>
	 */

	boolean setCourseResult(String courses, List<RelatedCourse> relatedCourseList);
}
