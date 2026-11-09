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
    private final LocalDate day = LocalDate.of(2026, 11, 8);
    private final Date oldStart = Date.from(LocalDate.of(2026, 10, 1).atTime(9, 0)
            .atZone(ZoneId.of("Asia/Shanghai")).toInstant());

    @BeforeEach
    void setUp() throws Exception {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE");
        jdbc = new JdbcTemplate(ds);
        for (Class<?> type : Arrays.asList(TeamRoom.class, CrRole.class, Mission.class, CrTask.class,
                CrTaskSubmit.class, CrTaskLog.class, CrScore.class, TrainRecord.class, CourseLessonUserRelation.class)) {
            createTable(type);
        }
        jdbc.execute("create table cr_team_template (resource_id varchar(64), business_template_id varchar(64), duration_time int)");
        jdbc.execute("create table local_scene (business_instance_id varchar(64), start_time timestamp, destroy_time timestamp, scene_status int)");
        new ResourceDatabasePopulator(new FileSystemResource("../../template/src/main/resources/static/sql/bc/V5.6.0.20261109.1__demo_data.sql")).execute(ds);
        MybatisConfiguration config = new MybatisConfiguration();
        config.setMapUnderscoreToCamelCase(true);
        List<Class<?>> mappers = Arrays.asList(TeamRoomMapper.class, XdDemoDataMapper.class, CrRoleMapper.class,
                MissionMapper.class, CrTaskMapper.class, CrTaskSubmitMapper.class, CrTaskLogMapper.class,
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
        Map<String, UserInfo> users = new HashMap<>();
        users.put("teacher", new UserInfo());
        users.put("student", new UserInfo());
        when(userService.getIdUserInfoMap(anyList())).thenReturn(users);
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
    void clonesLinkedBusinessRecordsAndPreservesTheSource() {
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
        assertEquals(day, room.getStartTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).toLocalDate());
        assertEquals(7_200_000L, room.getEndTime().getTime() - room.getStartTime().getTime());
        assertEquals("topo", session.getMapper(TeamRoomMapper.class).selectById("source").getCopyedTopoId());
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task where room_id=? and resource_id <> 'task' and mission_id <> 'mission' and role_id <> 'student-role'", Integer.class, roomId));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task t join cr_mission m on t.mission_id=m.resource_id join cr_role r on t.role_id=r.resource_id where t.room_id=? and m.room_id=t.room_id and r.room_id=t.room_id", Integer.class, roomId));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task_submit s join cr_task t on s.task_id=t.resource_id where s.room_id=? and s.room_id=t.room_id", Integer.class, roomId));
        assertEquals("teacher", jdbc.queryForObject("select evaluator_id from cr_task_submit where room_id=?", String.class, roomId));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_task_log l join cr_task t on l.task_id=t.resource_id join cr_mission m on l.mission_id=m.resource_id where l.room_id=? and l.room_id=t.room_id and l.room_id=m.room_id", Integer.class, roomId));
        CrRole role = session.getMapper(CrRoleMapper.class).getRolesByRoomId(roomId).stream().filter(CrRole::isTeamStudent).findFirst().get();
        String missionId = jdbc.queryForObject("select resource_id from cr_mission where room_id=?", String.class, roomId);
        assertEquals(Collections.singletonList(missionId), role.getMissionStageSchemes().get(0).getMissions());
        assertEquals("演习文书：保留原文与附件引用", role.getExercisePaper());
        assertEquals("[\"student\"]", role.getUserIds());
        assertEquals(176, jdbc.queryForObject("select sum(final_score) from cr_score where user_id='student'", Integer.class));
        assertEquals(2, jdbc.queryForObject("select count(*) from course_lesson_user_relation where user_id='student' and course_id='course'", Integer.class));
        assertEquals(1, session.getMapper(XdDemoDataMapper.class).selectSources().size());
        CrTask task = session.getMapper(CrTaskMapper.class).selectList(new QueryWrapper<CrTask>().eq("room_id", roomId)).get(0);
        assertEquals(room.getStartTime().getTime() + 3_600_000L, task.getLastSubmitTime().getTime());
        assertEquals(room.getStartTime(), task.getCreateTime());
        verify(topoService).batchGetTopoDigest(Collections.singletonList("topo"));
        verifyNoMoreInteractions(topoService);
    }

    @Test
    void twoRoomDayUsesIndependentIdsAndOneExecutionRecord() {
        when(random.nextInt(10)).thenReturn(9);
        service.generate(day);
        assertEquals(2, jdbc.queryForObject("select generated_count from cr_demo_data_run", Integer.class));
        assertEquals(2, jdbc.queryForObject("select count(distinct room_id) from cr_demo_data_room", Integer.class));
        assertEquals(3, jdbc.queryForObject("select count(distinct resource_id) from cr_task", Integer.class));
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
        assertEquals(88, report.getGetScoreRsps().get(0).getSynthesisScore());
        TrainReportExportContext export = BeanUtils.deepCopy(report, TrainReportExportContext.class);
        export.initExport();
        assertEquals(88, export.getGetScoreRsps().get(0).getSynthesisScore());
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
    void brokenSamplesDoNotLeaveASuccessMarker() {
        jdbc.update("update cr_task set mission_id='missing'");
        service.generate(day);
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_demo_data_run", Integer.class));
        assertEquals(1, jdbc.queryForObject("select count(*) from cr_team_room", Integer.class));
    }

    @Test
    void documentTextAndStageNamesArePreservedWhileMissionIdsAreRemapped() {
        jdbc.update("update cr_role set exercise_paper=?, mission_scheme=? where resource_id='student-role'",
                "mission", "[{\"stage\":\"mission\",\"missions\":[\"mission\"],\"dynamic\":[]}]");
        service.generate(day);
        String roomId = jdbc.queryForObject("select room_id from cr_demo_data_room", String.class);
        CrRole role = session.getMapper(CrRoleMapper.class).getRolesByRoomId(roomId).stream()
                .filter(CrRole::isTeamStudent).findFirst().get();
        assertEquals("mission", role.getExercisePaper());
        assertEquals("mission", role.getMissionStageSchemes().get(0).getStage());
        assertNotEquals("mission", role.getMissionStageSchemes().get(0).getMissions().get(0));
    }

    @Test
    void missingHistoricalAuditTimesUseTheGeneratedExperimentWindow() {
        jdbc.update("update cr_role set exercise_paper=null");
        jdbc.update("update cr_task set create_time=null, update_time=null where resource_id='task'");
        service.generate(day);
        String roomId = jdbc.queryForObject("select room_id from cr_demo_data_room", String.class);
        TeamRoom room = session.getMapper(TeamRoomMapper.class).selectById(roomId);
        CrTask task = session.getMapper(CrTaskMapper.class)
                .selectList(new QueryWrapper<CrTask>().eq("room_id", roomId)).get(0);
        assertEquals(room.getStartTime(), task.getCreateTime());
        assertEquals(room.getEndTime(), task.getUpdateTime());
        assertNull(jdbc.queryForObject("select create_time from cr_task where resource_id='task'", Date.class));
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
        ReflectionTestUtils.setField(service, "timeZone", "Pacific/Kiritimati");
        service.automatic();
        assertEquals(LocalDate.now(ZoneId.of("Pacific/Kiritimati")).minusDays(1).toString(),
                jdbc.queryForObject("select business_date from cr_demo_data_run", String.class));
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
        jdbc.update("insert into cr_team_template values ('template','business-template',2)");
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
        mission.setType(1); mission.setCategoryId("category");
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

    private void stamp(BaseTable record) {
        record.setCreateTime(oldStart);
        record.setUpdateTime(new Date(oldStart.getTime() + 14_400_000L));
    }
}
