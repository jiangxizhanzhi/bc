package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceRoundMechanismRuleService;
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
 * 这段代码是一个单元测试类，用于测试 `SpaceRoundMechanismRuleController` 控制器的功能。以下是对代码的简要分析： 
 * 1. **测试环境设置**： 
 * - 使用了 `@WebMvcTest` 注解来加载与 `SpaceRoundMechanismRuleController` 相关的 Spring MVC 组件。 
 * - `MockMvc` 用于模拟 HTTP 请求并验证响应。 
 * - `@MockBean` 和 `@SpyBean` 分别用于模拟服务层和控制器实例。 
 * 2. **测试方法**： 
 * - `testPage()`：测试分页功能，验证返回的状态码和 JSON 响应是否符合预期。 
 * - `testAddAttack()`：测试添加规则的功能，验证 POST 请求的响应。 
 * - `testUpdateA()`：测试更新规则的功能，验证 PATCH 请求的响应。 
 * - `testDeleteA()`：测试删除规则的功能，验证 DELETE 请求的响应。 
 * - `testGetADetail()`：测试获取单个规则详情的功能，验证 GET 请求的响应。 
 * 3. **数据准备**： 
 * - 在 `setUp()` 方法中，通过 Mockito 模拟了服务层的方法行为，确保测试环境中的依赖行为可控。 
 * 4. **JSON 验证**： 
 * - 使用 `JsonUtil.toJsonString` 将对象转换为 JSON 字符串，并通过 `content().json()` 验证返回的 JSON 是否符合预期。 
 * 5. **工具类和依赖**： 
 * - 使用了 `Guava` 的 `Lists.newArrayList()` 创建列表。 
 * - 使用了 `MybatisConfiguration` 和 `MapperBuilderAssistant` 初始化表信息。 
 * 总体而言，这段代码通过单元测试全面覆盖了控制器的主要功能点，确保了每个接口的行为符合预期。 
**/
@WebMvcTest(SpaceRoundMechanismRuleController.class)
class SpaceRoundMechanismRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceRoundMechanismRuleService mockService;
    @SpyBean
    private SpaceRoundMechanismRuleController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceRoundMechanismRule());
        when(mockService.save(any(SpaceRoundMechanismRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceRoundMechanismRule.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/rule/space_round_mechanism_rule/list")
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
        mockMvc.perform(post("/space/rule/space_round_mechanism_rule")
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
        mockMvc.perform(patch("/space/rule/space_round_mechanism_rule")
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
        mockMvc.perform(delete("/space/rule/space_round_mechanism_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_round_mechanism_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"description\":null,\"roundLength\":null,\"initScore\":null,\"maxResetTime\":null,\"minResetInterval\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

