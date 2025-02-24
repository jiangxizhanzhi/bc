
package com.cyberpeace.oj.practice.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.practice.controller.req.RandomPracticeReq;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.*;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminPaperController.class)
class AdminPaperControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PaperService mockPaperService;

	@MockBean
	private PracticeCategoryService mockPracticeCategoryService;

	@MockBean
	private PracticeSetMapper mockPracticeSetMapper;

	@BeforeEach
	void setUp() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("admin");
		userInfo.setPermissions(Sets.newHashSet("courses"));
		UserInfoContext.setUserContext(userInfo);
	}

	@Test
	void testSelectPracticeInfo() throws Exception {
		// Setup
		when(mockPracticeCategoryService.selectChildrenIds("categoryId", true))
				.thenReturn(Arrays.asList("value"));

		// Configure PaperService.selectLibPractice(...).
		final LibPracticeQueryBo queryBo = new LibPracticeQueryBo();
		queryBo.setResourceIds(Arrays.asList("value"));
		queryBo.setPracticeSetId("practiceSetId");
		queryBo.setCategoryIds(Arrays.asList("value"));
		queryBo.setQuestionType(0);

		IPage<LibPracticeListBo> page= new Page<>();
		LibPracticeListBo bo = new LibPracticeListBo();
		bo.setTitle("test1");
		bo.setContent("test-test");
		page.setRecords(Lists.newArrayList(bo));

		when(mockPaperService.selectLibPractice(any(Page.class), eq(queryBo),
				any(AuthContext.class))).thenReturn(page);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/paper/practice/list").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":null,\"blankCount\":null,\"title\":\"test1\",\"content\":\"test-test\",\"categoryId\":null,\"categoryName\":null,\"practiceType\":null,\"questionType\":null,\"option\":null,\"score\":null,\"answer\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testGet() throws Exception {
		// Setup
		// Configure PracticeCategoryService.listByTypeAndName(...).
		final CategoryListBo categoryListBo = new CategoryListBo();
		categoryListBo.setCategoryListBos(Arrays.asList(new CategoryListBo()));
		final List<CategoryListBo> categoryListBos = Arrays.asList(categoryListBo);
		when(mockPracticeCategoryService
				.listByTypeAndName(CategoryListQuery.builder().practiceType(0).build(), false))
						.thenReturn(categoryListBos);
		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/paper/select_info").param("practice_type", "0")
				.param("children", "false").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"practiceSet\":null,\"category\":[{\"name\":null,\"resourceId\":null,\"categoryListBos\":[{\"name\":null,\"resourceId\":null,\"categoryListBos\":null}]}],\"questionType\":[0,1,2,3]},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testGetRandomPractice() throws Exception {
		// Setup
		when(mockPracticeCategoryService.selectChildrenIds("category1", true))
				.thenReturn(Arrays.asList("category1"));

		// Configure PaperService.selectRandomPractice(...).
		final LibPracticeListBo libPracticeListBo = new LibPracticeListBo();
		libPracticeListBo.setResourceId("resourceId");
		libPracticeListBo.setBlankCount(0);
		libPracticeListBo.setTitle("title");
		libPracticeListBo.setContent("content");
		libPracticeListBo.setCategoryId("categoryId");
		final List<LibPracticeListBo> libPracticeListBos = Arrays.asList(libPracticeListBo);
		final RandomPracticeQuery practiceQuery = new RandomPracticeQuery();
		practiceQuery.setCategoryIds(Arrays.asList("category1"));
		practiceQuery.setQuestionType(0);
		practiceQuery.setCount(3);
		practiceQuery.setSelectIds(Lists.newArrayList());
		when(mockPaperService.selectRandomPractice(eq(practiceQuery), eq(10.0),
				any(AuthContext.class))).thenReturn(libPracticeListBos);

		final RandomPracticeReq req = new RandomPracticeReq();
		req.setCount(3);
		req.setScore(10.0);
		req.setQuestionType(0);
		req.setCategoryId("category1");
		req.setResourceIds(Lists.newArrayList());

		// Run the test and verify the results
		mockMvc.perform(post("/oj/admin/paper/random/practice").content(JsonUtil.toJsonString(req))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":[{\"resourceId\":\"resourceId\",\"blankCount\":0,\"title\":\"title\",\"content\":\"content\",\"categoryId\":\"categoryId\",\"categoryName\":null,\"practiceType\":null,\"questionType\":null,\"option\":null,\"score\":null,\"answer\":null}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}
