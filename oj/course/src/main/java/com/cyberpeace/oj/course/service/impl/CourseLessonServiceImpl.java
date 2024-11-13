package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.LessonPracticeSceneService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationRefMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryRefMapper;
import com.cyberpeace.oj.practice.service.*;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.impl.LessonOperationEvaluateHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 课程课Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
@Slf4j
public class CourseLessonServiceImpl extends ServiceImpl<CourseLessonMapper, CourseLesson> implements CourseLessonService {

	/**
	 * 课记录Mapper
	 */
	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	/**
	 * 课程课Mapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 附件Service
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 资源收集Service
	 */
	@Autowired
	private ResourceCollectionService resourceCollectionService;

	/**
	 * 资源收集Mapper
	 */
	@Autowired
	private ResourceCollectionMapper resourceCollectionMapper;

	/**
	 * 课程Mapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课配置Service
	 */
	@Autowired
	private LessonConfigService lessonConfigService;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 纸Service
	 */
	@Autowired
	private PaperService paperService;

	/**
	 * 纸Mapper
	 */
	@Autowired
	private PaperMapper paperMapper;

	/**
	 * 课纸评估Mapper
	 */
	@Autowired
	private LessonPaperEvaluateMapper lessonPaperEvaluateMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 理论评估处理器
	 */
	@Resource(name = "theory")
	private PracticeEvaluateHandler theoryEvaluateHandler;

	/**
	 * 课实操评估处理器
	 */
	@Resource(name = "lesson_operation")
	private LessonOperationEvaluateHandler lessonOperationEvaluateHandler;

	/**
	 * 实践理论裁判Service
	 */
	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	/**
	 * 实践理论裁判Mapper
	 */
	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	/**
	 * 实践实操裁判Mapper
	 */
	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	/**
	 * 实践实操裁判Service
	 */
	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * OJ当地场景Mapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 注意Mapper
	 */
	@Autowired
	private NoteMapper noteMapper;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * OJ当地场景Service
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 课程验证处理器
	 */
	@Autowired
	private CourseValidateHandler courseValidateHandler;

	/**
	 * 课配置Mapper
	 */
	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	/**
	 * 评论Mapper
	 */
	@Autowired
	private CommentMapper commentMapper;

	/**
	 * 课堂Service
	 */
	@Autowired
	private ClassroomService classroomService;

	/**
	 * 课堂Mapper
	 */
	@Autowired
	private ClassroomMapper classroomMapper;

	/**
	 * 课实践场景Service
	 */
	@Autowired
	private LessonPracticeSceneService lessonPracticeSceneService;

	/**
	 * 实践理论Service
	 */
	@Autowired
	private PracticeTheoryService practiceTheoryService;

	/**
	 * 实践实操Service
	 */
	@Autowired
	private PracticeOperationService practiceOperationService;

