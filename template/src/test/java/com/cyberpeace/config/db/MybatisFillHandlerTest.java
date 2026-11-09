package com.cyberpeace.config.db;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MybatisFillHandlerTest {
    private final MybatisFillHandler handler = new MybatisFillHandler();

    @BeforeEach
    void tableMetadata() {
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), "test"), CrTask.class);
    }

    @Test
    void explicitHistoricalTimesSurviveInsertion() {
        CrTask task = new CrTask();
        Date created = new Date(1_000L), updated = new Date(2_000L);
        task.setCreateTime(created); task.setUpdateTime(updated);
        handler.insertFill(SystemMetaObject.forObject(task));
        assertEquals(created, task.getCreateTime());
        assertEquals(updated, task.getUpdateTime());
    }

    @Test
    void unspecifiedInsertionTimesStillReceiveCurrentTime() {
        CrTask task = new CrTask();
        long before = System.currentTimeMillis();
        handler.insertFill(SystemMetaObject.forObject(task));
        assertTrue(task.getCreateTime().getTime() >= before);
        assertTrue(task.getUpdateTime().getTime() >= before);
    }
}
