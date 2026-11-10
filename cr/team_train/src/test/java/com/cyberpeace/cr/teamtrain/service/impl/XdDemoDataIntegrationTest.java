package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.impl.CrScoreServiceImpl;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.TrainReportContext;
import com.cyberpeace.cr.crbase.service.context.TrainReportExportContext;
import com.cyberpeace.cr.crbase.service.context.score.ScoreTrendReq;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.CourseLessonUserRelation;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.mapper.*;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class XdDemoDataIntegrationTest {
    private XdAutomaticDataServiceImpl service;
    private JdbcTemplate jdbc;
    private SqlSessionTemplate session;
    private RemoteTopoService topoService;
    private RemoteUserService userService;
    private Random random;
    private Map<String, UserInfo> users;
    private final LocalDate day = LocalDate.of(2026, 12, 31);
    private final Date oldStart = Date.from(LocalDate.of(2025, 10, 1).atTime(9, 0)
            .atZone(ZoneId.of("Asia/Shanghai")).toInstant());

    @BeforeEach
    void setUp() throws Exception {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE");
        jdbc = new JdbcTemplate(ds);
        for (Class<?> type : Arrays.asList(TeamRoom.class, CrRole.class, Mission.class, CrTask.class,
                CrTaskSubmit.class, CrTaskLog.class, CrScore.class, TrainRecord.class, CourseLessonUserRelation.class,
                TeamTemplate.class, BusinessTemplate.class, MissionTemplate.class)) {
            createTable(type);
        }
        jdbc.execute("create table local_scene (business_instance_id varchar(64), start_time timestamp, destroy_time timestamp, scene_status int)");
        new ResourceDatabasePopulator(new FileSystemResource("../../template/src/main/resources/static/sql/bc/V5.6.0.20261109.1__demo_data.sql"),
                new FileSystemResource("../../template/src/main/resources/static/sql/bc/V5.6.0.20261110.1__annual_demo_data.sql")).execute(ds);
        MybatisConfiguration config = new MybatisConfiguration();
        config.setMapUnderscoreToCamelCase(true);
        List<Class<?>> mappers = Arrays.asList(TeamRoomMapper.class, XdDemoDataMapper.class, CrRoleMapper.class,
                MissionMapper.class, MissionTemplateMapper.class, CrTaskMapper.class, CrTaskSubmitMapper.class, CrTaskLogMapper.class,
                CrScoreMapper.class, TrainRecordMapper.class, CourseLessonUserRelationMapper.class);
        mappers.forEach(config::addMapper);
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        config.addInterceptor(interceptor);
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(ds);
        factory.setConfiguration(config);
        factory.setMapperLocations(new ClassPathResource("mapper/cr/TeamRoomMapper.xml"),
                new ClassPathResource("mapper/cr/XdDemoDataMapper.xml"),
                new ClassPathResource("mapper/cr/CrRoleMapper.xml"),
                new ClassPathResource("mapper/cr/CrTaskSubmitMapper.xml"),
                new ClassPathResource("mapper/cr/CrTaskLogMapper.xml"),
                new ClassPathResource("mapper/cr/CrScoreMapper.xml"),
                new ClassPathResource("mapper/cr/TrainRecordMapper.xml"));
        session = new SqlSessionTemplate(factory.getObject());
        service = new XdAutomaticDataServiceImpl();
        for (Field field : XdAutomaticDataServiceImpl.class.getDeclaredFields()) {
            if (mappers.contains(field.getType())) {
                ReflectionTestUtils.setField(service, field.getName(), session.getMapper(field.getType()));
            }
        }
        ReflectionTestUtils.setField(service, "transactionTemplate", new TransactionTemplate(new DataSourceTransactionManager(ds)));
        ReflectionTestUtils.setField(service, "timeZone", "Asia/Shanghai");
        random = mock(Random.class);
        when(random.nextInt(anyInt())).thenReturn(0);
        ReflectionTestUtils.setField(service, "random", random);
        topoService = mock(RemoteTopoService.class);
        userService = mock(RemoteUserService.class);
        ReflectionTestUtils.setField(service, "remoteTopoService", topoService);
        ReflectionTestUtils.setField(service, "remoteUserService", userService);
        TopoDigest digest = new TopoDigest();
        digest.setNodeCounts(8);
        digest.setResourceOccupiedNums(5);
        when(topoService.batchGetTopoDigest(anyList())).thenReturn(Collections.singletonMap("topo", digest));
        users = new HashMap<>();
        addUser("teacher", "liuwenyan");
        addUser("shen-teacher", "shenjuan");
        addUser("student", "student");
        when(userService.getUserIdsByRoleName("教员")).thenReturn(Arrays.asList("teacher", "shen-teacher"));
        when(userService.getUserIdsByRoleName("学员")).thenReturn(Collections.singletonList("student"));
        when(userService.getIdUserInfoMap(anyList())).thenAnswer(call -> users);
        Properties properties = new Properties();
        for (String module : Arrays.asList("cr", "rtks")) {
            try (InputStream input = Files.newInputStream(Paths.get("../../template/src/main/resources/modules/" + module + ".properties"))) {
                properties.load(input);
            }
        }
        Map<String, String> constants = new HashMap<>();
        for (String key : Arrays.asList("cr.skill.point.size", "cr.skill.point.min", "cr.skill.point.max",
                "cr.stage.name.len.min", "cr.stage.name.len.max", "cr.score.max", "cr.tag.count.min", "cr.tag.count.max",
                "cr.training.name.len.min", "cr.training.name.len.max",
                "cr.str.len.zero", "cr.str.len.min", "cr.str.len.name.max", "cr.text.len.max", "cr.option.len.max",
                "cr.desc.len.max", "rtks.skill.weight.min", "rtks.skill.weight.max",
                "rtks.mission.weight.min", "rtks.mission.weight.max")) {
            constants.put(key, properties.getProperty(key));
        }
        ReflectionTestUtils.setField(ConfigUtil.class, "ctxPropertiesMap", constants);
        seedSource();
    }

    @AfterEach
    void clearUser() {
        UserInfoContext.removeUserContext();
    }

    @Test
    void createsLinkedBusinessRecordsAndPreservesHistory() {
        service.generate(day);
        String roomId = jdbc.queryForObject("select room_id from cr_demo_data_room", String.class);
        assertNotEquals("source", roomId);
        assertEquals(1, jdbc.queryForObject("select generated_count from cr_demo_data_run", Integer.class));
        TeamRoom room = session.getMapper(TeamRoomMapper.class).selectById(roomId);
        assertEquals(2, room.getTrainingStatus());
        assertEquals(3, room.getXdType());
        assertNull(room.getLocalSceneId());
        assertNull(room.getReplaySceneId());
        assertEquals("", room.getCopyedTopoId());
        assertEquals(XdAutomaticDataServiceImpl.scheduledDate(2026, "template", "student", 3), room.getStartTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).toLocalDate());
        assertEquals(7_200_000L, room.getEndTime().getTime() - room.getStartTime().getTime());
        assertEquals("topo", session.getMapper(TeamRoomMapper.class).selectById("source").getCopyedTopoId());
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task where room_id=? and resource_id <> 'task' and mission_id <> 'mission' and role_id <> 'student-role'", Integer.class, roomId));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task t join cr_mission m on t.mission_id=m.resource_id join cr_role r on t.role_id=r.resource_id where t.room_id=? and m.room_id=t.room_id and r.room_id=t.room_id", Integer.class, roomId));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task_submit s join cr_task t on s.task_id=t.resource_id where s.room_id=? and s.room_id=t.room_id", Integer.class, roomId));
        assertEquals("teacher", jdbc.queryForObject("select evaluator_id from cr_task_submit where room_id=?", String.class, roomId));
        assertEquals(3, jdbc.queryForObject("select count(*) from cr_task_log l join cr_task t on l.task_id=t.resource_id join cr_mission m on l.mission_id=m.resource_id where l.room_id=? and l.room_id=t.room_id and l.room_id=m.room_id", Integer.class, roomId));
        CrRole role = session.getMapper(CrRoleMapper.class).getRolesByRoomId(roomId).stream().filter(CrRole::isTeamStudent).findFirst().get();
        String missionId = jdbc.queryForObject("select resource_id from cr_mission where room_id=?", String.class, roomId);
        assertEquals(Collections.singletonList(missionId), role.getMissionStageSchemes().get(0).getMissions());
        assertEquals("[\"student\"]", role.getUserIds());
        assertEquals(163, jdbc.queryForObject("select sum(final_score) from cr_score where user_id='student'", Integer.class));
        assertEquals(2, jdbc.queryForObject("select count(*) from course_lesson_user_relation where user_id='student' and course_id='course'", Integer.class));
        assertEquals(1, session.getMapper(XdDemoDataMapper.class).selectTemplates().size());
        CrTask task = session.getMapper(CrTaskMapper.class).selectList(new QueryWrapper<CrTask>().eq("room_id", roomId)).get(0);
        assertEquals(room.getStartTime().getTime() + 3_600_000L, task.getLastSubmitTime().getTime());
        assertEquals(room.getStartTime(), task.getCreateTime());
        verify(topoService).batchGetTopoDigest(Collections.singletonList("topo"));
        verifyNoMoreInteractions(topoService);
    }

    @Test
    void allSevenCategoriesUseTheirTeacherAndSeasonAndExcludeTraining() {
        for (int type = 1; type <= 7; type++) {
            if (type != 3) seedTemplate("template" + type, type);
        }
        seedTemplate("subject", 1);
        jdbc.update("update cr_business_template set xd_template_category=2 where resource_id='subject-business'");
        seedTemplate("exercise", 8);
        service.generate(day);
        assertEquals(7, count("cr_demo_data_room"));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_team_room where resource_id <> 'source' and xd_type not between 1 and 7", Integer.class));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_team_room r join cr_train_record t on t.room_id=r.resource_id where r.resource_id <> 'source' and ((r.xd_type between 1 and 5 and (t.user_id <> 'teacher' or month(r.start_time) not between 10 and 12)) or (r.xd_type in (6,7) and (t.user_id <> 'shen-teacher' or month(r.start_time) not between 4 and 6)))", Integer.class));
        assertEquals(7, jdbc.queryForObject("select count(*) from cr_demo_data_room where source_room_id is null", Integer.class));
    }

    @Test
    void generatedListUsesItsSnapshotWithoutLookingUpTheOriginalTopology() {
        service.generate(day);
        clearInvocations(topoService);
        TeamRoomServiceImpl rooms = new TeamRoomServiceImpl();
        ReflectionTestUtils.setField(rooms, "teamRoomMapper", session.getMapper(TeamRoomMapper.class));
        ReflectionTestUtils.setField(rooms, "remoteTopoService", topoService);
        ReflectionTestUtils.setField(rooms, "remoteUserService", userService);
        ReflectionTestUtils.setField(rooms, "rtksRelatedService", mock(RtksRelatedService.class));
        CrRoleService roles = mock(CrRoleService.class);
        when(roles.getRoomRoleMapByRoomIdList(anyList())).thenAnswer(call -> {
            Map<String, List<CrRole>> result = new HashMap<>();
            for (String id : (List<String>) call.getArgument(0)) {
                result.put(id, session.getMapper(CrRoleMapper.class).getRolesByRoomId(id));
            }
            return result;
        });
        ReflectionTestUtils.setField(rooms, "crRoleService", roles);
        UserInfo teacher = new UserInfo(); teacher.setUserId("teacher");
        UserInfoContext.setUserContext(teacher);
        ListQuery query = new ListQuery();
        query.setName(jdbc.queryForObject("select name from cr_team_room where resource_id <> 'source'", String.class));
        ListRsp<TeamRoomRsp> result = rooms.listRoom(query, true);
        assertEquals(1, result.getTotal());
        assertEquals(8, result.getList().get(0).getNodesCount());
        assertEquals(5, result.getList().get(0).getResourceOccupiedNums());
        assertEquals(2, result.getList().get(0).getTrainingRoleData().getRoleDataList().size());
        verifyNoInteractions(topoService);
    }

    @Test
    void generatedScoresAreConsistentInReportsAndExportDataWithoutARunningScene() {
        service.generate(day);
        String roomId = jdbc.queryForObject("select room_id from cr_demo_data_room", String.class);
        CrScoreServiceImpl scores = new CrScoreServiceImpl();
        ReflectionTestUtils.setField(scores, "crScoreMapper", session.getMapper(CrScoreMapper.class));
        ReflectionTestUtils.setField(scores, "crTaskLogMapper", session.getMapper(CrTaskLogMapper.class));
        CrRoleService roles = mock(CrRoleService.class);
        when(roles.getRolesByRoomAndGroups(eq(roomId), anyCollection())).thenReturn(
                session.getMapper(CrRoleMapper.class).getRolesByRoomId(roomId).stream()
                        .filter(CrRole::isTeamStudent).collect(Collectors.toList()));
        UserData student = new UserData(); student.setResourceId("student"); student.setRepName("Student");
        when(roles.getUserDatas(anyList())).thenReturn(Collections.singletonList(student));
        ReflectionTestUtils.setField(scores, "crRoleService", roles);
        CrTaskService tasks = mock(CrTaskService.class);
        when(tasks.getByRoomId(roomId)).thenReturn(session.getMapper(CrTaskMapper.class)
                .selectList(new QueryWrapper<CrTask>().eq("room_id", roomId)));
        ReflectionTestUtils.setField(scores, "crTaskService", tasks);
        MissionService missions = mock(MissionService.class);
        when(missions.listByIds(anyCollection())).thenAnswer(call -> session.getMapper(MissionMapper.class)
                .selectList(new QueryWrapper<Mission>().in("resource_id", (Collection<?>) call.getArgument(0))));
        List<RadarLabel> labels = new ArrayList<>();
        for (int i = 1; i <= Integer.parseInt(ConfigUtil.getContextProperty("cr.skill.point.size")); i++) {
            RadarLabel label = new RadarLabel(); label.setResourceId(i); label.setLabel("skill" + i); labels.add(label);
        }
        when(missions.getRadarLabel()).thenReturn(labels);
        ReflectionTestUtils.setField(scores, "missionService", missions);
        ReflectionTestUtils.setField(scores, "crConstantsService", mock(CrConstantsService.class));
        ReflectionTestUtils.setField(scores, "localSceneService", mock(LocalSceneService.class));
        RemoteSceneService remoteScenes = mock(RemoteSceneService.class);
        ReflectionTestUtils.setField(scores, "remoteSceneService", remoteScenes);
        TeamRoom room = session.getMapper(TeamRoomMapper.class).selectById(roomId);
        TrainReportContext report = scores.getTrainReport(roomId, new ArrayList<>(Collections.singletonList(2)),
                new ScoreTrendReq(session.getMapper(TrainRecordMapper.class).getByRoomId(roomId),
                        room.getStartTime(), room.getEndTime(), 10, room.getReplaySceneId()));
        assertEquals(1, report.getGetScoreRsps().get(0).getAllTaskCount());
        assertEquals(75, report.getGetScoreRsps().get(0).getSynthesisScore());
        TrainReportExportContext export = BeanUtils.deepCopy(report, TrainReportExportContext.class);
        export.initExport();
        assertEquals(75, export.getGetScoreRsps().get(0).getSynthesisScore());
        assertEquals(1, export.getGetScoreRsps().get(0).getSuccessTaskCount());
        verifyNoInteractions(remoteScenes);
    }

    @Test
    void repeatedAndConcurrentExecutionsDoNotDuplicateTheDay() throws Exception {
        ExecutorService workers = Executors.newFixedThreadPool(2);
        try {
            Future<?> first = workers.submit(() -> service.generate(day));
            Future<?> second = workers.submit(() -> service.generate(day));
            first.get(15, TimeUnit.SECONDS);
            second.get(15, TimeUnit.SECONDS);
        } finally {
            workers.shutdownNow();
        }
        service.generate(day);
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_demo_data_run", Integer.class));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_demo_data_room", Integer.class));
        assertEquals(2, jdbc.queryForObject("select count(*) from cr_team_room", Integer.class));
    }

    @Test
    void failuresRollBackExecutionMarkerAndAllLinkedRows() {
        jdbc.execute("alter table cr_task_submit add constraint fail_demo check (room_id='source')");
        assertThrows(RuntimeException.class, () -> service.generate(day));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_demo_data_run", Integer.class));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_demo_data_room", Integer.class));
        for (String table : Arrays.asList("cr_team_room", "cr_task", "cr_mission", "cr_score", "cr_train_record", "course_lesson_user_relation")) {
            assertEquals(1, jdbc.queryForObject("select count(*) from " + table, Integer.class), table);
        }
        assertEquals(2, jdbc.queryForObject("select count(*) from cr_role", Integer.class));
    }

    @Test
    void missingTemplateTasksDoNotLeaveASuccessMarker() {
        jdbc.update("delete from cr_mission_template");
        service.generate(day);
        assertEquals(0, count("cr_demo_data_run"));
        assertEquals(1, count("cr_team_room"));
    }

    @Test
    void everyStudentCompletesTheUnionOfRoleTasksExactlyOnce() {
        addUser("other-student", "other");
        when(userService.getUserIdsByRoleName("学员")).thenReturn(Arrays.asList("student", "other-student", "student"));
        seedMissionTemplate("second-mission");
        String first = roleConfig("template-mission");
        String second = roleConfig("template-mission", "second-mission");
        jdbc.update("update cr_team_template set role_config=?", first.substring(0, first.length() - 1) + "," + second.substring(1));
        service.generate(day);
        assertEquals(4, jdbc.queryForObject("select count(*) from cr_task where room_id <> 'source'", Integer.class));
        assertEquals(4, jdbc.queryForObject("select count(*) from cr_task_submit where room_id <> 'source'", Integer.class));
        assertEquals(0, jdbc.queryForObject("select count(*) from (select t.user_id,m.mission_template_id,count(*) as n from cr_task t join cr_mission m on m.resource_id=t.mission_id where t.room_id <> 'source' group by t.user_id,m.mission_template_id having count(*) <> 1) duplicates", Integer.class));
        assertEquals(1, jdbc.queryForObject("select count(*) from course_lesson_user_relation where lesson_id <> 'source'", Integer.class));
        service.generate(day);
        assertEquals(4, jdbc.queryForObject("select count(*) from cr_task where room_id <> 'source'", Integer.class));
    }

    @Test
    void generatedTimesStayWithinTheExperimentWithoutChangingHistoricalNulls() {
        jdbc.update("update cr_task set create_time=null, update_time=null where resource_id='task'");
        service.generate(day);
        String roomId = jdbc.queryForObject("select room_id from cr_demo_data_room", String.class);
        TeamRoom room = session.getMapper(TeamRoomMapper.class).selectById(roomId);
        CrTask task = session.getMapper(CrTaskMapper.class)
                .selectList(new QueryWrapper<CrTask>().eq("room_id", roomId)).get(0);
        assertEquals(room.getStartTime(), task.getCreateTime());
        assertTrue(task.getUpdateTime().before(room.getEndTime()));
        assertNull(jdbc.queryForObject("select create_time from cr_task where resource_id='task'", Date.class));
        assertEquals(88, jdbc.queryForObject("select score from cr_task where resource_id='task'", Integer.class));
        assertEquals("演习文书：保留原文与附件引用", jdbc.queryForObject("select exercise_paper from cr_role where resource_id='student-role'", String.class));
    }

    @Test
    void deletedParticipantsDoNotBecomeNewDemoParticipants() {
        when(userService.getIdUserInfoMap(anyList())).thenReturn(Collections.emptyMap());
        service.generate(day);
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_demo_data_run", Integer.class));
    }

    @Test
    void disabledJobDoesNotReadOrWriteBusinessRecords() {
        ReflectionTestUtils.setField(ConfigUtil.class, "ctxPropertiesMap", Collections.singletonMap("ojj.demoDataEnabled", "false"));
        service.automatic();
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_demo_data_run", Integer.class));
        verifyNoInteractions(topoService, userService);
    }

    @Test
    void defaultEnabledJobUsesPreviousDateInConfiguredZone() {
        XdAutomaticDataServiceImpl job = spy(service);
        ReflectionTestUtils.setField(job, "timeZone", "Pacific/Kiritimati");
        doNothing().when(job).generate(any(LocalDate.class));
        job.automatic();
        verify(job).generate(LocalDate.now(ZoneId.of("Pacific/Kiritimati")).minusDays(1));
    }

    @Test
    void exactParticipationAndPaginationExcludeSimilarUserIds() {
        TeamRoomMapper rooms = session.getMapper(TeamRoomMapper.class);
        assertEquals(Collections.singletonList("source"), rooms.selectParticipatingRoomIds("student"));
        assertEquals(Collections.singletonList("source"), rooms.selectParticipatingRoomIds("teacher"));
        assertTrue(rooms.selectParticipatingRoomIds("stud").isEmpty());
        assertTrue(rooms.selectParticipatingRoomIds("stu%ent").isEmpty());
        assertTrue(rooms.selectParticipatingRoomIds("stu_ent").isEmpty());
        service.generate(day);
        ListQuery query = new ListQuery();
        query.setIds(rooms.selectParticipatingRoomIds("student"));
        Page<TeamRoomData> page = new Page<>(1, 1);
        assertEquals(2, rooms.listTeamRoom(page, query).getTotal());
        assertEquals(1, page.getRecords().size());
        query.setIds(Collections.emptyList());
        assertEquals(0, rooms.listTeamRoom(new Page<>(1, 10), query).getTotal());
    }

    @Test
    void datesBefore2026DoNotReadOrWriteData() {
        service.generate(LocalDate.of(2025, 12, 31));
        verifyNoInteractions(userService, topoService);
        assertEquals(0, count("cr_demo_data_run"));
    }

    @Test
    void onlyDueDatesAreGeneratedAndCalendarDoesNotDependOnRosterOrder() {
        LocalDate scheduled = XdAutomaticDataServiceImpl.scheduledDate(2026, "template", "student", 3);
        service.generate(scheduled.minusDays(1));
        assertEquals(0, count("cr_demo_data_room"));
        service.generate(scheduled);
        assertEquals(scheduled, jdbc.queryForObject("select business_date from cr_demo_data_room", java.sql.Date.class).toLocalDate());
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_demo_data_room where business_date > ?", Integer.class, scheduled));
    }

    @Test
    void completionIsRequiredOnceForEachYear() {
        service.generate(LocalDate.of(2027, 12, 31));
        assertEquals(2, count("cr_demo_data_room"));
        assertEquals(2, jdbc.queryForObject("select count(distinct year(business_date)) from cr_demo_data_room", Integer.class));
        service.generate(LocalDate.of(2027, 12, 31));
        assertEquals(2, count("cr_demo_data_room"));
    }

    @Test
    void qualifiedHistoricalCompletionCountsEvenOnAnotherDate() {
        moveSource(LocalDate.of(2026, 11, 1));
        service.generate(day);
        assertEquals(0, count("cr_demo_data_room"));
        assertEquals(0, count("cr_demo_data_run"));
        assertEquals(88, jdbc.queryForObject("select final_score from cr_score where room_id='source'", Integer.class));
    }

    @Test
    void partialHistoricalExperimentIsPreservedAndReplacedWithACompleteExperiment() {
        moveSource(LocalDate.of(2026, 11, 1));
        seedMissionTemplate("second-mission");
        jdbc.update("update cr_team_template set role_config=?", roleConfig("template-mission", "second-mission"));
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
        assertEquals(2, jdbc.queryForObject("select count(*) from cr_task where room_id <> 'source'", Integer.class));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task where room_id='source'", Integer.class));
        assertEquals(88, jdbc.queryForObject("select final_score from cr_score where room_id='source'", Integer.class));
    }

    @Test
    void aScoreWithoutACompletedSubmissionDoesNotCount() {
        moveSource(LocalDate.of(2026, 11, 1));
        jdbc.update("update cr_task_submit set evaluate_status=0 where room_id='source'");
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
    }

    @Test
    void aCompletedTaskWithoutAFinalScoreDoesNotCount() {
        moveSource(LocalDate.of(2026, 11, 1));
        jdbc.update("update cr_score set final_score=null where room_id='source'");
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
    }

    @Test
    void evaluatedFailureAlsoCountsAsCompleted() {
        moveSource(LocalDate.of(2026, 11, 1));
        jdbc.update("update cr_task set status=2 where room_id='source'");
        jdbc.update("update cr_task_submit set evaluate_status=2 where room_id='source'");
        service.generate(day);
        assertEquals(0, count("cr_demo_data_room"));
    }

    @Test
    void aRecordOutsideTheSeasonDoesNotConsumeTheAnnualCompletion() {
        moveSource(LocalDate.of(2026, 7, 1));
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
    }

    @Test
    void aRecordWithTheWrongTeacherDoesNotConsumeTheAnnualCompletion() {
        moveSource(LocalDate.of(2026, 11, 1));
        jdbc.update("update cr_role set user_ids='[\"shen-teacher\"]' where resource_id='teacher-role'");
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
    }

    @Test
    void concurrentExecutionsAlsoSerializeAnExistingDateMarker() throws Exception {
        LocalDate date = XdAutomaticDataServiceImpl.scheduledDate(2026, "template", "student", 3);
        session.getMapper(XdDemoDataMapper.class).insertRun(date);
        ExecutorService workers = Executors.newFixedThreadPool(3);
        try {
            List<Future<?>> calls = new ArrayList<>();
            for (int i = 0; i < 3; i++) calls.add(workers.submit(() -> service.generate(day)));
            for (Future<?> call : calls) call.get(15, TimeUnit.SECONDS);
        } finally {
            workers.shutdownNow();
        }
        assertEquals(1, count("cr_demo_data_room"));
        assertEquals(1, jdbc.queryForObject("select generated_count from cr_demo_data_run", Integer.class));
    }

    @Test
    void incrementalMigrationsPreserveOldSourcesAndAllowTemplateSources() {
        for (String dialect : Arrays.asList("bc", "bc_dm")) {
            jdbc.update("delete from cr_demo_data_room");
            jdbc.execute("alter table cr_demo_data_room alter column source_room_id set not null");
            session.getMapper(XdDemoDataMapper.class).insertRoom("legacy", "source", day, "{}");
            new ResourceDatabasePopulator(new FileSystemResource("../../template/src/main/resources/static/sql/"
                    + dialect + "/V5.6.0.20261110.1__annual_demo_data.sql")).execute(jdbc.getDataSource());
            session.getMapper(XdDemoDataMapper.class).insertRoom("new", null, day, "{}");
            assertEquals("source", jdbc.queryForObject("select source_room_id from cr_demo_data_room where room_id='legacy'", String.class));
            assertNull(jdbc.queryForObject("select source_room_id from cr_demo_data_room where room_id='new'", String.class));
        }
    }

    @Test
    void dmMergeStatementSupportsFirstAndRepeatedDateAcquisition() {
        String previous = System.getProperty("BC_DB_TYPE");
        System.setProperty("BC_DB_TYPE", "dm");
        try {
            service.generate(day);
            service.generate(day);
            assertEquals(1, count("cr_demo_data_room"));
            assertEquals(1, count("cr_demo_data_run"));
        } finally {
            if (previous == null) System.clearProperty("BC_DB_TYPE");
            else System.setProperty("BC_DB_TYPE", previous);
        }
    }

    @Test
    void anExistingDateMarkerStillAllowsNewStudentsAndUpdatesTheCount() {
        service.generate(day);
        LocalDate date = XdAutomaticDataServiceImpl.scheduledDate(2026, "template", "student", 3);
        String newcomer = null;
        for (int i = 0; i < 1000; i++) {
            String id = "new-" + i;
            if (XdAutomaticDataServiceImpl.scheduledDate(2026, "template", id, 3).equals(date)) {
                newcomer = id;
                break;
            }
        }
        assertNotNull(newcomer);
        addUser(newcomer, "new-student");
        when(userService.getUserIdsByRoleName("学员")).thenReturn(Arrays.asList(newcomer, "student"));
        service.generate(day);
        assertEquals(2, count("cr_demo_data_room"));
        assertEquals(2, jdbc.queryForObject("select generated_count from cr_demo_data_run where business_date=?", Integer.class, date));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task where room_id <> 'source' and user_id='student'", Integer.class));
        assertEquals(0, jdbc.queryForObject("select count(*) from course_lesson_user_relation where user_id=?", Integer.class, newcomer));
    }

    @Test
    void templatesWithoutHistoricalExperimentsStillGenerate() {
        for (String table : Arrays.asList("cr_task_log", "cr_task_submit", "cr_task", "cr_mission", "cr_role", "cr_score", "cr_train_record", "course_lesson_user_relation", "cr_team_room")) {
            jdbc.update("delete from " + table);
        }
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
        assertEquals(1, count("cr_task"));
        assertEquals(0, count("course_lesson_user_relation"));
    }

    @Test
    void missingOrAmbiguousTeacherAccountsSkipOnlyTheirGroup() {
        seedTemplate("new-network", 6);
        users.get("teacher").setUserName("another-account");
        users.get("teacher").setName("刘老师");
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
        assertEquals(6, jdbc.queryForObject("select xd_type from cr_team_room where resource_id <> 'source'", Integer.class));
        users.get("teacher").setUserName("liuwenyan");
        addUser("duplicate-teacher", "liuwenyan");
        when(userService.getUserIdsByRoleName("教员")).thenReturn(Arrays.asList("teacher", "duplicate-teacher", "shen-teacher"));
        service.generate(day);
        assertEquals(1, count("cr_demo_data_room"));
    }

    @Test
    void aMissingTopologyDoesNotCreateAnExecutionMarker() {
        when(topoService.batchGetTopoDigest(anyList())).thenReturn(Collections.emptyMap());
        service.generate(day);
        assertEquals(0, count("cr_demo_data_room"));
        assertEquals(0, count("cr_demo_data_run"));
    }

    @Test
    void personalCourseMembershipIsDeduplicatedAndNewMembershipsAreNotInvented() {
        jdbc.update("insert into course_lesson_user_relation(resource_id,course_id,course_name,lesson_id,user_id,scored_flag) values ('duplicate','course','renamed-course','source','student',false)");
        service.generate(day);
        assertEquals(1, jdbc.queryForObject("select count(*) from course_lesson_user_relation where lesson_id <> 'source'", Integer.class));
        assertTrue(jdbc.queryForObject("select scored_flag from course_lesson_user_relation where lesson_id <> 'source'", Boolean.class));
        assertEquals(2, jdbc.queryForObject("select count(*) from course_lesson_user_relation where lesson_id='source'", Integer.class));
    }

    @Test
    void allHistoricalBusinessRowsRemainUnchanged() {
        Map<String, String> filters = new LinkedHashMap<>();
        filters.put("cr_team_room", "resource_id='source'");
        for (String table : Arrays.asList("cr_role", "cr_mission", "cr_task", "cr_task_submit", "cr_task_log", "cr_score", "cr_train_record")) {
            filters.put(table, "room_id='source'");
        }
        filters.put("course_lesson_user_relation", "lesson_id='source'");
        Map<String, List<Map<String, Object>>> before = new HashMap<>();
        filters.forEach((table, filter) -> before.put(table, jdbc.queryForList("select * from " + table + " where " + filter + " order by id")));
        service.generate(day);
        filters.forEach((table, filter) -> assertEquals(before.get(table), jdbc.queryForList("select * from " + table + " where " + filter + " order by id"), table));
    }

    @Test
    void finalScoresAreGaussianAndClippedToTheHundredPointScale() {
        when(random.nextGaussian()).thenReturn(-100.0, 100.0, 0.0);
        assertEquals(0, service.sampleScore());
        assertEquals(100, service.sampleScore());
        assertEquals(75, service.sampleScore());
        ReflectionTestUtils.setField(service, "random", new Random(34));
        double sum = 0;
        double squared = 0;
        for (int i = 0; i < 10000; i++) {
            int score = service.sampleScore();
            assertTrue(score >= 0 && score <= 100);
            sum += score;
            squared += score * score;
        }
        double mean = sum / 10000;
        double deviation = Math.sqrt(squared / 10000 - mean * mean);
        assertEquals(75, mean, 0.5);
        assertEquals(10, deviation, 0.5);
    }

    @Test
    void taskSubmissionAndTrainingScoresShareTheSameActualPoints() {
        jdbc.update("update cr_mission_template set score=200");
        when(random.nextGaussian()).thenReturn(1.0);
        service.generate(day);
        assertEquals(85, jdbc.queryForObject("select final_score from cr_score where room_id <> 'source'", Integer.class));
        assertEquals(170, jdbc.queryForObject("select training_score from cr_score where room_id <> 'source'", Integer.class));
        assertEquals(170, jdbc.queryForObject("select score from cr_task where room_id <> 'source'", Integer.class));
        assertEquals(170, jdbc.queryForObject("select score from cr_task_submit where room_id <> 'source'", Integer.class));
        assertEquals(200, jdbc.queryForObject("select mission_score from cr_task_submit where room_id <> 'source'", Integer.class));
    }

    private void createTable(Class<?> type) {
        List<String> columns = new ArrayList<>();
        for (Class<?> current = type; current != Object.class; current = current.getSuperclass()) {
            for (Field field : current.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) continue;
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation != null && !annotation.exist()) continue;
                String name = field.getName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
                String sqlType = field.getType() == String.class ? "varchar(16000)"
                        : Date.class.isAssignableFrom(field.getType()) ? "timestamp"
                        : field.getType() == Boolean.class ? "boolean" : "int";
                columns.add("`" + name + "` " + sqlType + (name.equals("id") ? " auto_increment primary key" : ""));
            }
        }
        jdbc.execute("create table " + type.getAnnotation(TableName.class).value() + " (" + String.join(",", columns) + ")");
    }

    private void seedSource() {
        seedTemplate("template", 3);
        TeamRoom room = new TeamRoom();
        room.setResourceId("source"); room.setName("experiment"); room.setTeamTemplateId("template");
        room.setXdType(3); room.setTrainingStatus(2); room.setCopyedTopoId("topo");
        room.setLocalSceneId("real-scene"); room.setReplaySceneId("real-replay");
        room.setStartTime(oldStart); room.setEndTime(new Date(oldStart.getTime() + 14_400_000L));
        stamp(room); session.getMapper(TeamRoomMapper.class).insert(room);
        for (String name : Arrays.asList("teacher", "student")) {
            CrRole role = new CrRole();
            role.setResourceId(name + "-role"); role.setRoomId("source"); role.setName(name);
            role.setBusinessType(3); role.setRoleGroup(name.equals("teacher") ? 1 : 2);
            role.setUserIds("[\"" + name + "\"]");
            role.setMissionScheme(name.equals("teacher") ? "[]" : "[{\"stage\":\"one\",\"missions\":[\"mission\"],\"dynamic\":[]}]");
            role.setExercisePaper("演习文书：保留原文与附件引用"); stamp(role);
            session.getMapper(CrRoleMapper.class).insert(role);
        }
        Mission mission = new Mission();
        mission.setResourceId("mission"); mission.setRoomId("source"); mission.setTitle("question"); mission.setScore(100);
        mission.setType(1); mission.setCategoryId("category"); mission.setMissionTemplateId("template-mission");
        stamp(mission); session.getMapper(MissionMapper.class).insert(mission);
        CrTask task = new CrTask();
        task.setResourceId("task"); task.setRoomId("source"); task.setUserId("student");
        task.setRoleId("student-role"); task.setMissionId("mission"); task.setStatus(1); task.setScore(88); task.setBusinessType(3);
        task.setReleaseTime(oldStart); task.setTaskStartTime(oldStart);
        task.setLastSubmitTime(new Date(oldStart.getTime() + 7_200_000L)); stamp(task);
        session.getMapper(CrTaskMapper.class).insert(task);
        CrTaskSubmit submit = new CrTaskSubmit();
        submit.setResourceId("submission"); submit.setRoomId("source"); submit.setTaskId("task");
        submit.setEvaluateStatus(1); submit.setScore(88); submit.setEvaluatorId("teacher");
        submit.setEvaluateTime(task.getLastSubmitTime()); stamp(submit);
        session.getMapper(CrTaskSubmitMapper.class).insert(submit);
        CrTaskLog log = new CrTaskLog();
        log.setResourceId("log"); log.setRoomId("source"); log.setMissionId("mission"); log.setTaskId("task");
        log.setActionCode(7); log.setUserId("teacher"); stamp(log);
        session.getMapper(CrTaskLogMapper.class).insert(log);
        CrScore score = new CrScore();
        score.setResourceId("score"); score.setRoomId("source"); score.setUserId("student");
        score.setFinalScore(88); score.setTrainingScore(88); stamp(score);
        session.getMapper(CrScoreMapper.class).insert(score);
        TrainRecord record = new TrainRecord();
        record.setRoomId("source"); record.setRoomName("experiment"); record.setBusinessType(3);
        record.setBusinessTemplateId("business-template"); record.setUserId("teacher");
        record.setStartTime(oldStart); record.setEndTime(room.getEndTime()); session.getMapper(TrainRecordMapper.class).insert(record);
        CourseLessonUserRelation relation = new CourseLessonUserRelation();
        relation.setResourceId("relation"); relation.setCourseId("course"); relation.setLessonId("source"); relation.setUserId("student");
        relation.setCourseName("course"); relation.setScoredFlag(true); stamp(relation);
        session.getMapper(CourseLessonUserRelationMapper.class).insert(relation);
    }

    private void addUser(String id, String account) {
        UserInfo user = new UserInfo();
        user.setUserId(id); user.setUserName(account); user.setName("display-" + account);
        users.put(id, user);
    }

    private void seedTemplate(String id, int type) {
        BusinessTemplate business = new BusinessTemplate();
        business.setResourceId(id + "-business"); business.setName("experiment");
        business.setBusinessType(3); business.setXdTemplateCategory(1); business.setXdTemplateType(type);
        business.setTopoId("topo"); stamp(business);
        // BusinessTemplate is only read by the demo mapper, so seed through JDBC.
        jdbc.update("insert into cr_business_template(resource_id,name,business_type,xd_template_category,xd_template_type,topo_id) values (?,?,?,?,?,?)",
                business.getResourceId(), business.getName(), 3, 1, type, "topo");
        TeamTemplate template = new TeamTemplate();
        template.setResourceId(id); template.setBusinessTemplateId(business.getResourceId());
        template.setDurationTime(2); template.setRoleConfig(roleConfig(id + "-mission"));
        jdbc.update("insert into cr_team_template(resource_id,business_template_id,duration_time,role_config) values (?,?,?,?)",
                id, template.getBusinessTemplateId(), 2, template.getRoleConfig());
        seedMissionTemplate(id + "-mission");
    }

    private void seedMissionTemplate(String id) {
        MissionTemplate mission = new MissionTemplate();
        mission.setResourceId(id); mission.setTitle("question"); mission.setScore(100);
        mission.setType(1); mission.setCategoryId("category"); mission.setAnswer("A");
        stamp(mission); session.getMapper(MissionTemplateMapper.class).insert(mission);
    }

    private String roleConfig(String... missions) {
        String tasks = Arrays.stream(missions).map(id -> "{\"resourceId\":\"" + id + "\"}").collect(Collectors.joining(","));
        return "[{\"name\":\"student\",\"missionsScheme\":{\"schemeData\":[{\"stage\":\"one\",\"missions\":[" + tasks + "],\"dynamic\":[]}]}}]";
    }

    private int count(String table) {
        return jdbc.queryForObject("select count(*) from " + table, Integer.class);
    }

    private void moveSource(LocalDate date) {
        Date start = Date.from(date.atTime(9, 0).atZone(ZoneId.of("Asia/Shanghai")).toInstant());
        Date end = new Date(start.getTime() + 7_200_000L);
        jdbc.update("update cr_team_room set start_time=?,end_time=? where resource_id='source'", start, end);
        jdbc.update("update cr_train_record set start_time=?,end_time=? where room_id='source'", start, end);
    }

    private void stamp(BaseTable record) {
        record.setCreateTime(oldStart);
        record.setUpdateTime(new Date(oldStart.getTime() + 14_400_000L));
    }
}
