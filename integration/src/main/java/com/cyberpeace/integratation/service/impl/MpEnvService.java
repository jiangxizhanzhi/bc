package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 中台环境Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpEnvService` 的 Spring 服务类，用于初始化和获取中台环境的相关配置信息。以下是对代码的简要分析： 
 * ### 类定义与注解 
 * - **类名**: `MpEnvService` 
 * - **包名**: `com.cyberpeace.integratation.service.impl` 
 * - **注解**: 
 * - `@Slf4j`: 使用 Lombok 自动生成日志对象 `log`。 
 * - `@Service`: 标识该类为一个 Spring 服务组件。 
 * ### 成员变量 
 * - **MP_HOST**: 存储中台主机地址。 
 * - **MP_PORT**: 存储中台端口。 
 * - **MP_URL**: 存储中台网址（由主机地址和端口拼接而成）。 
 * ### 方法 
 * - **getMpHost()**: 返回中台主机地址。但是这里返回的是 `MP_URL` 而不是 `MP_HOST`，这可能是一个错误。 
 * - **init()**: 使用 `@PostConstruct` 注解，表示该方法在构造函数执行完毕后自动调用。 
 * - 从系统属性中读取 `MP_HOST` 和 `MP_PORT`。 
 * - 构建 `MP_URL` 并赋值。 
 * - 记录日志输出 `MP_HOST` 和 `MP_PORT` 的值。 
 * ### 总结 
 * 1. **功能**: 该服务类主要用于初始化并存储中台环境的主机、端口和网址，并提供获取主机的方法。 
 * 2. **问题**: `getMpHost()` 方法返回的是 `MP_URL` 而不是 `MP_HOST`，这可能导致逻辑错误。 
 * 3. **改进**: 应将 `getMpHost()` 方法中的 `return MP_URL;` 改为 `return MP_HOST;`。 
 * 总体来说，这段代码实现了基本的功能，但存在一个小错误需要修正。 
**/
@Slf4j
@Service
public class MpEnvService {

	/**
	 * 中台主机
	 */
	private String MP_HOST;

	/**
	 * 中台港口
	 */
	private String MP_PORT;

	/**
	 * 中台网址
	 */
	private String MP_URL;

	/**
	 * 获取中台主机
	 *
	 * @return {@code String }
	 */
	public String getMpHost() {
		return MP_URL;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		MP_HOST = SystemPropUtil.getProp("MP_HOST");
		MP_PORT = SystemPropUtil.getProp("MP_PORT");
		MP_URL = "http://" + MP_HOST + ":" + MP_PORT;

		log.info("mp host=[{}], port=[{}]", MP_HOST, MP_PORT);
	}
}

