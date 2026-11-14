package com.cyberpeace.cr.singletrain.service.impl;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.impl.TrainRecordServiceImpl;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SingleRoomCleanupIdempotencyTest {
    private SingleRoomServiceImpl service;
    private JdbcTemplate jdbc;
    private SingleRoom room;

    @BeforeEach
    void setUp() throws Exception {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE");
        jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("create table cr_single_room (resource_id varchar(64) primary key)");
        jdbc.execute("create table cr_train_record (room_id varchar(64), end_time timestamp, extra varchar(4096))");
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.addMapper(SingleRoomMapper.class);
        configuration.addMapper(TrainRecordMapper.class);
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setConfiguration(configuration);
        factory.setMapperLocations(new ClassPathResource("mapper/cr/TrainRecordMapper.xml"));
        SqlSessionTemplate session = new SqlSessionTemplate(factory.getObject());
        TrainRecordServiceImpl records = new TrainRecordServiceImpl();
        ReflectionTestUtils.setField(records, "trainRecordMapper", session.getMapper(TrainRecordMapper.class));
        service = new SingleRoomServiceImpl();
        ReflectionTestUtils.setField(service, "singleRoomMapper", session.getMapper(SingleRoomMapper.class));
        ReflectionTestUtils.setField(service, "localSceneService", mock(LocalSceneService.class));
        ReflectionTestUtils.setField(service, "trainRecordService", records);
        ReflectionTestUtils.setField(service, "transactionTemplate",
                new TransactionTemplate(new DataSourceTransactionManager(dataSource)));
        room = new SingleRoom();
        room.setResourceId("room");
        room.setLocalSceneId("already-deleted-local");
        room.setUserId("student");
    }

    @Test
    void repeatedCleanupKeepsExistingEndTimeAndSubmittedScores() {
        Timestamp ended = Timestamp.valueOf("2026-01-01 12:30:00");
        jdbc.update("insert into cr_single_room values (?)", "room");
        jdbc.update("insert into cr_train_record values (?, ?, ?)", "room", ended, "{\"score\":85}");
        service.deleteRoomForTemplateDeletion(room);
        service.deleteRoomForTemplateDeletion(room);
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_single_room", Integer.class));
        assertEquals(ended, jdbc.queryForObject("select end_time from cr_train_record", Timestamp.class));
        assertEquals("{\"score\":85}", jdbc.queryForObject("select extra from cr_train_record", String.class));
    }

    @Test
    void roomAlreadyRemovedStillFinishesTheRemainingTrainingRecord() {
        jdbc.update("insert into cr_train_record values (?, null, ?)", "room", "{\"answer\":\"submitted\"}");
        service.deleteRoomForTemplateDeletion(room);
        assertNotNull(jdbc.queryForObject("select end_time from cr_train_record", Timestamp.class));
        assertEquals("{\"answer\":\"submitted\"}", jdbc.queryForObject("select extra from cr_train_record", String.class));
    }

    @Test
    void roomAndTrainingRecordAlreadyGoneAreACompletedCleanup() {
        assertDoesNotThrow(() -> service.deleteRoomForTemplateDeletion(room));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_single_room", Integer.class));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_train_record", Integer.class));
    }

    @Test
    void deletionNotificationFinishesTrainingBeforeTheRoomDisappears() {
        jdbc.update("insert into cr_single_room values (?)", "room");
        jdbc.update("insert into cr_train_record values (?, null, ?)", "room", "{\"score\":85}");
        service.clearLocal(room);
        Timestamp ended = jdbc.queryForObject("select end_time from cr_train_record", Timestamp.class);
        assertNotNull(ended);
        service.clearLocal(room);
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_single_room", Integer.class));
        assertEquals(ended, jdbc.queryForObject("select end_time from cr_train_record", Timestamp.class));
        assertEquals("{\"score\":85}", jdbc.queryForObject("select extra from cr_train_record", String.class));
    }

    @Test
    void sceneLookupCleanupAlsoFinishesTrainingWhenLocalSceneIsAlreadyGone() {
        jdbc.update("insert into cr_single_room values (?)", "room");
        jdbc.update("insert into cr_train_record values (?, null, ?)", "room", "{\"score\":85}");
        assertNull(ReflectionTestUtils.invokeMethod(service, "getMpSceneData", room));
        assertEquals(0, jdbc.queryForObject("select count(*) from cr_single_room", Integer.class));
        assertNotNull(jdbc.queryForObject("select end_time from cr_train_record", Timestamp.class));
        assertEquals("{\"score\":85}", jdbc.queryForObject("select extra from cr_train_record", String.class));
    }
}
