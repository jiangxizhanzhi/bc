package com.cyberpeace.evaluate.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.TestIndexOut;

@Mapper
public interface TestIndexOutMapper {

	void insert(TestIndexOut testIndexOut);

	void update(TestIndexOut testIndexOut);

	TestIndexOut selectByInstanceAndIndexId(@Param("instanceId") String instanceId,
			@Param("itemId") String itemId, @Param("indexId") String indexId);
}
