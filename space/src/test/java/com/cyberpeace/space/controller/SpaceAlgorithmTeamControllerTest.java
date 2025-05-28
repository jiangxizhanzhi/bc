/**
 * 这段代码是一个基于Spring Boot的单元测试类，用于测试`SpaceAlgorithmTeamController`控制器的功能。以下是对代码的简要分析： 
 * 1. **测试类结构**： 
 * - 类名为`SpaceAlgorithmTeamControllerTest`，继承自`WebMvcTest`，表明这是一个针对Web MVC层的单元测试。 
 * - 使用了JUnit 5的`@Test`注解来定义测试方法。 
 * 2. **依赖注入与模拟**： 
 * - 使用`@Autowired`注解注入`MockMvc`对象，用于发送HTTP请求并验证响应。 
 * - 使用`@MockBean`创建`SpaceAlgorithmTeamService`的模拟实例，用于模拟服务层的行为。 
 * - 使用`@SpyBean`创建`SpaceAlgorithmTeamController`的spy实例，用于部分模拟控制器行为。 
 * 3. **初始化设置**： 
 * - 在`setUp`方法中，通过`when`和`doReturn`设置模拟服务的行为，例如返回空列表、特定的对象等。 
 * - 初始化`TableInfoHelper`以支持MyBatis的动态SQL功能。 
 * 4. **测试方法**： 
 * - `testPage`：测试分页功能，验证GET请求返回正确的JSON格式数据。 
 * - `testAddAttack`：测试添加新记录的功能，验证POST请求返回正确的响应。 
 * - `testUpdateA`：测试更新记录的功能，验证PATCH请求返回正确的响应。 
 * - `testDeleteA`：测试删除记录的功能，验证DELETE请求返回正确的响应。 
 * - `testGetADetail`：测试获取单个记录详情的功能，验证GET请求返回正确的响应。 
 * 5. **断言**： 
 * - 使用`andExpect`方法断言HTTP状态码为`200 OK`，以及响应内容是否符合预期的JSON格式。 
 * 6. **工具类**： 
 * - 使用了`JsonUtil`将对象转换为JSON字符串。 
 * - 使用了Guava库中的`Lists.newArrayList`创建列表。 
 * 总体而言，这段代码展示了如何使用Spring Boot的测试框架来验证控制器的各个端点的功能，确保其行为符合预期。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmTeam;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceAlgorithmTeamService;
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

@WebMvcTest(SpaceAlgorithmTeamController.class)
class SpaceAlgorithmTeamControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceAlgorithmTeamService mockService;
    @SpyBean
    private SpaceAlgorithmTeamController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceAlgorithmTeam());
        when(mockService.save(any(SpaceAlgorithmTeam.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceAlgorithmTeam.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/algorithm/algorithm_team/list")
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
        mockMvc.perform(post("/space/algorithm/algorithm_team")
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
        mockMvc.perform(patch("/space/algorithm/algorithm_team")
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
        mockMvc.perform(delete("/space/algorithm/algorithm_team")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/algorithm_team")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"expertisePercent\":null,\"rolePercent\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
