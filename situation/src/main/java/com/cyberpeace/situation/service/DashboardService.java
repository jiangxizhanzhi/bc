package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;

public interface DashboardService {

	/**
	 * 获取大屏基本属性
	 * @param dashboardId
	 * @return
	 */
	DashboardBasicAttributeRes getDashboardBasicAttribute(String dashboardId);

	/**
	 * 通过大屏ID获取大屏ID
	 * @param taskId
	 * @param subjectId
	 * @return String
	 */
	DashboardIdRes getAttributeByTaskId(String taskId, String subjectId);

	DashboardIdRes getAttributeByDashboardId(String dashboardId);

	DashboardAttribute getDashboardAttribute(String dashboardId);

}
