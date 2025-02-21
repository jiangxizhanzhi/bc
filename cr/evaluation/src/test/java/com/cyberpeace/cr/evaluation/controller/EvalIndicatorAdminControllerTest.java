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
