/**
 * 这段代码是一个Java类 `CourseDumpServiceImpl`，实现了 `CourseDumpService` 接口。该类主要用于处理课程资源的导出操作。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **课程导出**：根据给定的资源ID (`resourceId`) 和其他参数（如是否导出拓扑、场景数等），将课程及其相关数据导出到指定路径。 
 * 2. **日志记录**：使用日志记录关键步骤，便于调试和问题追踪。 
 * 3. **错误处理**：在关键步骤中捕获并处理异常，确保程序的健壮性。 
 * ### 主要方法 
 * - **`rMCourseDump`**：主方法，负责整体的课程导出流程。 
 * - **`combineLessons`**：合并课时数据。 
 * - **`combineChapterFirst`**：处理章节数据。 
 * - **`combineChapterSecond`**：处理具体课时的数据。 
 * - **`generateLessonBase`**：生成课时的基本信息，包括附件、视频等。 
 * - **`combineTheoryLesson`** 和 **`combineOperationLesson`**：分别处理理论课和实验课的具体内容。 
 * - **`generateReadmeMD`** 和 **`generateOutlineMD`**：生成课程的README和大纲文件。 
 * ### 数据处理 
 * - **课程数据**：通过 `courseMapper` 获取课程信息。 
 * - **课时数据**：通过 `courseLessonMapper` 获取课时信息。 
 * - **章节数据**：通过 `lessonConfigMapper` 获取章节信息。 
 * - **用户数据**：通过 `remoteUserService` 获取用户信息。 
 * ### 工具和依赖 
 * - **日志记录**：使用 `slf4j` 进行日志记录。 
 * - **文件操作**：使用 `FileUtil` 进行文件创建和复制。 
 * - **MD文件处理**：使用 `MdParser` 和 `MdLesson` 进行Markdown文件的生成和写入。 
 * - **附件处理**：通过 `attachmentService` 获取附件信息。 
 * ### 错误处理 
 * - 在关键步骤中抛出 `BusinessException` 异常，例如当课程或章节不存在时。 
 * - 使用 `try-catch` 块捕获并记录异常，确保程序不会因未处理的异常而崩溃。 
 * ### 总结 
 * 该类主要负责课程资源的导出操作，从数据库中读取课程和课时信息，处理文件的创建和复制，并生成相关的Markdown文件。通过丰富的日志记录和异常处理机制，确保了系统的稳定性和可维护性。 
**/
package com.cyberpeace.oj.course.service.impl;

import static com.cyberpeace.common.util.MdUtil.saveAsFileWriter;
import static com.cyberpeace.oj.ojbase.constant.ResourceDumpConstant.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;

/**
 * 课程转 储Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see CourseDumpService
 * @since 1.0.0
 */
@Slf4j(topic = "resourceImport")
@Service
public class CourseDumpServiceImpl implements CourseDumpService {

