package com.cyberpeace.integratation.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CacheConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.model.mp.MpAuthPermission;
import com.cyberpeace.integratation.model.mp.MpAuthenticatedUser;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
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

	private String AUTH_PERMISSION_MENUS_URL;

	private String USERINFO_IDS_URL;

	private final LoadingCache<String, List<UserInfo>> allUserCache = CacheBuilder.newBuilder()
			.expireAfterWrite(15, TimeUnit.MINUTES)
			.build(new CacheLoader<String, List<UserInfo>>() {

				@Override
				public List<UserInfo> load(String key) {
					return getList(null, 0L, null, null).getJSONArray("list")
							.toJavaList(UserInfo.class);
				}
			});

	@Autowired
	private MpEnvService mpEnvService;

	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@PostConstruct
	public void init() {
		TOKEN_AUTHENTICATE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("token.authenticate");
		USERINFO_BY_ID_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("userinfo.by.id");
		USERINFO_LIST_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("userinfo.list");
		ORG_INFO_LIST_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.org.user");
		AUTH_PERMISSION_MENUS_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("mp.auth.permission.menus");
		USERINFO_IDS_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("userinfo.ids");
	}

	@Override
	@Cacheable(key = "#p0")
	public UserInfo doAuthentication(String token) {
		// 1、调用mp
		String targetUrl = TOKEN_AUTHENTICATE_URL + token;

		MpAuthenticatedUser mpAuthenticatedUser = HttpClientUtil.rawGet(targetUrl,
				MpAuthenticatedUser.class);

		// 2、转化mp的用户模型为本地模型
		return convertToLocal(mpAuthenticatedUser);
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
	public JSONObject getList(Long page, Long perPage, String pattern, String organization) {
		String targetUrl = USERINFO_LIST_URL;
		if (!targetUrl.endsWith("?")) {
			targetUrl += "?";
		}

		// 参数拼接
		List<String> queryParams = Lists.newArrayList();
		if (page != null) {
			queryParams.add(String.format("page=%s", page));
		}
		if (perPage != null) {
			queryParams.add(String.format("per_page=%s", perPage));
		}
		if (pattern != null) {
			// 对特殊字符转义
			try {
				pattern = java.net.URLEncoder.encode(pattern, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			queryParams.add(String.format("username=%s", pattern));
		}
		if (organization != null) {
			queryParams.add(String.format("org=%s", organization));
		}

		targetUrl += String.join("&", queryParams);

		JSONObject jsonResponse = HttpClientUtil.rawGet(targetUrl, JSONObject.class);
		List jsonArray = (List) jsonResponse.get("list");
		List<UserInfo> userInfoList = new ArrayList<>();
		for (Object object : jsonArray) {
			userInfoList.add(userInfoFromJsonObject((JSONObject) object));
		}
		JSONObject result = new JSONObject();
		result.put("total", jsonResponse.getInteger("total"));
		result.put("list", userInfoList);

		return result;
	}

	/**
	 * 姓名、用户名模糊查询
	 *
	 * @param userPageContext
	 * @return
	 */
	@Override
	@Cacheable(cacheNames = "getFuzzyQueryuser", key = "#p0.ids+#p0.username+#p0.page+#p0.perPage")
	public List<UserInfo> getFuzzyQueryUserList(UserPageContext userPageContext) {
		List<UserInfo> userInfoList = new ArrayList<>();

		List jsonArray = (List) HttpClientUtil
				.rawGetByParam(userPageContext, USERINFO_LIST_URL, JSONObject.class).get("list");

		for (Object object : jsonArray) {
			userInfoList.add(userInfoFromJsonObject((JSONObject) object));
		}

		return userInfoList;
	}

	@Override
	public List<UserInfo> getByIdList(List<String> ids) {
		List<UserInfo> userInfoList = new ArrayList<>();

		if (CollectionUtils.isEmpty(ids)) {
			return userInfoList;
		}

		JSONArray jsonArray = HttpClientUtil.post(USERINFO_IDS_URL, ids, JSONArray.class);
		for (Object json : jsonArray) {
			UserInfo userInfo = userInfoFromJsonObject((JSONObject) json);
			userInfoList.add(userInfo);
		}

		return userInfoList;
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
	public <T> Map<String, String> getIdNameMap(List<T> entities, Function<T, String> userIdGetter) {

		// 如果传入的实体列表为空，则直接返回空map
		if (CollectionUtils.isEmpty(entities)) {
			return Collections.emptyMap();
		}

		// 使用方法参数提取用户ID列表，忽略结果为空的值，并对结果进行去重
		List<String> userIds = entities.stream().map(userIdGetter).filter(StringUtils::isNotBlank)
				.distinct().collect(Collectors.toList());

		return getIdNameMap(userIds);
	}

	@Override
	public Map<String, UserInfo> getIdUserInfoMap(List<String> ids) {
		Set<String> idsSet = Sets.newHashSet(ids);
		List<UserInfo> userInfos = getByIdList(ids);
		if (idsSet.size() != userInfos.size()) {
			for (UserInfo info : userInfos) {
				idsSet.remove(info.getUserId());
			}
			log.error("invalid user_id: " + idsSet);
		}
		return userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, pc -> pc));
	}

	@Override
	public List<UserInfo> ListByOrgId(String organizationId, boolean children) {

		List<String> orgIds = Lists.newArrayList();
		if (children) {
			List<String> childrenList = remoteOrganizationService.childrenList(organizationId,
					true);
			orgIds.addAll(childrenList);
		} else {
			orgIds.add(organizationId);
		}

		List<UserInfo> userInfos = Lists.newArrayList();

		for (String orgId : orgIds) {
			JSONArray userArray = HttpClientUtil.rawGet(ORG_INFO_LIST_URL + orgId, JSONArray.class);
			List<UserInfo> orgUsers = StreamApiUtils.map(userArray,
					obj -> userToOrgUserInfo((JSONObject) obj, organizationId));
			userInfos.addAll(orgUsers);
		}

		return userInfos;
	}

	@Override
	public List<String> idsByOrgId(String orgId) {
		List<UserInfo> userInfos = ListByOrgId(orgId, true);
		return StreamApiUtils.map(userInfos, UserInfo::getUserId);
	}

	/**
	 * 编辑角色以后清空缓存
	 *

	 * @return
	 */
	@Override
	@CacheEvict(allEntries = true)
	public void clearCache() {

 	}

	@Override
	public UserInfo searchUserByUserIdInUserList(String userId, List<UserInfo> userInfos) {
		if(StringUtils.isEmpty(userId) || CollectionUtils.isEmpty(userInfos)) {
			return null;
		}
		for(UserInfo userInfo : userInfos) {
			if(!userId.equals(userInfo.getUserId())) {
				continue;
			}
			return userInfo;
		}
		return null;
	}

	/**
	 * 获取用户可见的菜单
	 *
	 * @param userId
	 * @return
	 */
	@Override
	@Cacheable(key = "#p0")
	public Set<String> getUserPermission(String userId) {
		String targetUrl = AUTH_PERMISSION_MENUS_URL + userId;
		// 获取该用户下对应多个角色可看见菜单的合集
		JSONArray objects = HttpClientUtil.rawGet(targetUrl, JSONArray.class);
		List<MpAuthPermission> mpAuthPermissions = objects.toJavaList(MpAuthPermission.class);

		return mpAuthPermissions.stream().map(MpAuthPermission::getIdentifier)
				.collect(Collectors.toSet());
	}

	@Override
	public List<UserInfo> getAllUsers() {
		List<UserInfo> res = Lists.newArrayList();
		try {
			res = allUserCache.get("allUsers");
		} catch (ExecutionException e) {
			log.error("executionException", e);
			throw new BusinessException(ErrorCode.INTERNAL_ERROR);
		}
		return res;
	}

	@Override
	public Map<String, UserInfo> allUsers() {
		List<UserInfo> userInfos = getList(null, 0L, null, null).getJSONArray("list")
				.toJavaList(UserInfo.class);

		return StreamApiUtils.toMap(userInfos, UserInfo::getUserId, userInfo -> userInfo);
	}

	/**
	 * 组织用户数据转
	 *
	 * @param jsonObject
	 * @return
	 */
	private UserInfo userToOrgUserInfo(JSONObject jsonObject, String orgId) {
		if (null == jsonObject) {
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(jsonObject.getString("id"));
		userInfo.setUserName(jsonObject.getString("username"));
		userInfo.setName(jsonObject.getString("name"));
		userInfo.setAvatarUrl(jsonObject.getString("avatar"));
		userInfo.setOrganizationId(orgId);

		return userInfo;
	}

	/**
	 * 用户数据转换
	 *
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
		userInfo.setRoles(jsonObject.getJSONArray("roles").toJavaList(JSONObject.class));
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
		userInfo.setAdmin((int) jsonObject.get("is_admin") == 1);
		userInfo.setSuperuser((int) jsonObject.get("is_super_user") == 1);
		return userInfo;
	}

	/**
	 * mp 用户模型到本地到映射
	 *
	 * @param mpAuthenticatedUser
	 * @return
	 */
	private UserInfo convertToLocal(MpAuthenticatedUser mpAuthenticatedUser) {
		UserInfo userInfo = new UserInfo();

		userInfo.setRoles(Lists.newArrayList());
		userInfo.setAvatarUrl(mpAuthenticatedUser.getAvatar());
		userInfo.setName(mpAuthenticatedUser.getName());
		userInfo.setUserName(mpAuthenticatedUser.getUsername());
		userInfo.setUserId(mpAuthenticatedUser.getId());

		UserInfo byId = getById(mpAuthenticatedUser.getId());
		userInfo.setOrganizationId(byId.getOrganizationId());
		userInfo.setSuperuser(byId.isSuperuser());
		userInfo.setAdmin(byId.isAdmin());

		userInfo.setCreateTime(mpAuthenticatedUser.getCreateTime());
		return userInfo;
	}

}
