package com.cyberpeace.oj.course.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ModelConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCollection;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseCollectionMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseAdminBo;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseCategoryMapper directionMapper;

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
	 *
	 * @param course
	 * @return
	 */
	private CourseInfoBo courseToBo(Course course) {

		// 转换类别
		CourseCategory courseDirection = directionMapper.selectById(course.getDirection());

		CourseInfoBo courseInfoBo = BeanUtils.copy(course, CourseInfoBo.class);
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
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		List<String> authCourseIds = courseMapper.selectByAuth(authContext);

		List<Course> courses = courseMapper.selectBatchIds(ids);
		List<CourseInfoBo> infoBos = courses.stream().map(course -> courseToBo(course))
				.map(infBo -> {
					if (infBo.getLessonCount().intValue() > 0) {
						infBo.setInterview(
								infBo.getIsPublic() && CollectionUtils.isNotEmpty(authCourseIds)
										&& authCourseIds.contains(infBo.getResourceId()));
					} else {
						infBo.setInterview(Boolean.FALSE);
					}
					return infBo;
				}).collect(Collectors.toList());
		return infoBos;
	}

	@Override
	public ListRsp<CourseInfoBo> selectPage(Page page, CourseContext courseContext,
			AuthContext authContext) {
		List<String> courseIds = null;
		if (null != authContext) {
			courseIds = courseMapper.selectByAuth(authContext);
		}
		IPage<Course> iPage = courseMapper.listByPage(page, courseContext, courseIds);

		iPage.convert(course -> transferToBo(course, authContext));
		return PageUtil.getListResult(iPage);
	}

	/**
	 * 数据格式转换
	 *
	 * @param course
	 * @param authContext
	 * @return
	 */
	private CourseWebBo transferToBo(Course course, AuthContext authContext) {

		if (null != authContext && !authContext.getIsWeb()) {
			CourseInfoBo courseInfoBo = courseToBo(course);
			CourseAdminBo courseAdminBo = BeanUtils.copy(courseInfoBo, CourseAdminBo.class);
			courseAdminBo.setAuth(course.getAuth());
			courseAdminBo.setShare(course.getShare());
			if (course.getAuth().intValue() == Course.CUSTOM_SHARE_MODE) {
				List<String> orgs = JsonUtil.toJsonObjectArray(course.getAuthOrganization(),
						String.class);
				courseAdminBo.setAuthCount(orgs.size());
			}
			if (course.getShare().intValue() == Course.CUSTOM_SHARE_MODE) {
				List<String> shares = JsonUtil.toJsonObjectArray(course.getShareTeachers(),
						String.class);
				courseAdminBo.setAuthCount(shares.size());
			}
			return courseAdminBo;

		} else {
			CourseInfoBo courseInfoBo = courseToBo(course);
			CourseWebBo courseWebBo = BeanUtils.copy(courseInfoBo, CourseWebBo.class);
			return courseWebBo;
		}

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

		CourseCollection exist = courseCollectionMapper.selectByUserAndCourse(userId, course);

		// 新增或者更新
		if (null == exist) {
			CourseCollection collection = new CourseCollection();
			collection.setUser(userId);
			collection.setCourse(course);
			collection.setIsCollection(isCollection);
			collection.setUpdateTime(new Date());
			courseCollectionMapper.insert(collection);
		} else {
			CourseCollection collection = BeanUtils.copy(exist, CourseCollection.class);
			collection.setIsCollection(isCollection);
			courseCollectionMapper.updateById(collection);
		}

		return course;
	}

	@Override
	public Course addCourse(String userId, Course course) {

		if (StringUtils.isNotBlank(course.getResourceId())) {
			courseMapper.updateById(course);
		} else {
			course.setCreateUser(userId);
			course.setLastEditUser(userId);
			course.setStatus(ModelConstant.NOT_DELETE);
			course.setHeat(0);
			courseMapper.insert(course);
		}

		return course;
	}
}