	/**
	 * 附件Service
	 *
	 * @see AttachmentService
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 课程Mapper
	 *
	 * @see CourseMapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课程课Mapper
	 *
	 * @see CourseLessonMapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 实践理论裁判Service
	 *
	 * @see PracticeTheoryRefService
	 */
	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	/**
	 * 实践实操裁判Service
	 *
	 * @see PracticeOperationRefService
	 */
	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * utilService
	 *
	 * @see UtilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 课配置Mapper
	 *
	 * @see LessonConfigMapper
	 */
	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	/**
	 * 课程类别Mapper
	 *
	 * @see CourseCategoryMapper
	 */
	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	/**
	 * r mcourse dump
	 *
	 * @param resourceId         资源id
	 * @param isDumpTopo         是转 储拓扑
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see String
	 * @see Boolean
	 * @see String
	 */
	@Override
	public String rMCourseDump(String resourceId, Boolean isDumpTopo, String sceneNumber, Boolean disableImageExport) {
		log.info("----------course dump start-----------");
		String destFilePath = "";
		// 1.获取所有课程数据 内容及文件
		Course course = courseMapper.selectById(resourceId);
		if (course == null) {
			log.error("The course export ID [{}] does not match any course in db!", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_ID_NOT_EXIST, resourceId);
		}
		log.info("get course [{}] successfully!", course.getName());

		// 2.获取所有课时数据 内容及文件
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("course_id", resourceId);
		List<CourseLesson> courseLessons = courseLessonMapper.selectByMap(filterMap);
		// 根据目录索引生成课时文件名 node_type分章节 课时 course_id查询
		List<LessonConfig> lessonConfigs = lessonConfigMapper.selectByMap(filterMap);

		Map<String, Object> categoryFilterMap = new HashMap<>();
		String categoryId = course.getCategoryId();
		filterMap.put("resource_id", categoryId);
		List<CourseCategory> categories = courseCategoryMapper.selectByMap(categoryFilterMap);

		List<UserInfo> allUsers = remoteUserService.getAllUsers();
		log.info("get all users successfully!");

		Map<String, List<CourseLesson>> lessonCol = courseLessons.stream()
				.collect(Collectors.groupingBy(CourseLesson::getCourseId));
		Map<String, List<LessonConfig>> lessonConfigCol = lessonConfigs.stream()
				.collect(Collectors.groupingBy(LessonConfig::getCourseId));
		String oj = DUMP_ALL_FILE_STORE_PATH;
		FileUtil.mkdirs(oj);
		String coursePath = oj + ResourceDumpConstant.DUMP_ALL_COURSE_FILE_STORE_PATH;

		destFilePath = coursePath + File.separatorChar + course.getName();
		FileUtil.mkdirs(destFilePath);

		String courseId = course.getResourceId();
		List<LessonConfig> currentLessonConfigs = lessonConfigCol.get(courseId);
		List<String> courseConfigId = currentLessonConfigs.stream()
				.filter(s -> s.getNodeType().equals(LessonConfig.NodeType.NODE_COURSE.getCode()))
				.map(LessonConfig::getResourceId).collect(Collectors.toList());
		if (courseConfigId.isEmpty()) {
			log.error("The lesson config  to course export ID [{}] does not exist!", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_ID_NOT_EXIST);
		}

		List<CourseLesson> currentLessons = lessonCol.get(courseId);

		List<LessonConfig> lessonWithNoChapter = currentLessonConfigs.stream()
				.filter(s -> s.getNodeType().equals(LessonConfig.NodeType.NODE_LESSON.getCode())
						&& s.getParentId().equals(courseConfigId.get(0)))
				.collect(Collectors.toList());
		List<LessonConfig> chapterOnes = StreamApiUtils.filter(currentLessonConfigs,
				s -> s.getNodeType().equals(LessonConfig.NodeType.NODE_CHAPTER.getCode()));
		if (!chapterOnes.isEmpty() && !lessonWithNoChapter.isEmpty()) {
			List<String> lessonids = StreamApiUtils.getColumn(lessonWithNoChapter,
					LessonConfig::getLessonId);
			List<CourseLesson> lessons = courseLessonMapper.selectBatchIds(lessonids);
			List<String> lessonNames = StreamApiUtils.getColumn(lessons, CourseLesson::getName);
			String join = String.join(",", lessonNames);
			log.error("Lesson [{}] has no assigned chapter.", join);
			throw new BusinessException(OjBusinessErrorCode.LESSON_WITHOUT_CHAPTER, join);
		}

		try {
			combineCourseContent(destFilePath, course, categories, allUsers);
			if (null != currentLessons) {
				combineLessons(destFilePath, currentLessonConfigs, currentLessons, isDumpTopo, sceneNumber, disableImageExport);
			}
			log.info("course [{}] dump finish the path is [{}]", course.getName(), destFilePath);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("resource dump failed! catch error: ", e);
		}
		log.info("---------dump course [{}] success--------", course.getName());
		return destFilePath;
	}

