package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.CourseName;

/**
 * 课程名称Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface CourseNameService extends IService<CourseName> {

	/**
	 * 新增课程名称
	 *
	 * @param relatedCourseList 相关课程列表
	 * @throws
	 * @see List<RelatedCourse>
	 */
	void addCourseName(List<RelatedCourse> relatedCourseList);

	/**
	 * 查询由id列表
	 *
	 * @param notExistIds 不存在id列表
	 * @return {@code List<CourseName> }
	 * @throws
	 * @see List<String>
	 * @see List<CourseName>
	 */
	List<CourseName> selectByIds(List<String> notExistIds);

	/**
	 * 验证当前真实存在的 course
	 *
	 * @param courseIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> filterId(List<String> courseIds);
}
