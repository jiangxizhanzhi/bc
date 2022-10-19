package com.cyberpeace.oj.course.service.impl;

import java.io.File;
import java.util.*;

import com.cyberpeace.oj.course.dao.mapper.LessonConfigMapper;
import com.cyberpeace.oj.course.service.bo.CombinedCourseImportContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.*;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.bo.PaperContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourseImportServiceImpl implements CourseImportService {

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private LessonConfigService lessonConfigService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PaperService paperService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private UtilService utilService;

	@Value("${import.resource.cover}")
	private Boolean resourceCover;

	@Autowired
	private CourseValidateHandler courseValidateHandler;

	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	@Override
	public Course parseRmCourse(String path) {

		// 解析md文件 获取对应数据
		File markdownFile = new File(path, ResourceImportConstant.README_NAME);
		if (!markdownFile.exists() || markdownFile.isDirectory()) {
			log.error("Markdown file not found! path=[{}]", markdownFile.getAbsolutePath());
			throw new BusinessException(ErrorCode.FILE_READ_FAIL);
		}
		JSONObject markdownValue = MdUtil.parseJsonMarkdown(markdownFile);

		// 解析数据转换 由markdown的key-value转换为数据库对应数据
		JSONObject convertValue = JsonUtil.filedConvert(markdownValue,
				ResourceImportConstant.COURSE_DATA_MAPPING);
		Course course = jsonParseCourse(convertValue);

		// 课程存在性校验
//		courseValidateHandler.validateCourseNameExist(course.getName());
//		courseValidateHandler.validateCourseNumExist(course.getCourseNum());
		courseValidateHandler.validateCourseName2NumExist(course);

		// 用户修改
		course.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
		course.setModifyUserId(ResourceImportConstant.SUPER_ADMIN_HASH);

		// 课程分类数据查询或创建
		CourseCategory category = courseCategoryService.getOrCreate(course.getCategoryId());
		course.setCategoryId(category.getResourceId());

		// logo文件迁移
		File logoFile = new File(path, ResourceImportConstant.PNG_LOGO);
		if (!logoFile.exists()) {
			logoFile = new File(path, ResourceImportConstant.JPG_LOGO);
		}
		String dest = EnumUtils.getEnumDescByCode(AttachmentPath.LOGO.getCode(),
				AttachmentPath.class) + logoFile.getName();
		String destFilePath = FileUtil.copyFile(logoFile.getAbsolutePath(), dest, true);
		course.setLogo(destFilePath);

		// 课程大纲数据读取
		File outlineFile = new File(path, ResourceImportConstant.OUTLINE_NAME);
		if (outlineFile.exists()) {
			String content = FileUtil.getContent(outlineFile);
			course.setOutline(content);
		}

		// 课程其他数据初始化
		course.setCourseType(CourseCategory.CourseType.BASE.getCode());
		course.setBuiltin(CommonConstant.TRUE);
		course.setHeat(CommonConstant.ZERO);
		course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setReadAuthIds(JsonUtil.toJsonString(Lists.newArrayList()));
		course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setOperationAuthIds(JsonUtil.toJsonString(Lists.newArrayList()));
		course.setIsPublic(CommonConstant.FALSE);
		course.setScore(CommonConstant.ZERO);

		// 查询是否已存在课程数据 已存在则进行课时编号转换
		if (resourceCover) {
			Course existCourse = courseMapper.selectById(course.getResourceId());
			if (null != existCourse) {
				course.setResourceId(existCourse.getResourceId());
				return course;
			}

			// 根据名称查询
			String existId = courseMapper.selectByName(course.getName());
			Course existNameCourse = courseMapper.selectById(existId);
			course.setResourceId(existNameCourse.getResourceId());
			return course;
		}

		return course;
	}

	/**
	 * json数据转为课程 部分字段属性不对应实体类类型 单独处理
	 *
	 * @param jsonObject
	 * @return
	 */
	private Course jsonParseCourse(JSONObject jsonObject) {

		// 计算难度值
		String difficulty = (String) jsonObject.remove("difficulty");
		Integer code = EnumUtils.getEnumCodeByDesc(difficulty, Course.Difficulty.class);

		// 类型转换
		Course course = jsonObject.toJavaObject(Course.class);
		course.setDifficulty(code);

		return course;
	}

	@Override
	public List<CourseLesson> parseRmLesson(Course course, String path, Map<String, String> envReqs,
			Map<String, String> nameMap) {

		List<CourseLesson> allLessons = Lists.newArrayList();

		List<String> chapterPaths = FileUtil.listFileByFile(new File(path), 1, false);
		for (String chapter : chapterPaths) {
			File file = new File(chapter);
			// 镜像目录过滤
			if (file.isFile() || file.getName().equals(ResourceImportConstant.IMAGE_NAME)) {
				continue;
			}

			// 目录课程数据解析
			List<String> lessonPaths = FileUtil.listFileByFile(file, 1, true);
			List<CourseLesson> courseLessons = StreamApiUtils.map(lessonPaths, lessonPath -> {
				CourseLesson courseLesson = parseLesson(lessonPath, course, envReqs);
				if (null != courseLesson) {
					// 如果存在该课时，则替换resource_id
					CourseLesson existLesson = courseLessonMapper.selectCourseByCourseAndName(course.getResourceId(), courseLesson.getName());
					if (null != existLesson){
						courseLesson.setResourceId(existLesson.getResourceId());
						courseLesson.setPaperId(existLesson.getPaperId());


						if (courseLesson.getType() == CourseLesson.LessonType.EXPERIMENT.getCode().intValue()
								&& null == courseLesson.getTopoId()){
							courseLesson.setSceneShareMode(existLesson.getSceneShareMode());
							courseLesson.setDestroyDelay(existLesson.getDestroyDelay());
							courseLesson.setTopoId(existLesson.getTopoId());
						}
					}


					if (null != nameMap.putIfAbsent(courseLesson.getName(),
							courseLesson.getResourceId())) {
						log.error("Lesson name exist! course_path=[{}], lesson_path=[{}]", path,
								lessonPath);
						throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
					}

					return courseLesson;
				}
				return null;
			});
			allLessons.addAll(courseLessons);
		}

		if (resourceCover) {
			// 查询全部课时信息
			List<CourseLesson> courseLessons = courseLessonMapper
					.selectByCourse(course.getResourceId(), false);
			Map<String, String> lessonNameMap = StreamApiUtils.toMap(courseLessons,
					CourseLesson::getName, CourseLesson::getResourceId);

			// 已存在的替换为原来的课时编号
			for (CourseLesson lesson : allLessons) {
				String lessonId = lessonNameMap.get(lesson.getName());
				if (StringUtils.isNotBlank(lessonId)) {
					lesson.setResourceId(lessonId);
				}
			}
		}

		return allLessons;
	}

	/**
	 * 解析课时数据
	 *
	 * @param path
	 * @param course
	 * @return
	 */
	private CourseLesson parseLesson(String path, Course course, Map<String, String> envReqs) {

		// 解析课时数据
		File file = new File(path, ResourceImportConstant.README_NAME);
		if (!file.exists()) {
			return null;
		}
		JSONObject jsonMarkdown = MdUtil.parseJsonMarkdown(file);

		// 字段类型转换
		JSONObject filedConvert = JsonUtil.filedConvert(jsonMarkdown,
				ResourceImportConstant.LESSON_DATA_MAPPING);

		// 解析课时数据
		CourseLesson courseLesson = jsonParseLesson(filedConvert);

		// 解析附件
		List<String> attachmentList = FileUtil.listDirs(
				new File(path, ResourceImportConstant.ATTACHMENT).getAbsolutePath(), true);
		if (CollectionUtils.isNotEmpty(attachmentList)) {
			List<String> attachmentPaths = StreamApiUtils.map(attachmentList, attachmentPath -> {
				File attachmentFile = new File(attachmentPath);
				File destFile = new File(AttachmentPath.ATTACHMENT.getDesc(),
						attachmentFile.getName());
				String filePath = FileUtil.copyFile(attachmentPath, destFile.getAbsolutePath(),
						true);
				return utilService.replaceFileUrl(filePath);
			});

			List<String> attachmentIds = attachmentService.batchInsert(attachmentPaths);
			String idStr = String.join(CommonConstant.CODE_COMMA, attachmentIds);
			courseLesson.setAttachmentIds(idStr);
		}

		// 解析课时视频
		String videoPath = String.format(AttachmentPath.VIDEO.getDesc(), course.getResourceId());
		String video = FileUtil
				.getOneFile(new File(path, ResourceImportConstant.VIDEO).getAbsolutePath(), null);
		if (StringUtils.isNotBlank(video)) {
			String videoUrl = FileUtil.copyFile(video,
					videoPath + FileUtil.getFileName(video, true), true);
			courseLesson.setVideoUrl(videoUrl);
		}

		// 解析讲义
		parseLessonHandout(path, courseLesson, course.getResourceId());

		// 其他数据初始化
		courseLesson.setCourseId(course.getResourceId());
		courseLesson.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
		courseLesson.setIsPublic(CommonConstant.TRUE);
		courseLesson.setHeat(CommonConstant.ZERO);
		courseLesson.setBuiltin(CommonConstant.TRUE);
		// 修复课时类型理论变为实验时延时值为null异常
		courseLesson.setDestroyDelay(CommonConstant.TWO);
		// 是否具有环境地址
		if (new File(path, ResourceImportConstant.ENV).exists()) {
			courseLesson.setSceneShareMode(SceneShare.PRIVATE.getCode());
			courseLesson.setTopoId(envReqs.get(new File(path).getName()));
		}

		return courseLesson;
	}

	/**
	 * 课时数据处理
	 * @param lessonPath 课时目录
	 * @param courseLesson 课时数据
	 * @param courseId 课程编号
	 */
	private void parseLessonHandout(String lessonPath, CourseLesson courseLesson, String courseId) {

		// 解析课时讲义文件
		String handoutPath = courseLesson.getType().intValue() == CourseLesson.LessonType.THEORY
				.getCode().intValue()
						? new File(lessonPath, ResourceImportConstant.HANDOUT).getAbsolutePath()
						: new File(lessonPath, ResourceImportConstant.DOC).getAbsolutePath();
		String destHandoutPath = String.format(AttachmentPath.HANDOUT.getDesc(), courseId);

		List<String> handoutFileList = FileUtil.listDirs(handoutPath, true);

		// 讲义数据类型判断和文件处理
		String wordOrPptFile = null;
		String destWordOrPptFile = null;
		for (String handoutFile : handoutFileList) {
			if (!new File(handoutFile).isFile()) {
				continue;
			}

			String fileSuffix = FileUtil.getFileSuffix(handoutFile);
			if (fileSuffix.equals(FileSunffixConstant.MD.getDesc())) {
				courseLesson.setHandoutType(CourseLesson.HandoutType.MD.getCode());
			}
			if (fileSuffix.equals(FileSunffixConstant.DOC.getDesc())
					|| fileSuffix.equals(FileSunffixConstant.DOCX.getDesc())) {
				courseLesson.setHandoutType(CourseLesson.HandoutType.DOC.getCode());
				wordOrPptFile = handoutFile;
				destWordOrPptFile = destHandoutPath + File.separatorChar + IDGeneratorUtil.getID()
						+ CommonConstant.PERIOD + fileSuffix;
			}
			if (fileSuffix.equals(FileSunffixConstant.PPT.getDesc())
					|| fileSuffix.equals(FileSunffixConstant.PPTX.getDesc())) {
				courseLesson.setHandoutType(CourseLesson.HandoutType.PPT.getCode());
				wordOrPptFile = handoutFile;
				destWordOrPptFile = destHandoutPath + File.separatorChar + IDGeneratorUtil.getID()
						+ CommonConstant.PERIOD + fileSuffix;
			}
			if (fileSuffix.equals(FileSunffixConstant.PDF.getDesc())) {
				courseLesson.setHandoutType(CourseLesson.HandoutType.PDF.getCode());
				wordOrPptFile = handoutFile;
				destWordOrPptFile = destHandoutPath + File.separatorChar + IDGeneratorUtil.getID()
						+ CommonConstant.PERIOD + fileSuffix;
			}
		}

		// 无讲义怎不管
		if (courseLesson.getHandoutType() == null) {
			return;
		}

		// 执行文件复制和
		if (courseLesson.getHandoutType().intValue() == CourseLesson.HandoutType.MD.getCode()
				.intValue()) {
			// 目录拷贝
			String dest = new File(destHandoutPath, IDGeneratorUtil.getID()).getAbsolutePath();
			FileUtil.copyDirectory(handoutPath, dest, false);

			// 目录压缩设置原始markdown文件地址
			String markdownPath = String.format(AttachmentPath.MARKDOWN.getDesc(), courseId);
			String originUrl = ZipUtil.toZipDest(dest, markdownPath);
			courseLesson.setOriginMarkdownFile(originUrl);

			// 文件解析
			String handoutFilePath = courseLesson.getType()
					.intValue() == CourseLesson.LessonType.THEORY.getCode().intValue()
							? new File(dest, ResourceImportConstant.HANDOUT_FILE_NAME)
									.getAbsolutePath()
							: new File(dest, ResourceImportConstant.DOC_FILE_NAME)
									.getAbsolutePath();
			String markdown = MdUtil.parseMarkdown(handoutFilePath);
			markdown = utilService.replaceMarkdownFile(markdown);
			courseLesson.setHandoutUrl(markdown);
		} else {
			FileUtil.copyFile(wordOrPptFile, destWordOrPptFile, false);
			String fileUrl = utilService.replaceFileUrl(destWordOrPptFile);
			courseLesson.setHandoutUrl(fileUrl);
		}

	}

	/**
	 * 解析课时json数据 处理不能转化的数据
	 *
	 * @param jsonObject
	 * @return
	 */
	private CourseLesson jsonParseLesson(JSONObject jsonObject) {

		String difficulty = (String) jsonObject.remove("difficulty");
		Integer difficultyCode = EnumUtils.getEnumCodeByDesc(difficulty, Difficulty.class);

		String compulsory = (String) jsonObject.remove("compulsory");
		Integer compulsoryCode = EnumUtils.getEnumCodeByDesc(compulsory,
				CourseLesson.CompulsoryType.class);

		String type = (String) jsonObject.remove("type");
		Integer typeCode = EnumUtils.getEnumCodeByDesc(type, CourseLesson.LessonType.class);

		CourseLesson courseLesson = jsonObject.toJavaObject(CourseLesson.class);
		courseLesson.setType(typeCode);

		// 知识点数据处理
		String knowledge = (String) jsonObject.remove("knowledge");
		String[] strArray = knowledge.split(PatternConstant.KNOWLEDGE_SPLIT_STR);
		// 经过剪切 第一个为空字符 删掉
		if (strArray.length > CommonConstant.ZERO) {
			String[] knowledges = Arrays.copyOfRange(strArray, CommonConstant.ONE, strArray.length);
			courseLesson.setKnowledge(JsonUtil.toJsonString(knowledges));
		}

		courseLesson.setCompulsory(compulsoryCode);
		courseLesson.setDifficulty(difficultyCode);
		courseLesson.setResourceId(IDGeneratorUtil.getID());

		return courseLesson;
	}

	@Override
	public List<LessonConfig> parseRmLessonConfig(Course course, String path,
			Map<String, String> lessonNameMap) {

		List<LessonConfig> lessonConfigs = Lists.newArrayList();
		// 设置根目录
		LessonConfig courseConfig = LessonConfig.builder().name(course.getName())
				.courseId(course.getResourceId()).index(CommonConstant.ZERO)
				.nodeType(LessonConfig.NodeType.NODE_COURSE.getCode())
				.resourceId(IDGeneratorUtil.getID()).build();
		lessonConfigs.add(courseConfig);

		int index = CommonConstant.ONE;
		List<String> chapterPaths = FileUtil.listDirs(path, false);

		// 过滤掉镜像文件夹并排序
		List<String> notImagePathList = StreamApiUtils.filter(chapterPaths,
				pathStr -> !new File(pathStr).getName().equals(ResourceImportConstant.IMAGE_NAME));
		List<String> sortList = StreamApiUtils.sortByColumn(notImagePathList,
				pathStr -> Integer
						.parseInt(new File(pathStr).getName().split(PatternConstant.UNDER_LINE,
								CommonConstant.TWO)[CommonConstant.ZERO]));

		for (String chapterPath : sortList) {

			String[] chapterNameArray = new File(chapterPath).getName()
					.split(PatternConstant.UNDER_LINE, CommonConstant.TWO);
			String chapterName = chapterNameArray.length > CommonConstant.ONE
					? chapterNameArray[CommonConstant.ONE]
					: chapterNameArray[0];
			// 生成章节配置
			LessonConfig chapterConfig = LessonConfig.builder().courseId(course.getResourceId())
					.parentId(courseConfig.getResourceId()).resourceId(IDGeneratorUtil.getID())
					.nodeType(LessonConfig.NodeType.NODE_CHAPTER.getCode()).name(chapterName)
					.index(index).build();
			lessonConfigs.add(chapterConfig);

			// 索引加1
			index++;

			// 课时数据组装
			List<String> lessonPathList = FileUtil.listDirs(chapterPath, false);
			List<String> sortLessonList = StreamApiUtils.sortByColumn(lessonPathList, pathStr -> {
				String sortStr = new File(pathStr).getName().split(PatternConstant.UNDER_LINE,
						CommonConstant.TWO)[CommonConstant.ZERO];
				return Integer
						.parseInt(sortStr.split(PatternConstant.STRIGULA)[CommonConstant.ONE]);
			});
			for (String lessonPath : sortLessonList) {
				String[] nameArray = new File(lessonPath).getName()
						.split(PatternConstant.UNDER_LINE, CommonConstant.TWO);
				String name = nameArray.length > CommonConstant.ONE ? nameArray[CommonConstant.ONE]
						: nameArray[0];

				if (StringUtils.isBlank(lessonNameMap.get(name))) {
					log.error(
							"Course directory name and course name are inconsistent! course_path=[{}], lesson_dir=[{}]",
							path, lessonPath);
					throw new BusinessException(OjBusinessErrorCode.LESSON_DIR_NAME_ERROR);
				}
				LessonConfig lessonConfig = LessonConfig.builder().courseId(course.getResourceId())
						.parentId(chapterConfig.getResourceId()).resourceId(IDGeneratorUtil.getID())
						.nodeType(LessonConfig.NodeType.NODE_LESSON.getCode()).name(name)
						.lessonId(lessonNameMap.get(name)).index(index).build();
				lessonConfigs.add(lessonConfig);

				// 索引加一
				index++;
			}
		}

		return lessonConfigs;
	}

	@Override
	public Boolean rMCourseImport(String path, Map<String, String> envReqs,
			AuthContext authContext) {

		// 课程信息组装
		Course course = parseRmCourse(path);

		// 课时信息组装
		Map<String, String> lessonNameMap = Maps.newHashMap();
		List<CourseLesson> lessons = parseRmLesson(course, path, envReqs, lessonNameMap);

		List<LessonConfig> lessonConfigs = parseRmLessonConfig(course, path, lessonNameMap);

		// 数据入库
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 如果覆盖的话先删除
				if (resourceCover) {
					courseService.batchDelete(Arrays.asList(course.getResourceId()), authContext);
				}

				// 课程相关数据存储
				courseService.saveOrUpdate(course);
				// 试卷数据初始化存储
				StreamApiUtils.map(lessons, courseLesson -> initPaper(courseLesson));
				// 课时和课时目录数据存储
				courseLessonService.saveOrUpdateBatch(lessons);

                //先删除旧的编排
                lessonConfigMapper.deleteByCourse(course.getResourceId());
				lessonConfigService.saveBatch(lessonConfigs);

			}
		});

		return true;
	}

	/**
	 * 初始化课后练习题
	 *
	 * @param courseLesson
	 * @return
	 */
	private Paper initPaper(CourseLesson courseLesson) {

		if (courseLesson.getPaperId() != null){
			return null;
		}
		PaperContext paperContext = PaperContext.builder().resourceId(courseLesson.getResourceId())
				.type(Paper.PaperType.LESSON.getCode()).name(courseLesson.getName())
				.description(courseLesson.getName()).isPublic(CommonConstant.TRUE).build();
		Paper paper = paperService.create(paperContext);
		courseLesson.setPaperId(paper.getResourceId());

		return paper;
	}


	public Course generateCombineCourse(CombinedCourseImportContext courseInfo, List<String> existConfig) {

		// 增量更新
		Course courseExist = courseMapper.selectByCourseNum(courseInfo.getCourseNum());
		if (null != courseExist) {
			if (!StringUtils.equals(courseExist.getName(), courseInfo.getCourseName())) {
				log.error(
						"The courseNum=[{}] already exists! import course name=[{}], exists course name=[{}]",
						courseInfo.getCourseNum(), courseInfo.getCourseName(), courseExist.getName());
				throw new BusinessException(OjBusinessErrorCode.COURSE_NUM_IS_EXIST);
			}

			List<LessonConfig> configs = lessonConfigMapper.selectByCourse(courseInfo.getCourseNum());
			for (LessonConfig config: configs) {
				existConfig.add(config.getResourceId());
			}
			return courseExist;
		}

		Course course = new Course();
		course.setCourseNum(courseInfo.getCourseNum());
		course.setResourceId(courseInfo.getCourseNum());
		course.setName(courseInfo.getCourseName());
		course.setCategoryId(courseInfo.getCategoryName());


		// 用户修改
		course.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
		course.setModifyUserId(ResourceImportConstant.SUPER_ADMIN_HASH);

		// 课程分类数据查询或创建
		CourseCategory category = courseCategoryService.getOrCreate(course.getCategoryId());
		course.setCategoryId(category.getResourceId());

		// 课程其他数据初始化
		course.setBuiltin(CommonConstant.TRUE);
		course.setCourseType(CourseCategory.CourseType.BASE.getCode());
		course.setHeat(CommonConstant.ZERO);
		course.setScore(CommonConstant.ZERO);
		course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setReadAuthIds(JsonUtil.toJsonString(Lists.newArrayList()));
		course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setOperationAuthIds(JsonUtil.toJsonString(Lists.newArrayList()));
		course.setIsPublic(CommonConstant.FALSE);

		course.setDifficulty(Course.Difficulty.PRIMARY.getCode());


		return course;
	}

	public List<LessonConfig> generateLessonConfig(Course course, List<CombinedCourseImportContext> courseInfos, Map<String, String> lessonNameMap) {

		List<LessonConfig> lessonConfigs = Lists.newArrayList();
		HashMap<String, LessonConfig> chapterMap = Maps.newHashMap();
		// 设置根目录
		LessonConfig courseConfig = LessonConfig.builder().name(course.getName())
				.courseId(course.getResourceId()).index(CommonConstant.ZERO)
				.nodeType(LessonConfig.NodeType.NODE_COURSE.getCode())
				.resourceId(IDGeneratorUtil.getID()).build();
		lessonConfigs.add(courseConfig);

		int index = CommonConstant.ONE;

		for (CombinedCourseImportContext courseInfo: courseInfos) {

			// 处理章节
			String chapterName = courseInfo.getChapterName();
			LessonConfig chapterConfig = chapterMap.get(chapterName);

			if (null == chapterConfig) {
				chapterConfig = LessonConfig.builder().courseId(course.getResourceId())
						.parentId(courseConfig.getResourceId()).resourceId(IDGeneratorUtil.getID())
						.nodeType(LessonConfig.NodeType.NODE_CHAPTER.getCode()).name(chapterName)
						.index(index).build();
				lessonConfigs.add(chapterConfig);

				index ++;

				chapterMap.put(chapterName, chapterConfig);
			}

			String[] lessonNameList = courseInfo.getLessonName().split("_", 2);
			String lessonName;
			if (lessonNameList.length == 2){
				lessonName = lessonNameList[1];
			}else{
				lessonName = lessonNameList[0];
			}

			// 处理课时
			LessonConfig lessonConfig = LessonConfig.builder().courseId(course.getResourceId())
					.parentId(chapterConfig.getResourceId()).resourceId(IDGeneratorUtil.getID())
					.nodeType(LessonConfig.NodeType.NODE_LESSON.getCode()).name(lessonName)
					.lessonId(lessonNameMap.get(lessonName)).index(index).build();
			lessonConfigs.add(lessonConfig);

			// 索引加一
			index++;

		}


		return lessonConfigs;
	}

	@Override

	public Boolean rMCombinedCourseImport(List<CombinedCourseImportContext> courseInfos, AuthContext authContext) {

		log.info("Combined-Lesson-Import's request is: [{}]", JsonUtil.toJsonString(courseInfos));

		if (null == courseInfos || courseInfos.size() == 0){
			log.info("The request body is empty!");
			return true;
		}

		// 增量更新时，删除原课程编排数据
		List<String> existConfig = Lists.newArrayList();

		// 课程信息组装
		Course course = generateCombineCourse(courseInfos.get(0), existConfig);

		// 课时信息组装
		List<CourseLesson> lessons = Lists.newArrayList();

		Map<String, String> lessonNameMap = Maps.newHashMap();

		for (CombinedCourseImportContext courseInfo: courseInfos) {
			if (null == courseInfo.getPath()){
				continue;
			}

			String[] lessonNameList = courseInfo.getLessonName().split("_", 2);
			String lessonName;
			if (lessonNameList.length == 2){
				lessonName = lessonNameList[1];
			}else{
				lessonName = lessonNameList[0];
			}

			// 该课时已存在，不再处理
			String lessonId =  courseLessonMapper.selectByCourseAndName(course.getResourceId(), lessonName);
			if (null != lessonId) {
				lessonNameMap.putIfAbsent(lessonName,lessonId);
				continue;
			}

			Map<String, String> envReqs = Maps.newHashMap();
			envReqs.put(courseInfo.getLessonName(), courseInfo.getTopoId());
			CourseLesson courseLesson = parseLesson(courseInfo.getPath(), course, envReqs);

			if (null != courseLesson) {
				if (null != lessonNameMap.putIfAbsent(lessonName,
						courseLesson.getResourceId())) {
					log.error("Lesson name exist! course_name=[{}], lesson_path=[{}]", courseInfo.getCourseName(),
							courseInfo.getPath());
					throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
				}
				lessons.add(courseLesson);
			}

		}


		List<LessonConfig> lessonConfigs = generateLessonConfig(course, courseInfos, lessonNameMap);


		// 数据入库
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				if (lessons.size() == 0){
					log.info("All lessons have already imported");

				} else {
					if (existConfig.size() == 0) {
						// 课程相关数据存储
						courseMapper.insert(course);
					}


					// 试卷数据初始化存储
					StreamApiUtils.map(lessons, courseLesson -> initPaper(courseLesson));
					// 课时和课时目录数据存储
					courseLessonService.saveBatch(lessons);
				}

				lessonConfigMapper.deleteByCourse(course.getResourceId());
				lessonConfigService.saveBatch(lessonConfigs);

			}
		});

		return true;
	}
}
