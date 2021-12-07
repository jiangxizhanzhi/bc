package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.context.SpecialCourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private LessonConfigService lessonConfigService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public CourseInfoBo selectById(String courseId, String userId) {
		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			log.error("Course not found! course_id=[{}]", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}
		CourseInfoBo courseInfoBo = courseToBo(course, true);
		// 设置是否收藏

		Boolean collection = resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.COURSE.getCode(), courseId);
		courseInfoBo.setIsCollection(collection);
		return courseInfoBo;
	}

	/**
	 * 数据转换
	 * @param isWeb 前台查询 仅查询公开
	 * @param course
	 * @return
	 */
	private CourseInfoBo courseToBo(Course course, Boolean isWeb) {

		// 转换类别
		CourseCategory courseDirection = courseCategoryMapper.selectById(course.getCategoryId());

		CourseInfoBo courseInfoBo = BeanUtils.copy(course, CourseInfoBo.class);
		courseInfoBo.setDirectionName(courseDirection.getName());

		// 计算课时数量
		List<CourseLesson> courseLessons = courseLessonMapper.selectByCourse(course.getResourceId(),
				isWeb);
		courseInfoBo.setLessonCount(courseLessons.size());

		// 计算课程时长
		int sum = courseLessons.stream().mapToInt(CourseLesson::getDuration).sum();
		courseInfoBo.setCourseDuration(sum);

		// 计算实验课和理论课数量
		List<CourseLesson> theoryCount = courseLessons.stream()
				.filter(courseLesson -> courseLesson.getType()
						.intValue() == CourseLesson.LessonType.THEORY.getCode().intValue())
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
		List<CourseInfoBo> infoBos = courses.stream().map(course -> courseToBo(course, true))
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
	public ListRsp<CourseInfoBo> selectPage(Page page, CourseQueryBo courseContext,
			AuthContext authContext) {
		// 权限过滤
		List<String> courseIds = null;
		if (null != authContext) {
			courseIds = courseMapper.selectByAuth(authContext);
			if (CollectionUtils.isEmpty(courseIds)) {
				return PageUtil.getNullResult();
			}
		}

		// 我的课程 系统课程 共享课程过滤
		SpecialCourseContext specialContext = BeanUtils.copy(courseContext,
				SpecialCourseContext.class);
		if (!specialContext.getMyCourse() || !specialContext.getShareCourse()
				|| !specialContext.getSystemCourse()) {
			courseIds = courseMapper.selectSpecial(courseIds, specialContext);
			if (CollectionUtils.isEmpty(courseIds)) {
				return PageUtil.getNullResult();
			}
		}

		// 常规条件过滤
		IPage<Course> iPage = courseMapper.listByPage(page, courseContext, courseIds,
				authContext.getIsWeb());

		IPage<CourseWebBo> webBoIPage = iPage.convert(course -> transferToBo(course, authContext));
		return PageUtil.getListResult(webBoIPage);
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
			CourseInfoBo courseInfoBo = courseToBo(course, authContext.getIsWeb());
			CourseAdminBo courseAdminBo = BeanUtils.copy(courseInfoBo, CourseAdminBo.class);
			courseAdminBo.setAuth(course.getReadAuthType());
			courseAdminBo.setShare(course.getOperationAuthType());
			if (course.getReadAuthType().intValue() == AuthTable.AuthType.CUSTOM_SHARE_MODE
					.getCode().intValue()) {
				List<String> orgs = JsonUtil.toJsonObjectArray(course.getReadAuthIds(),
						String.class);
				courseAdminBo.setAuthCount(orgs.size());
			}
			if (course.getOperationAuthType().intValue() == AuthTable.AuthType.CUSTOM_SHARE_MODE
					.getCode().intValue()) {
				List<String> shares = JsonUtil.toJsonObjectArray(course.getOperationAuthIds(),
						String.class);
				courseAdminBo.setShareCount(shares.size());
			}

			UserInfo userInfo = remoteUserService.getById(course.getCreateUserId());
			courseAdminBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");
			return courseAdminBo;

		} else {
			CourseInfoBo courseInfoBo = courseToBo(course, true);
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
	public Course addCourse(String userId, Course course) {

		// 名称重复性查询
		String resourceId = courseMapper.selectByName(course.getName());
		if (StringUtils.isNotBlank(resourceId)) {
			throw new InvalidParamException("课程名称重复！");
		}

		// 基本信息组装
		course.setHeat(CommonConstant.ZERO);
		course.setBuiltin(CommonConstant.FALSE);
		course.setCreateUserId(userId);
		course.setModifyUserId(userId);
		course.setResourceId(IDGeneratorUtil.getID());

		// 组装课程目录
		AddLessonConfigContext configContext = AddLessonConfigContext.builder()
				.courseId(course.getResourceId())
				.nodeType(LessonConfig.NodeType.NODE_COURSE.getCode()).name(course.getName())
				.build();
		LessonConfig lessonConfig = lessonConfigService.packageLessonConfig(configContext);

		// 执行事务存储
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				courseMapper.insert(course);
				lessonConfigService.save(lessonConfig);
			}
		});
		return course;
	}

	@Override
	public Course updateCourse(String userId, Course course) {

		Course existCourse = courseMapper.selectById(course.getResourceId());
		if (null == existCourse) {
			log.error("Course not found! course_id=[{}]", course.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		course.setModifyUserId(userId);
		courseMapper.updateById(course);
		return course;
	}

	@Override
	public List<String> selectIdsByCategory(String category, AuthContext authContext) {

		List<String> authIds = courseMapper.selectByAuth(authContext);

		List<String> childrenIds = null;
		if (StringUtils.isNotBlank(category)) {
			childrenIds = courseCategoryService.getChildrenIds(category, CommonConstant.TRUE);
		}

		List<String> courseIds = courseMapper.selectIdsByCategory(authIds, childrenIds,
				authContext.getIsWeb());

		return courseIds;
	}
}
