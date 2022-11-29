package com.cyberpeace.situation.service.impl;

import org.springframework.stereotype.Service;

import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Override
	public DashboardBasicAttributeRes getDashboardBasicAttribute(String dashboardId) {

		DashboardBasicAttributeRes data = new DashboardBasicAttributeRes();
		data.setDashboardId(dashboardId);
		data.setTaskId(dashboardId);

		return data;

	}

}
