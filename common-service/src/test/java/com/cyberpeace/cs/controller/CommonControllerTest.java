
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
