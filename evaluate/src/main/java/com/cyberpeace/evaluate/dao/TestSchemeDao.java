package com.cyberpeace.evaluate.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.TestScheme;

public interface TestSchemeDao {

	void add(TestScheme testScheme);

	TestScheme selectBySchemeId(String testSchemeId);

	void deleteBySchemeId(String testSchemeId);

	int countAll();

	IPage<TestScheme> selectByPage(Page pageBase);
}
