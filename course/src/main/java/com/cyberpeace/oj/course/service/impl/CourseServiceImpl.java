package com.cyberpeace.oj.course.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.common.exception.InvalidParamException;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.dao.entity.Lesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.Direction;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.DirectionMapper;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private DirectionMapper directionMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Override
	public CourseInfoBo selectById(String courseId) {
		Course course = courseMapper.selectByCourse(courseId);
		if (null == course) {
			throw new InvalidParamException("课程不存在！");
		}
		return courseToBo(course);
	}

	/**
	 * 数据转换
	 * @param course
	 * @return
	 */
	private CourseInfoBo courseToBo(Course course) {

		// 转换类别
		Direction direction = directionMapper.selectByResource(course.getDirection());
		CourseInfoBo courseInfoBo = new CourseInfoBo();
		BeanUtils.copyProperties(course, courseInfoBo);
		courseInfoBo.setDirectionName(direction.getName());

		// 计算课时数量
		List<Lesson> lessons = courseLessonMapper.listByCourses(Arrays.asList(course.getResourceId()));
		courseInfoBo.setLessonCount(lessons.size());

		// 计算课程时长
		int sum = lessons.stream().mapToInt(Lesson::getDuration).sum();
		courseInfoBo.setCourseDuration(sum);

		return courseInfoBo;
	}

	@Override
	public List<CourseInfoBo> selectByIds(List<String> ids) {
		List<Course> courses = courseMapper.selectBatchIds(ids);
		List<CourseInfoBo> infoBos = courses.stream().map(course -> courseToBo(course))
				.collect(Collectors.toList());
		return infoBos;
	}

	@Override
	public ListRsp<CourseInfoBo> selectPage(CourseListReq listReq) {
		Page page = listReq.toPageParam();
		IPage<Course> iPage = courseMapper.listByPage(page, listReq.getEBook(),
				listReq.getDirection(), listReq.getDifficulty(), listReq.getPattern());

		iPage.convert(course -> courseToBo(course));
		return PageUtil.getListResult(iPage);
	}

	@Override
	public List<String> idsByNotDeleteIds(List<String> courseIds) {
		List<String> ids = courseMapper.notDeleteIdsByIds(courseIds);
		return ids;
	}
}
