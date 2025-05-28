/**
 * 这段代码是一个单元测试类 `SpaceAptFeatureControllerTest`，用于测试 `SpaceAptFeatureController` 的功能。以下是对代码的简要分析： 
 * 1. **测试框架**： 
 * - 使用了 JUnit 5 (`org.junit.jupiter`) 进行单元测试。 
 * - 使用了 Spring Boot 的 `@WebMvcTest` 注解来加载 `SpaceAptFeatureController` 并进行 Web 层的测试。 
 * 2. **依赖注入**： 
 * - `MockMvc`：用于模拟 HTTP 请求并验证响应。 
 * - `@MockBean` 和 `@SpyBean`：分别用于模拟 `SpaceAptFeatureService` 和 `SpaceAptFeatureController` 的行为。 
 * 3. **初始化方法**： 
 * - `setUp()` 方法在每个测试之前执行，设置服务层的模拟行为，并初始化表信息。 
 * 4. **测试方法**： 
 * - `testPage()`：测试分页功能，验证返回的 JSON 数据是否符合预期。 
 * - `testAddAttack()`：测试添加攻击特征的功能，验证返回的 JSON 数据是否正确。 
 * - `testUpdateA()`：测试更新攻击特征的功能，验证返回的 JSON 数据是否正确。 
 * - `testDeleteA()`：测试删除攻击特征的功能，验证返回的 JSON 数据是否正确。 
 * - `testGetADetail()`：测试获取单个攻击特征详情的功能，验证返回的 JSON 数据是否正确。 
 * 5. **数据准备**： 
 * - 使用 `Lists.newArrayList()` 和 `JsonUtil.toJsonString()` 准备测试数据。 
 * 6. **断言**： 
 * - 使用 `andExpect()` 验证 HTTP 状态码、响应内容是否符合预期。 
 * 总体来说，这段代码通过模拟服务层的行为，全面测试了 `SpaceAptFeatureController` 的各个接口功能，确保其正确性。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceAptFeatureService;
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

@WebMvcTest(SpaceAptFeatureController.class)
class SpaceAptFeatureControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceAptFeatureService mockService;
    @SpyBean
    private SpaceAptFeatureController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceAptFeature());
        when(mockService.save(any(SpaceAptFeature.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceAptFeature.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/rule/space_apt_feature/list")
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
        mockMvc.perform(post("/space/rule/space_apt_feature")
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
        mockMvc.perform(patch("/space/rule/space_apt_feature")
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
        mockMvc.perform(delete("/space/rule/space_apt_feature")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_apt_feature")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"alias\":null,\"attackerType\":null,\"skill\":null,\"source\":null,\"firstTime\":null,\"latestTime\":null,\"effected\":null,\"industry\":null,\"method\":null,\"frequency\":null,\"language\":null,\"organization\":null,\"tactic\":null,\"historyReport\":[],\"logo\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
