package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.TestItemInputDao;
import com.cyberpeace.evaluate.dao.entity.TestItemInput;
import com.cyberpeace.evaluate.dao.mapper.TestItemInputMapper;

@Repository
public class TestItemInputDaoImpl implements TestItemInputDao {

	@Autowired
	private TestItemInputMapper testItemInputMapper;

	@Override
	public void add(TestItemInput testItemInput) {
		testItemInputMapper.insert(testItemInput);
	}

	@Override
	public void update(TestItemInput testItemInput) {
		testItemInputMapper.update(testItemInput);
	}

	@Override
	public TestItemInput selectByInstanceAndItemId(String instanceId, String itemId) {
		return testItemInputMapper.selectByInstanceAndItemId(instanceId, itemId);
	}

	@Override
	public List<TestItemInput> selectByInstance(String instanceId) {
		return testItemInputMapper.selectByInstance(instanceId);
	}
}
