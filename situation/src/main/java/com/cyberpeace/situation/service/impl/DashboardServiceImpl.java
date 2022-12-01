package com.cyberpeace.situation.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Override
	public DashboardBasicAttributeRes getDashboardBasicAttribute(String dashboardId) {

		DashboardBasicAttributeRes data = new DashboardBasicAttributeRes();

		if ("lgzx".equals(dashboardId)) {
			data = new DashboardBasicAttributeRes(dashboardId, 1, "LG中心综合态势", null, null, null,
					new Date(), new ViewAngle("117.2591507", "31.7956434", 10000));
		} else if ("zhxfbc".equals(dashboardId)) {
			data = new DashboardBasicAttributeRes(dashboardId, 2, "XXX分靶场综合态势", null, "zhxfbc",
					null, new Date(), new ViewAngle());
		} else {
			data = new DashboardBasicAttributeRes(dashboardId, 3, "ZH性分靶场任务态势", "XXXX红蓝对抗任务", null,
					dashboardId, new Date(), new ViewAngle("117.2591507", "31.7956434", 10000));
		}

		return data;

	}

}
