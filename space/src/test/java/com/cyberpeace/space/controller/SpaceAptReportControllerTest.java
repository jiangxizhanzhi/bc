/**
 * 这段代码是一个针对 `SpaceAptReportController` 的单元测试类，使用了 JUnit 5 和 Spring Boot 提供的测试工具。以下是对代码的简要分析： 
 * 1. **测试类结构**： 
 * - 类名为 `SpaceAptReportControllerTest`，继承自 `WebMvcTest(SpaceAptReportController.class)`，表明这是一个针对 `SpaceAptReportController` 的 Web 层测试。 
 * - 使用了 `@MockBean` 注解模拟了 `SpaceAptReportService`，并用 `@SpyBean` 对 `SpaceAptReportController` 进行部分模拟。 
 * 2. **依赖注入与初始化**： 
 * - `MockMvc` 用于模拟 HTTP 请求和响应。 
 * - 在 `setUp` 方法中，通过 `when` 和 `doReturn` 配置了 `mockService` 的行为，确保服务层返回预期结果。 
 * - 初始化了分页对象 `Page` 并设置了记录为空列表。 
 * - 调用了 `TableInfoHelper.initTableInfo` 方法来初始化表信息。 
 * 3. **测试方法**： 
 * - `testPage`：测试分页接口 `/space/theory/space_apt_report/list`，验证返回 JSON 数据是否符合预期。 
 * - `testAddAttack`：测试新增接口 `/space/theory/space_apt_report`，验证 POST 请求的处理。 
 * - `testUpdateA`：测试更新接口 `/space/theory/space_apt_report`，验证 PATCH 请求的处理。 
 * - `testDeleteA`：测试删除接口 `/space/theory/space_apt_report`，验证 DELETE 请求的处理。 
 * - `testGetADetail`：测试获取详情接口 `/space/theory/space_apt_report`，验证 GET 请求的处理。 
 * 4. **断言与验证**： 
 * - 使用 `andExpect` 方法对 HTTP 状态码、返回的 JSON 内容进行断言，确保接口行为符合预期。 
 * 总结：该测试类全面覆盖了 `SpaceAptReportController` 的主要接口，通过模拟服务层和请求参数，验证了控制器的逻辑正确性和接口响应的准确性。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.SpaceAptReportService;
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

@WebMvcTest(SpaceAptReportController.class)
class SpaceAptReportControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceAptReportService mockService;
    @SpyBean
    private SpaceAptReportController spaceTrafficExtractionRuleController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceAptReport());
        when(mockService.save(any(SpaceAptReport.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceAptReport.class))).thenReturn(true);
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
        mockMvc.perform(get("/space/theory/space_apt_report/list")
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
        mockMvc.perform(post("/space/theory/space_apt_report")
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
        mockMvc.perform(patch("/space/theory/space_apt_report")
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
        mockMvc.perform(delete("/space/theory/space_apt_report")
                        .content(JsonUtil.toJsonString(req)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/theory/space_apt_report")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"aptOrg\":null,\"publicTime\":null,\"publicCompany\":null,\"type\":null,\"reportPath\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
