package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceThreatImageRuleService;
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
 * 这段代码是一个基于Spring Boot的单元测试类，用于测试`SpaceThreatImageRuleController`控制器的功能。以下是代码的主要分析： 
 * 1. **测试类结构**： 
 * - 类名为`SpaceThreatImageRuleControllerTest`，继承自`WebMvcTest`注解，表明这是一个针对Web层的单元测试。 
 * - 测试类中包含了多个测试方法，每个方法对应一个具体的API接口。 
 * 2. **依赖注入**： 
 * - 使用`@Autowired`注解注入了`MockMvc`对象，用于模拟HTTP请求。 
 * - 使用`@SpyBean`和`@MockBean`注解分别创建了`SpaceThreatImageRuleController`的spy实例和`SpaceThreatImageRuleService`的mock实例。 
 * 3. **初始化设置**： 
 * - 在`setUp`方法中，通过`when`和`doReturn`设置了mock服务的行为，包括返回空列表、特定的对象等。 
 * - 初始化了`TableInfoHelper`，用于处理数据库表信息。 
 * 4. **测试方法**： 
 * - `testPage`：测试分页功能，验证GET请求是否返回正确的JSON响应。 
 * - `testAddAttack`：测试添加攻击规则的功能，验证POST请求是否成功。 
 * - `testUpdateA`：测试更新规则的功能，验证PATCH请求是否成功。 
 * - `testDeleteA`：测试删除规则的功能，验证DELETE请求是否成功。 
 * - `testGetADetail`：测试获取单个规则详情的功能，验证GET请求是否返回正确的JSON响应。 
 * 5. **断言**： 
 * - 使用`andExpect`方法对HTTP状态码、响应内容进行断言，确保API行为符合预期。 
 * 6. **工具类**： 
 * - 使用了`JsonUtil`来将对象转换为JSON字符串，并使用`MediaType.APPLICATION_JSON`指定请求和响应的内容类型。 
 * 总体来看，这段代码通过模拟HTTP请求和响应，全面测试了`SpaceThreatImageRuleController`中各个API接口的功能，确保其行为符合预期。 
**/
@WebMvcTest(SpaceThreatImageRuleController.class)
class SpaceThreatImageRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private SpaceThreatImageRuleController spaceTrafficExtractionRuleController;
    @MockBean
    private SpaceThreatImageRuleService mockService;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceThreatImageRule());
        when(mockService.save(any(SpaceThreatImageRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceThreatImageRule.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/rule/space_threat_image_rule/list")
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
        mockMvc.perform(post("/space/rule/space_threat_image_rule")
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
        mockMvc.perform(patch("/space/rule/space_threat_image_rule")
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
        mockMvc.perform(delete("/space/rule/space_threat_image_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_threat_image_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"level\":null,\"assetType\":null,\"information\":null,\"result\":null,\"tag\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

