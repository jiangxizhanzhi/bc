package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.context.SessionToken;

import java.util.List;

public interface RemoteSupersetService {

	SessionToken getToken();

	String createDashboard(String dashboardName);

	void deleteDashboard(List<String> dashboardIds);

	JSONObject getDashboardDetail(String id);

	JSONObject getDashboardPermalink(String id);
}
