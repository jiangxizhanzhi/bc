package com.cyberpeace.cr.teamtrain.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.utils.BaseCommon;
import com.cyberpeace.cr.crbase.utils.JsonCommon;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateCopyReq;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cs.service.LocalSceneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SingleTrainPublicationTest {
    private static final String MISSION = "11111111111111111111111111111111";
    private TeamTrainTemplateController controller;
    private BusinessTemplateService businesses;
    private MissionTemplateService missions;
    private SingleTemplateService singles;
    private LocalSceneService scenes;
    private BusinessTemplate source;
    private TeamTemplate team;
    private MissionTemplate mission;
    private TeamTrainTemplateCopyReq request;

    @BeforeEach
    void setUp() throws Exception {
        ReflectionTestUtils.setField(ConfigUtil.class, "ctxPropertiesMap",
                Collections.singletonMap(ErrorCode.DIRECT_MSG_EXCEPTION + ".cn", "%s"));
        Constructor<BaseCommon> baseConstructor = BaseCommon.class.getDeclaredConstructor();
        baseConstructor.setAccessible(true);
        BaseCommon base = baseConstructor.newInstance();
        Constructor<JsonCommon> jsonConstructor = JsonCommon.class.getDeclaredConstructor(BaseCommon.class);
        jsonConstructor.setAccessible(true);
        jsonConstructor.newInstance(base);
        controller = new TeamTrainTemplateController();
        businesses = mock(BusinessTemplateService.class);
        missions = mock(MissionTemplateService.class);
        singles = mock(SingleTemplateService.class);
        scenes = mock(LocalSceneService.class);
        TeamTemplateService teams = mock(TeamTemplateService.class);
        ReflectionTestUtils.setField(controller, "businessTemplateService", businesses);
        ReflectionTestUtils.setField(controller, "missionTemplateService", missions);
        ReflectionTestUtils.setField(controller, "singleTemplateService", singles);
        ReflectionTestUtils.setField(controller, "localSceneService", scenes);
        ReflectionTestUtils.setField(controller, "teamTemplateService", teams);
        ReflectionTestUtils.setField(controller, "transactionTemplate",
                new TransactionTemplate(mock(PlatformTransactionManager.class)));
        source = new BusinessTemplate();
        source.setResourceId("source");
        source.setTopoId("source-topo");
        source.setLocalSceneId("source-test");
        source.setName("Source scene");
        team = new TeamTemplate();
        team.setResourceId("team");
        team.setBusinessTemplateId("source");
        team.setRoleConfig("[{\"missions_scheme\":{\"color\":2,\"template\":3,\"scheme_data\":[{\"stage\":\"Stage\",\"dynamic\":[\"" + MISSION + "\"],\"missions\":[{\"resource_id\":\"" + MISSION + "\"}]}]}}]");
        mission = new MissionTemplate();
        mission.setResourceId(MISSION);
        mission.setBusinessTemplateId("source");
        mission.setTitle("Latest title");
        mission.setDescription("Latest content");
        mission.setAnswer("Latest answer");
        mission.setScore(90);
        mission.setAttachment("[\"manual.pdf\"]");
        mission.setSkillPoints("skill");
        when(teams.getByTemplateBusinessId("source")).thenReturn(team);
        when(businesses.getById("source")).thenReturn(source);
        when(missions.getMappingByIds(Collections.singletonList(MISSION))).thenReturn(Collections.singletonMap(MISSION, mission));
        when(missions.getMappingByIds(Collections.emptyList())).thenReturn(Collections.emptyMap());
        when(scenes.copyTopo("source-topo", "Source scene")).thenReturn("copied-topo");
        when(missions.save(any(MissionTemplate.class))).thenAnswer(call -> {
            call.<MissionTemplate>getArgument(0).setResourceId(UUID.randomUUID().toString().replace("-", ""));
            return true;
        });
        request = new TeamTrainTemplateCopyReq();
        request.setResourceId("source");
    }

    @Test
    void publishingCopiesLatestTaskContentAndTopologyWithoutSharingSourceIds() {
        controller.copyToSingleTrain(request);
        ArgumentCaptor<BusinessTemplate> business = ArgumentCaptor.forClass(BusinessTemplate.class);
        verify(businesses).save(business.capture());
        assertEquals("source_single", business.getValue().getResourceId());
        assertEquals("copied-topo", business.getValue().getTopoId());
        assertNull(business.getValue().getLocalSceneId());
        ArgumentCaptor<MissionTemplate> task = ArgumentCaptor.forClass(MissionTemplate.class);
        verify(missions).save(task.capture());
        MissionTemplate copy = task.getValue();
        assertNotEquals(MISSION, copy.getResourceId());
        assertEquals("source_single", copy.getBusinessTemplateId());
        assertEquals("Latest answer", copy.getAnswer());
        assertEquals(mission.getAttachment(), copy.getAttachment());
        assertEquals(mission.getSkillPoints(), copy.getSkillPoints());
        assertEquals(Integer.valueOf(90), copy.getScore());
        ArgumentCaptor<SingleTemplate> single = ArgumentCaptor.forClass(SingleTemplate.class);
        verify(singles).save(single.capture());
        String scheme = single.getValue().getMissionsScheme();
        assertTrue(scheme.contains(copy.getResourceId()));
        assertFalse(scheme.contains(MISSION));
        assertTrue(team.getRoleConfig().contains(MISSION));
        assertEquals("source-topo", source.getTopoId());
        assertEquals("source-test", source.getLocalSceneId());
        mission.setAnswer("Subsequent edit");
        assertEquals("Latest answer", copy.getAnswer());
    }

    @Test
    void republishingAfterDeletionCopiesTheLatestSourceVersionWithNewTaskIds() {
        controller.copyToSingleTrain(request);
        mission.setTitle("Revised title");
        mission.setAnswer("Revised answer");
        // 已删除的单兵模板查询仍返回 null，允许重新发布。
        controller.copyToSingleTrain(request);
        ArgumentCaptor<MissionTemplate> tasks = ArgumentCaptor.forClass(MissionTemplate.class);
        verify(missions, times(2)).save(tasks.capture());
        MissionTemplate first = tasks.getAllValues().get(0);
        MissionTemplate second = tasks.getAllValues().get(1);
        assertEquals("Latest answer", first.getAnswer());
        assertEquals("Revised answer", second.getAnswer());
        assertEquals("Revised title", second.getTitle());
        assertNotEquals(first.getResourceId(), second.getResourceId());
    }

    @Test
    void missingSourceTaskStopsPublicationBeforeCreatingAnyCopies() {
        when(missions.getMappingByIds(Collections.singletonList(MISSION))).thenReturn(Collections.emptyMap());
        BusinessException error = assertThrows(BusinessException.class, () -> controller.copyToSingleTrain(request));
        assertTrue(error.getErrorMessage().contains(MISSION));
        verifyNoInteractions(scenes);
        verify(businesses, never()).save(any());
        verify(singles, never()).save(any());
        verify(missions, never()).save(any());
    }

    @Test
    void repeatedTaskReferencesAcrossRolesShareOneIndependentCopy() {
        String role = team.getRoleConfig().substring(1, team.getRoleConfig().length() - 1);
        team.setRoleConfig("[" + role + "," + role + "]");
        controller.copyToSingleTrain(request);
        ArgumentCaptor<MissionTemplate> task = ArgumentCaptor.forClass(MissionTemplate.class);
        verify(missions).save(task.capture());
        ArgumentCaptor<SingleTemplate> single = ArgumentCaptor.forClass(SingleTemplate.class);
        verify(singles).save(single.capture());
        JSONObject scheme = JSONObject.parseObject(single.getValue().getMissionsScheme());
        String first = scheme.getJSONArray("scheme_data").getJSONObject(0).getJSONArray("missions").getJSONObject(0).getString("resource_id");
        String second = scheme.getJSONArray("scheme_data").getJSONObject(1).getJSONArray("missions").getJSONObject(0).getString("resource_id");
        assertEquals(task.getValue().getResourceId(), first);
        assertEquals(first, second);
    }

    @Test
    void publishingAnEmptySchemeProducesAnEmptyArray() {
        team.setRoleConfig("");
        controller.copyToSingleTrain(request);
        ArgumentCaptor<SingleTemplate> single = ArgumentCaptor.forClass(SingleTemplate.class);
        verify(singles).save(single.capture());
        assertEquals(0, JSONObject.parseObject(single.getValue().getMissionsScheme()).getJSONArray("scheme_data").size());
        verify(missions, never()).save(any());
    }

    @Test
    void alreadyPublishedTrainingCannotBeOverwritten() {
        when(singles.getByBusinessTemplateId("source_single")).thenReturn(new SingleTemplate());
        assertThrows(BusinessException.class, () -> controller.copyToSingleTrain(request));
        verifyNoInteractions(scenes, missions);
        verify(businesses, never()).save(any());
    }
}