	/**
	 * 远程日志Service
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 远程组织Service
	 */
	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	/**
	 * 课堂群信息Mapper
	 */
	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	/**
	 * 获取课详情
	 *
	 * @param courseId    课程id
	 * @param lessonId    课id
	 * @param roomId      房间id
	 * @param learnedType 学习类型
	 * @param authContext auth 上下文
	 * @return {@code LessonInfoBo }
	 */
	@Override
	public LessonInfoBo getLessonDetail(String courseId, String lessonId, String roomId, Integer learnedType, AuthContext authContext) {

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

		String orgId = authContext.getOrganization();
		infoBo.setPaperPublic(
				null != paper.getReadAuthIds() && paper.getReadAuthIds().contains(orgId));

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

		// 判断聊天框前端是否应该显示
		ClassroomGroupInfo groupInfo = null;
		if (StringUtils.isNotBlank(roomId)) {
			Classroom classroom = classroomMapper.selectById(roomId);
			if (null != classroom) {
				groupInfo = classroomGroupInfoMapper.selectByRoomAndUser(roomId,
						authContext.getUserId());
				Date current = new Date();
				if ((null != groupInfo || lesson.getType()
						.intValue() == CourseLesson.LessonType.EXPERIMENT.getCode().intValue())
						&& current.after(classroom.getStartTime())
						&& current.before(classroom.getEndTime())) {
					infoBo.setRoomId(roomId);
				}
			}
		}

		// 查询课时场景信息
		if (lesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode().intValue()
				&& StringUtils.isNotBlank(lesson.getTopoId())) {

			// 拓扑信息查询
			infoBo.setSceneConfigData(remoteTopoService.getTopoById(lesson.getTopoId()));

			// 前台需要先查询课堂场景
			OjLocalScene ojLocalScene = null;
			if (authContext.getIsWeb()) {
				if (null != groupInfo) {
					infoBo.setSceneShareMode(SceneShare.GROUP.getCode());
					infoBo.setGroupId(groupInfo.getResourceId());
					// 存在课堂场景 则设置group_id用于前端简历websocket
					ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
				}
			}

			// 查询课时场景
			if (null == ojLocalScene && StringUtils.isBlank(roomId)) {
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
			lessonRecordMapper.updateLearnedTime(lessonId, courseId, authContext.getUserId(), learnedType, StringUtils.isBlank(roomId) ? "" : roomId);
			// 代表数据库已存在学习记录 不做任何处理
		}

		return infoBo;
	}

	/**
	 * 查询由课程和课
	 *
	 * @param courseId 课程id
	 * @param lessonId 课id
	 * @return {@code AdminLessonBo }
	 */
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
		long practiceCount = getPracticeCount(lesson.getPaperId());
		lessonInfoBo.setPracticeCount(Integer.parseInt(String.valueOf(practiceCount)));

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

		// office讲义处理
		if (null != lesson.getHandoutType()
				&& CourseLesson.HandoutType.isOfficeFile(lesson.getHandoutType())
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

	/**
	 * 新增课
	 *
	 * @param courseLesson  课程课程
	 * @param ojSceneConfig OJ场景配置
	 * @param practiceList  执业名单
	 * @return {@code CourseLesson }
	 */
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

	/**
	 * 更新课程
	 *
	 * @param courseLesson  课程课程
	 * @param ojSceneConfig OJ场景配置
	 * @param practiceList  执业名单
	 * @return {@code CourseLesson }
	 */
	@Override
	public CourseLesson updateLesson(CourseLesson courseLesson, JSONObject ojSceneConfig,
			List<PaperPracticeContext> practiceList) {

		// 数据校验
		CourseLesson lessonExist = courseValidateHandler
				.validateLessonExist(courseLesson.getResourceId(), false);
		courseValidateHandler.validateLessonUpdateNameExist(lessonExist, courseLesson.getName());
		courseValidateHandler.validateLessonUpdateSceneExist(lessonExist, ojSceneConfig);

		// 试卷数据
		PaperContext paperContext = packagePaperContext(courseLesson, practiceList);

		// 拓扑数据修改
		int lessonType = courseLesson.getType().intValue();
		if (lessonType == CourseLesson.LessonType.THEORY.getCode()) {
			if (StringUtils.isNotBlank(lessonExist.getTopoId())) {
				remoteTopoService.deleteById(lessonExist.getTopoId());
				courseLesson.setTopoId(CommonConstant.EMPTY_STRING);
			}
		} else {
			if (StringUtils.isNotBlank(lessonExist.getTopoId())) {
				String topoId = localSceneService.saveTopo(ojSceneConfig, lessonExist.getTopoId());
				courseLesson.setTopoId(topoId);
			} else {
				String topo = localSceneService.saveTopo(ojSceneConfig,
						LocalScene.BusinessCode.LESSON.getCode());
				courseLesson.setTopoId(topo);
			}
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

	/**
	 * 分页由上下文
	 *
	 * @param page    分页
	 * @param context 上下文
	 * @param isWeb   是 Web
	 * @return {@code IPage<AdminLessonListBo> }
	 */
	@Override
	public IPage<AdminLessonListBo> pageByContext(Page page, LessonContext context, boolean isWeb) {

		IPage<CourseLesson> iPage = courseLessonMapper.pageByContext(page, context, isWeb, context.getType());

		List<CourseLesson> records = iPage.getRecords();
		List<String> paperIds = StreamApiUtils.getColumn(records, CourseLesson::getPaperId);
		LambdaQueryWrapper<PracticeOperationRef> filterOperation = new LambdaQueryWrapper<>();
		LambdaQueryWrapper<PracticeTheoryRef> filterTheory = new LambdaQueryWrapper<>();
		if(!paperIds.isEmpty()){
			filterOperation.in(PracticeOperationRef::getPaperId, paperIds);
			filterTheory.in(PracticeTheoryRef::getPaperId, paperIds);
		}
		List<PracticeOperationRef> practiceOperationRefs = practiceOperationRefMapper.selectList(filterOperation);
		Map<String, List<PracticeOperationRef>> operationMap = StreamApiUtils.groupBy(practiceOperationRefs, PracticeOperationRef::getPaperId);
		List<PracticeTheoryRef> practiceTheoryRefs = practiceTheoryRefMapper.selectList(filterTheory);
		Map<String, List<PracticeTheoryRef>> theoryMap = StreamApiUtils.groupBy(practiceTheoryRefs, PracticeTheoryRef::getPaperId);
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

			String paperId = courseLesson.getPaperId();
			List<PracticeOperationRef> operations = operationMap.getOrDefault(paperId, new ArrayList<>());
			List<PracticeTheoryRef> theories = theoryMap.getOrDefault(paperId, new ArrayList<>());
			adminLessonListBo.setPracticeCount(operations.size() + theories.size());
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
			adminLessonListBo.setHasReport(
					courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT
							.getCode() && CollectionUtils.isNotEmpty(noteIds));
			return adminLessonListBo;
		});
		return boIPage;
	}

	/**
	 * 获取实践计数
	 *
	 * @param paperId 纸id
	 * @return long
	 */
	private long getPracticeCount(String paperId) {
		LambdaQueryWrapper<PracticeOperationRef> filterOperation = new LambdaQueryWrapper<>();
		filterOperation.eq(PracticeOperationRef::getPaperId, paperId);
		LambdaQueryWrapper<PracticeTheoryRef> filterTheory = new LambdaQueryWrapper<>();
		filterTheory.eq(PracticeTheoryRef::getPaperId, paperId);
		List<PracticeOperationRef> practiceOperationRefs = practiceOperationRefMapper.selectList(filterOperation);
		List<PracticeTheoryRef> practiceTheoryRefs = practiceTheoryRefMapper.selectList(filterTheory);
		long operationCount = practiceOperationRefs.stream().filter(practiceOperationRef -> practiceOperationRef.getPaperId().equals(paperId)).count();
		long theoryCount = practiceTheoryRefs.stream().filter(practiceOperationRef -> practiceOperationRef.getPaperId().equals(paperId)).count();
		long practiceCount = operationCount + theoryCount;
		return practiceCount;
	}

	/**
	 * 批量公共实践
	 *
	 * @param resourceIds 资源id列表
	 * @param isPublic    是否公开
	 * @return {@code Boolean }
	 */
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

	/**
	 * 导入课程
	 *
	 * @param courseId  课程id
	 * @param lessonIds 课id列表
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean importLessons(String courseId, List<String> lessonIds) {

		// 课程数据校验
		Course course = courseValidateHandler.validateCourseExist(courseId);
		List<CourseLesson> courseLessons = courseLessonMapper.selectBatchIds(lessonIds);
		if (CollectionUtils.isEmpty(courseLessons)) {
			log.error("error lessonIds [{}]", lessonIds);
			throw new BusinessException(OjBusinessErrorCode.NOT_SELECT_LESSON_INFORMATION);
		}

		List<LessonConfig> lessonConfigs = Lists.newArrayList();
		for (CourseLesson courseLesson : courseLessons) {
			// 课时数据校验
			courseValidateHandler.validateLessonNameExist(courseId, courseLesson.getName());
			// 重新初始化课时
			reinitLesson(courseLesson, course.getResourceId());
			// 目录数组组装
			lessonConfigs.add(initLessonConfig(courseLesson));
		}
		Set<String> lessonNameSet = courseLessons.stream().map(CourseLesson::getName).collect(Collectors.toSet());
		if (courseLessons.size() != lessonNameSet.size()) {
			log.error("repeat import same lesson name [{}]", StreamApiUtils.getColumn(courseLessons, CourseLesson::getName).toString());
			throw new BusinessException(OjBusinessErrorCode.REPEAT_IMPORT_SAME_LESSON_NAME);
		}

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				lessonConfigService.saveBatch(lessonConfigs);
				return saveBatch(courseLessons);
			}
		});

	}

	/**
	 * 初始化课程目录
	 *
	 * @param courseLesson 课程课程
	 * @return {@code LessonConfig }
	 */
	private LessonConfig initLessonConfig(CourseLesson courseLesson) {
		AddLessonConfigContext configContext = AddLessonConfigContext.builder()
				.lessonId(courseLesson.getResourceId()).courseId(courseLesson.getCourseId())
				.name(courseLesson.getName()).nodeType(LessonConfig.NodeType.NODE_LESSON.getCode())
				.build();
		return lessonConfigService.packageLessonConfig(configContext);
	}

	/**
	 * 重新初始化课时
	 *
	 * @param lesson   课
	 * @param courseId 课程id
	 */
	private void reinitLesson(CourseLesson lesson, String courseId) {
		lesson.setId(null);
		lesson.setCourseId(courseId);
		lesson.setResourceId(IDGeneratorUtil.getID());
	}

	/**
	 * 提交课程练习
	 *
	 * @param answerContext 答案上下文
	 * @param answers       答案
	 * @param authContext   auth 上下文
	 * @return {@code EvaluateResult }
	 */
	@Override
	public EvaluateResult submitLessonPractice(SubmitAnswerContext answerContext,
			List<PracticeAnswerContext> answers, AuthContext authContext) {

		// 数据校验
		courseValidateHandler.validateCoursePermission(answerContext.getCourseId(), authContext);
		CourseLesson lesson = courseValidateHandler.validateLessonExist(answerContext.getLessonId(),
				authContext.getIsWeb());
		Paper paper = validatePaper(lesson, CommonConstant.TRUE);

		//获取主观题数量（理论题的填空和简答）
		List<TheoryLessonBo> theoryLessonBos = practiceTheoryRefService
				.selectByPaper(paper.getResourceId(), false);
		List<LessonTheoryBo> subjectTheoryBoList = StreamApiUtils.map(theoryLessonBos, theoryLessonBo -> {
			LessonTheoryBo theoryBo = BeanUtils.copy(theoryLessonBo, LessonTheoryBo.class);
			if(theoryBo.getQuestionType().intValue() == PracticeTheory.TheoryType.FILL_BLANK
					.getCode().intValue() || theoryBo.getQuestionType().intValue() == PracticeTheory.TheoryType.SHORT_ANSWER
					.getCode().intValue() ){
				return theoryBo;

			}
			return null;
		});

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

		AtomicInteger correctObjectNum = new AtomicInteger();
		Integer totalObjectNum = paper.getPracticeCount() - subjectTheoryBoList.size();
		// 习题数据提交评估
		List<EvaluateResult> evaluateResultList = StreamApiUtils.map(answers, submitContext -> {

			// 查询理论习题数据并评估
			if (submitContext.getPracticeType().intValue() == PracticeCategory.PracticeType.THEORY
					.getCode().intValue()) {

				PracticeTheory practiceTheory = practiceTheoryRefService
						.selectByPracticeId(paper.getResourceId(), submitContext.getPracticeId());

				boolean isObject = practiceTheory.getTheoryType()
						.intValue() != PracticeTheory.TheoryType.FILL_BLANK.getCode().intValue()
						&& practiceTheory.getTheoryType()
								.intValue() != PracticeTheory.TheoryType.SHORT_ANSWER.getCode()
										.intValue();

				EvaluateResult evaluateResult = new EvaluateResult();
				if (isObject || submitContext.getNeedEvaluate()) {
					evaluateResult = theoryEvaluateHandler.evaluateByBusiness(
							authContext.getUserId(), practiceTheory,
							submitContext.getSubmitAnswer());
				} else {
					// 主观题不再判题
					evaluateResult.setResourceId(practiceTheory.getResourceId());
					evaluateResult.setScore(CommonConstant.ZERO.doubleValue());
					evaluateResult.setTotalScore(CommonConstant.ZERO.doubleValue());
					evaluateResult.setSolved(false);
				}

				PracticeSolvedBo solvedBo = BeanUtils.copy(evaluateResult, PracticeSolvedBo.class);
				solvedBo.setUserAnswer(submitContext.getSubmitAnswer());
				practiceSolvedBos.add(solvedBo);

				// 客观题统计
				if (isObject) {
					if (solvedBo.getSolved()) {
						correctObjectNum.addAndGet(1);
					}
				}

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

				if (solvedBo.getSolved()) {
					correctObjectNum.addAndGet(1);
				}
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

		// 准确率计算
		String accuracy = correctObjectNum + "/" + totalObjectNum;
		evaluate.setAccuracy(accuracy);

		lessonPaperEvaluateMapper.insert(evaluate);

		return evaluateResult;
	}

	/**
	 * 逐节课练习
	 *
	 * @param courseId    课程id
	 * @param lessonId    课id
	 * @param authContext auth 上下文
	 * @return {@code LessonPracticeBo }
	 */
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

		// 统计习题数据
		PracticeBriefBo practiceBriefBo = new PracticeBriefBo();
		if (null != paperEvaluate) {
			practiceBriefBo.setLevel(paperEvaluate.getLevel());
			practiceBriefBo.setAccuracy(paperEvaluate.getAccuracy());
		}

		int objectCount = 0, subjectCount = 0, totalCount = 0;
		for (Map.Entry entry : handlerPractice.entrySet()) {
			LessonPracticeListBo val = (LessonPracticeListBo) entry.getValue();
			if (entry.getKey() == "fill_blank" || entry.getKey() == "short_answer") {
				subjectCount += val.getCount();
			} else {
				objectCount += val.getCount();
			}
			totalCount += val.getCount();
		}
		practiceBriefBo.setObjectCount(objectCount);
		practiceBriefBo.setSubjectCount(subjectCount);
		practiceBriefBo.setTotalCount(totalCount);
		lessonPracticeBo.setPracticeInfo(practiceBriefBo);

		// 计算课时基本数据
		LessonBriefBo lessonBriefBo = new LessonBriefBo();
		lessonBriefBo.setCourseId(course.getResourceId());
		lessonBriefBo.setCourseName(course.getName());
		lessonBriefBo.setLessonId(lesson.getResourceId());
		lessonBriefBo.setLessonName(lesson.getName());
		lessonPracticeBo.setLessonInfo(lessonBriefBo);

		return lessonPracticeBo;
	}

	/**
	 * 管理员课程练习
	 *
	 * @param courseId    课程id
	 * @param lessonId    课id
	 * @param authContext auth 上下文
	 * @return {@code List<AdminLessonPracticeBo> }
	 */
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

	/**
	 * 新增课实践
	 *
	 * @param lessonId     课id
	 * @param practiceList 执业名单
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean addLessonPractice(String lessonId, List<PaperPracticeContext> practiceList) {
		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId, false);

		PaperContext paperContext = packagePaperContext(courseLesson, practiceList);
		Paper paper = paperService.update(paperContext);
		return null != paper;
	}

	/**
	 * 标记课实践
	 *
	 * @param lessonId    课id
	 * @param userId      用户id
	 * @param level       水平
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean markLessonPractice(String lessonId, String userId, Integer level,
			AuthContext authContext) {
		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId, false);

		// 课后练习评估数据查询
		LessonPaperEvaluate paperEvaluate = lessonPaperEvaluateMapper.selectByUserAndLesson(userId,
				lessonId);
		if (null == paperEvaluate) {

			return false;
		}
		paperEvaluate.setLevel(level);
		paperEvaluate.setCorrector(authContext.getUserId());
		lessonPaperEvaluateMapper.updateById(paperEvaluate);
		return true;

	}

	/**
	 * 发布课程练习
	 *
	 * @param lessonId    课id
	 * @param authId      认证id
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean releaseLessonPractice(String lessonId, String authId, AuthContext authContext) {
		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId, false);

		// 下发课堂练习
		Paper paper = paperMapper.selectById(courseLesson.getPaperId());
		String readAuthIds = paper.getReadAuthIds();
		if (StringUtils.isBlank(readAuthIds)) {
			readAuthIds = "[]";
		}

		List<String> orgIds = remoteOrganizationService.childrenList(authId, true);
		Set<String> orgIdSet = new HashSet<>(orgIds);

		JSONArray readAuthArray = JsonUtil.stringToArray(readAuthIds);
		orgIdSet.addAll(readAuthArray.toJavaList(String.class));
		List<String> readAuthList = Lists.newArrayList(orgIdSet);
		paper.setReadAuthIds(JsonUtil.arrayToString(readAuthList));
		paperMapper.updateById(paper);

		return true;

	}

	/**
	 * 上传讲义
	 *
	 * @param courseLesson 课程课程
	 * @param handout      讲义
	 */
	@Override
	public void uploadHandout(CourseLesson courseLesson, String handout) {

		if (null == handout) {
			return;
		}

		String[] split = handout.split("\\.");
		String suffix = split[split.length-1];

		// zip可能为md 或者 html
		if (suffix.equals(FileSunffixConstant.ZIP.getDesc())) {

			// 文件上传至服务器 原始文件地址存储
			courseLesson.setOriginMarkdownFile(handout);

			// 获取讲义文件存储地址
			String handoutPath = String.format(AttachmentPath.HANDOUT.getDesc(),
					courseLesson.getCourseId());
			String realPath = handout;

			// 计算要解压的文件地址
			String unzipPath = handoutPath + FileUtil.getFileName(realPath, false);
			ZipUtil.unzipToDest(handout, unzipPath);

			// 获取解压的讲义文件
			String mdFilePath = FileUtil.getOneFile(unzipPath, FileSunffixConstant.MD.getDesc());
			if (StringUtils.isBlank(mdFilePath)) {
				log.error("error lesson handout content [{}]", unzipPath);
				throw new BusinessException(OjBusinessErrorCode.LESSON_HANDOUT_CONTENT_ERROR);
			}
			courseLesson.setHandoutType(CourseLesson.HandoutType.MD.getCode());

			// md文件内容作为url
			String content = MdUtil.parseMarkdown(mdFilePath);
			courseLesson.setHandoutUrl(utilService.replaceMarkdownFile(content));
		}

		// pdf讲义文件上传
		if (suffix.equals(FileSunffixConstant.PDF.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.PDF.getCode());
			courseLesson.setHandoutUrl(handout);
		}

		// word文档
		if (suffix.equals(FileSunffixConstant.DOC.getDesc())
				|| suffix.equals(FileSunffixConstant.DOCX.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.DOC.getCode());
			courseLesson.setHandoutUrl(handout);
		}

		// ppt文档
		if (suffix.equals(FileSunffixConstant.PPT.getDesc())
				|| suffix.equals(FileSunffixConstant.PPTX.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.PPT.getCode());
			courseLesson.setHandoutUrl(handout);
		}

		// xls文档
		if (suffix.equals(FileSunffixConstant.XLS.getDesc())
				|| suffix.equals(FileSunffixConstant.XLSX.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.XLS.getCode());
			courseLesson.setHandoutUrl(handout);
		}

	}

	/**
	 * 上传视频
	 *
	 * @param courseLesson 课程课程
	 * @param video        视频
	 */
	@Override
	public void uploadVideo(CourseLesson courseLesson, String video) {

		if (null != video) {
			courseLesson.setVideoUrl(video);
		}
	}

	/**
	 * 批量删除
	 *
	 * @param courseId    课程id
	 * @param resourceIds 资源id列表
	 * @param authContext auth 上下文
	 * @return {@code Integer }
	 */
	@Override
	public Integer batchDelete(String courseId, List<String> resourceIds, AuthContext authContext) {

		courseValidateHandler.validateCourseExist(courseId);

		List<Classroom> classrooms = classroomMapper.selectByLesson(resourceIds);
		List<String> classroomIds = StreamApiUtils.map(classrooms, Classroom::getResourceId);

		// 运行场景
		List<CourseLesson> lessonList = courseLessonMapper.selectBatchIds(resourceIds);
		CourseSceneContext sceneContext = this.selectCourseScene(lessonList, authContext);

		// 收藏
		List<String> collectionIds = lessonList.stream()
				.map(lessonInfo -> resourceCollectionMapper.selectByBusiness(null,
						lessonInfo.getResourceId(),
						ResourceCollection.BusinessType.LESSON.getCode()))
				.flatMap(List::stream).collect(Collectors.toList());

		transactionTemplate.execute(new TransactionCallback<Integer>() {

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
				batchDeleteClassroom(classroomIds, authContext);

				// 课后练习
				lessonList.forEach(courseLesson -> {
					practiceOperationRefMapper.deleteByPaper(courseLesson.getPaperId());
					practiceTheoryRefMapper.deleteByPaper(courseLesson.getPaperId());
				});

				// 目录和场景
				lessonConfigMapper.deleteByLessonIds(resourceIds);
				return courseLessonMapper.deleteBatchIds(resourceIds);
			}
		});
		// 删除课件和视频
		String prefix = ConfigUtil.getContextProperty("cr.local.file.prefix");
		for (CourseLesson lesson : lessonList) {
			String handoutUrl = lesson.getHandoutUrl();
			String videoUrl = lesson.getVideoUrl();
			this.deleteAttachment(handoutUrl, prefix);
			this.deleteAttachment(videoUrl, prefix);
		}

		this.clearSceneAndTopos(sceneContext.getScenes(), sceneContext.getTopos());
		return resourceIds.size();
	}

	/**
	 * 批量删除
	 *
	 * @param resourceIds 资源id列表
	 */
	@Override
	public void batchDelete(List<String> resourceIds) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		// 运行场景
		List<CourseLesson> lessonList = courseLessonMapper.selectBatchIds(resourceIds);
		CourseSceneContext sceneContext = this.selectCourseScene(lessonList, authContext);


		transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {

				// 笔记和评论
				// 收藏

				// 课堂相关

				// 课后练习

				// 目录和场景
				return courseLessonMapper.deleteBatchIds(resourceIds);
			}
		});
		// 删除课件和视频
		String prefix = ConfigUtil.getContextProperty("cr.local.file.prefix");
		for (CourseLesson lesson : lessonList) {
			String handoutUrl = lesson.getHandoutUrl();
			String videoUrl = lesson.getVideoUrl();
			this.deleteAttachment(handoutUrl, prefix);
			this.deleteAttachment(videoUrl, prefix);
		}
		this.clearSceneAndTopos(sceneContext.getScenes(), sceneContext.getTopos());
	}

