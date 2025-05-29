package com.cyberpeace.cs.controller;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 这段代码是一个单元测试类 `CommonControllerTest`，用于测试 `CommonController` 的两个功能：图片上传 (`testImageUpload`) 和附件上传 (`testAttachmentUpload`)。以下是代码的主要分析： 
 * ### 1. **测试环境配置** 
 * - 使用了 `@WebMvcTest(CommonController.class)` 注解来加载 `CommonController` 的 Spring MVC 上下文。 
 * - 使用了 `@MockBean` 注解来模拟依赖的服务（`AttachmentService` 和 `UtilService`）。 
 * - 在 `setUp()` 方法中，设置了当前用户信息（`UserInfo`），并将其放入线程上下文中。 
 * ### 2. **测试方法** 
 * #### (1) `testImageUpload` 
 * - 模拟了 `AttachmentService.uploadFile()` 方法的行为，返回一个 `AttachmentBo` 对象。 
 * - 配置了 `UtilService.handlerFileUrl()` 方法的返回值。 
 * - 使用 `mockMvc.perform()` 发起多部分文件上传请求到 `/common/image_upload` 接口。 
 * - 验证响应状态码为 `200 OK`，并且响应内容符合预期 JSON 格式。 
 * #### (2) `testAttachmentUpload` 
 * - 类似于 `testImageUpload`，但测试的是 `/common/attachment_upload` 接口。 
 * - 模拟了 `AttachmentService.uploadDest()` 方法的行为，并配置了 `UtilService.handlerFileUrl()` 的返回值。 
 * - 使用 `mockMvc.perform()` 发起多部分文件上传请求，并验证响应状态码和内容。 
 * ### 3. **核心逻辑** 
 * - 测试了文件上传接口的功能，包括文件路径、资源 ID、文件名等字段的正确性。 
 * - 验证了服务层方法被正确调用，并且返回的结果与预期一致。 
 * ### 总结 
 * 该测试类通过模拟依赖服务和发起 HTTP 请求的方式，验证了 `CommonController` 中两个上传接口的功能正确性。代码结构清晰，测试覆盖全面，使用了 Spring Boot 提供的测试工具，确保了测试的可靠性和可维护性。 
**/

@WebMvcTest(CommonController.class)
class CommonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AttachmentService mockAttachmentService;

	@MockBean
	private UtilService mockUtilService;

	@BeforeEach
	void setUp() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("admin");
		userInfo.setPermissions(Sets.newHashSet("courses"));
		UserInfoContext.setUserContext(userInfo);
	}

	@Test
	void testImageUpload() throws Exception {
		// Setup
		// Configure AttachmentService.uploadFile(...).
		final AttachmentBo attachmentBo = AttachmentBo.builder().filePath("filePath").resourceId("123").fileName("123.jpg").build();
		when(mockAttachmentService.uploadFile(eq(12), eq(true), any(MultipartFile.class), eq(true)))
				.thenReturn(attachmentBo);

		when(mockUtilService.handlerFileUrl("filePath")).thenReturn("newFilePath");

		// Run the test and verify the results
		mockMvc.perform(multipart("/common/image_upload")
				.file(new MockMultipartFile("file", "123.jpg",
						MediaType.APPLICATION_JSON_VALUE, "content".getBytes()))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"resourceId\":\"123\",\"fileName\":\"123.jpg\",\"filePath\":\"newFilePath\",\"isPublic\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}

	@Test
	void testAttachmentUpload() throws Exception {
		// Setup
		// Configure AttachmentService.uploadDest(...).
		final AttachmentBo attachmentBo = AttachmentBo.builder().filePath("filePath").resourceId("456").fileName("456.zip").build();
		when(mockAttachmentService.uploadDest(any(String.class), eq(true), any(MultipartFile.class)))
				.thenReturn(attachmentBo);

		when(mockUtilService.handlerFileUrl("filePath")).thenReturn("newFilePath");

		// Run the test and verify the results
		mockMvc.perform(multipart("/common/attachment_upload")
				.file(new MockMultipartFile("file", "456.zip",
						MediaType.APPLICATION_JSON_VALUE, "content".getBytes()))
				.param("type", "0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"resourceId\":\"456\",\"fileName\":\"456.zip\",\"filePath\":\"newFilePath\",\"isPublic\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}

