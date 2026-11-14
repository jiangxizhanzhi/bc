package com.cyberpeace.cr.singletrain.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.cr.crbase.service.impl.BusinessTemplateServiceImpl;
import com.cyberpeace.cr.crbase.service.impl.MissionTemplateServiceImpl;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteLogService;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SingleTemplateResourceOwnershipTest {
    private static final String SOURCE = "source-scene";
    private static final String SINGLE = "source-scene_single";
    private static final String MISSION = "11111111111111111111111111111111";
    private JdbcTemplate jdbc;
    private BusinessTemplateServiceImpl businesses;
    private SingleTemplateServiceImpl singles;
    private LocalSceneService scenes;
    private RtksRelatedService rtks;

    @BeforeEach
    void setUp() throws Exception {
        Map<String, String> config = new HashMap<>();
        for (String key : new String[]{"cr.skill.point.size", "cr.skill.point.min", "cr.skill.point.max",
                "cr.stage.name.len.min", "cr.stage.name.len.max", "cr.score.max", "cr.training.name.len.min",
                "cr.training.name.len.max", "cr.tag.count.min", "cr.tag.count.max", "cr.str.len.zero",
                "cr.str.len.min", "cr.str.len.name.max", "cr.text.len.max", "cr.option.len.max", "cr.desc.len.max",
                "rtks.skill.weight.min", "rtks.skill.weight.max", "rtks.mission.weight.min", "rtks.mission.weight.max"}) {
            config.put(key, "1");
        }
        config.put(ErrorCode.DIRECT_MSG_EXCEPTION + ".cn", "%s");
        ReflectionTestUtils.setField(ConfigUtil.class, "ctxPropertiesMap", config);
        UserInfo user = new UserInfo();
        user.setUserId("administrator");
        UserInfoContext.setUserContext(user);
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE");
        new ResourceDatabasePopulator(new ClassPathResource("mission-isolation-schema.sql")).execute(dataSource);
        jdbc = new JdbcTemplate(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.addMapper(BusinessTemplateMapper.class);
        configuration.addMapper(MissionTemplateMapper.class);
        configuration.addMapper(SingleTemplateMapper.class);
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setConfiguration(configuration);
        factory.setMapperLocations(new ClassPathResource("mapper/cr/MissionTemplateMapper.xml"),
                new ClassPathResource("mapper/cr/SingleTemplateMapper.xml"));
        SqlSessionTemplate session = new SqlSessionTemplate(factory.getObject());
        TransactionTemplate transaction = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        scenes = mock(LocalSceneService.class);
        rtks = mock(RtksRelatedService.class);
        businesses = new BusinessTemplateServiceImpl();
        ReflectionTestUtils.setField(businesses, "baseMapper", session.getMapper(BusinessTemplateMapper.class));
        ReflectionTestUtils.setField(businesses, "businessTemplateMapper", session.getMapper(BusinessTemplateMapper.class));
        ReflectionTestUtils.setField(businesses, "localSceneService", scenes);
        ReflectionTestUtils.setField(businesses, "transactionTemplate", transaction);
        ReflectionTestUtils.setField(businesses, "rtksRelatedService", rtks);
        ReflectionTestUtils.setField(businesses, "remoteLogService", mock(RemoteLogService.class));
        MissionTemplateServiceImpl missions = new MissionTemplateServiceImpl();
        ReflectionTestUtils.setField(missions, "baseMapper", session.getMapper(MissionTemplateMapper.class));
        ReflectionTestUtils.setField(missions, "missionTemplateMapper", session.getMapper(MissionTemplateMapper.class));
        ReflectionTestUtils.setField(missions, "transactionTemplate", transaction);
        ReflectionTestUtils.setField(missions, "rtksRelatedService", rtks);
        ReflectionTestUtils.setField(businesses, "missionTemplateService", missions);
        singles = new SingleTemplateServiceImpl();
        ReflectionTestUtils.setField(singles, "baseMapper", session.getMapper(SingleTemplateMapper.class));
        ReflectionTestUtils.setField(singles, "singleTemplateMapper", session.getMapper(SingleTemplateMapper.class));
        ReflectionTestUtils.setField(singles, "missionTemplateService", missions);
        ReflectionTestUtils.setField(singles, "businessTemplateService", businesses);
        ReflectionTestUtils.setField(singles, "localSceneService", scenes);
        SingleBusinessTemplateHandlerImpl handler = new SingleBusinessTemplateHandlerImpl();
        ReflectionTestUtils.setField(handler, "singleTemplateService", singles);
        ReflectionTestUtils.setField(handler, "missionTemplateService", missions);
        ReflectionTestUtils.setField(handler, "singleRoomService", mock(SingleRoomService.class));
        handler.init();
        ReflectionTestUtils.setField(businesses, "businessTemplateHandler", new BusinessTemplateHandler());
        business(SOURCE, "source-topo", "source-test", BusinessType.TRAINING_TEAM.getCode());
        business(SINGLE, "source-topo", "source-test", BusinessType.TRAINING_SINGLE.getCode());
        SingleTemplate single = new SingleTemplate();
        single.setResourceId("single");
        single.setBusinessTemplateId(SINGLE);
        single.setPreSkills("[]");
        single.setTrainSkills("[]");
        single.setMissionsScheme("{\"scheme_data\":[{\"stage\":\"Stage\",\"missions\":[{\"resource_id\":\"" + MISSION + "\"}]}]}");
        singles.save(single);
        MissionTemplate mission = new MissionTemplate();
        mission.setResourceId(MISSION);
        mission.setBusinessTemplateId(SOURCE);
        mission.setTitle("Latest source task");
        mission.setDescription("Updated description");
        mission.setAnswer("Updated answer");
        mission.setScore(90);
        missions.save(mission);
    }

    @AfterEach
    void clearUser() {
        UserInfoContext.removeUserContext();
    }

    @Test
    void deletingLegacyTrainingKeepsLatestSourceTaskAndSharedTopologyAndTestScene() {
        businesses.batchDelete(Collections.singletonList(SINGLE));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_business_template", Integer.class));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_single_template", Integer.class));
        assertEquals("Updated answer", jdbc.queryForObject("select answer from cr_mission_template", String.class));
        assertEquals(90, jdbc.queryForObject("select score from cr_mission_template", Integer.class));
        assertEquals(SOURCE, jdbc.queryForObject("select business_template_id from cr_mission_template", String.class));
        verifyNoInteractions(scenes);
        verify(rtks, never()).deleteSkillMissionRelation(argThat(ids -> ids.contains(MISSION)));
    }

    @Test
    void deletingIndependentTrainingDeletesOnlyItsOwnTaskAndTopology() {
        jdbc.update("update cr_business_template set topo_id='single-topo',local_scene_id=null where resource_id=?", SINGLE);
        jdbc.update("update cr_mission_template set business_template_id=? where resource_id=?", SINGLE, MISSION);
        jdbc.update("insert into cr_mission_template (resource_id,business_template_id,title) values ('other-task',?,'Source task')", SOURCE);
        businesses.batchDelete(Collections.singletonList(SINGLE));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_mission_template where resource_id=?", Integer.class, MISSION));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_mission_template where business_template_id=?", Integer.class, SOURCE));
        verify(scenes).deleteTopo("single-topo");
        verify(scenes, never()).deleteTopo("source-topo");
        verify(rtks).deleteSkillMissionRelation(Collections.singletonList(MISSION));
    }

    @Test
    void deletingLegacyTrainingAlsoRemovesItsOwnTasksWhenTheSchemeContainsBothOwners() {
        String own = "22222222222222222222222222222222";
        jdbc.update("insert into cr_mission_template (resource_id,business_template_id) values (?,?)", own, SINGLE);
        jdbc.update("update cr_single_template set missions_scheme=?", "{\"scheme_data\":[{\"missions\":[{\"resource_id\":\"" + MISSION + "\"},{\"resource_id\":\"" + own + "\"}]}]}");
        businesses.batchDelete(Collections.singletonList(SINGLE));
        assertEquals(MISSION, jdbc.queryForObject("select resource_id from cr_mission_template", String.class));
        verify(rtks).deleteSkillMissionRelation(Collections.singletonList(own));
    }

    @Test
    void removingATaskFromLegacyTrainingDoesNotDeleteTheSourceTask() {
        String before = singles.getByBusinessTemplateId(SINGLE).getMissionsScheme();
        businesses.updateMissionTemplateRelation(SINGLE, before, "{\"scheme_data\":[]}");
        assertEquals("Updated answer", jdbc.queryForObject("select answer from cr_mission_template", String.class));
        assertEquals(SOURCE, jdbc.queryForObject("select business_template_id from cr_mission_template", String.class));
        verify(rtks, never()).deleteSkillMissionRelation(argThat(ids -> ids.contains(MISSION)));
    }

    @Test
    void missingTaskProducesAnExplicitErrorInsteadOfNullPointerForRoomAndTemplateDetails() {
        jdbc.update("delete from cr_mission_template where resource_id=?", MISSION);
        BusinessException error = assertThrows(BusinessException.class, () -> singles.get(SINGLE));
        assertEquals(ErrorCode.DIRECT_MSG_EXCEPTION, error.getMessage());
        assertTrue(error.getErrorMessage().contains(MISSION));
        assertTrue(error.getErrorMessage().contains("任务不存在"));
    }

    @Test
    void validTaskDetailsStillShowCurrentTitleAndDescription() {
        String detail = singles.get(SINGLE).getMissionsScheme().toJSONString();
        assertTrue(detail.contains("Latest source task"));
        assertTrue(detail.contains("Updated description"));
    }

    private void business(String id, String topo, String local, int type) {
        BusinessTemplate business = new BusinessTemplate();
        business.setResourceId(id);
        business.setName("Training");
        business.setTopoId(topo);
        business.setLocalSceneId(local);
        business.setBusinessType(type);
        businesses.save(business);
    }
}
