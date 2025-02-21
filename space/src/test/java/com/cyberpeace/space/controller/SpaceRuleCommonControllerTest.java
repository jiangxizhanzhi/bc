package com.cyberpeace.space.controller;

import com.cyberpeace.space.service.SpaceRuleCommonService;
import com.cyberpeace.space.service.context.rule.BaseDataRuleStatistics;
import com.cyberpeace.space.service.context.rule.RaceStatistics;
import com.cyberpeace.space.service.context.rule.ThreatDetectStatistics;
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

@WebMvcTest(SpaceRuleCommonController.class)
class SpaceRuleCommonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceRuleCommonService mockService;

    @Test
    void testGetRaceRuleS() throws Exception {
        // Setup
        // Configure SpaceRuleCommonService.getRaceStatistics(...).
        final RaceStatistics raceStatistics = new RaceStatistics();
        raceStatistics.setScoreRanking(0);
        raceStatistics.setRoundMechanism(0);
        raceStatistics.setViolationJudge(0);
        when(mockService.getRaceStatistics()).thenReturn(raceStatistics);

        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/common/race/statistics")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"scoreRanking\":0,\"roundMechanism\":0,\"violationJudge\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetThreatRuleS() throws Exception {
        // Setup
        // Configure SpaceRuleCommonService.getThreatDetectStatistics(...).
        final ThreatDetectStatistics threatDetectStatistics = new ThreatDetectStatistics();
        threatDetectStatistics.setTrafficExtraction(0);
        threatDetectStatistics.setTrafficDetect(0);
        threatDetectStatistics.setLoadedFileThreat(0);
        threatDetectStatistics.setAptThreat(0);
        threatDetectStatistics.setEventIntegration(0);
        when(mockService.getThreatDetectStatistics()).thenReturn(threatDetectStatistics);

        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/common/threat/statistics")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"trafficExtraction\":0,\"trafficDetect\":0,\"loadedFileThreat\":0,\"aptThreat\":0,\"eventIntegration\":0,\"threatImage\":null,\"sourceAnalyze\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetBaseDataRuleS() throws Exception {
        // Setup
        // Configure SpaceRuleCommonService.getBaseDataStatistics(...).
        final BaseDataRuleStatistics baseDataRuleStatistics = new BaseDataRuleStatistics();
        baseDataRuleStatistics.setVuln(0);
        baseDataRuleStatistics.setFingerPrint(0);
        baseDataRuleStatistics.setApt(0);
        baseDataRuleStatistics.setIoc(0);
        when(mockService.getBaseDataStatistics()).thenReturn(baseDataRuleStatistics);

        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/common/base_data/statistics")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"vuln\":0,\"fingerPrint\":0,\"apt\":0,\"ioc\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
