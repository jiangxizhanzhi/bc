package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.mp.MpLog;

public interface RemoteLogService {

    /**
     * 新增操作日志
     */
    void addLog(MpLog mpLog);

}
