package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.course.controller.req.BatchDeleteReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Disabled
@WebMvcTest(AdminCourseController.class)
class AdminCourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService mockCourseService;

	@MockBean
	private CourseMapper mockCourseMapper;

	@MockBean
	private CourseCategoryService mockCourseCategoryService;

	@MockBean
	private CourseCategoryMapper mockCourseCategoryMapper;

	@MockBean
	private AttachmentService mockAttachmentService;

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
		Page page = new Page();
		page.setRecords(Lists.newArrayList());

	}

	@Test
	void testGetAdminCourse() throws Exception {
		// Setup
		when(mockCourseCategoryService.getChildrenIds("direction", CommonConstant.TRUE))
				.thenReturn(Arrays.asList("value"));

		// Configure CourseService.selectPage(...).
		final CourseWebBo courseWebBo = new CourseWebBo();
		courseWebBo.setResourceId("resourceId");
		courseWebBo.setCourseNum("courseNum");
		courseWebBo.setName("name");
		courseWebBo.setCategoryId("categoryId");
		courseWebBo.setDirectionName("directionName");
		final ListRsp<CourseWebBo> courseWebBoListRsp = new ListRsp<>(Arrays.asList(courseWebBo),
				0L, 0L, 0L);
		final CourseQueryBo courseContext = new CourseQueryBo();
		courseContext.setDirections(Arrays.asList("value"));
		courseContext.setMyCourse(false);
		courseContext.setSystemCourse(false);
		courseContext.setShareCourse(false);
		courseContext.setUserId("id");
		when(mockCourseService.selectPage(any(Page.class), eq(courseContext),
				any(AuthContext.class), eq(false))).thenReturn(courseWebBoListRsp);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/course/list").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"resourceId\",\"courseNum\":\"courseNum\",\"name\":\"name\",\"categoryId\":\"categoryId\",\"directionName\":\"directionName\",\"logo\":null,\"difficulty\":null,\"createUserId\":null,\"createUserName\":null,\"lecturer\":null,\"score\":null,\"courseType\":null,\"ebook\":null,\"lessonCount\":null,\"courseDuration\":null,\"isPublic\":null,\"role\":null,\"videoCount\":null,\"videoDuration\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testAdd() throws Exception {
		// Setup
		when(mockCourseCategoryMapper.selectById("category1"))
				.thenReturn(CourseCategory.builder().build());

		// Configure CourseService.addCourse(...).
		final Course course = new Course();
		course.setName("name");
		course.setLogo("logo");
		course.setResourceId("newCourse1");
		course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setCourseType(CommonConstant.ZERO);
//		final CourseAddReq req = new CourseAddReq();
//		req.setName("name");
//		req.setLogo("logo");
//		req.setCourseType(0);
		when(mockCourseService.addCourse(eq("admin"), any())).thenReturn(course);

		String req = "{\"name\":\"test-add\",\"categoryId\":\"category1\",\"difficulty\":0,\"lecturer\":\"\",\"score\":0,\"role\":1,\"description\":\"\",\"outline\":\"\",\"isPublic\":true,\"logo\":\"\",\"courseType\":0,\"courseNum\":\"\"}";

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/course").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"operationAuthType\":2,\"operationAuthIds\":\"[]\",\"readAuthType\":2,\"readAuthIds\":\"[]\",\"resourceId\":\"newCourse1\",\"builtin\":null,\"name\":\"name\",\"description\":null,\"categoryId\":null,\"logo\":\"logo\",\"outline\":null,\"difficulty\":null,\"createUserId\":null,\"modifyUserId\":null,\"isPublic\":null,\"courseType\":0,\"heat\":null,\"lecturer\":null,\"score\":null,\"courseNum\":null,\"role\":null,\"lessonCount\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addCrLog(any(UserInfo.class), eq(OperationModule.COURSE),
				eq(OperationType.CREATE), eq("name"));
	}

	@Test
	void testPatch() throws Exception {
		// Setup
		// Configure CourseService.updateCourse(...).
		final Course course = new Course();
		course.setName("name");
		course.setLogo("logo");
		course.setResourceId("newCourse1");
		course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setCourseType(CommonConstant.ZERO);

		when(mockCourseService.updateCourse(eq("admin"), any(Course.class))).thenReturn(course);

		// Configure CourseService.getById(...).
		when(mockCourseService.getById("newCourse1")).thenReturn(course);

		String req = "{\"description\":\"desc111111\",\"lecturer\":\"techer1\",\"resourceId\":\"newCourse1\"}";

		// Run the test and verify the results
		mockMvc.perform(patch("/oj/admin/course").content(req)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"operationAuthType\":2,\"operationAuthIds\":\"[]\",\"readAuthType\":2,\"readAuthIds\":\"[]\",\"resourceId\":\"newCourse1\",\"builtin\":null,\"name\":\"name\",\"description\":null,\"categoryId\":null,\"logo\":\"logo\",\"outline\":null,\"difficulty\":null,\"createUserId\":null,\"modifyUserId\":null,\"isPublic\":null,\"courseType\":0,\"heat\":null,\"lecturer\":null,\"score\":null,\"courseNum\":null,\"role\":null,\"lessonCount\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addCrLog(any(UserInfo.class), eq(OperationModule.COURSE),
				eq(OperationType.UPDATE), eq("name"));
	}

	@Test
	void testGet() throws Exception {
		// Setup
		// Configure CourseMapper.selectById(...).
		final Course course = new Course();
		course.setName("name");
		course.setLogo("logo");
		course.setResourceId("newCourse1");
		course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setCourseType(CommonConstant.ZERO);
		when(mockCourseMapper.selectById("newCourse1")).thenReturn(course);

		when(mockUtilService.handlerFileUrl("logo")).thenReturn("logo");

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/course").param("resource_id", "newCourse1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"resourceId\":\"newCourse1\",\"name\":\"name\",\"description\":null,\"logo\":\"logo\",\"score\":null,\"categoryId\":null,\"outline\":null,\"difficulty\":null,\"courseType\":0,\"isPublic\":null,\"lecturer\":null,\"courseNum\":null,\"role\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testBatchDelete() throws Exception {
		// Setup
		// Configure CourseService.listByIds(...).
		final Course course = new Course();
		course.setName("name");
		course.setLogo("logo");
		course.setResourceId("newCourse1");
		course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setCourseType(CommonConstant.ZERO);
		final List<Course> courseList = Arrays.asList(course);
		BatchDeleteReq req = new BatchDeleteReq();
		List<String> ids = Lists.newArrayList("newCourse1");
		req.setResourceIds(ids);
		when(mockCourseService.listByIds(ids)).thenReturn(courseList);

		when(mockCourseService.batchDelete(eq(ids), any(AuthContext.class)))
				.thenReturn(true);

		// Run the test and verify the results
		mockMvc.perform(delete("/oj/admin/course/batch").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":true,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addCrLog(any(UserInfo.class), eq(OperationModule.COURSE),
				eq(OperationType.DELETE), eq("name"));
	}

	@Test
	void testUpload_attachment() throws Exception {
		// Setup
		// Configure AttachmentService.uploadFile(...).
		final AttachmentBo attachmentBo = AttachmentBo.builder().filePath("filePath").fileName("123.png").build();
		when(mockAttachmentService.uploadFile(any(), eq(false), any(MultipartFile.class), eq(true)))
				.thenReturn(attachmentBo);

		when(mockUtilService.handlerFileUrl("filePath")).thenReturn("filePath");

		// Run the test and verify the results
		mockMvc.perform(multipart("/oj/admin/course/outline/upload_attachment")
				.file(new MockMultipartFile("file", "123.png",
						MediaType.APPLICATION_JSON_VALUE, "content".getBytes()))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"resourceId\":null,\"fileName\":\"123.png\",\"filePath\":\"filePath\",\"isPublic\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}

