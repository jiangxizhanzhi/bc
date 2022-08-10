package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.CourseName;

/**
 *
 */
public interface CourseNameService extends IService<CourseName> {

	void addCourseName(List<RelatedCourse> relatedCourseList);

	List<CourseName> selectByIds(List<String> notExistIds);

	/**
	 * 验证当前真实存在的 course
	 * @param courseIds
	 * @return
	 */
	List<String> filterId(List<String> courseIds);
}
