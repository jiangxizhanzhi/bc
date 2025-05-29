
package com.cyberpeace.oj.practice.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import com.cyberpeace.oj.practice.controller.req.OperationAddReq;
import com.cyberpeace.oj.practice.controller.req.OperationUpdateReq;
import com.cyberpeace.oj.practice.controller.req.ResourceDelReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.OperationBo;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import com.cyberpeace.oj.practice.service.impl.PracticeValidateHandler;
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
@WebMvcTest(AdminPracticeOperationController.class)
class AdminPracticeOperationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PracticeOperationService mockPracticeOperationService;

	@MockBean
	private PracticeOperationMapper mockPracticeOperationMapper;

	@MockBean
	private PracticeCategoryService mockPracticeCategoryService;

	@MockBean
	private AttachmentService mockAttachmentService;

	@MockBean
	private PracticeValidateHandler mockPracticeValidateHandler;

	@MockBean
	private RemoteLogService mockRemoteLogService;

	@MockBean
	private RemoteTerminalService mockRemoteTerminalService;

	@MockBean
	private UtilService utilService;

	@BeforeEach
	void setUp() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("admin");
		userInfo.setPermissions(Sets.newHashSet("courses"));
		UserInfoContext.setUserContext(userInfo);
	}

	@Test
	void testGet() throws Exception {
		// Setup
		// Configure PracticeOperationService.selectById(...).
		final OperationBo operationBo = new OperationBo();
		operationBo.setResourceId("resourceId");
		operationBo.setTitle("title");
		operationBo.setCreateUserId("createUserId");
		operationBo.setWriteupPublic(false);
		operationBo.setDynamicEnv(false);
		operationBo.setTopoId("topoId");
		operationBo.setSceneShareMode(0);
		when(mockPracticeOperationService.selectById(eq("resourceId"), any(AuthContext.class)))
				.thenReturn(operationBo);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/practice/operation").param("resource_id", "resourceId")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"resourceId\":\"resourceId\",\"categoryId\":null,\"title\":\"title\",\"isPublic\":null,\"practiceSetId\":null,\"content\":null,\"answer\":null,\"difficulty\":null,\"knowledge\":null,\"score\":null,\"createUserId\":\"createUserId\",\"courseCategoryId\":null,\"attachmentId\":null,\"writeup\":null,\"writeupPublic\":false,\"dynamicEnv\":false,\"topoId\":\"topoId\",\"sceneShareMode\":0,\"dynamicFlag\":null,\"destroyDelay\":null,\"type\":null,\"needOuter\":null,\"networking\":null,\"fileName\":null,\"fileUrl\":null,\"sceneConfigData\":null,\"files\":null,\"targetList\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", false));
	}

	@Test
	void testAdd() throws Exception {
		// Setup
		final OperationAddReq req = new OperationAddReq();
		req.setTitle("title");
		req.setCategoryId("categoryId");
		req.setCourseCategoryId("courseCategoryId");
		req.setDifficulty(0);
		req.setContent("content");
		req.setDynamicEnv(false);
		req.setDynamicFlag(0);
		req.setIsPublic(false);
		req.setWriteupPublic(false);
		req.setAnswer("answer");
		req.setScore(10.0);
		req.setTarget("target");
		// Configure PracticeOperationService.addPracticeOperation(...).
		final PracticeOperation addOperation = BeanUtils.copy(req, PracticeOperation.class);
		when(mockPracticeOperationService.addPracticeOperation(eq(addOperation), eq("target"),
				any(AuthContext.class))).thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/practice/operation")
						.param("title", "title")
						.param("category_id", "categoryId")
						.param("course_category_id", "courseCategoryId")
						.param("difficulty", "0")
						.param("content", "content")
						.param("dynamic_env", "false")
						.param("dynamic_flag", "0")
						.param("is_public", "false")
						.param("writeup_public", "false")
						.param("answer", "answer")
						.param("score", "10.0")
						.param("target", "target")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":0,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.CAPTURE_FLAG),
				eq(OperationType.CREATE), eq("实操题"), eq("创建实操题 \"title\""));
	}

	@Test
	void testUploadAttachment() throws Exception {
		// Setup
		// Configure AttachmentService.uploadDest(...).
		final AttachmentBo attachmentBo = AttachmentBo.builder().resourceId("data").build();
		when(mockAttachmentService.uploadDest(any(String.class), eq(true), any(MultipartFile.class)))
				.thenReturn(attachmentBo);

		// Run the test and verify the results
		mockMvc.perform(multipart("/oj/admin/practice/operation/upload_attachment")
				.file(new MockMultipartFile("attachment", "originalFilename",
						MediaType.APPLICATION_JSON_VALUE, "content".getBytes()))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":\"data\",\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testUpdate() throws Exception {
		// Setup
		final OperationUpdateReq req = new OperationUpdateReq();
		req.setTitle("title");
		req.setCategoryId("categoryId");
		req.setCourseCategoryId("courseCategoryId");
		req.setDifficulty(0);
		req.setContent("content");
		req.setDynamicEnv(false);
		req.setDynamicFlag(0);
		req.setIsPublic(false);
		req.setWriteupPublic(false);
		req.setAnswer("answer");
		req.setScore(10.0);
		req.setTarget("target");
		req.setResourceId("operation1");
		// Configure PracticeOperationService.updateOperation(...).
		final PracticeOperation updateOperation = BeanUtils.copy(req, PracticeOperation.class);
		when(mockPracticeOperationService.updateOperation(updateOperation, "target")).thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(patch("/oj/admin/practice/operation")
						.param("title", "title")
						.param("category_id", "categoryId")
						.param("course_category_id", "courseCategoryId")
						.param("difficulty", "0")
						.param("content", "content")
						.param("dynamic_env", "false")
						.param("dynamic_flag", "0")
						.param("is_public", "false")
						.param("writeup_public", "false")
						.param("answer", "answer")
						.param("score", "10.0")
						.param("target", "target")
						.param("resource_id", "operation1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":0,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.CAPTURE_FLAG),
				eq(OperationType.UPDATE), eq("实操题"), eq("更新实操题 \"title\""));
	}

	@Test
	void testDelete() throws Exception {
		// Setup
		final ResourceDelReq req = new ResourceDelReq();
		req.setResourceIds(Lists.newArrayList("operation1"));
		// Configure PracticeOperationService.listByIds(...).
		final PracticeOperation practiceOperation = new PracticeOperation();
		practiceOperation.setResourceId("resourceId");
		practiceOperation.setTitle("title");
		practiceOperation.setCreateUserId("createUserId");
		practiceOperation.setWriteupPublic(false);
		practiceOperation.setDynamicEnv(false);
		practiceOperation.setTopoId("topoId");
		practiceOperation.setSceneShareMode(0);
		final List<PracticeOperation> practiceOperations = Arrays.asList(practiceOperation);
		when(mockPracticeOperationService.listByIds(Arrays.asList("operation1")))
				.thenReturn(practiceOperations);

		when(mockPracticeOperationService.batchDelete(eq(Arrays.asList("operation1")),
				any(AuthContext.class))).thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(delete("/oj/admin/practice/operation").content(JsonUtil.toJsonString(req))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":0,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.CAPTURE_FLAG),
				eq(OperationType.DELETE), eq("实操题"), eq("删除实操题 \"title\""));
	}

	@Test
	void testList() throws Exception {
		// Setup
		when(mockPracticeCategoryService.selectChildrenIds("categoryId", CommonConstant.TRUE))
				.thenReturn(Arrays.asList("value"));

		// Configure PracticeOperationService.pageByQuery(...).
		final OperationListQueryBo listQuery = new OperationListQueryBo();
		listQuery.setPracticeSetId("practiceSetId");
		listQuery.setCategoryIds(Arrays.asList("value"));
		listQuery.setDifficulty(0);
		listQuery.setSolved(false);

		IPage<AdminOperationListBo> page = new Page<>();
		AdminOperationListBo bo = new AdminOperationListBo();
		bo.setTitle("title");
		bo.setCategoryId("categoryId");
		page.setRecords(Lists.newArrayList(bo));

		when(mockPracticeOperationService.pageByQuery(any(Page.class), eq(listQuery),
				any(AuthContext.class))).thenReturn(page);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/practice/operation/list").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":null,\"categoryId\":\"categoryId\",\"title\":\"title\",\"isPublic\":null,\"writeupPublic\":null,\"difficulty\":null,\"createUserId\":null,\"createUserName\":null,\"topoId\":null,\"sceneShareMode\":null,\"updateTime\":null,\"solved\":null,\"hasSceneData\":null,\"targetList\":null,\"files\":null,\"answer\":null,\"dynamicFlag\":null,\"content\":null,\"type\":null,\"needOuter\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", false));
	}

	@Test
	void testGetImagePage() throws Exception {
		// Setup
		when(mockRemoteTerminalService.pageImages(any(ClusterImagePageReq.class)))
				.thenReturn(new JSONObject(0, false));

		// Run the test and verify the results
		mockMvc.perform(
				get("/oj/admin/practice/operation/images").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}
