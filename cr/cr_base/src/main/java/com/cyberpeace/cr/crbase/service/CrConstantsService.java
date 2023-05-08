package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

/**
 * cr常量服务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface CrConstantsService {

	/**
	 * 按类获取常量配置
	 * @param type 常量类型
	 * @return 常量列表
	 */
	List<CrConstantsConfig> getByType(Integer type);

	/**
	 * 校验常量配置是否存在
	 * @param type 常量类型
	 * @param code 常量代号
	 * @return 是否存在
	 */
	Boolean validateType(Integer type, Integer code);

}
