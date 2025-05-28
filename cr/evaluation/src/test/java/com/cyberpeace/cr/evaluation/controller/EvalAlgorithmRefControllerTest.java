/**
 * 这段代码是一个单元测试类 `EvalAlgorithmRefControllerTest`，用于测试 `EvalAlgorithmRefController` 控制器。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller` 
 * - **类名**: `EvalAlgorithmRefControllerTest` 
 * - **注解**: 
 * - `@WebMvcTest(EvalAlgorithmRefController.class)`: 用于测试 Spring MVC 控制器，并且仅加载指定的控制器。 
 * - `@MockBean`: 用于创建模拟对象（mock objects）。 
 * ### 测试方法 
 * #### 1. `testEditAlgorithm` 
 * - **功能**: 测试 `PUT /cr/eval/algorithm/ref` 请求的处理。 
 * - **步骤**: 
 * - 配置模拟对象 (`mockEvalAlgorithmRefMapper`, `mockEvalIndexRefMapper`, 等) 返回预期的实体对象。 
 * - 执行 PUT 请求并验证响应状态码为 4xx 客户端错误。 
 * #### 2. `testGetEvalAlgorithmRef` 
 * - **功能**: 测试 `GET /cr/eval/algorithm/ref` 请求的处理。 
 * - **步骤**: 
 * - 配置模拟对象 (`mockEvalIndexRefMapper`, `mockEvalAlgorithmRefMapper`, `mockEvalVariableRefMapper`) 返回预期的实体对象。 
 * - 执行 GET 请求并验证响应状态码为 200 OK，并且响应内容符合预期的 JSON 格式。 
 * ### 模拟对象 
 * - `mockEvalAlgorithmRefMapper` 
 * - `mockEvalIndexRefMapper` 
 * - `mockEvalVariableRefMapper` 
 * - `mockRemotePythonService` 
 * - `mockEvalAlgorithmRefService` 
 * - `mockTransactionTemplate` 
 * 这些模拟对象用于在测试中替代实际的对象，以便可以控制它们的行为并验证控制器的逻辑。 
 * ### 总结 
 * 这段代码主要目的是通过配置模拟对象来验证 `EvalAlgorithmRefController` 控制器的两个主要功能：编辑算法和获取算法引用。测试通过发送 HTTP 请求并验证响应的状态码和内容来确保控制器的行为符合预期。 
**/
package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmRefService;
import com.cyberpeace.integratation.service.RemotePythonService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EvalAlgorithmRefController.class)
class EvalAlgorithmRefControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvalAlgorithmRefMapper mockEvalAlgorithmRefMapper;
    @MockBean
    private EvalIndexRefMapper mockEvalIndexRefMapper;
    @MockBean
    private EvalVariableRefMapper mockEvalVariableRefMapper;
    @MockBean
    private RemotePythonService mockRemotePythonService;
    @MockBean
    private EvalAlgorithmRefService mockEvalAlgorithmRefService;
    @MockBean
    private TransactionTemplate mockTransactionTemplate;

    @Test
    void testEditAlgorithm() throws Exception {
        // Setup
        // Configure EvalAlgorithmRefMapper.selectById(...).
        final EvalAlgorithmRef evalAlgorithmRef = new EvalAlgorithmRef();
        evalAlgorithmRef.setId(0);
        evalAlgorithmRef.setResourceId("");
        evalAlgorithmRef.setName("name");
        evalAlgorithmRef.setCode("code");
        evalAlgorithmRef.setMatrixData("matrixData");
        when(mockEvalAlgorithmRefMapper.selectById("resourceId")).thenReturn(evalAlgorithmRef);

        // Configure EvalAlgorithmRefMapper.selectOne(...).
        final EvalAlgorithmRef evalAlgorithmRef1 = new EvalAlgorithmRef();
        evalAlgorithmRef1.setId(0);
        evalAlgorithmRef1.setResourceId("");
        evalAlgorithmRef1.setName("name");
        evalAlgorithmRef1.setCode("code");
        evalAlgorithmRef1.setMatrixData("matrixData");
        when(mockEvalAlgorithmRefMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(evalAlgorithmRef1);

        // Configure EvalIndexRefMapper.selectOne(...).
        final EvalIndexRef evalIndexRef = new EvalIndexRef();
        evalIndexRef.setId(0);
        evalIndexRef.setName("name");
        evalIndexRef.setAlgorithmId("algorithmId");
        evalIndexRef.setAlgorithmParams("algorithmParams");
        evalIndexRef.setNextIds("nextIds");
        evalIndexRef.setMatrixData("matrixData");
        when(mockEvalIndexRefMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(evalIndexRef);

        // Run the test and verify the results
        mockMvc.perform(put("/cr/eval/algorithm/ref")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        // Confirm EvalAlgorithmRefMapper.updateById(..
    }





    @Test
    void testGetEvalAlgorithmRef() throws Exception {
        // Setup
        // Configure EvalIndexRefMapper.selectList(...).
        final EvalIndexRef evalIndexRef = new EvalIndexRef();
        evalIndexRef.setId(0);
        evalIndexRef.setName("name");
        evalIndexRef.setAlgorithmId("algorithmId");
        evalIndexRef.setAlgorithmParams("algorithmParams");
        evalIndexRef.setNextIds("nextIds");
        evalIndexRef.setMatrixData("matrixData");
        final List<EvalIndexRef> evalIndexRefs = Arrays.asList(evalIndexRef);
        when(mockEvalIndexRefMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(evalIndexRefs);

        // Configure EvalAlgorithmRefMapper.selectById(...).
        final EvalAlgorithmRef evalAlgorithmRef = new EvalAlgorithmRef();
        evalAlgorithmRef.setId(0);
        evalAlgorithmRef.setResourceId("");
        evalAlgorithmRef.setName("name");
        evalAlgorithmRef.setCode("code");
        evalAlgorithmRef.setMatrixData("matrixData");
        when(mockEvalAlgorithmRefMapper.selectById(any())).thenReturn(evalAlgorithmRef);

        // Configure EvalVariableRefMapper.selectOne(...).
        final EvalVariableRef evalVariableRef = new EvalVariableRef();
        evalVariableRef.setId(0);
        evalVariableRef.setFormatAlgorithmId("");
        evalVariableRef.setFormatParams("formatParams");
        evalVariableRef.setMatchAlgorithmId("");
        evalVariableRef.setMatchParams("matchParams");
        evalVariableRef.setMarkAlgorithmId("");
        evalVariableRef.setMarkParams("markParams");
        when(mockEvalVariableRefMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(evalVariableRef);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/algorithm/ref")
                        .param("resourceId", "resourceId")
                        .param("type", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"evalAlgorithmId\":\"\",\"name\":\"name\",\"description\":null,\"type\":null,\"algorithmLanguage\":null,\"code\":\"code\",\"matrixData\":\"matrixData\",\"mode\":null,\"builtin\":null,\"params\":[],\"paramsNum\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }




}