	/**
	 * 合并课程
	 *
	 * @param destFilePath         目标文件路径
	 * @param currentLessonConfigs 当前课程配置
	 * @param currentLessons       当前课程
	 * @param isDumpTopo           是转 储拓扑
	 * @param sceneNumber          场景数
	 * @param disableImageExport   禁用图像导出
	 * @throws
	 * @see String
	 * @see List<LessonConfig>
	 * @see List<CourseLesson>
	 * @see Boolean
	 * @see String
	 * @see Boolean
	 */
	private void combineLessons(String destFilePath, List<LessonConfig> currentLessonConfigs,
			List<CourseLesson> currentLessons, Boolean isDumpTopo, String sceneNumber, Boolean disableImageExport) {
		Map<String, List<CourseLesson>> lessonCols = currentLessons.stream()
				.collect(Collectors.groupingBy(CourseLesson::getResourceId));

		List<LessonConfig> chapterFirsts = currentLessonConfigs.stream()
				.filter(s -> null != s.getNodeType()
						&& s.getNodeType().equals(LessonConfig.NodeType.NODE_CHAPTER.getCode()))
				.sorted(Comparator.comparing(LessonConfig::getIndex)).collect(Collectors.toList());
		log.info("get chapterFirst of the current lesson successfully");

		if (chapterFirsts.isEmpty()) {
			int index = 1;
			List<LessonConfig> chapterSeconds = currentLessonConfigs.stream()
					.filter(s -> null != s.getNodeType()
							&& s.getNodeType().equals(LessonConfig.NodeType.NODE_LESSON.getCode()))
					.sorted(Comparator.comparing(LessonConfig::getIndex))
					.collect(Collectors.toList());
			for (LessonConfig chapterSecond : chapterSeconds) {
				combineChapterSecond(destFilePath, 1, index, chapterSecond,
						destFilePath + File.separator + "1_chapter", lessonCols, isDumpTopo, sceneNumber, disableImageExport);
				index += 1;
			}
		} else {
			StreamApiUtils.map(chapterFirsts,
					chapterFirst -> combineChapterFirst(destFilePath,
							chapterFirsts.indexOf(chapterFirst) + 1, chapterFirst,
							currentLessonConfigs, lessonCols, isDumpTopo, sceneNumber, disableImageExport));
		}

	}

	/**
	 * 合并章节第一
	 *
	 * @param destFilePath       目标文件路径
	 * @param chapterIndex       章节索引
	 * @param chapterOne         第一章
	 * @param lessonConfigs      课程配置
	 * @param lessons            课
	 * @param isDumpTopo         是转 储拓扑
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @return {@code Map<String, String> }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see LessonConfig
	 * @see List<LessonConfig>
	 * @see Map<String, List<CourseLesson>>
	 * @see Boolean
	 * @see String
	 * @see Boolean
	 * @see Map<String, String>
	 */
	private Map<String, String> combineChapterFirst(String destFilePath, Integer chapterIndex,
			LessonConfig chapterOne, List<LessonConfig> lessonConfigs,
			Map<String, List<CourseLesson>> lessons, Boolean isDumpTopo, String sceneNumber, Boolean disableImageExport) {

		Map<String, String> filePathMap = new HashMap<>();
		Integer currentChapter = chapterOne.getIndex();
		String chapterFileName = destFilePath + File.separator + chapterIndex + "_"
				+ chapterOne.getName();
		FileUtil.mkdirs(chapterFileName);
		int index = 1;
		List<LessonConfig> chapterSeconds = lessonConfigs.stream().filter(
				s -> null != s.getParentId() && s.getParentId().equals(chapterOne.getResourceId()))
				.sorted(Comparator.comparing(LessonConfig::getIndex)).collect(Collectors.toList());
		for (LessonConfig chapterSecond : chapterSeconds) {
			combineChapterSecond(destFilePath, chapterIndex, index, chapterSecond,
					chapterFileName, lessons, isDumpTopo, sceneNumber, disableImageExport);
			index += 1;
		}
		return filePathMap;
	}

