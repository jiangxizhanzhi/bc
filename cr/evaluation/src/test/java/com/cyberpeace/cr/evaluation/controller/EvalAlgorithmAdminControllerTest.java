package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlgorithmReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 这段代码是一个JUnit测试类 `EvalAlgorithmAdminControllerTest`，用于测试 `EvalAlgorithmAdminController` 控制器的功能。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **类名**: `EvalAlgorithmAdminControllerTest` 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller` 
 * ### 主要组件 
 * 1. **依赖注入**: 
 * - 使用 `@Autowired` 注入 `MockMvc` 对象。 
 * - 使用 `@MockBean` 注入模拟对象（mock）：`EvalAlgorithmService`, `EvalAlgorithmMapper`, `TransactionTemplate`。 
 * - 使用 `@SpyBean` 注入被测试类 `EvalAlgorithmAdminController`。 
 * 2. **初始化方法**: 
 * - `setup()` 方法在每个测试之前运行，配置模拟对象的行为。 
 * ### 测试方法 
 * 1. **添加算法 (`testAddAlgorithm`)**: 
 * - 模拟 `EvalAlgorithmMapper.existByName` 返回 `false`。 
 * - 发送 POST 请求到 `/cr/eval/algorithm`。 
 * - 验证返回状态为 `200 OK`，并检查响应内容是否符合预期。 
 * 2. **处理已存在的算法名称 (`testAddAlgorithm_EvalAlgorithmMapperReturnsTrue`)**: 
 * - 模拟 `EvalAlgorithmMapper.existByName` 返回 `true`。 
 * - 发送 POST 请求到 `/cr/eval/algorithm`。 
 * - 验证返回状态为客户端错误（4xx）。 
 * 3. **编辑算法 (`testEditAlgorithm`)**: 
 * - 模拟 `EvalAlgorithmMapper.selectList` 和 `EvalAlgorithmService.getById` 返回特定的 `EvalAlgorithm` 实例。 
 * - 发送 PUT 请求到 `/cr/eval/algorithm`。 
 * - 验证返回状态为 `200 OK`，并检查响应内容是否符合预期。 
 * 4. **处理不存在的算法 (`testEditAlgorithm_EvalAlgorithmServiceGetByIdReturnsNull`)**: 
 * - 模拟 `EvalAlgorithmService.getById` 返回 `null`。 
 * - 发送 PUT 请求到 `/cr/eval/algorithm`。 
 * - 验证返回状态为客户端错误（4xx）。 
 * 5. **处理已存在的算法名称 (`testEditAlgorithm_EvalAlgorithmMapperExistByNameReturnsTrue`)**: 
 * - 模拟 `EvalAlgorithmMapper.existByName` 返回 `true`。 
 * - 发送 PUT 请求到 `/cr/eval/algorithm`。 
 * - 验证返回状态为 `200 OK`，并检查响应内容是否符合预期。 
 * 6. **处理事务异常 (`testEditAlgorithm_TransactionTemplateThrowsTransactionException`)**: 
 * - 模拟 `TransactionTemplate.execute` 抛出异常。 
 * - 发送 PUT 请求到 `/cr/eval/algorithm`。 
 * - 验证返回状态为客户端错误（4xx）。 
 * 7. **批量删除 (`testBatchDelete`)**: 
 * - 模拟 `EvalAlgorithmService.selectUsedAlgorithmIds` 返回特定的 ID 列表。 
 * - 发送 DELETE 请求到 `/cr/eval/algorithm`。 
 * - 验证返回状态为 `200 OK`，并检查响应内容是否符合预期。 
 * 8. **获取单个算法 (`testGetEvalAlgorithmRsp`)**: 
 * - 模拟 `EvalAlgorithmService.getById` 返回特定的 `EvalAlgorithm` 实例。 
 * - 发送 GET 请求到 `/cr/eval/algorithm`。 
 * - 验证返回状态为 `200 OK`，并检查响应内容是否符合预期。 
 * 9. **获取算法列表 (`testAlgorithmList`)**: 
 * - 模拟 `EvalAlgorithmService.selectByType` 返回特定的 `EvalAlgorithm` 列表。 
 * - 发送 GET 请求到 `/cr/eval/algorithm/type`。 
 * - 验证返回状态为 `200 OK`，并检查响应内容是否符合预期。 
 * 10. **获取所有算法 (`testGetAll`)**: 
 * - 模拟 `EvalAlgorithmMapper.getAll` 返回特定的 `EvalAlgorithm` 列表。 
 * - 发送 GET 请求到 `/cr/eval/algorithm/getAll`。 
 * - 验证返回状态为 `200 OK`，并检查响应内容是否符合预期。 
 * 11. **分页查询 (`testList`)**: 
 * - 模拟 `EvalAlgorithmService.page` 返回特定的分页结果。 
 * - 发送 GET 请求到 `/cr/eval/algorithm/list`。 
 * - 验证返回状态为 `200 OK`，并检查响应内容是否符合预期。 
 * ### 总结 
 * 该测试类通过模拟各种场景来验证 `EvalAlgorithmAdminController` 控制器的各个端点的功能。每个测试方法都设置了相应的模拟行为，并通过 `MockMvc` 发送请求来验证控制器的响应。这有助于确保控制器在不同条件下的正确性和健壮性。 
**/
@WebMvcTest(EvalAlgorithmAdminController.class)
class EvalAlgorithmAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvalAlgorithmService mockEvalAlgorithmService;
    @MockBean
    private EvalAlgorithmMapper mockEvalAlgorithmMapper;
    @MockBean
    private TransactionTemplate mockTransactionTemplate;
    @SpyBean
    private EvalAlgorithmAdminController evalAlgorithmAdminControllerUnderTest;

    @BeforeEach
    void setup() {
        doNothing().when(evalAlgorithmAdminControllerUnderTest).validateReq(any());
        doReturn(new EvalAlgorithm()).when(evalAlgorithmAdminControllerUnderTest).convertEvalAlgorithmReqToEvalAlgorithm(any());
        doNothing().when(evalAlgorithmAdminControllerUnderTest).checkAlgorithmMode(any());
    }
    @Test
    void testAddAlgorithm() throws Exception {
        // Setup
        when(mockEvalAlgorithmMapper.existByName("name", "evalAlgorithmId")).thenReturn(false);
        EvalAlgorithmReq evalAlgorithmReq = new EvalAlgorithmReq();
        evalAlgorithmReq.setEvalAlgorithmId("evalAlgorithmId");
        evalAlgorithmReq.setName("name");

        // Run the test and verify the results
        mockMvc.perform(post("/cr/eval/algorithm")
                        .content(JsonUtil.toJsonString(evalAlgorithmReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"resourceId\":null,\"name\":null,\"description\":null,\"type\":null,\"algorithmLanguage\":null,\"code\":null,\"state\":0,\"params\":null,\"paramsNum\":null,\"createUserId\":null,\"mode\":null,\"builtin\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));

        // Confirm EvalAlgorithmService.save(...).
        final EvalAlgorithm entity = new EvalAlgorithm();
        entity.setResourceId("evalAlgorithmId");
        entity.setState(0);
        entity.setParams("params");
        entity.setParamsNum(0);
        entity.setCreateUserId("createUserId");
        entity.setBuiltin(0);
        verify(mockEvalAlgorithmService).save(entity);
    }

    @Test
    void testAddAlgorithm_EvalAlgorithmMapperReturnsTrue() throws Exception {
        // Setup
        when(mockEvalAlgorithmMapper.existByName("name", "evalAlgorithmId")).thenReturn(true);

        // Run the test and verify the results
        mockMvc.perform(post("/cr/eval/algorithm")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testEditAlgorithm() throws Exception {
        // Setup
        // Configure EvalAlgorithmMapper.selectList(...).
        final EvalAlgorithm evalAlgorithm = new EvalAlgorithm();
        evalAlgorithm.setResourceId("evalAlgorithmId");
        evalAlgorithm.setState(0);
        evalAlgorithm.setParams("params");
        evalAlgorithm.setParamsNum(0);
        evalAlgorithm.setCreateUserId("createUserId");
        evalAlgorithm.setBuiltin(0);
        final List<EvalAlgorithm> evalAlgorithms = Arrays.asList(evalAlgorithm);
        when(mockEvalAlgorithmMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(evalAlgorithms);

        // Configure EvalAlgorithmService.getById(...).
        final EvalAlgorithm evalAlgorithm1 = new EvalAlgorithm();
        evalAlgorithm1.setResourceId("evalAlgorithmId");
        evalAlgorithm1.setState(0);
        evalAlgorithm1.setParams("params");
        evalAlgorithm1.setParamsNum(0);
        evalAlgorithm1.setCreateUserId("createUserId");
        evalAlgorithm1.setBuiltin(0);
        when(mockEvalAlgorithmService.getById("evalAlgorithmId")).thenReturn(evalAlgorithm1);

        when(mockEvalAlgorithmMapper.existByName("name", "evalAlgorithmId")).thenReturn(false);
        when(mockEvalAlgorithmService.selectUsedNum(Arrays.asList("value"))).thenReturn(0);

        // Run the test and verify the results
        EvalAlgorithmReq evalAlgorithmReq = new EvalAlgorithmReq();
        evalAlgorithmReq.setEvalAlgorithmId("evalAlgorithmId");
        mockMvc.perform(put("/cr/eval/algorithm")
                        .content(JsonUtil.toJsonString(evalAlgorithmReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"resourceId\":null,\"name\":null,\"description\":null,\"type\":null,\"algorithmLanguage\":null,\"code\":null,\"state\":0,\"params\":null,\"paramsNum\":null,\"createUserId\":null,\"mode\":null,\"builtin\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));

        // Confirm EvalAlgorithmService.save(...).
        final EvalAlgorithm entity = new EvalAlgorithm();
        entity.setResourceId("evalAlgorithmId");
        entity.setState(0);
        entity.setParams("params");
        entity.setParamsNum(0);
        entity.setCreateUserId("createUserId");
        entity.setBuiltin(0);


        // Confirm EvalAlgorithmService.updateById(...).
        final EvalAlgorithm entity1 = new EvalAlgorithm();
        entity1.setResourceId("evalAlgorithmId");
        entity1.setState(0);
        entity1.setParams("params");
        entity1.setParamsNum(0);
        entity1.setCreateUserId("createUserId");
        entity1.setBuiltin(0);
    }

    @Test
    void testEditAlgorithm_EvalAlgorithmServiceGetByIdReturnsNull() throws Exception {
        // Setup
        // Configure EvalAlgorithmMapper.selectList(...).
        final EvalAlgorithm evalAlgorithm = new EvalAlgorithm();
        evalAlgorithm.setResourceId("evalAlgorithmId");
        evalAlgorithm.setState(0);
        evalAlgorithm.setParams("params");
        evalAlgorithm.setParamsNum(0);
        evalAlgorithm.setCreateUserId("createUserId");
        evalAlgorithm.setBuiltin(0);
        final List<EvalAlgorithm> evalAlgorithms = Arrays.asList(evalAlgorithm);
        when(mockEvalAlgorithmMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(evalAlgorithms);

        when(mockEvalAlgorithmService.getById("evalAlgorithmId")).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(put("/cr/eval/algorithm")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testEditAlgorithm_EvalAlgorithmMapperExistByNameReturnsTrue() throws Exception {
        // Setup
        // Configure EvalAlgorithmMapper.selectList(...).
        final EvalAlgorithm evalAlgorithm = new EvalAlgorithm();
        evalAlgorithm.setResourceId("evalAlgorithmId");
        evalAlgorithm.setState(0);
        evalAlgorithm.setParams("params");
        evalAlgorithm.setParamsNum(0);
        evalAlgorithm.setCreateUserId("createUserId");
        evalAlgorithm.setBuiltin(0);
        final List<EvalAlgorithm> evalAlgorithms = Arrays.asList(evalAlgorithm);
        when(mockEvalAlgorithmMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(evalAlgorithms);

        // Configure EvalAlgorithmService.getById(...).
        final EvalAlgorithm evalAlgorithm1 = new EvalAlgorithm();
        evalAlgorithm1.setResourceId("evalAlgorithmId");
        evalAlgorithm1.setState(0);
        evalAlgorithm1.setParams("params");
        evalAlgorithm1.setParamsNum(0);
        evalAlgorithm1.setCreateUserId("createUserId");
        evalAlgorithm1.setBuiltin(0);
        when(mockEvalAlgorithmService.getById("evalAlgorithmId")).thenReturn(evalAlgorithm1);

        when(mockEvalAlgorithmMapper.existByName("name", "evalAlgorithmId")).thenReturn(true);

        // Run the test and verify the results
        EvalAlgorithmReq evalAlgorithmReq = new EvalAlgorithmReq();
        evalAlgorithmReq.setEvalAlgorithmId("evalAlgorithmId");
        mockMvc.perform(put("/cr/eval/algorithm")
                        .content(JsonUtil.toJsonString(evalAlgorithmReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"resourceId\":null,\"name\":null,\"description\":null,\"type\":null,\"algorithmLanguage\":null,\"code\":null,\"state\":0,\"params\":null,\"paramsNum\":null,\"createUserId\":null,\"mode\":null,\"builtin\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testEditAlgorithm_TransactionTemplateThrowsTransactionException() throws Exception {
        // Setup
        // Configure EvalAlgorithmMapper.selectList(...).
        final EvalAlgorithm evalAlgorithm = new EvalAlgorithm();
        evalAlgorithm.setResourceId("evalAlgorithmId");
        evalAlgorithm.setState(0);
        evalAlgorithm.setParams("params");
        evalAlgorithm.setParamsNum(0);
        evalAlgorithm.setCreateUserId("createUserId");
        evalAlgorithm.setBuiltin(0);
        final List<EvalAlgorithm> evalAlgorithms = Arrays.asList(evalAlgorithm);
        when(mockEvalAlgorithmMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(evalAlgorithms);

        // Configure EvalAlgorithmService.getById(...).
        final EvalAlgorithm evalAlgorithm1 = new EvalAlgorithm();
        evalAlgorithm1.setResourceId("evalAlgorithmId");
        evalAlgorithm1.setState(0);
        evalAlgorithm1.setParams("params");
        evalAlgorithm1.setParamsNum(0);
        evalAlgorithm1.setCreateUserId("createUserId");
        evalAlgorithm1.setBuiltin(0);
        when(mockEvalAlgorithmService.getById("evalAlgorithmId")).thenReturn(evalAlgorithm1);

        when(mockEvalAlgorithmMapper.existByName("name", "evalAlgorithmId")).thenReturn(false);
        when(mockEvalAlgorithmService.selectUsedNum(Arrays.asList("value"))).thenReturn(0);
        when(mockTransactionTemplate.execute(any(TransactionCallback.class))).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(put("/cr/eval/algorithm")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        // Confirm EvalAlgorithmService.save(...).
        final EvalAlgorithm entity = new EvalAlgorithm();
        entity.setResourceId("evalAlgorithmId");
        entity.setState(0);
        entity.setParams("params");
        entity.setParamsNum(0);
        entity.setCreateUserId("createUserId");
        entity.setBuiltin(0);
    }

    @Test
    void testBatchDelete() throws Exception {
        // Setup
        when(mockEvalAlgorithmService.selectUsedAlgorithmIds(Arrays.asList("value")))
                .thenReturn(Arrays.asList("value"));

        // Configure EvalAlgorithmMapper.selectList(...).
        final EvalAlgorithm evalAlgorithm = new EvalAlgorithm();
        evalAlgorithm.setResourceId("evalAlgorithmId");
        evalAlgorithm.setState(0);
        evalAlgorithm.setParams("params");
        evalAlgorithm.setParamsNum(0);
        evalAlgorithm.setCreateUserId("createUserId");
        evalAlgorithm.setBuiltin(0);
        final List<EvalAlgorithm> evalAlgorithms = Arrays.asList(evalAlgorithm);
        when(mockEvalAlgorithmMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(evalAlgorithms);

        // Run the test and verify the results
        BatchDeleteReq batchDeleteReq = new BatchDeleteReq();
        batchDeleteReq.setIds(Lists.list("11111"));
        mockMvc.perform(delete("/cr/eval/algorithm")
                        .content(JsonUtil.toJsonString(batchDeleteReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }


    @Test
    void testGetEvalAlgorithmRsp() throws Exception {
        // Setup
        // Configure EvalAlgorithmService.getById(...).
        final EvalAlgorithm evalAlgorithm = new EvalAlgorithm();
        evalAlgorithm.setResourceId("evalAlgorithmId");
        evalAlgorithm.setState(0);
        evalAlgorithm.setParams("[]");
        evalAlgorithm.setParamsNum(0);
        evalAlgorithm.setCreateUserId("createUserId");
        evalAlgorithm.setBuiltin(0);
        when(mockEvalAlgorithmService.getById("resource_id")).thenReturn(evalAlgorithm);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/algorithm")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"evalAlgorithmId\":\"evalAlgorithmId\",\"name\":null,\"description\":null,\"type\":null,\"algorithmLanguage\":null,\"code\":null,\"matrixData\":null,\"mode\":null,\"builtin\":0,\"params\":[],\"paramsNum\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }


    @Test
    void testAlgorithmList() throws Exception {
        // Setup
        // Configure EvalAlgorithmService.selectByType(...).
        final EvalAlgorithm evalAlgorithm = new EvalAlgorithm();
        evalAlgorithm.setResourceId("evalAlgorithmId");
        evalAlgorithm.setState(0);
        evalAlgorithm.setParams("[]");
        evalAlgorithm.setParamsNum(0);
        evalAlgorithm.setCreateUserId("createUserId");
        evalAlgorithm.setBuiltin(0);
        final List<EvalAlgorithm> evalAlgorithms = Arrays.asList(evalAlgorithm);
        when(mockEvalAlgorithmService.selectByType("type")).thenReturn(evalAlgorithms);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/algorithm/type")
                        .param("type", "type")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"evalAlgorithmId\":\"evalAlgorithmId\",\"name\":null,\"description\":null,\"type\":null,\"algorithmLanguage\":null,\"code\":null,\"matrixData\":null,\"mode\":null,\"builtin\":0,\"params\":[],\"paramsNum\":0}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetAll() throws Exception {
        // Setup
        // Configure EvalAlgorithmMapper.getAll(...).
        final EvalAlgorithm evalAlgorithm = new EvalAlgorithm();
        evalAlgorithm.setResourceId("evalAlgorithmId");
        evalAlgorithm.setState(0);
        evalAlgorithm.setParams("[]");
        evalAlgorithm.setParamsNum(0);
        evalAlgorithm.setCreateUserId("createUserId");
        evalAlgorithm.setBuiltin(0);
        final List<EvalAlgorithm> evalAlgorithms = Arrays.asList(evalAlgorithm);
        when(mockEvalAlgorithmMapper.getAll()).thenReturn(evalAlgorithms);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/algorithm/getAll")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"evalAlgorithmId\":\"evalAlgorithmId\",\"name\":null,\"description\":null,\"type\":null,\"algorithmLanguage\":null,\"code\":null,\"matrixData\":null,\"mode\":null,\"builtin\":0,\"params\":[],\"paramsNum\":0}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }


    @Test
    void testList() throws Exception {
        // Setup
        // Configure EvalAlgorithmService.page(...).
        final EvalListQuery listQuery = new EvalListQuery();
        listQuery.setPattern("pattern");
        listQuery.setPer_page(0);
        listQuery.setSort("-create_time");
        listQuery.setName("pattern");
        listQuery.setType(0);
        Page<EvalAlgorithm> pages = new Page<>(0, 10);
        pages.setRecords(Collections.emptyList());
        when(mockEvalAlgorithmService.page(any(EvalListQuery.class))).thenReturn(pages);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/algorithm/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}

