/**
 * ### 代码分析 
 * #### 概述 
 * 该代码是一个基于Spring Boot框架的单元测试类，用于测试`SpaceBaseAlgorithmController`控制器的功能。测试类使用了JUnit 5和MockMvc工具来模拟HTTP请求并验证响应。 
 * #### 主要功能模块 
 * 1. **依赖注入与Mock对象**： 
 * - 使用`@Autowired`注解注入`MockMvc`对象。 
 * - 使用`@MockBean`和`@SpyBean`注解创建服务层和控制器的Mock对象，以便在测试中模拟其行为。 
 * 2. **初始化方法 (`setUp`)** 
 * - 在每个测试之前调用`setUp`方法，设置Mock服务的行为，例如： 
 * - 返回空列表或特定对象。 
 * - 模拟分页查询返回空记录。 
 * - 初始化MyBatis表信息。 
 * - 模拟检查重复名称的方法。 
 * 3. **测试方法** 
 * - **`testPage`**：测试分页接口，验证返回的数据格式是否符合预期。 
 * - **`testAddAttack`**：测试新增接口，验证POST请求的响应。 
 * - **`testUpdateA`**：测试更新接口，验证PATCH请求的响应。 
 * - **`testDeleteA`**：测试删除接口，验证DELETE请求的响应。 
 * - **`testGetADetail`**：测试获取单个记录的接口，验证GET请求的响应。 
 * 4. **断言** 
 * - 使用`MockMvcResultMatchers`验证HTTP状态码、响应内容和JSON结构。 
 * #### 总结 
 * 该代码通过MockMvc模拟HTTP请求，测试了`SpaceBaseAlgorithmController`的多个接口，包括分页、新增、更新、删除和详情获取。每个测试都验证了响应的状态码和JSON格式，确保接口行为符合预期。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceBaseAlgorithm;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceBaseAlgorithmService;
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

@WebMvcTest(SpaceBaseAlgorithmController.class)
class SpaceBaseAlgorithmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceBaseAlgorithmService mockService;
    @SpyBean
    private SpaceBaseAlgorithmController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceBaseAlgorithm());
        when(mockService.save(any(SpaceBaseAlgorithm.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceBaseAlgorithm.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/algorithm/base_algorithm/list")
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
        mockMvc.perform(post("/space/algorithm/base_algorithm")
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
        mockMvc.perform(patch("/space/algorithm/base_algorithm")
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
        mockMvc.perform(delete("/space/algorithm/base_algorithm")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/base_algorithm")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"description\":null,\"type\":null,\"codeType\":null,\"code\":null,\"parameter\":[]},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
