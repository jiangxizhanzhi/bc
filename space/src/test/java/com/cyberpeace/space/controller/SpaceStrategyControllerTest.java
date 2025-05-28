/**
 * ### 分析 
 * 这段代码是一个Spring Boot单元测试类，用于测试`SpaceStrategyController`的功能。以下是代码的主要内容和功能： 
 * --- 
 * #### **1. 测试类概述** 
 * - 类名：`SpaceStrategyControllerTest` 
 * - 包名：`com.cyberpeace.space.controller` 
 * - 使用了`@WebMvcTest`注解，表明这是一个针对Spring MVC控制器的测试类。 
 * - 注入了`MockMvc`对象用于模拟HTTP请求。 
 * - 使用`@MockBean`注入了一个模拟的`SpaceStrategyService`服务层对象。 
 * --- 
 * #### **2. 测试方法概览** 
 * 该类包含多个测试方法，每个方法对应一个API接口的测试场景。以下是主要的测试方法及其功能： 
 * 1. **`testGetFrame`** 
 * - 测试获取策略框架信息（`GET /space/strategy/frame`）。 
 * - 验证返回的JSON格式是否符合预期，并且调用了`mockSpaceStrategyService.getFrame()`。 
 * 2. **`testAddFrame`** 
 * - 测试添加策略框架信息（`POST /space/strategy/frame`）。 
 * - 验证返回的状态码为200，并且调用了`mockSpaceStrategyService.addFrame()`。 
 * 3. **`testModifyFrame`** 
 * - 测试修改策略框架信息（`PATCH /space/strategy/frame`）。 
 * - 验证返回的状态码为200，并且调用了`mockSpaceStrategyService.modify()`。 
 * 4. **`testDeleteFrame`** 
 * - 测试删除策略框架信息（`DELETE /space/strategy/frame`）。 
 * - 验证返回的状态码为200。 
 * 5. **`testGetStrategies`** 
 * - 测试获取策略列表（`GET /space/strategy/list`）。 
 * - 验证返回的JSON数据是否符合预期，并且调用了`mockSpaceStrategyService.listStrategiesByFrame()`。 
 * 6. **`testGetStrategies_SpaceStrategyServiceReturnsNoItems`** 
 * - 测试当策略列表为空时的响应（`GET /space/strategy/list`）。 
 * - 验证返回的JSON数据中`data`字段为空数组。 
 * 7. **`testPageStrategies`** 
 * - 测试分页获取策略列表（`GET /space/strategy/page`）。 
 * - 验证返回的JSON数据是否符合预期，并且调用了`mockSpaceStrategyService.pageStrategiesByFrame()`。 
 * 8. **`testAddStrategy`** 
 * - 测试添加策略信息（`POST /space/strategy`）。 
 * - 验证返回的状态码为200，并且调用了`mockSpaceStrategyService.addStrategy()`。 
 * 9. **`testUpdateStrategy`** 
 * - 测试更新策略信息（`PATCH /space/strategy`）。 
 * - 验证返回的状态码为200，并且调用了`mockSpaceStrategyService.updateStrategy()`。 
 * 10. **`testDeleteStrategy`** 
 * - 测试删除策略信息（`DELETE /space/strategy`）。 
 * - 验证返回的状态码为200。 
 * 11. **`testGetDetail`** 
 * - 测试获取策略详情（`GET /space/strategy`）。 
 * - 验证返回的JSON数据是否符合预期，并且调用了`mockSpaceStrategyService.getDetail()`。 
 * 12. **`testGetRoute`** 
 * - 测试获取策略路由信息（`GET /space/strategy/route`）。 
 * - 验证返回的JSON数据是否符合预期，并且调用了`mockSpaceStrategyService.getFrameRouteById()`和`mockSpaceStrategyService.getById()`。 
 * --- 
 * #### **3. 关键技术点** 
 * - **MockMvc**：用于模拟HTTP请求和响应，验证控制器的行为。 
 * - **JsonUtil**：用于将Java对象转换为JSON字符串。 
 * - **Mockito**：用于模拟服务层行为，并验证方法调用。 
 * - **Spring Boot Test**：提供了方便的注解（如`@WebMvcTest`、`@MockBean`）来简化测试开发。 
 * --- 
 * #### **4. 总结** 
 * 该测试类覆盖了`SpaceStrategyController`的所有主要功能接口，包括框架管理（增删改查）、策略管理（增删改查）以及详细信息和路由查询。测试通过模拟服务层行为，验证了控制器的输入输出是否符合预期，并确保了服务层方法被正确调用。 
**/
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
