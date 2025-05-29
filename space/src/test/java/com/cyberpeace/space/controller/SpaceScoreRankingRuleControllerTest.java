package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.dao.entity.SpaceSourceAnalyzeRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceScoreRankingRuleService;
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
 * 这段代码是一个Spring Boot单元测试类，主要用于测试`SpaceScoreRankingRuleController`控制器的功能。以下是代码的主要功能和结构分析： 
 * 1. **测试环境配置**： 
 * - 使用了`@WebMvcTest`注解来加载`SpaceScoreRankingRuleController`相关的Web层组件。 
 * - 使用`MockMvc`模拟HTTP请求，验证控制器的响应。 
 * 2. **依赖注入与Mock**： 
 * - `@Autowired`注入了`MockMvc`实例，用于发送HTTP请求。 
 * - `@MockBean`模拟了`SpaceScoreRankingRuleService`服务层，提供预定义的行为。 
 * - `@SpyBean`用于部分模拟`SpaceScoreRankingRuleController`自身的方法。 
 * 3. **测试方法**： 
 * - `testPage`：测试分页接口，验证返回的JSON数据是否符合预期。 
 * - `testAddAttack`：测试新增规则接口，验证POST请求的响应。 
 * - `testUpdateA`：测试更新规则接口，验证PATCH请求的响应。 
 * - `testDeleteA`：测试删除规则接口，验证DELETE请求的响应。 
 * - `testGetADetail`：测试获取单条规则详情接口，验证GET请求的响应。 
 * 4. **数据准备**： 
 * - 在`setUp`方法中，通过`when`和`doReturn`设置服务层方法的返回值，模拟数据库操作。 
 * - 初始化了一些必要的实体对象和请求参数。 
 * 5. **断言**： 
 * - 使用`andExpect`方法验证HTTP状态码、响应内容和JSON格式是否符合预期。 
 * 总结来说，这段代码通过模拟HTTP请求和响应，全面测试了`SpaceScoreRankingRuleController`的所有主要接口，确保其功能正常。 
**/
@WebMvcTest(SpaceScoreRankingRuleController.class)
class SpaceScoreRankingRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceScoreRankingRuleService mockService;
    @SpyBean
    private SpaceScoreRankingRuleController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceScoreRankingRule());
        when(mockService.save(any(SpaceScoreRankingRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceScoreRankingRule.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/rule/space_score_ranking_rule/list")
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
        mockMvc.perform(post("/space/rule/space_score_ranking_rule")
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
        mockMvc.perform(patch("/space/rule/space_score_ranking_rule")
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
        mockMvc.perform(delete("/space/rule/space_score_ranking_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_score_ranking_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"description\":null,\"taskScoreType\":null,\"roundScoreType\":null,\"sumScoreType\":null,\"firstBlood\":null,\"secondBlood\":null,\"thirdBlood\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

