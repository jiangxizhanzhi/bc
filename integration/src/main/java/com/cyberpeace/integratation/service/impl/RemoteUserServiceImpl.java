package com.cyberpeace.integratation.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CacheConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.model.mp.MpAuthenticatedUser;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@CacheConfig(cacheNames = { CacheConstant.REMOTE_USER })
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
		MP_HOST = SystemPropUtil.getProp("MP_HOST");
		MP_PORT = SystemPropUtil.getProp("MP_PORT");
		MP_URL = "http://" + MP_HOST + ":" + MP_PORT;

		TOKEN_AUTHENTICATE_URL = MP_URL + ConfigUtil.getContextProperty("token.authenticate");
		USERINFO_BY_ID_URL = MP_URL + ConfigUtil.getContextProperty("userinfo.by.id");
		USERINFO_LIST_URL = MP_URL + ConfigUtil.getContextProperty("userinfo.list");

		log.info("mp host=[{}], port=[{}]", MP_HOST, MP_PORT);
	}

	@Override
	@Cacheable(key = "#p0")
	public UserInfo doAuthentication(String token) {
		// 1、调用mp
		String targetUrl = TOKEN_AUTHENTICATE_URL + token;

		MpAuthenticatedUser mpAuthenticatedUser = HttpClientUtil.rawGet(targetUrl,
				MpAuthenticatedUser.class);

		// 2、转化mp的用户模型为本地模型
		UserInfo userInfo = convertToLocal(mpAuthenticatedUser);

		return userInfo;
	}

	@Override
	public UserInfo getById(String userId) {
		String targetUrl = USERINFO_BY_ID_URL + userId;
		JSONObject jsonObject = HttpClientUtil.rawGet(targetUrl, JSONObject.class);

		return userInfoFromJsonObject(jsonObject);
	}

	@Override
	public List<UserInfo> getList() {
		List jsonArray = (List) HttpClientUtil.rawGet(USERINFO_LIST_URL, JSONObject.class)
				.get("list");

		List<UserInfo> userInfoList = new ArrayList<>();
		for (Object object : jsonArray) {
			userInfoList.add(userInfoFromJsonObject((JSONObject) object));
		}
		return userInfoList;
	}

	@Override
	public List<UserInfo> getLazyUserList(List<BasicNameValuePair> nvps) {
		List jsonArray1 = (List) HttpClientUtil.rawGet(USERINFO_LIST_URL, JSONObject.class)
				.get("list");
		String url = USERINFO_LIST_URL;
		String paramsStr = "";
		if (nvps.size() > 0) {
			paramsStr = "";
			try {
				HttpEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
				paramsStr = EntityUtils.toString(urlEncodedFormEntity);
				url = url + "?" + paramsStr;
			} catch (IOException e) {
				log.error("error ", e);
			}
		}
		List jsonArray = (List) HttpClientUtil.rawGet(url, JSONObject.class).get("list");

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
		userInfo.setName(jsonObject.getObject("name", String.class));
		userInfo.setAvatarUrl(jsonObject.getObject("avatar", String.class));
		List<String> org_ids = jsonObject.getJSONArray("org_ids").toJavaList(String.class);
		if (org_ids.size() > 0) {
			userInfo.setOrganizationId(org_ids.get(0));
			JSONArray orgs = jsonObject.getJSONArray("orgs");

			for (int i = 0; i < orgs.size(); i++) {
				JSONObject jsonObject1 = orgs.getJSONObject(i);
				if (jsonObject1.containsKey("path")) {
					userInfo.setOrganizationData(jsonObject1.getObject("path", String.class));
				}
			}
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
