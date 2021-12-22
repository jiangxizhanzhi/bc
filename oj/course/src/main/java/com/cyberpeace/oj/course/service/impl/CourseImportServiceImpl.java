package com.cyberpeace.oj.course.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.*;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.bo.PaperContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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

	@Value("${import.resource.cover}")
	private Boolean resourceCover;

	@Override
	public Course parseRmCourse(String path) {

		// 解析md文件 获取对应数据
		File markdownFile = new File(path, ResourceImportConstant.README_NAME);
		if (!markdownFile.exists() || markdownFile.isDirectory()) {
			log.error(
					"parseRmCourse: Markdown file not found or is directory under path! path=[{}]",
					path);
			throw new InvalidParamException("文件路径不正确！");
		}
		JSONObject markdownValue = MdUtil.parseJsonMarkdown(markdownFile);

		// 解析数据转换 由markdown的key-value转换为数据库对应数据
		JSONObject convertValue = JsonUtil.filedConvert(markdownValue,
				ResourceImportConstant.COURSE_DATA_MAPPING);
		Course course = jsonParseCourse(convertValue);

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
		FileUtil.copyFile(logoFile.getAbsolutePath(), dest, true);
		course.setLogo(dest);

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
	public List<CourseLesson> parseRmLesson(Course course, String path,
			Map<String, String> envReqs) {

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
			List<CourseLesson> courseLessons = StreamApiUtils.map(lessonPaths,
					lessonPath -> parseLesson(lessonPath, course, envReqs));
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

		// 解析markdown文件
		String handoutPath = courseLesson.getType().intValue() == CourseLesson.LessonType.THEORY
				.getCode().intValue()
						? new File(path, ResourceImportConstant.HANDOUT).getAbsolutePath()
						: new File(path, ResourceImportConstant.DOC).getAbsolutePath();

		// 目录拷贝
		String destHandoutPath = String.format(AttachmentPath.HANDOUT.getDesc(),
				course.getResourceId());
		String dest = new File(destHandoutPath, courseLesson.getName()).getAbsolutePath();
		FileUtil.copyDirectory(handoutPath, dest, false);

		// 目录压缩设置原始markdown文件地址
		String markdownPath = String.format(AttachmentPath.MARKDOWN.getDesc(),
				course.getResourceId());
		String originUrl = ZipUtil.toZipDest(dest, markdownPath);
		courseLesson.setOriginMarkdownFile(originUrl);

		// 文件解析
		String handoutFilePath = courseLesson.getType().intValue() == CourseLesson.LessonType.THEORY
				.getCode().intValue()
						? new File(dest, ResourceImportConstant.HANDOUT_FILE_NAME).getAbsolutePath()
						: new File(dest, ResourceImportConstant.DOC_FILE_NAME).getAbsolutePath();
		String markdown = MdUtil.parseMarkdown(handoutFilePath);
		courseLesson.setHandoutUrl(markdown);

		// 解析附件
		List<String> attachmentList = FileUtil.listDirs(
				new File(path, ResourceImportConstant.ATTACHMENT).getAbsolutePath(), true);
		List<String> attachmentPaths = StreamApiUtils.map(attachmentList, attachmentPath -> {
			File attachmentFile = new File(attachmentPath);
			File destFile = new File(AttachmentPath.ATTACHMENT.getDesc(), attachmentFile.getName());
			return FileUtil.copyFile(attachmentPath, destFile.getAbsolutePath(), true);
		});

		List<String> attachmentIds = attachmentService.batchInsert(attachmentPaths);
		String idStr = String.join(CommonConstant.CODE_COMMA, attachmentIds);
		courseLesson.setAttachmentIds(idStr);

		// 解析课时视频
		String videoPath = String.format(AttachmentPath.VIDEO.getDesc(), course.getResourceId());
		String video = FileUtil
				.getOneFile(new File(path, ResourceImportConstant.VIDEO).getAbsolutePath());
		if (StringUtils.isNotBlank(video)) {
			String videoUrl = FileUtil.copyFile(video,
					videoPath + FileUtil.getFileName(video, true), true);
			courseLesson.setVideoUrl(videoUrl);
		}

		// 其他数据初始化
		courseLesson.setCourseId(course.getResourceId());
		courseLesson.setHandoutType(CourseLesson.HandoutType.MD.getCode());
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
	 * 解析课时json数据 处理不能转化的数据
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
			Map<String, String> nameMap) {

		List<LessonConfig> lessonConfigs = Lists.newArrayList();
		// 设置根目录
		LessonConfig courseConfig = LessonConfig.builder().name(course.getName())
				.courseId(course.getResourceId()).index(CommonConstant.ZERO)
				.nodeType(LessonConfig.NodeType.NODE_COURSE.getCode())
				.resourceId(IDGeneratorUtil.getID()).build();
		lessonConfigs.add(courseConfig);

		int index = CommonConstant.ONE;
		List<String> chapterPaths = FileUtil.listDirs(path, false);
		for (String chapterPath : chapterPaths) {
			// 过滤掉镜像文件夹
			if (new File(chapterPath).getName().equals(ResourceImportConstant.IMAGE_NAME)) {
				continue;
			}
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
			for (String lessonPath : lessonPathList) {
				String[] nameArray = new File(lessonPath).getName()
						.split(PatternConstant.UNDER_LINE, CommonConstant.TWO);
				String name = nameArray.length > CommonConstant.ONE ? nameArray[CommonConstant.ONE]
						: nameArray[0];

				LessonConfig lessonConfig = LessonConfig.builder().courseId(course.getResourceId())
						.parentId(chapterConfig.getResourceId()).resourceId(IDGeneratorUtil.getID())
						.nodeType(LessonConfig.NodeType.NODE_LESSON.getCode()).name(name)
						.lessonId(nameMap.get(name)).index(index).build();
				lessonConfigs.add(lessonConfig);

				// 索引加一
				index++;
			}
		}

		return lessonConfigs;
	}

	@Override
	public Boolean rMCourseImport(String path, Map<String, String> envReqs) {

		// 课程信息组装
		Course course = parseRmCourse(path);

		// 课时信息组装
		List<CourseLesson> lessons = parseRmLesson(course, path, envReqs);

		// 目录数据组装
		Map<String, String> nameMap = StreamApiUtils.toMap(lessons, CourseLesson::getName,
				CourseLesson::getResourceId);
		List<LessonConfig> lessonConfigs = parseRmLessonConfig(course, path, nameMap);

		// 数据入库
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 如果覆盖的话先删除
				if (resourceCover) {
					courseService.batchDelete(Arrays.asList(course.getResourceId()));
				}

				// 课程相关数据存储
				courseMapper.insert(course);
				// 试卷数据初始化存储
				StreamApiUtils.map(lessons, courseLesson -> initPaper(courseLesson));
				// 课时和课时目录数据存储
				courseLessonService.saveBatch(lessons);
				lessonConfigService.saveBatch(lessonConfigs);

			}
		});

		return true;
	}

	/**
	 * 初始化课后练习题
	 * @param courseLesson
	 * @return
	 */
	private Paper initPaper(CourseLesson courseLesson) {

		PaperContext paperContext = PaperContext.builder().resourceId(courseLesson.getResourceId())
				.type(Paper.PaperType.LESSON.getCode()).name(courseLesson.getName())
				.description(courseLesson.getName()).isPublic(CommonConstant.TRUE).build();
		Paper paper = paperService.create(paperContext);
		courseLesson.setPaperId(paper.getResourceId());

		return paper;
	}
}
