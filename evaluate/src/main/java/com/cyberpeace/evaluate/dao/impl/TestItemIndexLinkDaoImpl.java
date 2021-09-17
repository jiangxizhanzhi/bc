package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.TestItemIndexLinkDao;
import com.cyberpeace.evaluate.dao.entity.TestItemIndexLink;
import com.cyberpeace.evaluate.dao.mapper.TestItemIndexLinkMapper;

@Repository
public class TestItemIndexLinkDaoImpl implements TestItemIndexLinkDao {

	@Autowired
	private TestItemIndexLinkMapper testItemIndexLinkMapper;

	@Override
	public void add(TestItemIndexLink testItemIndexLink) {
		testItemIndexLinkMapper.insert(testItemIndexLink);
	}

	@Override
	public List<TestItemIndexLink> selectByItemId(String itemId) {
		return testItemIndexLinkMapper.selectByItemId(itemId);
	}

	@Override
	public void batchDelete(List<String> ids) {
		testItemIndexLinkMapper.batchDelete(ids);
	}
}
