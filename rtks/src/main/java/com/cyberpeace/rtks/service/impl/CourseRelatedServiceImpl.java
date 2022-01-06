package com.cyberpeace.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.mapper.CourseRelatedMapper;
import com.cyberpeace.rtks.service.CourseNameService;
import com.cyberpeace.rtks.service.CourseRelatedService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class CourseRelatedServiceImpl extends ServiceImpl<CourseRelatedMapper, CourseRelated> implements CourseRelatedService {

	@Autowired
	CourseRelatedMapper courseRelatedMapper;

	@Autowired
	CourseNameService courseNameService;

	@Override
	public String saveCourseRelatedList(List<RelatedCourse> relatedCourseList, String resourceId,
			Integer type) {
		List<CourseRelated> courseRelatedList = Lists.newArrayList();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			CourseRelated courseRelated = new CourseRelated();

			courseRelated.setRelatedId(resourceId);
			courseRelated.setResourceId(relatedCourse.getResourceId());
			courseRelated.setWeight(relatedCourse.getWeight());
			if (relatedCourse.getWeight() == null || relatedCourse.getWeight() == 0) {
				courseRelated.setWeight(CourseInfoBo.DEFAULT_WEIGHT);
			}
			courseRelated.setType(type);

			courseRelatedList.add(courseRelated);
		}
		List<String> ids = Lists.newArrayList();
		ids.add(resourceId);

		deleteCourseRelatedByRelatedIds(ids, type);

		this.saveBatch(courseRelatedList);
		courseNameService.addCourseName(relatedCourseList);

		return JsonUtil.toJsonString(courseRelatedList);
	}

	@Override
	public void deleteCourseRelatedByRelatedIds(List<String> ids, Integer type) {
		courseRelatedMapper.deleteByRelatedId(ids, type);
	}
}