	/**
	 * 结合第二章
	 *
	 * @param destFilePath       目标文件路径
	 * @param lastChapter        上一章
	 * @param index              指数
	 * @param chapterSecond      第二章
	 * @param chapterFileName    章文件名称
	 * @param lessons            课
	 * @param isDumpTopo         是转 储拓扑
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @throws
	 * @see String
	 * @see Integer
	 * @see int
	 * @see LessonConfig
	 * @see String
	 * @see Map<String, List<CourseLesson>>
	 * @see Boolean
	 * @see String
	 * @see Boolean
	 */
	private void combineChapterSecond(String destFilePath, Integer lastChapter, int index,
			LessonConfig chapterSecond, String chapterFileName,
			Map<String, List<CourseLesson>> lessons, Boolean isDumpTopo, String sceneNumber, Boolean disableImageExport) {
		CourseLesson lesson = lessons.get(chapterSecond.getLessonId()).get(0);

		String courseId = lesson.getCourseId();
		Integer minIndex = courseLessonMapper.selectMinIndex(courseId);
		Integer chapterCount = courseLessonMapper.selectChapterCount(courseId);
		if(minIndex.equals(0) && chapterCount != 0){
			lastChapter += 1;
		}
		String pathname = chapterFileName + File.separator + lastChapter + "-" + index + "_"
				+ lesson.getName();

		FileUtil.mkdirs(pathname);
		// 课时内容基础保存
		generateLessonBase(pathname, lesson, disableImageExport);
		// TODO文件是否均
		if (Objects.equals(lesson.getType(), CourseLesson.LessonType.THEORY.getCode())) {
			// 理论课文件保存
			combineTheoryLesson(pathname, lesson);
		} else {
			// 实验课文件保存
			if (Boolean.TRUE.equals(isDumpTopo)) {
				combineOperationLesson(destFilePath, pathname, lesson, sceneNumber, disableImageExport);
			}

		}

	}

	/**
	 * COMBINE 课程 README MD
	 *
	 * @param pathname 路径
	 * @param lesson   课
	 * @throws
	 * @see String
	 * @see CourseLesson
	 */
	private void combineLessonReadmeMD(String pathname, CourseLesson lesson) {
		log.info("start dump lesson [{}] readme.md!", lesson.getName());
		String readmeMDFileName = pathname + File.separator + "README.md";
		MdParser mdParser = new MdParser();
		MdLesson mdLesson = new MdLesson();
		mdLesson.setLessonName(lesson.getName());
		String lessonType = Arrays.stream(CourseLesson.LessonType.values())
				.filter(it -> Objects.equals(it.getCode(), lesson.getType()))
				.collect(Collectors.toList()).get(0).getDesc();
		mdLesson.setType(lessonType);
		// TODO待修改
		mdLesson.setDifficulty(lesson.getDifficulty());
		mdLesson.setDuration(lesson.getDuration().toString());
		mdLesson.setKnowledge(lesson.getKnowledge());
		String compulsory = Arrays.stream(CourseLesson.CompulsoryType.values())
				.filter(it -> Objects.equals(it.getCode(),
						lesson.getCompulsory().equals(CourseLesson.CompulsoryType.NOT_CONFIGURED.getCode()) ?
								CourseLesson.CompulsoryType.ELECTIVE.getCode() : lesson.getCompulsory()))
				.collect(Collectors.toList()).get(0).getDesc();
		mdLesson.setCompulsory(compulsory.equals(CourseLesson.CompulsoryType.NOT_CONFIGURED.getDesc()) ? CourseLesson.CompulsoryType.ELECTIVE.getDesc() : compulsory);
		mdParser.write(mdLesson, new File(readmeMDFileName));
		log.info("dump lesson [{}] readme.md successfully!", lesson.getName());

	}

	/**
	 * 合实操课
	 *
	 * @param destFilePath       目标文件路径
	 * @param pathname           路径
	 * @param lesson             课
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @throws
	 * @see String
	 * @see String
	 * @see CourseLesson
	 * @see String
	 * @see Boolean
	 */
	private void combineOperationLesson(String destFilePath, String pathname, CourseLesson lesson, String sceneNumber, Boolean disableImageExport) {
		// doc 指导文档doc/pdf/md 包含img文件夹 指导书md
		String docFile = pathname + File.separator + "doc";
		FileUtil.mkdirs(docFile);

		generateHandout(lesson, docFile);

		// env 文件夹包含topo.yml
		log.info("start dump lesson [{}] env & images!", lesson.getName());
		String envFile = pathname + File.separator + "env";
		FileUtil.mkdirs(envFile);
		SceneDumpContext sceneDumpContext = new SceneDumpContext();
		sceneDumpContext.setId(lesson.getTopoId());
		sceneDumpContext.setEnv_path(envFile);
		sceneDumpContext.setImage_path(destFilePath +  File.separator + IMAGE_NAME);
		sceneDumpContext.setScene_number(sceneNumber);
		sceneDumpContext.setDisable_image_export(disableImageExport);
		remoteSceneService.sceneDump(sceneDumpContext);
		log.info("dump lesson [{}] env & images successfully!", lesson.getName());
	}

