package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.TestItemDao;
import com.cyberpeace.evaluate.dao.entity.TestItem;
import com.cyberpeace.evaluate.dao.mapper.TestItemMapper;

@Repository
public class TestItemDaoImpl implements TestItemDao {

	@Autowired
	private TestItemMapper testItemMapper;

	@Override
	public void add(TestItem testItem) {
		testItemMapper.insert(testItem);
	}

	@Override
	public List<TestItem> selectBySchemeId(String schemeId) {
		return testItemMapper.selectBySchemeId(schemeId);
	}

	@Override
	public void deleteBySchemeId(String schemeId) {
		testItemMapper.deleteBySchemeId(schemeId);
	}

	@Override
	public TestItem selectByItemId(String itemId) {
		return testItemMapper.selectByItemId(itemId);
	}
}
