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
 * 课程名称Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class CourseNameServiceImpl extends ServiceImpl<CourseNameMapper, CourseName> implements CourseNameService {

	/**
	 * 课程名称Mapper
	 */
	@Autowired
	private CourseNameMapper courseNameMapper;

	/**
	 * 新增课程名称
	 *
	 * @param relatedCourseList 相关课程列表
	 */
	@Override
	public void addCourseName(List<RelatedCourse> relatedCourseList) {

		List<CourseName> courseNameList = relatedCourseList.stream().map(relatedCourse -> {
			CourseName courseName = BeanUtils.copy(relatedCourse, CourseName.class);
			courseName.setInterview(false);
			return courseName;
		}).collect(Collectors.toList());

		saveOrUpdateBatch(courseNameList);
	}

	/**
	 * 查询由id列表
	 *
	 * @param notExistIds 不存在id列表
	 * @return {@code List<CourseName> }
	 */
	@Override
	public List<CourseName> selectByIds(List<String> notExistIds) {
		List<CourseName> courseNames = Lists.newArrayList();

		if (notExistIds.size() > 0) {
			courseNames = this.listByIds(notExistIds);
		}

		return courseNames;
	}

	/**
	 * 滤波器id
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> filterId(List<String> courseIds) {
		if (CollectionUtils.isEmpty(courseIds)) {
			return Lists.newArrayList();
		}
		return courseNameMapper.filterId(courseIds);
	}
}
