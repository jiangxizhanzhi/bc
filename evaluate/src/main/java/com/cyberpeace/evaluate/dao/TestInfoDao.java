package com.cyberpeace.evaluate.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.TestInfo;

public interface TestInfoDao {

	TestInfo selectByInfoId(String infoId);

	IPage<TestInfo> pageByUserId(String userId, Page pageBase);

	Integer countByUserId(String userId);

	void add(TestInfo testInfo);

	void deleteByInfoId(String infoId);
}
