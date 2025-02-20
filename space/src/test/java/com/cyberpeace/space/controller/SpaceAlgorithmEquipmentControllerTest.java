package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmEquipment;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.SpaceAlgorithmEquipmentService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmEquipmentBo;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceAlgorithmEquipmentController.class)
class SpaceAlgorithmEquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SpaceAlgorithmEquipmentService mockService;
    @SpyBean
    private SpaceAlgorithmEquipmentController spaceAlgorithmEquipmentController;

    @BeforeEach
    void setUp() {
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceAlgorithmEquipment());
        when(mockService.save(any(SpaceAlgorithmEquipment.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceAlgorithmEquipment.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);


        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceBaseTable.class);

        doReturn(true).when(spaceAlgorithmEquipmentController).checkNameDuplication(any());

    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/algorithm/algorithm_equipment/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceAlgorithmEquipmentBo spaceAlgorithmEquipmentBo = new SpaceAlgorithmEquipmentBo();
        spaceAlgorithmEquipmentBo.setResourceId("resource_id");
        spaceAlgorithmEquipmentBo.setName("resource_name");

        mockMvc.perform(post("/space/algorithm/algorithm_equipment")
                        .content(JsonUtil.toJsonString(spaceAlgorithmEquipmentBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceAlgorithmEquipmentBo spaceAlgorithmEquipmentBo = new SpaceAlgorithmEquipmentBo();
        spaceAlgorithmEquipmentBo.setResourceId("resource_id");
        spaceAlgorithmEquipmentBo.setName("resource_name");
        mockMvc.perform(patch("/space/algorithm/algorithm_equipment")
                        .content(JsonUtil.toJsonString(spaceAlgorithmEquipmentBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testDeleteA() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq idListReq = new IdListReq();
        idListReq.setIds(Lists.newArrayList("111111"));
        mockMvc.perform(delete("/space/algorithm/algorithm_equipment")
                        .content(JsonUtil.toJsonString(idListReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results

        mockMvc.perform(get("/space/algorithm/algorithm_equipment")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"description\":null,\"assetManagement\":null,\"behaviorManagement\":null,\"moduleIsolation\":null,\"riskDiscovery\":null,\"threatMonitoring\":null,\"strategy\":null,\"capability\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
