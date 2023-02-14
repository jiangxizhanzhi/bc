package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.monitor.MonitorDataRes;

public interface FlowReportService {

	/**
	 * 上报行为仪表监控数据
	 * @param monitorData
	 */
	void reportMonitorData(MonitorDataRes monitorData);

	/**
	 * 上报行为仪表创建请求体
	 * @param reqString
	 */
	void reportCreation(String reqString);
}
