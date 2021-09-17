package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestInstance;

public interface TestInstanceDao {

	void add(TestInstance testInstance);

	TestInstance selectByInstanceId(String instanceId);

	void update(TestInstance testInstance);

	List<TestInstance> selectAllInstance(String infoId);

	/**
	 * 查询最近一台运行中的测评实例
	 * @param status
	 * @param infoId
	 * @return
	 */
	TestInstance selectLastByStatus(String infoId, int status);
}
