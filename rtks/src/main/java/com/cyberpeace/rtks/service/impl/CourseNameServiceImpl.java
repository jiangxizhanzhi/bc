package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.CourseName;
import com.cyberpeace.rtks.dao.mapper.CourseNameMapper;
import com.cyberpeace.rtks.service.CourseNameService;

/**
 *
 */
@Service
public class CourseNameServiceImpl extends ServiceImpl<CourseNameMapper, CourseName> implements CourseNameService {

	@Override
	public void addCourseName(List<RelatedCourse> relatedCourseList) {

		List<CourseName> courseNameList = relatedCourseList.stream().map(relatedCourse -> {
			CourseName courseName = BeanUtils.copy(relatedCourse, CourseName.class);

			return courseName;
		}).collect(Collectors.toList());

		saveOrUpdateBatch(courseNameList);
	}
}
