package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.mp.MpLog;
import com.cyberpeace.integratation.service.RemoteLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 远程日志Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `RemoteLogServiceImpl` 的 Spring 服务类，实现了 `RemoteLogService` 接口。以下是该代码的主要功能和结构的简要分析： 
 * ### 类定义与注解 
 * - **类名**：`RemoteLogServiceImpl` 
 * - **包名**：`com.cyberpeace.integratation.service.impl` 
 * - **注解**： 
 * - `@Slf4j`：使用 Lombok 自动生成日志记录器。 
 * - `@Service`：表明这是一个 Spring 服务组件。 
 * - `@Async`：用于异步执行方法。 
 * ### 成员变量 
 * - **`mpEnvService`**：通过 `@Autowired` 注入的 `MpEnvService` 对象，用于获取中台环境的相关配置。 
 * - **`LOG_ADD_URL`**：一个字符串变量，用于存储日志添加的 URL 地址。在 `init()` 方法中初始化。 
 * ### 初始化方法 
 * - **`@PostConstruct` 注解的方法 `init()`**： 
 * - 在 bean 初始化后调用。 
 * - 设置 `LOG_ADD_URL` 为 `mpEnvService` 获取的主机地址加上配置文件中的日志添加路径。 
 * ### 主要方法 
 * #### `addLog(MpLog mpLog)` 
 * - **功能**：异步地将日志发送到指定的 URL。 
 * - **参数**：`MpLog` 对象，包含日志信息。 
 * - **异常处理**：如果发送失败，则记录错误日志。 
 * #### `addBasicInfo(UserInfo userInfo, OperationModule operationModule, OperationType operationType, String operationTarget)` 
 * - **功能**：创建并填充 `MpLog` 对象的基本信息。 
 * - **参数**： 
 * - `userInfo`：用户信息。 
 * - `operationModule`：操作模块。 
 * - `operationType`：操作类型。 
 * - `operationTarget`：操作目标。 
 * - **返回值**：填充后的 `MpLog` 对象。 
 * #### `addCrLog(UserInfo userInfo, OperationModule operationModule, OperationType operationType, String operationTarget)` 
 * - **功能**：添加靶场日志。 
 * - **参数**： 
 * - `userInfo`：用户信息。 
 * - `operationModule`：操作模块。 
 * - `operationType`：操作类型。 
 * - `operationTarget`：操作目标。 
 * - **步骤**： 
 * - 调用 `addBasicInfo` 方法生成基本日志信息。 
 * - 设置日志内容。 
 * - 调用 `addLog` 方法发送日志。 
 * #### `addOjLog(UserInfo userInfo, OperationModule operationModule, OperationType operationType, String operationTarget, String content)` 
 * - **功能**：添加 OJ 日志。 
 * - **参数**： 
 * - `userInfo`：用户信息。 
 * - `operationModule`：操作模块。 
 * - `operationType`：操作类型。 
 * - `operationTarget`：操作目标。 
 * - `content`：日志内容。 
 * - **步骤**： 
 * - 调用 `addBasicInfo` 方法生成基本日志信息。 
 * - 设置日志内容。 
 * - 调用 `addLog` 方法发送日志。 
 * ### 总结 
 * 这个服务类主要负责管理和发送日志信息到远程服务器。它通过 `addLog` 方法异步发送日志，并提供了两个具体的方法 `addCrLog` 和 `addOjLog` 来处理不同类型的操作日志。这些方法首先生成日志的基本信息，然后设置具体内容，并最终发送到远程服务器。 
**/
@Slf4j
@Service
public class RemoteLogServiceImpl implements RemoteLogService {

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 日志新增网址
	 */
	private String LOG_ADD_URL;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		LOG_ADD_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.log.add");
	}

	/**
	 * 新增日志
	 *
	 * @param mpLog 中台日志
	 */
	@Async
	public void addLog(MpLog mpLog) {
		try {
			HttpClientUtil.post(LOG_ADD_URL, mpLog, JSONObject.class);
		} catch (Exception e) {
			log.error("Failed to create remote operation log.", e);
		}
	}

	/**
	 * 新增基本信息
	 *
	 * @param userInfo        用户信息
	 * @param operationModule 实操模块
	 * @param operationType   实操类型
	 * @param operationTarget 实操目标
	 * @return {@code MpLog }
	 */
	private MpLog addBasicInfo(UserInfo userInfo, OperationModule operationModule,
			OperationType operationType, String operationTarget) {

		MpLog mpLog = new MpLog();

		// 记录用户信息
		mpLog.setUserId(userInfo.getUserId());
		mpLog.setUserName(userInfo.getUserName());

		// 记录操作对象信息
		mpLog.setOpTarget(operationTarget);
		mpLog.setOpModule(operationModule.getCode());
		mpLog.setOpType(operationType.getCode());

		return mpLog;
	}

	/**
	 * 新增靶场日志
	 *
	 * @param userInfo        用户信息
	 * @param operationModule 实操模块
	 * @param operationType   实操类型
	 * @param operationTarget 实操目标
	 */
	@Override
	public void addCrLog(UserInfo userInfo, OperationModule operationModule, OperationType operationType,
						 String operationTarget) {

		// 处理基础信息
		MpLog mpLog = addBasicInfo(userInfo, operationModule, operationType, operationTarget);

		// 拼接操作描述
		mpLog.setContent(LogContent.CR_FORMAT.format(operationType, operationModule, operationTarget));
		addLog(mpLog);

	}

	/**
	 * 新增OJ日志
	 *
	 * @param userInfo        用户信息
	 * @param operationModule 实操模块
	 * @param operationType   实操类型
	 * @param operationTarget 实操目标
	 * @param content         内容
	 */
	@Override
	public void addOjLog(UserInfo userInfo, OperationModule operationModule,
			OperationType operationType, String operationTarget, String content) {

		// 处理基础信息
		MpLog mpLog = addBasicInfo(userInfo, operationModule, operationType, operationTarget);

		// 拼接操作描述
		mpLog.setContent(content);

		addLog(mpLog);
	}
}

