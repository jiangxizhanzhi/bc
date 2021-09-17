package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.TestSchemeDao;
import com.cyberpeace.evaluate.dao.entity.TestScheme;
import com.cyberpeace.evaluate.dao.mapper.TestSchemeMapper;

@Repository
public class TestSchemeDaoImpl implements TestSchemeDao {

	@Autowired
	private TestSchemeMapper testSchemeMapper;

	@Override
	public void add(TestScheme testScheme) {
		testSchemeMapper.insert(testScheme);
	}

	@Override
	public TestScheme selectBySchemeId(String testSchemeId) {
		return testSchemeMapper.selectBySchemeId(testSchemeId);
	}

	@Override
	public void deleteBySchemeId(String testSchemeId) {
		testSchemeMapper.deleteBySchemeId(testSchemeId);
	}

	@Override
	public int countAll() {
		return testSchemeMapper.countAll();
	}

	@Override
	public IPage<TestScheme> selectByPage(Page pageBase) {
		return testSchemeMapper.selectPage(pageBase, null);
	}
}