	/**
	 * 删除附件
	 *
	 * @param url        url
	 * @param pathPrefix 路径前缀
	 */
	private void deleteAttachment(String url, String pathPrefix) {
		if (StringUtils.isBlank(url) || url.contains(CommonConstant.SPACE)
				|| url.contains(CommonConstant.RETURN)) {
			return;
		}
		if (!url.startsWith(pathPrefix)) {
			url = url + pathPrefix;
		}
		try {
			File toDelete = new File(url);
			if (toDelete.exists()) {
				FileUtil.deleteDir(toDelete);
			}
		} catch (Exception e) {
			log.error("failed to delete [{}]", url, e);
		}
	}

	/**
	 * 批量删除课程表
	 *
	 * @param classroomIds
	 * @param authContext  auth 上下文
	 */
	private void batchDeleteClassroom(List<String> classroomIds, AuthContext authContext) {

		List<ClassroomGroupInfo> groupInfos = StreamApiUtils.flatMap(classroomIds,
				classroomId -> classroomGroupInfoMapper.selectByRoom(classroomId));
		List<String> groupIds = StreamApiUtils.map(groupInfos, ClassroomGroupInfo::getResourceId);
		if (CollectionUtils.isEmpty(groupIds)) {
			return;
		}
		
		// 课程表场景删除 防止课程表无法删除
		List<OjLocalScene> scenes = ojLocalSceneMapper.selectByGroupIds(groupIds);
		scenes.forEach(ojLocalScene -> ojLocalSceneService.deleteLocalAndRemoteScene(ojLocalScene));

		for (String classroomId : classroomIds) {
			classroomService.deleteClassroom(classroomId, authContext);
		}
	}

