package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.mp.MpLog;

public interface RemoteLogService {

    /**
     * 新增操作日志
     */
    void addLog(MpLog mpLog);

    void addCrLog(OperationModule operationModule, OperationType operationType, String operationTarget);

}
