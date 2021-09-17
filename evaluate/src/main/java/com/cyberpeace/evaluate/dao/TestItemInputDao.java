package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestItemInput;

public interface TestItemInputDao {

    void add(TestItemInput testItemInput);

    void update(TestItemInput testItemInput);

    TestItemInput selectByInstanceAndItemId(String instanceId, String itemId);

    List<TestItemInput> selectByInstance(String instanceId);
}
