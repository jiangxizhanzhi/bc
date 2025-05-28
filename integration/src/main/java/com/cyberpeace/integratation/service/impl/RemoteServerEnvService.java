/**
 * 这段代码定义了一个名为 `RemoteServerEnvService` 的 Spring 服务类，用于初始化和提供远程服务器（包括办公室服务器和文件服务器）的URL。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.integratation.service.impl` 
 * - **导入的库**： 
 * - `com.cyberpeace.common.util.SystemPropUtil`：用于获取系统属性。 
 * - `org.apache.commons.lang3.StringUtils`：用于字符串处理。 
 * - `org.springframework.stereotype.Service`：用于标记这是一个 Spring 服务类。 
 * - `javax.annotation.PostConstruct`：用于标记在依赖注入完成后需要执行的方法。 
 * ### 类注释 
 * - 文档说明了该类的功能是处理远程服务器环境，并指出了作者和日期。 
 * ### 类成员变量 
 * - **办公室服务器**： 
 * - `OFFICE_SERVER_HOST`：办公室服务器的主机名。 
 * - `OFFICE_SERVER_PORT`：办公室服务器的端口号。 
 * - `OFFICE_SERVER_URL`：办公室服务器的完整URL。 
 * - **文件服务器**： 
 * - `FILE_SERVER_HOST`：文件服务器的主机名。 
 * - `FILE_SERVER_PORT`：文件服务器的端口号。 
 * - `FILE_SERVER_URL`：文件服务器的完整URL。 
 * ### 方法 
 * - **getOfficeHost()** 
 * - 返回办公室服务器的URL。 
 * - **getFileServerHost()** 
 * - 返回文件服务器的URL。 
 * - **init()** 
 * - 使用 `@PostConstruct` 注解标记该方法在依赖注入完成后执行。 
 * - 从系统属性中读取服务器的主机名和端口号。 
 * - 根据主机名是否以 `http` 开头，构建完整的URL。 
 * - 记录日志，输出初始化后的服务器URL。 
 * ### 总结 
 * 这个类的主要功能是在应用启动时初始化办公室服务器和文件服务器的相关信息，并提供方法来获取这些服务器的URL。通过使用 `@PostConstruct` 注解，确保在依赖注入完成后进行必要的初始化操作。 
**/
package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 远程服务器环境Service
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class RemoteServerEnvService {

	/**
	 * Office Server 主机
	 */
	private String OFFICE_SERVER_HOST;

	/**
	 * Office 服务器端口
	 */
	private String OFFICE_SERVER_PORT;

	/**
	 * Office 服务器 URL
	 */
	private String OFFICE_SERVER_URL;

	/**
	 * 文件服务器主机
	 */
	private String FILE_SERVER_HOST;

	/**
	 * 文件服务器端口
	 */
	private String FILE_SERVER_PORT;

	/**
	 * 文件服务器 URL
	 */
	private String FILE_SERVER_URL;

	/**
	 * 获取办公室主机
	 *
	 * @return {@code String }
	 */
	public String getOfficeHost() {
		return OFFICE_SERVER_URL;
	}

	/**
	 * 获取文件服务器主机
	 *
	 * @return {@code String }
	 */
	public String getFileServerHost() {
		return FILE_SERVER_URL;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		OFFICE_SERVER_HOST = SystemPropUtil.getProp("OFFICE_SERVER_HOST");
		OFFICE_SERVER_PORT = SystemPropUtil.getProp("OFFICE_SERVER_PORT");

		if (StringUtils.isNotBlank(OFFICE_SERVER_HOST) && OFFICE_SERVER_HOST.startsWith("http")) {
			OFFICE_SERVER_URL = OFFICE_SERVER_HOST + ":" + OFFICE_SERVER_PORT;
		} else {
			OFFICE_SERVER_URL = "http://" + OFFICE_SERVER_HOST + ":" + OFFICE_SERVER_PORT;
		}

		log.info("Office server url=[{}]", OFFICE_SERVER_URL);

		FILE_SERVER_HOST = SystemPropUtil.getProp("FILE_SERVER_HOST");
		FILE_SERVER_PORT = SystemPropUtil.getProp("FILE_SERVER_PORT");
		if (StringUtils.isNotBlank(FILE_SERVER_HOST) && FILE_SERVER_HOST.startsWith("http")) {
			FILE_SERVER_URL = FILE_SERVER_HOST + ":" + FILE_SERVER_PORT;
		} else {
			FILE_SERVER_URL = "http://" + FILE_SERVER_HOST + ":" + FILE_SERVER_PORT;
		}
		log.info("File server url=[{}]", FILE_SERVER_URL);
	}

}
