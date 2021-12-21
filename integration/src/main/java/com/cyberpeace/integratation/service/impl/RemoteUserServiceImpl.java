package com.cyberpeace.integratation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.util.StreamApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CacheConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.model.mp.MpAuthenticatedUser;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@CacheConfig(cacheNames = { CacheConstant.REMOTE_USER })
@Slf4j
@Service
public class RemoteUserServiceImpl implements RemoteUserService {

	private String TOKEN_AUTHENTICATE_URL;

	private String USERINFO_BY_ID_URL;

	private String USERINFO_LIST_URL;

	private String ORG_INFO_LIST_URL;

	@Autowired
	private MpEnvService mpEnvService;

	@PostConstruct
	public void init() {
		TOKEN_AUTHENTICATE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("token.authenticate");
		USERINFO_BY_ID_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("userinfo.by.id");
		USERINFO_LIST_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("userinfo.list");
		ORG_INFO_LIST_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.org.user");
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

		if (null == jsonObject || jsonObject.isEmpty()) {
			return null;
		}
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
	public List<UserInfo> getLazyUserList(UserPageContext userPageContext) {
		List<UserInfo> userInfoList = new ArrayList<>();

		if (userPageContext.getIds().length() > 0) {
			List jsonArray = (List) HttpClientUtil
					.rawGetByParam(userPageContext, USERINFO_LIST_URL, JSONObject.class)
					.get("list");

			for (Object object : jsonArray) {
				userInfoList.add(userInfoFromJsonObject((JSONObject) object));
			}
		}

		return userInfoList;
	}

	@Override
	public List<UserInfo> getByIdList(List<String> ids) {
		UserPageContext userPageContext = new UserPageContext();
		userPageContext.setIds(String.join(",", ids));
		return getLazyUserList(userPageContext);
	}

	@Override
	public Map<String, String> getIdNameMap(List<String> ids) {
		Set<String> idsSet = Sets.newHashSet(ids);
		List<UserInfo> userInfos = getByIdList(ids);
		if (idsSet.size() != userInfos.size()) {
			for (UserInfo info : userInfos) {
				idsSet.remove(info.getUserId());
			}
			log.error("invalid user_id: " + idsSet);
		}
		return userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getName));
	}

	@Override
	public List<UserInfo> ListByOrgId(String organizationId) {
		JSONArray userArray = HttpClientUtil.rawGet(ORG_INFO_LIST_URL + organizationId,
				JSONArray.class);

		List<UserInfo> userInfos = StreamApiUtils.map(userArray,
				obj -> userInfoFromJsonObject((JSONObject) obj));
		return userInfos;
	}

	/**
	 * 用户数据转换
	 * @param jsonObject
	 * @return
	 */
	private UserInfo userInfoFromJsonObject(JSONObject jsonObject) {

		if (null == jsonObject) {
			return null;
		}

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
		userInfo.setOrgpaths((List<List<String>>) jsonObject.get("orgpaths"));
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

		UserInfo byId = getById(mpAuthenticatedUser.getId());
		userInfo.setOrganizationId(byId.getOrganizationId());

		return userInfo;
	}

}
