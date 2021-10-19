package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Override
	public List<String> getAllChildrenIds(String directionId) {
		CourseCategory courseCategory = courseCategoryMapper.selectById(directionId);
		List<CourseCategory> directions = courseCategoryMapper.selectChildrenByCode(courseCategory.getCode());
		return directions.stream().map(CourseCategory::getResourceId).collect(Collectors.toList());
	}

	@Override
	public List<CourseCategory> selectAll() {
		List<CourseCategory> categories = courseCategoryMapper.selectFirstLevel();

		return null;
	}
}
