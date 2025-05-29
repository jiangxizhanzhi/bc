package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmEquipment;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceWarningDictionaryService;
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
 * 这段代码是一个针对 `SpaceWarningDictionaryController` 的单元测试类，使用了 JUnit 5 和 Spring Boot 提供的测试工具。以下是代码的主要内容和功能分析： 
 * --- 
 * ### **1. 测试类概述** 
 * - **测试类名称**：`SpaceWarningDictionaryControllerTest` 
 * - **测试目标**：对 `SpaceWarningDictionaryController` 类的功能进行单元测试。 
 * - **测试框架**：使用了 JUnit 5 和 Spring Boot 的 `@WebMvcTest` 注解，专注于测试控制器层。 
 * --- 
 * ### **2. 主要依赖和配置** 
 * - **Spring Test Context**： 
 * - 使用 `@WebMvcTest(SpaceWarningDictionaryController.class)` 注解，加载 `SpaceWarningDictionaryController` 并自动配置相关的 MVC 上下文。 
 * - 使用 `@MockBean` 模拟 `SpaceWarningDictionaryService` 服务层，避免实际调用数据库或外部服务。 
 * - 使用 `@SpyBean` 对 `SpaceWarningDictionaryController` 进行部分方法的模拟行为。 
 * - **MockMvc**： 
 * - `mockMvc` 是 Spring 提供的用于模拟 HTTP 请求和响应的对象。 
 * - 通过 `mockMvc.perform()` 方法发送请求并验证响应。 
 * --- 
 * ### **3. 测试数据准备** 
 * 在 `setUp()` 方法中，为模拟服务和控制器设置了各种默认返回值： 
 * - `mockService` 返回空列表、空对象等。 
 * - `TableInfoHelper` 初始化表信息，用于 MyBatis Plus 的元数据操作。 
 * - 控制器方法（如 `checkNameDuplication` 和 `exportExcel`）被模拟为返回特定的结果。 
 * --- 
 * ### **4. 单元测试方法** 
 * 以下是对每个测试方法的简要分析： 
 * #### **(1) `testExportExcel`** 
 * - **测试场景**：测试导出 Excel 功能。 
 * - **HTTP 请求**：发送 GET 请求到 `/space/rule/space_warning_dictionary/excel`。 
 * - **验证结果**：期望返回状态码为 200，并且响应 JSON 数据符合预期格式。 
 * #### **(2) `testPage`** 
 * - **测试场景**：测试分页查询功能。 
 * - **HTTP 请求**：发送 GET 请求到 `/space/rule/space_warning_dictionary/list`。 
 * - **验证结果**：期望返回状态码为 200，并且响应 JSON 数据符合预期格式（分页数据为空）。 
 * #### **(3) `testAddAttack`** 
 * - **测试场景**：测试添加警告字典项的功能。 
 * - **HTTP 请求**：发送 POST 请求到 `/space/rule/space_warning_dictionary`，携带 JSON 格式的请求体。 
 * - **验证结果**：期望返回状态码为 200，并且响应 JSON 数据符合预期格式。 
 * #### **(4) `testUpdateA`** 
 * - **测试场景**：测试更新警告字典项的功能。 
 * - **HTTP 请求**：发送 PATCH 请求到 `/space/rule/space_warning_dictionary`，携带 JSON 格式的请求体。 
 * - **验证结果**：期望返回状态码为 200，并且响应 JSON 数据符合预期格式。 
 * #### **(5) `testDeleteA`** 
 * - **测试场景**：测试删除警告字典项的功能。 
 * - **HTTP 请求**：发送 DELETE 请求到 `/space/rule/space_warning_dictionary`，携带 JSON 格式的请求体。 
 * - **验证结果**：期望返回状态码为 200，并且响应 JSON 数据符合预期格式。 
 * #### **(6) `testGetADetail`** 
 * - **测试场景**：测试获取单个警告字典项详情的功能。 
 * - **HTTP 请求**：发送 GET 请求到 `/space/rule/space_warning_dictionary`，携带查询参数 `resource_id`。 
 * - **验证结果**：期望返回状态码为 200，并且响应 JSON 数据符合预期格式（空对象）。 
 * --- 
 * ### **5. 总结** 
 * - **测试范围**：覆盖了控制器的核心功能，包括分页查询、新增、更新、删除和详情获取。 
 * - **测试重点**：主要验证 HTTP 请求路径、请求方法、请求参数、响应状态码以及响应 JSON 数据的正确性。 
 * - **优点**：使用了 Spring 提供的测试工具，减少了对外部依赖的耦合；通过模拟服务和控制器的方法，确保测试独立性。 
 * - **改进建议**：可以增加更多边界条件的测试，例如异常情况处理、错误响应的验证等。 
**/
@WebMvcTest(SpaceWarningDictionaryController.class)
class SpaceWarningDictionaryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceWarningDictionaryService mockService;
    @SpyBean
    private SpaceWarningDictionaryController spaceWarningDictionaryController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceWarningDictionary());
        when(mockService.save(any(SpaceWarningDictionary.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceWarningDictionary.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceBaseTable.class);

        doReturn(true).when(spaceWarningDictionaryController).checkNameDuplication(any());
        doReturn(new BaseRsp<>(Boolean.TRUE)).when(spaceWarningDictionaryController).exportExcel(any(), any());

    }

    @Test
    void testExportExcel() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_warning_dictionary/excel")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":true,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_warning_dictionary/list")
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
        mockMvc.perform(post("/space/rule/space_warning_dictionary")
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
        mockMvc.perform(patch("/space/rule/space_warning_dictionary")
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
        mockMvc.perform(delete("/space/rule/space_warning_dictionary")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_warning_dictionary")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"toolType\":null,\"toolLevel\":null,\"level\":null,\"description\":null,\"updateTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

