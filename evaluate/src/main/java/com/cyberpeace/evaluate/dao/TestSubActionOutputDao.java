package com.cyberpeace.evaluate.dao;

import com.cyberpeace.evaluate.dao.entity.TestSubActionOutput;

public interface TestSubActionOutputDao {

	void add(TestSubActionOutput testSubActionOutput);

	void update(TestSubActionOutput testSubActionOutput);

	void deleteByInstanceAndItemId(String instanceId, String itemId);

	TestSubActionOutput select(String instanceId, String itemId, String actionId);
}
