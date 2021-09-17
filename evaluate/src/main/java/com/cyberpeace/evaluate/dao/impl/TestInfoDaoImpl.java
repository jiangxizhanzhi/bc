package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.TestInfoDao;
import com.cyberpeace.evaluate.dao.entity.TestInfo;
import com.cyberpeace.evaluate.dao.mapper.TestInfoMapper;

@Repository
public class TestInfoDaoImpl implements TestInfoDao {

	@Autowired
	private TestInfoMapper testInfoMapper;

	@Override
	public TestInfo selectByInfoId(String infoId) {
		return testInfoMapper.selectByInfoId(infoId);
	}

	@Override
	public IPage<TestInfo> pageByUserId(String userId, Page pageBase) {
		return testInfoMapper.pageByUserId(pageBase, userId);
	}

	@Override
	public Integer countByUserId(String userId) {
		return testInfoMapper.countByUserId(userId);
	}

	@Override
	public void add(TestInfo testInfo) {
		testInfoMapper.insert(testInfo);
	}

	@Override
	public void deleteByInfoId(String infoId) {
		testInfoMapper.deleteByInfoId(infoId);
	}
}
