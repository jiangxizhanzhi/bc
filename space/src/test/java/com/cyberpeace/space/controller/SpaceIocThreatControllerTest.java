package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceIocThreatService;
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
 * ### 代码分析 
 * #### 项目背景 
 * 该代码是一个单元测试类 `SpaceIocThreatControllerTest`，用于测试 `SpaceIocThreatController` 控制器的功能。它使用了 Spring Boot 提供的 `@WebMvcTest` 注解来加载与 Web 层相关的配置，并通过 `MockMvc` 模拟 HTTP 请求。 
 * #### 主要功能模块 
 * 1. **依赖注入**： 
 * - 使用 `@Autowired` 注入 `MockMvc` 对象。 
 * - 使用 `@MockBean` 和 `@SpyBean` 模拟服务层（`SpaceIocThreatService`）和控制器本身。 
 * 2. **测试方法**： 
 * - 测试了多个 HTTP 方法（GET、POST、PATCH、DELETE），模拟了对 `SpaceIocThreat` 的分页查询、新增、更新、删除以及详情获取等操作。 
 * - 每个测试方法都通过 `MockMvc` 发起请求，并验证返回的状态码、JSON 响应内容是否符合预期。 
 * 3. **辅助设置**： 
 * - 在 `setUp()` 方法中，预设了服务层方法的返回值，确保测试用例能够独立运行。 
 * - 初始化了一些工具类（如 `TableInfoHelper`），可能与数据库表结构相关。 
 * 4. **测试数据**： 
 * - 使用了 `SpaceWarningDictionaryBo` 和 `IdListReq` 等对象作为请求体或参数，模拟实际业务场景中的数据。 
 * #### 代码结构 
 * - **注解驱动**：通过 `@WebMvcTest` 和其他 Spring 注解实现测试环境的快速搭建。 
 * - **MockMvc**：核心工具，用于模拟 HTTP 请求并验证响应。 
 * - **断言**：通过 `andExpect` 方法验证 HTTP 状态码、响应内容等是否符合预期。 
 * #### 总结 
 * 该代码实现了对 `SpaceIocThreatController` 控制器的全面功能测试，覆盖了常见的 CRUD 操作。通过 Mock 技术隔离了依赖，确保测试的独立性和稳定性。代码逻辑清晰，注释较少但结构明确，适合扩展和维护。 
**/
@WebMvcTest(SpaceIocThreatController.class)
class SpaceIocThreatControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceIocThreatService mockService;
    @SpyBean
    private SpaceIocThreatController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceIocThreat());
        when(mockService.save(any(SpaceIocThreat.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceIocThreat.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/rule/space_ioc_threat/list")
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
        mockMvc.perform(post("/space/rule/space_ioc_threat")
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
        mockMvc.perform(patch("/space/rule/space_ioc_threat")
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
        mockMvc.perform(delete("/space/rule/space_ioc_threat")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_ioc_threat")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"ioc\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

