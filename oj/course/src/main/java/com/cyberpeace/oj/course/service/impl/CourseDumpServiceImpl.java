package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.MdLesson;
import com.cyberpeace.common.mdparser.MdParser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
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
import com.cyberpeace.oj.course.service.CourseDumpService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceDumpConstant;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


import static com.cyberpeace.common.util.MdUtil.saveAsFileWriter;
import static com.cyberpeace.oj.ojbase.constant.ResourceDumpConstant.*;

@Slf4j
@Service
public class CourseDumpServiceImpl implements CourseDumpService {


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
	public String rMCourseDump(String resource_id, Boolean is_dump_topo) throws IOException {
		log.info("----course dump start----");
		// 1.获取所有课程数据 内容及文件
		Map<String, Object> courseFilterMap = new HashMap<>();
		courseFilterMap.put("resource_id",resource_id);
		List<Course> courses = courseMapper.selectByMap(courseFilterMap);
		if(courses.size() == 0){
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_ID_NOT_EXIST);
		}
		Course course = courses.get(0);
		log.info(String.format("get course %s successfully!", course.getName()));
		// 2.获取所有课时数据 内容及文件
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("course_id",resource_id);
		List<CourseLesson> courseLessons = courseLessonMapper.selectByMap(filterMap);
		// 根据目录索引生成课时文件名 node_type分章节 课时 course_id查询
		List<LessonConfig> lessonConfigs = lessonConfigMapper.selectByMap(filterMap);

		Map<String, Object> categoryFilterMap = new HashMap<>();
		String category_id = course.getCategoryId();
		filterMap.put("resource_id",category_id);
		List<CourseCategory> categories = courseCategoryMapper.selectByMap(categoryFilterMap);

		List<UserInfo> allUsers = remoteUserService.getAllUsers();
		log.info("get all users successfully!");

		Map<String, Object> map = new HashMap<>();
		map.put("type",3);
		List<Paper> papers = paperMapper.selectByMap(map);
		List<PracticeTheoryRef> practiceTheoryRefs = practiceTheoryRefMapper.selectList(null);
		List<PracticeOperationRef> practiceOperationRefs = practiceOperationRefMapper.selectList(null);

		List<PracticeOperation> practiceOperations = practiceOperationMapper.selectList(null);

		List<PracticeTheory> practiceTheories = practiceTheoryMapper.selectList(null);

