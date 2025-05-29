package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmRole;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmTeam;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceAlgorithmRoleService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryBo;
import com.google.common.collect.Lists;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 这段代码是一个Spring Boot单元测试类，用于测试`SpaceAlgorithmRoleController`控制器的功能。以下是对代码的简要分析： 
 * 1. **测试环境配置**： 
 * - 使用`@WebMvcTest`注解来加载`SpaceAlgorithmRoleController`控制器，并自动配置Spring MVC测试环境。 
 * - 使用`@MockBean`创建了`CourseService`和`SpaceAlgorithmRoleService`的模拟对象，用于隔离依赖。 
 * - 使用`@SpyBean`创建了`SpaceAlgorithmRoleController`的实例，以便在测试中监控其行为。 
 * 2. **测试数据准备**： 
 * - 在`setUp`方法中，通过`when`方法定义了模拟服务的行为，例如返回空列表、特定的对象等。 
 * - 初始化了MyBatis的表信息助手，并设置了名称重复检查的方法返回值为`true`。 
 * 3. **测试方法**： 
 * - `testPage`：测试分页功能，验证GET请求返回的结果是否符合预期。 
 * - `testAddAttack`：测试添加功能，验证POST请求返回的结果是否正确。 
 * - `testUpdateA`：测试更新功能，验证PATCH请求返回的结果是否正确。 
 * - `testDeleteA`：测试删除功能，验证DELETE请求返回的结果是否正确。 
 * - `testGetADetail`：测试获取详情功能，验证GET请求返回的结果是否符合预期。 
 * 4. **断言**： 
 * - 使用`andExpect`方法对响应状态码、内容进行断言，确保API接口的行为与预期一致。 
 * 总结来说，这段代码通过Spring Boot的测试框架，全面地测试了`SpaceAlgorithmRoleController`控制器的各种功能，确保其能够正确处理HTTP请求并返回预期结果。 
**/
@WebMvcTest(SpaceAlgorithmRoleController.class)
class SpaceAlgorithmRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService mockCourseService;
    @MockBean
    private SpaceAlgorithmRoleService mockService;
    @SpyBean
    private SpaceAlgorithmRoleController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceAlgorithmRole());
        when(mockService.save(any(SpaceAlgorithmRole.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceAlgorithmRole.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);
        when(mockCourseService.listByIds(anyList())).thenReturn(Collections.emptyList());


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceBaseTable.class);

        doReturn(true).when(spaceTrafficExtractionRuleController).checkNameDuplication(any());

    }


    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/algorithm_role/list")
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
        mockMvc.perform(post("/space/algorithm/algorithm_role")
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
        mockMvc.perform(patch("/space/algorithm/algorithm_role")
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
        mockMvc.perform(delete("/space/algorithm/algorithm_role")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/algorithm_role")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"course\":[],\"coursePercent\":null,\"training\":[],\"trainingPercent\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

