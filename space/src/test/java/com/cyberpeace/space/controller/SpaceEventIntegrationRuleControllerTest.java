package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.service.SpaceEventIntegrationRuleService;
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
 * 这段代码是一个单元测试类，用于测试 `SpaceEventIntegrationRuleController` 控制器的功能。以下是代码的主要内容和功能分析： 
 * 1. **测试环境设置**： 
 * - 使用了 `@WebMvcTest` 注解来加载仅包含 Web 层的 Spring 上下文。 
 * - `MockMvc` 用于模拟 HTTP 请求并验证响应。 
 * - 使用 `@MockBean` 和 `@SpyBean` 注解分别创建了 `SpaceEventIntegrationRuleService` 的模拟对象和 `SpaceEventIntegrationRuleController` 的部分模拟对象。 
 * 2. **初始化方法 (`setUp`)**： 
 * - 在每个测试之前执行，设置服务层的方法返回值，例如 `list()`、`getById()`、`save()` 等。 
 * - 初始化了一些数据结构，如 `Page` 对象，并设置了表信息的帮助类。 
 * 3. **测试方法**： 
 * - **`testExportExcel`**：测试导出 Excel 功能，验证返回状态和 JSON 响应。 
 * - **`testPage`**：测试分页功能，验证返回状态和 JSON 响应。 
 * - **`testAddAttack`**：测试添加规则功能，验证 POST 请求的响应。 
 * - **`testUpdateA`**：测试更新规则功能，验证 PATCH 请求的响应。 
 * - **`testDeleteA`**：测试删除规则功能，验证 DELETE 请求的响应。 
 * - **`testGetADetail`**：测试获取单个规则详情功能，验证 GET 请求的响应。 
 * 4. **工具类和依赖**： 
 * - 使用了 `JsonUtil` 来处理 JSON 数据。 
 * - 使用了 Guava 的 `Lists.newArrayList()` 方法来创建列表。 
 * 5. **断言**： 
 * - 使用 `andExpect` 方法验证 HTTP 状态码和 JSON 响应内容是否符合预期。 
 * 总体来说，这段代码通过模拟 HTTP 请求和响应，全面测试了 `SpaceEventIntegrationRuleController` 的各种功能，确保其行为符合预期。 
**/
@WebMvcTest(SpaceEventIntegrationRuleController.class)
class SpaceEventIntegrationRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceEventIntegrationRuleService mockService;
    @SpyBean
    private SpaceEventIntegrationRuleController spaceTrafficExtractionRuleController;


    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceEventIntegrationRule());
        when(mockService.save(any(SpaceEventIntegrationRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceEventIntegrationRule.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceEventIntegrationRule.class);

        doReturn(true).when(spaceTrafficExtractionRuleController).checkNameDuplication(any());
        doReturn(new BaseRsp<>(Boolean.TRUE)).when(spaceTrafficExtractionRuleController).exportExcel(any(), any());

    }

    @Test
    void testExportExcel() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_event_integration_rule/excel")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":true,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_event_integration_rule/list")
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
        mockMvc.perform(post("/space/rule/space_event_integration_rule")
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
        mockMvc.perform(patch("/space/rule/space_event_integration_rule")
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
        mockMvc.perform(delete("/space/rule/space_event_integration_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_event_integration_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"type\":null,\"description\":null,\"content\":null,\"tool\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