	/**
	 * 合理论课
	 *
	 * @param pathname 路径
	 * @param lesson   课
	 * @throws
	 * @see String
	 * @see CourseLesson
	 */
	private void combineTheoryLesson(String pathname, CourseLesson lesson) {
		// handout 讲义

		String handoutFile = pathname + File.separator + "handout";
		FileUtil.mkdirs(handoutFile);
		generateHandout(lesson, handoutFile);

	}

	/**
	 * 生成讲义
	 *
	 * @param lesson      课
	 * @param handoutFile 讲义文件
	 * @throws
	 * @see CourseLesson
	 * @see String
	 */
	private void generateHandout(CourseLesson lesson, String handoutFile) {
		Integer handoutType = lesson.getHandoutType();
		String handoutUrl = lesson.getHandoutUrl();

		if (null != handoutUrl && !handoutUrl.isEmpty()) {
			log.info("start dump lesson [{}] handout!", lesson.getName());
			if (!handoutType.equals(CourseLesson.HandoutType.MD.getCode())) {

				if (!handoutUrl.startsWith(ConfigUtil.getContextProperty("cr.local.upload.path"))) {
					handoutUrl = ConfigUtil.getContextProperty("cr.local.upload.path")
							+ File.separator + handoutUrl;
				}
				String[] split = handoutUrl.split("\\.");
				String fileSuffix = split[split.length - 1];
				String handoutFileName =  lesson.getType().equals(CourseLesson.LessonType.THEORY.getCode()) ? HANDOUT_OTHER_FILE_NAME + "." + fileSuffix : DOC_OTHER_FILE_NAME  + "." + fileSuffix;
				FileUtil.copyFile(handoutUrl, handoutFile + File.separator + handoutFileName,
						false);
			} else {
				File file = new File(handoutFile,
						lesson.getType().equals(CourseLesson.LessonType.THEORY.getCode())
								? HANDOUT_FILE_NAME
								: DOC_FILE_NAME);
				String markdown = MdUtil.dumpMarkdownImages(file, handoutUrl);
				saveAsFileWriter(markdown, file.getAbsolutePath());
			}
			log.info("dump lesson [{}] handout successfully!", lesson.getName());
		}
	}

	/**
	 * 生成课程库
	 *
	 * @param pathname           路径
	 * @param lesson             课
	 * @param disableImageExport 禁用图像导出
	 * @throws
	 * @see String
	 * @see CourseLesson
	 * @see Boolean
	 */
	private void generateLessonBase(String pathname, CourseLesson lesson, Boolean disableImageExport) {
		// 存储附件
		log.info("start dump lesson [{}] attachFile!", lesson.getName());
		String attachFile = pathname + File.separator + "attachments";
		FileUtil.mkdirs(attachFile);
		String attachmentIds = lesson.getAttachmentIds();
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(attachmentIds, false);
		if (!attachmentBos.isEmpty()) {
			List<String> attachFiles = StreamApiUtils.map(attachmentBos, AttachmentBo::getFilePath);
			for (String attach : attachFiles) {
				saveAttachFile(attach, attachFile);
			}
			log.info("dump lesson [{}] attachFile successfully!", lesson.getName());

		}

		// 存储video
		log.info("start dump lesson [{}] video!", lesson.getName());
		String videoPath = pathname + File.separator + "video";
		FileUtil.mkdirs(videoPath);
		String videoUrl = lesson.getVideoUrl();
		if (null != videoUrl && !videoUrl.isEmpty()) {
			String[] split = videoUrl.split("/");
			String videoName = split[split.length - 1];
			if (!videoUrl.startsWith("/cpms")) {
				videoUrl = "/cpms" + File.separator + videoUrl;
			}
			FileUtil.copyFile(videoUrl, videoPath + File.separator + videoName, false);
			log.info("dump lesson [{}] video successfully!", lesson.getName());
		}

		// 课时readme.md生成
		combineLessonReadmeMD(pathname, lesson);

		// 导出理论题
		practiceTheoryRefService.exportLessonPractice(
				pathname + File.separator + ResourceImportConstant.PRACTICES, lesson.getPaperId());

		practiceOperationRefService.exportLessonPractice(
				pathname + File.separator + ResourceImportConstant.OPERATION, lesson.getPaperId(), disableImageExport);

	}

