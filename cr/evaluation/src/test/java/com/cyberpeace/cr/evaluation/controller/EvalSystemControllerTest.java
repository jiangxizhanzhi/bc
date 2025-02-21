package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexService;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.EvalSystemService;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EvalSystemController.class)
class EvalSystemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvalIndexService mockEvalIndexService;
    @MockBean
    private EvalCollectionMapper mockEvalCollectionMapper;
    @MockBean
    private EvalIndicatorService mockEvalIndicatorService;
    @MockBean
    private EvalSystemService mockEvalSystemService;

    @Test
    void testGetEvalIndexTree() throws Exception {
        // Setup
        // Configure EvalSystemService.getById(...).
        final EvalSystem evalSystem = new EvalSystem();
        evalSystem.setResourceId("evalSystemId");
        evalSystem.setName("name");
        evalSystem.setDescription("description");
        evalSystem.setConnlist("connlist");
        evalSystem.setFirstIndexNum(0);
        when(mockEvalSystemService.getById("resource_id")).thenReturn(evalSystem);

        // Configure EvalIndexService.getIndexesByEvalSystemId(...).
        final IndexContext indexContext = new IndexContext();
        indexContext.setIndexId("indexId");
        indexContext.setName("name");
        indexContext.setDescription("description");
        indexContext.setEvalIndicatorId("evalIndicatorId");
        indexContext.setChildrenIds(Arrays.asList("value"));
        final List<IndexContext> indexContexts = Arrays.asList(indexContext);
        when(mockEvalIndexService.getIndexesByEvalSystemId("evalSystemId", 0)).thenReturn(indexContexts);

        // Configure EvalIndicatorService.getById(...).
        final EvalIndicator evalIndicator = new EvalIndicator();
        evalIndicator.setResourceId("resourceId");
        evalIndicator.setName("name");
        evalIndicator.setDescription("description");
        evalIndicator.setDatatype(0);
        evalIndicator.setAttribute(0);
        when(mockEvalIndicatorService.getById("evalIndicatorId")).thenReturn(evalIndicator);

        // Configure EvalCollectionMapper.selectBythirdIndexId(...).
        final EvalCollection evalCollection = new EvalCollection();
        evalCollection.setResourceId("indexId");
        evalCollection.setName("name");
        evalCollection.setEvalIndicatorId("evalIndicatorId");
        evalCollection.setFormatAlgorithmId("formatAlgorithmId");
        evalCollection.setFormatAlgorithmParams("formatAlgorithmParams");
        final List<EvalCollection> evalCollections = Arrays.asList(evalCollection);
        when(mockEvalCollectionMapper.selectBythirdIndexId("indexId")).thenReturn(evalCollections);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/system_tree")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetEvalIndexTree_EvalSystemServiceReturnsNull() throws Exception {
        // Setup
        when(mockEvalSystemService.getById("resource_id")).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/system_tree")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetEvalIndexTree_EvalIndexServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure EvalSystemService.getById(...).
        final EvalSystem evalSystem = new EvalSystem();
        evalSystem.setResourceId("evalSystemId");
        evalSystem.setName("name");
        evalSystem.setDescription("description");
        evalSystem.setConnlist("connlist");
        evalSystem.setFirstIndexNum(0);
        when(mockEvalSystemService.getById("resource_id")).thenReturn(evalSystem);

        when(mockEvalIndexService.getIndexesByEvalSystemId("evalSystemId", 0)).thenReturn(Collections.emptyList());

        // Configure EvalIndicatorService.getById(...).
        final EvalIndicator evalIndicator = new EvalIndicator();
        evalIndicator.setResourceId("resourceId");
        evalIndicator.setName("name");
        evalIndicator.setDescription("description");
        evalIndicator.setDatatype(0);
        evalIndicator.setAttribute(0);
        when(mockEvalIndicatorService.getById("evalIndicatorId")).thenReturn(evalIndicator);

        // Configure EvalCollectionMapper.selectBythirdIndexId(...).
        final EvalCollection evalCollection = new EvalCollection();
        evalCollection.setResourceId("indexId");
        evalCollection.setName("name");
        evalCollection.setEvalIndicatorId("evalIndicatorId");
        evalCollection.setFormatAlgorithmId("formatAlgorithmId");
        evalCollection.setFormatAlgorithmParams("formatAlgorithmParams");
        final List<EvalCollection> evalCollections = Arrays.asList(evalCollection);
        when(mockEvalCollectionMapper.selectBythirdIndexId("indexId")).thenReturn(evalCollections);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/system_tree")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetEvalIndexTree_EvalCollectionMapperReturnsNoItems() throws Exception {
        // Setup
        // Configure EvalSystemService.getById(...).
        final EvalSystem evalSystem = new EvalSystem();
        evalSystem.setResourceId("evalSystemId");
        evalSystem.setName("name");
        evalSystem.setDescription("description");
        evalSystem.setConnlist("connlist");
        evalSystem.setFirstIndexNum(0);
        when(mockEvalSystemService.getById("resource_id")).thenReturn(evalSystem);

        // Configure EvalIndexService.getIndexesByEvalSystemId(...).
        final IndexContext indexContext = new IndexContext();
        indexContext.setIndexId("indexId");
        indexContext.setName("name");
        indexContext.setDescription("description");
        indexContext.setEvalIndicatorId("evalIndicatorId");
        indexContext.setChildrenIds(Arrays.asList("value"));
        final List<IndexContext> indexContexts = Arrays.asList(indexContext);
        when(mockEvalIndexService.getIndexesByEvalSystemId("evalSystemId", 0)).thenReturn(indexContexts);

        // Configure EvalIndicatorService.getById(...).
        final EvalIndicator evalIndicator = new EvalIndicator();
        evalIndicator.setResourceId("resourceId");
        evalIndicator.setName("name");
        evalIndicator.setDescription("description");
        evalIndicator.setDatatype(0);
        evalIndicator.setAttribute(0);
        when(mockEvalIndicatorService.getById("evalIndicatorId")).thenReturn(evalIndicator);

        when(mockEvalCollectionMapper.selectBythirdIndexId("indexId")).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/system_tree")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}
