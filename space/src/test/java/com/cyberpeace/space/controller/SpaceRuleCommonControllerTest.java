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

/**
 * 这段代码是一个Spring Boot单元测试类，用于测试`SpaceRuleCommonController`控制器的功能。以下是代码的主要分析： 
 * 1. **测试类结构**： 
 * - 使用了`@WebMvcTest`注解，专注于测试Spring MVC层（如控制器），并且自动配置了MockMvc。 
 * - 使用了`@MockBean`来模拟`SpaceRuleCommonService`服务层。 
 * 2. **测试方法**： 
 * - `testGetRaceRuleS`：测试获取种族统计数据的方法。通过设置模拟服务返回的`RaceStatistics`对象，并验证返回的JSON是否符合预期。 
 * - `testGetThreatRuleS`：测试获取威胁检测统计数据的方法。设置模拟服务返回的`ThreatDetectStatistics`对象，并验证返回的JSON是否符合预期。 
 * - `testGetBaseDataRuleS`：测试获取基础数据规则统计数据的方法。设置模拟服务返回的`BaseDataRuleStatistics`对象，并验证返回的JSON是否符合预期。 
 * 3. **核心功能**： 
 * - 每个测试方法都通过`MockMvc`发送GET请求到不同的API端点，并使用`andExpect`方法验证HTTP状态码和响应内容是否符合预期。 
 * 4. **依赖注入与模拟**： 
 * - 使用`@Autowired`注入`MockMvc`实例。 
 * - 使用`@MockBean`创建`SpaceRuleCommonService`的模拟对象，并定义其行为。 
 * 5. **断言**： 
 * - 使用`content().json()`方法验证返回的JSON格式和内容是否正确。 
 * - 使用`status().isOk()`验证HTTP响应状态码为200。 
 * 总结来说，这段代码通过模拟服务层的行为，全面测试了`SpaceRuleCommonController`中不同统计数据接口的功能，确保其返回的数据格式和内容符合预期。 
**/
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

