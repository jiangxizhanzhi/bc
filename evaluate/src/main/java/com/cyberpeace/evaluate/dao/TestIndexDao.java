package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestIndex;

public interface TestIndexDao {

	void add(TestIndex testIndex);

	List<TestIndex> selectByItemId(String itemId);

	void batchDelete(List<String> ids);

	TestIndex selectByIndexId(String indexId);
}
