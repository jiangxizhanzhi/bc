package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;

/**
 *
 */
public interface CourseRelatedService extends IService<CourseRelated> {

	/**批量插入课程关联字段
	 * @param relatedCourseList
	 * @param resourceId
	 * @param type
	 *@return 处理后的课程字段
	*/
	String saveCourseRelatedList(List<RelatedCourse> relatedCourseList, String resourceId,
			Integer type);

	void deleteCourseRelatedByRelatedIds(List<String> ids, Integer type);
}
