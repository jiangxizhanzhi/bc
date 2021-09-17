package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.TestSubActionOutputDao;
import com.cyberpeace.evaluate.dao.entity.TestSubActionOutput;
import com.cyberpeace.evaluate.dao.mapper.TestSubActionOutputMapper;

@Repository
public class TestSubActionOutputDaoImpl implements TestSubActionOutputDao {

	@Autowired
	private TestSubActionOutputMapper testSubActionOutputMapper;

	@Override
	public void add(TestSubActionOutput testSubActionOutput) {
		testSubActionOutputMapper.insert(testSubActionOutput);
	}

	@Override
	public void update(TestSubActionOutput testSubActionOutput) {
		testSubActionOutputMapper.update(testSubActionOutput);
	}

	@Override
	public void deleteByInstanceAndItemId(String instanceId, String itemId) {
		testSubActionOutputMapper.deleteByInstanceAndItemId(instanceId, itemId);
	}

	@Override
	public TestSubActionOutput select(String instanceId, String itemId, String actionId) {
		return testSubActionOutputMapper.select(instanceId, itemId, actionId);
	}
}
