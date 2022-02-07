package com.cyberpeace.oj.course.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.*;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationRefMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryRefMapper;
import com.cyberpeace.oj.practice.service.*;
import com.cyberpeace.oj.practice.service.impl.LessonOperationEvaluateHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.service.bo.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class CourseLessonServiceImpl extends ServiceImpl<CourseLessonMapper, CourseLesson> implements CourseLessonService {

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Autowired
	private ResourceCollectionMapper resourceCollectionMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private LessonConfigService lessonConfigService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private PaperService paperService;

	@Autowired
	private PaperMapper paperMapper;

	@Autowired
	private LessonPaperEvaluateMapper lessonPaperEvaluateMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Resource(name = "theory")
	private PracticeEvaluateHandler theoryEvaluateHandler;

	@Resource(name = "lesson_operation")
	private LessonOperationEvaluateHandler lessonOperationEvaluateHandler;

	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private ClassroomGroupInfoService classroomGroupInfoService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private CourseValidateHandler courseValidateHandler;

	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private LessonPracticeSceneService lessonPracticeSceneService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Override
	public LessonInfoBo getLessonDetail(String courseId, String lessonId, AuthContext authContext) {

		// 课时数据校验
		CourseLesson lesson = courseValidateHandler.validateLessonPermission(lessonId, authContext);
		Course course = courseMapper.selectById(courseId);

		// 增加热度
		lesson.setHeat(lesson.getHeat() + CommonConstant.ONE);
		courseLessonMapper.updateById(lesson);

		// 基本信息拷贝
		LessonInfoBo infoBo = BeanUtils.copy(lesson, LessonInfoBo.class);

		// 课程名称数据
		infoBo.setCourseName(null != course ? course.getName() : "");

		// 知识点数据转换
		infoBo.setKnowledge(JsonUtil.toJsonObjectArray(lesson.getKnowledge(), String.class));

		// 课时文件组装
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(lesson.getAttachmentIds(),
				true);
		infoBo.setLessonFile(attachmentBos);

		// 练习数据查询
		Paper paper = paperMapper.selectById(lesson.getPaperId());
		infoBo.setPaperPublic(paper.getIsPublic());
		Integer countByPaper = paperService.practiceCountByPaper(lesson.getPaperId());
		infoBo.setPracticeCount(countByPaper);

		// 上节和下节课程查询
		LessonConfigBo lessonConfig = lessonConfigService.getLessonConfig(courseId, authContext);
		List<LessonConfigBo> boList = lessonConfig.sortListByChildren(false, false);
		List<String> sortLessonIds = boList.stream().map(LessonConfigBo::getLessonId)
				.collect(Collectors.toList());
		infoBo.setPreAndNext(sortLessonIds);

		// 是否收藏
		Boolean collection = resourceCollectionService.selectByUnique(authContext.getUserId(),
				ResourceCollection.BusinessType.LESSON.getCode(), lessonId);
		infoBo.setIsCollection(collection);

		// 文件地址处理
		if (null != lesson.getHandoutType() && lesson.getHandoutType()
				.intValue() == CourseLesson.HandoutType.PDF.getCode().intValue()) {
			infoBo.setHandoutUrl(utilService.handlerFileUrl(lesson.getHandoutUrl()));
		}
		if (null != lesson.getHandoutType()
				&& (lesson.getHandoutType().intValue() == CourseLesson.HandoutType.DOC.getCode()
						.intValue()
						|| lesson.getHandoutType().intValue() == CourseLesson.HandoutType.PPT
								.getCode().intValue())
				&& StringUtils.isNotBlank(lesson.getHandoutUrl())) {
			infoBo.setHandoutUrl(utilService.officeUrl(lesson.getHandoutUrl(), lessonId));
		}
		infoBo.setVideoUrl(utilService.handlerFileUrl(lesson.getVideoUrl()));

		// 查询课时场景信息
		if (lesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode().intValue()
				&& StringUtils.isNotBlank(lesson.getTopoId())) {

			// 拓扑信息查询
			infoBo.setSceneConfigData(remoteTopoService.getTopoById(lesson.getTopoId()));

			// 前台需要先查询课堂场景
			OjLocalScene ojLocalScene = null;
			if (authContext.getIsWeb()) {
				ClassroomGroupInfo groupInfo = classroomGroupInfoService
						.selectByLessonAndUser(lessonId, authContext);
				if (null != groupInfo) {
					// 存在课堂场景 则设置group_id用于前端简历websocket
					ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
					if (null != ojLocalScene) {
						infoBo.setSceneShareMode(SceneShare.GROUP.getCode());
						infoBo.setGroupId(groupInfo.getResourceId());
					}
				}
			}

			// 查询课时场景
			if (null == ojLocalScene) {
				ojLocalScene = ojLocalSceneService.selectOjLocalScene(lessonId,
						lesson.getSceneShareMode(), authContext);
			}

			if (null != ojLocalScene) {
				JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
				infoBo.setSceneData(sceneData);
			}
		}

		// 添加学习记录
		try {
			LessonRecord lessonRecord = LessonRecord.builder().lessonId(lessonId).courseId(courseId)
					.userId(authContext.getUserId()).build();
			lessonRecordMapper.insert(lessonRecord);
		} catch (DuplicateKeyException e) {
			// 代表数据库已存在学习记录 不做任何处理
		}

		return infoBo;
	}

	@Override
	public AdminLessonBo selectByCourseAndLesson(String courseId, String lessonId) {

		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			log.error("Course not found! course_id=[{}]", courseId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		CourseLesson lesson = courseLessonMapper.selectById(lessonId);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 附件查询
		AdminLessonBo lessonInfoBo = BeanUtils.copy(lesson, AdminLessonBo.class);
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(lesson.getAttachmentIds(),
				false);
		lessonInfoBo.setLessonFile(attachmentBos);

		// 习题数据查询
		Paper paper = paperMapper.selectById(lesson.getPaperId());
		lessonInfoBo.setPracticeCount(paper.getPracticeCount());

		// 场景数据查询
		if (lesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode().intValue()
				&& StringUtils.isNotBlank(lesson.getTopoId())) {
			JSONObject topoData = remoteTopoService.getTopoById(lesson.getTopoId());
			lessonInfoBo.setSceneConfig(topoData);
		}

		// 视频和讲义数据处理
		lessonInfoBo.setVideoUrl(utilService.handlerFileUrl(lesson.getVideoUrl()));
		if (null != lesson.getHandoutType() && lesson.getHandoutType()
				.intValue() == CourseLesson.HandoutType.PDF.getCode().intValue()) {
			lessonInfoBo.setHandoutUrl(utilService.handlerFileUrl(lesson.getHandoutUrl()));
			lessonInfoBo.setHandoutName(StringUtils.substring(lesson.getHandoutUrl(),
					lesson.getHandoutUrl().lastIndexOf(File.separatorChar) + CommonConstant.ONE));
		}

		// word或者ppt讲义处理
		if (null != lesson.getHandoutType()
				&& (lesson.getHandoutType().intValue() == CourseLesson.HandoutType.DOC.getCode()
						.intValue()
						|| lesson.getHandoutType().intValue() == CourseLesson.HandoutType.PPT
								.getCode().intValue())
				&& StringUtils.isNotBlank(lesson.getHandoutUrl())) {
			lessonInfoBo.setHandoutUrl(utilService.officeUrl(lesson.getHandoutUrl(), lessonId));
			lessonInfoBo.setHandoutName(StringUtils.substring(lesson.getHandoutUrl(),
					lesson.getHandoutUrl().lastIndexOf(File.separatorChar) + CommonConstant.ONE));
		}

		// md讲义文件名称处理
		if (null != lesson.getHandoutType() && lesson.getHandoutType()
				.intValue() == CourseLesson.HandoutType.MD.getCode().intValue()
				&& StringUtils.isNotBlank(lesson.getOriginMarkdownFile())) {
			lessonInfoBo.setHandoutName(StringUtils.substring(lesson.getOriginMarkdownFile(),
					lesson.getOriginMarkdownFile().lastIndexOf(File.separatorChar)
							+ CommonConstant.ONE));
		}

		// 知识点数据转换
		lessonInfoBo.setKnowledge(JsonUtil.toJsonObjectArray(lesson.getKnowledge(), String.class));

		return lessonInfoBo;
	}

	@Override
	public CourseLesson addLesson(CourseLesson courseLesson, JSONObject ojSceneConfig,
			List<PaperPracticeContext> practiceList) {

		// 数据重复性校验
		courseValidateHandler.validateLessonNameExist(courseLesson.getCourseId(),
				courseLesson.getName());

		courseLesson.setHeat(CommonConstant.ZERO);
		courseLesson.setBuiltin(CommonConstant.FALSE);

		// 处理课后练习数据
		PaperContext paperContext = packagePaperContext(courseLesson, practiceList);

		// 处理课程目录数据
		courseLesson.setResourceId(IDGeneratorUtil.getID());
		LessonConfig lessonConfig = packageLessonConfig(courseLesson);

		// 场景数据新增
		if (courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode()
				&& null != ojSceneConfig) {
			String topoId = localSceneService.saveTopo(ojSceneConfig,
					LocalScene.BusinessCode.LESSON.getCode());
			courseLesson.setTopoId(topoId);
		}

		// 执行数据插入
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 课后练习新增或修改
				Paper paper = paperService.create(paperContext);

				// 课时新增或修改
				courseLesson.setPaperId(paper.getResourceId());
				courseLessonMapper.insert(courseLesson);

				// 课程目录数据新增
				lessonConfigService.save(lessonConfig);

			}
		});

		return courseLesson;
	}

	@Override
	public CourseLesson updateLesson(CourseLesson courseLesson, JSONObject ojSceneConfig,
			List<PaperPracticeContext> practiceList) {

		// 数据校验
		CourseLesson lessonExist = courseValidateHandler
				.validateLessonExist(courseLesson.getResourceId(), false);
		courseValidateHandler.validateLessonUpdateNameExist(lessonExist, courseLesson.getName());
		courseValidateHandler.validateLessonUpdateSceneExist(lessonExist);

		// 试卷数据
		PaperContext paperContext = packagePaperContext(courseLesson, practiceList);

		// 拓扑数据修改
		if (courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode()
				&& null != ojSceneConfig) {
			String topoId = localSceneService.saveTopo(ojSceneConfig, courseLesson.getTopoId());
			courseLesson.setTopoId(topoId);
		}

		// 课时目录修改
		LessonConfig lessonConfig = LessonConfig.builder().lessonId(courseLesson.getResourceId())
				.courseId(courseLesson.getCourseId()).name(courseLesson.getName()).build();

		Boolean updateFlag = !lessonExist.getName().equals(courseLesson.getName());
		// 课时留言theme_name修改
		Comment comment = new Comment();
		if (updateFlag) {
			Course course = courseMapper.selectById(courseLesson.getCourseId());
			String themeName = course.getName() + CommonConstant.LEFT_OBLIQUE_LINE
					+ courseLesson.getName();
			comment.setThemeName(themeName);
			comment.setBusinessType(Comment.CommentType.LESSON.getCode());
			comment.setBusinessId(courseLesson.getResourceId());
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				if (StringUtils.isBlank(courseLesson.getPaperId())) {
					paperService.update(paperContext);
				}
				if (updateFlag) {
					lessonConfigMapper.updateByCourseAndLesson(lessonConfig);
					commentMapper.updateComment(comment);
				}
				courseLessonMapper.updateById(courseLesson);

			}
		});

		return courseLesson;
	}

	/**
	 * 组装试卷数据
	 *
	 * @param courseLesson
	 * @param practiceList
	 * @return
	 */
	private PaperContext packagePaperContext(CourseLesson courseLesson,
			List<PaperPracticeContext> practiceList) {

		if (StringUtils.isNotBlank(courseLesson.getResourceId())) {

			// 试卷数据修改
			CourseLesson existLesson = courseLessonMapper.selectById(courseLesson.getResourceId());
			return PaperContext.builder().resourceId(existLesson.getPaperId())
					.practiceList(practiceList).build();
		} else {

			// 试卷初始数据组装
			return PaperContext.builder().description(courseLesson.getName())
					.name(courseLesson.getName()).isPublic(CommonConstant.TRUE)
					.practiceList(practiceList).type(Paper.PaperType.LESSON.getCode()).build();
		}
	}

	/**
	 * 组装课程目录数据
	 *
	 * @param courseLesson
	 * @return
	 */
	private LessonConfig packageLessonConfig(CourseLesson courseLesson) {

		// 课程目录数据组装
		AddLessonConfigContext configContext = AddLessonConfigContext.builder()
				.lessonId(courseLesson.getResourceId()).courseId(courseLesson.getCourseId())
				.nodeType(LessonConfig.NodeType.NODE_LESSON.getCode()).name(courseLesson.getName())
				.build();

		return lessonConfigService.packageLessonConfig(configContext);
	}

	@Override
	public IPage<AdminLessonListBo> pageByContext(Page page, LessonContext context, boolean isWeb) {

		IPage<CourseLesson> iPage = courseLessonMapper.pageByContext(page, context, isWeb);
		IPage<AdminLessonListBo> boIPage = iPage.convert(courseLesson -> {
			// 前台没有课程列表查询 不用管是否存在课堂场景 修改sceneShareMode
			AdminLessonListBo adminLessonListBo = BeanUtils.copy(courseLesson,
					AdminLessonListBo.class);

			// 附件数据相关
			if (StringUtils.isNotBlank(courseLesson.getAttachmentIds())) {
				List<AttachmentBo> attachmentBoList = attachmentService
						.selectByIds(courseLesson.getAttachmentIds(), isWeb);
				adminLessonListBo.setLessonFile(attachmentBoList);
			}

			// 文件相关处理
			if (StringUtils.isNotBlank(courseLesson.getHandoutUrl())
					&& courseLesson.getHandoutType().intValue() == CourseLesson.HandoutType.MD
							.getCode().intValue()) {
				adminLessonListBo.setHandoutUrl(courseLesson.getHandoutUrl());
			} else {
				adminLessonListBo
						.setHandoutUrl(utilService.handlerFileUrl(courseLesson.getHandoutUrl()));
			}
			adminLessonListBo.setVideoUrl(utilService.handlerFileUrl(courseLesson.getVideoUrl()));

			// 试卷相关
			Paper paper = paperMapper.selectById(courseLesson.getPaperId());
			adminLessonListBo.setPracticeCount(paper.getPracticeCount());
			adminLessonListBo.setPaperPublic(paper.getIsPublic());

			// 课程相关
			Course course = courseMapper.selectById(courseLesson.getCourseId());
			adminLessonListBo.setCourseName(course.getName());

			// 用户相关
			UserInfo userInfo = remoteUserService.getById(courseLesson.getCreateUserId());
			adminLessonListBo.setCreateUserName(null != userInfo ? userInfo.getName() : "");

			// 是否具有实验报告
			NoteUniqueContext uniqueContext = NoteUniqueContext.builder()
					.noteType(Note.NoteType.REPORT.getCode())
					.businessType(Note.NoteResourceType.LESSON.getCode())
					.businessId(courseLesson.getResourceId()).build();
			List<String> noteIds = noteMapper.selectByContext(uniqueContext);
			adminLessonListBo.setHasReport(CollectionUtils.isNotEmpty(noteIds));
			return adminLessonListBo;
		});
		return boIPage;
	}

	@Override
	public Boolean batchPublicPractice(List<String> resourceIds, Boolean isPublic) {
		List<CourseLesson> courseLessons = courseLessonMapper.selectBatchIds(resourceIds);

		List<String> paperIds = courseLessons.stream()
				.filter(courseLesson -> StringUtils.isNotBlank(courseLesson.getResourceId()))
				.map(CourseLesson::getPaperId).collect(Collectors.toList());

		// 试卷数据公开
		List<Paper> papers = StreamApiUtils.map(paperIds, paperId -> {
			Paper paper = new Paper();
			paper.setIsPublic(isPublic);
			paper.setResourceId(paperId);
			return paper;
		});

		// 课时数据公开
		List<CourseLesson> lessonList = StreamApiUtils.map(courseLessons, courseLesson -> {
			CourseLesson lesson = new CourseLesson();
			lesson.setResourceId(courseLesson.getResourceId());
			lesson.setPaperPublic(isPublic);
			return lesson;
		});

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				updateBatchById(lessonList);
				return paperService.updateBatchById(papers);
			}
		});
	}

	@Override
	public Boolean importLessons(String courseId, List<String> lessonIds) {

		// 课程数据校验
		Course course = courseValidateHandler.validateCourseExist(courseId);

		// 目录数组组装
		List<LessonConfig> lessonConfigs = Lists.newArrayList();
		List<CourseLesson> courseLessons = courseLessonMapper.selectBatchIds(lessonIds);
		for (CourseLesson courseLesson : courseLessons) {
			courseLesson.setId(null);
			courseLesson.setCourseId(course.getResourceId());
			courseLesson.setResourceId(IDGeneratorUtil.getID());

			AddLessonConfigContext configContext = AddLessonConfigContext.builder()
					.lessonId(courseLesson.getResourceId()).courseId(courseLesson.getCourseId())
					.name(courseLesson.getName())
					.nodeType(LessonConfig.NodeType.NODE_LESSON.getCode()).build();
			LessonConfig lessonConfig = lessonConfigService.packageLessonConfig(configContext);
			lessonConfigs.add(lessonConfig);
		}

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				lessonConfigService.saveBatch(lessonConfigs);
				return saveBatch(courseLessons);
			}
		});

	}

	@Override
	public EvaluateResult submitLessonPractice(SubmitAnswerContext answerContext,
			List<PracticeAnswerContext> answers, AuthContext authContext) {

		// 数据校验
		courseValidateHandler.validateCoursePermission(answerContext.getCourseId(), authContext);
		CourseLesson lesson = courseValidateHandler.validateLessonExist(answerContext.getLessonId(),
				authContext.getIsWeb());
		Paper paper = validatePaper(lesson, CommonConstant.TRUE);

		// 课后练习评估数据查询
		LessonPaperEvaluate paperEvaluate = lessonPaperEvaluateMapper
				.selectByUserAndLesson(answerContext.getUserId(), answerContext.getLessonId());
		if (null != paperEvaluate) {
			EvaluateResult evaluateResult = BeanUtils.copy(paperEvaluate, EvaluateResult.class);
			evaluateResult.setSolved(CommonConstant.TRUE);
			evaluateResult.setTotalScore(paper.getTotalScore());

			return evaluateResult;
		}

		List<PracticeSolvedBo> practiceSolvedBos = Lists.newArrayList();

		// 习题数据提交评估
		List<EvaluateResult> evaluateResultList = StreamApiUtils.map(answers, submitContext -> {

			// 查询理论习题数据并评估
			if (submitContext.getPracticeType().intValue() == PracticeCategory.PracticeType.THEORY
					.getCode().intValue()) {

				PracticeTheory practiceTheory = practiceTheoryRefService
						.selectByPracticeId(paper.getResourceId(), submitContext.getPracticeId());
				EvaluateResult evaluateResult = theoryEvaluateHandler.evaluateByBusiness(
						authContext.getUserId(), practiceTheory, submitContext.getSubmitAnswer());

				PracticeSolvedBo solvedBo = BeanUtils.copy(evaluateResult, PracticeSolvedBo.class);
				solvedBo.setUserAnswer(submitContext.getSubmitAnswer());
				practiceSolvedBos.add(solvedBo);
				return evaluateResult;

			} else {

				// 实操数据并评估
				PracticeOperation operation = practiceOperationRefService
						.selectByPracticeId(paper.getResourceId(), submitContext.getPracticeId());
				EvaluateResult evaluateResult = lessonOperationEvaluateHandler.evaluateByBusiness(
						authContext.getUserId(), operation, submitContext.getSubmitAnswer(),
						answerContext.getLessonId());

				PracticeSolvedBo solvedBo = BeanUtils.copy(evaluateResult, PracticeSolvedBo.class);
				solvedBo.setUserAnswer(submitContext.getSubmitAnswer());
				practiceSolvedBos.add(solvedBo);
				return evaluateResult;
			}
		});

		Double totalScore = StreamApiUtils.mapToDouble(evaluateResultList,
				EvaluateResult::getTotalScore);
		Double score = StreamApiUtils.mapToDouble(evaluateResultList, EvaluateResult::getScore);
		EvaluateResult evaluateResult = EvaluateResult.builder().solved(CommonConstant.TRUE)
				.score(score).totalScore(totalScore).build();

		LessonPaperEvaluate evaluate = BeanUtils.copy(evaluateResult, LessonPaperEvaluate.class);
		evaluate.setLessonId(answerContext.getLessonId());
		evaluate.setRecord(JsonUtil.toJsonString(practiceSolvedBos));
		evaluate.setSubmitUserId(authContext.getUserId());

		lessonPaperEvaluateMapper.insert(evaluate);

		return evaluateResult;
	}

	@Override
	public LessonPracticeBo practiceByLesson(String courseId, String lessonId,
			AuthContext authContext) {

		// 数据校验
		courseValidateHandler.validateCoursePermission(courseId, authContext);
		Course course = courseMapper.selectById(courseId);
		CourseLesson lesson = courseValidateHandler.validateLessonExist(lessonId,
				authContext.getIsWeb());
		Paper paper = validatePaper(lesson, authContext.getIsWeb());

		LessonPracticeBo lessonPracticeBo = new LessonPracticeBo();

		// 计算评估结果
		LessonPaperEvaluate paperEvaluate = lessonPaperEvaluateMapper
				.selectByUserAndLesson(authContext.getUserId(), lessonId);
		EvaluateResult evaluateResult = EvaluateResult.builder().solved(null != paperEvaluate)
				.resourceId(lessonId).totalScore(paper.getTotalScore())
				.score(null != paperEvaluate ? paperEvaluate.getScore() : CommonConstant.ZERO)
				.build();
		lessonPracticeBo.setEvaluateResult(evaluateResult);

		// 处理习题数据
		Map<String, LessonPracticeListBo> handlerPractice = handlerPractice(paperEvaluate, paper,
				null != paperEvaluate, authContext, lessonId);
		lessonPracticeBo.setPracticeList(handlerPractice);

		// 计算课时基本数据
		LessonBriefBo lessonBriefBo = new LessonBriefBo();
		lessonBriefBo.setCourseId(course.getResourceId());
		lessonBriefBo.setCourseName(course.getName());
		lessonBriefBo.setLessonId(lesson.getResourceId());
		lessonBriefBo.setLessonName(lesson.getName());
		lessonPracticeBo.setLessonInfo(lessonBriefBo);

		return lessonPracticeBo;
	}

	@Override
	public List<AdminLessonPracticeBo> adminLessonPractice(String courseId, String lessonId,
			AuthContext authContext) {
		// 数据校验
		courseValidateHandler.validateCoursePermission(courseId, authContext);
		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId,
				authContext.getIsWeb());

		Paper paper = paperMapper.selectById(courseLesson.getPaperId());
		List<TheoryLessonBo> theoryLessonBos = practiceTheoryRefService
				.selectByPaper(paper.getResourceId(), true);
		List<OperationLessonBo> operationLessonBos = practiceOperationRefService
				.selectByPaperId(lessonId, paper.getResourceId(), true, true, authContext);

		List<AdminLessonPracticeBo> practiceBos = StreamApiUtils.map(theoryLessonBos,
				theoryLessonBo -> {
					AdminLessonPracticeBo practiceBo = BeanUtils.copy(theoryLessonBo,
							AdminLessonPracticeBo.class);
					practiceBo.setTitle(practiceBo.getContent());
					return practiceBo;
				});
		List<AdminLessonPracticeBo> practiceBoList = StreamApiUtils.map(operationLessonBos,
				operationLessonBo -> BeanUtils.copy(operationLessonBo,
						AdminLessonPracticeBo.class));
		practiceBos.addAll(practiceBoList);

		return practiceBos;
	}

	@Override
	public Boolean addLessonPractice(String lessonId, List<PaperPracticeContext> practiceList) {
		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId, false);

		PaperContext paperContext = packagePaperContext(courseLesson, practiceList);
		Paper paper = paperService.update(paperContext);
		return null != paper;
	}

	@Override
	public TopoSceneDataBo getTopoScene(String lessonId, AuthContext authContext) {

		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (null == courseLesson) {
			log.error("getTopoScene: lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		TopoSceneDataBo topoSceneDataRes = new TopoSceneDataBo();
		if (courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode()
				&& StringUtils.isNotBlank(courseLesson.getTopoId())) {

			// 拓扑信息查询
			JSONObject topoData = remoteTopoService.getTopoById(courseLesson.getTopoId());
			topoSceneDataRes.setSceneConfigData(topoData);

			// 前台需要先查询课堂场景
			OjLocalScene ojLocalScene = null;
			if (authContext.getIsWeb()) {
				ClassroomGroupInfo groupInfo = classroomGroupInfoService
						.selectByLessonAndUser(lessonId, authContext);
				if (null != groupInfo) {
					ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
				}
			}

			// 查询课时场景
			if (null == ojLocalScene) {
				ojLocalScene = ojLocalSceneService.selectOjLocalScene(courseLesson.getResourceId(),
						courseLesson.getSceneShareMode(), authContext);
			}

			if (null != ojLocalScene) {
				JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
				topoSceneDataRes.setSceneData(sceneData);
			}
		}

		return topoSceneDataRes;
	}

	@Override
	public void uploadHandout(CourseLesson courseLesson, MultipartFile handout) {

		if (null == handout) {
			return;
		}

		FileUtil.validateFileSuffix(handout.getOriginalFilename(),
				FileTypeContant.LESSON_HANDOUT_TYPE);
		String suffix = FileUtil.getFileSuffix(handout.getOriginalFilename());

		// zip可能为md 或者 html
		if (suffix.equals(FileSunffixConstant.ZIP.getDesc())) {

			// 文件上传至服务器 原始文件地址存储
			String markdownPath = String.format(AttachmentPath.MARKDOWN.getDesc(),
					courseLesson.getCourseId());
			AttachmentBo attachmentBo = attachmentService.uploadDest(markdownPath, false, handout);
			courseLesson.setOriginMarkdownFile(attachmentBo.getFilePath());

			// 获取讲义文件存储地址
			String handoutPath = String.format(AttachmentPath.HANDOUT.getDesc(),
					courseLesson.getCourseId());
			String realPath = utilService.getRealPath(attachmentBo.getFilePath());

			// 计算要解压的文件地址
			String unzipPath = handoutPath + FileUtil.getFileName(realPath, false);
			ZipUtil.unzipToDest(realPath, unzipPath);

			// 获取解压的讲义文件
			String mdFilePath = FileUtil.getOneFile(unzipPath, FileSunffixConstant.MD.getDesc());
			courseLesson.setHandoutType(CourseLesson.HandoutType.MD.getCode());

			// md文件内容作为url
			String content = MdUtil.parseMarkdown(mdFilePath);
			courseLesson.setHandoutUrl(utilService.replaceMarkdownFile(content));
		}

		// pdf讲义文件上传
		if (suffix.equals(FileSunffixConstant.PDF.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.PDF.getCode());
			courseLesson.setHandoutUrl(uploadHandout(courseLesson.getCourseId(), handout));
		}

		// word文档
		if (suffix.equals(FileSunffixConstant.DOC.getDesc())
				|| suffix.equals(FileSunffixConstant.DOCX.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.DOC.getCode());
			courseLesson.setHandoutUrl(uploadHandout(courseLesson.getCourseId(), handout));
		}

		// ppt文档
		if (suffix.equals(FileSunffixConstant.PPT.getDesc())
				|| suffix.equals(FileSunffixConstant.PPTX.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.PPT.getCode());
			courseLesson.setHandoutUrl(uploadHandout(courseLesson.getCourseId(), handout));
		}

	}

	@Override
	public void uploadVideo(CourseLesson courseLesson, MultipartFile video) {

		if (null == video) {
			return;
		}

		String videoPath = String.format(AttachmentPath.VIDEO.getDesc(),
				courseLesson.getCourseId());
		AttachmentBo attachmentBo = attachmentService.uploadDest(videoPath, false, video);
		courseLesson.setVideoUrl(attachmentBo.getFilePath());
	}

	@Override
	public Integer batchDelete(String courseId, List<String> resourceIds, AuthContext authContext) {

		AuthContext webAuth = BeanUtils.copy(authContext, AuthContext.class);
		webAuth.setIsWeb(true);

		courseValidateHandler.validateCourseExist(courseId);

		List<Classroom> classrooms = classroomMapper.selectByLesson(resourceIds);
		List<String> classroomIds = StreamApiUtils.map(classrooms, Classroom::getResourceId);

		// 运行场景
		List<CourseLesson> lessonList = courseLessonMapper.selectBatchIds(resourceIds);
		List<CourseLesson> experimentList = StreamApiUtils.filter(lessonList,
				lessonInfo -> lessonInfo.getType().intValue() == CourseLesson.LessonType.EXPERIMENT
						.getCode().intValue() && StringUtils.isNotBlank(lessonInfo.getTopoId()));

		List<OjLocalScene> adminOjLocalScenes = StreamApiUtils.map(experimentList,
				lessonInfo -> ojLocalSceneService.selectOjLocalScene(lessonInfo.getResourceId(),
						lessonInfo.getSceneShareMode(), authContext));

		List<OjLocalScene> webOjLocalScenes = StreamApiUtils.map(experimentList,
				lessonInfo -> ojLocalSceneService.selectOjLocalScene(lessonInfo.getResourceId(),
						lessonInfo.getSceneShareMode(), webAuth));
		adminOjLocalScenes.addAll(webOjLocalScenes);

		// 收藏
		List<String> collectionIds = lessonList.stream()
				.map(lessonInfo -> resourceCollectionMapper.selectByBusiness(null,
						lessonInfo.getResourceId(),
						ResourceCollection.BusinessType.LESSON.getCode()))
				.flatMap(List::stream).collect(Collectors.toList());

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {

				// 笔记和评论
				resourceIds.forEach(lessonId -> commentMapper.deleteByBusinessId(lessonId));
				resourceIds.forEach(lessonId -> noteMapper.deleteByBusiness(lessonId, null,
						Note.NoteResourceType.LESSON.getCode()));
				// 收藏
				if (CollectionUtils.isNotEmpty(collectionIds)) {
					resourceCollectionMapper.deleteBatchIds(collectionIds);
				}

				// 课堂相关
				classroomIds.forEach(
						classroomId -> classroomService.deleteClassroom(classroomId, authContext));

				// 课后练习
				lessonList.forEach(courseLesson -> {
					List<String> practiceRefs = practiceOperationRefMapper
							.selectByPaper(courseLesson.getPaperId());
					practiceRefs.forEach(practiceId -> lessonPracticeSceneService
							.deleteScene(courseLesson.getResourceId(), practiceId, authContext));
					practiceOperationRefMapper.deleteByPaper(courseLesson.getPaperId());
					practiceTheoryRefMapper.deleteByPaper(courseLesson.getPaperId());
				});

				// 目录和场景
				lessonConfigMapper.deleteByLessonIds(resourceIds);
				adminOjLocalScenes.forEach(ojLocalScene -> ojLocalSceneService
						.deleteLocalAndRemoteScene(ojLocalScene));

				return courseLessonMapper.deleteBatchIds(resourceIds);
			}
		});
	}

	@Override
	public LessonDumpLoadBo handlerLessonDump(CourseLesson courseLesson,
			Map<String, String> filePathMap, String destFilePath) {
		LessonDumpLoadBo dumpLoadBo = BeanUtils.copy(courseLesson, LessonDumpLoadBo.class);

		// markdown源文件copy
		if (StringUtils.isNotBlank(courseLesson.getOriginMarkdownFile())) {
			String fileName = copyFile(courseLesson.getOriginMarkdownFile(), destFilePath);
			filePathMap.put(courseLesson.getOriginMarkdownFile(), fileName);
		}

		// 视频文件copy
		if (StringUtils.isNotBlank(courseLesson.getVideoUrl())) {
			String fileName = copyFile(courseLesson.getVideoUrl(), destFilePath);
			filePathMap.put(courseLesson.getVideoUrl(), fileName);
		}

		// 课时附件
		if (StringUtils.isNotBlank(courseLesson.getAttachmentIds())) {
			List<AttachmentBo> attachmentBoList = attachmentService
					.selectByIds(courseLesson.getAttachmentIds(), false);
			List<String> filePathList = StreamApiUtils.map(attachmentBoList,
					AttachmentBo::getFilePath);
			dumpLoadBo.setFilePathList(filePathList);

			// 文件拷贝
			attachmentBoList.forEach(attachmentBo -> {
				String fileName = copyFile(attachmentBo.getFilePath(), destFilePath);
				filePathMap.put(attachmentBo.getFilePath(), fileName);
			});
		}

		// 讲义文件
		if (null != courseLesson.getHandoutType() && courseLesson.getHandoutType()
				.intValue() != CourseLesson.HandoutType.MD.getCode().intValue()
				&& StringUtils.isNotBlank(courseLesson.getHandoutUrl())) {
			String fileName = copyFile(courseLesson.getHandoutUrl(), destFilePath);
			filePathMap.put(courseLesson.getHandoutUrl(), fileName);
		}

		// 理论课后练习习题数据导出
		List<String> theoryRefIds = practiceTheoryRefMapper
				.selectByPaper(courseLesson.getPaperId());
		if (CollectionUtils.isNotEmpty(theoryRefIds)) {
			List<PracticeTheoryRef> theoryRefList = practiceTheoryRefMapper
					.selectBatchIds(theoryRefIds);

			List<PracticeTheoryDumpLoadBo> theoryDumpLoadBos = StreamApiUtils.map(theoryRefList,
					practiceTheoryRef -> {
						PracticeTheory practiceTheory = JsonUtil.toJsonObject(
								practiceTheoryRef.getSnapshot(), PracticeTheory.class);
						return practiceTheoryService.dumpTheory(practiceTheory);
					});
			dumpLoadBo.setTheoryPractice(theoryDumpLoadBos);
		}

		// 实操课后练习题数据导出
		List<String> operationRefIds = practiceOperationRefMapper
				.selectByPaper(courseLesson.getPaperId());
		if (CollectionUtils.isNotEmpty(operationRefIds)) {
			List<PracticeOperationRef> operationRefs = practiceOperationRefMapper
					.selectBatchIds(operationRefIds);
			List<PracticeOperationDumpLoadBo> dumpLoadBos = StreamApiUtils.map(operationRefs,
					practiceOperationRef -> {
						PracticeOperation practiceOperation = JsonUtil.toJsonObject(
								practiceOperationRef.getSnapshot(), PracticeOperation.class);
						return practiceOperationService.handlerDumpOperation(practiceOperation,
								destFilePath, filePathMap);
					});
			dumpLoadBo.setOperationPractice(dumpLoadBos);
		}

		// 场景数据导出
		if (courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode()
				.intValue() && StringUtils.isNotBlank(courseLesson.getTopoId())) {
			JSONObject topoData = remoteTopoService.exportTopo(courseLesson.getTopoId());
			dumpLoadBo.setTopoConfig(topoData);
		}

		return dumpLoadBo;
	}

	@Override
	public CourseLesson loadLesson(String courseId, LessonDumpLoadBo lessonDumpLoadBo,
			String filePath, Map<String, String> fileMap) {

		CourseLesson lesson = BeanUtils.copy(lessonDumpLoadBo, CourseLesson.class);
		lesson.setCourseId(courseId);
		lesson.setResourceId(IDGeneratorUtil.getID());
		lesson.setBuiltin(CommonConstant.TRUE);
		lesson.setHeat(CommonConstant.ZERO);
		String lessonId = courseLessonMapper.selectByCourseAndName(courseId,
				lessonDumpLoadBo.getName());

		// 已存在该课时 则更新
		if (StringUtils.isNotBlank(lessonId)) {
			lesson.setResourceId(lessonId);
			CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
			lesson.setPaperId(courseLesson.getPaperId());
		}

		// 附件处理
		if (CollectionUtils.isNotEmpty(lessonDumpLoadBo.getFilePathList())) {
			List<String> attachmentIds = StreamApiUtils.map(lessonDumpLoadBo.getFilePathList(),
					attachmentPath -> {
						String relative = fileMap.get(attachmentPath);
						String realPath = filePath + File.separatorChar + relative;

						String fileSuffix = FileUtil.getFileSuffix(relative);
						String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD
								+ fileSuffix;
						String destFilePath = AttachmentPath.ATTACHMENT.getDesc()
								+ File.separatorChar + fileName;
						FileUtil.copyFile(realPath, destFilePath, false);

						String fileUrl = utilService.replaceFileUrl(destFilePath);
						AttachmentBo attachmentBo = attachmentService.create(fileName, fileUrl);
						return attachmentBo.getResourceId();
					});
			lesson.setAttachmentIds(String.join(CommonConstant.CODE_COMMA, attachmentIds));
		}

		// 视频
		if (StringUtils.isNotBlank(lessonDumpLoadBo.getVideoUrl())) {
			String videoStorePath = String.format(AttachmentPath.VIDEO.getDesc(), courseId);
			String fileUrl = loadLessonCopyFile(lessonDumpLoadBo.getVideoUrl(), videoStorePath,
					fileMap, filePath);
			lesson.setVideoUrl(fileUrl);
		}

		// 讲义
		if (null != lessonDumpLoadBo.getHandoutType() && lessonDumpLoadBo
				.getHandoutType() != CourseLesson.HandoutType.MD.getCode().intValue()
				&& StringUtils.isNotBlank(lessonDumpLoadBo.getHandoutUrl())) {
			String handoutStorePath = String.format(AttachmentPath.HANDOUT.getDesc(), courseId);
			String fileUrl = loadLessonCopyFile(lessonDumpLoadBo.getHandoutUrl(), handoutStorePath,
					fileMap, filePath);
			lesson.setHandoutUrl(fileUrl);
		}

		// markdown讲义修改
		if (null != lessonDumpLoadBo.getHandoutType() && lessonDumpLoadBo
				.getHandoutType() == CourseLesson.HandoutType.MD.getCode().intValue()
				&& StringUtils.isNotBlank(lessonDumpLoadBo.getHandoutUrl())) {

			List<String> matchList = LocalStringUtil.getMatchStr(lessonDumpLoadBo.getHandoutUrl(),
					PatternConstant.MARKDOWN_IMAGE);
			List<String> matchPathList = StreamApiUtils.map(matchList, matchPath -> LocalStringUtil
					.getFirstMatchStr(matchPath, PatternConstant.MARKDOWN_IMAGE_URL));
			List<String> urlList = StreamApiUtils.map(matchPathList, match -> {
				String fileUrl = loadLessonCopyFile(match, AttachmentPath.ATTACHMENT.getDesc(),
						fileMap, filePath);
				return utilService.handlerFileUrl(fileUrl);
			});

			String content = LocalStringUtil.replaceAll(lessonDumpLoadBo.getHandoutUrl(), matchList,
					urlList);
			lesson.setHandoutUrl(content);
		}

		// markdown源文件
		if (StringUtils.isNotBlank(lessonDumpLoadBo.getOriginMarkdownFile())) {
			String format = String.format(AttachmentPath.MARKDOWN.getDesc(), courseId);
			String fileUrl = loadLessonCopyFile(lessonDumpLoadBo.getOriginMarkdownFile(), format,
					fileMap, filePath);
			lesson.setOriginMarkdownFile(fileUrl);
		}

		// 场景数据
		if (lessonDumpLoadBo.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode()
				.intValue() && null != lessonDumpLoadBo.getTopoConfig()) {
			String topoId = localSceneService.saveTopo(lessonDumpLoadBo.getTopoConfig(),
					LocalScene.BusinessCode.LESSON.getCode());
			lesson.setTopoId(topoId);
		}

		return lesson;
	}

	/**
	 * 加载课时练习拷贝文件
	 * @param dataPath
	 * @param storePath
	 * @return
	 */
	private String loadLessonCopyFile(String dataPath, String storePath,
			Map<String, String> filePathMap, String filePath) {
		String relative = filePathMap.get(dataPath);
		String realPath = filePath + File.separatorChar + relative;

		String fileSuffix = FileUtil.getFileSuffix(realPath);
		String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD + fileSuffix;
		String destFile = storePath + fileName;

		FileUtil.copyFile(realPath, destFile, false);
		return utilService.replaceFileUrl(destFile);
	}

	/**
	 * 文件拷贝 返回文件名称
	 * @param storeDbPath
	 * @param destFilePath
	 * @return
	 */
	private String copyFile(String storeDbPath, String destFilePath) {
		String realPath = utilService.getRealPath(storeDbPath);
		String fileSuffix = FileUtil.getFileSuffix(realPath);
		String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD + fileSuffix;
		String markdownFilePath = destFilePath + File.separatorChar + fileName;

		FileUtil.copyFile(realPath, markdownFilePath, false);

		return fileName;
	}

	/**
	 * 前台习题数据处理
	 *
	 * @param paperEvaluate
	 * @param paper
	 * @return
	 */
	private Map<String, LessonPracticeListBo> handlerPractice(LessonPaperEvaluate paperEvaluate,
			Paper paper, Boolean viewAnswer, AuthContext authContext, String lessonId) {

		Map<String, PracticeSolvedBo> solvedBoMap = Maps.newHashMap();
		if (null != paperEvaluate) {
			// 获取解题记录
			List<PracticeSolvedBo> solvedBos = JsonUtil.toJsonObjectArray(paperEvaluate.getRecord(),
					PracticeSolvedBo.class);
			solvedBoMap.putAll(StreamApiUtils.toMap(solvedBos, PracticeSolvedBo::getResourceId,
					practiceSolvedBo -> practiceSolvedBo));
		}

		// 查询习题内容 转换数据类型
		List<OperationLessonBo> operationLessonBos = practiceOperationRefService
				.selectByPaperId(lessonId, paper.getResourceId(), viewAnswer, false, authContext);

		// 实操习题转换
		List<LessonOperationBo> lessonOperationBos = StreamApiUtils.map(operationLessonBos,
				operationBo -> {
					LessonOperationBo lessonOperationBo = BeanUtils.copy(operationBo,
							LessonOperationBo.class);
					lessonOperationBo
							.setResolvedDetail(solvedBoMap.get(operationBo.getResourceId()));
					return lessonOperationBo;
				});

		// 理论习题转换
		List<TheoryLessonBo> theoryLessonBos = practiceTheoryRefService
				.selectByPaper(paper.getResourceId(), viewAnswer);
		List<LessonTheoryBo> theoryBoList = StreamApiUtils.map(theoryLessonBos, theoryLessonBo -> {
			LessonTheoryBo theoryBo = BeanUtils.copy(theoryLessonBo, LessonTheoryBo.class);
			theoryBo.setResolvedDetail(solvedBoMap.get(theoryBo.getResourceId()));
			return theoryBo;
		});

		// 理论习题数据分组
		Map<Integer, List<LessonTheoryBo>> practiceMap = StreamApiUtils.groupBy(theoryBoList,
				TheoryLessonBo::getQuestionType);

		// 新建map存储数据
		Map<Integer, List<? extends TheoryLessonBo>> allMap = Maps.newHashMap();
		allMap.putAll(practiceMap);
		if (CollectionUtils.isNotEmpty(lessonOperationBos)) {
			allMap.put(PracticeTheory.TheoryType.OPERATION.getCode(), lessonOperationBos);
		}

		// 组装习题数据
		Map<String, LessonPracticeListBo> listBoMap = Maps.newHashMap();
		allMap.forEach((key, value) -> {
			Double totalScore = StreamApiUtils.mapToDouble(value, TheoryLessonBo::getScore);
			LessonPracticeListBo practiceListBo = new LessonPracticeListBo();
			practiceListBo.setPracticeBos(value);
			practiceListBo.setCount(value.size());
			practiceListBo.setTotalScore(totalScore);
			listBoMap.put(EnumUtils.getEnumDescByCode(key, PracticeTheory.TheoryType.class),
					practiceListBo);
		});

		return listBoMap;
	}

	/**
	 * 试卷数据校验
	 *
	 * @return
	 */
	private Paper validatePaper(CourseLesson lesson, Boolean isWeb) {

		Paper paper = paperMapper.selectById(lesson.getPaperId());
		if (isWeb && !paper.getIsPublic()) {
			log.error("The paper was not made public！lesson_id=[{}], paper_id=[{}]",
					lesson.getResourceId(), lesson.getPaperId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		return paper;
	}

	/**
	 * 讲义文件上传
	 * @param courseId
	 * @param handout
	 * @return
	 */
	private String uploadHandout(String courseId, MultipartFile handout) {
		String handoutPath = String.format(AttachmentPath.HANDOUT.getDesc(), courseId);
		AttachmentBo attachmentBo = attachmentService.uploadDest(handoutPath, false, handout);
		return attachmentBo.getFilePath();
	}

	@Override
	public void createMpLog(LessonInfoBo lessonInfoBo) {
		String operationTarget = lessonInfoBo.getCourseName() + "-" + lessonInfoBo.getName();
		String content = LogContent.COURSE_FORMAT.format(lessonInfoBo.getCourseName(), lessonInfoBo.getName());
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.COURSE,
				OperationType.STUDY, operationTarget, content);
	}
}
