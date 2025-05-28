/**
 * 这段代码是一个基于Spring Boot的单元测试类 `SpaceTacticAttackControllerTest`，用于测试 `SpaceTacticAttackController` 控制器的功能。以下是代码的主要内容和功能分析： 
 * ### 1. **测试目标** 
 * 该类主要用于测试 `SpaceTacticAttackController` 的各种HTTP接口（如`GET`、`POST`、`PATCH`、`DELETE`等），确保其行为符合预期。 
 * --- 
 * ### 2. **测试框架与依赖** 
 * - 使用了 **JUnit 5** 作为测试框架。 
 * - 使用了 **MockMvc** 来模拟HTTP请求并验证响应。 
 * - 使用了 **MockBean** 来模拟依赖的服务层（`SpaceTacticAttackService`）。 
 * - 使用了 **JsonUtil** 工具类将对象序列化为JSON字符串。 
 * --- 
 * ### 3. **主要测试方法** 
 * #### (1) **测试攻击列表接口 (`testListAttack`)** 
 * - 模拟了分页查询攻击列表的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * #### (2) **测试添加攻击接口 (`testAddAttack`)** 
 * - 模拟了添加攻击记录的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * - 验证服务层方法 `addAttack` 是否被调用。 
 * #### (3) **测试更新攻击接口 (`testUpdateA`)** 
 * - 模拟了更新攻击记录的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * - 验证服务层方法 `updateAttack` 是否被调用。 
 * #### (4) **测试删除攻击接口 (`testDeleteA`)** 
 * - 模拟了批量删除攻击记录的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * - 验证服务层方法 `deleteBatch` 是否被调用。 
 * #### (5) **测试获取攻击详情接口 (`testGetADetail`)** 
 * - 模拟了根据资源ID获取攻击详细信息的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * - 验证服务层方法 `getDetail` 是否被调用。 
 * #### (6) **测试攻击分类列表接口 (`testPageC`)** 
 * - 模拟了分页查询攻击分类的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * #### (7) **测试添加攻击分类接口 (`testAddC`)** 
 * - 模拟了添加攻击分类的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * - 验证服务层方法 `addAttackCategory` 是否被调用。 
 * #### (8) **测试更新攻击分类接口 (`testUpdateC`)** 
 * - 模拟了更新攻击分类的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * - 验证服务层方法 `updateAttackCategory` 是否被调用。 
 * #### (9) **测试删除攻击分类接口 (`testDeleteC`)** 
 * - 模拟了批量删除攻击分类的功能。 
 * - 验证返回的状态码为 `200 OK`。 
 * - 验证返回的JSON数据是否符合预期格式。 
 * - 验证服务层方法 `deleteCategory` 是否被调用。 
 * --- 
 * ### 4. **核心逻辑** 
 * - 测试类通过 `MockMvc` 模拟HTTP请求，并使用 `andExpect` 方法验证响应状态码、JSON格式以及服务层方法调用情况。 
 * - 每个测试方法都模拟了具体的业务场景，并验证了控制器的行为是否符合预期。 
 * --- 
 * ### 5. **优点** 
 * - 测试覆盖全面：涵盖了常见的HTTP方法（`GET`、`POST`、`PATCH`、`DELETE`）。 
 * - 使用了Mock技术隔离了依赖服务，确保测试独立性。 
 * - 返回结果与实际需求一致，便于快速定位问题。 
 * --- 
 * ### 6. **可能的改进建议** 
 * - 可以增加异常处理测试，例如测试接口在参数错误或服务异常时的行为。 
 * - 如果JSON数据格式复杂，可以考虑提取公共方法来减少重复代码。 
 * 总结来说，这段代码是一个结构清晰、功能完整的单元测试类，能够有效验证 `SpaceTacticAttackController` 的各项功能是否正常运行。 
**/
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
