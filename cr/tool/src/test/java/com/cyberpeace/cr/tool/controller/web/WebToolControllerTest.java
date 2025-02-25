
package com.cyberpeace.cr.tool.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.service.ToolService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebToolController.class)
class WebToolControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ToolService mockToolService;

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

		IPage<ToolRsp> page = new Page<>();
		ToolRsp rsp = new ToolRsp();
		rsp.setResourceId("tool1");
		rsp.setTag(Lists.newArrayList("tag1", "tag2"));
		rsp.setName("toolName1");
		page.setRecords(Lists.newArrayList(rsp));

		when(mockToolService.selectPage(any(Page.class), any(ToolListQuery.class), eq(false)))
				.thenReturn(page);
		// Run the test and verify the results
		mockMvc.perform(get("/cr/tools/list")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"list\":[{\"categoryI18nName\":null,\"coverUrl\":null,\"id\":null,\"name\":\"toolName1\",\"resourceId\":\"tool1\",\"platforms\":null,\"introduction\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testGetDetail() throws Exception {
		// Setup
		// Configure ToolService.selectById(...).
		final ToolRsp toolRsp = new ToolRsp();
		toolRsp.setCategoryI18nName("categoryI18nName");
		toolRsp.setCracked(false);
		toolRsp.setDeviceName("deviceName");
		toolRsp.setId(0);
		toolRsp.setLanguage(Arrays.asList("cn"));
		toolRsp.setResourceId("toolId1");
		toolRsp.setName("tool");
		toolRsp.setTag(Lists.newArrayList("tag1", "tag2"));
		when(mockToolService.selectById("toolId1")).thenReturn(toolRsp);

		// Run the test and verify the results
		mockMvc.perform(get("/cr/tools").param("resource_id", "toolId1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"categoryI18nName\":\"categoryI18nName\",\"cracked\":false,\"deviceName\":\"deviceName\",\"id\":0,\"language\":[\"cn\"],\"name\":\"tool\",\"platforms\":null,\"resourceId\":\"toolId1\",\"toolType\":null,\"category\":null,\"cover\":null,\"coverUrl\":null,\"files\":null,\"toolFiles\":null,\"introduction\":null,\"version\":null,\"visible\":null,\"createTime\":null,\"updateTime\":null,\"deviceResourceId\":\"\",\"homePage\":\"\",\"imageResourceId\":\"\",\"manual\":null,\"scene\":null,\"sceneData\":null,\"tag\":[\"tag1\",\"tag2\"],\"disabled\":null,\"type\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}
