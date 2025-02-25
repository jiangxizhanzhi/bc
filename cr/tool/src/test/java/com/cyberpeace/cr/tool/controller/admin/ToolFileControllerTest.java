
package com.cyberpeace.cr.tool.controller.admin;

import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.service.ToolFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ToolFileController.class)
class ToolFileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ToolFileService mockToolFileService;

	@Test
	void testUpdateToolFile() throws Exception {
		// Setup
		// Configure ToolFileService.saveFile(...).
		final ToolFile toolFile = new ToolFile();
		toolFile.setId(0);
		toolFile.setFileName("tool.zip");
		toolFile.setSavePath("savePath");
		toolFile.setFileUrl("fileUrl");
		when(mockToolFileService.saveFile(any(MultipartFile.class))).thenReturn(toolFile);

		// Run the test and verify the results
		mockMvc.perform(multipart("/cr/tools/file/upload_tool_file")
				.file(new MockMultipartFile("file", "tool.zip",
						MediaType.MULTIPART_FORM_DATA_VALUE, "content".getBytes()))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("{\"data\":{\"id\":0,\"fileName\":\"tool.zip\",\"savePath\":\"savePath\",\"fileUrl\":\"/cpms/savePath\"},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
	}
}