		Map<String, List<CourseLesson>> lessonCol = courseLessons.stream().collect(Collectors.groupingBy(CourseLesson::getCourseId));
		Map<String, List<LessonConfig>> lessonConfigCol = lessonConfigs.stream().collect(Collectors.groupingBy(LessonConfig::getCourseId));
		String oj = DUMP_ALL_FILE_STORE_PATH + File.separator + DUMP_OJ_FILE_STORE_PATH;
		FileUtil.mkdirs(oj);
		File version = new File(oj, VERSION_FILE);
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(version),
				StandardCharsets.UTF_8);
		FileUtil.writeString(course.getName() + IDGeneratorUtil.getID(), osw);
		String coursePath = oj + File.separator + ResourceDumpConstant.DUMP_ALL_COURSE_FILE_STORE_PATH;

		String destFilePath = coursePath +
				File.separatorChar + course.getName();
		FileUtil.mkdirs(destFilePath);

		String resourceId = course.getResourceId();
		List<LessonConfig> currentLessonConfigs = lessonConfigCol.get(resourceId);
		combineCourseContent(destFilePath, course, categories, allUsers);

		List<CourseLesson> currentLessons = lessonCol.get(resourceId);
		combineLessons(destFilePath,currentLessonConfigs,currentLessons,papers,practiceTheoryRefs,practiceOperationRefs, is_dump_topo);
		log.info(String.format("----course %s dump finish----", course.getName()));
		return destFilePath;
	}

	private void combineLessons(String courseFileName, List<LessonConfig> currentLessonConfigs, List<CourseLesson> currentLessons, List<Paper> papers,
								List<PracticeTheoryRef> practiceTheoryRefs, List<PracticeOperationRef> practiceOperationRefs, Boolean is_dump_topo) {
		Map<String, List<CourseLesson>> lessonCols = currentLessons.stream().collect(Collectors.groupingBy(CourseLesson::getResourceId));

		List<LessonConfig> chapterFirsts = currentLessonConfigs.stream().filter(s -> null != s.getNodeType() && s.getNodeType().equals(LessonConfig.NodeType.NODE_CHAPTER.getCode())).
				sorted(Comparator.comparing(LessonConfig::getIndex).reversed()).collect(Collectors.toList());
		log.info("get chapterFirst of the current lesson successfully");

		if (chapterFirsts.size() == 0) {
			int index = 1;
			List<LessonConfig> chapterSeconds = currentLessonConfigs.stream().filter(s -> null != s.getNodeType() && s.getNodeType().equals(LessonConfig.NodeType.NODE_LESSON.getCode())).sorted(Comparator.comparing(LessonConfig::getIndex).reversed()).collect(Collectors.toList());
			for(LessonConfig chapterSecond:chapterSeconds){
				combineChapterSecond(1, index, chapterSecond, courseFileName + File.separator + "1_chapter",
						lessonCols, papers, practiceTheoryRefs, practiceOperationRefs, is_dump_topo);
				index += 1;
			}
		} else {
			StreamApiUtils.map(chapterFirsts, chapterFirst -> combineChapterFirst(chapterFirst, currentLessonConfigs, lessonCols, courseFileName, papers, practiceTheoryRefs, practiceOperationRefs, is_dump_topo));
		}

	}

	private Map<String, String> combineChapterFirst(LessonConfig chapterOne, List<LessonConfig> lessonConfigs, Map<String, List<CourseLesson>> lessons, String courseFileName,
													List<Paper> papers, List<PracticeTheoryRef> practiceTheoryRefs, List<PracticeOperationRef> practiceOperationRefs, Boolean is_dump_topo) {

		Map<String, String> filePathMap = new HashMap<>();
		Integer currentChapter = chapterOne.getIndex();
		String chapterFileName = courseFileName + File.separator + currentChapter + "_" + chapterOne.getName();
		FileUtil.mkdirs(chapterFileName);
		int index = 1;
		List<LessonConfig> chapterSeconds = lessonConfigs.stream().filter(s -> null != s.getParentId() && s.getParentId().equals(chapterOne.getResourceId())).
				sorted(Comparator.comparing(LessonConfig::getIndex).reversed()).collect(Collectors.toList());
		for (LessonConfig chapterSecond:chapterSeconds){
			combineChapterSecond(currentChapter, index, chapterSecond, chapterFileName, lessons, papers, practiceTheoryRefs, practiceOperationRefs, is_dump_topo);
			index += 1;
		}
		return filePathMap;
	}

	private void combineChapterSecond(Integer lastChapter, int index, LessonConfig chapterSecond, String chapterFileName, Map<String, List<CourseLesson>> lessons, List<Paper> papers,
									  List<PracticeTheoryRef> practiceTheoryRefs, List<PracticeOperationRef> practiceOperationRefs, Boolean is_dump_topo) {
		CourseLesson lesson = lessons.get(chapterSecond.getLessonId()).get(0);
		String pathname = chapterFileName + File.separator + lastChapter + "-" + index + "_" + lesson.getName();

		FileUtil.mkdirs(pathname);
		// 课时内容基础保存
		generateLessonBase(pathname, lesson, papers, practiceTheoryRefs,practiceOperationRefs);
		// TODO文件是否均
		if(Objects.equals(lesson.getType(), CourseLesson.LessonType.THEORY.getCode())) {
			// 理论课文件保存
			combineTheoryLesson(pathname, lesson);
		} else {
			// 实验课文件保存
			if (is_dump_topo) {
				combineOperationLesson(pathname, lesson, ResourceDumpConstant.DUMP_ALL_FILE_STORE_PATH + File.separator + DUMP_OJ_FILE_STORE_PATH + File.separator + ResourceDumpConstant.DUMP_ALL_COURSE_FILE_STORE_PATH);
			}

		}

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
		log.info(String.format("dump lesson %s readme.md successfully!", lesson.getName()));


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
		sceneDumpContext.setEnv_path(envFile);
		sceneDumpContext.setImage_path(destFilePath + File.separator + "images");
		remoteSceneService.sceneDump(sceneDumpContext);
		log.info(String.format("dump lesson %s env & images successfully!", lesson.getName()));
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
				File file = new File(handoutFile, lesson.getType().equals(CourseLesson.LessonType.THEORY.getCode()) ? HANDOUT_FILE_NAME : DOC_FILE_NAME);
				String markdown = MdUtil.dumpMarkdownImages(file, handoutUrl);
				saveAsFileWriter(markdown, file.getAbsolutePath());
			}
			log.info(String.format("dump lesson %s handout successfully!", lesson.getName()));
		}
	}

	private void generateLessonBase(String pathname, CourseLesson lesson,List<Paper> papers,
									List<PracticeTheoryRef> practiceTheoryRefs, List<PracticeOperationRef> practiceOperationRefs) {
		// 存储附件
		String attachFile = pathname + File.separator + "attachments";
		FileUtil.mkdirs(attachFile);
		String attachmentIds = lesson.getAttachmentIds();
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(attachmentIds, false);
		if(attachmentBos.size() > 0){
			List<String> attachFiles = StreamApiUtils.map(attachmentBos, AttachmentBo::getFilePath);
			for(String attach:attachFiles){
				saveAttachFile(attach, attachFile);
			}
			log.info(String.format("dump lesson %s attachFile successfully!", lesson.getName()));

		}

		// 存储video
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
			log.info(String.format("dump lesson %s video successfully!", lesson.getName()));
		}

		String practices = pathname + File.separator + "practices";
		// 存储practices
		FileUtil.mkdirs(practices);
		String paperId = lesson.getPaperId();
		if (null != paperId && !paperId.isEmpty()){
			List<Paper> papers1 = papers.stream().filter(s -> s.getResourceId().equals(paperId)).collect(Collectors.toList());
			if (papers1.size() > 0) {
				// TODO模板为空　待写入
				List<PracticeTheoryRef> theoryRefs = practiceTheoryRefs.stream().filter(s -> s.getPaperId().equals(paperId)).collect(Collectors.toList());

				List<PracticeOperationRef> operationRefs = practiceOperationRefs.stream().filter(s -> s.getPaperId().equals(paperId)).collect(Collectors.toList());
				log.info(String.format("get lesson %s practices successfully!", lesson.getName()));
				// TODO习题导出excel
				// FileUtil.copyFile(videoUrl, videoPath, false);
			}
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
		String logo = course.getLogo();
		String fileSuffix = "png";
		String logoUrl = destFilePath + File.separator + "logo." + fileSuffix;
		if(logo.isEmpty()){
			FileUtil.copyFile(DUMP_COURSE_DEFAULT_LOGO_STORE_PATH, logoUrl, false);
		} else {
			String realPath = utilService.getRealPath(logo);
			fileSuffix = FileUtil.getFileSuffix(realPath);
			logoUrl = destFilePath + File.separator + "logo." + fileSuffix;
			FileUtil.copyFile(realPath, logoUrl, false);
		}
		log.info(String.format("dump course %s logo successfully!", course.getName()));
		// 3.生成READEME.md
		String categoryName = categories.stream().filter(s -> s.getResourceId().equals(course.getCategoryId())).collect(Collectors.toList()).get(0).getName();
		List<UserInfo> users = allUsers.stream().filter(s -> s.getUserId().equals(course.getCreateUserId())).collect(Collectors.toList());
		String userName = users.size() > 0 ? users.get(0).getName() : "";

		generateReadmeMD(destFilePath, course, categoryName, userName);
		log.info(String.format("dump course %s readme.md successfully!", course.getName()));
		// 4.生成课程大纲md
		generateOutlineMD(destFilePath, course.getOutline());
		log.info(String.format("dump course %s outline.md successfully!", course.getName()));

		FileUtil.mkdirs(destFilePath + File.separator + "images");
	}

	private void generateOutlineMD(String destFilePath, String outline) {
		File file = new File(destFilePath,OUTLINE_NAME);
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
