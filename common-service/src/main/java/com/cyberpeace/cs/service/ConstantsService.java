package com.cyberpeace.cs.service;

import com.cyberpeace.cs.dao.entity.ConstantsConfig;

import java.util.List;

public interface ConstantsService {

	/**
	 * 按类获取常量配置
	 * @param type 常量类型
	 * @return 常量列表
	 */
	List<ConstantsConfig> getByType(Integer type);

}
