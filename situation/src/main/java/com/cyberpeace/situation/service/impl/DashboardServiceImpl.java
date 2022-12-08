package com.cyberpeace.situation.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private DashboardAttributeMapper dashboardAttributeMapper;

	@Override
	public DashboardBasicAttributeRes getDashboardBasicAttribute(String dashboardId) {

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper.getById(dashboardId);

		if (dashboardAttribute != null) {
			DashboardBasicAttributeRes dashboardBasicAttributeRes = BeanUtils
					.deepCopy(dashboardAttribute, DashboardBasicAttributeRes.class);
			dashboardBasicAttributeRes
					.setViewAngle(BeanUtils.deepCopy(dashboardAttribute, ViewAngle.class));
			return dashboardBasicAttributeRes;

		}

		DashboardBasicAttributeRes data = new DashboardBasicAttributeRes();

		if ("lgzx".equals(dashboardId)) {
			data = new DashboardBasicAttributeRes(dashboardId, 1, "LG中心综合态势", null, null, null,
					new Date(), new ViewAngle("108.768", "33.712", 6115168));
		} else if ("zhxfbc".equals(dashboardId)) {
			data = new DashboardBasicAttributeRes(dashboardId, 2, "XXX分靶场综合态势", null, "7",
					null, new Date(), new ViewAngle());
		} else {
			data = new DashboardBasicAttributeRes(dashboardId, 3, "ZH性分靶场任务态势", "XXXX红蓝对抗任务", null,
					dashboardId, new Date(), new ViewAngle("104.086", "30.649", 1463));
		}

		return data;

	}

}
