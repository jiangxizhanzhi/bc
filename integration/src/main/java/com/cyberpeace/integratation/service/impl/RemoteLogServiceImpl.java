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
 * @version 1.0.0
 * @date 2024/11/14
 * @see RemoteLogService
 * @since 1.0.0
 */
@Slf4j
@Service
public class RemoteLogServiceImpl implements RemoteLogService {

	/**
	 * 中台环境Service
	 *
	 * @see MpEnvService
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 日志新增网址
	 *
	 * @see String
	 */
	private String LOG_ADD_URL;

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	public void init() {
		LOG_ADD_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.log.add");
	}

	/**
	 * 新增日志
	 *
	 * @param mpLog 中台日志
	 * @throws
	 * @see MpLog
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
	 * @throws
	 * @see UserInfo
	 * @see OperationModule
	 * @see OperationType
	 * @see String
	 * @see MpLog
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
	 * @throws
	 * @see UserInfo
	 * @see OperationModule
	 * @see OperationType
	 * @see String
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
	 * @throws
	 * @see UserInfo
	 * @see OperationModule
	 * @see OperationType
	 * @see String
	 * @see String
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
