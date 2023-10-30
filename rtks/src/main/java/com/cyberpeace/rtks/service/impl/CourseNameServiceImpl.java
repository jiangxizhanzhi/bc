package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.CourseName;
import com.cyberpeace.rtks.dao.mapper.CourseNameMapper;
import com.cyberpeace.rtks.service.CourseNameService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class CourseNameServiceImpl extends ServiceImpl<CourseNameMapper, CourseName> implements CourseNameService {

	@Autowired
	private CourseNameMapper courseNameMapper;

	@Override
	public void addCourseName(List<RelatedCourse> relatedCourseList) {

		List<CourseName> courseNameList = relatedCourseList.stream().map(relatedCourse -> {
			CourseName courseName = BeanUtils.copy(relatedCourse, CourseName.class);
			courseName.setInterview(false);
			return courseName;
		}).collect(Collectors.toList());

		saveOrUpdateBatch(courseNameList);
	}

	@Override
	public List<CourseName> selectByIds(List<String> notExistIds) {
		List<CourseName> courseNames = Lists.newArrayList();

		if (!notExistIds.isEmpty()) {
			courseNames = this.listByIds(notExistIds);
		}

		return courseNames;
	}

	@Override
	public List<String> filterId(List<String> courseIds) {
		if (CollectionUtils.isEmpty(courseIds)) {
			return Lists.newArrayList();
		}
		return courseNameMapper.filterId(courseIds);
	}
}
