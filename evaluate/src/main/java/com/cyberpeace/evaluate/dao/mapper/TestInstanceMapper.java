package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.TestInstance;

@Mapper
public interface TestInstanceMapper {

    void insert(TestInstance testInstance);

    void update(TestInstance testInstance);

    TestInstance selectByInstanceId(String instanceId);

    List<TestInstance> selectAllInstance(String infoId);

    TestInstance selectLastByStatus(@Param("infoId") String infoId, @Param("status") int status);
}
