package com.cyberpeace.oj.course.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.context.SpecialCourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonConfigMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Autowired
	private ResourceCollectionMapper resourceCollectionMapper;

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private LessonConfigService lessonConfigService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private CourseValidateHandler courseValidateHandler;

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public CourseInfoBo selectById(String courseId, String userId) {
		// 基本数据校验
		Course course = courseValidateHandler.validateCourseExist(courseId);

		CourseInfoBo courseInfoBo = courseToBo(course, true);
		courseInfoBo.setLogo(utilService.handlerFileUrl(course.getLogo()));
		// 设置是否收藏
		Boolean collection = resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.COURSE.getCode(), courseId);
		courseInfoBo.setIsCollection(collection);

		// 访问量计算
		Integer heat = null != course.getHeat() ? course.getHeat() + CommonConstant.ONE
				: CommonConstant.ONE;

		// 更新访问量数据
		Course heatCourse = new Course();
		heatCourse.setHeat(heat);
		heatCourse.setResourceId(course.getResourceId());
		courseMapper.updateById(heatCourse);
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

		// logo数据地址转换
		courseInfoBo.setLogo(utilService.handlerFileUrl(course.getLogo()));
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
					infBo.setInterview(
							infBo.getIsPublic() && CollectionUtils.isNotEmpty(authCourseIds)
									&& authCourseIds.contains(infBo.getResourceId()));

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

		if (authContext.getIsWeb()) {
			courseIds = StreamApiUtils.filter(courseIds, courseId -> CollectionUtils.isNotEmpty(
					courseLessonMapper.selectByCourse(courseId, authContext.getIsWeb())));
			if (CollectionUtils.isEmpty(courseIds)) {
				return PageUtil.getNullResult();
			}
		}

		// 我的课程 系统课程 共享课程过滤
		SpecialCourseContext specialContext = BeanUtils.copy(courseContext,
				SpecialCourseContext.class);
		if (!specialContext.getMyCourse() && !specialContext.getSystemCourse()
				&& !specialContext.getShareCourse()) {
			return PageUtil.getNullResult();
		}
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
			courseAdminBo.setCreateUserName(null != userInfo ? userInfo.getName() : "");
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
		courseValidateHandler.validateCourseNameExist(course.getName());
		courseValidateHandler.validateCourseNumExist(course.getCourseNum());

		// 基本信息组装
		course.setHeat(CommonConstant.ZERO);
		course.setBuiltin(CommonConstant.FALSE);
		course.setCreateUserId(userId);
		course.setModifyUserId(userId);
		course.setResourceId(StringUtils.isNotBlank(course.getCourseNum()) ? course.getCourseNum()
				: IDGeneratorUtil.getID());

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

		// 数据存在性校验
		Course courseExist = courseValidateHandler.validateCourseExist(course.getResourceId());
		if (StringUtils.isNotBlank(course.getCourseNum())
				&& StringUtils.isNotBlank(courseExist.getCourseNum())) {
			course.setCourseNum(null);
		}

		// 数据更新
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

	@Override
	public Boolean batchDelete(List<String> courseIds, AuthContext authContext) {

		// 课时数据查询
		Map<String, List<String>> courseLessonMap = StreamApiUtils.toMap(courseIds,
				courseId -> courseId, courseId -> {
					List<CourseLesson> lessons = courseLessonMapper.selectByCourse(courseId, false);
					return StreamApiUtils.map(lessons, CourseLesson::getResourceId);
				});

		// 收藏数据
		List<String> collectionIds = courseIds.stream()
				.map(courseId -> resourceCollectionMapper.selectByBusiness(null, courseId,
						ResourceCollection.BusinessType.COURSE.getCode()))
				.flatMap(Collection::stream).collect(Collectors.toList());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 留言笔记数据删除
				courseIds.forEach(courseId -> {
					noteMapper.deleteByBusiness(courseId, Note.NoteType.NOTE.getCode(),
							Note.NoteResourceType.COURSE.getCode());
					commentMapper.deleteByBusinessId(courseId);
				});

				// 收藏数据删除
				if (CollectionUtils.isNotEmpty(collectionIds)) {
					resourceCollectionMapper.deleteBatchIds(collectionIds);
				}

				// 课时数据删除
				courseIds.forEach(courseId -> {
					List<String> lessonIds = courseLessonMap.get(courseId);
					if (CollectionUtils.isNotEmpty(lessonIds)) {
						courseLessonService.batchDelete(courseId, lessonIds, authContext);
					}
				});

				// 章节数据删除
				lessonConfigMapper.batchDelete(courseIds);
				// 课程数据删除
				courseMapper.deleteBatchIds(courseIds);
			}
		});

		return true;
	}
}
