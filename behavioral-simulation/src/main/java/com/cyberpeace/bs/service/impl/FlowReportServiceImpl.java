package com.cyberpeace.bs.service.impl;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.bs.service.FlowReportService;
import com.cyberpeace.bs.service.bo.monitor.MonitorDataRes;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流量上报Service实现
 *
 * @author SN
 * @date 2023/05/08
 */
@Service
@Slf4j
public class FlowReportServiceImpl implements FlowReportService {

    private static String MONITOR_REPORT_URL = "/api/mp/flow/flows/behavior_count_report";
    private static String CREATION_REPORT_URL = "/api/mp/flow/flows/behavior_task_report";

    @Autowired
    private MpEnvService mpEnvService;

    @Override
    public void reportMonitorData(MonitorDataRes monitorData) {
        // 组装上报地址
        String targetUrl = mpEnvService.getMpHost() + MONITOR_REPORT_URL;
        String body = JsonUtil.toJsonString(monitorData, PropertyNamingStrategy.SnakeCase);
        // 日志输出上报内容
        log.info(body);
        // 上报监控数据
        HttpClientUtil.post(targetUrl, body, String.class);
    }

    @Override
    public void reportCreation(String reqString) {
        // 组装请求地址
        String targetUrl = mpEnvService.getMpHost() + CREATION_REPORT_URL;
        // 发送请求
        HttpClientUtil.post(targetUrl, reqString, String.class);
    }
}
