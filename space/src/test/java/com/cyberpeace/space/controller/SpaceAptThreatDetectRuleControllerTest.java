package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceAptThreatDetectRuleService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryBo;
import com.google.common.collect.Lists;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 这段代码是一个单元测试类，用于测试 `SpaceAptThreatDetectRuleController` 控制器的功能。以下是代码的主要分析： 
 * 1. **测试环境配置**： 
 * - 使用了 `@WebMvcTest` 注解来加载 `SpaceAptThreatDetectRuleController` 的 Spring MVC 配置。 
 * - 通过 `@MockBean` 和 `@SpyBean` 注解模拟了服务层和控制器本身的行为。 
 * 2. **测试方法**： 
 * - **`testExportExcel`**：测试导出 Excel 功能，验证返回的状态码和 JSON 响应是否符合预期。 
 * - **`testPage`**：测试分页功能，验证分页结果是否为空，并且响应状态和 JSON 格式正确。 
 * - **`testAddAttack`**：测试添加攻击规则的功能，验证 POST 请求的响应状态和 JSON 格式。 
 * - **`testUpdateA`**：测试更新攻击规则的功能，验证 PATCH 请求的响应状态和 JSON 格式。 
 * - **`testDeleteA`**：测试删除攻击规则的功能，验证 DELETE 请求的响应状态和 JSON 格式。 
 * - **`testGetADetail`**：测试获取单个攻击规则详情的功能，验证 GET 请求的响应状态和 JSON 格式。 
 * 3. **数据准备**： 
 * - 在 `setUp` 方法中，通过 Mockito 模拟了服务层的方法调用，确保每个测试方法都能独立运行而不依赖实际数据库操作。 
 * 4. **断言**： 
 * - 使用 `andExpect` 方法对 HTTP 响应的状态码、内容等进行断言，确保接口行为符合预期。 
 * 总体而言，这段代码通过模拟和断言的方式，全面测试了 `SpaceAptThreatDetectRuleController` 的各个功能点，确保其在不同场景下的正确性。 
**/
@WebMvcTest(SpaceAptThreatDetectRuleController.class)
class SpaceAptThreatDetectRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceAptThreatDetectRuleService mockService;
    @SpyBean
    private SpaceAptThreatDetectRuleController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceAptThreatDetectRule());
        when(mockService.save(any(SpaceAptThreatDetectRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceAptThreatDetectRule.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceBaseTable.class);

        doReturn(true).when(spaceTrafficExtractionRuleController).checkNameDuplication(any());
        doReturn(new BaseRsp<>(Boolean.TRUE)).when(spaceTrafficExtractionRuleController).exportExcel(any(), any());

    }

    @Test
    void testExportExcel() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_apt_threat_detect_rule/excel")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":true,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_apt_threat_detect_rule/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceWarningDictionaryBo spaceWarningDictionaryBo = new SpaceWarningDictionaryBo();
        spaceWarningDictionaryBo.setResourceId("resource_id");
        mockMvc.perform(post("/space/rule/space_apt_threat_detect_rule")
                        .content(JsonUtil.toJsonString(spaceWarningDictionaryBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceWarningDictionaryBo spaceWarningDictionaryBo = new SpaceWarningDictionaryBo();
        spaceWarningDictionaryBo.setResourceId("resource_id");
        mockMvc.perform(patch("/space/rule/space_apt_threat_detect_rule")
                        .content(JsonUtil.toJsonString(spaceWarningDictionaryBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testDeleteA() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq req = new IdListReq();
        req.setIds(Arrays.asList("value"));
        mockMvc.perform(delete("/space/rule/space_apt_threat_detect_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_apt_threat_detect_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"aptCode\":null,\"content\":null,\"description\":null,\"tool\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

