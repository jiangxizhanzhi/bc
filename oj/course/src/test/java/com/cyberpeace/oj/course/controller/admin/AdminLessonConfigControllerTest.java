package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminLessonConfigController.class)
class AdminLessonConfigControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LessonConfigService mockLessonConfigService;

	@BeforeEach
	void setUp() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("admin");
		userInfo.setPermissions(Sets.newHashSet("courses"));
		UserInfoContext.setUserContext(userInfo);
	}

	@Test
	void testGetLessonConfig() throws Exception {
		// Setup
		// Configure LessonConfigService.getLessonConfig(...).
		final LessonConfigBo lessonConfigBo = new LessonConfigBo();
		lessonConfigBo.setCourseId("courseId1");
		lessonConfigBo.setIndex(0);
		lessonConfigBo.setLearn(false);
		lessonConfigBo.setCompulsory(0);

		final LessonConfigBo lessonConfigBo1 = new LessonConfigBo();
		lessonConfigBo1.setCourseId("courseId1");
		lessonConfigBo1.setIndex(0);
		lessonConfigBo1.setLearn(false);
		lessonConfigBo1.setCompulsory(0);
		lessonConfigBo1.setLessonId("lessonId1");

		final LessonConfigBo lessonConfigBo2 = new LessonConfigBo();
		lessonConfigBo2.setCourseId("courseId1");
		lessonConfigBo2.setIndex(0);
		lessonConfigBo2.setLearn(false);
		lessonConfigBo2.setCompulsory(0);
		lessonConfigBo2.setLessonId("lessonId2");

		lessonConfigBo.setChildren(Lists.newArrayList(lessonConfigBo1, lessonConfigBo2));

		when(mockLessonConfigService.getLessonConfig(eq("courseId1"), any(AuthContext.class)))
				.thenReturn(lessonConfigBo);

		// Run the test and verify the results
		mockMvc.perform(get("/oj/admin/lessonconfig").param("course_id", "courseId1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"courseId\":\"courseId1\",\"index\":0,\"compulsory\":0,\"lessonId\":null,\"type\":null,\"name\":null,\"nodeType\":null,\"parentId\":null,\"resourceId\":null,\"chapterName\":null,\"children\":[{\"courseId\":\"courseId1\",\"index\":0,\"compulsory\":0,\"lessonId\":\"lessonId1\",\"type\":null,\"name\":null,\"nodeType\":null,\"parentId\":null,\"resourceId\":null,\"chapterName\":null,\"children\":null,\"typeIndex\":null,\"learn\":false},{\"courseId\":\"courseId1\",\"index\":0,\"compulsory\":0,\"lessonId\":\"lessonId2\",\"type\":null,\"name\":null,\"nodeType\":null,\"parentId\":null,\"resourceId\":null,\"chapterName\":null,\"children\":null,\"typeIndex\":null,\"learn\":false}],\"typeIndex\":null,\"learn\":false},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testSetLessonConfig() throws Exception {
		// Setup
		// Configure LessonConfigService.updateLessonConfig(...).
		final LessonConfigBo lessonConfigBo = new LessonConfigBo();
		lessonConfigBo.setCourseId("courseId1");
		lessonConfigBo.setIndex(0);
		lessonConfigBo.setLearn(false);
		lessonConfigBo.setCompulsory(0);

		final LessonConfigBo lessonConfigBo1 = new LessonConfigBo();
		lessonConfigBo1.setCourseId("courseId1");
		lessonConfigBo1.setIndex(0);
		lessonConfigBo1.setLearn(false);
		lessonConfigBo1.setCompulsory(0);
		lessonConfigBo1.setLessonId("lessonId1");

		final LessonConfigBo lessonConfigBo2 = new LessonConfigBo();
		lessonConfigBo2.setCourseId("courseId1");
		lessonConfigBo2.setIndex(0);
		lessonConfigBo2.setLearn(false);
		lessonConfigBo2.setCompulsory(0);
		lessonConfigBo2.setLessonId("lessonId2");

		lessonConfigBo.setChildren(Lists.newArrayList(lessonConfigBo1, lessonConfigBo2));

		when(mockLessonConfigService.updateLessonConfig(any(LessonConfigBo.class)))
				.thenReturn(lessonConfigBo);

		// Run the test and verify the results
		mockMvc.perform(put("/oj/admin/lessonconfig").content(JsonUtil.toJsonString(lessonConfigBo))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("{\"data\":{\"courseId\":\"courseId1\",\"index\":0,\"compulsory\":0,\"lessonId\":null,\"type\":null,\"name\":null,\"nodeType\":null,\"parentId\":null,\"resourceId\":null,\"chapterName\":null,\"children\":[{\"courseId\":\"courseId1\",\"index\":0,\"compulsory\":0,\"lessonId\":\"lessonId1\",\"type\":null,\"name\":null,\"nodeType\":null,\"parentId\":null,\"resourceId\":null,\"chapterName\":null,\"children\":null,\"typeIndex\":null,\"learn\":false},{\"courseId\":\"courseId1\",\"index\":0,\"compulsory\":0,\"lessonId\":\"lessonId2\",\"type\":null,\"name\":null,\"nodeType\":null,\"parentId\":null,\"resourceId\":null,\"chapterName\":null,\"children\":null,\"typeIndex\":null,\"learn\":false}],\"typeIndex\":null,\"learn\":false},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}

