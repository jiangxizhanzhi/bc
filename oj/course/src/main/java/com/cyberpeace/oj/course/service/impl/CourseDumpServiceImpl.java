package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.MdLesson;
import com.cyberpeace.common.mdparser.MdParser;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.model.scene.SceneDumpContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonConfigMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseDumpService;
import com.cyberpeace.oj.ojbase.constant.*;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.bo.PaperContext;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.cyberpeace.common.util.MdUtil.saveAsFileWriter;
import static com.cyberpeace.oj.ojbase.constant.ResourceDumpConstant.*;

@Slf4j
@Service
public class CourseDumpServiceImpl implements CourseDumpService {

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private PaperService paperService;

	@Autowired
	private PaperMapper paperMapper;

	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private UtilService utilService;

	@Value("${import.resource.cover}")
	private Boolean resourceCover;

	@Autowired
	private CourseValidateHandler courseValidateHandler;

	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

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

		// 是否具有环境地址
		if (new File(path, ResourceImportConstant.ENV).exists()) {
			courseLesson.setSceneShareMode(SceneShare.PRIVATE.getCode());
			courseLesson.setDestroyDelay(CommonConstant.TWO);
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
	public File rMCourseDump(String path, AuthContext authContext) {
		// 1.获取所有课程数据 内容及文件
		List<Course> courses = courseMapper.selectList(null);
		// 2.获取所有课时数据 内容及文件
		List<CourseLesson> courseLessons = courseLessonMapper.selectList(null);
		// 根据目录索引生成课时文件名 node_type分章节 课时 course_id查询
		List<LessonConfig> lessonConfigs = lessonConfigMapper.selectList(null);

		List<CourseCategory> categories = courseCategoryMapper.selectList(null);

		List<UserInfo> allUsers = remoteUserService.getAllUsers();

		Map<String, Object> map = new HashMap<>();
		map.put("type",3);
		List<Paper> papers = paperMapper.selectByMap(map);
		List<PracticeTheoryRef> practiceTheoryRefs = practiceTheoryRefMapper.selectList(null);
		List<PracticeOperationRef> practiceOperationRefs = practiceOperationRefMapper.selectList(null);

		List<PracticeOperation> practiceOperations = practiceOperationMapper.selectList(null);

		List<PracticeTheory> practiceTheories = practiceTheoryMapper.selectList(null);

		Map<String, List<CourseLesson>> lessonCol = courseLessons.stream().collect(Collectors.groupingBy(CourseLesson::getCourseId));
		Map<String, List<LessonConfig>> lessonConfigCol = lessonConfigs.stream().collect(Collectors.groupingBy(LessonConfig::getCourseId));
		String coursePath = ResourceDumpConstant.DUMP_ALL_FILE_STORE_PATH + File.separator + ResourceDumpConstant.DUMP_ALL_COURSE_FILE_STORE_PATH;
		for (Course course:courses){
			String destFilePath = coursePath +
					File.separatorChar + course.getName();
			FileUtil.mkdirs(destFilePath);
			String resourceId = course.getResourceId();
			List<LessonConfig> currentLessonConfigs = lessonConfigCol.get(resourceId);
			combineCourseContent(destFilePath, course, categories, allUsers);
			List<CourseLesson> currentLessons = lessonCol.get(resourceId);
			combineLessons(destFilePath,currentLessonConfigs,currentLessons,papers,practiceTheoryRefs,practiceOperationRefs);
		}
		// TODO 判断文件是否存在

		String destDir = "course";
		FileUtil.mkdirs(destDir);
		// 压缩当前文件夹
		String zipDest = ZipUtil.toZipDest(destDir, AttachmentPath.OTHER.getDesc());
		FileUtil.deleteDir(destDir, true);
		return new File(zipDest);
	}

	private void combineLessons(String courseFileName, List<LessonConfig> currentLessonConfigs, List<CourseLesson> currentLessons, List<Paper> papers,
								List<PracticeTheoryRef> practiceTheoryRefs, List<PracticeOperationRef> practiceOperationRefs) {
		Map<String, List<CourseLesson>> lessonCols = currentLessons.stream().collect(Collectors.groupingBy(CourseLesson::getResourceId));

		List<LessonConfig> chapterFirsts = currentLessonConfigs.stream().filter(s -> s.getNodeType().equals(LessonConfig.NodeType.NODE_CHAPTER.getCode())).
				sorted(Comparator.comparing(LessonConfig::getIndex).reversed()).collect(Collectors.toList());
		if (chapterFirsts.size() == 0) {
			int index = 1;
			List<LessonConfig> chapterSeconds = currentLessonConfigs.stream().filter(s -> s.getNodeType().equals(LessonConfig.NodeType.NODE_LESSON.getCode())).sorted(Comparator.comparing(LessonConfig::getIndex).reversed()).collect(Collectors.toList());
			StreamApiUtils.map(chapterSeconds, chapterSecond -> combineChapterSecond(1, index, chapterSecond, courseFileName + File.separator + "1_chapter",
					lessonCols, papers, practiceTheoryRefs, practiceOperationRefs));
		} else {
			StreamApiUtils.map(chapterFirsts, chapterFirst -> combineChapterFirst(chapterFirst, currentLessonConfigs, lessonCols, courseFileName, papers, practiceTheoryRefs, practiceOperationRefs));
		}

	}

	private Map<String, String> combineChapterFirst(LessonConfig chapterOne, List<LessonConfig> lessonConfigs, Map<String, List<CourseLesson>> lessons, String courseFileName,
													List<Paper> papers, List<PracticeTheoryRef> practiceTheoryRefs, List<PracticeOperationRef> practiceOperationRefs) {

		Map<String, String> filePathMap = new HashMap<>();
		Integer currentChapter = chapterOne.getNodeType();
		String chapterFileName = courseFileName + File.separator + currentChapter + "_" + chapterOne.getName();
		FileUtil.mkdirs(chapterFileName);
		int index = 1;
		List<LessonConfig> chapterSeconds = lessonConfigs.stream().filter(s -> s.getParentId().equals(chapterOne.getResourceId())).collect(Collectors.toList());
		StreamApiUtils.map(chapterSeconds, chapterSecond->combineChapterSecond(currentChapter, index, chapterSecond,chapterFileName, lessons, papers, practiceTheoryRefs,practiceOperationRefs));
		return filePathMap;
	}

	private Integer combineChapterSecond(Integer lastChapter, int index, LessonConfig chapterSecond, String chapterFileName, Map<String, List<CourseLesson>> lessons, List<Paper> papers,
										 List<PracticeTheoryRef> practiceTheoryRefs, List<PracticeOperationRef> practiceOperationRefs) {
		CourseLesson lesson = lessons.get(chapterSecond.getLessonId()).get(0);
		String pathname = chapterFileName + File.separator + lastChapter + "-" + index + "_" + lesson.getName();

		FileUtil.mkdirs(pathname);
		index += 1;
		// 课时内容基础保存
		generateLessonBase(pathname, lesson, papers, practiceTheoryRefs,practiceOperationRefs);
		// TODO文件是否均
//		if(Objects.equals(lesson.getType(), CourseLesson.LessonType.THEORY.getCode())){
		// 理论课文件保存
		combineTheoryLesson(pathname,lesson);

		// 实验课文件保存
//		combineOperationLesson(pathname,lesson, chapterFileName);

		// 课时readme.md生成
		combineLessonReadmeMD(pathname,lesson);
		return index;
	}

	private void combineLessonReadmeMD(String pathname, CourseLesson lesson) {
		String readmeMDFileName = pathname + File.separator + "README.md";
		MdParser mdParser = new MdParser();
		MdLesson mdLesson = new MdLesson();
		mdLesson.setLessonName(lesson.getName());
		String lessonType = Arrays.stream(CourseLesson.LessonType.values()).filter(it -> Objects.equals(it.getCode(), lesson.getType())).collect(Collectors.toList()).get(0).getDesc();
		mdLesson.setType(lessonType);
		// TODO待修改
		mdLesson.setDifficulty(lesson.getDifficulty());
		mdLesson.setDuration(lesson.getDuration().toString());
		mdLesson.setKnowledge(lesson.getKnowledge());
		String compulsory = Arrays.stream(CourseLesson.CompulsoryType.values()).filter(it -> Objects.equals(it.getCode(), lesson.getCompulsory())).collect(Collectors.toList()).get(0).getDesc();
		mdLesson.setCompulsory(compulsory);
		mdParser.write(mdLesson, new File(readmeMDFileName));


	}

	private void combineOperationLesson(String pathname, CourseLesson lesson, String destFilePath) {
		//doc 指导文档doc/pdf/md 包含img文件夹 指导书md
		String docFile = pathname + File.separator + "doc";
		FileUtil.mkdirs(docFile);

		generateHandout(lesson, docFile);

		// env 文件夹包含topo.yml
		String envFile = pathname + File.separator + "env";
		FileUtil.mkdirs(envFile);
		SceneDumpContext sceneDumpContext = new SceneDumpContext();
		sceneDumpContext.setId(lesson.getTopoId());
		sceneDumpContext.setEnvPath(envFile);
		sceneDumpContext.setImagePath(destFilePath + File.separator + "images");
		remoteSceneService.sceneDump(sceneDumpContext);
	}

	private void combineTheoryLesson(String pathname, CourseLesson lesson) {
		// handout 讲义

		String handoutFile = pathname + File.separator + "handout";
		FileUtil.mkdirs(handoutFile);
		generateHandout(lesson, handoutFile);


	}

	private void generateHandout(CourseLesson lesson, String handoutFile) {
		Integer handoutType = lesson.getHandoutType();
		String handoutUrl = lesson.getHandoutUrl();
		String img = handoutFile + File.separator + "img";
		FileUtil.mkdirs(img);
		if (null != handoutUrl) {
			if (!handoutType.equals(CourseLesson.HandoutType.MD.getCode())) {

				if (!handoutUrl.startsWith("/cpms")) {
					handoutUrl = "/cpms" + handoutUrl;
				}
				String[] split = handoutUrl.split("/");
				String handoutFileName = split[split.length - 1];
				FileUtil.copyFile(handoutUrl, handoutFile + File.separator + handoutFileName, false);
			} else {
				MdParser mdParser = new MdParser();
				String handoutMd = handoutFile + File.separator + "handout.md";
				saveAsFileWriter(handoutUrl, "handout.md", handoutFile);
				String markdown = MdUtil.dumpMarkdownImages(handoutMd, img);
				markdown = utilService.replaceMarkdownFile(markdown);
				mdParser.write(markdown, new File(handoutMd));
			}
		}
	}

	private void generateLessonBase(String pathname, CourseLesson lesson,List<Paper> papers,
									List<PracticeTheoryRef> practiceTheoryRefs, List<PracticeOperationRef> practiceOperationRefs) {
		String attachFile = pathname + File.separator + "attachments";
		FileUtil.mkdirs(attachFile);
		String attachmentIds = lesson.getAttachmentIds();
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(attachmentIds, false);
		if(attachmentBos.size() > 0){
			List<String> attachFiles = StreamApiUtils.map(attachmentBos, AttachmentBo::getFilePath);
			for(String attach:attachFiles){
				saveAttachFile(attach, attachFile);
			}
		}

		String videoPath = pathname + File.separator + "video";
		FileUtil.mkdirs(videoPath);
		String videoUrl = lesson.getVideoUrl();
		if (null != videoUrl) {
			String[] split = videoUrl.split("/");
			String videoName = split[split.length-1];
			if (!videoUrl.startsWith("/cpms")) {
				videoUrl = "/cpms" + File.separator + videoUrl;
			}
			FileUtil.copyFile(videoUrl, videoPath + File.separator + videoName, false);
		}

		String practices = pathname + File.separator + "practices";

		FileUtil.mkdirs(practices);
		String paperId = lesson.getPaperId();
		List<Paper> papers1 = papers.stream().filter(s -> s.getResourceId().equals(paperId)).collect(Collectors.toList());
		if (papers1.size() > 0) {
			// TODO模板为空　待写入
			List<PracticeTheoryRef> theoryRefs = practiceTheoryRefs.stream().filter(s -> s.getPaperId().equals(paperId)).collect(Collectors.toList());

			List<PracticeOperationRef> operationRefs = practiceOperationRefs.stream().filter(s -> s.getPaperId().equals(paperId)).collect(Collectors.toList());
			// TODO习题导出excel
			// FileUtil.copyFile(videoUrl, videoPath, false);
		}

	}

	private void saveAttachFile(String attachFile, String pathname) {
		String[] split = attachFile.split("/");
		String fileName = split[split.length-1];
		FileUtil.copyFile(attachFile, pathname + File.separator + fileName , false);
	}


	private void combineCourseContent(String destFilePath, Course course, List<CourseCategory> categories, List<UserInfo> allUsers) {
		// logo保存
		String realPath = utilService.getRealPath(course.getLogo());
		String fileSuffix = FileUtil.getFileSuffix(realPath);
		String logoUrl = destFilePath + File.separator + "logo." + fileSuffix;
		FileUtil.copyFile(realPath, logoUrl, false);
		// 3.生成READEME.md
		String categoryName = categories.stream().filter(s -> s.getResourceId().equals(course.getCategoryId())).collect(Collectors.toList()).get(0).getName();
		List<UserInfo> users = allUsers.stream().filter(s -> s.getUserId().equals(course.getCreateUserId())).collect(Collectors.toList());
		String userName = users.size() > 0 ? users.get(0).getName() : "";

		generateReadmeMD(destFilePath, course, categoryName, userName,logoUrl);
		// 4.生成课程大纲md
		generateOutlineMD(destFilePath, course.getOutline());
		// TODO 文件待校验
		FileUtil.mkdirs(destFilePath + File.separator + "images");
		FileUtil.mkdirs(destFilePath + File.separator + STAGE_EXAM);
		FileUtil.mkdirs(destFilePath + File.separator + FINAL_EXAM);
	}

	private void generateOutlineMD(String destFilePath, String outline) {
		String readmeMDFileName = destFilePath + File.separator + OUTLINE_NAME;
		MdParser mdParser = new MdParser();

		// TODO 待写入
		mdParser.write(outline, new File(readmeMDFileName));
	}

	private void generateReadmeMD(String pathname, Course course, String categoryName, String userName, String logoUrl) {
		String readmeMDFileName = pathname + File.separator + "README.md";
		MdParser mdParser = new MdParser();
		CourseMd courseMd = BeanUtils.copy(course, CourseMd.class);
		courseMd.setCategoryId(categoryName);
		courseMd.setCreateUserId(null == userName ? "" : userName);
		mdParser.write(courseMd, new File(readmeMDFileName));

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

}
