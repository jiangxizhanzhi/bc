package com.cyberpeace.space.controller;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.model.req.IdReq;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.strategy.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceStrategyController.class)
class SpaceStrategyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceStrategyService mockSpaceStrategyService;

    @Test
    void testGetFrame() throws Exception {
        // Setup
        // Configure SpaceStrategyService.getFrame(...).
        final SpaceStrategyFrameBo spaceStrategyFrameBo = new SpaceStrategyFrameBo();
        spaceStrategyFrameBo.setResourceId("resourceId");
        spaceStrategyFrameBo.setName("name");
        spaceStrategyFrameBo.setParent("parent");
        spaceStrategyFrameBo.setBuiltin(false);
        spaceStrategyFrameBo.setChildren(Arrays.asList(new SpaceStrategyFrameBo()));
        when(mockSpaceStrategyService.getFrame()).thenReturn(spaceStrategyFrameBo);

        // Run the test and verify the results
        mockMvc.perform(get("/space/strategy/frame")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"resourceId\":null,\"name\":null,\"parent\":null,\"builtin\":null,\"removable\":null,\"children\":[]}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddFrame() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceStrategyFrameAddReq spaceStrategyFrameAddReq = new SpaceStrategyFrameAddReq();
        spaceStrategyFrameAddReq.setName("name");
        spaceStrategyFrameAddReq.setParent("parent");
        mockMvc.perform(post("/space/strategy/frame")
                        .content(JsonUtil.toJsonString(spaceStrategyFrameAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockSpaceStrategyService).addFrame(any(SpaceStrategyFrameAddReq.class));
    }

    @Test
    void testModifyFrame() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceStrategyFrameModifyReq spaceStrategyFrameModifyReq = new SpaceStrategyFrameModifyReq();
        spaceStrategyFrameModifyReq.setName("name");
        spaceStrategyFrameModifyReq.setResourceId("resource_id");
        mockMvc.perform(patch("/space/strategy/frame")
                        .content(JsonUtil.toJsonString(spaceStrategyFrameModifyReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockSpaceStrategyService).modify(any(SpaceStrategyFrameModifyReq.class));
    }

    @Test
    void testDeleteFrame() throws Exception {
        // Setup
        // Run the test and verify the results
        IdReq idReq = new IdReq();
        idReq.setResourceId("11234213");
        mockMvc.perform(delete("/space/strategy/frame")
                        .content(JsonUtil.toJsonString(idReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetStrategies() throws Exception {
        // Setup
        // Configure SpaceStrategyService.listStrategiesByFrame(...).
        final SpaceStrategySimple spaceStrategySimple = new SpaceStrategySimple();
        spaceStrategySimple.setResourceId("resourceId");
        spaceStrategySimple.setName("name");
        spaceStrategySimple.setContent("content");
        spaceStrategySimple.setRoute("route");
        final List<SpaceStrategySimple> spaceStrategySimples = Arrays.asList(spaceStrategySimple);
        when(mockSpaceStrategyService.listStrategiesByFrame("frame_id", "name")).thenReturn(spaceStrategySimples);

        // Run the test and verify the results
        mockMvc.perform(get("/space/strategy/list")
                        .param("frame_id", "frame_id")
                        .param("name", "name")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"resourceId\":\"resourceId\",\"name\":\"name\",\"content\":\"content\",\"route\":\"route\"}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetStrategies_SpaceStrategyServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockSpaceStrategyService.listStrategiesByFrame("frame_id", "name")).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/space/strategy/list")
                        .param("frame_id", "frame_id")
                        .param("name", "name")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPageStrategies() throws Exception {
        // Setup
        // Configure SpaceStrategyService.pageStrategiesByFrame(...).
        final SpaceStrategyBo spaceStrategyBo = new SpaceStrategyBo();
        spaceStrategyBo.setResourceId("resourceId");
        spaceStrategyBo.setName("name");
        spaceStrategyBo.setUserId("userId");
        spaceStrategyBo.setUser("user");
        spaceStrategyBo.setUserAvatar("userAvatar");
        final ListRsp<SpaceStrategyBo> spaceStrategyBoListRsp = new ListRsp<>(Arrays.asList(spaceStrategyBo), 0L, 0L,
                0L);
        final PageReqBase req = new PageReqBase();
        req.setPage(0);
        req.setPerPage(0);
        req.setSort("sort");
        when(mockSpaceStrategyService.pageStrategiesByFrame(req, "frame_id", "name"))
                .thenReturn(spaceStrategyBoListRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/space/strategy/page")
                        .param("frame_id", "frame_id")
                        .param("name", "name")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"resourceId\",\"name\":\"name\",\"userId\":\"userId\",\"user\":\"user\",\"userAvatar\":\"userAvatar\",\"content\":null,\"step\":null,\"example\":null,\"script\":null,\"scriptType\":null,\"frameId\":null,\"view\":null,\"createTime\":null,\"updateTime\":null,\"method\":null,\"route\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddStrategy() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceStrategyAddReq spaceStrategyAddReq = new SpaceStrategyAddReq();
        spaceStrategyAddReq.setFrameId("frameId");
        spaceStrategyAddReq.setName("name");
        spaceStrategyAddReq.setContent("content");
        mockMvc.perform(post("/space/strategy")
                        .content(JsonUtil.toJsonString(spaceStrategyAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockSpaceStrategyService).addStrategy(any(SpaceStrategyAddReq.class));
    }

    @Test
    void testUpdateStrategy() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceStrategyModifyReq spaceStrategyModifyReq = new SpaceStrategyModifyReq();
        spaceStrategyModifyReq.setResourceId("resource_id");
        spaceStrategyModifyReq.setName("name");
        spaceStrategyModifyReq.setContent("content");
        spaceStrategyModifyReq.setFrameId("frameId");
        mockMvc.perform(patch("/space/strategy")
                        .content(JsonUtil.toJsonString(spaceStrategyModifyReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockSpaceStrategyService).updateStrategy(any(SpaceStrategyModifyReq.class));
    }

    @Test
    void testDeleteStrategy() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq idListReq = new IdListReq();
        idListReq.setIds(Lists.list("id"));
        mockMvc.perform(delete("/space/strategy")
                        .content(JsonUtil.toJsonString(idListReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetDetail() throws Exception {
        // Setup
        // Configure SpaceStrategyService.getDetail(...).
        final SpaceStrategyBo spaceStrategyBo = new SpaceStrategyBo();
        spaceStrategyBo.setResourceId("resourceId");
        spaceStrategyBo.setName("name");
        spaceStrategyBo.setUserId("userId");
        spaceStrategyBo.setUser("user");
        spaceStrategyBo.setUserAvatar("userAvatar");
        when(mockSpaceStrategyService.getDetail("resource_id")).thenReturn(spaceStrategyBo);

        // Run the test and verify the results
        mockMvc.perform(get("/space/strategy")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"name\":\"name\",\"userId\":\"userId\",\"user\":\"user\",\"userAvatar\":\"userAvatar\",\"content\":null,\"step\":null,\"example\":null,\"script\":null,\"scriptType\":null,\"frameId\":null,\"view\":null,\"createTime\":null,\"updateTime\":null,\"method\":null,\"route\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetRoute() throws Exception {
        // Setup
        when(mockSpaceStrategyService.getFrameRouteById("frame_id")).thenReturn("data");

        // Configure SpaceStrategyService.getById(...).
        final SpaceStrategy spaceStrategy = new SpaceStrategy();
        spaceStrategy.setResourceId("resourceId");
        spaceStrategy.setName("name");
        spaceStrategy.setUserId("userId");
        spaceStrategy.setView(0);
        spaceStrategy.setFrameId("frameId");
        when(mockSpaceStrategyService.getById("strategy_id")).thenReturn(spaceStrategy);

        // Run the test and verify the results
        mockMvc.perform(get("/space/strategy/route")
                        .param("strategy_id", "strategy_id")
                        .param("frame_id", "frame_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":\"data\",\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
