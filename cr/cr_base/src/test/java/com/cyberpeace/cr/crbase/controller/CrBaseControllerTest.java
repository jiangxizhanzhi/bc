package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CrBaseController.class)
class CrBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrConstantsService mockCrConstantsService;

    @Test
    void testGetConstantsConfig() throws Exception {
        // Setup
        // Configure CrConstantsService.getByType(...).
        final CrConstantsConfig crConstantsConfig = new CrConstantsConfig();
        crConstantsConfig.setId(0);
        crConstantsConfig.setResourceId("resourceId");
        crConstantsConfig.setCode(0);
        crConstantsConfig.setType(0);
        crConstantsConfig.setCnName("cnName");
        final List<CrConstantsConfig> crConstantsConfigs = Arrays.asList(crConstantsConfig);
        when(mockCrConstantsService.getByType(0)).thenReturn(crConstantsConfigs);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/common/constants_config")
                        .param("type", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"code\":0,\"name\":\"cnName\"}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetConstantsConfig_CrConstantsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockCrConstantsService.getByType(0)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/cr/common/constants_config")
                        .param("type", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetCurrentTime() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/cr/common/current_time")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