	/**
	 * 保存附加文件
	 *
	 * @param attachFile 附加文件
	 * @param pathname   路径
	 * @throws
	 * @see String
	 * @see String
	 */
	private void saveAttachFile(String attachFile, String pathname) {
		String[] split = attachFile.split("/");
		String fileName = split[split.length - 1];
		FileUtil.copyFile(attachFile, pathname + File.separator + fileName, false);
	}

	/**
	 * 合并课程内容
	 *
	 * @param destFilePath 目标文件路径
	 * @param course       课程
	 * @param categories   类别
	 * @param allUsers     所有用户
	 * @throws
	 * @see String
	 * @see Course
	 * @see List<CourseCategory>
	 * @see List<UserInfo>
	 */
	private void combineCourseContent(String destFilePath, Course course,
			List<CourseCategory> categories, List<UserInfo> allUsers) {
		// logo保存
		log.info("start dump course [{}] logo!", course.getName());
		String logo = course.getLogo();
		String fileSuffix;
		String logoUrl;
		File file = new File(DUMP_COURSE_DEFAULT_LOGO_STORE_PATH);
		if (logo.isEmpty()) {
			if (!file.exists()) {
				log.error(
						"no default course logo! please manually copy logo.png under /cpms/built_in!");
				throw new BusinessException(OjBusinessErrorCode.NO_DEFAULT_COURSE_LOGO,
						course.getName());
			}
			FileUtil.copyFile(DUMP_COURSE_DEFAULT_LOGO_STORE_PATH, destFilePath + File.separator + "logo.png", false);
			course.setLogo(DUMP_COURSE_DEFAULT_LOGO_STORE_PATH);
		} else {
			String realPath = utilService.getRealPath(logo);
			fileSuffix = FileUtil.getFileSuffix(realPath);
			logoUrl = destFilePath + File.separator + "logo." + fileSuffix;
			FileUtil.copyFile(realPath, logoUrl, false);
		}
		log.info("dump course [{}] logo successfully!", course.getName());
		// 3.生成READEME.md
		String categoryName = categories.stream()
				.filter(s -> s.getResourceId().equals(course.getCategoryId()))
				.collect(Collectors.toList()).get(0).getName();
		List<UserInfo> users = allUsers.stream()
				.filter(s -> s.getUserId().equals(course.getCreateUserId()))
				.collect(Collectors.toList());
		String userName = !users.isEmpty() ? users.get(0).getName() : "";

		log.info("start dump course [{}] readme.md!", course.getName());
		generateReadmeMD(destFilePath, course, categoryName, userName);
		log.info("dump course [{}] readme.md successfully!", course.getName());
		// 4.生成课程大纲md

		log.info("start dump course [{}] outline.md!", course.getName());
		generateOutlineMD(destFilePath, course.getOutline());
		log.info("dump course [{}] outline.md successfully!", course.getName());

		FileUtil.mkdirs(destFilePath + File.separator + IMAGE_NAME);
	}

	/**
	 * 生成大纲 MD
	 *
	 * @param destFilePath 目标文件路径
	 * @param outline      大纲
	 * @throws
	 * @see String
	 * @see String
	 */
	private void generateOutlineMD(String destFilePath, String outline) {
		File file = new File(destFilePath, OUTLINE_NAME);
		saveAsFileWriter(outline, file.getAbsolutePath());
	}

	/**
	 * 生成 README MD
	 *
	 * @param pathname     路径
	 * @param course       课程
	 * @param categoryName 类别名称
	 * @param userName     用户名称
	 * @throws
	 * @see String
	 * @see Course
	 * @see String
	 * @see String
	 */
	private void generateReadmeMD(String pathname, Course course, String categoryName,
			String userName) {
		String readmeMDFileName = pathname + File.separator + "README.md";
		MdParser mdParser = new MdParser();
		CourseMd courseMd = BeanUtils.copy(course, CourseMd.class);
		courseMd.setCategoryId(categoryName);
		courseMd.setCreateUserId(null == userName ? "" : userName);
		mdParser.write(courseMd, new File(readmeMDFileName));

	}
}
