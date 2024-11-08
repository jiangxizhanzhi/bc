package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

/**
 * 靶场常数Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface CrConstantsService {

	/**
	 * 按类获取常量配置
	 *
	 * @param type 常量类型
	 * @return 常量列表
	 */
	List<CrConstantsConfig> getByType(Integer type);

	/**
	 * 校验常量配置是否存在
	 *
	 * @param type 常量类型
	 * @param code 常量代号
	 * @return 是否存在
	 */
	Boolean validateType(Integer type, Integer code);

	/**
	 * 通用 电气中文名称由类型和代号
	 *
	 * @param type 类型
	 * @param code 代号
	 * @return {@code String }
	 */
	String geCnNameByTypeAndCode(Integer type, Integer code);

}
