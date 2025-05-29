package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.controller.req.BatchDeleteReq;
import com.cyberpeace.oj.course.controller.req.SaveCourseCategoryReq;
import com.cyberpeace.oj.course.controller.req.UpdateCategoryReq;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import com.cyberpeace.oj.course.service.impl.CourseValidateHandler;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Disabled
@WebMvcTest(AdminCourseCategoryController.class)
class AdminCourseCategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseCategoryService mockCourseCategoryService;

	@MockBean
	private CourseCategoryMapper mockCourseCategoryMapper;

	@MockBean
	private CourseValidateHandler mockCourseValidateHandler;

	@Test
	void testList() throws Exception {
		// Setup
		CourseCategoryBo bo = new CourseCategoryBo();
		bo.setName("category1Name");
		bo.setResourceId("category1");
		IPage<CourseCategoryBo> page = new Page<>(1, 10);
		page.setRecords(Lists.newArrayList(bo));
		when(mockCourseCategoryService.pageBySearch(any(Page.class), eq("pattern1"), eq(false)))
				.thenReturn(page);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/course/direction/list")
				.param("children", "false").param("per_page", "10").param("page", "1")
				.param("pattern", "pattern1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(
						"{\"data\":{\"list\":[{\"children\":null,\"resourceId\":\"category1\",\"name\":\"category1Name\",\"courseType\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}",
						true));
	}

	@Test
	void testGet() throws Exception {
		// Setup
		// Configure CourseCategoryService.selectById(...).
		final CourseCategoryBo courseCategoryBo = new CourseCategoryBo();
		courseCategoryBo.setChildren(Arrays.asList(new CourseCategoryBo()));
		courseCategoryBo.setParentId("parentId");
		courseCategoryBo.setResourceId("category1");
		courseCategoryBo.setName("category1name");
		courseCategoryBo.setCourseType(0);
		when(mockCourseCategoryService.selectById("category1", false))
				.thenReturn(courseCategoryBo);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/course/direction").param("resource_id", "category1")
				.param("children", "false").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"children\":[{\"children\":null,\"resourceId\":null,\"name\":null,\"courseType\":null}],\"resourceId\":\"category1\",\"name\":\"category1name\",\"courseType\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testDelete() throws Exception {
		// Setup
		BatchDeleteReq req = new BatchDeleteReq();
		req.setResourceIds(Lists.newArrayList("category1"));
		when(mockCourseCategoryService.batchDeleteByIds(Arrays.asList("category1"))).thenReturn(0);

		// Run the test and verify the results
		mockMvc.perform(delete("/oj/admin/course/direction/batch").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":0,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testAdd() throws Exception {
		// Setup
		final SaveCourseCategoryReq req = new SaveCourseCategoryReq();
		req.setName("categoryName1");
		req.setCourseType(0);

		// Configure CourseCategoryMapper.selectByName(...).
		final CourseCategory courseCategory = CourseCategory.builder().name("categoryName1").build();
		when(mockCourseCategoryMapper.selectByName("categoryName1")).thenReturn(null);

		when(mockCourseCategoryService.saveOrUpdate(any(CategoryAddBo.class))).thenReturn(courseCategory);
		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/course/direction").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":\"categoryName1\",\"courseType\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockCourseValidateHandler).categoryNameLengthValidate("categoryName1");
	}

	@Test
	void testPatch() throws Exception {
		// Setup
		UpdateCategoryReq req = new UpdateCategoryReq();
		req.setName("categoryName1");
		req.setResourceId("categoryId1");
		// Configure CourseCategoryService.saveOrUpdate(...).
		final CourseCategory courseCategory = CourseCategory.builder().name("categoryName1").resourceId("categoryId1").build();
		when(mockCourseCategoryService.saveOrUpdate(any(CategoryAddBo.class)))
				.thenReturn(courseCategory);

		// Run the test and verify the results
		mockMvc.perform(patch("/oj/admin/course/direction").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"resourceId\":\"categoryId1\",\"name\":\"categoryName1\",\"courseType\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
		verify(mockCourseValidateHandler).categoryNameLengthValidate("categoryName1");
	}
}

