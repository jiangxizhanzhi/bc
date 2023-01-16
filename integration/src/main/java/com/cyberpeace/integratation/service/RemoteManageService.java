package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.manage.ServiceLogContext;

public interface RemoteManageService {

	Boolean reportStatus();

	Boolean writeLog(ServiceLogContext serviceLogContext);

}
