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
import java.util.logging.Logger;
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

	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Override
	public File rMCourseDump(AuthContext authContext, String resource_id) {
		// 1.获取所有课程数据 内容及文件
		Map<String, Object> courseFilterMap = new HashMap<>();
		courseFilterMap.put("resource_id",resource_id);
		List<Course> courses = courseMapper.selectByMap(courseFilterMap);
		if(courses.size() == 0){
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_ID_NOT_EXIST);
		}
		// 2.获取所有课时数据 内容及文件
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("course_id",resource_id);
		List<CourseLesson> courseLessons = courseLessonMapper.selectByMap(filterMap);
		// 根据目录索引生成课时文件名 node_type分章节 课时 course_id查询
		List<LessonConfig> lessonConfigs = lessonConfigMapper.selectByMap(filterMap);

		Map<String, Object> categoryFilterMap = new HashMap<>();
		Course course = courses.get(0);
		String category_id = course.getCategoryId();
		filterMap.put("resource_id",category_id);
		List<CourseCategory> categories = courseCategoryMapper.selectByMap(categoryFilterMap);

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

		String destFilePath = coursePath +
				File.separatorChar + course.getName();
		FileUtil.mkdirs(destFilePath);
		String resourceId = course.getResourceId();
		List<LessonConfig> currentLessonConfigs = lessonConfigCol.get(resourceId);
		combineCourseContent(destFilePath, course, categories, allUsers);
		List<CourseLesson> currentLessons = lessonCol.get(resourceId);
		combineLessons(destFilePath,currentLessonConfigs,currentLessons,papers,practiceTheoryRefs,practiceOperationRefs);

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
		log.info("get chapterFirst of the current lesson successfully");
		if (chapterFirsts.size() == 0) {
			int index = 1;
			List<LessonConfig> chapterSeconds = currentLessonConfigs.stream().filter(s -> s.getNodeType().equals(LessonConfig.NodeType.NODE_LESSON.getCode())).sorted(Comparator.comparing(LessonConfig::getIndex).reversed()).collect(Collectors.toList());
			for(LessonConfig chapterSecond:chapterSeconds){
				combineChapterSecond(1, index, chapterSecond, courseFileName + File.separator + "1_chapter",
						lessonCols, papers, practiceTheoryRefs, practiceOperationRefs);
			}
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
		List<LessonConfig> chapterSeconds = lessonConfigs.stream().filter(s -> s.getParentId().equals(chapterOne.getResourceId())).
				sorted(Comparator.comparing(LessonConfig::getIndex).reversed()).collect(Collectors.toList());
		for (LessonConfig chapterSecond:chapterSeconds){
			combineChapterSecond(currentChapter, index, chapterSecond, chapterFileName, lessons, papers, practiceTheoryRefs, practiceOperationRefs);
			index += 1;
		}
		return filePathMap;
	}

	private void combineChapterSecond(Integer lastChapter, int index, LessonConfig chapterSecond, String chapterFileName, Map<String, List<CourseLesson>> lessons, List<Paper> papers,
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
		log.info("dump lesson readme.md successfully!");


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
		log.info("dump lesson env & images successfully!");
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

		if (null != handoutUrl) {
			if (!handoutType.equals(CourseLesson.HandoutType.MD.getCode())) {

				if (!handoutUrl.startsWith("/cpms")) {
					handoutUrl = "/cpms" + File.separator + handoutUrl;
				}
				String[] split = handoutUrl.split("/");
				String handoutFileName = split[split.length - 1];
				FileUtil.copyFile(handoutUrl, handoutFile + File.separator + handoutFileName, false);
			} else {
				File file = new File(handoutFile, "handout.md");
				String markdown = MdUtil.dumpMarkdownImages(file, handoutUrl);
				saveAsFileWriter(markdown, file.getAbsolutePath());
			}
			log.info("dump lesson handout successfully!");
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
			log.info("dump lesson attachFile successfully!");

		}

		String videoPath = pathname + File.separator + "video";
		FileUtil.mkdirs(videoPath);
		String videoUrl = lesson.getVideoUrl();
		if (null != videoUrl && !videoUrl.isEmpty()) {
			String[] split = videoUrl.split("/");
			String videoName = split[split.length-1];
			if (!videoUrl.startsWith("/cpms")) {
				videoUrl = "/cpms" + File.separator + videoUrl;
			}
			FileUtil.copyFile(videoUrl, videoPath + File.separator + videoName, false);
			log.info("dump lesson video successfully!");
		}

		String practices = pathname + File.separator + "practices";

		FileUtil.mkdirs(practices);
		String paperId = lesson.getPaperId();
		List<Paper> papers1 = papers.stream().filter(s -> s.getResourceId().equals(paperId)).collect(Collectors.toList());
		if (papers1.size() > 0) {
			// TODO模板为空　待写入
			List<PracticeTheoryRef> theoryRefs = practiceTheoryRefs.stream().filter(s -> s.getPaperId().equals(paperId)).collect(Collectors.toList());

			List<PracticeOperationRef> operationRefs = practiceOperationRefs.stream().filter(s -> s.getPaperId().equals(paperId)).collect(Collectors.toList());
			log.info("get lesson practices successfully!");
			// TODO习题导出excel
			// FileUtil.copyFile(videoUrl, videoPath, false);
		}

		// 课时readme.md生成
		combineLessonReadmeMD(pathname,lesson);

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
		log.info("dump course logo successfully!");
		// 3.生成READEME.md
		String categoryName = categories.stream().filter(s -> s.getResourceId().equals(course.getCategoryId())).collect(Collectors.toList()).get(0).getName();
		List<UserInfo> users = allUsers.stream().filter(s -> s.getUserId().equals(course.getCreateUserId())).collect(Collectors.toList());
		String userName = users.size() > 0 ? users.get(0).getName() : "";

		generateReadmeMD(destFilePath, course, categoryName, userName);
		log.info("dump course readme.md successfully!");
		// 4.生成课程大纲md
		generateOutlineMD(destFilePath, course.getOutline());
		log.info("dump course outline successfully!");
		// TODO 文件待校验
		FileUtil.mkdirs(destFilePath + File.separator + "images");
		FileUtil.mkdirs(destFilePath + File.separator + STAGE_EXAM);
		FileUtil.mkdirs(destFilePath + File.separator + FINAL_EXAM);
	}

	private void generateOutlineMD(String destFilePath, String outline) {
		File file = new File(destFilePath,OUTLINE_NAME);
		// TODO 待写入
		saveAsFileWriter(outline, file.getAbsolutePath());
	}

	private void generateReadmeMD(String pathname, Course course, String categoryName, String userName) {
		String readmeMDFileName = pathname + File.separator + "README.md";
		MdParser mdParser = new MdParser();
		CourseMd courseMd = BeanUtils.copy(course, CourseMd.class);
		courseMd.setCategoryId(categoryName);
		courseMd.setCreateUserId(null == userName ? "" : userName);
		mdParser.write(courseMd, new File(readmeMDFileName));

	}
}
