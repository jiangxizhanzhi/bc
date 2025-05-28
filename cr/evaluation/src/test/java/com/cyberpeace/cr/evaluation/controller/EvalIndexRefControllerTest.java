/**
 * 这段代码是一个Spring Boot应用中的单元测试类，用于测试`EvalIndexRefController`控制器。以下是对代码的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller` 
 * - **类名**：`EvalIndexRefControllerTest` 
 * - **注解**： 
 * - `@WebMvcTest(EvalIndexRefController.class)`：该注解用于配置一个只针对Web层的测试环境，并且指定要测试的控制器类为`EvalIndexRefController`。 
 * ### 成员变量 
 * - **`mockMvc`**：通过`@Autowired`自动注入的`MockMvc`对象，用于模拟HTTP请求。 
 * - **`mockEvalIndexRefMapper`**、**`mockEvalVariableRefMapper`**、**`mockEvalIndexRefService`**：通过`@MockBean`注解创建的模拟对象，用于模拟数据访问层和业务服务层的行为。 
 * ### 测试方法 
 * 1. **`testUpdate()`** 
 * - **功能**：测试更新操作（HTTP PATCH请求）。 
 * - **步骤**： 
 * - 配置模拟数据：`EvalIndexRef`和`EvalVariableRef`对象。 
 * - 设置模拟行为：当调用`selectById`方法时返回配置好的对象；调用`thirdSpecialist`方法时返回特定值。 
 * - 发送PATCH请求并验证响应状态码为4xx（客户端错误）。 
 * 2. **`testGet()`** 
 * - **功能**：测试获取操作（HTTP GET请求）。 
 * - **步骤**： 
 * - 配置模拟数据：`EvalIndexRef`对象。 
 * - 设置模拟行为：当调用`selectById`方法时返回配置好的对象。 
 * - 发送GET请求并验证响应状态码为200（OK），并且响应内容与预期的JSON字符串匹配。 
 * ### 总结 
 * - 这段代码主要用于测试`EvalIndexRefController`控制器中的两个主要操作：更新（PATCH）和获取（GET）。 
 * - 使用了Spring Boot提供的`@WebMvcTest`注解来配置测试环境，并使用`MockMvc`对象来模拟HTTP请求。 
 * - 通过`@MockBean`注解创建了模拟对象来模拟数据访问层和业务服务层的行为。 
 * - 每个测试方法都包含了详细的配置步骤和期望的结果验证。 
**/
package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EvalIndexRefController.class)
class EvalIndexRefControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvalIndexRefMapper mockEvalIndexRefMapper;
    @MockBean
    private EvalVariableRefMapper mockEvalVariableRefMapper;
    @MockBean
    private EvalIndexRefService mockEvalIndexRefService;

    @Test
    void testUpdate() throws Exception {
        // Setup
        // Configure EvalIndexRefMapper.selectById(...).
        final EvalIndexRef evalIndexRef = new EvalIndexRef();
        evalIndexRef.setType(0);
        evalIndexRef.setScore(0);
        evalIndexRef.setSpecialistValue(0);
        evalIndexRef.setSpecialistType(0);
        evalIndexRef.setSpecialistAttr(0);
        evalIndexRef.setSpecialistAttrValue(0);
        evalIndexRef.setProgress(0);
        when(mockEvalIndexRefMapper.selectById("resourceId")).thenReturn(evalIndexRef);

        // Configure EvalVariableRefMapper.selectById(...).
        final EvalVariableRef evalVariableRef = new EvalVariableRef();
        evalVariableRef.setScore(0);
        evalVariableRef.setType(0);
        evalVariableRef.setDeviceId("deviceId");
        evalVariableRef.setProbeId("probeId");
        evalVariableRef.setProgress(0);
        when(mockEvalVariableRefMapper.selectById("resourceId")).thenReturn(evalVariableRef);

        when(mockEvalIndexRefService.thirdSpecialist(0, 0, 0, 0)).thenReturn(0);

        // Run the test and verify the results
        mockMvc.perform(patch("/cr/eval/index/ref")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void testGet() throws Exception {
        // Setup
        // Configure EvalIndexRefMapper.selectById(...).
        final EvalIndexRef evalIndexRef = new EvalIndexRef();
        evalIndexRef.setType(0);
        evalIndexRef.setScore(0);
        evalIndexRef.setSpecialistValue(0);
        evalIndexRef.setSpecialistType(0);
        evalIndexRef.setSpecialistAttr(0);
        evalIndexRef.setSpecialistAttrValue(0);
        evalIndexRef.setProgress(0);
        when(mockEvalIndexRefMapper.selectById("resourceId")).thenReturn(evalIndexRef);

        // Run the test and verify the results
        mockMvc.perform(get("/cr/eval/index/ref")
                        .param("resource_id", "resourceId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"id\":null,\"createTime\":null,\"updateTime\":null,\"resourceId\":null,\"name\":null,\"type\":0,\"score\":0,\"level\":null,\"recordId\":null,\"algorithmId\":null,\"algorithmParams\":null,\"specialistValue\":0,\"specialistType\":0,\"specialistAttr\":0,\"specialistAttrValue\":0,\"nextIds\":null,\"startTime\":null,\"endTime\":null,\"progress\":0,\"matrixData\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

}
