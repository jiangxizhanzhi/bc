package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

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

	@PostConstruct
	public void init() {
		String outerHostGl = "http://" + ConfigUtil.getContextProperty("outer.host.gl");
		GL_REPORT_STATUS = outerHostGl + ConfigUtil.getContextProperty("gl.report.status");
		GL_REPORT_LOG = outerHostGl + ConfigUtil.getContextProperty("gl.report.log");
	}

	@Override
	public Boolean reportStatus() {
		for (ServiceStatusContext.ServiceName serviceName : ServiceStatusContext.ServiceName
				.values()) {
			ServiceStatusContext serviceStatusContext = new ServiceStatusContext(
					serviceName.getCode(), serviceName.getDesc());
			HttpClientUtil.post(GL_REPORT_STATUS, serviceStatusContext, String.class);
		} ;
		return true;
	}

	@Override
	public Boolean writeLog(ServiceLogContext serviceLogContext) {
		String result = HttpClientUtil.post(GL_REPORT_LOG, serviceLogContext, String.class);
		return true;
	}

}
