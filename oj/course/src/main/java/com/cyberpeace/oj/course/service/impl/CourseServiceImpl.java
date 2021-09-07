package com.cyberpeace.oj.course.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.oj.course.dao.entity.CourseCollection;
import com.cyberpeace.oj.course.dao.mapper.CourseCollectionMapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseDirection;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.DirectionMapper;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private DirectionMapper directionMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private CourseCollectionMapper courseCollectionMapper;

	@Override
	public CourseInfoBo selectById(String courseId, String userId) {
		Course course = courseMapper.selectByCourse(courseId);
		if (null == course) {
			throw new InvalidParamException("课程不存在！");
		}
		CourseInfoBo courseInfoBo = courseToBo(course);
		// 设置是否收藏
		CourseCollection collection = courseCollectionMapper.selectByUserAndCourse(userId,
				courseId);
		courseInfoBo.setIsCollection(null != collection && collection.getIsCollection());
		return courseInfoBo;
	}

	/**
	 * 数据转换
	 * @param course
	 * @return
	 */
	private CourseInfoBo courseToBo(Course course) {

		// 转换类别
		CourseDirection courseDirection = directionMapper.selectByResource(course.getDirection());
		CourseInfoBo courseInfoBo = new CourseInfoBo();
		BeanUtils.copyProperties(course, courseInfoBo);
		courseInfoBo.setDirectionName(courseDirection.getName());

		// 计算课时数量
		List<CourseLesson> courseLessons = courseLessonMapper
				.listByCourses(Arrays.asList(course.getResourceId()));
		courseInfoBo.setLessonCount(courseLessons.size());

		// 计算课程时长
		int sum = courseLessons.stream().mapToInt(CourseLesson::getDuration).sum();
		courseInfoBo.setCourseDuration(sum);

		// 计算实验课和理论课数量
		List<CourseLesson> theoryCount = courseLessons.stream().filter(
				courseLesson -> courseLesson.getType().intValue() == CourseLesson.TYPE_THEORY)
				.collect(Collectors.toList());
		courseInfoBo.setTheoryCount(theoryCount.size());
		courseInfoBo
				.setExerciseCount(courseInfoBo.getLessonCount() - courseInfoBo.getTheoryCount());
		return courseInfoBo;
	}

	@Override
	public List<CourseInfoBo> selectByIds(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Lists.newArrayList();
		}
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
		if (CollectionUtils.isEmpty(courseIds)) {
			return Lists.newArrayList();
		}
		List<String> ids = courseMapper.notDeleteIdsByIds(courseIds);
		return ids;
	}

	@Override
	public String collectCourse(String userId, String course, Boolean isCollection) {
		CourseCollection collection = new CourseCollection();
		CourseCollection exist = courseCollectionMapper.selectByUserAndCourse(userId, course);

		// 新增或者更新
		if (null == exist) {
			collection.setUser(userId);
			collection.setCourse(course);
			collection.setIsCollection(isCollection);
			collection.setUpdateTime(new Date());
			courseCollectionMapper.insert(collection);
		} else {
			BeanUtils.copyProperties(exist, collection);
			collection.setIsCollection(isCollection);
			courseCollectionMapper.updateById(collection);
		}

		return course;
	}
}
