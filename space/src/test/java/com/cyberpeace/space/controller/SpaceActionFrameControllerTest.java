package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceActionFrameController.class)
class SpaceActionFrameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceActionFrameService mockService;
    @MockBean
    private SpaceActionRelationService relationService;
    @MockBean
    private TransactionTemplate transactionTemplate;


    @Test
    void testGetFrame() throws Exception {
        // Setup
        // Configure SpaceActionFrameService.getFrameLevels(...).
        final SpaceFrameLevel spaceFrameLevel = new SpaceFrameLevel("resourceId", "name");
        when(mockService.getFrameLevels("resource_id")).thenReturn(spaceFrameLevel);

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_frame/frame")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"name\":\"name\",\"children\":[]},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testSaveFrame() throws Exception {
        // Setup
        // Run the test and verify the results
        when(mockService.saveFrame(any(SpaceFrameLevel.class))).thenReturn(true);
        mockMvc.perform(post("/space/action_frame/action_frame/frame")
                        .content(JsonUtil.toJsonString(new SpaceFrameLevel())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mockService).saveFrame(any(SpaceFrameLevel.class));
    }
}
