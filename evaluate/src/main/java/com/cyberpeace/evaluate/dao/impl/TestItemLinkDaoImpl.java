package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.TestItemLinkDao;
import com.cyberpeace.evaluate.dao.entity.TestItemLink;
import com.cyberpeace.evaluate.dao.mapper.TestItemLinkMapper;

@Repository
public class TestItemLinkDaoImpl implements TestItemLinkDao {

	@Autowired
	private TestItemLinkMapper testItemLinkMapper;

	@Override
	public void add(TestItemLink testItemLink) {
		testItemLinkMapper.insert(testItemLink);
	}

	@Override
	public List<TestItemLink> selectBySchemeId(String schemeId) {
		return testItemLinkMapper.selectBySchemeId(schemeId);
	}

	@Override
	public void deleteBySchemeId(String schemeId) {
		testItemLinkMapper.deleteBySchemeId(schemeId);
	}
}
