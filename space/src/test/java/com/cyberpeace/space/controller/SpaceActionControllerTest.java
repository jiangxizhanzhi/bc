package com.cyberpeace.space.controller;

import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.service.SpaceActionMethodService;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.SpaceActionService;
import com.cyberpeace.space.service.SpaceStrategyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private SpaceActionService baseService;
    @MockBean
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void beforeEach() {
        when(baseService.getById("resource_id")).thenReturn(new SpaceAction());

    }

    @Test
    void testGetADetail() throws Exception {
        // Setup
        // Configure ToolService.listByIdsInOrder(...).
        final Tool tool = new Tool();
        tool.setResourceId("resourceId");
        tool.setName("name");
        tool.setCover("cover");
        tool.setCategory("category");
        tool.setImageResourceId("imageResourceId");
        final List<Tool> tools = Arrays.asList(tool);
        when(mockToolService.listByIdsInOrder(Arrays.asList("value"))).thenReturn(tools);

        // Configure SpaceStrategyService.listByIds(...).
        final SpaceStrategy spaceStrategy = new SpaceStrategy();
        spaceStrategy.setResourceId("resourceId");
        spaceStrategy.setName("name");
        spaceStrategy.setUserId("userId");
        spaceStrategy.setContent("content");
        spaceStrategy.setFrameId("frameId");
        final List<SpaceStrategy> spaceStrategies = Arrays.asList(spaceStrategy);
        when(mockStrategyService.listByIds(Arrays.asList("value"))).thenReturn(spaceStrategies);

        when(mockStrategyService.getFrameRouteById("frameId")).thenReturn("route");
        when(mockRelationService.getUpperIdsByLowerId("resourceId")).thenReturn(Arrays.asList("value"));

        // Configure SpaceActionMethodService.listByIds(...).
        final SpaceActionMethod spaceActionMethod = new SpaceActionMethod();
        spaceActionMethod.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        spaceActionMethod.setResourceId("resourceId");
        spaceActionMethod.setName("name");
        spaceActionMethod.setDescription("description");
        spaceActionMethod.setTag("tag");
        final List<SpaceActionMethod> spaceActionMethods = Arrays.asList(spaceActionMethod);
        when(mockMethodService.listByIds(Arrays.asList("value"))).thenReturn(spaceActionMethods);

        when(baseService.getById("resource_id")).thenReturn(new SpaceAction());

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"idNumber\":null,\"parentNumber\":null,\"platform\":[],\"description\":null,\"method\":[],\"environment\":null,\"action\":null,\"analyze\":null,\"tool\":[],\"offlineTool\":[],\"strategy\":[],\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail_ToolServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockToolService.listByIdsInOrder(Arrays.asList("value"))).thenReturn(Collections.emptyList());

        // Configure SpaceStrategyService.listByIds(...).
        final SpaceStrategy spaceStrategy = new SpaceStrategy();
        spaceStrategy.setResourceId("resourceId");
        spaceStrategy.setName("name");
        spaceStrategy.setUserId("userId");
        spaceStrategy.setContent("content");
        spaceStrategy.setFrameId("frameId");
        final List<SpaceStrategy> spaceStrategies = Arrays.asList(spaceStrategy);
        when(mockStrategyService.listByIds(Arrays.asList("value"))).thenReturn(spaceStrategies);

        when(mockStrategyService.getFrameRouteById("frameId")).thenReturn("route");
        when(mockRelationService.getUpperIdsByLowerId("resourceId")).thenReturn(Arrays.asList("value"));

        // Configure SpaceActionMethodService.listByIds(...).
        final SpaceActionMethod spaceActionMethod = new SpaceActionMethod();
        spaceActionMethod.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        spaceActionMethod.setResourceId("resourceId");
        spaceActionMethod.setName("name");
        spaceActionMethod.setDescription("description");
        spaceActionMethod.setTag("tag");
        final List<SpaceActionMethod> spaceActionMethods = Arrays.asList(spaceActionMethod);
        when(mockMethodService.listByIds(Arrays.asList("value"))).thenReturn(spaceActionMethods);
        when(baseService.getById("resource_id")).thenReturn(new SpaceAction());


        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"idNumber\":null,\"parentNumber\":null,\"platform\":[],\"description\":null,\"method\":[],\"environment\":null,\"action\":null,\"analyze\":null,\"tool\":[],\"offlineTool\":[],\"strategy\":[],\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail_SpaceStrategyServiceListByIdsReturnsNoItems() throws Exception {
        // Setup
        // Configure ToolService.listByIdsInOrder(...).
        final Tool tool = new Tool();
        tool.setResourceId("resourceId");
        tool.setName("name");
        tool.setCover("cover");
        tool.setCategory("category");
        tool.setImageResourceId("imageResourceId");
        final List<Tool> tools = Arrays.asList(tool);
        when(mockToolService.listByIdsInOrder(Arrays.asList("value"))).thenReturn(tools);

        when(mockStrategyService.listByIds(Arrays.asList("value"))).thenReturn(Collections.emptyList());
        when(mockRelationService.getUpperIdsByLowerId("resourceId")).thenReturn(Arrays.asList("value"));

        // Configure SpaceActionMethodService.listByIds(...).
        final SpaceActionMethod spaceActionMethod = new SpaceActionMethod();
        spaceActionMethod.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        spaceActionMethod.setResourceId("resourceId");
        spaceActionMethod.setName("name");
        spaceActionMethod.setDescription("description");
        spaceActionMethod.setTag("tag");
        final List<SpaceActionMethod> spaceActionMethods = Arrays.asList(spaceActionMethod);
        when(mockMethodService.listByIds(Arrays.asList("value"))).thenReturn(spaceActionMethods);

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"idNumber\":null,\"parentNumber\":null,\"platform\":[],\"description\":null,\"method\":[],\"environment\":null,\"action\":null,\"analyze\":null,\"tool\":[],\"offlineTool\":[],\"strategy\":[],\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail_SpaceActionRelationServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure ToolService.listByIdsInOrder(...).
        final Tool tool = new Tool();
        tool.setResourceId("resourceId");
        tool.setName("name");
        tool.setCover("cover");
        tool.setCategory("category");
        tool.setImageResourceId("imageResourceId");
        final List<Tool> tools = Arrays.asList(tool);
        when(mockToolService.listByIdsInOrder(Arrays.asList("value"))).thenReturn(tools);

        // Configure SpaceStrategyService.listByIds(...).
        final SpaceStrategy spaceStrategy = new SpaceStrategy();
        spaceStrategy.setResourceId("resourceId");
        spaceStrategy.setName("name");
        spaceStrategy.setUserId("userId");
        spaceStrategy.setContent("content");
        spaceStrategy.setFrameId("frameId");
        final List<SpaceStrategy> spaceStrategies = Arrays.asList(spaceStrategy);
        when(mockStrategyService.listByIds(Arrays.asList("value"))).thenReturn(spaceStrategies);

        when(mockStrategyService.getFrameRouteById("frameId")).thenReturn("route");
        when(mockRelationService.getUpperIdsByLowerId("resourceId")).thenReturn(Collections.emptyList());

        // Configure SpaceActionMethodService.listByIds(...).
        final SpaceActionMethod spaceActionMethod = new SpaceActionMethod();
        spaceActionMethod.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        spaceActionMethod.setResourceId("resourceId");
        spaceActionMethod.setName("name");
        spaceActionMethod.setDescription("description");
        spaceActionMethod.setTag("tag");
        final List<SpaceActionMethod> spaceActionMethods = Arrays.asList(spaceActionMethod);
        when(mockMethodService.listByIds(Arrays.asList("value"))).thenReturn(spaceActionMethods);

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"idNumber\":null,\"parentNumber\":null,\"platform\":[],\"description\":null,\"method\":[],\"environment\":null,\"action\":null,\"analyze\":null,\"tool\":[],\"offlineTool\":[],\"strategy\":[],\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }

    @Test
    void testGetADetail_SpaceActionMethodServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure ToolService.listByIdsInOrder(...).
        final Tool tool = new Tool();
        tool.setResourceId("resourceId");
        tool.setName("name");
        tool.setCover("cover");
        tool.setCategory("category");
        tool.setImageResourceId("imageResourceId");
        final List<Tool> tools = Arrays.asList(tool);
        when(mockToolService.listByIdsInOrder(Arrays.asList("value"))).thenReturn(tools);

        // Configure SpaceStrategyService.listByIds(...).
        final SpaceStrategy spaceStrategy = new SpaceStrategy();
        spaceStrategy.setResourceId("resourceId");
        spaceStrategy.setName("name");
        spaceStrategy.setUserId("userId");
        spaceStrategy.setContent("content");
        spaceStrategy.setFrameId("frameId");
        final List<SpaceStrategy> spaceStrategies = Arrays.asList(spaceStrategy);
        when(mockStrategyService.listByIds(Arrays.asList("value"))).thenReturn(spaceStrategies);

        when(mockStrategyService.getFrameRouteById("frameId")).thenReturn("route");
        when(mockRelationService.getUpperIdsByLowerId("resourceId")).thenReturn(Arrays.asList("value"));
        when(mockMethodService.listByIds(Arrays.asList("value"))).thenReturn(Collections.emptyList());

        // Run the test and verify the results
        mockMvc.perform(get("/space/action_frame/action")
                        .param("resource_id", "resource_id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"resourceId\":null,\"name\":null,\"type\":null,\"idNumber\":null,\"parentNumber\":null,\"platform\":[],\"description\":null,\"method\":[],\"environment\":null,\"action\":null,\"analyze\":null,\"tool\":[],\"offlineTool\":[],\"strategy\":[],\"createTime\":null},\"code\":\"OJ-000000\",\"message\":null,\"detail\":null}", true));
    }
}
