/**
 * 这段代码是一个Spring Boot测试类，用于测试`SpaceActionMethodController`控制器的功能。以下是对代码的简要分析： 
 * 1. **测试类结构**： 
 * - 类名为`SpaceActionMethodControllerTest`，继承自`WebMvcTest`注解，表明这是一个针对Web MVC层的单元测试。 
 * - 使用了`@MockBean`和`@SpyBean`注解来模拟和部分模拟依赖服务。 
 * 2. **依赖注入**： 
 * - `mockMvc`：用于执行HTTP请求并验证响应。 
 * - `spaceActionMethodController`：被测试的控制器实例。 
 * - `mockTargetService`, `mockService`, `mockTransactionTemplate`, `mockRelationService`：这些是被模拟的服务对象。 
 * 3. **测试方法**： 
 * - `testPage()`：测试分页功能。 
 * - `testListAll()`：测试列出所有记录的功能。 
 * - `testAddAttack()`：测试添加新的记录。 
 * - `testUpdateA()`：测试更新记录。 
 * - `testDeleteA()`：测试删除记录。 
 * - `testGetADetail()`：测试获取单个记录的详细信息。 
 * 4. **测试准备**： 
 * - 在`setUp()`方法中设置了各种模拟行为，确保每个测试方法都能独立运行且结果可预测。 
 * 5. **断言与验证**： 
 * - 使用`andExpect()`方法对HTTP状态码、响应内容等进行断言。 
 * - 通过`print()`方法打印请求和响应日志以便调试。 
 * 6. **数据模型**： 
 * - 使用了多个内部类或模型对象（如`SpaceActionMethodBo`, `IdListReq`）来表示请求和响应的数据结构。 
 * 7. **工具类**： 
 * - 引入了`JsonUtil`用于JSON字符串的序列化/反序列化操作。 
 * - 使用了Guava库中的`Lists.newArrayList()`创建列表。 
 * 总体而言，这段代码展示了如何使用JUnit和Mockito框架编写高效、可靠的Spring Boot控制器单元测试。每种HTTP方法（GET, POST, PATCH, DELETE）都有相应的测试用例覆盖，并且对返回的数据格式进行了精确匹配。 
**/
package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.SpaceActionTargetService;
import com.cyberpeace.space.service.context.IdListReq;
import com.cyberpeace.space.service.context.frame.SpaceActionMethodBo;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpaceActionMethodController.class)
class SpaceActionMethodControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private SpaceActionMethodController spaceActionMethodController;

    @MockBean
    private SpaceActionTargetService mockTargetService;
    
    @MockBean
    private SpaceActionMethodService mockService;
    @MockBean
    private TransactionTemplate mockTransactionTemplate;
    @MockBean
    private SpaceActionRelationService mockRelationService;

    @BeforeEach
    void setUp() {
        // Setup
        when(mockService.list()).thenReturn(Lists.newArrayList());
        when(mockRelationService.getLowerIdsByUpperIds(anyList())).thenReturn(Lists.newArrayList());
        when(mockService.getById("resource_id")).thenReturn(new SpaceActionMethod());
        when(mockService.save(any(SpaceActionMethod.class))).thenReturn(true);
        when(mockService.updateById(any(SpaceActionMethod.class))).thenReturn(true);
        Page page = new Page();
        page.setRecords(Lists.newArrayList());
        when(mockService.page(any())).thenReturn(page);
        when(mockService.page(any(), any())).thenReturn(page);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceFrameBaseTable.class);
        when(mockTransactionTemplate.execute(any())).thenReturn(null);
        doReturn(true).when(spaceActionMethodController).checkNameDuplication(any());
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_method/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testListAll() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_method/list_all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        // Run the test and verify the results
        doReturn(true).when(spaceActionMethodController).checkNameDuplication(any());
        SpaceActionMethodBo spaceActionMethodBo = new SpaceActionMethodBo();
        spaceActionMethodBo.setName("spaceActionMethodBo");
        spaceActionMethodBo.setResourceId("243b4039d2fd4225a24bb0af88db0444");
        mockMvc.perform(post("/space/action_frame/action_method")
                        .content(JsonUtil.toJsonString(spaceActionMethodBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"243b4039d2fd4225a24bb0af88db0444\",\"name\":\"spaceActionMethodBo\",\"description\":null,\"tag\":\"[]\"},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        // Run the test and verify the results
        SpaceActionMethodBo spaceActionMethodBo = new SpaceActionMethodBo();
        spaceActionMethodBo.setName("spaceActionMethodBo");
        spaceActionMethodBo.setResourceId("243b4039d2fd4225a24bb0af88db0444");
        mockMvc.perform(patch("/space/action_frame/action_method")
                        .content(JsonUtil.toJsonString(spaceActionMethodBo)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testDeleteA() throws Exception {
        // Setup
        // Run the test and verify the results
        IdListReq idListReq = new IdListReq();
        idListReq.setIds(Lists.newArrayList("111"));
        mockMvc.perform(delete("/space/action_frame/action_method")
                        .content(JsonUtil.toJsonString(idListReq)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_method")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"description\":null,\"tag\":[],\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
