package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.mp.MpLog;
import com.cyberpeace.integratation.service.RemoteLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class RemoteLogServiceImpl implements RemoteLogService {

	@Autowired
	private MpEnvService mpEnvService;

	private String LOG_ADD_URL;

	@PostConstruct
	public void init() {
		LOG_ADD_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.log.add");
	}

	private void addLog(MpLog mpLog) {
		try {
			HttpClientUtil.post(LOG_ADD_URL, mpLog, JSONObject.class);
		} catch (Exception e) {
			log.error("Failed to create remote operation log.", e);
		}
	}

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

	@Override
	@Async
	public void addCrLog(UserInfo userInfo, OperationModule operationModule,
			OperationType operationType, String operationTarget) {

		// 处理基础信息
		MpLog mpLog = addBasicInfo(userInfo, operationModule, operationType, operationTarget);

		// 拼接操作描述
		mpLog.setContent(String.format("%s%s了%s[%s]", userInfo.getUserName(),
				operationType.getDesc(), operationModule.getDesc(), operationTarget));

		addLog(mpLog);

	}

	@Override
	@Async
	public void addOjLog(UserInfo userInfo, OperationModule operationModule,
			OperationType operationType, String operationTarget, String cotent) {

		// 处理基础信息
		MpLog mpLog = addBasicInfo(userInfo, operationModule, operationType, operationTarget);

		// 拼接操作描述
		mpLog.setContent(String.format("%s%s了%s，%s", userInfo.getUserName(),
				operationType.getDesc(), operationModule.getDesc(), cotent));

		addLog(mpLog);
	}
}