	/**
	 * 查询课程下的所有场景
	 *
	 * @param lessonList
	 * @param authContext auth 上下文
	 * @return
	 */
	private CourseSceneContext selectCourseScene(List<CourseLesson> lessonList,
			AuthContext authContext) {
		if (CollectionUtils.isEmpty(lessonList)) {
			return new CourseSceneContext();
		}

		// 课时场景
		List<OjLocalScene> scenes = this.selectLessonAllScene(lessonList, authContext);
		// topo查询
		List<String> topos = this.selectLessonAllTopo(lessonList);
		return new CourseSceneContext(scenes, topos);
	}

	/**
	 * 查询课都拓扑
	 *
	 * @param lessonList 课程列表
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> selectLessonAllTopo(List<CourseLesson> lessonList) {
		List<String> topos = new ArrayList<>();

		List<PracticeOperation> operations = StreamApiUtils.flatMap(lessonList,
				lesson -> practiceOperationRefService.operationByPaper(lesson.getPaperId(), true));
		List<String> lessonPracticeTopo = StreamApiUtils.map(operations,
				operation -> operation.getTopoId());

		List<String> lessonTopo = StreamApiUtils.map(lessonList,
				lesson -> StringUtils.isNotBlank(lesson.getTopoId()) ? lesson.getTopoId() : null);
		topos.addAll(lessonPracticeTopo);
		topos.addAll(lessonTopo);
		return topos;
	}

	/**
	 * 清楚场景和拓扑
	 *
	 * @param scenes 场景
	 * @param topos  拓扑
	 */
	@Override
	public void clearSceneAndTopos(List<OjLocalScene> scenes, List<String> topos) {
		// 场景删除
		if (CollectionUtils.isNotEmpty(scenes)) {
			for (OjLocalScene scene : scenes) {
				try {
					// 删不掉算了
					ojLocalSceneService.deleteLocalAndRemoteScene(scene);
				} catch (Exception e) {
					log.error(
							"Lesson scene delete failed! oj_local_scene_id=[{}], lesson_id=[{}], catch error:",
							scene.getResourceId(), scene.getBusinessId(), e);
					continue;
				}
			}
		}
		
		// 拓扑删除
		if (CollectionUtils.isNotEmpty(topos)) {
			for (String topo : topos) {
				try {
					remoteTopoService.deleteById(topo);
				} catch (Exception e) {
					log.error("Lesson topo delete failed! topo_id=[{}], catch error:", topo, e);
				}
			}
		}
	}

