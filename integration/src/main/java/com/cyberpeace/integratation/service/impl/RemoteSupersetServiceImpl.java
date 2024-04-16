package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CacheConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.context.SessionToken;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.SupersetHttpUtil;
import com.cyberpeace.integratation.service.RemoteSupersetService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@CacheConfig(cacheNames = { CacheConstant.REMOTE_SUPERSET })
public class RemoteSupersetServiceImpl implements RemoteSupersetService {

	private String SUPERSET_LOGIN_URL;

	private String CREATE_DASHBOARD_URL;

	private String DELETE_DASHBOARD_URL;

	private String GET_DASHBOARD_DETAIL_URL;

	private String GET_DASHBOARD_PERMALINK_URL;

	@Autowired
	private SupersetEnvService supersetEnvService;

	@PostConstruct
	public void init() {
		SUPERSET_LOGIN_URL = supersetEnvService.getSupersetHost() + ConfigUtil.getContextProperty("superset.login");
		CREATE_DASHBOARD_URL = supersetEnvService.getSupersetHost() + ConfigUtil.getContextProperty("create.dashboard");
		DELETE_DASHBOARD_URL = supersetEnvService.getSupersetHost() + ConfigUtil.getContextProperty("delete.dashboard");
		GET_DASHBOARD_DETAIL_URL = supersetEnvService.getSupersetHost() + ConfigUtil.getContextProperty("get.dashboard.detail");
		GET_DASHBOARD_PERMALINK_URL = supersetEnvService.getSupersetHost() + ConfigUtil.getContextProperty("get.dashboard.permalink");
	}

	@Override
	@Cacheable(key = "#p0")
	public SessionToken getToken(String token) {
		String targetUrl = SUPERSET_LOGIN_URL + token;
		SessionToken sessionToken = SupersetHttpUtil.rawGetLogin(targetUrl);
		String csrfToken = LocalStringUtil.getMatchStr(sessionToken.getCsrfToken(), PatternConstant.CSRF_TOKEN).get(0);
		csrfToken = csrfToken.substring(7, csrfToken.length()-1);
		sessionToken.setCsrfToken(csrfToken);
		String session = LocalStringUtil.getMatchStr(sessionToken.getSession(), PatternConstant.SESSION).get(0);
		session = session.substring(8, session.length()-1);
		sessionToken.setSession(session);
		return sessionToken;
	}

	@Override
	public String createDashboard(String dashboardName) {
		String targetUrl = CREATE_DASHBOARD_URL;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dashboard_title", dashboardName);
		return SupersetHttpUtil.post(targetUrl, jsonObject, JSONObject.class, HttpStatus.SC_CREATED, supersetEnvService.getSupersetHost()).getString("id");
	}

	@Override
	public void deleteDashboard(List<String> dashboardIds) {
		String ids = StringUtils.join(dashboardIds, ",");
		String targetUrl = String.format(DELETE_DASHBOARD_URL, ids);
		SupersetHttpUtil.delete(targetUrl, null, JSONObject.class);
	}

	@Override
	public JSONObject getDashboardDetail(String id) {
		String targetUrl = GET_DASHBOARD_DETAIL_URL + id;
		return SupersetHttpUtil.rawGet(targetUrl, JSONObject.class);
	}

	@Override
	public JSONObject getDashboardPermalink(String id) {
		String targetUrl = String.format(GET_DASHBOARD_PERMALINK_URL, id);
		return SupersetHttpUtil.post(targetUrl, new JSONObject(), JSONObject.class, HttpStatus.SC_CREATED, supersetEnvService.getSupersetHost());
	}

}
