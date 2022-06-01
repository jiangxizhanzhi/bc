package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

public interface CrConstantsService {

	/**
	 * 按类获取常量配置
	 * @param type
	 * @return
	 */
	List<CrConstantsConfig> getByType(Integer type);

	/**
	 * 校验常量配置是否存在
	 * @param type
	 * @param code
	 * @return
	 */
	Boolean validateType(Integer type, Integer code);

}
