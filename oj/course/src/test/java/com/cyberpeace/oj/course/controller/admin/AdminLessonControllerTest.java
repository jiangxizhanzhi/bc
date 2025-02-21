
package com.cyberpeace.oj.course.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.LessonSceneService;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonPracticeBo;
import com.cyberpeace.oj.ojbase.service.bo.MonitorInfoBo;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminLessonController.class)
class AdminLessonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseLessonService mockCourseLessonService;

	@MockBean
	private CourseLessonMapper mockCourseLessonMapper;

	@MockBean
	private AttachmentService mockAttachmentService;

	@MockBean
	private CourseService mockCourseService;

	@MockBean
	private LessonSceneService mockLessonSceneService;

	@MockBean
	private CourseMapper mockCourseMapper;

	@MockBean
	private UtilService mockUtilService;

	@MockBean
	private RemoteLogService mockRemoteLogService;

	@BeforeEach
	void setUp() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("admin");
		userInfo.setPermissions(Sets.newHashSet("courses"));
		UserInfoContext.setUserContext(userInfo);

	}

	@Test
	void testGetLessonList() throws Exception {
		// Setup
		when(mockCourseService.selectIdsByCategory(any(), any(AuthContext.class)))
				.thenReturn(Lists.newArrayList("course1"));

		// Configure CourseLessonService.pageByContext(...).
		final LessonContext context = new LessonContext();
		context.setType(0);
		context.setCourseIds(Arrays.asList("course1"));

		IPage<AdminLessonListBo> page = new Page<>();
		AdminLessonListBo bo = new AdminLessonListBo();
		bo.setName("lesson1");
		bo.setResourceId("lessonId1");
		page.setRecords(Lists.newArrayList(bo));
		when(mockCourseLessonService.pageByContext(any(Page.class), eq(context), eq(false)))
				.thenReturn(page);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/list").param("course_id", "course1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"lessonId1\",\"courseId\":null,\"courseName\":null,\"type\":null,\"name\":\"lesson1\",\"handoutType\":null,\"handoutUrl\":null,\"videoUrl\":null,\"videoDuration\":null,\"difficulty\":null,\"isPublic\":null,\"compulsory\":null,\"duration\":null,\"topoId\":null,\"createUserId\":null,\"createUserName\":null,\"practiceCount\":null,\"paperPublic\":null,\"hasReport\":null,\"lessonFile\":null,\"sceneShareMode\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testGet() throws Exception {
		// Setup
		// Configure CourseLessonService.selectByCourseAndLesson(...).
		final AdminLessonBo adminLessonBo = new AdminLessonBo();
		adminLessonBo.setResourceId("lessonId1");
		adminLessonBo.setCourseId("courseId1");
		adminLessonBo.setName("name");
		adminLessonBo.setType(0);
		adminLessonBo.setHandoutType(0);
		when(mockCourseLessonService.selectByCourseAndLesson("courseId1", "lessonId1"))
				.thenReturn(adminLessonBo);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson").param("course_id", "courseId1")
				.param("lesson_id", "lessonId1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"resourceId\":\"lessonId1\",\"courseId\":\"courseId1\",\"name\":\"name\",\"type\":0,\"handoutType\":0,\"handoutUrl\":null,\"handoutName\":null,\"originMarkdownFile\":null,\"videoUrl\":null,\"difficulty\":null,\"isPublic\":null,\"compulsory\":null,\"destroyDelay\":null,\"duration\":null,\"topoId\":null,\"sceneShareMode\":null,\"sceneConfig\":null,\"practiceCount\":null,\"paperId\":null,\"knowledge\":null,\"videoDuration\":null,\"lessonFile\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testAdd() throws Exception {
		// Setup
		// Configure CourseLessonService.addLesson(...).
		final CourseLesson lesson = new CourseLesson();
		lesson.setCourseId("courseId1");
		lesson.setName("newLesson");
		lesson.setHandoutUrl("");
		lesson.setVideoUrl("");
		lesson.setAttachmentIds("[]");
		lesson.setKnowledge("[]");
		lesson.setCreateUserId("admin");
		when(mockCourseLessonService.addLesson(any(), any(), eq(Lists.newArrayList()))).thenReturn(lesson);

		// Configure CourseMapper.selectById(...).
		final Course course = new Course();
		course.setResourceId("courseId1");
		course.setBuiltin(false);
		course.setName("course1");
		course.setDescription("description");
		course.setCategoryId("categoryId");
		when(mockCourseMapper.selectById("courseId1")).thenReturn(course);

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/lesson")
						.param("name", "newLesson")
						.param("course_id", "courseId1")
						.param("attachment_ids", "")
						.param("knowledge", "[]")
						.param("type", "0")
						.param("difficulty", "0")
						.param("is_public", "0")
						.param("duration", "5")
						.param("compulsory", "0")
						.param("practice_list", "[]")
						.param("oj_scene_config", "{\"scene_config\":{}}")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"resourceId\":null,\"builtin\":null,\"courseId\":\"courseId1\",\"name\":\"newLesson\",\"type\":null,\"handoutType\":null,\"handoutUrl\":\"\",\"videoUrl\":\"\",\"difficulty\":null,\"isPublic\":null,\"attachmentIds\":\"[]\",\"compulsory\":null,\"destroyDelay\":null,\"duration\":null,\"topoId\":null,\"knowledge\":\"[]\",\"sceneShareMode\":null,\"originMarkdownFile\":null,\"createUserId\":\"admin\",\"paperId\":null,\"heat\":null,\"paperPublic\":null,\"path\":null,\"videoDuration\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));

		// Confirm CourseLessonService.uploadVideo(...).
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.COURSE),
				eq(OperationType.CREATE), eq("course1-newLesson"), eq("课程名：course1，课时名：newLesson。"));
	}

	@Test
	void testPatch() throws Exception {
		// Setup
		when(mockUtilService.replaceFileUrl("videoUrl")).thenReturn("");

		// Configure CourseLessonMapper.selectById(...).
		final CourseLesson lesson = new CourseLesson();
		lesson.setResourceId("lessonId1");
		lesson.setCourseId("courseId1");
		lesson.setName("newLesson");
		lesson.setHandoutUrl("");
		lesson.setVideoUrl("");
		lesson.setAttachmentIds("[]");
		lesson.setKnowledge("[]");
		lesson.setCreateUserId("admin");
		when(mockCourseLessonMapper.selectById("lessonId1")).thenReturn(lesson);

		// Configure CourseLessonService.updateLesson(...).
		when(mockCourseLessonService.updateLesson(any(), any(JSONObject.class),
				eq(Lists.newArrayList()))).thenReturn(lesson);

		// Configure CourseMapper.selectById(...).
		final Course course = new Course();
		course.setResourceId("courseId1");
		course.setBuiltin(false);
		course.setName("courseName1");
		course.setDescription("description");
		course.setCategoryId("categoryId");
		when(mockCourseMapper.selectById("courseId1")).thenReturn(course);

		// Run the test and verify the results
		mockMvc.perform(patch("/oj/admin/lesson")
				.param("name", "newLesson")
				.param("resource_id", "lessonId1")
				.param("course_id", "courseId1")
				.param("attachment_ids", "")
				.param("knowledge", "[]")
				.param("type", "0")
				.param("difficulty", "0")
				.param("is_public", "0")
				.param("duration", "5")
				.param("compulsory", "0")
				.param("practice_list", "[]")
				.param("oj_scene_config", "{\"scene_config\":{}}")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"resourceId\":\"lessonId1\",\"builtin\":null,\"courseId\":\"courseId1\",\"name\":\"newLesson\",\"type\":null,\"handoutType\":null,\"handoutUrl\":\"\",\"videoUrl\":\"\",\"difficulty\":null,\"isPublic\":null,\"attachmentIds\":\"[]\",\"compulsory\":null,\"destroyDelay\":null,\"duration\":null,\"topoId\":null,\"knowledge\":\"[]\",\"sceneShareMode\":null,\"originMarkdownFile\":null,\"createUserId\":\"admin\",\"paperId\":null,\"heat\":null,\"paperPublic\":null,\"path\":null,\"videoDuration\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));

		// Confirm CourseLessonService.uploadVideo(...).
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.COURSE),
				eq(OperationType.UPDATE), eq("courseName1-newLesson"), eq("课程名：courseName1，课时名：newLesson。"));
	}

	@Test
	void testDelete() throws Exception {
		// Setup
		// Configure CourseLessonMapper.selectById(...).
		final CourseLesson lesson = new CourseLesson();
		lesson.setCourseId("courseId");
		lesson.setName("name");
		lesson.setHandoutUrl("");
		lesson.setVideoUrl("");
		lesson.setAttachmentIds("attachmentIds");
		lesson.setKnowledge("knowledge");
		lesson.setCreateUserId("createUserId");
		lesson.setPaperId("paperId");
		when(mockCourseLessonMapper.selectById("id")).thenReturn(lesson);

		// Configure CourseMapper.selectById(...).
		final Course course = new Course();
		course.setResourceId("resourceId");
		course.setBuiltin(false);
		course.setName("name");
		course.setDescription("description");
		course.setCategoryId("categoryId");
		when(mockCourseMapper.selectById("courseId")).thenReturn(course);

		// Configure CourseLessonService.listByIds(...).
		final CourseLesson lesson1 = new CourseLesson();
		lesson1.setCourseId("courseId");
		lesson1.setName("name");
		lesson1.setHandoutUrl("");
		lesson1.setVideoUrl("");
		lesson1.setAttachmentIds("attachmentIds");
		lesson1.setKnowledge("knowledge");
		lesson1.setCreateUserId("createUserId");
		lesson1.setPaperId("paperId");
		final List<CourseLesson> lessonList = Arrays.asList(lesson1);
		when(mockCourseLessonService.listByIds(Arrays.asList("value"))).thenReturn(lessonList);

		when(mockCourseLessonService.batchDelete(eq("courseId"), eq(Arrays.asList("value")),
				any(AuthContext.class))).thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(delete("/oj/admin/lesson/batch").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.COURSE),
				eq(OperationType.DELETE), eq("operationTarget"), eq("content"));
	}

	@Test
	void testDelete_CourseLessonServiceListByIdsReturnsNoItems() throws Exception {
		// Setup
		// Configure CourseLessonMapper.selectById(...).
		final CourseLesson lesson = new CourseLesson();
		lesson.setCourseId("courseId");
		lesson.setName("name");
		lesson.setHandoutUrl("");
		lesson.setVideoUrl("");
		lesson.setAttachmentIds("attachmentIds");
		lesson.setKnowledge("knowledge");
		lesson.setCreateUserId("createUserId");
		lesson.setPaperId("paperId");
		when(mockCourseLessonMapper.selectById("id")).thenReturn(lesson);

		// Configure CourseMapper.selectById(...).
		final Course course = new Course();
		course.setResourceId("resourceId");
		course.setBuiltin(false);
		course.setName("name");
		course.setDescription("description");
		course.setCategoryId("categoryId");
		when(mockCourseMapper.selectById("courseId")).thenReturn(course);

		when(mockCourseLessonService.listByIds(Arrays.asList("value")))
				.thenReturn(Collections.emptyList());
		when(mockCourseLessonService.batchDelete(eq("courseId"), eq(Arrays.asList("value")),
				any(AuthContext.class))).thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(delete("/oj/admin/lesson/batch").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testGetAttachments() throws Exception {
		// Setup
		// Configure CourseLessonMapper.selectById(...).
		final CourseLesson lesson = new CourseLesson();
		lesson.setCourseId("courseId");
		lesson.setName("name");
		lesson.setHandoutUrl("");
		lesson.setVideoUrl("");
		lesson.setAttachmentIds("attachmentIds");
		lesson.setKnowledge("knowledge");
		lesson.setCreateUserId("createUserId");
		lesson.setPaperId("paperId");
		when(mockCourseLessonMapper.selectById("resourceId")).thenReturn(lesson);

		// Configure AttachmentService.selectByIds(...).
		final List<AttachmentBo> attachmentBos = Arrays
				.asList(AttachmentBo.builder().resourceId("data").build());
		when(mockAttachmentService.selectByIds("attachmentIds", false)).thenReturn(attachmentBos);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/attachment").param("resource_id", "resourceId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{}", true));
	}

	@Test
	void testGetAttachments_CourseLessonMapperReturnsNull() throws Exception {
		// Setup
		when(mockCourseLessonMapper.selectById("resourceId")).thenReturn(null);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/attachment").param("resource_id", "resourceId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError())
				.andExpect(content().json("{}", true));
	}

	@Test
	void testGetAttachments_AttachmentServiceReturnsNoItems() throws Exception {
		// Setup
		// Configure CourseLessonMapper.selectById(...).
		final CourseLesson lesson = new CourseLesson();
		lesson.setCourseId("courseId");
		lesson.setName("name");
		lesson.setHandoutUrl("");
		lesson.setVideoUrl("");
		lesson.setAttachmentIds("attachmentIds");
		lesson.setKnowledge("knowledge");
		lesson.setCreateUserId("createUserId");
		lesson.setPaperId("paperId");
		when(mockCourseLessonMapper.selectById("resourceId")).thenReturn(lesson);

		when(mockAttachmentService.selectByIds("attachmentIds", false))
				.thenReturn(Collections.emptyList());

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/attachment").param("resource_id", "resourceId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]", true));
	}

	@Test
	void testGetPracticeList() throws Exception {
		// Setup
		// Configure CourseLessonService.adminLessonPractice(...).
		final AdminLessonPracticeBo adminLessonPracticeBo = new AdminLessonPracticeBo();
		adminLessonPracticeBo.setAnswer("answer");
		adminLessonPracticeBo.setTitle("title");
		adminLessonPracticeBo.setContent("content");
		adminLessonPracticeBo.setOption("option");
		adminLessonPracticeBo.setDirectionName("directionName");
		final List<AdminLessonPracticeBo> adminLessonPracticeBos = Arrays
				.asList(adminLessonPracticeBo);
		when(mockCourseLessonService.adminLessonPractice(eq("courseId"), eq("lessonId"),
				any(AuthContext.class))).thenReturn(adminLessonPracticeBos);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/practice").param("course_id", "courseId")
				.param("lesson_id", "lessonId").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testGetPracticeList_CourseLessonServiceReturnsNoItems() throws Exception {
		// Setup
		when(mockCourseLessonService.adminLessonPractice(eq("courseId"), eq("lessonId"),
				any(AuthContext.class))).thenReturn(Collections.emptyList());

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/practice").param("course_id", "courseId")
				.param("lesson_id", "lessonId").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testUpdateLessonPractice() throws Exception {
		// Setup
		// Configure CourseLessonService.addLessonPractice(...).
		final PaperPracticeContext paperPracticeContext = new PaperPracticeContext();
		paperPracticeContext.setResourceId("resourceId");
		paperPracticeContext.setRefId("refId");
		paperPracticeContext.setPracticeType(0);
		paperPracticeContext.setScore(0.0);
		paperPracticeContext.setOptionRandom(false);
		final List<PaperPracticeContext> practiceList = Arrays.asList(paperPracticeContext);
		when(mockCourseLessonService.addLessonPractice("resourceId", practiceList))
				.thenReturn(false);

		// Run the test and verify the results
		mockMvc.perform(patch("/oj/admin/lesson/practice").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testPublicPractice() throws Exception {
		// Setup
		when(mockCourseLessonService.batchPublicPractice(Arrays.asList("value"), false))
				.thenReturn(false);

		// Run the test and verify the results
		mockMvc.perform(patch("/oj/admin/lesson/practice/batch_public").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testImportLesson() throws Exception {
		// Setup
		when(mockCourseLessonService.importLessons("courseId", Arrays.asList("value")))
				.thenReturn(false);

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/lesson/import").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testUploadAttachment() throws Exception {
		// Setup
		// Configure AttachmentService.uploadDest(...).
		final AttachmentBo attachmentBo = AttachmentBo.builder().resourceId("data").build();
		when(mockAttachmentService.uploadDest(eq("desc"), eq(true), any(MultipartFile.class)))
				.thenReturn(attachmentBo);

		// Run the test and verify the results
		mockMvc.perform(multipart("/oj/admin/lesson/upload_attachment")
				.file(new MockMultipartFile("attachment", "originalFilename",
						MediaType.APPLICATION_JSON_VALUE, "content".getBytes()))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{}", true));
	}

	@Test
	void testStartScene() throws Exception {
		// Setup
		when(mockLessonSceneService.startScene(eq("resourceId"), any(AuthContext.class)))
				.thenReturn(new JSONObject(0, false));

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/lesson/start_scene").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testUpdateSceneStatus() throws Exception {
		// Setup
		when(mockLessonSceneService.updateSceneStatus(eq("roomId"), eq("resourceId"), eq(0),
				any(AuthContext.class))).thenReturn(false);

		// Run the test and verify the results
		mockMvc.perform(patch("/oj/admin/lesson/update_status").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testDeleteScene() throws Exception {
		// Setup
		when(mockLessonSceneService.deleteScene(eq("resourceId"), any(AuthContext.class)))
				.thenReturn(false);

		// Run the test and verify the results
		mockMvc.perform(delete("/oj/admin/lesson/delete_scene").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testRestartNode() throws Exception {
		// Setup
		when(mockLessonSceneService.restartNode(eq("resourceId"), eq("nodeId"), eq("roomId"),
				any(AuthContext.class))).thenReturn(false);

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/lesson/node_restart").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testRecreateNode() throws Exception {
		// Setup
		when(mockLessonSceneService.recreateNode(eq("resourceId"), eq("nodeId"), eq("roomId"),
				any(AuthContext.class))).thenReturn(false);

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/lesson/node_recreate").content("content")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testLessonMonitor() throws Exception {
		// Setup
		// Configure LessonSceneService.lessonMonitor(...).
		final SceneMonitorBo sceneMonitorBo = new SceneMonitorBo();
		sceneMonitorBo.setUserId("userId");
		sceneMonitorBo.setUserName("userName");
		final MonitorInfoBo monitorInfoBo = new MonitorInfoBo();
		monitorInfoBo.setServer("server");
		monitorInfoBo.setUserName("userName");
		sceneMonitorBo.setMonitorInfo(Arrays.asList(monitorInfoBo));
		final List<SceneMonitorBo> sceneMonitorBos = Arrays.asList(sceneMonitorBo);
		when(mockLessonSceneService.lessonMonitor("lessonId")).thenReturn(sceneMonitorBos);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/lesson_monitor").param("resource_id", "lessonId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{}", true));
	}

	@Test
	void testLessonMonitor_LessonSceneServiceReturnsNoItems() throws Exception {
		// Setup
		when(mockLessonSceneService.lessonMonitor("lessonId")).thenReturn(Collections.emptyList());

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/lesson_monitor").param("resource_id", "lessonId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]", true));
	}

	@Test
	void testGetMonitorRefreshInterval() throws Exception {
		// Setup
		// Run the test and verify the results
		mockMvc.perform(
				get("/oj/admin/lesson/monitor_refresh_interval").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{}", true));
	}

	@Test
	void testSelectAllLesson() throws Exception {
		// Setup
		when(mockCourseMapper.selectByAuth(any(AuthContext.class)))
				.thenReturn(Arrays.asList("value"));

		// Configure CourseLessonMapper.selectByCourse(...).
		final CourseLesson lesson = new CourseLesson();
		lesson.setCourseId("courseId");
		lesson.setName("name");
		lesson.setHandoutUrl("");
		lesson.setVideoUrl("");
		lesson.setAttachmentIds("attachmentIds");
		lesson.setKnowledge("knowledge");
		lesson.setCreateUserId("createUserId");
		lesson.setPaperId("paperId");
		final List<CourseLesson> lessonList = Arrays.asList(lesson);
		when(mockCourseLessonMapper.selectByCourse("courseId", true)).thenReturn(lessonList);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/simple/list").param("course_id", "courseId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{}", true));
	}

	@Test
	void testSelectAllLesson_CourseMapperReturnsNoItems() throws Exception {
		// Setup
		when(mockCourseMapper.selectByAuth(any(AuthContext.class)))
				.thenReturn(Collections.emptyList());

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/simple/list").param("course_id", "courseId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError())
				.andExpect(content().json("{}", true));
	}

	@Test
	void testSelectAllLesson_CourseLessonMapperReturnsNoItems() throws Exception {
		// Setup
		when(mockCourseMapper.selectByAuth(any(AuthContext.class)))
				.thenReturn(Arrays.asList("value"));
		when(mockCourseLessonMapper.selectByCourse("courseId", true))
				.thenReturn(Collections.emptyList());

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/simple/list").param("course_id", "courseId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]", true));
	}

	@Test
	void testGetTopoScene() throws Exception {
		// Setup
		// Configure CourseLessonService.getTopoScene(...).
		final TopoSceneDataBo topoSceneDataBo = new TopoSceneDataBo();
		final JSONObject sceneConfigData = new JSONObject();
		topoSceneDataBo.setSceneConfigData(sceneConfigData);
		final JSONObject sceneData = new JSONObject();
		topoSceneDataBo.setSceneData(sceneData);
		when(mockCourseLessonService.getTopoScene(eq("lessonId"), eq("roomId"),
				any(AuthContext.class))).thenReturn(topoSceneDataBo);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lesson/scene_topo").param("resource_id", "lessonId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{}", true));
	}
}
