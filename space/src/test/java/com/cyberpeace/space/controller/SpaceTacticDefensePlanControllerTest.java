/**
 * 这段代码是一个针对 `SpaceTacticDefensePlanController` 的单元测试类，使用了 JUnit 5 和 Spring Boot 提供的测试工具。以下是代码的主要分析和总结： 
 * --- 
 * ### **代码功能概述** 
 * 1. **测试目标**： 
 * - 测试 `SpaceTacticDefensePlanController` 的多个接口（如获取详情、更新计划、分页查询、新增、修改、删除等）。 
 * - 验证接口返回的状态码、JSON 响应内容以及服务层逻辑是否正确。 
 * 2. **测试环境**： 
 * - 使用了 `@WebMvcTest` 注解，专注于测试 Web 层（即控制器层）。 
 * - 使用了 `@MockBean` 模拟依赖的服务层对象（`SpaceTacticDefensePlanService`），并用 `@SpyBean` 模拟控制器自身。 
 * - 使用了 `MockMvc` 来模拟 HTTP 请求并验证响应结果。 
 * 3. **测试方法**： 
 * - 每个测试方法都模拟了一个具体的 HTTP 请求（GET、POST、PATCH、DELETE 等），并通过断言验证返回的状态码、JSON 内容是否符合预期。 
 * --- 
 * ### **代码结构与关键点** 
 * #### **测试类定义** 
 * ```java 
 * @WebMvcTest(SpaceTacticDefensePlanController.class) 
 * class SpaceTacticDefensePlanControllerTest { ... } 
 * ``` 
 * - 使用 `@WebMvcTest` 注解，仅加载 `SpaceTacticDefensePlanController` 的相关配置，不启动整个应用上下文。 
 * - 自动注入 `MockMvc` 对象用于模拟 HTTP 请求。 
 * #### **依赖注入与 Mock** 
 * ```java 
 * @Autowired 
 * private MockMvc mockMvc; 
 * @MockBean 
 * private SpaceTacticDefensePlanService mockService; 
 * @SpyBean 
 * private SpaceTacticDefensePlanController spaceTacticDefensePlanController; 
 * ``` 
 * - `mockMvc`：用于发送 HTTP 请求并验证响应。 
 * - `mockService`：模拟 `SpaceTacticDefensePlanService` 的行为。 
 * - `spaceTacticDefensePlanController`：通过 `@SpyBean` 模拟控制器自身，方便调用私有方法或验证其行为。 
 * #### **初始化设置** 
 * ```java 
 * @BeforeEach 
 * void setUp() { ... } 
 * ``` 
 * - 在每个测试方法运行之前执行，模拟服务层的行为： 
 * - 返回空列表或特定对象。 
 * - 设置分页查询的结果。 
 * - 初始化表信息（`TableInfoHelper`）。 
 * - 模拟检查重复名称的方法。 
 * #### **测试方法** 
 * ##### **1. 获取详情 (`testGetADetail`)** 
 * - 模拟 GET 请求 `/space/tactic/defense_plan?resource_id=...`。 
 * - 验证返回状态码为 `200 OK`，并检查 JSON 响应内容是否符合预期。 
 * ##### **2. 更新计划 (`testUpdatePlan`)** 
 * - 模拟 POST 请求 `/space/tactic/defense_plan/renew`，传递 `DefensePlanLevelUpdateReq` 对象。 
 * - 验证返回状态码为 `200 OK`，并检查 JSON 响应内容。 
 * ##### **3. 分页查询 (`testPage`)** 
 * - 模拟 GET 请求 `/space/tactic/defense_plan/list`。 
 * - 验证返回状态码为 `200 OK`，并检查 JSON 响应内容是否为空列表。 
 * ##### **4. 新增 (`testAddAttack`)** 
 * - 模拟 POST 请求 `/space/tactic/defense_plan`，传递 `SpaceTacticDefensePlanBo` 对象。 
 * - 验证返回状态码为 `200 OK`，并检查 JSON 响应内容。 
 * ##### **5. 修改 (`testUpdateA`)** 
 * - 模拟 PATCH 请求 `/space/tactic/defense_plan`，传递 `SpaceTacticDefensePlanBo` 对象。 
 * - 验证返回状态码为 `200 OK`，并检查 JSON 响应内容。 
 * ##### **6. 删除 (`testDeleteA`)** 
 * - 模拟 DELETE 请求 `/space/tactic/defense_plan`，传递 `IdListReq` 对象。 
 * - 验证返回状态码为 `200 OK`，并检查 JSON 响应内容。 
 * --- 
 * ### **总结** 
 * - **优点**： 
 * - 测试覆盖了控制器的主要接口，包括 CRUD 操作。 
 * - 使用了 `MockMvc` 和 `MockBean`，确保测试独立性，避免依赖实际数据库或外部服务。 
 * - 每个测试方法清晰、独立，便于维护和扩展。 
 * - **改进建议**： 
 * - 可以增加更多边界条件的测试，例如参数校验失败的情况。 
 * - 如果服务层逻辑复杂，可以单独编写服务层的单元测试，减少冗余。 
 * 总体而言，这段代码是一个高质量的单元测试示例，展示了如何使用 Spring Boot 提供的测试工具来验证控制器的行为。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceTacticDefensePlanService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryBo;
import com.cyberpeace.space.service.context.tactic.DefensePlanLevelUpdateReq;
import com.cyberpeace.space.service.context.tactic.SpaceTacticDefensePlanBo;
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

@WebMvcTest(SpaceTacticDefensePlanController.class)
class SpaceTacticDefensePlanControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceTacticDefensePlanService mockService;
    @SpyBean
    private SpaceTacticDefensePlanController spaceTacticDefensePlanController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceTacticDefensePlan());
        when(mockService.save(any(SpaceTacticDefensePlan.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceTacticDefensePlan.class))).thenReturn(true);
        when(mockService.renewFrame(any())).thenReturn(new SpaceFrameLevel());
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceBaseTable.class);

        doReturn(true).when(spaceTacticDefensePlanController).checkNameDuplication(any());

    }
    

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense_plan")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"description\":null,\"level\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdatePlan() throws Exception {
        // Setup
        // Run the test and verify the results
        DefensePlanLevelUpdateReq req = new DefensePlanLevelUpdateReq();
        req.setResourceId("resource_id");

        mockMvc.perform(post("/space/tactic/defense_plan/renew")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"children\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense_plan/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceTacticDefensePlanBo spaceWarningDictionaryBo = new SpaceTacticDefensePlanBo();
        spaceWarningDictionaryBo.setResourceId("resource_id");
        spaceWarningDictionaryBo.setLevel(new SpaceFrameLevel("11111111111", "frame"));
        mockMvc.perform(post("/space/tactic/defense_plan")
                        .content(JsonUtil.toJsonString(spaceWarningDictionaryBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceTacticDefensePlanBo spaceWarningDictionaryBo = new SpaceTacticDefensePlanBo();
        spaceWarningDictionaryBo.setResourceId("resource_id");
        spaceWarningDictionaryBo.setLevel(new SpaceFrameLevel("11111111111", "frame"));
        mockMvc.perform(patch("/space/tactic/defense_plan")
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
        mockMvc.perform(delete("/space/tactic/defense_plan")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
