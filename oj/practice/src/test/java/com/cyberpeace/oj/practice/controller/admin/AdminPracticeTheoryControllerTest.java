package com.cyberpeace.oj.practice.controller.admin;

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
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.ojbase.service.IndicatorService;
import com.cyberpeace.oj.practice.controller.req.ResourceDelReq;
import com.cyberpeace.oj.practice.controller.req.TheoryAddReq;
import com.cyberpeace.oj.practice.controller.req.TheoryUpdateReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeTheoryImportService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
@WebMvcTest(AdminPracticeTheoryController.class)
class AdminPracticeTheoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PracticeTheoryService mockPracticeTheoryService;

	@MockBean
	private PracticeTheoryMapper mockPracticeTheoryMapper;

	@MockBean
	private PracticeCategoryService mockPracticeCategoryService;

	@MockBean
	private AttachmentService mockAttachmentService;

	@MockBean
	private UtilService mockUtilService;

	@MockBean
	private RemoteLogService mockRemoteLogService;

	@MockBean
	private PracticeTheoryImportService mockPracticeTheoryImportService;
	@MockBean
	private IndicatorService indicatorService;

	@BeforeEach
	void setUp() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("admin");
		userInfo.setPermissions(Sets.newHashSet("courses"));
		UserInfoContext.setUserContext(userInfo);
	}

	@Test
	void testAdd() throws Exception {
		// Setup
		// Configure PracticeTheoryService.create(...).
		final TheoryAddReq req = new TheoryAddReq();
		req.setContent("content1");
		req.setCategoryId("category1");
		req.setCourseCategoryId("courseCategoryId1");
		req.setAnswer("answer");
		req.setTheoryType(1);
		req.setDifficulty(1);
		final PracticeTheory practiceTheory = BeanUtils.copy(req, PracticeTheory.class);

		when(mockPracticeTheoryService.create(eq(practiceTheory), any(AuthContext.class)))
				.thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/theory").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":0,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.THEORY),
				eq(OperationType.CREATE), eq("理论题"), eq("创建理论题 \"content1\""));
	}

	@Test
	void testUpdate() throws Exception {
		// Setup
		final TheoryUpdateReq req = new TheoryUpdateReq();
		req.setContent("content1");
		req.setCategoryId("category1");
		req.setCourseCategoryId("courseCategoryId1");
		req.setAnswer("answer");
		req.setTheoryType(1);
		req.setDifficulty(1);
		req.setResourceId("theory1");

		PracticeTheory practiceTheory = BeanUtils.copy(req, PracticeTheory.class);

		when(mockPracticeTheoryService.updateTheory(eq(practiceTheory), any(AuthContext.class)))
				.thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(patch("/oj/admin/theory").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":0,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.THEORY),
				eq(OperationType.UPDATE), eq("理论题"), eq("更新理论题 \"content1\""));
	}

	@Test
	void testGet() throws Exception {
		// Setup
		// Configure PracticeTheoryMapper.selectById(...).
		final PracticeTheory theory = new PracticeTheory();
		theory.setTitle("content");
		theory.setContent("content");
		theory.setAnswer("answer");
		theory.setCreateUserId("createUserId");
		theory.setTheoryType(0);
		theory.setBlank("answer");
		theory.setVideo("video");
		theory.setResourceId("theory1");
		when(mockPracticeTheoryMapper.selectById("theory1")).thenReturn(theory);

		when(mockUtilService.handlerFileUrl("video")).thenReturn("video");

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/theory").param("resource_id", "theory1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"resourceId\":\"theory1\",\"content\":\"content\",\"blank\":\"answer\",\"categoryId\":null,\"courseCategoryId\":null,\"answer\":\"answer\",\"theoryType\":0,\"knowledge\":null,\"option\":null,\"keyword\":null,\"isPublic\":null,\"difficulty\":null,\"type\":null,\"video\":\"video\"},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testDelete() throws Exception {
		// Setup
		// Configure PracticeTheoryService.listByIds(...).
		final PracticeTheory theory = new PracticeTheory();
		theory.setTitle("content");
		theory.setContent("content");
		theory.setAnswer("answer");
		theory.setCreateUserId("createUserId");
		theory.setTheoryType(0);
		theory.setBlank("answer");
		theory.setVideo("video");
		theory.setResourceId("theory1");
		final List<PracticeTheory> practiceTheoryList = Arrays.asList(theory);
		when(mockPracticeTheoryService.listByIds(Arrays.asList("theory1")))
				.thenReturn(practiceTheoryList);

		final ResourceDelReq req = new ResourceDelReq();
		req.setResourceIds(Lists.newArrayList("theory1"));

		when(mockPracticeTheoryService.batchDelete(Arrays.asList("theory1"))).thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(delete("/oj/admin/theory").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":0,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockRemoteLogService).addOjLog(any(UserInfo.class), eq(OperationModule.THEORY),
				eq(OperationType.DELETE), eq("理论题"), eq("删除理论题 \"content\""));
	}

	@Test
	void testList() throws Exception {
		// Setup
		when(mockPracticeCategoryService.selectChildrenIds("categoryId1", CommonConstant.TRUE))
				.thenReturn(Arrays.asList("categoryId1"));

		final PracticeTheoryListBo theory = new PracticeTheoryListBo();
		theory.setContent("content");
		theory.setCreateUserId("admin");
		theory.setTheoryType(0);
		theory.setVideo("video");
		theory.setResourceId("theory1");

		IPage<PracticeTheoryListBo> page = new Page<>();
		page.setRecords(Lists.newArrayList(theory));

		// Configure PracticeTheoryService.pageByQuery(...).
		final TheoryListQueryBo listQuery = new TheoryListQueryBo();
		listQuery.setPracticeSetId("practiceSetId");
		listQuery.setCategoryIds(Arrays.asList("categoryId1"));
		listQuery.setSolved(0);
		listQuery.setDifficulty(0);
		listQuery.setTheoryType(0);
		when(mockPracticeTheoryService.pageByQuery(any(Page.class), eq(listQuery),
				any(AuthContext.class))).thenReturn(page);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/theory/list").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"theory1\",\"categoryId\":null,\"isPublic\":null,\"content\":\"content\",\"difficulty\":null,\"createUserId\":\"admin\",\"createUserName\":null,\"createTime\":null,\"updateTime\":null,\"solved\":null,\"type\":null,\"video\":\"video\",\"theoryType\":0}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}


}

