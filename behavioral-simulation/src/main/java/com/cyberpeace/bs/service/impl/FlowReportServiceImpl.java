package com.cyberpeace.bs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.bs.service.FlowReportService;
import com.cyberpeace.bs.service.bo.monitor.MonitorDataRes;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.impl.MpEnvService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlowReportServiceImpl implements FlowReportService {

	private static String MONITOR_REPORT_URL = "/api/mp/flow/flows/behavior_count_report";
	private static String CREATION_REPORT_URL = "/api/mp/flow/flows/behavior_task_report";
	@Autowired
	private MpEnvService mpEnvService;

	@Override
	public void reportMonitorData(MonitorDataRes monitorData) {
		String targetUrl = mpEnvService.getMpHost() + MONITOR_REPORT_URL;
		String body = JsonUtil.toJsonString(monitorData, PropertyNamingStrategy.SnakeCase);
		log.info(body);
		HttpClientUtil.post(targetUrl, body, String.class);
	}

	@Override
	public void reportCreation(String reqString) {
		String targetUrl = mpEnvService.getMpHost() + CREATION_REPORT_URL;
		HttpClientUtil.post(targetUrl, reqString, String.class);
	}
}
