
package com.cyberpeace.cr.tool.controller.admin;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
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

@WebMvcTest(ToolCategoryController.class)
class ToolCategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ToolCategoryService mockToolCategoryService;

	@Test
	void testGetList() throws Exception {
		// Setup
		// Configure ToolCategoryService.selectPage(...).
		final ToolCategory toolCategory = new ToolCategory();
		toolCategory.setId(0);
		toolCategory.setResourceId("resourceId");
		toolCategory.setCnName("cnName");
		toolCategory.setEnName("enName");
		toolCategory.setType("type");
		final ListRsp<ToolCategory> toolCategoryListRsp = new ListRsp<>(Arrays.asList(toolCategory),
				1L, 1L, 1L);
		final PageReqBase listReq = new PageReqBase();
		when(mockToolCategoryService.selectPage(any(PageReqBase.class), eq("attack"))).thenReturn(toolCategoryListRsp);

		// Run the test and verify the results
		mockMvc.perform(get("/cr/admin/tool/category/list").param("type", "attack")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"list\":[{\"id\":0,\"resourceId\":\"resourceId\",\"cnName\":\"cnName\",\"enName\":\"enName\",\"type\":\"type\"}],\"total\":1,\"lastPage\":1,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}
