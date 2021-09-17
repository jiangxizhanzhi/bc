package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.TestIndexOutDao;
import com.cyberpeace.evaluate.dao.entity.TestIndexOut;
import com.cyberpeace.evaluate.dao.mapper.TestIndexOutMapper;

@Repository
public class TestIndexOutDaoImpl implements TestIndexOutDao {

	@Autowired
	private TestIndexOutMapper testIndexOutMapper;

	@Override
	public void add(TestIndexOut testIndexOut) {
		testIndexOutMapper.insert(testIndexOut);
	}

	@Override
	public void update(TestIndexOut testIndexOut) {
		testIndexOutMapper.update(testIndexOut);
	}

	@Override
	public TestIndexOut selectByInstanceAndIndexId(String instanceId, String itemId,
			String indexId) {
		return testIndexOutMapper.selectByInstanceAndIndexId(instanceId, itemId, indexId);
	}
}
