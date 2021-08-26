package com.cyberpeace.oj.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.rtks.dao.entity.CourseRelated;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.oj.rtks.dao.mapper.CourseRelatedMapper;
import com.cyberpeace.oj.rtks.service.CourseRelatedService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class CourseRelatedServiceImpl extends ServiceImpl<CourseRelatedMapper, CourseRelated> implements CourseRelatedService {

	@Autowired
	CourseRelatedMapper courseRelatedMapper;

	@Override
	public String saveCourseRelatedList(List<RelatedCourse> relatedCourseList, String resourceId,
			Integer type) {
		List<CourseRelated> courseRelatedList = Lists.newArrayList();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			CourseRelated courseRelated = new CourseRelated();
			courseRelated.setRelatedId(resourceId);
			courseRelated.setResourceId(relatedCourse.getResourceId());
			courseRelated.setWeight(relatedCourse.getWeight());
			courseRelated.setType(type);

			courseRelatedList.add(courseRelated);
		}
		if (CourseRelated.RELATED_TYPE_KNOWLEDGE.equals(type)) { // 知识点要修改单独的课程关联数据库
			List<String> ids = Lists.newArrayList();
			ids.add(resourceId);
			deleteCourseRelatedByRelatedIds(ids);

			this.saveBatch(courseRelatedList);
		}

		return JsonUtil.toJsonString(courseRelatedList);
	}

	@Override
	public void deleteCourseRelatedByRelatedIds(List<String> ids) {
		courseRelatedMapper.deleteByRelatedId(ids);
	}
}
