package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.CourseRelated;

/**
 * 课程相关Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface CourseRelatedService extends IService<CourseRelated> {

	/**
	 * 批量插入课程关联字段
	 *
	 * @param relatedCourseList
	 * @param resourceId
	 * @param type              类型
	 * @return 处理后的课程字段
	 * @throws
	 * @see List<RelatedCourse>
	 * @see String
	 * @see Integer
	 * @see String
	 */
	String saveCourseRelatedList(List<RelatedCourse> relatedCourseList, String resourceId,
			Integer type);

	/**
	 * 删除课程相关由相关id列表
	 *
	 * @param ids  id列表
	 * @param type 类型
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	void deleteCourseRelatedByRelatedIds(List<String> ids, Integer type);
}
