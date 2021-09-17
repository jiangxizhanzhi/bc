package com.cyberpeace.evaluate.dao;

import com.cyberpeace.evaluate.dao.entity.TestIndexOut;

public interface TestIndexOutDao {

	void add(TestIndexOut testIndexOut);

	void update(TestIndexOut testIndexOut);

	TestIndexOut selectByInstanceAndIndexId(String instanceId, String itemId, String indexId);
}
