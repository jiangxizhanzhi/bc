package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.*;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
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
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;
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
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.PracticeEvaluateHandler;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

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

	@Resource(name = "operation")
	private PracticeEvaluateHandler operationEvaluateHandler;

	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private ClassroomGroupInfoService classroomGroupInfoService;

	@Override
	public LessonInfoBo getLessonDetail(String courseId, String lessonId, AuthContext authContext) {

		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			log.error("getLessonDetail: Course not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		CourseLesson lesson = courseLessonMapper.selectById(lessonId);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

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
					ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
					infoBo.setSceneShareMode(SceneShare.GROUP.getCode());
				} else {
					ojLocalScene = ojLocalSceneService.selectOjLocalScene(lessonId,
							lesson.getSceneShareMode(), authContext);
				}
			} else {
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

		AdminLessonBo lessonInfoBo = BeanUtils.copy(lesson, AdminLessonBo.class);
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(lesson.getAttachmentIds(),
				false);
		lessonInfoBo.setLessonFile(attachmentBos);

		// 习题数据查询
		Paper paper = paperMapper.selectById(lesson.getPaperId());
		lessonInfoBo.setPracticeCount(paper.getPracticeCount());

		// 知识点数据转换
		lessonInfoBo.setKnowledge(JsonUtil.toJsonObjectArray(lesson.getKnowledge(), String.class));

		// topo数据组装
		if (lesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode().intValue()
				&& StringUtils.isNotBlank(lesson.getTopoId())) {
			JSONObject topo = remoteTopoService.getTopoById(lesson.getTopoId());
			lessonInfoBo.setSceneConfig(topo);
		}
		return lessonInfoBo;
	}

	@Override
	public CourseLesson addLesson(CourseLesson courseLesson, JSONObject ojSceneConfig,
			List<PaperPracticeContext> practiceList) {

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

		PaperContext paperContext = packagePaperContext(courseLesson, practiceList);

		if (courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode()
				&& null != ojSceneConfig) {
			String topoId = localSceneService.saveTopo(ojSceneConfig, courseLesson.getTopoId());
			courseLesson.setTopoId(topoId);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 试卷编号不为空 则进行实体更新操作
				if (StringUtils.isBlank(courseLesson.getPaperId())) {
					paperService.update(paperContext);
				}

				// 课时数据更新
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
			AdminLessonListBo adminLessonListBo = BeanUtils.copy(courseLesson,
					AdminLessonListBo.class);

			// 附件数据相关
			if (StringUtils.isNotBlank(courseLesson.getAttachmentIds())) {
				List<AttachmentBo> attachmentBoList = attachmentService
						.selectByIds(courseLesson.getAttachmentIds(), isWeb);
				adminLessonListBo.setLessonFile(attachmentBoList);
			}

			// 试卷相关
			Paper paper = paperMapper.selectById(courseLesson.getPaperId());
			adminLessonListBo.setPracticeCount(paper.getPracticeCount());
			adminLessonListBo.setPaperPublic(paper.getIsPublic());

			// 课程相关
			Course course = courseMapper.selectById(courseLesson.getCourseId());
			adminLessonListBo.setCourseName(course.getName());

			// 用户相关
			UserInfo userInfo = remoteUserService.getById(courseLesson.getCreateUserId());
			adminLessonListBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");

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

		List<Paper> papers = StreamApiUtils.map(paperIds, paperId -> {
			Paper paper = new Paper();
			paper.setIsPublic(isPublic);
			paper.setResourceId(paperId);
			return paper;
		});

		return paperService.updateBatchById(papers);
	}

	@Override
	public Boolean importLessons(String courseId, List<String> lessonIds) {
		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			log.error("Course not found! course_id=[{}]", courseId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

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
		validateCourse(answerContext.getCourseId(), authContext);
		CourseLesson lesson = validateLesson(answerContext.getLessonId(), CommonConstant.TRUE);
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
				EvaluateResult evaluateResult = operationEvaluateHandler.evaluateByBusiness(
						answerContext.getUserId(), operation, submitContext.getSubmitAnswer());

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
		Course course = validateCourse(courseId, authContext);
		CourseLesson lesson = validateLesson(lessonId, authContext.getIsWeb());
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
				null != paperEvaluate);
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
		validateCourse(courseId, authContext);
		CourseLesson courseLesson = validateLesson(lessonId, authContext.getIsWeb());

		Paper paper = paperMapper.selectById(courseLesson.getPaperId());
		List<TheoryLessonBo> theoryLessonBos = practiceTheoryRefService
				.selectByPaper(paper.getResourceId(), true);
		List<OperationLessonBo> operationLessonBos = practiceOperationRefService
				.selectByPaperId(paper.getResourceId(), true, true);

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
	public Boolean addLessonPractice(String resourceId, List<PaperPracticeContext> practiceList) {
		CourseLesson courseLesson = courseLessonMapper.selectById(resourceId);
		if (null == courseLesson) {
			log.error("addLessonPractice: lesson not found! lesson_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

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

			// topo数据查询
			JSONObject topo = remoteTopoService.getTopoById(courseLesson.getTopoId());
			topoSceneDataRes.setSceneConfigData(topo);

			// 场景数据查询
			OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(
					courseLesson.getResourceId(), courseLesson.getSceneShareMode(), authContext);
			if (null != ojLocalScene) {
				LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
				JSONObject sceneData = remoteSceneService.getById(localScene.getMpSceneId(),
						localScene.getUserId());
				topoSceneDataRes.setSceneData(sceneData);
			}
		}

		return topoSceneDataRes;
	}

	/**
	 * 前台习题数据处理
	 *
	 * @param paperEvaluate
	 * @param paper
	 * @return
	 */
	private Map<String, LessonPracticeListBo> handlerPractice(LessonPaperEvaluate paperEvaluate,
			Paper paper, Boolean viewAnswer) {

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
				.selectByPaperId(paper.getResourceId(), viewAnswer, false);

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
	 * 课程校验
	 *
	 * @param courseId
	 * @param authContext
	 */
	private Course validateCourse(String courseId, AuthContext authContext) {

		List<String> courseIds = courseMapper.selectByAuth(authContext);
		if (!courseIds.contains(courseId)) {
			log.error("Course No Permission！course_id=[{}]", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		return courseMapper.selectById(courseId);
	}

	/**
	 * 校验课时
	 *
	 * @param lessonId
	 * @param isWeb
	 */
	private CourseLesson validateLesson(String lessonId, Boolean isWeb) {
		CourseLesson lesson = courseLessonMapper.selectByPublic(lessonId, isWeb);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return lesson;
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
}
