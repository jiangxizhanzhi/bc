package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameMapper;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.SpaceActionService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.google.common.collect.Lists;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 这段代码是一个单元测试类，用于测试`SpaceActionFrameController`的功能。以下是代码的主要内容和功能分析： 
 * 1. **测试环境设置**： 
 * - 使用了Spring Boot的`@WebMvcTest`注解来加载仅包含Web层的上下文。 
 * - 使用了`@MockBean`注解来模拟依赖的服务和Mapper。 
 * 2. **测试方法**： 
 * - `testGetFrame`：测试获取框架信息的功能，包括嵌套的子资源（方法和动作）。 
 * - `testAddAttack`：测试添加新的框架信息，并验证事务处理。 
 * - `testDeleteA`：测试删除多个框架信息的功能。 
 * - `testListAll`：测试列出所有框架信息的功能。 
 * - `testPage`：测试分页查询框架信息的功能。 
 * - `testUpdateA`：测试更新框架信息的功能。 
 * - `testSaveFrame`：测试保存框架信息的功能。 
 * 3. **工具和库**： 
 * - 使用了`MockMvc`来进行HTTP请求的模拟和响应的验证。 
 * - 使用了`JsonUtil`进行JSON字符串与对象之间的转换。 
 * - 使用了`MybatisConfiguration`和`MapperBuilderAssistant`来初始化表信息。 
 * 4. **测试数据准备**： 
 * - 每个测试方法都设置了相应的模拟行为（使用`when`），并创建了必要的测试数据对象。 
 * 5. **断言**： 
 * - 使用`andExpect`方法来验证HTTP状态码、响应内容等是否符合预期。 
 * 6. **事务管理**： 
 * - 每个涉及写操作的测试方法都验证了事务模板`transactionTemplate`的执行。 
 * 总结来说，这段代码通过JUnit和Mockito框架，全面地测试了`SpaceActionFrameController`的各种功能，确保其在不同场景下的正确性。 
**/
@WebMvcTest(SpaceActionFrameController.class)
class SpaceActionFrameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceActionFrameService baseService;

    @MockBean
    private TransactionTemplate transactionTemplate;

    @MockBean
    private SpaceActionMethodService methodService;

    @MockBean
    private SpaceActionService actionService;

    @MockBean
    private SpaceActionFrameMapper baseMapper;
    @MockBean
    private SpaceActionRelationService mockRelationService;

    @BeforeEach
    void beforeEach() {
        when(baseService.getOne(any())).thenReturn(null);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceFrameBaseTable.class);
    }

    @Test
    void testGetFrame() throws Exception {
        // Setup
        SpaceActionFrame frame = new SpaceActionFrame();
        frame.setResourceId("frame1");
        frame.setFrame("{\n" +
                "  \"resourceId\": \"frame1\",\n" +
                "  \"name\": \"frame1Name\",\n" +
                "  \"children\": [\n" +
                "    {\n" +
                "      \"resourceId\": \"method1\",\n" +
                "      \"name\": \"method1Name\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"resourceId\": \"action1\",\n" +
                "          \"name\": \"action1Name\",\n" +
                "          \"children\": []\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        doReturn(frame).when(baseService).getById("frame1");

        SpaceAction action = new SpaceAction();
        action.setResourceId("action1");
        action.setName("action1Name");

        SpaceActionMethod method = new SpaceActionMethod();
        method.setResourceId("method1");
        method.setName("method1Name");

        when(methodService.listByIds(Lists.newArrayList("method1"))).thenReturn(Lists.newArrayList(method));
        when(actionService.listByIds(Lists.newArrayList("action1"))).thenReturn(Lists.newArrayList(action));

        when(baseService.getFrameLevels("frame1")).thenReturn(JsonUtil.toJsonObject(frame.getFrame(),SpaceFrameLevel.class));
        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_frame/frame")
                        .param("resource_id", "frame1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"frame1\",\"name\":\"frame1Name\",\"children\":[{\"resourceId\":\"method1\",\"name\":\"method1Name\",\"children\":[{\"resourceId\":\"action1\",\"name\":\"action1Name\",\"children\":[]}]}]},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        when(baseMapper.insert(any())).thenReturn(1);
        doNothing().when(mockRelationService).createRelation(any(), any());
        SpaceActionFrameBo bo = new SpaceActionFrameBo();
        bo.setName("frameName1");
        bo.setResourceId("frame1");
        bo.setTag(Lists.newArrayList("111", "222"));
        String req = JsonUtil.toJsonString(bo);
        // Run the test and verify the results
        mockMvc.perform(post("/space/action_frame/action_frame")
                        .content(req).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"frame1\",\"name\":\"frameName1\",\"type\":null,\"role\":null,\"attack\":null,\"defense\":null,\"tag\":\"[\\\"111\\\",\\\"222\\\"]\",\"topo\":null,\"description\":null,\"frame\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(transactionTemplate).execute(any());
    }

    @Test
    void testDeleteA() throws Exception {
        // Setup
        List<String> ids = Lists.newArrayList("frame1", "frame2");
        String req = "{\"ids\":[\"frame1\", \"frame2\"]}";
        when(baseService.removeByIds(ids)).thenReturn(true);
        doNothing().when(mockRelationService).deleteRelationsByIds(ids);
        // Run the test and verify the results
        mockMvc.perform(delete("/space/action_frame/action_frame")
                        .content(req).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(transactionTemplate).execute(any());
    }

    @Test
    void testListAll() throws Exception {
        // Setup
        SpaceActionFrame frame = new SpaceActionFrame();
        frame.setResourceId("frame1");
        frame.setName("frameName1");
        when(baseService.list()).thenReturn(Lists.newArrayList(frame));
        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_frame/list_all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"resourceId\":\"frame1\",\"name\":\"frameName1\",\"type\":null,\"role\":null,\"attack\":null,\"defense\":null,\"tag\":[],\"topo\":null,\"description\":null}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage() throws Exception {
        // Setup
        final PageReqBase req = new PageReqBase();
        IPage<SpaceActionFrame> page = new Page<>();
        SpaceActionFrame frame = new SpaceActionFrame();
        frame.setResourceId("frame1");
        frame.setName("frameName1");
        page.setRecords(Lists.newArrayList(frame));

        when(baseService.page(any(), any())).thenReturn(page);
        when(mockRelationService.list(any())).thenReturn(Lists.newArrayList());
        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action_frame/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"frame1\",\"name\":\"frameName1\",\"type\":null,\"role\":null,\"attack\":null,\"defense\":null,\"tag\":[],\"topo\":null,\"description\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        SpaceActionFrameBo bo = new SpaceActionFrameBo();
        bo.setName("frameName1");
        bo.setResourceId("frame1");
        bo.setTag(Lists.newArrayList("111", "222"));
        String req = JsonUtil.toJsonString(bo);
        when(baseService.updateById(any())).thenReturn(true);
        // Run the test and verify the results
        mockMvc.perform(patch("/space/action_frame/action_frame")
                        .content(req).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
        verify(transactionTemplate).execute(any());
    }

    @Test
    void testSaveFrame() throws Exception {
        // Setup
        // Run the test and verify the results
        when(baseService.saveFrame(any(SpaceFrameLevel.class))).thenReturn(true);
        mockMvc.perform(post("/space/action_frame/action_frame/frame")
                        .content(JsonUtil.toJsonString(new SpaceFrameLevel())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(baseService).saveFrame(any(SpaceFrameLevel.class));
    }
}

