package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfoContext;
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
        LOG_ADD_URL = mpEnvService.getMpHost()
                + ConfigUtil.getContextProperty("mp.log.add");
    }

    @Override
    @Async
    public void addLog(MpLog mpLog) {
        HttpClientUtil.post(LOG_ADD_URL, mpLog, JSONObject.class);
    }

    @Override
    public void addCrLog(OperationModule operationModule, OperationType operationType, String operationTarget) {

        try {
            MpLog mpLog = new MpLog();

            // 记录用户信息
            String userName = UserInfoContext.getUserContext().getUserName();
            mpLog.setUser_id(UserInfoContext.getUserContext().getUserId());
            mpLog.setUser_name(userName);

            // 记录操作对象信息
            mpLog.setOp_target(operationTarget);
            mpLog.setOp_module(operationModule.getCode());
            mpLog.setOp_type(operationType.getCode());

            // 拼接操作描述
            mpLog.setContent(String.format("%s%s了%s[%s]", userName, operationType.getDesc(), operationModule.getDesc(),
                    operationTarget));
            addLog(mpLog);
        } catch (Exception e) {
            log.error("Failed to create remote operation log.", e);
        }
    }

}
