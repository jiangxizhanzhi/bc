package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmIndividual;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmTeam;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceAlgorithmIndividualService;
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
 * ### 代码分析 
 * #### 概述 
 * 该代码是一个单元测试类 `SpaceAlgorithmIndividualControllerTest`，用于测试 `SpaceAlgorithmIndividualController` 控制器的功能。它使用了 Spring Boot 的测试框架（如 `@WebMvcTest` 和 `MockMvc`）以及 Mockito 进行依赖注入和行为模拟。 
 * --- 
 * #### 主要功能模块 
 * 1. **测试环境初始化** 
 * - 使用 `@BeforeEach` 方法 `setUp()` 初始化测试环境： 
 * - 配置 `mockService` 的行为，模拟 `SpaceAlgorithmIndividualService` 的方法返回值。 
 * - 初始化分页对象 `Page` 并设置其记录为空列表。 
 * - 初始化表信息助手 `TableInfoHelper`，用于处理数据库表的元信息。 
 * - 模拟控制器中的方法 `checkNameDuplication` 返回 `true`。 
 * 2. **测试方法** 
 * - 测试了多个 HTTP 请求场景，包括： 
 * - **分页查询**：测试 `/space/algorithm/algorithm_individual/list` 接口的分页功能。 
 * - **新增数据**：测试 `/space/algorithm/algorithm_individual` 的 POST 请求。 
 * - **更新数据**：测试 `/space/algorithm/algorithm_individual` 的 PATCH 请求。 
 * - **删除数据**：测试 `/space/algorithm/algorithm_individual` 的 DELETE 请求。 
 * - **获取详情**：测试 `/space/algorithm/algorithm_individual` 的 GET 请求，通过参数 `resource_id` 获取单条记录。 
 * 3. **断言** 
 * - 使用 `MockMvc` 发起请求并验证响应状态码、内容是否符合预期： 
 * - 状态码为 `200 OK`。 
 * - 响应 JSON 内容与预期 JSON 字符串一致。 
 * --- 
 * #### 代码特点 
 * - **测试覆盖全面**：涵盖了常见的 CRUD 操作以及分页查询，确保控制器的主要功能被充分测试。 
 * - **依赖注入与模拟**：通过 `@MockBean` 和 `@SpyBean` 模拟服务层和控制器的行为，避免直接依赖实际数据库或服务。 
 * - **JSON 数据对比**：使用 `content().json()` 方法精确验证返回的 JSON 数据格式和内容。 
 * - **Spring Boot 测试工具**：利用 `@WebMvcTest` 针对 MVC 层进行轻量级测试，专注于控制器逻辑。 
 * --- 
 * #### 总结 
 * 该代码展示了如何使用 Spring Boot 和 Mockito 构建高效的单元测试，验证了 `SpaceAlgorithmIndividualController` 的核心功能。测试用例设计合理，能够有效捕获潜在问题，并保证接口行为符合预期。 
**/
@WebMvcTest(SpaceAlgorithmIndividualController.class)
class SpaceAlgorithmIndividualControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceAlgorithmIndividualService mockService;
    @SpyBean
    private SpaceAlgorithmIndividualController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceAlgorithmIndividual());
        when(mockService.save(any(SpaceAlgorithmIndividual.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceAlgorithmIndividual.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceBaseTable.class);

        doReturn(true).when(spaceTrafficExtractionRuleController).checkNameDuplication(any());

    }


    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/algorithm_individual/list")
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
        mockMvc.perform(post("/space/algorithm/algorithm_individual")
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
        mockMvc.perform(patch("/space/algorithm/algorithm_individual")
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
        mockMvc.perform(delete("/space/algorithm/algorithm_individual")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/algorithm_individual")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"expertisePercent\":null,\"rolePercent\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

