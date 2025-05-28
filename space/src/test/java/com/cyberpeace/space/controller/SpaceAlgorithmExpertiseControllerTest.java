/**
 * 这段代码是一个针对 `SpaceAlgorithmExpertiseController` 的单元测试类。以下是对其的简要分析： 
 * ### 主要功能： 
 * 1. **测试类定义**：该类名为 `SpaceAlgorithmExpertiseControllerTest`，使用了 JUnit 5 的 `@WebMvcTest` 注解来加载指定的控制器类 `SpaceAlgorithmExpertiseController`。 
 * 2. **依赖注入与模拟**：通过 `@Autowired` 和 `@MockBean` 注解注入了 `MockMvc` 对象和其他服务层的模拟对象（如 `SpaceAlgorithmExpertiseService` 和 `CourseService`），用于模拟控制器的行为。 
 * 3. **初始化设置**：在 `setUp()` 方法中配置了多个服务方法的返回值，包括分页查询、新增、更新、删除等操作，并初始化了一些基础数据。 
 * ### 测试方法： 
 * - **testPage()**：测试分页接口 `/space/algorithm/algorithm_expertise/list`，验证返回的状态码为 200 并且 JSON 格式正确。 
 * - **testAddAttack()**：测试新增接口 `/space/algorithm/algorithm_expertise`，验证 POST 请求后返回的状态码为 200 并且 JSON 格式正确。 
 * - **testUpdateA()**：测试更新接口 `/space/algorithm/algorithm_expertise`，验证 PATCH 请求后返回的状态码为 200 并且 JSON 格式正确。 
 * - **testDeleteA()**：测试删除接口 `/space/algorithm/algorithm_expertise`，验证 DELETE 请求后返回的状态码为 200 并且 JSON 格式正确。 
 * - **testGetADetail()**：测试获取详情接口 `/space/algorithm/algorithm_expertise`，验证 GET 请求后返回的状态码为 200 并且 JSON 格式正确。 
 * ### 总结： 
 * 此代码主要用于确保 `SpaceAlgorithmExpertiseController` 中的各个 API 接口能够正常工作，涵盖了常见的 CRUD 操作以及分页查询的功能。通过模拟服务层的行为，保证了测试的独立性和准确性。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmExpertise;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmRole;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceAlgorithmExpertiseService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceAlgorithmExpertiseController.class)
class SpaceAlgorithmExpertiseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceAlgorithmExpertiseService mockService;
    @SpyBean
    private SpaceAlgorithmExpertiseController spaceTrafficExtractionRuleController;

    @MockBean
    private CourseService mockCourseService;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceAlgorithmExpertise());
        when(mockService.save(any(SpaceAlgorithmExpertise.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceAlgorithmExpertise.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/algorithm/algorithm_expertise/list")
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
        mockMvc.perform(post("/space/algorithm/algorithm_expertise")
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
        mockMvc.perform(patch("/space/algorithm/algorithm_expertise")
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
        mockMvc.perform(delete("/space/algorithm/algorithm_expertise")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/algorithm_expertise")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"course\":[],\"coursePercent\":null,\"training\":[],\"trainingPercent\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