	/**
	 * 第 2 课 管理员 bo
	 *
	 * @param lessonList 课程列表
	 * @return {@code List<AdminLessonListBo> }
	 */
	@Override
    public List<AdminLessonListBo> lesson2AdminBo(List<CourseLesson> lessonList) {

		List<AdminLessonListBo> res = Lists.newArrayList();
		if (lessonList.isEmpty()) {
			return res;
		}
		List<String> paperIds = StreamApiUtils.getColumn(lessonList, CourseLesson::getPaperId);
		LambdaQueryWrapper<PracticeOperationRef> filterOperation = new LambdaQueryWrapper<>();
		LambdaQueryWrapper<PracticeTheoryRef> filterTheory = new LambdaQueryWrapper<>();
		if (!paperIds.isEmpty()) {
			filterOperation.in(PracticeOperationRef::getPaperId, paperIds);
			filterTheory.in(PracticeTheoryRef::getPaperId, paperIds);
		}
		List<PracticeOperationRef> practiceOperationRefs = practiceOperationRefMapper
				.selectList(filterOperation);
		Map<String, List<PracticeOperationRef>> operationMap = StreamApiUtils
				.groupBy(practiceOperationRefs, PracticeOperationRef::getPaperId);
		List<PracticeTheoryRef> practiceTheoryRefs = practiceTheoryRefMapper
				.selectList(filterTheory);
		Map<String, List<PracticeTheoryRef>> theoryMap = StreamApiUtils.groupBy(practiceTheoryRefs,
				PracticeTheoryRef::getPaperId);
		List<String> userIds = StreamApiUtils.map(lessonList, CourseLesson::getCreateUserId);
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(userIds);
		List<String> courseIds = StreamApiUtils.map(lessonList, CourseLesson::getCourseId);
		List<Course> courseList = courseMapper.selectBatchIds(courseIds);
		Map<String, Course> courseMap = StreamApiUtils.toMap(courseList, Course::getResourceId,
				c -> c);
		List<Paper> papers = paperMapper.selectBatchIds(paperIds);
		Map<String, Paper> paperMap = StreamApiUtils.toMap(papers, Paper::getResourceId, p -> p);
		for (CourseLesson courseLesson : lessonList) {
			// 前台没有课程列表查询 不用管是否存在课堂场景 修改sceneShareMode
			AdminLessonListBo adminLessonListBo = BeanUtils.copy(courseLesson,
					AdminLessonListBo.class);

			// 附件数据相关
			if (StringUtils.isNotBlank(courseLesson.getAttachmentIds())) {
				List<AttachmentBo> attachmentBoList = attachmentService
						.selectByIds(courseLesson.getAttachmentIds(), false);
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
			Paper paper = paperMap.get(courseLesson.getPaperId());

			String paperId = courseLesson.getPaperId();
			List<PracticeOperationRef> operations = operationMap.getOrDefault(paperId,
					new ArrayList<>());
			List<PracticeTheoryRef> theories = theoryMap.getOrDefault(paperId, new ArrayList<>());
			adminLessonListBo.setPracticeCount(operations.size() + theories.size());
			adminLessonListBo.setPracticeCount(paper.getPracticeCount());
			adminLessonListBo.setPaperPublic(paper.getIsPublic());

//			 课程相关
			Course course = courseMap.get(courseLesson.getCourseId());
			adminLessonListBo.setCourseName(course.getName());

			// 用户相关
			adminLessonListBo.setCreateUserName(
					userNameMap.getOrDefault(courseLesson.getCreateUserId(), ""));

			res.add(adminLessonListBo);
		}
		return res;
    }

	/**
	 * 查询课都场景
	 *
	 * @param lessonList  课程列表
	 * @param authContext auth 上下文
	 * @return {@code List<OjLocalScene> }
	 */
	@Override
	public List<OjLocalScene> selectLessonAllScene(List<CourseLesson> lessonList, AuthContext authContext) {
		List<OjLocalScene> scenes = new ArrayList<>();
		// 查询课时场景
		List<OjLocalScene> lessonScene = this.lessonSceneByLessons(lessonList, authContext);
		scenes.addAll(lessonScene);

		// 查询课后练习场景 课后练习场景仅前台存在
		AuthContext web = BeanUtils.copy(authContext, AuthContext.class);
		web.setIsWeb(true);
		List<OjLocalScene> lessonPracticeScene = this.lessonPracticeSceneByLessons(lessonList,
				web);
		scenes.addAll(lessonPracticeScene);
		
		return scenes;
	}

	/**
	 * 课场景由课
	 *
	 * @param lessonList  课程列表
	 * @param authContext auth 上下文
	 * @return {@code List<OjLocalScene> }
	 */
	@Override
	public List<OjLocalScene> lessonSceneByLessons(List<CourseLesson> lessonList, AuthContext authContext) {

		List<OjLocalScene> scenes = new ArrayList<>();
		AuthContext reverse = BeanUtils.copy(authContext, AuthContext.class);
		reverse.setIsWeb(!authContext.getIsWeb());
		
		List<CourseLesson> experimentList = StreamApiUtils.filter(lessonList,
				lessonInfo -> lessonInfo.getType().intValue() == CourseLesson.LessonType.EXPERIMENT
						.getCode().intValue() && StringUtils.isNotBlank(lessonInfo.getTopoId()));

		List<OjLocalScene> sceneList = StreamApiUtils.map(experimentList,
				lessonInfo -> ojLocalSceneService.selectOjLocalScene(lessonInfo.getResourceId(),
						lessonInfo.getSceneShareMode(), authContext));
		List<OjLocalScene> reverseSceneList = StreamApiUtils.map(experimentList,
				lessonInfo -> ojLocalSceneService.selectOjLocalScene(lessonInfo.getResourceId(),
						lessonInfo.getSceneShareMode(), reverse));
		
		scenes.addAll(sceneList);
		scenes.addAll(reverseSceneList);
		
		return scenes;
	}

	/**
	 * 课实践场景由课
	 *
	 * @param lessonList  课程列表
	 * @param authContext auth 上下文
	 * @return {@code List<OjLocalScene> }
	 */
	@Override
	public List<OjLocalScene> lessonPracticeSceneByLessons(List<CourseLesson> lessonList,
			AuthContext authContext) {
		return StreamApiUtils.flatMap(lessonList, lesson -> practiceOperationRefService
				.operationByPaper(lesson.getResourceId(), lesson.getPaperId(), authContext));
	}

	/**
	 * 处理程序类转储
	 *
	 * @param courseLesson 课程课程
	 * @param filePathMap  文件路径图
	 * @param destFilePath 目标文件路径
	 * @return {@code LessonDumpLoadBo }
	 */
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

		// markdown讲义修改
		if (null != courseLesson.getHandoutType()
				&& courseLesson.getHandoutType() == CourseLesson.HandoutType.MD.getCode().intValue()
				&& StringUtils.isNotBlank(courseLesson.getHandoutUrl())) {
			String content = MdUtil.filterCodeContent(courseLesson.getHandoutUrl());
			List<String> matchList = LocalStringUtil.getMatchStr(content,
					PatternConstant.MARKDOWN_IMAGE);
			List<String> matchPathList = StreamApiUtils.map(matchList, matchPath -> LocalStringUtil
					.getFirstMatchStr(matchPath, PatternConstant.MARKDOWN_IMAGE_URL));
			matchPathList.forEach(match -> {
				String fileName = copyFile(match, destFilePath);
				filePathMap.put(match, fileName);
			});
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
		List<PracticeOperationRef> operationRefs = practiceOperationRefMapper
				.selectByPaperId(courseLesson.getPaperId());
		if (CollectionUtils.isNotEmpty(operationRefs)) {
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

	/**
	 * 加载课程
	 *
	 * @param courseId         课程id
	 * @param lessonDumpLoadBo 课程 dump load bo
	 * @param filePath         文件路径
	 * @param fileMap          文件映射
	 * @return {@code CourseLesson }
	 */
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

			String filterContent = MdUtil.filterCodeContent(lessonDumpLoadBo.getHandoutUrl());
			List<String> matchList = LocalStringUtil.getMatchStr(filterContent,
					PatternConstant.MARKDOWN_IMAGE);
			List<String> matchPathList = StreamApiUtils.map(matchList, matchPath -> LocalStringUtil
					.getFirstMatchStr(matchPath, PatternConstant.MARKDOWN_IMAGE_URL));
			List<String> urlList = StreamApiUtils.map(matchPathList, match -> {
				String fileUrl = loadLessonCopyFile(match, AttachmentPath.ATTACHMENT.getDesc(),
						fileMap, filePath);
				return utilService.handlerFileUrl(fileUrl);
			});

			String content = LocalStringUtil.replaceAll(lessonDumpLoadBo.getHandoutUrl(), matchPathList,
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
	 *
	 * @param dataPath
	 * @param storePath
	 * @param filePathMap 文件路径图
	 * @param filePath    文件路径
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
	 *
	 * @param storeDbPath
	 * @param destFilePath
	 * @return
	 */
	private String copyFile(String storeDbPath, String destFilePath) {
		String realPath = utilService.getRealPath(storeDbPath);
		String fileSuffix = FileUtil.getFileSuffix(realPath);
		String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD + fileSuffix;
		String destPath = destFilePath + File.separatorChar + fileName;

		FileUtil.copyFile(realPath, destPath, false);

		return fileName;
	}

	/**
	 * 前台习题数据处理
	 *
	 * @param paperEvaluate
	 * @param paper
	 * @param viewAnswer    视图答案
	 * @param authContext   auth 上下文
	 * @param lessonId      课id
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
	 * @param lesson 课
	 * @param isWeb  是 Web
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
	 *
	 * @param courseId
	 * @param handout
	 * @return
	 */
	private String uploadHandout(String courseId, MultipartFile handout) {
		String handoutPath = String.format(AttachmentPath.HANDOUT.getDesc(), courseId);
		AttachmentBo attachmentBo = attachmentService.uploadDest(handoutPath, false, handout);
		return attachmentBo.getFilePath();
	}

	/**
	 * 创造中台日志
	 *
	 * @param lessonInfoBo 课程信息 bo
	 */
	@Override
	public void createMpLog(LessonInfoBo lessonInfoBo) {
		String operationTarget = lessonInfoBo.getCourseName() + "-" + lessonInfoBo.getName();
		String content = LogContent.COURSE_FORMAT.format(lessonInfoBo.getCourseName(),
				lessonInfoBo.getName());
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.COURSE,
				OperationType.STUDY, operationTarget, content);
	}
}
