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
 * 课程相关Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see ServiceImpl
 * @see CourseRelatedService
 * @since 1.0.0
 */
@Service
public class CourseRelatedServiceImpl extends ServiceImpl<CourseRelatedMapper, CourseRelated> implements CourseRelatedService {

	/**
	 * 课程相关Mapper
	 *
	 * @see CourseRelatedMapper
	 */
	@Autowired
	CourseRelatedMapper courseRelatedMapper;

	/**
	 * 课程名称Service
	 *
	 * @see CourseNameService
	 */
	@Autowired
	CourseNameService courseNameService;

	/**
	 * 保存课程相关列表
	 *
	 * @param relatedCourseList 相关课程列表
	 * @param resourceId        资源id
	 * @param type              类型
	 * @return {@code String }
	 * @throws
	 * @see List<RelatedCourse>
	 * @see String
	 * @see Integer
	 * @see String
	 */
	@Override
	public String saveCourseRelatedList(List<RelatedCourse> relatedCourseList, String resourceId,
			Integer type) {
		List<CourseRelated> courseRelatedList = Lists.newArrayList();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			CourseRelated courseRelated = new CourseRelated();

			courseRelated.setRelatedId(resourceId);
			courseRelated.setResourceId(relatedCourse.getResourceId());
			courseRelated.setWeight(relatedCourse.getWeight());
			if (relatedCourse.getWeight() == null) {
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

	/**
	 * 删除课程相关由相关id列表
	 *
	 * @param ids  id列表
	 * @param type 类型
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	@Override
	public void deleteCourseRelatedByRelatedIds(List<String> ids, Integer type) {
		courseRelatedMapper.deleteByRelatedId(ids, type);
	}
}
