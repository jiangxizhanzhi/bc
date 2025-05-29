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

/**
 * 这段代码是一个Spring Boot单元测试类，用于测试`SpaceAlgorithmEquipmentController`控制器的功能。以下是代码的主要分析： 
 * 1. **测试环境配置**： 
 * - 使用`@WebMvcTest`注解来加载仅与Web相关的上下文，只初始化`SpaceAlgorithmEquipmentController`及其依赖。 
 * - `@MockBean`和`@SpyBean`分别用于模拟服务层和控制器实例。 
 * 2. **测试数据准备**： 
 * - 在`setUp`方法中，通过`when`方法设置mock服务的行为，例如返回空列表、单个对象等。 
 * - 初始化了数据库表信息，并设置了控制器的方法`checkNameDuplication`的返回值为`true`。 
 * 3. **具体测试方法**： 
 * - `testPage`：测试分页功能，验证GET请求返回的状态码和JSON格式是否符合预期。 
 * - `testAddAttack`：测试添加新记录的功能，验证POST请求的响应状态和JSON内容。 
 * - `testUpdateA`：测试更新记录的功能，验证PATCH请求的响应状态和JSON内容。 
 * - `testDeleteA`：测试删除记录的功能，验证DELETE请求的响应状态和JSON内容。 
 * - `testGetADetail`：测试获取单条记录详情的功能，验证GET请求的响应状态和JSON内容。 
 * 4. **工具使用**： 
 * - 使用`MockMvc`进行HTTP请求模拟和结果断言。 
 * - 使用`JsonUtil`将Java对象转换为JSON字符串。 
 * 5. **断言方式**： 
 * - 使用`status().isOk()`验证HTTP状态码为200。 
 * - 使用`content().json()`验证返回的JSON内容是否符合预期。 
 * 总体而言，这段代码通过模拟服务层行为，全面测试了控制器的各个接口功能，确保其正确性和健壮性。 
**/
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

