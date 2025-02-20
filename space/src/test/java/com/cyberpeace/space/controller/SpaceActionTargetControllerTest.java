package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.SpaceActionTargetService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetBo;
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
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceActionTargetController.class)
class SpaceActionTargetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceActionFrameService mockFrameService;
    @MockBean
    private SpaceActionTargetService mockService;
    @MockBean
    private TransactionTemplate mockTransactionTemplate;
    @MockBean
    private SpaceActionRelationService mockRelationService;
    @SpyBean
    private SpaceActionTargetController spaceActionTargetController;

    @BeforeEach
    void setUp() {
        // Setup
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockRelationService.getLowerIdsByUpperIds(anyList())).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceActionTarget());
        when(mockService.save(any(SpaceActionTarget.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceActionTarget.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceFrameBaseTable.class);
        when(mockTransactionTemplate.execute(any())).thenReturn(null);
        doReturn(true).when(spaceActionTargetController).checkNameDuplication(any());
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Configure SpaceActionFrameService.listByIds(...).
        final SpaceActionFrame spaceActionFrame = new SpaceActionFrame();
        spaceActionFrame.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        spaceActionFrame.setResourceId("resourceId");
        spaceActionFrame.setName("name");
        spaceActionFrame.setType(0);
        spaceActionFrame.setRole(0);
        final List<SpaceActionFrame> spaceActionFrames = Arrays.asList(spaceActionFrame);
        when(mockFrameService.listByIds(Arrays.asList("value"))).thenReturn(spaceActionFrames);

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_target/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage_SpaceActionFrameServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockFrameService.listByIds(Arrays.asList("value"))).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_target/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testListAll() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_target/list_all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceActionTargetBo spaceActionTargetBo = new SpaceActionTargetBo();
        spaceActionTargetBo.setName("name");
        spaceActionTargetBo.setResourceId("3413241241");

        mockMvc.perform(post("/space/action_frame/action_target")
                        .content(JsonUtil.toJsonString(spaceActionTargetBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"3413241241\",\"name\":\"name\",\"description\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceActionTargetBo spaceActionTargetBo = new SpaceActionTargetBo();
        spaceActionTargetBo.setName("name");
        spaceActionTargetBo.setResourceId("3413241241");
        mockMvc.perform(patch("/space/action_frame/action_target")
                        .content(JsonUtil.toJsonString(spaceActionTargetBo)).contentType(MediaType.APPLICATION_JSON)
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
        mockMvc.perform(delete("/space/action_frame/action_target")
                        .content(JsonUtil.toJsonString(idListReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Configure SpaceActionFrameService.listByIds(...).
        final SpaceActionFrame spaceActionFrame = new SpaceActionFrame();
        spaceActionFrame.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        spaceActionFrame.setResourceId("resource_id");
        spaceActionFrame.setName("name");
        spaceActionFrame.setType(0);
        spaceActionFrame.setRole(0);
        final List<SpaceActionFrame> spaceActionFrames = Arrays.asList(spaceActionFrame);
        when(mockFrameService.listByIds(Arrays.asList("value"))).thenReturn(spaceActionFrames);

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_target")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"description\":null,\"frame\":null,\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail_SpaceActionFrameServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockFrameService.listByIds(Arrays.asList("value"))).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_target")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"description\":null,\"frame\":null,\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
