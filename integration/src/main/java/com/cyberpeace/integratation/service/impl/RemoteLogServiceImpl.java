package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
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

}
