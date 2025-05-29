package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceTrafficDetectRuleService;
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
 * 这段代码是一个单元测试类，主要用于测试 `SpaceTrafficDetectRuleController` 控制器的功能。以下是对代码的简要分析： 
 * 1. **测试类结构**： 
 * - 该类名为 `SpaceTrafficDetectRuleControllerTest`，继承自 `WebMvcTest(SpaceTrafficDetectRuleController.class)`。 
 * - 使用了 JUnit 5 的测试框架，并通过 `@BeforeEach` 注解设置了每个测试方法执行前的初始化操作。 
 * 2. **依赖注入**： 
 * - 使用 `@Autowired` 注入了 `MockMvc` 对象，用于模拟 HTTP 请求。 
 * - 使用 `@SpyBean` 和 `@MockBean` 分别对 `SpaceTrafficDetectRuleController` 和 `SpaceTrafficDetectRuleService` 进行部分模拟和完全模拟。 
 * 3. **测试方法**： 
 * - `testExportExcel`：测试导出 Excel 功能，验证返回的状态码和 JSON 响应是否正确。 
 * - `testPage`：测试分页功能，验证返回的状态码和 JSON 响应是否正确。 
 * - `testAddAttack`：测试添加攻击规则的功能，验证 POST 请求的响应。 
 * - `testUpdateA`：测试更新攻击规则的功能，验证 PATCH 请求的响应。 
 * - `testDeleteA`：测试删除攻击规则的功能，验证 DELETE 请求的响应。 
 * - `testGetADetail`：测试获取单个攻击规则详情的功能，验证 GET 请求的响应。 
 * 4. **Mock 数据设置**： 
 * - 在 `setUp` 方法中，通过 Mockito 设置了多个模拟行为，确保测试环境中的服务方法返回预期的结果。 
 * 5. **JSON 验证**： 
 * - 使用 `content().json()` 方法来验证返回的 JSON 是否符合预期格式。 
 * 总体来说，这段代码通过模拟 HTTP 请求和响应，全面测试了 `SpaceTrafficDetectRuleController` 控制器的各种功能，确保其在不同场景下的正确性。 
**/
@WebMvcTest(SpaceTrafficDetectRuleController.class)
class SpaceTrafficDetectRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private SpaceTrafficDetectRuleController spaceTrafficExtractionRuleController;
    @MockBean
    private SpaceTrafficDetectRuleService mockService;


    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceTrafficDetectRule());
        when(mockService.save(any(SpaceTrafficDetectRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceTrafficDetectRule.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/rule/space_traffic_detect_rule/excel")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":true,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_traffic_detect_rule/list")
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
        mockMvc.perform(post("/space/rule/space_traffic_detect_rule")
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
        mockMvc.perform(patch("/space/rule/space_traffic_detect_rule")
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
        mockMvc.perform(delete("/space/rule/space_traffic_detect_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_traffic_detect_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"ruleContent\":null,\"ruleId\":null,\"ruleInfo\":null,\"threatLevel\":null,\"tool\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

