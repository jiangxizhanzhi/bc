package com.cyberpeace.evaluate.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.TestSubActionOutput;

@Mapper
public interface TestSubActionOutputMapper {

	void insert(TestSubActionOutput testSubActionOutput);

	void update(TestSubActionOutput testSubActionOutput);

	void deleteByInstanceAndItemId(@Param("instanceId") String instanceId,
			@Param("itemId") String itemId);

	TestSubActionOutput select(@Param("instanceId") String instanceId,
			@Param("itemId") String itemId, @Param("actionId") String actionId);
}
