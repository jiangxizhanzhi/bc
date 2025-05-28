/**
 * 这段代码是一个针对 `SpaceSourceAnalyzeRuleController` 的单元测试类，使用了 JUnit 5 和 Spring Boot 测试框架。以下是对代码的简要分析： 
 * ### 主要功能 
 * 1. **测试目标**：该类主要用于测试 `SpaceSourceAnalyzeRuleController` 控制器的功能，包括分页、新增、更新、删除和获取详细信息等操作。 
 * 2. **依赖注入**： 
 * - 使用 `@Autowired` 注入 `MockMvc` 对象用于模拟 HTTP 请求。 
 * - 使用 `@MockBean` 模拟 `SpaceSourceAnalyzeRuleService` 的行为。 
 * - 使用 `@SpyBean` 模拟 `SpaceSourceAnalyzeRuleController` 的部分方法。 
 * ### 测试方法 
 * 1. **`testPage`**： 
 * - 测试分页功能，验证返回结果是否符合预期。 
 * - 使用 `get` 方法请求 `/space/rule/space_source_analyze_rule/list`。 
 * - 验证返回状态为 `200 OK`，且 JSON 内容与预期一致。 
 * 2. **`testAddAttack`**： 
 * - 测试新增功能，通过 `post` 方法请求 `/space/rule/space_source_analyze_rule`。 
 * - 验证返回状态为 `200 OK`，且 JSON 内容与预期一致。 
 * 3. **`testUpdateA`**： 
 * - 测试更新功能，通过 `patch` 方法请求 `/space/rule/space_source_analyze_rule`。 
 * - 验证返回状态为 `200 OK`，且 JSON 内容与预期一致。 
 * 4. **`testDeleteA`**： 
 * - 测试删除功能，通过 `delete` 方法请求 `/space/rule/space_source_analyze_rule`。 
 * - 验证返回状态为 `200 OK`，且 JSON 内容与预期一致。 
 * 5. **`testGetADetail`**： 
 * - 测试获取详细信息功能，通过 `get` 方法请求 `/space/rule/space_source_analyze_rule`。 
 * - 验证返回状态为 `200 OK`，且 JSON 内容与预期一致。 
 * ### 初始化设置 
 * - 在 `setUp` 方法中，通过 `when` 和 `doReturn` 设置了多个模拟行为，确保测试时服务层的行为可控。 
 * - 初始化了分页对象 `Page` 和表信息 `TableInfoHelper`，用于支持后续测试。 
 * ### 总结 
 * 该测试类覆盖了控制器的主要功能点，通过模拟 HTTP 请求和响应，验证了控制器的行为是否符合预期。代码结构清晰，测试用例设计合理，能够有效保证控制器的正确性。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceSourceAnalyzeRule;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceSourceAnalyzeRuleService;
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

@WebMvcTest(SpaceSourceAnalyzeRuleController.class)
class SpaceSourceAnalyzeRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceSourceAnalyzeRuleService mockService;
    @SpyBean
    private SpaceSourceAnalyzeRuleController spaceTrafficExtractionRuleController;



    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceSourceAnalyzeRule());
        when(mockService.save(any(SpaceSourceAnalyzeRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceSourceAnalyzeRule.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/rule/space_source_analyze_rule/list")
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
        mockMvc.perform(post("/space/rule/space_source_analyze_rule")
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
        mockMvc.perform(patch("/space/rule/space_source_analyze_rule")
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
        mockMvc.perform(delete("/space/rule/space_source_analyze_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_source_analyze_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"rule\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
