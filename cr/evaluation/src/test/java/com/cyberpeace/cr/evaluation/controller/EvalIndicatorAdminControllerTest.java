package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndicatorReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalIndicatorRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndicatorMapper;
import com.cyberpeace.cr.evaluation.service.EvalAggregationService;
import com.cyberpeace.cr.evaluation.service.EvalCollectionService;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.service.RemotePythonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 这段代码是一个用于测试 `EvalIndicatorAdminController` 控制器的JUnit测试类。以下是对其主要部分的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller` 
 * - **类名**：`EvalIndicatorAdminControllerTest` 
 * - **注解**： 
 * - `@WebMvcTest(EvalIndicatorAdminController.class)`：指定这是一个Web MVC测试，并且只加载 `EvalIndicatorAdminController` 类。 
 * ### 成员变量 
 * - **Mock对象**：使用 `@MockBean` 和 `@SpyBean` 注解创建了多个模拟对象（mocks），包括服务层、数据访问层以及远程服务等。 
 * - **MockMvc对象**：通过 `@Autowired` 注入，用于模拟HTTP请求。 
 * ### 测试方法 
 * 每个测试方法都遵循相同的模式：设置模拟对象的行为，执行一个HTTP请求，然后验证响应的状态码和内容。 
 * #### `testAddEvalIndicator` 
 * - 模拟 `mockEvalIndicatorMapper.existByName` 方法返回 `false`。 
 * - 发送POST请求到 `/cr/eval/indicator`，期望响应状态码为4xx（客户端错误）。 
 * #### `testCheckMatrixDataConsistency` 
 * - 模拟 `mockRemotePythonService.formatPerform` 方法返回一个列表。 
 * - 发送POST请求到 `/cr/eval/indicator/check_consistency`，期望响应状态码为4xx。 
 * #### `testEditEvalIndicator` 
 * - 模拟 `mockEvalIndicatorMapper.existByName` 方法返回 `false`。 
 * - 配置 `mockEvalIndicatorService.getById` 返回一个 `EvalIndicator` 对象。 
 * - 发送PUT请求到 `/cr/eval/indicator`，期望响应状态码为4xx。 
 * #### `testGetEvalIndex` 
 * - 配置多个服务的方法返回预期的数据。 
 * - 发送GET请求到 `/cr/eval/indicator`，期望响应状态码为200，并且响应内容符合预期的JSON格式。 
 * #### `testList` 
 * - 配置 `mockEvalIndicatorService.page` 返回一个空的分页对象。 
 * - 发送GET请求到 `/cr/eval/indicator/list`，期望响应状态码为200，并且响应内容符合预期的JSON格式。 
 * #### `testBatchDelete` 
 * - 模拟 `mockEvalIndicatorService.selectIndexUsedNum` 和 `mockEvalIndicatorService.batchDelete` 方法的返回值。 
 * - 发送DELETE请求到 `/cr/eval/indicator`，期望响应状态码为4xx。 
 * ### 总结 
 * 这段代码主要是为了确保 `EvalIndicatorAdminController` 控制器中的各个操作能够正确处理HTTP请求，并返回正确的响应状态码和内容。通过模拟服务层和数据访问层的行为，测试方法可以独立地验证控制器的行为是否符合预期。 
**/
@WebMvcTest(EvalIndicatorAdminController.class)
class EvalIndicatorAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvalIndicatorService mockEvalIndicatorService;
    @MockBean
    private EvalIndicatorMapper mockEvalIndicatorMapper;
    @MockBean
    private RemotePythonService mockRemotePythonService;
    @MockBean
    private EvalAlgorithmMapper mockEvalAlgorithmMapper;
    @MockBean
    private EvalCollectionService mockEvalCollectionService;
    @MockBean
    private EvalAggregationService mockEvalAggregationService;
    @MockBean
    private TransactionTemplate mockTransactionTemplate;
    @SpyBean
    private EvalIndicatorAdminController evalIndicatorAdminController;


    @Test
    void testAddEvalIndicator() throws Exception {
        // Setup
        when(mockEvalIndicatorMapper.existByName("name", "evalIndicatorId")).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(post("/cr/eval/indicator")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void testCheckMatrixDataConsistency() throws Exception {
        // Setup
        when(mockRemotePythonService.formatPerform(eq(CrCommonConstants.AHP_CONSISTENCY_ID), eq("algorithmCode"),
                any(FormatData.class), eq(Arrays.asList("value")))).thenReturn(Arrays.asList("value"));

        // Run the test and verify the results
        mockMvc.perform(post("/cr/eval/indicator/check_consistency")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }



    @Test
    void testEditEvalIndicator() throws Exception {
        // Setup
        when(mockEvalIndicatorMapper.existByName("name", "evalIndicatorId")).thenReturn(false);

        // Configure EvalIndicatorService.getById(...).
        final EvalIndicator evalIndicator = new EvalIndicator();
        evalIndicator.setResourceId("evalIndicatorId");
        evalIndicator.setName("name");
        evalIndicator.setAttribute(0);
        evalIndicator.setAggregationIds("aggregationIds");
        evalIndicator.setCollectionIds("collectionIds");
        when(mockEvalIndicatorService.getById("evalIndicatorId")).thenReturn(evalIndicator);

        // Run the test and verify the results
        mockMvc.perform(put("/cr/eval/indicator")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }



    @Test
    void testGetEvalIndex() throws Exception {
        // Setup
        // Configure EvalIndicatorService.getById(...).
        final EvalIndicator evalIndicator = new EvalIndicator();
        evalIndicator.setResourceId("evalIndicatorId");
        evalIndicator.setName("name");
        evalIndicator.setAttribute(0);
        evalIndicator.setAggregationIds("aggregationIds");
        evalIndicator.setCollectionIds("collectionIds");
        when(mockEvalIndicatorService.getById("resource_id")).thenReturn(evalIndicator);

        // Configure EvalCollectionService.getContextByEvalIndicatorId(...).
        final EvalCollectionContext evalCollectionContext = new EvalCollectionContext();
        evalCollectionContext.setEvalCollectionId("evalCollectionId");
        evalCollectionContext.setEvalIndicatorId("evalIndicatorId");
        evalCollectionContext.setFormatAlgorithmId("formatAlgorithmId");
        evalCollectionContext.setFormatAlgorithmParams("formatAlgorithmParams");
        evalCollectionContext.setDetectionAlgorithmId("detectionAlgorithmId");
        final List<EvalCollectionContext> evalCollectionContexts = Arrays.asList(evalCollectionContext);
        when(mockEvalCollectionService.getContextByEvalIndicatorId("evalIndicatorId"))
                .thenReturn(evalCollectionContexts);

        // Configure EvalAggregationService.getContextByEvalIndicatorId(...).
        final EvalAggregationContext evalAggregationContext = new EvalAggregationContext();
        evalAggregationContext.setEvalAggregationId("evalAggregationId");
        evalAggregationContext.setName("name");
        evalAggregationContext.setEvalIndicatorId("[]");
        evalAggregationContext.setAggregatingAlgorithmId("aggregatingAlgorithmId");
        evalAggregationContext.setAggregatingAlgorithmParams("aggregatingAlgorithmParams");
        final List<EvalAggregationContext> evalAggregationContexts = Arrays.asList(evalAggregationContext);
        when(mockEvalAggregationService.getContextByEvalIndicatorId("evalIndicatorId"))
                .thenReturn(evalAggregationContexts);

        // Run the test and verify the results
        doReturn(new EvalIndicatorRsp()).when(evalIndicatorAdminController).convertEvalIndicatorToEvalSystemRsp(any());
        mockMvc.perform(get("/cr/eval/indicator")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"evalIndicatorId\":null,\"name\":null,\"description\":null,\"attribute\":null,\"attributeValue\":null,\"attributeVal\":null,\"datatype\":null,\"connlist\":null,\"evalCollections\":null,\"evalAggregations\":null,\"evalIndicators\":null,\"evalAggregationNum\":null,\"evalCollectionNum\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }




    @Test
    void testList() throws Exception {
        // Setup
        // Configure EvalIndicatorService.page(...).
        final EvalListQuery listQuery = new EvalListQuery();
        listQuery.setPattern("pattern");
        listQuery.setPer_page(0);
        listQuery.setSort("-create_time");
        listQuery.setName("pattern");
        listQuery.setType(0);
        when(mockEvalIndicatorService.page(any(EvalListQuery.class))).thenReturn(new Page<>());

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/indicator/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testBatchDelete() throws Exception {
        // Setup
        when(mockEvalIndicatorService.selectIndexUsedNum(Arrays.asList("value"))).thenReturn(0);
        when(mockEvalIndicatorService.batchDelete(Arrays.asList("value"))).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(delete("/cr/eval/indicator")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}

