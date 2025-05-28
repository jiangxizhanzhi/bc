/**
 * 这段代码是一个针对 `SpaceFingerPrintController` 的单元测试类，使用了 JUnit 5 和 Spring Boot 测试框架。以下是代码的主要分析： 
 * ### 主要功能 
 * 1. **测试目标**： 
 * - 测试 `SpaceFingerPrintController` 中的各种 HTTP 请求处理逻辑，包括分页查询、新增、更新、删除和获取详情。 
 * 2. **测试方法**： 
 * - 使用 `MockMvc` 模拟 HTTP 请求，并验证响应状态码和 JSON 响应内容。 
 * - 通过 `@MockBean` 和 `@SpyBean` 注解模拟服务层的行为。 
 * 3. **测试覆盖的功能点**： 
 * - 分页查询 (`testPage`) 
 * - 新增 (`testAddAttack`) 
 * - 更新 (`testUpdateA`) 
 * - 删除 (`testDeleteA`) 
 * - 获取详情 (`testGetADetail`) 
 * ### 关键点解析 
 * #### 1. 测试环境搭建 
 * - 使用 `@WebMvcTest` 注解，仅加载与 Web 层相关的配置。 
 * - `MockMvc` 用于模拟 HTTP 请求。 
 * - `@MockBean` 和 `@SpyBean` 用于模拟依赖的服务层和控制器行为。 
 * #### 2. 测试数据准备 
 * - 在 `setUp` 方法中，为 `SpaceFingerPrintService` 的各种方法设置返回值。 
 * - 初始化 `TableInfoHelper` 以支持 MyBatis 的表信息初始化。 
 * #### 3. 测试用例设计 
 * - 每个测试用例都模拟了一个具体的 HTTP 请求，并验证其响应是否符合预期。 
 * - 使用 `JsonUtil.toJsonString` 将请求对象转换为 JSON 格式。 
 * - 验证响应的状态码和 JSON 内容是否匹配预设值。 
 * #### 4. 测试工具 
 * - 使用 `Mockito` 的 `when` 和 `doReturn` 方法模拟服务层行为。 
 * - 使用 `MockMvc` 提供的 `perform` 方法发送 HTTP 请求，并通过 `andExpect` 验证响应。 
 * ### 总结 
 * 该代码通过单元测试全面覆盖了 `SpaceFingerPrintController` 的核心功能，确保了各个接口在不同场景下的正确性。测试用例的设计清晰且易于维护，适合团队协作开发和持续集成环境。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceFingerPrint;
import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceFingerPrintService;
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

@WebMvcTest(SpaceFingerPrintController.class)
class SpaceFingerPrintControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceFingerPrintService mockService;
    @SpyBean
    private SpaceFingerPrintController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceFingerPrint());
        when(mockService.save(any(SpaceFingerPrint.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceFingerPrint.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/rule/space_finger_print/list")
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
        mockMvc.perform(post("/space/rule/space_finger_print")
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
        mockMvc.perform(patch("/space/rule/space_finger_print")
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
        mockMvc.perform(delete("/space/rule/space_finger_print")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_finger_print")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"content\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
