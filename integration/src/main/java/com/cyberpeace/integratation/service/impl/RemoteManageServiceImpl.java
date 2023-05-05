package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.integratation.model.manage.ServiceStatusContext;
import com.cyberpeace.integratation.service.RemoteManageService;

@Service
public class RemoteManageServiceImpl implements RemoteManageService {

	private String GL_REPORT_STATUS;

	private String GL_REPORT_LOG;

	@Autowired
	private SituationEnvService situationEnvService;

	@PostConstruct
	public void init() {
		String outerHostGl = situationEnvService.getTsGlHost();
		GL_REPORT_STATUS = outerHostGl + ConfigUtil.getContextProperty("gl.report.status");
		GL_REPORT_LOG = outerHostGl + ConfigUtil.getContextProperty("gl.report.log");
	}

	@Override
	public Boolean reportStatus() {
		for (ServiceStatusContext.Service service : ServiceStatusContext.Service.values()) {
			ServiceStatusContext serviceStatusContext = new ServiceStatusContext(service.getCode(),
					service.getDesc());
			HttpClientUtil.tsPost(GL_REPORT_STATUS, serviceStatusContext, String.class,
					"上报态势系统状态失败");

		}
		return true;
	}

	@Override
	public Boolean writeLog(ServiceLogContext.SubModule subModule, ServiceLogContext.Type type) {
		ServiceLogContext serviceLogContext = new ServiceLogContext(subModule, type);
		writeLog(serviceLogContext);
		return true;
	}

	@Async
	public void writeLog(ServiceLogContext serviceLogContext) {
		HttpClientUtil.tsPost(GL_REPORT_LOG, serviceLogContext, String.class, "上报态势系统日志失败");
	}

}
