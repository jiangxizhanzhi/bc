package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;


public interface DashboardService {
    /**
     * 获取大屏基本属性
     * @param dashboardId
     * @return
     */
    DashboardBasicAttributeRes getDashboardBasicAttribute(String dashboardId);


}
