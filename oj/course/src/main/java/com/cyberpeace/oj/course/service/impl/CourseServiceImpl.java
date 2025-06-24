package com.cyberpeace.oj.course.service.impl;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

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
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.SystemPropertyService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.res.CourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.LessonRecordRes;
import com.cyberpeace.oj.course.dao.context.SpecialCourseContext;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.*;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationRefMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryRefMapper;
import com.cyberpeace.oj.practice.entity.Paper;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 课程Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see ServiceImpl
 * @see CourseService
 * @since 1.0.0
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	/**
	 * 课程Mapper
	 *
	 * @see CourseMapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课程类别Mapper
	 *
	 * @see CourseCategoryMapper
	 */
	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	/**
	 * 课时配置Mapper
	 *
	 * @see LessonConfigMapper
	 */
	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	/**
	 * 课程课时Mapper
	 *
	 * @see CourseLessonMapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 资源收集Service
	 *
	 * @see ResourceCollectionService
	 */
	@Autowired
	private ResourceCollectionService resourceCollectionService;

	/**
	 * 资源收集Mapper
	 *
	 * @see ResourceCollectionMapper
	 */
	@Autowired
	private ResourceCollectionMapper resourceCollectionMapper;

	/**
	 * 课程类别Service
	 *
	 * @see CourseCategoryService
	 */
	@Autowired
	private CourseCategoryService courseCategoryService;

	/**
	 * 课时配置Service
	 *
	 * @see LessonConfigService
	 */
	@Autowired
	private LessonConfigService lessonConfigService;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * utilService
	 *
	 * @see UtilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 课程验证处理器
	 *
	 * @see CourseValidateHandler
	 */
	@Autowired
	private CourseValidateHandler courseValidateHandler;

	/**
	 * 课程课时Service
	 *
	 * @see CourseLessonService
	 */
	@Autowired
	private CourseLessonService courseLessonService;

	/**
	 * 笔记Mapper
	 *
	 * @see NoteMapper
	 */
	@Autowired
	private NoteMapper noteMapper;

	/**
	 * 评论Mapper
	 *
	 * @see CommentMapper
	 */
	@Autowired
	private CommentMapper commentMapper;

	/**
	 * 练习理论Service
	 *
	 * @see PracticeTheoryService
	 */
	@Autowired
	private PracticeTheoryService practiceTheoryService;

	/**
	 * 练习实操Service
	 *
	 * @see PracticeOperationService
	 */
	@Autowired
	private PracticeOperationService practiceOperationService;

	/**
	 * 试卷Mapper
	 *
	 * @see PaperMapper
	 */
	@Autowired
	private PaperMapper paperMapper;

	/**
	 * 试卷Service
	 *
	 * @see PaperService
	 */
	@Autowired
	private PaperService paperService;

	/**
	 * 练习理论裁判Mapper
	 *
	 * @see PracticeTheoryRefMapper
	 */
	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	/**
	 * 练习实操裁判Mapper
	 *
	 * @see PracticeOperationRefMapper
	 */
	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	/**
	 * 练习理论裁判Service
	 *
	 * @see PracticeTheoryRefService
	 */
	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	/**
	 * 练习实操裁判Service
	 *
	 * @see PracticeOperationRefService
	 */
	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	/**
	 * 课程研究历史Mapper
	 *
	 * @see CourseStudyHistoryMapper
	 */
	@Autowired
	private CourseStudyHistoryMapper courseStudyHistoryMapper;

	/**
	 * 系统配置Service
	 *
	 * @see SystemPropertyService
	 */
	@Autowired
	private SystemPropertyService systemPropertyService;

    /**
     * 课程统计学Service
     *
     * @see CourseStatisticsService
     */
    @Autowired
    private CourseStatisticsService courseStatisticsService;


	/**
	 * 课时记录Mapper
	 *
	 * @see LessonRecordMapper
	 */
	@Autowired
	private LessonRecordMapper lessonRecordMapper;


	/**
     * 查询由id
     *
     * @param courseId    课程id
     * @param authContext auth 上下文
     * @return {@code CourseInfoBo }
     * @throws
     * @see String
     * @see AuthContext
     * @see CourseInfoBo
     */
    @Override
    public CourseInfoBo selectById(String courseId, AuthContext authContext) {
        // 基本数据校验
        Course course = courseValidateHandler.validateCourseExist(courseId);
        courseValidateHandler.validateCoursePermission(courseId, authContext);

		CourseInfoBo courseInfoBo = courseToBo(course, true);
		courseInfoBo.setLogo(utilService.handlerFileUrl(course.getLogo()));
		// 设置是否收藏
		Boolean collection = resourceCollectionService.selectByUnique(authContext.getUserId(),
				ResourceCollection.BusinessType.COURSE.getCode(), courseId);
		courseInfoBo.setIsCollection(collection);

		// 访问量计算
		Integer heat = null != course.getHeat() ? course.getHeat() + CommonConstant.ONE
				: CommonConstant.ONE;

		// 更新访问量数据
		Course heatCourse = new Course();
		heatCourse.setHeat(heat);
		heatCourse.setResourceId(course.getResourceId());
		courseMapper.updateCourseHeat(heatCourse.getHeat(),heatCourse.getResourceId());

		//增加学习记录
		CourseStudyHistory courseStudyHistory = courseStudyHistoryMapper.selectByCourseIdAndUserId(courseId, authContext.getUserId());
		if(courseStudyHistory == null){
			courseStudyHistoryMapper.insert(new CourseStudyHistory(IDGeneratorUtil.getID(), courseId,
					course.getName(), authContext.getUserId()));
		}else {
			courseStudyHistoryMapper.updateById(courseStudyHistory);
		}
		return courseInfoBo;
	}

	/**
	 * 数据转换
	 *
	 * @param course
	 * @param isWeb  前台查询 仅查询公开
	 * @return
	 * @throws
	 * @see Course
	 * @see Boolean
	 * @see CourseInfoBo
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

	/**
	 * 查询由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<CourseInfoBo> }
	 * @throws
	 * @see List<String>
	 * @see List<CourseInfoBo>
	 */
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

	/**
	 * 查询分页
	 *
	 * @param page          分页
	 * @param courseContext 课程上下文
	 * @param authContext   auth 上下文
	 * @param isWeb         是否Web
	 * @return {@code ListRsp<CourseWebBo> }
	 * @throws
	 * @see Page<Course>
	 * @see CourseQueryBo
	 * @see AuthContext
	 * @see boolean
	 * @see ListRsp<CourseWebBo>
	 */
	@Override
	public ListRsp<CourseWebBo> selectPage(Page<Course> page, CourseQueryBo courseContext,
			AuthContext authContext, boolean isWeb) {
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

//		进度
        List<CourseStudyProcessBo> processBos = courseStatisticsService.statisticByIds(courseIds,
                authContext.getUserId());
        Map<String, CourseStudyProcessBo> processBoMap = processBos.stream()
                .collect(Collectors.toMap(CourseStudyProcessBo::getCourseId, bo -> bo));

        if (isWeb) {
            // 常规条件过滤
            Page<Course> pageTmp = BeanUtils.copy(page, Page.class);
            pageTmp.setSize(courseIds.size());
            pageTmp.setCurrent(1);
            IPage<Course> iPage = courseMapper.listByPage(pageTmp, courseContext, courseIds,
                    authContext.getIsWeb());
            //该用户可见的课程列表
            List<Course> allCourseList = iPage.getRecords();
            //学习历史
            List<CourseStudyHistory> courseStudyHistoryList = courseStudyHistoryMapper.selectByUserId(authContext.getUserId());
            //将学习历史中的课程顺序提前
            List<Course> studyCourseList = new ArrayList<>();
            for (CourseStudyHistory courseStudyHistory : courseStudyHistoryList) {
                for (Course course : allCourseList) {
                    if (courseStudyHistory.getCourseId().equals(course.getCourseNum())) {
                        studyCourseList.add(course);
                    }
                }
            }
            allCourseList.removeAll(studyCourseList);
            studyCourseList.addAll(allCourseList);

            // 提取用户名映射
            Map<String, String> userNameMap = remoteUserService.getIdNameMap(studyCourseList,
                    Course::getCreateUserId);
            List<CourseWebBo> courseWebBoList = studyCourseList.stream()
                    .map(course -> transferToBo(course, authContext, userNameMap, processBoMap))
                    .collect(Collectors.toList());
            return PageUtil.getListResult(courseWebBoList, page, studyCourseList.size());
        }
        // 常规条件过滤
        IPage<Course> iPage = courseMapper.listByPage(page, courseContext, courseIds,
                authContext.getIsWeb());

		// 提取用户名映射
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(iPage.getRecords(),
				Course::getCreateUserId);

        IPage<CourseWebBo> webBoIPage = iPage
                .convert(course -> transferToBo(course, authContext, userNameMap, processBoMap));
        return PageUtil.getListResult(webBoIPage);
    }

    /**
     * 数据格式转换
     *
     * @param course
     * @param authContext
     * @param userNameMap  用户名称映射
     * @param processBoMap
     * @return
     * @throws
     * @see Course
     * @see AuthContext
     * @see Map<String, String>
     * @see CourseWebBo
     */
    private CourseWebBo transferToBo(Course course, AuthContext authContext,
                                     Map<String, String> userNameMap, Map<String, CourseStudyProcessBo> processBoMap) {

		if (null != authContext && !authContext.getIsWeb()) {
			CourseInfoBo courseInfoBo = courseToBo(course, authContext.getIsWeb());
			CourseAdminBo courseAdminBo = BeanUtils.copy(courseInfoBo, CourseAdminBo.class);
			courseAdminBo.setAuth(course.getReadAuthType());
			courseAdminBo.setShare(course.getOperationAuthType());
			courseAdminBo.setEbook(course.getCourseType());
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

            courseAdminBo.setCreateUserName(userNameMap.getOrDefault(course.getCreateUserId(), ""));

//			进度
            if (processBoMap != null && processBoMap.get(course.getResourceId()) != null) {
                CourseStudyProcessBo bo = processBoMap.get(course.getResourceId());
                courseAdminBo.setProcess(MathUtils.ceil(bo.getProcess()));
            }

            return courseAdminBo;

        } else {
            CourseInfoBo courseInfoBo = courseToBo(course, true);
            CourseWebBo courseWebBo = BeanUtils.copy(courseInfoBo, CourseWebBo.class);
            courseWebBo.setEbook(course.getCourseType());

            //			进度
            if (processBoMap != null && processBoMap.get(course.getResourceId()) != null) {
                CourseStudyProcessBo bo = processBoMap.get(course.getResourceId());
                courseWebBo.setProcess(MathUtils.ceil(bo.getProcess()));
            }

            return courseWebBo;
        }

	}

	/**
	 * id列表由不删除id列表
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	@Override
	public List<String> idsByNotDeleteIds(List<String> courseIds) {
		if (CollectionUtils.isEmpty(courseIds)) {
			return Lists.newArrayList();
		}
		return courseMapper.notDeleteIdsByIds(courseIds);
	}

	/**
	 * 新增课程
	 *
	 * @param userId 用户id
	 * @param course 课程
	 * @return {@code Course }
	 * @throws
	 * @see String
	 * @see Course
	 * @see Course
	 */
	@Override
	public Course addCourse(String userId, Course course) {

		// 名称重复性查询
		courseValidateHandler.validateCourseNameExist(course.getName());

		// 基本信息组装
		course.setHeat(CommonConstant.ZERO);
		course.setBuiltin(CommonConstant.FALSE);
		course.setCreateUserId(userId);
		course.setModifyUserId(userId);
		course.setResourceId(getRandomCourseNum(CommonConstant.TEN));
		course.setCourseNum(course.getResourceId());

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

	/**
	 * 更新课程
	 *
	 * @param userId 用户id
	 * @param course 课程
	 * @return {@code Course }
	 * @throws
	 * @see String
	 * @see Course
	 * @see Course
	 */
	@Override
	public Course updateCourse(String userId, Course course) {

		// 数据存在性校验
		Course courseExist = courseValidateHandler.validateCourseExist(course.getResourceId());
		courseValidateHandler.validateCourseUpdateNameExist(courseExist.getResourceId(),
				course.getName());
		if (StringUtils.isNotBlank(course.getCourseNum())
				&& StringUtils.isNotBlank(courseExist.getCourseNum())) {
			course.setCourseNum(null);
		}

		final boolean modify = StringUtils.isNotBlank(course.getName())
				&& !course.getName().equals(courseExist.getName());

		// 数据更新
		course.setModifyUserId(userId);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				courseMapper.updateById(course);
				if (modify) {
					LessonConfig courseNode = lessonConfigMapper
							.getCourseNode(course.getResourceId());
					courseNode.setName(course.getName());
					lessonConfigMapper.updateById(courseNode);
				}
			}
		});

		return course;
	}

	/**
	 * 查询id列表由类别
	 *
	 * @param category    类别
	 * @param authContext auth 上下文
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see List<String>
	 */
	@Override
	public List<String> selectIdsByCategory(String category, AuthContext authContext) {

		List<String> authIds = courseMapper.selectByAuth(authContext);

		List<String> childrenIds = null;
		if (StringUtils.isNotBlank(category)) {
			childrenIds = courseCategoryService.getChildrenIds(category, CommonConstant.TRUE);
		}

		return courseMapper.selectIdsByCategory(authIds, childrenIds,
				authContext.getIsWeb());
	}

	/**
	 * 批量删除
	 *
	 * @param courseIds   课程id列表
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see List<String>
	 * @see AuthContext
	 * @see Boolean
	 */
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

	/**
	 * 导出课程
	 *
	 * @param courseIds   课程id列表
	 * @param authContext auth 上下文
	 * @return {@code String }
	 * @throws
	 * @see List<String>
	 * @see AuthContext
	 * @see String
	 */
	@Override
	public String dumpCourse(List<String> courseIds, AuthContext authContext) {

		// 数据校验
		courseIds.forEach(
				courseId -> courseValidateHandler.validateCoursePermission(courseId, authContext));
		List<Course> courseList = StreamApiUtils.map(courseIds,
				courseId -> courseValidateHandler.validateCourseExist(courseId));
		if (!systemPropertyService.getExportBuiltin()) {
			courseList.forEach(course -> courseValidateHandler.validateBuiltin(course));
		}

		// 创建文件夹
		String destPath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID();
		String destFilePath = destPath + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;

		// 数据导出
		List<CourseDumpLoadBo> courseData = StreamApiUtils.map(courseList, course -> {
			// 课程数据
			Map<String, String> filePathMap = Maps.newHashMap();
			CourseDumpLoadBo courseDumpLoadBo = dumCourse(course, filePathMap, destFilePath);

			// 课时数据
			List<CourseLesson> courseLessonList = courseLessonMapper
					.selectByCourse(course.getResourceId(), authContext.getIsWeb());
			List<LessonDumpLoadBo> lessonDumpLoadBos = StreamApiUtils.map(courseLessonList,
					courseLesson -> courseLessonService.handlerLessonDump(courseLesson, filePathMap,
							destFilePath));

			// 目录数据导出
			LessonConfigDumpBo lessonConfig = lessonConfigService
					.dumpLessonConfig(course.getResourceId(), authContext);
			courseDumpLoadBo.setLessonConfig(lessonConfig);

			// 设置数据
			courseDumpLoadBo.setLesson(lessonDumpLoadBos);
			courseDumpLoadBo.setFilePathMap(filePathMap);

			return courseDumpLoadBo;
		});

		// 数据写入
		String dataPath = destPath + File.separatorChar + ResourceImportConstant.DATA_JSON;
		FileUtil.writeToDestFile(JsonUtil.arrayToString(courseData), dataPath);

		// 文件压缩
		String zipPath = ZipUtil.toZipDest(destPath, AttachmentPath.OTHER.getDesc());
		FileUtil.deleteDir(destPath, true);
		return zipPath;
	}

	/**
	 * 加载课程
	 *
	 * @param realPath    真实路径
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	@Override
	public Boolean loadCourse(String realPath, AuthContext authContext) {

		// 文件解压
		String destFilePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID();
		ZipUtil.unzipToDest(realPath, destFilePath);
		String filePath = destFilePath + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;

		// 文件数据读取
		String dataPath = destFilePath + File.separatorChar + ResourceImportConstant.DATA_JSON;
		String content = FileUtil.getContent(new File(dataPath));
		List<CourseDumpLoadBo> courseDumpLoadBos = JsonUtil.toJsonObjectArray(content,
				CourseDumpLoadBo.class);

		// 数据解析
		List<Course> courseList = Lists.newArrayList();
		List<CourseLesson> lessonList = Lists.newArrayList();
		List<LessonConfig> configList = Lists.newArrayList();
		List<Paper> paperList = Lists.newArrayList();
		List<PracticeTheoryRef> theoryRefList = Lists.newArrayList();
		List<PracticeOperationRef> operationRefList = Lists.newArrayList();

		courseDumpLoadBos.forEach(dumpBo -> {
			// 课程数据处理
			Course course = loadCourse(dumpBo, authContext.getUserId(), filePath);
			courseList.add(course);

			// 课时数据处理
			List<CourseLesson> courseLessons = StreamApiUtils.map(dumpBo.getLesson(),
					lessonDumpLoadBo -> {
						// 解析课时
						CourseLesson lesson = courseLessonService.loadLesson(course.getResourceId(),
								lessonDumpLoadBo, filePath, dumpBo.getFilePathMap());

						// 解析习题
						List<PracticeTheory> practiceTheories = loadPracticeTheory(lessonDumpLoadBo,
								authContext.getUserId());
						List<PracticeOperation> operationList = loadPracticeOperation(
								lessonDumpLoadBo, authContext.getUserId(), filePath,
								dumpBo.getFilePathMap());

						// 课后练习试卷
						Paper paper = initPaper(lesson, practiceTheories, operationList);
						paperBindLesson(lesson, paper, paperList);

						// 课后练习习题数据
						theoryBindPaper(paper.getResourceId(), practiceTheories, theoryRefList);
						operationBindPaper(paper.getResourceId(), operationList, operationRefList);

						return lesson;
					});
			lessonList.addAll(courseLessons);

			// 章节数据
			List<LessonConfig> lessonConfigList = lessonConfigService.loadLessonConfig(
					dumpBo.getLessonConfig(), course.getResourceId(), courseLessons);
			configList.addAll(lessonConfigList);

		});

		return transactionTemplate.execute(status->  {
			courseLessonService.saveOrUpdateBatch(lessonList);
			lessonConfigService.saveOrUpdateBatch(configList);

			// 试卷数据更新
			for (Paper paper : paperList) {
				practiceOperationRefMapper.deleteByPaper(paper.getResourceId());
				practiceTheoryRefMapper.deleteByPaper(paper.getResourceId());
			}

			// 课后练习相关
			if (CollectionUtils.isNotEmpty(theoryRefList)) {
				practiceTheoryRefService.saveBatch(theoryRefList);
			}
			if (CollectionUtils.isNotEmpty(operationRefList)) {
				practiceOperationRefService.saveBatch(operationRefList);
			}
			if (CollectionUtils.isNotEmpty(paperList)) {
				paperService.saveOrUpdateBatch(paperList);
			}

			return saveOrUpdateBatch(courseList);
		});
	}

	/**
	 * 查询课程名称是否重复
	 *
	 * @param courseNames 课程名字
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	@Override
	public List<String> duplicateCourseInspect(List<String> courseNames) {
		List<String> duplicateName = Lists.newArrayList();
		if (null == courseNames || courseNames.size() == 0){
			return duplicateName;
		}

		List<Course> courses = courseMapper.selectIdsByCourseName(courseNames);
		for (Course course: courses){
			duplicateName.add(course.getName());
		}

		return duplicateName;

	}

	/**
	 * 清除用户认证
	 *
	 * @param userId 用户id
	 * @throws
	 * @see String
	 */
	@Override
	public void clearUserAuth(String userId) {
		List<Course> courseList = courseMapper.selectCourseByUserId(userId);

		for (Course course : courseList) {
			course.setOperationAuthIds(
					LocalStringUtil.strRemoveSubStr(course.getOperationAuthIds(), userId));
		}

		updateBatchById(courseList);
	}

	/**
	 * 清除组织认证
	 *
	 * @param orgId 组织id
	 * @throws
	 * @see String
	 */
	@Override
	public void clearOrgAuth(String orgId) {
		List<Course> courseList = courseMapper.selectCourseByOrgId(orgId);

		for (Course course : courseList) {
			course.setReadAuthIds(LocalStringUtil.strRemoveSubStr(course.getReadAuthIds(), orgId));
		}

		updateBatchById(courseList);
	}

    @Override
    public CourseStatisticsRes statistics(ListRsp<CourseWebBo> boListRsp, AuthContext authContext) {
        long count = boListRsp.getList().stream().filter(bo -> bo.getProcess() == 100).count();
        long courseCount = boListRsp.getList().size();
        CourseStatisticsRes courseStatisticsRes = new CourseStatisticsRes();
        courseStatisticsRes.setCompletedCourseCount((int) count);
        courseStatisticsRes.setCourseCount((int) courseCount);
//		最近在学
//		原数据
		List<String> webCourseIds = StreamApiUtils.map(boListRsp.getList(), CourseWebBo::getResourceId);
		List<LessonRecord> lessonRecords = webCourseIds.isEmpty() ? Lists.newArrayList()
				: lessonRecordMapper.selectLatestByUserId(authContext.getUserId(), webCourseIds);
        List<String> courseIds = lessonRecords.stream().map(LessonRecord::getCourseId).collect(Collectors.toList());
        List<Course> courses = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(courseIds)) {
			courses = courseMapper.selectBatchIds(courseIds);
		}

        List<LessonConfig> lessonConfigsByCourseIds = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(courseIds)) {
			lessonConfigsByCourseIds = lessonConfigMapper.selectPublicLessonsByCourseIds(courseIds);
		}

        Map<String, Course> courseMap = courses
                .stream()
                .collect(Collectors.toMap(Course::getResourceId,
                        course -> course,
                        (existing, replacement) -> existing));
        Map<String, List<LessonConfig>> lessonConfigsByCourseId = lessonConfigsByCourseIds.stream().collect(Collectors.groupingBy(LessonConfig::getCourseId));
