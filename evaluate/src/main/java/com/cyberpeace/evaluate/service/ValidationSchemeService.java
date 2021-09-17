package com.cyberpeace.evaluate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.evaluate.service.bo.TestSchemeBO;
import com.cyberpeace.evaluate.service.context.TestSchemeContext;

public interface ValidationSchemeService {

	/**
	 *
	 * @param testSchemeAddContext
	 */
	String addScheme(TestSchemeContext testSchemeAddContext);

	/**
	 * 删除指定测试方案
	 *
	 * @param testSchemeId
	 */
	void deleteScheme(String testSchemeId);

	void modifyScheme(TestSchemeContext testSchemeAddContext);

	TestSchemeContext getBySchemeId(String schemeId);

	ListRsp<TestSchemeBO> schemeList(Page pageBase);
}
