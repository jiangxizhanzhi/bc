package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.TestItemInput;

@Mapper
public interface TestItemInputMapper {

	void insert(TestItemInput testItemInput);

	void update(TestItemInput testItemInput);

	TestItemInput selectByInstanceAndItemId(@Param("instanceId") String instanceId,
			@Param("itemId") String itemId);

	List<TestItemInput> selectByInstance(@Param("instanceId") String instanceId);
}
