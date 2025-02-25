
package com.cyberpeace.cr.tool.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.tool.controller.req.DeleteReq;
import com.cyberpeace.cr.tool.controller.req.ToolAddReq;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ToolController.class)
class ToolControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ToolService mockToolService;

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
	void testGetList() throws Exception {
		// Setup
		// Configure ToolService.selectPage(...).
		final ToolListQuery toolListQuery = new ToolListQuery();
		toolListQuery.setCategory("category");
		toolListQuery.setPlatforms("platforms");
		toolListQuery.setCracked(false);
		toolListQuery.setType("type");
		toolListQuery.setTag("tag");

		IPage<ToolRsp> page = new Page<>(1, 10);
		ToolRsp rsp = new ToolRsp();
		rsp.setResourceId("tool1");
		rsp.setTag(Lists.newArrayList("tag1", "tag2"));
		rsp.setName("toolName1");
		page.setRecords(Lists.newArrayList(rsp));

		when(mockToolService.selectPage(any(Page.class), any(ToolListQuery.class), eq(true)))
				.thenReturn(page);

		// Run the test and verify the results
		mockMvc.perform(get("/cr/admin/tools/list")
				.contentType(MediaType.APPLICATION_JSON).param("type", "type").param("tag", "tag")
				.param("category", "category").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"list\":[{\"categoryI18nName\":null,\"cracked\":null,\"deviceName\":null,\"id\":null,\"language\":null,\"name\":\"toolName1\",\"platforms\":null,\"resourceId\":\"tool1\",\"toolType\":null,\"category\":null,\"cover\":null,\"coverUrl\":null,\"files\":null,\"toolFiles\":null,\"introduction\":null,\"version\":null,\"visible\":null,\"createTime\":null,\"updateTime\":null,\"deviceResourceId\":\"\",\"homePage\":\"\",\"imageResourceId\":\"\",\"manual\":null,\"scene\":null,\"sceneData\":null,\"tag\":[\"tag1\",\"tag2\"],\"disabled\":null,\"type\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testGetDetail() throws Exception {
		// Setup
		// Configure ToolService.selectById(...).
		final ToolRsp rsp = new ToolRsp();
		rsp.setResourceId("tool1");
		rsp.setTag(Lists.newArrayList("tag1", "tag2"));
		rsp.setName("toolName1");
		rsp.setCategoryI18nName("categoryI18nName");
		rsp.setCracked(false);
		rsp.setDeviceName("deviceName");
		rsp.setId(0);
		rsp.setLanguage(Arrays.asList("value"));
		when(mockToolService.selectById("tool1")).thenReturn(rsp);

		// Run the test and verify the results
		mockMvc.perform(get("/cr/admin/tools").param("resource_id", "tool1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"categoryI18nName\":\"categoryI18nName\",\"cracked\":false,\"deviceName\":\"deviceName\",\"id\":0,\"language\":[\"value\"],\"name\":\"toolName1\",\"platforms\":null,\"resourceId\":\"tool1\",\"toolType\":null,\"category\":null,\"cover\":null,\"coverUrl\":null,\"files\":null,\"toolFiles\":null,\"introduction\":null,\"version\":null,\"visible\":null,\"createTime\":null,\"updateTime\":null,\"deviceResourceId\":\"\",\"homePage\":\"\",\"imageResourceId\":\"\",\"manual\":null,\"scene\":null,\"sceneData\":null,\"tag\":[\"tag1\",\"tag2\"],\"disabled\":null,\"type\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testCreate() throws Exception {
		// Setup
		final ToolAddReq req = new ToolAddReq();
		req.setName("tool2");
		req.setCategory("cate2");
		req.setCover("cover");
		req.setLanguage("en");
		req.setPlatforms("[\"windows\"]");
		req.setFiles("[]");
		Tool tool = BeanUtils.copy(req, Tool.class);
		when(mockUtilService.replaceFileUrl("cover")).thenReturn("cover");
		doNothing().when(mockToolService).validateToolNameExist(any());
		when(mockToolService.save(any())).thenReturn(true);

		// Run the test and verify the results
		mockMvc.perform(post("/cr/admin/tools").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));

		// Confirm ToolService.validateToolNameExist(...).
		verify(mockToolService).validateToolNameExist(tool);

		// Confirm ToolService.save(...).
		verify(mockToolService).save(tool);
		verify(mockRemoteLogService).addCrLog(any(UserInfo.class), eq(OperationModule.TOOL),
				eq(OperationType.CREATE), eq("tool2"));
	}

	@Test
	void testUpdate() throws Exception {
		// Setup
		final ToolUpdateReq req = new ToolUpdateReq();
		req.setName("tool2");
		req.setCategory("cate2");
		req.setCover("cover");
		req.setLanguage("en");
		req.setPlatforms("[\"windows\"]");
		req.setFiles("[]");
		req.setResourceId("toolId2");

		Tool tool = BeanUtils.copy(req, Tool.class);
		when(mockUtilService.replaceFileUrl("cover")).thenReturn("cover");
		doNothing().when(mockToolService).validateToolNameExist(any());
		when(mockToolService.save(any())).thenReturn(true);
		// Run the test and verify the results
		mockMvc.perform(patch("/cr/admin/tools").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));

		// Confirm ToolService.validateEditToolNameExist(...).
		verify(mockToolService).validateEditToolNameExist(tool);

		// Confirm ToolService.updateById(...).
		verify(mockToolService).updateById(tool);
		verify(mockRemoteLogService).addCrLog(any(UserInfo.class), eq(OperationModule.TOOL),
				eq(OperationType.UPDATE), eq("tool2"));
	}

	@Test
	void testBatchRemove() throws Exception {
		// Setup
		final DeleteReq req = new DeleteReq();
		req.setIds(Arrays.asList("tool1"));
 		// Configure ToolService.listByIds(...).
		final Tool tool = new Tool();
		tool.setResourceId("tool1");
		tool.setName("name");
		tool.setCover("cover");
		tool.setCategory("category");
		tool.setTag("tag");
		final List<Tool> toolList = Arrays.asList(tool);
		when(mockToolService.listByIds(Arrays.asList("tool1"))).thenReturn(toolList);

		// Run the test and verify the results
		mockMvc.perform(delete("/cr/admin/tools").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockToolService).removeByIds(Arrays.asList("tool1"));
		verify(mockRemoteLogService).addCrLog(any(UserInfo.class), eq(OperationModule.TOOL),
				eq(OperationType.DELETE), eq("name"));
	}
}
