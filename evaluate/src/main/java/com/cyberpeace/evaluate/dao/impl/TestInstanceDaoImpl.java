package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.TestInstanceDao;
import com.cyberpeace.evaluate.dao.entity.TestInstance;
import com.cyberpeace.evaluate.dao.mapper.TestInstanceMapper;

@Repository
public class TestInstanceDaoImpl implements TestInstanceDao {

	@Autowired
	private TestInstanceMapper testInstanceMapper;

	@Override
	public void add(TestInstance testInstance) {
		testInstanceMapper.insert(testInstance);
	}

	@Override
	public TestInstance selectByInstanceId(String instanceId) {
		return testInstanceMapper.selectByInstanceId(instanceId);
	}

	@Override
	public void update(TestInstance testInstance) {
		testInstanceMapper.update(testInstance);
	}

	@Override
	public List<TestInstance> selectAllInstance(String infoId) {
		return testInstanceMapper.selectAllInstance(infoId);
	}

	@Override
	public TestInstance selectLastByStatus(String infoId, int status) {
		return testInstanceMapper.selectLastByStatus(infoId, status);
	}
}
