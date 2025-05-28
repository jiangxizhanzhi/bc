/**
 * 这段代码是一个Spring服务类，实现了`SystemConfigService`接口。以下是详细的分析和总结： 
 * ### 类和接口定义 
 * - **类名**: `SystemConfigServiceImpl` 
 * - **包名**: `com.cyberpeace.integratation.service.impl` 
 * - **实现的接口**: `SystemConfigService` 
 * ### 类注释 
 * - 注释说明了该类是系统配置服务的实现，并标注了作者和日期。 
 * ### 类成员变量 
 * - **SYS_CONFIG_URL**: 存储系统配置URL的字符串变量。 
 * - **mpEnvService**: 一个依赖注入的`MpEnvService`对象，用于获取中台环境信息。 
 * ### 构造方法和初始化 
 * - 使用`@Autowired`注解自动装配`MpEnvService`对象。 
 * - 使用`@PostConstruct`注解的方法`init()`在构造后执行，用于初始化`SYS_CONFIG_URL`。 
 * ### 方法 
 * - **init()**: 初始化方法，通过调用`mpEnvService.getMpHost()`和`ConfigUtil.getContextProperty("sys.config.all")`来设置`SYS_CONFIG_URL`。 
 * - **getSystemConfig(String name)**: 实现了从系统配置中根据名称获取配置值的方法。 
 * - 调用`HttpClientUtil.rawGet(SYS_CONFIG_URL, JSONArray.class)`获取系统配置JSON数组。 
 * - 遍历数组，查找与给定名称匹配的配置项并返回其值。 
 * - 如果没有找到匹配项，则返回`null`。 
 * ### 总结 
 * - 这段代码主要功能是从远程系统配置中获取特定配置项的值。 
 * - 使用了Spring框架的依赖注入和生命周期管理特性。 
 * - 使用了阿里巴巴的Fastjson库处理JSON数据。 
 * - 通过HTTP客户端工具类`HttpClientUtil`进行网络请求。 
 * 这段代码展示了如何在一个Spring应用中实现一个简单的配置服务，通过远程API获取配置信息并提供访问接口。 
**/
package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.service.SystemConfigService;

/**
 * 系统配置Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	/**
	 * 系统配置 URL
	 */
	private String SYS_CONFIG_URL;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		SYS_CONFIG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("sys.config.all");
	}

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 获取系统配置
	 *
	 * @param name 名称
	 * @return {@code String }
	 */
	@Override
	public String getSystemConfig(String name) {

		JSONArray configArray = HttpClientUtil.rawGet(SYS_CONFIG_URL, JSONArray.class);
		for (Object obj : configArray) {
			JSONObject jsonObject = (JSONObject) obj;
			if (name.equals(jsonObject.getString("key_name"))) {
				return jsonObject.getString("value");
			}
		}
		return null;
	}
}
