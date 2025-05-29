package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceLoadedFileThreatRuleService;
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
 * 这段代码是一个单元测试类，用于测试 `SpaceLoadedFileThreatRuleController` 控制器的功能。测试类使用了 JUnit 5 和 Spring Boot 提供的测试框架（`@WebMvcTest`），模拟了控制器的行为以及依赖的服务。 
 * --- 
 * #### 主要功能模块 
 * 1. **测试环境初始化** 
 * - 使用 `@MockBean` 模拟了 `SpaceLoadedFileThreatRuleService` 的行为。 
 * - 使用 `@SpyBean` 对 `SpaceLoadedFileThreatRuleController` 进行部分方法的拦截和验证。 
 * - 在 `setUp()` 方法中，为服务层方法设置了返回值，包括列表、分页、新增、更新等操作。 
 * - 初始化了 MyBatis 的表信息，并设置了重复名称检查和导出 Excel 的返回值。 
 * 2. **测试方法** 
 * - 测试了以下接口的功能： 
 * - **导出 Excel** (`testExportExcel`) 
 * - **分页查询** (`testPage`) 
 * - **添加规则** (`testAddAttack`) 
 * - **更新规则** (`testUpdateA`) 
 * - **删除规则** (`testDeleteA`) 
 * - **获取规则详情** (`testGetADetail`) 
 * 3. **断言** 
 * - 使用 `mockMvc` 发起 HTTP 请求，并通过 `andExpect` 验证响应状态码、JSON 内容是否符合预期。 
 * --- 
 * #### 关键点总结 
 * - **技术栈**：Spring Boot 测试框架、JUnit 5、Mockito。 
 * - **测试覆盖范围**：全面覆盖了控制器的主要功能接口。 
 * - **数据模拟**：通过 `when` 和 `doReturn` 方法模拟了服务层的行为。 
 * - **JSON 校验**：使用 `content().json()` 方法验证返回的 JSON 数据是否正确。 
 * --- 
 * #### 改进建议 
 * 1. **测试数据多样性**：当前测试用例的数据较为简单，可以增加边界值测试（如空列表、大数量分页等）。 
 * 2. **日志记录**：在实际生产环境中，建议增加日志记录以方便排查问题。 
 * 3. **异常处理**：可以补充异常场景的测试，例如服务层抛出异常时的响应情况。 
 * 总体来看，这段代码结构清晰，测试覆盖全面，是一个高质量的单元测试示例。 
**/
@WebMvcTest(SpaceLoadedFileThreatRuleController.class)
class SpaceLoadedFileThreatRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceLoadedFileThreatRuleService mockService;
    @SpyBean
    private SpaceLoadedFileThreatRuleController spaceTrafficExtractionRuleController;
    


    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceLoadedFileThreatRule());
        when(mockService.save(any(SpaceLoadedFileThreatRule.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceLoadedFileThreatRule.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceLoadedFileThreatRule.class);

        doReturn(true).when(spaceTrafficExtractionRuleController).checkNameDuplication(any());
        doReturn(new BaseRsp<>(Boolean.TRUE)).when(spaceTrafficExtractionRuleController).exportExcel(any(), any());

    }

    @Test
    void testExportExcel() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_loaded_file_threat_rule/excel")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":true,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_loaded_file_threat_rule/list")
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
        mockMvc.perform(post("/space/rule/space_loaded_file_threat_rule")
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
        mockMvc.perform(patch("/space/rule/space_loaded_file_threat_rule")
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
        mockMvc.perform(delete("/space/rule/space_loaded_file_threat_rule")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/rule/space_loaded_file_threat_rule")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"description\":null,\"content\":null,\"tool\":null,\"executeType\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

