package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.dao.entity.SpaceActionRelation;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.dao.mapper.SpaceActionMapper;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.SpaceActionService;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.frame.SpaceActionOfflineTool;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceActionController.class)
class SpaceActionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceActionMethodService mockMethodService;
    @MockBean
    private ToolService mockToolService;
    @MockBean
    private SpaceStrategyService mockStrategyService;
    @MockBean
    private SpaceActionRelationService mockRelationService;
    @MockBean
    private SpaceActionService mockService;
    @MockBean
    private TransactionTemplate transactionTemplate;

    @MockBean
    private SpaceActionMapper baseMapper;

    @BeforeEach
    void beforeEach() {
        when(mockService.getOne(any())).thenReturn(null);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
                SpaceFrameBaseTable.class);
    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Configure ToolService.listByIdsInOrder(...).
        final Tool tool = new Tool();
        tool.setResourceId("tool1");
        tool.setName("name");
        tool.setCover("cover");
        tool.setCategory("category");
        tool.setImageResourceId("imageResourceId");
        final List<Tool> tools = Arrays.asList(tool);
        when(mockToolService.listByIdsInOrder(Arrays.asList("tool1"))).thenReturn(tools);

        // Configure SpaceStrategyService.listByIds(...).
        final SpaceStrategy spaceStrategy = new SpaceStrategy();
        spaceStrategy.setResourceId("strategy1");
        spaceStrategy.setName("name");
        spaceStrategy.setUserId("userId");
        spaceStrategy.setContent("content");
        spaceStrategy.setFrameId("frameId");
        final List<SpaceStrategy> spaceStrategies = Arrays.asList(spaceStrategy);
        when(mockStrategyService.listByIds(Arrays.asList("strategy1"))).thenReturn(spaceStrategies);

        when(mockStrategyService.getFrameRouteById("frameId")).thenReturn("route");
        when(mockRelationService.getUpperIdsByLowerId("action1")).thenReturn(Arrays.asList("method1"));

        // Configure SpaceActionMethodService.listByIds(...).
        final SpaceActionMethod spaceActionMethod = new SpaceActionMethod();
        spaceActionMethod.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        spaceActionMethod.setResourceId("method1");
        spaceActionMethod.setName("name");
        spaceActionMethod.setDescription("description");
        spaceActionMethod.setTag("tag");
        final List<SpaceActionMethod> spaceActionMethods = Arrays.asList(spaceActionMethod);
        when(mockMethodService.listByIds(Arrays.asList("method1"))).thenReturn(spaceActionMethods);

        SpaceAction action = new SpaceAction();
        action.setResourceId("action1");
        action.setPlatform("[\"windows\"]");
        action.setOfflineTool(JsonUtil.arrayToString(Lists.newArrayList(new SpaceActionOfflineTool())));
        action.setDescription("desc");
        action.setTool("[\"tool1\"]");
        action.setStrategy("[\"strategy1\"]");

        when(mockService.getById("action1")).thenReturn(action);

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action")
                        .param("resource_id", "action1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":\"action1\",\"name\":null,\"type\":null,\"idNumber\":null,\"parentNumber\":null,\"platform\":[\"windows\"],\"description\":\"desc\",\"method\":[{\"resourceId\":\"method1\",\"name\":\"name\"}],\"environment\":null,\"action\":null,\"analyze\":null,\"tool\":[{\"resourceId\":\"tool1\",\"name\":\"name\",\"introduction\":null,\"platforms\":null}],\"offlineTool\":[{\"name\":\"\",\"introduction\":\"\"}],\"strategy\":[{\"resourceId\":\"strategy1\",\"name\":\"name\",\"route\":\"route\",\"content\":\"content\"}],\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testAddAttack() throws Exception {
        // Setup
        when(baseMapper.insert(any())).thenReturn(1);
        doNothing().when(mockRelationService).createRelation(eq(Lists.newArrayList("b3d0af4ab79f4fcca8707c812d4a2a25")), any());
        String req = "{\"name\":\"newAction\",\"description\":\"newAction desc\",\"id_number\":\"\",\"method\":[{\"name\":\"学习与改进\",\"resource_id\":\"b3d0af4ab79f4fcca8707c812d4a2a25\"}],\"platform\":[],\"environment\":\"1111\",\"action\":\"\",\"analyze\":\"2222\",\"tool\":[],\"strategy\":[],\"offline_tool\":[{\"name\":\"test-tool\",\"introduction\":\"2222\"}]}";
        // Run the test and verify the results
        mockMvc.perform(post("/space/action_frame/action")
                        .content(req).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"name\":\"newAction\",\"type\":null,\"description\":\"newAction desc\",\"idNumber\":null,\"parentNumber\":null,\"platform\":\"[]\",\"environment\":\"1111\",\"action\":\"\",\"analyze\":\"2222\",\"tool\":\"[]\",\"offlineTool\":\"[]\",\"strategy\":\"[]\"},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", false));
    }

    @Test
    void testDeleteA() throws Exception {
        // Setup
        List<String> ids = Lists.newArrayList("action1", "action2");
        String req = "{\"ids\":[\"action1\", \"action2\"]}";
        when(mockService.removeByIds(ids)).thenReturn(true);
        doNothing().when(mockRelationService).deleteRelationsByIds(ids);
        // Run the test and verify the results
        mockMvc.perform(delete("/space/action_frame/action")
                        .content(req).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testListAll() throws Exception {
        // Setup
        SpaceAction action = new SpaceAction();
        action.setResourceId("action1");
        action.setPlatform("[\"windows\"]");
        action.setOfflineTool(JsonUtil.arrayToString(Lists.newArrayList(new SpaceActionOfflineTool())));
        action.setDescription("desc");
        action.setTool("[\"tool1\"]");
        action.setStrategy("[\"strategy1\"]");

        when(mockService.list()).thenReturn(Lists.newArrayList(action));
        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action/list_all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"resourceId\":\"action1\",\"name\":null,\"type\":null,\"idNumber\":null,\"parentNumber\":null,\"platform\":[\"windows\"],\"description\":\"desc\",\"method\":null,\"environment\":null,\"action\":null,\"analyze\":null,\"tool\":null,\"offlineTool\":[{\"name\":\"\",\"introduction\":\"\"}],\"strategy\":null,\"createTime\":null}],\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testPage() throws Exception {
        // Setup
        // Configure SpaceActionMethodService.listByIds(...).
        final SpaceActionMethod method = new SpaceActionMethod();
        method.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        method.setResourceId("method1");
        method.setName("name");
        method.setDescription("description");
        method.setTag("tag");
        final List<SpaceActionMethod> methods = Arrays.asList(method);
        when(mockMethodService.listByIds(Arrays.asList("method1"))).thenReturn(methods);

        final PageReqBase req = new PageReqBase();
        IPage<SpaceAction> page = new Page<>();
        SpaceAction action = new SpaceAction();
        action.setResourceId("action1");
        action.setPlatform("[\"windows\"]");
        action.setOfflineTool(JsonUtil.arrayToString(Lists.newArrayList(new SpaceActionOfflineTool())));
        action.setDescription("desc");
        action.setTool("[\"tool1\"]");
        action.setStrategy("[\"strategy1\"]");
        page.setRecords(Lists.newArrayList(action));

        when(mockService.page(any(), any())).thenReturn(page);
        SpaceActionRelation relation =  new SpaceActionRelation();
        relation.setUpperId("method1");
        relation.setLowerId("action1");
        when(mockRelationService.list(any())).thenReturn(Lists.newArrayList(relation));

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"list\":[{\"resourceId\":\"action1\",\"name\":null,\"type\":null,\"idNumber\":null,\"parentNumber\":null,\"platform\":[\"windows\"],\"description\":\"desc\",\"method\":[{\"resourceId\":\"method1\",\"name\":\"name\"}],\"environment\":null,\"action\":null,\"analyze\":null,\"tool\":null,\"offlineTool\":[{\"name\":\"\",\"introduction\":\"\"}],\"strategy\":null,\"createTime\":null}],\"total\":0,\"lastPage\":0,\"currentPage\":1},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testUpdateA() throws Exception {
        // Setup
        String req = "{\"resourceId\":\"action2\", \"name\":\"newAction\",\"description\":\"newAction desc\",\"id_number\":\"\",\"method\":[{\"name\":\"学习与改进\",\"resource_id\":\"b3d0af4ab79f4fcca8707c812d4a2a25\"}],\"platform\":[],\"environment\":\"1111\",\"action\":\"\",\"analyze\":\"2222\",\"tool\":[],\"strategy\":[],\"offline_tool\":[{\"name\":\"test-tool\",\"introduction\":\"2222\"}]}";
        when(mockService.updateById(any())).thenReturn(true);
        doNothing().when(mockRelationService).updateRelation(Lists.newArrayList("b3d0af4ab79f4fcca8707c812d4a2a25"), "action2");
        // Run the test and verify the results
        mockMvc.perform(patch("/space/action_frame/action")
                        .content(req).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":null,\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
