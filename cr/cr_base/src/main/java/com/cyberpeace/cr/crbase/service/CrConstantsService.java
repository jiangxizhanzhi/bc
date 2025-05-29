package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

/**
 * 靶场常数Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CrConstantsService` 的接口，用于处理靶场常量相关的业务逻辑。以下是对其的简要分析： 
 * 1. **接口定义**： 
 * - 接口位于包 `com.cyberpeace.cr.crbase.service` 下。 
 * - 定义了三个方法，分别用于按类型获取常量配置、校验常量配置是否存在以及根据类型和代号获取中文名称。 
 * 2. **方法说明**： 
 * - `getByType(Integer type)`： 
 * 根据传入的常量类型 `type` 获取对应的常量列表。返回值为 `List<CrConstantsConfig>`，其中 `CrConstantsConfig` 是一个实体类。 
 * - `validateType(Integer type, Integer code)`： 
 * 校验指定类型的常量配置中是否存在某个代号 `code` 的常量。返回布尔值 `Boolean`，表示是否存在。 
 * - `geCnNameByTypeAndCode(Integer type, Integer code)`： 
 * 根据类型 `type` 和代号 `code` 获取对应的中文名称，返回值为 `String`。 
 * 3. **用途**： 
 * 这个接口主要用于管理靶场中的常量配置，包括查询、验证和翻译等操作，适用于需要动态加载或校验常量的场景。 
 * 总结：这是一个典型的业务服务接口，通过定义清晰的方法签名来提供常量相关的功能支持。 
**/
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

