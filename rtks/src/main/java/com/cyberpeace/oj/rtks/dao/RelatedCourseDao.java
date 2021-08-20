package com.cyberpeace.oj.rtks.dao;

import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;

import java.util.List;

public interface RelatedCourseDao {

	/**通过课程id字符串，获取所有课程名称和类型
	 * @param courses
	 * @return List<RelatedCourse>
	 */
	List<RelatedCourse> setCourseResult(String courses);
}
