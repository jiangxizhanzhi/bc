package com.cyberpeace.space.controller;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.service.SpaceTacticAttackService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.tactic.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceTacticAttackController.class)
class SpaceTacticAttackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceTacticAttackService mockAttackService;

    @Test
    void testListAttack() throws Exception {
        // Setup
        // Configure SpaceTacticAttackService.pageAttack(...).
        final AttackListBo attackListBo = new AttackListBo();
        attackListBo.setResourceId("resourceId");
        attackListBo.setName("name");
        attackListBo.setTacticId("tacticId");
        attackListBo.setCategoryId("categoryId");
        attackListBo.setCategory("category");
        final ListRsp<AttackListBo> attackListBoListRsp = new ListRsp<>(Arrays.asList(attackListBo), 0L, 0L, 0L);
        final SpaceTacticPageReq req = new SpaceTacticPageReq();
        req.setPage(0);
        req.setPerPage(0);
        req.setSort("sort");
        when(mockAttackService.pageAttack(req)).thenReturn(attackListBoListRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/attack/page")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"resourceId\",\"name\":\"name\",\"tacticId\":\"tacticId\",\"categoryId\":\"categoryId\",\"category\":\"category\",\"description\":null,\"scene\":null,\"feature\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        // Run the test and verify the results
        AttackAddReq attackAddReq = new AttackAddReq();
        attackAddReq.setSystemId("tactic_id");
        mockMvc.perform(post("/space/tactic/attack")
                        .content(JsonUtil.toJsonString(attackAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockAttackService).addAttack(any(AttackAddReq.class));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        // Run the test and verify the results
        AttackModifyReq attackModifyReq = new AttackModifyReq();
        attackModifyReq.setResourceId("resource_id");
        mockMvc.perform(patch("/space/tactic/attack")
                        .content(JsonUtil.toJsonString(attackModifyReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockAttackService).updateAttack(any(AttackModifyReq.class));
    }

    @Test
    void testDeleteA() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq idListReq = new IdListReq();
        idListReq.setIds(Arrays.asList("value"));
        mockMvc.perform(delete("/space/tactic/attack")
                        .content(JsonUtil.toJsonString(idListReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockAttackService).deleteBatch(Arrays.asList("value"));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Configure SpaceTacticAttackService.getDetail(...).
        final AttackDetail attackDetail = new AttackDetail();
        attackDetail.setResourceId("resourceId");
        attackDetail.setName("name");
        attackDetail.setTacticId("tacticId");
        attackDetail.setCategoryId("categoryId");
        attackDetail.setCategoryName("categoryName");
        when(mockAttackService.getDetail("resource_id")).thenReturn(attackDetail);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/attack")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"resourceId\",\"name\":\"name\",\"tacticId\":\"tacticId\",\"categoryId\":\"categoryId\",\"categoryName\":\"categoryName\",\"role\":null,\"tag\":null,\"description\":null,\"scene\":null,\"feature\":null,\"action\":null,\"tool\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPageC() throws Exception {
        // Setup
        // Configure SpaceTacticAttackService.pageCategory(...).
        final TacticAttackCategoryBo tacticAttackCategoryBo = new TacticAttackCategoryBo();
        tacticAttackCategoryBo.setResourceId("resourceId");
        tacticAttackCategoryBo.setName("name");
        final ListRsp<TacticAttackCategoryBo> tacticAttackCategoryBoListRsp = new ListRsp<>(
                Arrays.asList(tacticAttackCategoryBo), 0L, 0L, 0L);
        final PageReqBase reqBase = new PageReqBase();
        reqBase.setPage(0);
        reqBase.setPerPage(0);
        reqBase.setSort("sort");
        when(mockAttackService.pageCategory(reqBase)).thenReturn(tacticAttackCategoryBoListRsp);

        // Run the test and verify the results
        mockMvc.perform(get("/space/tactic/attack/category/page")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"resourceId\",\"name\":\"name\"}],\"total\":0,\"lastPage\":0,\"currentPage\":0},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddC() throws Exception {
        // Setup
        // Run the test and verify the results
        TacticAttackCategoryAddReq tacticAttackCategoryAddReq = new TacticAttackCategoryAddReq();
        tacticAttackCategoryAddReq.setName("resource_id");
        mockMvc.perform(post("/space/tactic/attack/category")
                        .content(JsonUtil.toJsonString(tacticAttackCategoryAddReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockAttackService).addAttackCategory(any(TacticAttackCategoryAddReq.class));
    }

    @Test
    void testUpdateC() throws Exception {
        // Setup
        // Run the test and verify the results
        TacticAttackCategoryModifyReq tacticAttackCategoryModifyReq = new TacticAttackCategoryModifyReq();
        tacticAttackCategoryModifyReq.setResourceId("resource_id");
        mockMvc.perform(patch("/space/tactic/attack/category")
                        .content(JsonUtil.toJsonString(tacticAttackCategoryModifyReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockAttackService).updateAttackCategory(any(TacticAttackCategoryModifyReq.class));
    }

    @Test
    void testDeleteC() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq idListReq = new IdListReq();
        idListReq.setIds(Arrays.asList("value"));
        mockMvc.perform(delete("/space/tactic/attack/category")
                        .content(JsonUtil.toJsonString(idListReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(mockAttackService).deleteCategory(Arrays.asList("value"));
    }
}
