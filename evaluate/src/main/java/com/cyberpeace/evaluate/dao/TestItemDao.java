package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestItem;

public interface TestItemDao {

    void add(TestItem testItem);

    List<TestItem> selectBySchemeId(String schemeId);

    void deleteBySchemeId(String schemeId);

    TestItem selectByItemId(String itemId);
}