//		计算目录
        List<LessonRecordRes> collect = lessonRecords.stream().map(lessonRecord -> {
            String lessonId = lessonRecord.getLessonId();
            Course course = courseMap.get(lessonRecord.getCourseId());
            List<LessonConfig> lessonConfigs = lessonConfigsByCourseId.get(lessonRecord.getCourseId());
            LessonConfig firstLessonConfigs = lessonConfigs.stream().filter(lessonConfig -> lessonConfig.getParentId() == null).findFirst().get();
            Map<String, List<LessonConfig>> lessonConfigsByParentId =
                    lessonConfigs.stream()
                    .filter(lessonConfig -> lessonConfig.getParentId() != null)
                    .collect(Collectors.groupingBy(LessonConfig::getParentId));
            LessonRecordRes lessonRecordRes = new LessonRecordRes();
			lessonRecordRes.setCourseId(course.getResourceId());
            lessonRecordRes.setCourseName(course.getName());
            lessonRecordRes.setLessonRecordDate(lessonRecord.getUpdateTime());
            lessonRecordRes.setLessonId(lessonId);
            lessonRecordRes.setFlag(false);
            String lessonIndex = findLessonIndexAndSetInfo(null, lessonId, firstLessonConfigs, lessonConfigsByParentId, lessonRecordRes);
            lessonRecordRes.setLessonIndex(lessonIndex);
            return lessonRecordRes;
        }).collect(Collectors.toList());
        courseStatisticsRes.setLessonRecords(collect);


        return courseStatisticsRes;
    }

	@Override
	public List<String> getWebCourseIds() {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		List<String> webCourseIds = courseMapper.selectByAuth(authContext);
		if (CollectionUtils.isEmpty(webCourseIds)) {
			return webCourseIds;
		}
		webCourseIds = courseMapper.selectWebCourseIds(webCourseIds);
		return webCourseIds;
	}


	/**
	 * 递归查找指定课时记录在课程目录结构中的索引路径，并设置相关信息。
	 *
	 * @param currentPath       当前递归路径（例如："1.2.3"），为空时从根开始
	 * @param lessonRecordId    要查找的课时ID
	 * @param node              当前处理的目录节点（章节或课时）
	 * @param childrenMap       父节点ID到子节点列表的映射，用于递归查找
	 * @param recordRes         用于保存查找到的信息（如课时名、路径、是否找到等）
	 * @return                  返回最终匹配到的索引路径（如："1.2.3"）
	 */
	private String findLessonIndexAndSetInfo(String currentPath, String lessonRecordId, LessonConfig node,
											 Map<String, List<LessonConfig>> childrenMap,
											 LessonRecordRes recordRes) {
		if (recordRes.getFlag()) {
			return currentPath;
		}
		int lessonTypeIndex = 1;
		int chapterTypeIndex = 1;
		String currentPathUpdated = currentPath == null ? chapterTypeIndex + "" : currentPath;

		for (LessonConfig child : childrenMap.getOrDefault(node.getResourceId(), Collections.emptyList())) {
			if (!child.getParentId().equals(node.getResourceId())) {
				continue;
			}
			if (child.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON.getCode().intValue()) {
				if (lessonRecordId.equals(child.getLessonId())) {
					String resultPath = currentPath == null ? lessonTypeIndex + "" : currentPathUpdated + CommonConstant.PERIOD + lessonTypeIndex;
					recordRes.setLessonName(child.getName());
					recordRes.setLessonIndex(resultPath);
					recordRes.setFlag(true);
					return resultPath;
				}
				lessonTypeIndex++;
			} else if (child.getNodeType().intValue() == LessonConfig.NodeType.NODE_CHAPTER.getCode().intValue()) {
				if (!recordRes.getFlag()) {
					currentPathUpdated = findLessonIndexAndSetInfo(
							currentPath == null ? chapterTypeIndex + "" : currentPath + CommonConstant.PERIOD + chapterTypeIndex,
							lessonRecordId, child, childrenMap, recordRes
					);
				}
				chapterTypeIndex++;
			}
		}

		return currentPathUpdated;
	}

    /**
     * 实操习题绑定试卷
     *
     * @param paperId    试卷id
     * @param operations 操作
     * @param refList    参考列表
     * @throws
     * @see String
     * @see List<PracticeOperation>
     * @see List<PracticeOperationRef>
     */
    private void operationBindPaper(String paperId, List<PracticeOperation> operations,
                                    List<PracticeOperationRef> refList) {
        List<PracticeOperationRef> operationRefs = operationEntityToRef(paperId, operations);
        refList.addAll(operationRefs);
    }

	/**
	 * 理论习题绑定试卷
	 *
	 * @param paperId  试卷id
	 * @param theories 理论
	 * @param refList  参考列表
	 * @throws
	 * @see String
	 * @see List<PracticeTheory>
	 * @see List<PracticeTheoryRef>
	 */
	private void theoryBindPaper(String paperId, List<PracticeTheory> theories,
			List<PracticeTheoryRef> refList) {
		// 课后练习习题数据
		List<PracticeTheoryRef> theoryRefs = theoryEntityToRef(paperId, theories);
		refList.addAll(theoryRefs);
	}

	/**
	 * 数据绑定
	 *
	 * @param lesson    课时
	 * @param paper     试卷
	 * @param paperList 试卷列表
	 * @throws
	 * @see CourseLesson
	 * @see Paper
	 * @see List<Paper>
	 */
	private void paperBindLesson(CourseLesson lesson, Paper paper, List<Paper> paperList) {
		lesson.setPaperId(paper.getResourceId());
		paperList.add(paper);
	}

	/**
	 * 加载实操习题
	 *
	 * @param dumpLoadBo 导出加载Bo
	 * @param userId     用户id
	 * @param filePath   文件路径
	 * @param fileMap    文件映射
	 * @return {@code List<PracticeOperation> }
	 * @throws
	 * @see LessonDumpLoadBo
	 * @see String
	 * @see String
	 * @see Map<String, String>
	 * @see List<PracticeOperation>
	 */
	private List<PracticeOperation> loadPracticeOperation(LessonDumpLoadBo dumpLoadBo,
			String userId, String filePath, Map<String, String> fileMap) {
		return StreamApiUtils
				.map(dumpLoadBo.getOperationPractice(),
						practiceDumpLoadBo -> practiceOperationService.loadPracticeOperation(
								IDGeneratorUtil.getID(), practiceDumpLoadBo, fileMap, filePath,
								userId));
	}

	/**
	 * 解析理论习题
	 *
	 * @param dumpLoadBo 导出加载Bo
	 * @param userId     用户id
	 * @return {@code List<PracticeTheory> }
	 * @throws
	 * @see LessonDumpLoadBo
	 * @see String
	 * @see List<PracticeTheory>
	 */
	private List<PracticeTheory> loadPracticeTheory(LessonDumpLoadBo dumpLoadBo, String userId) {
		return StreamApiUtils.map(dumpLoadBo.getTheoryPractice(),
				practiceTheoryDumpLoadBo -> practiceTheoryService.loadPracticeTheory(
						IDGeneratorUtil.getID(), practiceTheoryDumpLoadBo, userId));
	}

	/**
	 * 数据类型转换
	 *
	 * @param paperId
	 * @param practiceTheoryList
	 * @return
	 * @throws
	 * @see String
	 * @see List<PracticeTheory>
	 * @see List<PracticeTheoryRef>
	 */
	private List<PracticeTheoryRef> theoryEntityToRef(String paperId,
			List<PracticeTheory> practiceTheoryList) {

		return StreamApiUtils.map(practiceTheoryList, theory -> {
			String practiceData = JsonUtil.toJsonString(theory);
			PracticeTheoryRef theoryRef = new PracticeTheoryRef();
			theoryRef.setResourceId(IDGeneratorUtil.getID());
			theoryRef.setPaperId(paperId);
			theoryRef.setOriginId(theory.getResourceId());
			theoryRef.setSnapshot(practiceData);
			theoryRef.setOptionRandom(CommonConstant.FALSE);
			return theoryRef;
		});
	}

	/**
	 * 数据类型转换
	 *
	 * @param paperId
	 * @param practiceOperationList
	 * @return
	 * @throws
	 * @see String
	 * @see List<PracticeOperation>
	 * @see List<PracticeOperationRef>
	 */
	private List<PracticeOperationRef> operationEntityToRef(String paperId,
			List<PracticeOperation> practiceOperationList) {

		return StreamApiUtils.map(practiceOperationList, operation -> {
			String practiceData = JsonUtil.toJsonString(operation);
			PracticeOperationRef operationRef = new PracticeOperationRef();
			operationRef.setResourceId(IDGeneratorUtil.getID());
			operationRef.setPaperId(paperId);
			operationRef.setOriginId(operation.getResourceId());
			operationRef.setSnapshot(practiceData);

			return operationRef;
		});
	}

	/**
	 * 有则返回，无则初始化
	 *
	 * @param lesson
	 * @param practiceTheorys    练习理论
	 * @param practiceOperations 练习操作
	 * @return
	 * @throws
	 * @see CourseLesson
	 * @see List<PracticeTheory>
	 * @see List<PracticeOperation>
	 * @see Paper
	 */
	private Paper initPaper(CourseLesson lesson, List<PracticeTheory> practiceTheorys,
			List<PracticeOperation> practiceOperations) {

		Double operationScore = StreamApiUtils.mapToDouble(practiceOperations,
				PracticeOperation::getScore);
		Double theoryScore = StreamApiUtils.mapToDouble(practiceTheorys, PracticeTheory::getScore);

		Paper paper = StringUtils.isNotBlank(lesson.getPaperId())
				? paperMapper.selectById(lesson.getPaperId())
				: new Paper();

		if (StringUtils.isBlank(paper.getResourceId())) {
			paper.setResourceId(IDGeneratorUtil.getID());
		}
		paper.setTotalScore(operationScore + theoryScore);
		paper.setPracticeCount(practiceTheorys.size() + practiceOperations.size());
		paper.setType(Paper.PaperType.LESSON.getCode());
		paper.setName(lesson.getName());
		paper.setIsPublic(CommonConstant.TRUE);

		return paper;

	}

	/**
	 * 课程数据加载
	 *
	 * @param courseDumpLoadBo
	 * @param userId           用户id
	 * @param filePath         文件路径
	 * @return
	 * @throws
	 * @see CourseDumpLoadBo
	 * @see String
	 * @see String
	 * @see Course
	 */
	private Course loadCourse(CourseDumpLoadBo courseDumpLoadBo, String userId, String filePath) {

		// 基本数据设置
		Course course = BeanUtils.copy(courseDumpLoadBo, Course.class);
		course.setCreateUserId(userId);
		course.setModifyUserId(userId);

		// 已存在数据查询
		String courseId = courseMapper.selectByName(course.getName());
		course.setResourceId(
				StringUtils.isNotBlank(courseId) ? courseId : courseDumpLoadBo.getCourseNum());

		if (StringUtils.isBlank(courseId)) {
			course.setHeat(CommonConstant.ZERO);
			course.setBuiltin(CommonConstant.TRUE);
			course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
			course.setReadAuthIds(CommonConstant.EMPTY_LIST_JSON);
			course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
			course.setOperationAuthIds(CommonConstant.EMPTY_LIST_JSON);
		}

		// 分类数据
		CourseCategory category = courseCategoryService
				.getOrCreate(courseDumpLoadBo.getCategoryName());
		course.setCategoryId(category.getResourceId());

		Map<String, String> filePathMap = courseDumpLoadBo.getFilePathMap();

		// logo数据处理
		if (StringUtils.isNotBlank(courseDumpLoadBo.getLogo())) {
			String relativePath = filePathMap.get(courseDumpLoadBo.getLogo());
			String fileSuffix = FileUtil.getFileSuffix(relativePath);
			String logoPath = AttachmentPath.LOGO.getDesc() + IDGeneratorUtil.getID()
					+ CommonConstant.PERIOD + fileSuffix;
			String realPath = filePath + File.separatorChar + relativePath;

			FileUtil.copyFile(realPath, logoPath, false);
			String fileUrl = utilService.replaceFileUrl(logoPath);
			course.setLogo(fileUrl);
		}

		// 课程大纲数据
		if (StringUtils.isNotBlank(courseDumpLoadBo.getOutline())) {
			// 匹配文件路径
			String filterCodeContent = MdUtil.filterCodeContent(courseDumpLoadBo.getOutline());
			List<String> matchList = LocalStringUtil.getMatchStr(filterCodeContent,
					PatternConstant.MARKDOWN_IMAGE);
			List<String> matchPathList = StreamApiUtils.map(matchList, matchStr -> LocalStringUtil
					.getFirstMatchStr(matchStr, PatternConstant.MARKDOWN_IMAGE_URL));

			List<String> fileList = StreamApiUtils.map(matchPathList, match -> {
				String relativePath = filePathMap.get(match);
				String fileSuffix = FileUtil.getFileSuffix(relativePath);
				String realPath = filePath + File.separatorChar + relativePath;

				String outlinePath = AttachmentPath.OUTLINE.getDesc() + IDGeneratorUtil.getID()
						+ CommonConstant.PERIOD + fileSuffix;
				FileUtil.copyFile(realPath, outlinePath, false);
				String fileUrl = utilService.replaceFileUrl(outlinePath);
				return utilService.handlerFileUrl(fileUrl);
			});

			String content = LocalStringUtil.replaceAll(courseDumpLoadBo.getOutline(),
					matchPathList, fileList);
			course.setOutline(content);
		}

		return course;
	}

	/**
	 * 组织导出的课程数据
	 *
	 * @param course
	 * @param filePathMap
	 * @param fileStorePath 文件存储路径
	 * @return
	 * @throws
	 * @see Course
	 * @see Map<String, String>
	 * @see String
	 * @see CourseDumpLoadBo
	 */
	private CourseDumpLoadBo dumCourse(Course course, Map<String, String> filePathMap,
			String fileStorePath) {
		// 元数据拷贝
		CourseDumpLoadBo courseDumpLoadBo = BeanUtils.copy(course, CourseDumpLoadBo.class);

		// 分类数据
		CourseCategory courseCategory = courseValidateHandler
				.validateCategoryExist(course.getCategoryId());
		courseDumpLoadBo.setCategoryName(courseCategory.getName());

		// logo文件copy到临时文件夹
		if (StringUtils.isNotBlank(course.getLogo())) {
			String realPath = utilService.getRealPath(course.getLogo());
			String fileSuffix = FileUtil.getFileSuffix(realPath);
			String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD + fileSuffix;
			String destLogoPath = fileStorePath + File.separatorChar + fileName;

			FileUtil.copyFile(realPath, destLogoPath, false);
			filePathMap.put(course.getLogo(), fileName);
		}

		// 大纲文件处理
		if (StringUtils.isNotBlank(course.getOutline())) {
			String filterCodeContent = MdUtil.filterCodeContent(course.getOutline());
			List<String> matchList = LocalStringUtil.getMatchStr(filterCodeContent,
					PatternConstant.MARKDOWN_IMAGE);
			List<String> matchPathList = StreamApiUtils.map(matchList, matchStr -> LocalStringUtil
					.getFirstMatchStr(matchStr, PatternConstant.MARKDOWN_IMAGE_URL));

			matchPathList.forEach(str -> {
				// 文件拷贝
				String realPath = utilService.getRealPath(str);
				String fileSuffix = FileUtil.getFileSuffix(realPath);
				String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD + fileSuffix;
				String destFilePath = fileStorePath + File.separatorChar + fileName;
				FileUtil.copyFile(realPath, destFilePath, false);

				filePathMap.put(str, fileName);
			});
		}

		return courseDumpLoadBo;
	}

	/**
	 * 获取随机课程数量
	 *
	 * @param length 长度
	 * @return {@code String }
	 * @throws
	 * @see int
	 * @see String
	 */
	@Override
	public String getRandomCourseNum(int length) {
		if (length == CommonConstant.ZERO) {
			return "";
		}

		String courseNum= MathUtils.randomStr(length);
		for (; courseMapper
				.selectByCourseNum(courseNum) != null; courseNum= MathUtils.randomStr(length)) {
			log.warn("Failed to obtain the course number for the {} time", courseNum);
		}
		return courseNum;
	}
}
