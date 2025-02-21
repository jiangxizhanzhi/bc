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
