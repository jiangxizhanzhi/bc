package com.cyberpeace.integratation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.oj.integratation.model.mp.MpAuthenticatedUser;
import com.cyberpeace.oj.integratation.service.RemoteUserService;
import com.cyberpeace.oj.integratation.service.mp.HttpClient;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteUserServiceImpl implements RemoteUserService {

	private String TOKEN_AUTHENTICATE_URL;

	private String USERINFO_BY_ID_URL;

	private String USERINFO_LIST_URL;

	private String MP_HOST;

	private String MP_PORT;

	private String MP_URL;

	@PostConstruct
	public void init() {
		TOKEN_AUTHENTICATE_URL = ConfigUtil.getContextProperty("token.authenticate");
		USERINFO_BY_ID_URL = ConfigUtil.getContextProperty("userinfo.by.id");
		USERINFO_LIST_URL = ConfigUtil.getContextProperty("userinfo.list");
		MP_HOST = SystemPropUtil.getProp("MP_HOST");
		MP_PORT = SystemPropUtil.getProp("MP_PORT");

		MP_URL = "http://" + MP_HOST + ":" + MP_PORT;

		log.info("mp host=[{}], port=[{}]", MP_HOST, MP_PORT);
	}

	@Override
	public UserInfo doAuthentication(String token) {
		// 1、调用mp
		String targetUrl = MP_URL + TOKEN_AUTHENTICATE_URL + token;

		MpAuthenticatedUser mpAuthenticatedUser = HttpClientUtil.rawGet(targetUrl,
				MpAuthenticatedUser.class);

		// 2、转化mp的用户模型为本地模型
		UserInfo userInfo = convertToLocal(mpAuthenticatedUser);

		return userInfo;
	}

	@Override
	public UserInfo getById(String userId) {
		HttpClient httpClient = new HttpClient();
		JSONObject jsonObject = httpClient.get("/api/mp/auth/users/" + userId);
		return userInfoFromJsonObject(jsonObject);
	}

	@Override
	public List<UserInfo> getList() {
		HttpClient httpClient = new HttpClient();
		JSONArray jsonArray = httpClient.getList("/api/mp/auth/org_users/page/byorg");
		List<UserInfo> userInfoList = new ArrayList<>();
		for (Object object : jsonArray) {
			userInfoList.add(userInfoFromJsonObject((JSONObject) object));
		}
		return userInfoList;
	}

	private UserInfo userInfoFromJsonObject(JSONObject jsonObject) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(jsonObject.getObject("id", String.class));
		userInfo.setUserName(jsonObject.getObject("username", String.class));
		userInfo.setAvatarUrl(jsonObject.getObject("avatar", String.class));
		List<String> org_ids = jsonObject.getJSONArray("org_ids").toJavaList(String.class);
		if (org_ids.size() > 0) {
			userInfo.setOrganizationId(org_ids.get(0));
			// userInfo.setOrganizationData("");
		}
		// userInfo.setRoles();
		userInfo.setAdmin((int) jsonObject.get("is_admin") == 0);
		userInfo.setSuperuser((int) jsonObject.get("is_super_user") == 0);
		return userInfo;
	}

	/**
	 * mp 用户模型到本地到映射
	 * @param mpAuthenticatedUser
	 * @return
	 */
	private UserInfo convertToLocal(MpAuthenticatedUser mpAuthenticatedUser) {
		UserInfo userInfo = new UserInfo();

		userInfo.setRoles(Lists.newArrayList());
		userInfo.setAvatarUrl(mpAuthenticatedUser.getAvatar());
		userInfo.setUserName(mpAuthenticatedUser.getName());
		userInfo.setUserId(mpAuthenticatedUser.getId());

		return userInfo;
	}

}
