package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestItemLink;

public interface TestItemLinkDao {

	void add(TestItemLink testItemLink);

	List<TestItemLink> selectBySchemeId(String schemeId);

	void deleteBySchemeId(String schemeId);
}
