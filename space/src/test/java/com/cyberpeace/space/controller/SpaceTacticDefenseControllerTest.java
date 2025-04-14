package com.cyberpeace.space.controller;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.*;
import com.google.common.collect.Lists;
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

@WebMvcTest(SpaceTacticDefenseController.class)
class SpaceTacticDefenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceTacticDefenseService mockDefenseService;

    @Test
    void testListAttack() throws Exception {
        // Setup
        // Configure SpaceTacticDefenseService.page(...).
        final DefenseListBo defenseListBo = new DefenseListBo();
        defenseListBo.setResourceId("resourceId");
        defenseListBo.setName("name");
        defenseListBo.setTacticId("tacticId");
        defenseListBo.setCategoryId("categoryId");
        defenseListBo.setCategory("category");
        final ListRsp<DefenseListBo> defenseListBoListRsp = new ListRsp<>(Arrays.asList(defenseListBo), 0L, 0L, 0L);
        final SpaceTacticPageReq reqBase = new SpaceTacticPageReq();
        reqBase.setPage(0);
        reqBase.setPerPage(0);
        reqBase.setSort("sort");
        when(mockDefenseService.page(reqBase)).thenReturn(defenseListBoListRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense/page")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"resourceId\",\"name\":\"name\",\"tacticId\":\"tacticId\",\"categoryId\":\"categoryId\",\"category\":\"category\",\"description\":null,\"scene\":null,\"tool\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        // Run the test and verify the results
        DefenseAddReq defenseAddReq = new DefenseAddReq();
        defenseAddReq.setName("name");
        mockMvc.perform(post("/space/tactic/defense")
                        .content(JsonUtil.toJsonString(defenseAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockDefenseService).add(any(DefenseAddReq.class));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        // Run the test and verify the results
        DefenseModifyReq defenseModifyReq = new DefenseModifyReq();
        defenseModifyReq.setResourceId("resourceId");
        defenseModifyReq.setName("name");
        mockMvc.perform(patch("/space/tactic/defense")
                        .content(JsonUtil.toJsonString(defenseModifyReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockDefenseService).updateDefense(any(DefenseModifyReq.class));
    }

    @Test
    void testDeleteA() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq idListReq = new IdListReq();
        idListReq.setIds(Lists.newArrayList("values"));
        mockMvc.perform(delete("/space/tactic/defense")
                        .content(JsonUtil.toJsonString(idListReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Configure SpaceTacticDefenseService.getDetail(...).
        final DefenseDetail defenseDetail = new DefenseDetail();
        defenseDetail.setResourceId("resourceId");
        defenseDetail.setName("name");
        defenseDetail.setTacticId("tacticId");
        defenseDetail.setCategoryId("categoryId");
        defenseDetail.setCategoryName("categoryName");
        when(mockDefenseService.getDetail("resource_id")).thenReturn(defenseDetail);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"name\":\"name\",\"tacticId\":\"tacticId\",\"categoryId\":\"categoryId\",\"categoryName\":\"categoryName\",\"role\":null,\"tag\":null,\"description\":null,\"scene\":null,\"monitor\":null,\"analyze\":null,\"tool\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPageC() throws Exception {
        // Setup
        // Configure SpaceTacticDefenseService.pageCategory(...).
        final TacticDefenseCategoryBo tacticDefenseCategoryBo = new TacticDefenseCategoryBo();
        tacticDefenseCategoryBo.setResourceId("resourceId");
        tacticDefenseCategoryBo.setName("name");
        tacticDefenseCategoryBo.setStage(0);
        tacticDefenseCategoryBo.setCover("cover");
        final ListRsp<TacticDefenseCategoryBo> tacticDefenseCategoryBoListRsp = new ListRsp<>(
                Arrays.asList(tacticDefenseCategoryBo), 0L, 0L, 0L);
        final PageReqBase reqBase = new PageReqBase();
        reqBase.setPage(0);
        reqBase.setPerPage(0);
        reqBase.setSort("sort");
        when(mockDefenseService.pageCategory(reqBase)).thenReturn(tacticDefenseCategoryBoListRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense/category/page")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"resourceId\",\"name\":\"name\",\"stage\":0,\"cover\":\"cover\"}],\"total\":0,\"lastPage\":0,\"currentPage\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddC() throws Exception {
        // Setup
        // Run the test and verify the results
        TacticDefenseCategoryAddReq tacticDefenseCategoryAddReq = new TacticDefenseCategoryAddReq();
        tacticDefenseCategoryAddReq.setCover("resourceId");
        tacticDefenseCategoryAddReq.setStage(0);
        tacticDefenseCategoryAddReq.setName("name");
        mockMvc.perform(post("/space/tactic/defense/category")
                        .content(JsonUtil.toJsonString(tacticDefenseCategoryAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockDefenseService).addDefenseCategory(any(TacticDefenseCategoryAddReq.class));
    }

    @Test
    void testUpdateC() throws Exception {
        // Setup
        // Run the test and verify the results
        TacticDefenseCategoryModifyReq tacticDefenseCategoryModifyReq = new TacticDefenseCategoryModifyReq();
        tacticDefenseCategoryModifyReq.setResourceId("resource_id");
        tacticDefenseCategoryModifyReq.setName("name");
        tacticDefenseCategoryModifyReq.setStage(0);
        mockMvc.perform(patch("/space/tactic/defense/category")
                        .content(JsonUtil.toJsonString(tacticDefenseCategoryModifyReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockDefenseService).updateDefenseCategory(any(TacticDefenseCategoryModifyReq.class));
    }

    @Test
    void testDeleteC() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq idListReq = new IdListReq();
        idListReq.setIds(Lists.newArrayList("values"));
        mockMvc.perform(delete("/space/tactic/defense/category")
                        .content(JsonUtil.toJsonString(idListReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetCDetail() throws Exception {
        // Setup
        // Configure SpaceTacticDefenseService.getCategoryDetail(...).
        final DefenseCategoryBo defenseCategoryBo = new DefenseCategoryBo();
        defenseCategoryBo.setResourceId("resourceId");
        defenseCategoryBo.setStage(0);
        defenseCategoryBo.setCover("cover");
        when(mockDefenseService.getCategoryDetail("resource_id")).thenReturn(defenseCategoryBo);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense/category")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"stage\":0,\"cover\":\"cover\"},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testListC() throws Exception {
        // Setup
        // Configure SpaceTacticDefenseService.listCategory(...).
        final TacticDefenseCategoryBo tacticDefenseCategoryBo = new TacticDefenseCategoryBo();
        tacticDefenseCategoryBo.setResourceId("resourceId");
        tacticDefenseCategoryBo.setName("name");
        tacticDefenseCategoryBo.setStage(0);
        tacticDefenseCategoryBo.setCover("cover");
        final List<TacticDefenseCategoryBo> tacticDefenseCategoryBos = Arrays.asList(tacticDefenseCategoryBo);
        when(mockDefenseService.listCategory()).thenReturn(tacticDefenseCategoryBos);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense/category/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"stage\":1,\"categories\":[]},{\"stage\":2,\"categories\":[]},{\"stage\":3,\"categories\":[]}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testListC_SpaceTacticDefenseServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockDefenseService.listCategory()).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense/category/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"stage\":1,\"categories\":[]},{\"stage\":2,\"categories\":[]},{\"stage\":3,\"categories\":[]}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testListByCategory() throws Exception {
        // Setup
        // Configure SpaceTacticDefenseService.listByCategoryId(...).
        final DefenseDetail defenseDetail = new DefenseDetail();
        defenseDetail.setResourceId("resourceId");
        defenseDetail.setName("name");
        defenseDetail.setTacticId("tacticId");
        defenseDetail.setCategoryId("categoryId");
        defenseDetail.setCategoryName("categoryName");
        final List<DefenseDetail> defenseDetails = Arrays.asList(defenseDetail);
        when(mockDefenseService.listByCategoryId("category_id")).thenReturn(defenseDetails);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense/list")
                        .param("category_id", "category_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"resourceId\":\"resourceId\",\"name\":\"name\",\"tacticId\":\"tacticId\",\"categoryId\":\"categoryId\",\"categoryName\":\"categoryName\",\"role\":null,\"tag\":null,\"description\":null,\"scene\":null,\"monitor\":null,\"analyze\":null,\"tool\":null}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testListByCategory_SpaceTacticDefenseServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockDefenseService.listByCategoryId("category_id")).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense/list")
                        .param("category_id", "category_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetDefenseFrame() throws Exception {
        // Setup
        // Configure SpaceTacticDefenseService.getFrame(...).
        final SpaceFrameLevel spaceFrameLevel = new SpaceFrameLevel("resourceId", "name");
        when(mockDefenseService.getFrame()).thenReturn(spaceFrameLevel);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/defense/frame")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
