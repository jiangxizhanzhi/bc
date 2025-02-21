package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalSystemMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexService;
import com.cyberpeace.cr.evaluation.service.EvalSystemService;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EvalSystemIndexAdminController.class)
class EvalSystemIndexAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvalIndexService mockEvalIndexService;
    @MockBean
    private EvalSystemMapper mockEvalSystemMapper;
    @MockBean
    private EvalSystemService mockEvalSystemService;
    @MockBean
    private TransactionTemplate mockTransactionTemplate;

    @Test
    void testAddEvalIndex() throws Exception {
        // Setup
        when(mockEvalSystemMapper.existByName("name", "evalSystemId")).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(post("/cr/eval/system")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));

        // Confirm EvalIndexService.saveBatch(...).
        final EvalIndex evalIndex = new EvalIndex();
        evalIndex.setResourceId("evalIndicatorId");
        evalIndex.setLevel(0);
        evalIndex.setEvalIndicatorId("evalIndicatorId");
        evalIndex.setAggregatingAlgorithmId("aggregatingAlgorithmId");
        evalIndex.setAggregatingAlgorithmParams("aggregatingAlgorithmParams");
        evalIndex.setChildrenIds("childrenIds");
        final List<EvalIndex> entityList = Arrays.asList(evalIndex);
        verify(mockEvalIndexService).saveBatch(entityList);

        // Confirm EvalSystemService.save(...).
        final EvalSystem entity = new EvalSystem();
        entity.setResourceId("evalSystemId");
        entity.setConnlist("connlist");
        entity.setFirstIndexNum(0);
        entity.setSecondIndexNum(0);
        entity.setThirdIndexNum(0);
        entity.setCreateUserId("createUserId");
        verify(mockEvalSystemService).save(entity);
        verify(mockTransactionTemplate).execute(any(TransactionCallback.class));
    }

    @Test
    void testAddEvalIndex_EvalSystemMapperReturnsTrue() throws Exception {
        // Setup
        when(mockEvalSystemMapper.existByName("name", "evalSystemId")).thenReturn(true);

        // Run the test and verify the results
        mockMvc.perform(post("/cr/eval/system")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testAddEvalIndex_TransactionTemplateThrowsTransactionException() throws Exception {
        // Setup
        when(mockEvalSystemMapper.existByName("name", "evalSystemId")).thenReturn(false);
        when(mockTransactionTemplate.execute(any(TransactionCallback.class))).thenThrow(TransactionException.class);

        // Run the test and verify the results
        mockMvc.perform(post("/cr/eval/system")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{}", true));

        // Confirm EvalIndexService.saveBatch(...).
        final EvalIndex evalIndex = new EvalIndex();
        evalIndex.setResourceId("evalIndicatorId");
        evalIndex.setLevel(0);
        evalIndex.setEvalIndicatorId("evalIndicatorId");
        evalIndex.setAggregatingAlgorithmId("aggregatingAlgorithmId");
        evalIndex.setAggregatingAlgorithmParams("aggregatingAlgorithmParams");
        evalIndex.setChildrenIds("childrenIds");
        final List<EvalIndex> entityList = Arrays.asList(evalIndex);
        verify(mockEvalIndexService).saveBatch(entityList);

        // Confirm EvalSystemService.save(...).
        final EvalSystem entity = new EvalSystem();
        entity.setResourceId("evalSystemId");
        entity.setConnlist("connlist");
        entity.setFirstIndexNum(0);
        entity.setSecondIndexNum(0);
        entity.setThirdIndexNum(0);
        entity.setCreateUserId("createUserId");
        verify(mockEvalSystemService).save(entity);
    }

    @Test
    void testEditEvalIndex() throws Exception {
        // Setup
        // Configure EvalSystemService.getById(...).
        final EvalSystem evalSystem = new EvalSystem();
        evalSystem.setResourceId("evalSystemId");
        evalSystem.setConnlist("connlist");
        evalSystem.setFirstIndexNum(0);
        evalSystem.setSecondIndexNum(0);
        evalSystem.setThirdIndexNum(0);
        evalSystem.setCreateUserId("createUserId");
        when(mockEvalSystemService.getById("evalSystemId")).thenReturn(evalSystem);

        when(mockEvalSystemMapper.existByName("name", "evalSystemId")).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(put("/cr/eval/system")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockEvalIndexService).deleteBatchByEvalSystemId("evalSystemId");

        // Confirm EvalIndexService.saveBatch(...).
        final EvalIndex evalIndex = new EvalIndex();
        evalIndex.setResourceId("evalIndicatorId");
        evalIndex.setLevel(0);
        evalIndex.setEvalIndicatorId("evalIndicatorId");
        evalIndex.setAggregatingAlgorithmId("aggregatingAlgorithmId");
        evalIndex.setAggregatingAlgorithmParams("aggregatingAlgorithmParams");
        evalIndex.setChildrenIds("childrenIds");
        final List<EvalIndex> entityList = Arrays.asList(evalIndex);
        verify(mockEvalIndexService).saveBatch(entityList);

        // Confirm EvalSystemService.updateById(...).
        final EvalSystem entity = new EvalSystem();
        entity.setResourceId("evalSystemId");
        entity.setConnlist("connlist");
        entity.setFirstIndexNum(0);
        entity.setSecondIndexNum(0);
        entity.setThirdIndexNum(0);
        entity.setCreateUserId("createUserId");
        verify(mockEvalSystemService).updateById(entity);
        verify(mockTransactionTemplate).execute(any(TransactionCallback.class));
    }

    @Test
    void testEditEvalIndex_EvalSystemServiceGetByIdReturnsNull() throws Exception {
        // Setup
        when(mockEvalSystemService.getById("evalSystemId")).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(put("/cr/eval/system")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testEditEvalIndex_EvalSystemMapperReturnsTrue() throws Exception {
        // Setup
        // Configure EvalSystemService.getById(...).
        final EvalSystem evalSystem = new EvalSystem();
        evalSystem.setResourceId("evalSystemId");
        evalSystem.setConnlist("connlist");
        evalSystem.setFirstIndexNum(0);
        evalSystem.setSecondIndexNum(0);
        evalSystem.setThirdIndexNum(0);
        evalSystem.setCreateUserId("createUserId");
        when(mockEvalSystemService.getById("evalSystemId")).thenReturn(evalSystem);

        when(mockEvalSystemMapper.existByName("name", "evalSystemId")).thenReturn(true);

        // Run the test and verify the results
        mockMvc.perform(put("/cr/eval/system")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{}", true));
        verify(mockEvalIndexService).deleteBatchByEvalSystemId("evalSystemId");
    }

    @Test
    void testEditEvalIndex_TransactionTemplateThrowsTransactionException() throws Exception {
        // Setup
        // Configure EvalSystemService.getById(...).
        final EvalSystem evalSystem = new EvalSystem();
        evalSystem.setResourceId("evalSystemId");
        evalSystem.setConnlist("connlist");
        evalSystem.setFirstIndexNum(0);
        evalSystem.setSecondIndexNum(0);
        evalSystem.setThirdIndexNum(0);
        evalSystem.setCreateUserId("createUserId");
        when(mockEvalSystemService.getById("evalSystemId")).thenReturn(evalSystem);

        when(mockEvalSystemMapper.existByName("name", "evalSystemId")).thenReturn(false);
        when(mockTransactionTemplate.execute(any(TransactionCallback.class))).thenThrow(TransactionException.class);

        // Run the test and verify the results
        mockMvc.perform(put("/cr/eval/system")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{}", true));
        verify(mockEvalIndexService).deleteBatchByEvalSystemId("evalSystemId");

        // Confirm EvalIndexService.saveBatch(...).
        final EvalIndex evalIndex = new EvalIndex();
        evalIndex.setResourceId("evalIndicatorId");
        evalIndex.setLevel(0);
        evalIndex.setEvalIndicatorId("evalIndicatorId");
        evalIndex.setAggregatingAlgorithmId("aggregatingAlgorithmId");
        evalIndex.setAggregatingAlgorithmParams("aggregatingAlgorithmParams");
        evalIndex.setChildrenIds("childrenIds");
        final List<EvalIndex> entityList = Arrays.asList(evalIndex);
        verify(mockEvalIndexService).saveBatch(entityList);

        // Confirm EvalSystemService.updateById(...).
        final EvalSystem entity = new EvalSystem();
        entity.setResourceId("evalSystemId");
        entity.setConnlist("connlist");
        entity.setFirstIndexNum(0);
        entity.setSecondIndexNum(0);
        entity.setThirdIndexNum(0);
        entity.setCreateUserId("createUserId");
        verify(mockEvalSystemService).updateById(entity);
    }

    @Test
    void testGetEvalIndex() throws Exception {
        // Setup
        // Configure EvalSystemService.getById(...).
        final EvalSystem evalSystem = new EvalSystem();
        evalSystem.setResourceId("evalSystemId");
        evalSystem.setConnlist("connlist");
        evalSystem.setFirstIndexNum(0);
        evalSystem.setSecondIndexNum(0);
        evalSystem.setThirdIndexNum(0);
        evalSystem.setCreateUserId("createUserId");
        when(mockEvalSystemService.getById("resource_id")).thenReturn(evalSystem);

        // Configure EvalIndexService.getIndexesByEvalSystemId(...).
        final IndexContext indexContext = new IndexContext();
        indexContext.setIndexId("evalIndicatorId");
        indexContext.setAlgorithmId("aggregatingAlgorithmId");
        indexContext.setEvalIndicatorId("evalIndicatorId");
        indexContext.setParams("aggregatingAlgorithmParams");
        indexContext.setChildrenIds(Arrays.asList("value"));
        final List<IndexContext> indexContexts = Arrays.asList(indexContext);
        when(mockEvalIndexService.getIndexesByEvalSystemId("evalSystemId", 0)).thenReturn(indexContexts);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/system")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetEvalIndex_EvalSystemServiceReturnsNull() throws Exception {
        // Setup
        when(mockEvalSystemService.getById("resource_id")).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/system")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetEvalIndex_EvalIndexServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure EvalSystemService.getById(...).
        final EvalSystem evalSystem = new EvalSystem();
        evalSystem.setResourceId("evalSystemId");
        evalSystem.setConnlist("connlist");
        evalSystem.setFirstIndexNum(0);
        evalSystem.setSecondIndexNum(0);
        evalSystem.setThirdIndexNum(0);
        evalSystem.setCreateUserId("createUserId");
        when(mockEvalSystemService.getById("resource_id")).thenReturn(evalSystem);

        when(mockEvalIndexService.getIndexesByEvalSystemId("evalSystemId", 0)).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/system")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testBatchDelete() throws Exception {
        // Setup
        when(mockEvalSystemService.batchDelete(Arrays.asList("value"))).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(delete("/cr/eval/system")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testList() throws Exception {
        // Setup
        // Configure EvalSystemService.page(...).
        final EvalListQuery listQuery = new EvalListQuery();
        listQuery.setPattern("pattern");
        listQuery.setPer_page(0);
        listQuery.setSort("-create_time");
        listQuery.setName("pattern");
        listQuery.setType(0);
        when(mockEvalSystemService.page(listQuery)).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/system/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
