/**
 * 这段代码定义了一个名为 `AdEnvService` 的 Spring 服务类，用于处理与竞赛环境相关的配置。以下是代码的主要部分及其功能分析： 
 * ### 类和字段定义 
 * - **类定义**： 
 * - `AdEnvService` 是一个 `@Service` 注解的类，表示这是一个 Spring 管理的服务类。 
 * - **字段**： 
 * - `private String AD_HOST;`：存储竞赛主机名。 
 * - `private String AD_PORT;`：存储竞赛端口号。 
 * - `private String AD_URL;`：存储竞赛的完整 URL。 
 * ### 方法定义 
 * - **`getAdHost()` 方法**： 
 * - 返回竞赛主机名。 
 * - 如果 `AD_HOST` 字段为空，则返回空字符串（由 `CommonConstant.EMPTY_STRING` 定义）。 
 * - 注意这里返回的是 `AD_URL` 而不是 `AD_HOST`，这可能是代码中的一个小错误。 
 * - **`init()` 方法**： 
 * - 使用 `@PostConstruct` 注解，表示这个方法会在依赖注入完成后自动调用。 
 * - 从系统属性中读取竞赛主机 (`AD_HOST`) 和端口 (`AD_PORT`)。 
 * - 根据读取到的主机名和端口号构建完整的竞赛 URL (`AD_URL`)。 
 * - 记录日志信息，显示读取到的竞赛主机和端口。 
 * ### 总结 
 * - 这个类主要用于从系统属性中读取竞赛环境的相关配置，并初始化这些配置。 
 * - 存在一个小错误，即 `getAdHost()` 方法应该返回 `AD_HOST` 而不是 `AD_URL`。 
 * - 使用了 Lombok 的 `@Slf4j` 注解来简化日志记录。 
 * ### 建议改进 
 * - 修改 `getAdHost()` 方法，使其正确返回 `AD_HOST`。 
 * - 可以考虑添加更多的错误处理逻辑，例如在读取系统属性失败时提供默认值或抛出异常。 
**/
package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 竞赛环境Service
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class AdEnvService {

	/**
	 * 竞赛主机
	 */
	private String AD_HOST;

	/**
	 * 竞赛港口
	 */
	private String AD_PORT;

	/**
	 * 竞赛网址
	 */
	private String AD_URL;

	/**
	 * 获取竞赛主机
	 *
	 * @return {@code String }
	 */
	public String getAdHost() {
		if (StringUtils.isBlank(AD_HOST)) {
			return CommonConstant.EMPTY_STRING;
		}
		return AD_URL;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		AD_HOST = SystemPropUtil.getProp("AD_HOST");
		AD_PORT = SystemPropUtil.getProp("AD_PORT");
		AD_URL = "http://" + AD_HOST + ":" + AD_PORT;

		log.info("ad host=[{}], port=[{}]", AD_HOST, AD_PORT);
	}
}
