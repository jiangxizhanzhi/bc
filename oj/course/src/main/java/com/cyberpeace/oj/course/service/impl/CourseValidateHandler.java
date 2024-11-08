package com.cyberpeace.oj.course.service.impl;

import java.util.Arrays;
import java.util.List;

import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;
import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 课程验证处理器
 *
 * @author chenzhuo@date 2024/11/08
 */
@Slf4j
@Service
public class CourseValidateHandler {

	/**
	 * 课程Mapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课程课Mapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 课程类别Mapper
	 */
	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	/**
	 * OJ当地场景Mapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 纸Mapper
	 */
	@Autowired
	private PaperMapper paperMapper;

	/**
	 * 实践理论裁判Service
	 */
	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	/**
	 * 实践实操裁判Service
	 */
	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	/**
	 * 校验课时更新时 名称是否已存在
	 *
	 * @param existLesson
	 * @param newName     新增功能名称
	 */
	public void validateLessonUpdateNameExist(CourseLesson existLesson, String newName) {
		String nameExistLesson = courseLessonMapper.selectByCourseAndName(existLesson.getCourseId(),
				newName);
		if (StringUtils.isNotBlank(nameExistLesson)
				&& !nameExistLesson.equals(existLesson.getResourceId())) {
			log.error("lesson name [{}] is duplicate with [{}]", newName, existLesson.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 校验课时修改是否存在场景信息
	 *
	 * @param courseLesson  课程课程
	 * @param ojSceneConfig OJ场景配置
	 */
	public void validateLessonUpdateSceneExist(CourseLesson courseLesson,
			JSONObject ojSceneConfig) {

		List<OjLocalScene> webScenes = ojLocalSceneMapper
				.selectByBusiness(courseLesson.getResourceId(), true);
		List<OjLocalScene> adminScenes = ojLocalSceneMapper
				.selectByBusiness(courseLesson.getResourceId(), false);

		// 无场景 不校验
		if (CollectionUtils.isNotEmpty(webScenes) || CollectionUtils.isNotEmpty(adminScenes)) {
			log.error("lesson:[{}] has bind scene", courseLesson.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.SCENE_IS_RUNNING);
		}
	}

	/**
	 * 校验课程权限
	 *
	 * @param courseId
	 * @param authContext
	 */
	public void validateCoursePermission(String courseId, AuthContext authContext) {

		// 查询授权
		List<String> authIds = courseMapper.selectByAuth(authContext);
		if (CollectionUtils.isEmpty(authIds)) {
			log.error("course:[{}] hasn't been auth", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		// 查询公开
		List<String> publicIds = courseMapper.selectIdsByCategory(authIds, null,
				authContext.getIsWeb());
		if (CollectionUtils.isEmpty(publicIds)) {
			log.error("course:[{}] is non public", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		if (!publicIds.contains(courseId)) {
			log.error("course:[{}] is non public", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}
	}

	/**
	 * 校验课时权限
	 *
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	public CourseLesson validateLessonPermission(String lessonId, AuthContext authContext) {

		CourseLesson courseLesson = courseLessonMapper.selectByPublic(lessonId,
				authContext.getIsWeb());

		if (null == courseLesson) {
			log.error("Lesson not public! lesson_id=[{}]", lessonId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		validateCoursePermission(courseLesson.getCourseId(), authContext);
		return courseLesson;
	}

	/**
	 * 校验课时是否存在
	 *
	 * @param lessonId
	 * @param isPublic
	 * @return
	 */
	public CourseLesson validateLessonExist(String lessonId, Boolean isPublic) {
		CourseLesson lesson = courseLessonMapper.selectByPublic(lessonId, isPublic);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return lesson;
	}

	/**
	 * 校验课程修改时名称校验问题
	 *
	 * @param existCourse
	 * @param newName     新增功能名称
	 */
	public void validateCourseUpdateNameExist(String existCourse, String newName) {
		String existNameId = courseMapper.selectByName(newName);
		if (StringUtils.isNotBlank(existNameId) && !existCourse.equals(existNameId)) {
			log.error("[{}] is exist", newName);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 课程数据存在校验
	 *
	 * @param courseId
	 * @return
	 */
	public Course validateCourseExist(String courseId) {
		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			log.error("Course not found! course_id=[{}]", courseId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return course;
	}

	/**
	 * 课程名称存在性校验
	 *
	 * @param courseName
	 */
	public void validateCourseNameExist(String courseName) {
		String courseId = courseMapper.selectByName(courseName);
		if (StringUtils.isNotBlank(courseId)) {
			log.error("Course is exist! course_name=[{}]", courseName);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 课程编号存在性校验
	 *
	 * @param courseNum
	 */
	public void validateCourseNumExist(String courseNum) {
		Course course = courseMapper.selectByCourseNum(courseNum);
		if (null != course) {
			log.error("Course num is exist! course_num=[{}]", courseNum);
			throw new BusinessException(OjBusinessErrorCode.COURSE_NUM_IS_EXIST);
		}
	}

	/**
	 * 课程编号存在性校验
	 *
	 * @param course
	 * @param path   路径
	 * @return
	 */
	public Course validateCourseName2NumExist(Course course, String path) {
		String courseId = courseMapper.selectByName(course.getName());
		if (!StringUtils.isEmpty(courseId) && !StringUtils.equals(course.getResourceId(), courseId)){
			log.error(
					"The course name=[{}] already exists! import course num=[{}], exists course num=[{}]",
					course.getName(), course.getResourceId(), courseId);
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, path, "课程名称已存在:" + course.getName());
		}

		Course courseExist = courseMapper.selectByCourseNum(course.getCourseNum());

		if (null != courseExist && !StringUtils.equals(course.getName(), courseExist.getName())) {
			log.error("Course num=[{}] exists! import course name=[{}], exists course name=[{}]",
					course.getCourseNum(), course.getName(), courseExist.getName());
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, path, "课程编号已存在:" + course.getCourseNum());
		}
		return courseExist;
	}

	/**
	 * 课时名称重复性校验
	 *
	 * @param courseId
	 * @param name     名称
	 */
	public void validateLessonNameExist(String courseId, String name) {
		String lessonId = courseLessonMapper.selectByCourseAndName(courseId, name);
		if (StringUtils.isNotBlank(lessonId)) {
			log.error("Lesson is exist! course_id=[{}], lesson_name=[{}]", lessonId, name);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 分类数据能否删除校验
	 *
	 * @param categoryId 类别id
	 */
	public void validateEnableDeleteCategory(String categoryId) {
		CourseCategory courseCategory = courseCategoryMapper.selectById(categoryId);
		List<CourseCategory> children = courseCategoryMapper
				.selectChildrenByCode(courseCategory.getCode());

		List<String> categoryIds = StreamApiUtils.map(children, CourseCategory::getResourceId);
		categoryIds.add(categoryId);

		List<String> courseIds = courseMapper.selectIdsByCategory(null, categoryIds, false);
		if (CollectionUtils.isNotEmpty(courseIds)) {
			log.error("can't delete used resource:[{}]", courseIds);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_USED_NOT_DELETED);
		}

	}

	/**
	 * 校验分类数据是否存在
	 *
	 * @param categoryId
	 * @return
	 */
	public CourseCategory validateCategoryExist(String categoryId) {

		CourseCategory category = courseCategoryMapper.selectById(categoryId);
		if (null == category) {
			log.error("Course category not exist! course_category_id=[{}]", categoryId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return category;
	}

	/**
	 * 分类名称长度校验
	 *
	 * @param name 名称
	 */
	public void categoryNameLengthValidate(String name) {
		if (name.length() > 64) {
			log.error("[{}] is too long over 64", name);
			throw new BusinessException(OjBusinessErrorCode.FIELD_TOO_LONG);
		}
	}

	/**
	 * 校验课时类型
	 *
	 * @param lessonType
	 * @param accessType 访问类型
	 */
	public void validateLessonType(Integer lessonType, Integer accessType) {
		if (lessonType.intValue() != accessType) {
			log.error("error lesson type [{}]", lessonType);
			throw new BusinessException(OjBusinessErrorCode.ACCESS_LESSON_TYPE,
					EnumUtils.getEnumDescByCode(lessonType, CourseLesson.LessonType.class));
		}
	}

	/**
	 * 校验是否为内置资源
	 *
	 * @param course 课程
	 */
	public void validateBuiltin(Course course) {
		if (course.getBuiltin()) {
			log.error("built in course [{}] can't be export", course.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.UNABLE_TO_EXPORT_BUILTIN_RESOURCE, course.getName());
		}
		List<CourseLesson> courseLessonList = courseLessonMapper.listByCourses(Arrays.asList(course.getResourceId()), null);
		courseLessonList.forEach(courseLesson -> {
			if (courseLesson.getBuiltin()) {
				log.error("built in lesson [{}] can't be export", courseLesson.getResourceId());
				throw new BusinessException(OjBusinessErrorCode.COURSE_CONTAIN_BUILTIN_LESSON, course.getName(), courseLesson.getName());
			}
			Paper paper = paperMapper.selectById(courseLesson.getPaperId());
			List<TheoryLessonBo> theoryLessonBos = practiceTheoryRefService.selectByPaper(paper.getResourceId(), true);
			theoryLessonBos.forEach(theoryLessonBo -> {
				if (theoryLessonBo.getBuiltin()) {
					log.error("built in practice [{}] can't be export", theoryLessonBo.getResourceId());
					throw new BusinessException(OjBusinessErrorCode.LESSON_CONTAIN_BUILTIN_PRACTICE, course.getName(), courseLesson.getName(), theoryLessonBo.getTitle());
				}
			});
			List<OperationLessonBo> operationLessonBos = practiceOperationRefService
					.selectByPaperId(courseLesson.getResourceId(), paper.getResourceId(), true, true, null);
			operationLessonBos.forEach(operationLessonBo -> {
				if (operationLessonBo.getBuiltin()) {
					log.error("built in practice [{}] can't be export", operationLessonBo.getResourceId());
					throw new BusinessException(OjBusinessErrorCode.LESSON_CONTAIN_BUILTIN_PRACTICE, course.getName(), courseLesson.getName(), operationLessonBo.getTitle());
				}
			});
		});
	}
}
