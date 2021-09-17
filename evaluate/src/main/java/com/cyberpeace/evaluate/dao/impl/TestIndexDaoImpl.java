package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.TestIndexDao;
import com.cyberpeace.evaluate.dao.entity.TestIndex;
import com.cyberpeace.evaluate.dao.mapper.TestIndexMapper;

@Repository
public class TestIndexDaoImpl implements TestIndexDao {

	@Autowired
	private TestIndexMapper testIndexMapper;

	@Override
	public void add(TestIndex testIndex) {
		testIndexMapper.insert(testIndex);
	}

	@Override
	public List<TestIndex> selectByItemId(String itemId) {
		return testIndexMapper.selectByItemId(itemId);
	}

	@Override
	public void batchDelete(List<String> ids) {
		testIndexMapper.batchDelete(ids);
	}

	@Override
	public TestIndex selectByIndexId(String indexId) {
		return testIndexMapper.selectByIndexId(indexId);
	}
}
