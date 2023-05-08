package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.monitor.MonitorDataRes;

/**
 * 流量上报Service
 *
 * @author SN
 * @date 2023/05/08
 */
public interface FlowReportService {

    /**
     * 上报行为仪表监控数据
     *
     * @param monitorData 监控数据
     */
    void reportMonitorData(MonitorDataRes monitorData);

    /**
     * 上报行为仪表创建请求体
     *
     * @param reqString 请求体字符串
     */
    void reportCreation(String reqString);
}
