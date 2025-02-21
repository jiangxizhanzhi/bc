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
