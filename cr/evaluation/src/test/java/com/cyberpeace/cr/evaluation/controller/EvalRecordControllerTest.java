package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EvalRecordController.class)
class EvalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvalTaskService mockEvalTaskService;
    @MockBean
    private EvalRecordService mockEvalRecordService;
    @MockBean
    private EvalIndexRefService mockEvalIndexRefService;
    @MockBean
    private EvalVariableRefService mockEvalVariableRefService;

    @Test
    void testList() throws Exception {
        // Setup
        // Configure EvalRecordService.pageList(...).
        final EvalRecordListRsp evalRecordListRsp = new EvalRecordListRsp();
        evalRecordListRsp.setResourceId("resourceId");
        evalRecordListRsp.setName("name");
        evalRecordListRsp.setDescription("description");
        evalRecordListRsp.setStatus(0);
        evalRecordListRsp.setScore(0);
        final ListRsp<EvalRecordListRsp> evalRecordListRspListRsp = new ListRsp<>(Arrays.asList(evalRecordListRsp), 0L,
                0L, 0L);
        when(mockEvalRecordService.pageList(any(Page.class), eq("taskId"), eq(0), eq("pattern")))
                .thenReturn(evalRecordListRspListRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testStatusEnum() throws Exception {
        // Setup
        // Configure EvalRecordService.recordStatusEnum(...).
        final EvalRoomIntPairRsp evalRoomIntPairRsp = new EvalRoomIntPairRsp();
        evalRoomIntPairRsp.setId(0);
        evalRoomIntPairRsp.setLabel("label");
        final List<EvalRoomIntPairRsp> evalRoomIntPairRsps = Arrays.asList(evalRoomIntPairRsp);
        when(mockEvalRecordService.recordStatusEnum()).thenReturn(evalRoomIntPairRsps);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/status")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"id\":0,\"label\":\"label\"}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }


    @Test
    void testCreate() throws Exception {
        // Setup
        // Configure EvalRecordService.create(...).
        final EvalRecordRsp evalRecordRsp = new EvalRecordRsp();
        evalRecordRsp.setResourceId("resourceId");
        evalRecordRsp.setName("name");
        evalRecordRsp.setDescription("description");
        evalRecordRsp.setRoomId("roomId");
        evalRecordRsp.setSystemId("systemId");
        when(mockEvalRecordService.create(any(EvalRecordAddReq.class))).thenReturn(evalRecordRsp);

        // Run the test and verify the results
        EvalRecordAddReq evalRecordAddReq = new EvalRecordAddReq();
        evalRecordAddReq.setResourceId("1111");
        mockMvc.perform(post("/cr/eval/record")
                        .content(JsonUtil.toJsonString(evalRecordAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"name\":\"name\",\"description\":\"description\",\"roomId\":\"roomId\",\"systemId\":\"systemId\"},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdate() throws Exception {
        // Setup
        // Configure EvalRecordService.update(...).
        final EvalRecordRsp evalRecordRsp = new EvalRecordRsp();
        evalRecordRsp.setResourceId("resourceId");
        evalRecordRsp.setName("name");
        evalRecordRsp.setDescription("description");
        evalRecordRsp.setRoomId("roomId");
        evalRecordRsp.setSystemId("systemId");
        when(mockEvalRecordService.update(any(EvalRecordAddReq.class))).thenReturn(evalRecordRsp);

        // Run the test and verify the results
        EvalRecordAddReq evalRecordAddReq = new EvalRecordAddReq();
        evalRecordAddReq.setResourceId("1111");
        mockMvc.perform(patch("/cr/eval/record")
                        .content(JsonUtil.toJsonString(evalRecordAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"name\":\"name\",\"description\":\"description\",\"roomId\":\"roomId\",\"systemId\":\"systemId\"},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockEvalRecordService).isExist(any(EvalRecordAddReq.class));
    }

    @Test
    void testGet() throws Exception {
        // Setup
        // Configure EvalRecordService.get(...).
        final EvalRecordRsp evalRecordRsp = new EvalRecordRsp();
        evalRecordRsp.setResourceId("resourceId");
        evalRecordRsp.setName("name");
        evalRecordRsp.setDescription("description");
        evalRecordRsp.setRoomId("roomId");
        evalRecordRsp.setSystemId("systemId");
        when(mockEvalRecordService.get("recordId")).thenReturn(evalRecordRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record")
                        .param("resource_id", "recordId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"name\":\"name\",\"description\":\"description\",\"roomId\":\"roomId\",\"systemId\":\"systemId\"},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        when(mockEvalRecordService.delete(Arrays.asList("value"))).thenReturn(false);

        // Run the test and verify the results
        BatchDeleteReq batchDeleteReq = new BatchDeleteReq();
        batchDeleteReq.setIds(Lists.list("11111"));
        mockMvc.perform(delete("/cr/eval/record")
                        .content(JsonUtil.toJsonString(batchDeleteReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":false,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetNodes() throws Exception {
        // Setup
        // Configure EvalRecordService.getNodes(...).
        final EvalRoomPairRsp evalRoomPairRsp = new EvalRoomPairRsp();
        evalRoomPairRsp.setId("id");
        evalRoomPairRsp.setLabel("label");
        final List<EvalRoomPairRsp> evalRoomPairRsps = Arrays.asList(evalRoomPairRsp);
        when(mockEvalRecordService.getNodes("roomId")).thenReturn(evalRoomPairRsps);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/nodes")
                        .param("room_id", "roomId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"id\":\"id\",\"label\":\"label\"}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }



    @Test
    void testGetProbes1() throws Exception {
        // Setup
        // Configure EvalRecordService.getProbes(...).
        final EvalRoomPairRsp evalRoomPairRsp = new EvalRoomPairRsp();
        evalRoomPairRsp.setId("id");
        evalRoomPairRsp.setLabel("label");
        final List<EvalRoomPairRsp> evalRoomPairRsps = Arrays.asList(evalRoomPairRsp);
        when(mockEvalRecordService.getProbes("roomId", "nodeId")).thenReturn(evalRoomPairRsps);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/probes")
                        .param("room_id", "roomId")
                        .param("node_id", "nodeId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"id\":\"id\",\"label\":\"label\"}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }


    @Test
    void testGetProbes2() throws Exception {
        // Setup
        when(mockEvalRecordService.getProbes("roomId")).thenReturn(new HashMap<>());

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/probes-map")
                        .param("room_id", "roomId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetNodeProbe() throws Exception {
        // Setup
        // Configure EvalRecordService.getNodeProbe(...).
        final EvalRecordNodeProbeReq evalRecordNodeProbeReq = new EvalRecordNodeProbeReq();
        evalRecordNodeProbeReq.setId("id");
        evalRecordNodeProbeReq.setLabel("label");
        final EvalRoomPairRsp evalRoomPairRsp = new EvalRoomPairRsp();
        evalRoomPairRsp.setId("id");
        evalRoomPairRsp.setLabel("label");
        evalRecordNodeProbeReq.setData(Arrays.asList(evalRoomPairRsp));
        final List<EvalRecordNodeProbeReq> evalRecordNodeProbeReqs = Arrays.asList(evalRecordNodeProbeReq);
        when(mockEvalRecordService.getNodeProbe("roomId")).thenReturn(evalRecordNodeProbeReqs);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/probes/all")
                        .param("room_id", "roomId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"id\":\"id\",\"label\":\"label\",\"data\":[{\"id\":\"id\",\"label\":\"label\"}]}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }



    @Test
    void testStart() throws Exception {
        // Setup
        // Configure EvalRecordService.getById(...).
        final EvalRecord evalRecord = new EvalRecord();
        evalRecord.setResourceId("resourceId");
        evalRecord.setName("name");
        evalRecord.setStatus(0);
        evalRecord.setTaskId("taskId");
        evalRecord.setEvalTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockEvalRecordService.getById("resourceId")).thenReturn(evalRecord);

        // Configure EvalTaskService.getTask(...).
        final EvalTask evalTask = new EvalTask();
        evalTask.setResourceId("resourceId");
        evalTask.setTemplateId("templateId");
        evalTask.setTemplateName("templateName");
        evalTask.setTaskName("taskName");
        evalTask.setLocalSceneId("localSceneId");
        when(mockEvalTaskService.getTask("taskId")).thenReturn(evalTask);

        // Configure EvalRecordService.updateById(...).
        final EvalRecord entity = new EvalRecord();
        entity.setResourceId("resourceId");
        entity.setName("name");
        entity.setStatus(0);
        entity.setTaskId("taskId");
        entity.setEvalTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockEvalRecordService.updateById(entity)).thenReturn(false);

        // Run the test and verify the results
        mockMvc.perform(post("/cr/eval/record/start")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));

        // Confirm EvalRecordService.recordRun(...).
        final EvalRecord evalRecord1 = new EvalRecord();
        evalRecord1.setResourceId("resourceId");
        evalRecord1.setName("name");
        evalRecord1.setStatus(0);
        evalRecord1.setTaskId("taskId");
        evalRecord1.setEvalTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final EvalTask evalTask1 = new EvalTask();
        evalTask1.setResourceId("resourceId");
        evalTask1.setTemplateId("templateId");
        evalTask1.setTemplateName("templateName");
        evalTask1.setTaskName("taskName");
        evalTask1.setLocalSceneId("localSceneId");
        verify(mockEvalRecordService).recordRun(evalRecord1, evalTask1, false, "language");
    }



    @Test
    void testResult() throws Exception {
        // Setup
        // Configure EvalRecordService.result(...).
        final EvalRecordResultRsp evalRecordResultRsp = new EvalRecordResultRsp();
        evalRecordResultRsp.setScore(0);
        evalRecordResultRsp.setMessage("message");
        when(mockEvalRecordService.result("recordId")).thenReturn(evalRecordResultRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/result")
                        .param("resource_id", "recordId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testVariables() throws Exception {
        // Setup
        // Configure EvalIndexRefService.selectVariables(...).
        final EvalRoomPairRsp evalRoomPairRsp = new EvalRoomPairRsp();
        evalRoomPairRsp.setId("id");
        evalRoomPairRsp.setLabel("label");
        final List<EvalRoomPairRsp> evalRoomPairRsps = Arrays.asList(evalRoomPairRsp);
        when(mockEvalIndexRefService.selectVariables("indexId")).thenReturn(evalRoomPairRsps);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/variables")
                        .param("index_id", "indexId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testVariableGraph() throws Exception {
        // Setup
        // Configure EvalVariableRefService.graph(...).
        final EvalVariableGraphRsp evalVariableGraphRsp = new EvalVariableGraphRsp();
        evalVariableGraphRsp.setTime(Arrays.asList(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
        evalVariableGraphRsp.setValue(Arrays.asList(0));
        evalVariableGraphRsp.setIsMatched(Arrays.asList(false));
        when(mockEvalVariableRefService.graph("variableId")).thenReturn(evalVariableGraphRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/variables/graph")
                        .param("variable_id", "variableId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testVariableData() throws Exception {
        // Setup
        // Configure EvalVariableRefService.originData(...).
        final EvalVariableDataPageRsp evalVariableDataPageRsp = new EvalVariableDataPageRsp();
        evalVariableDataPageRsp.setNodeIp("nodeIp");
        evalVariableDataPageRsp.setCollectionTime("collectionTime");
        evalVariableDataPageRsp.setContent("content");
        final ListRsp<EvalVariableDataPageRsp> evalVariableDataPageRspListRsp = new ListRsp<>(
                Arrays.asList(evalVariableDataPageRsp), 0L, 0L, 0L);
        when(mockEvalVariableRefService.originData(any(Page.class), eq("variableId")))
                .thenReturn(evalVariableDataPageRspListRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/variables/data")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testResultIndex() throws Exception {
        // Setup
        // Configure EvalIndexRefService.resultIndex(...).
        final EvalRecordResultIndexRsp evalRecordResultIndexRsp = new EvalRecordResultIndexRsp();
        evalRecordResultIndexRsp.setResourceId("resourceId");
        evalRecordResultIndexRsp.setName("name");
        evalRecordResultIndexRsp.setScore(0);
        evalRecordResultIndexRsp.setAlgorithmId("algorithmId");
        evalRecordResultIndexRsp.setAlgorithmName("algorithmName");
        when(mockEvalIndexRefService.resultIndex("indexId")).thenReturn(evalRecordResultIndexRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/result/index")
                        .param("index_id", "indexId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testResultVariable() throws Exception {
        // Setup
        // Configure EvalVariableRefService.resultVariable(...).
        final EvalRecordResultVariableRsp evalRecordResultVariableRsp = new EvalRecordResultVariableRsp();
        evalRecordResultVariableRsp.setResourceId("resourceId");
        evalRecordResultVariableRsp.setName("name");
        final EvalRecordResultAlgorithmRsp formatAlgorithm = new EvalRecordResultAlgorithmRsp();
        formatAlgorithm.setResourceId("resourceId");
        formatAlgorithm.setName("name");
        evalRecordResultVariableRsp.setFormatAlgorithm(formatAlgorithm);
        when(mockEvalVariableRefService.resultVariable("variableId")).thenReturn(evalRecordResultVariableRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/result/variable")
                        .param("variable_id", "variableId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testDumpEvalRecord2PDF() throws Exception {
        // Setup
        when(mockEvalRecordService.dumpEvalRecordPath("resourceId", "taskId")).thenReturn("pathname");

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/train2pdf")
                        .param("resource_id", "resourceId")
                        .param("task_id", "taskId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testGetRecordResultList() throws Exception {
        // Setup
        // Configure EvalRecordService.getAllCycleResult(...).
        final EvalSystemResultRsp evalSystemResultRsp = new EvalSystemResultRsp();
        evalSystemResultRsp.setName("name");
        evalSystemResultRsp.setResourceId("resourceId");
        evalSystemResultRsp.setExeCycle(0);
        evalSystemResultRsp.setFirstScore(0);
        evalSystemResultRsp.setSecondScore(0);
        final List<EvalSystemResultRsp> evalSystemResultRsps = Arrays.asList(evalSystemResultRsp);
        when(mockEvalRecordService.getAllCycleResult("resourceId")).thenReturn(evalSystemResultRsps);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/record/result/list")
                        .param("resource_id", "resourceId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }


}
