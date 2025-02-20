package com.cyberpeace.space.controller;

import com.cyberpeace.space.service.SpaceAlgorithmExpertiseService;
import com.cyberpeace.space.service.SpaceAlgorithmIndividualService;
import com.cyberpeace.space.service.SpaceAlgorithmRoleService;
import com.cyberpeace.space.service.SpaceAlgorithmTeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceAlgorithmCommonController.class)
class SpaceAlgorithmCommonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceAlgorithmExpertiseService mockExpertiseService;
    @MockBean
    private SpaceAlgorithmRoleService mockRoleService;
    @MockBean
    private SpaceAlgorithmIndividualService mockIndividualService;
    @MockBean
    private SpaceAlgorithmTeamService mockTeamService;

    @Test
    void testGetStatistics() throws Exception {
        // Setup
        when(mockExpertiseService.count()).thenReturn(0);
        when(mockRoleService.count()).thenReturn(0);
        when(mockIndividualService.count()).thenReturn(0);
        when(mockTeamService.count()).thenReturn(0);

        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/common/statistics")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"expertise\":0,\"role\":0,\"individual\":0,\"team\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
