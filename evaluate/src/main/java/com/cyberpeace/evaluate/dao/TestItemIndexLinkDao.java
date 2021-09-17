package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestItemIndexLink;

public interface TestItemIndexLinkDao {

	void add(TestItemIndexLink testItemIndexLink);

	List<TestItemIndexLink> selectByItemId(String itemId);

	void batchDelete(List<String> ids);
}
