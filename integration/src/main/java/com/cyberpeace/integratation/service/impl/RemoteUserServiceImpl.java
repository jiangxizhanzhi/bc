package com.cyberpeace.integratation.service.impl;

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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 远程用户Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个Java类 `RemoteUserServiceImpl`，实现了 `RemoteUserService` 接口。这个类主要用于处理远程用户的管理和服务，包括用户认证、获取用户信息、缓存管理和一些辅助功能。以下是详细的分析： 
 * ### 类和接口声明 
 * - `RemoteUserServiceImpl` 类实现了 `RemoteUserService` 接口。 
 * - 使用了 Lombok 的 `@Slf4j` 注解来简化日志记录。 
 * - 使用了 Spring 的 `@Service` 注解，表明这是一个 Spring 服务组件。 
 * ### 成员变量 
 * - 包含多个字符串变量，用于存储不同的URL地址（如认证URL、用户信息URL等）。 
 * - `allUserCache`：使用 Guava 缓存来缓存所有用户的信息，缓存过期时间为15分钟。 
 * ### 自动注入依赖 
 * - `MpEnvService` 和 `CyberSpaceMpEnvService`：用于获取环境相关的配置信息。 
 * - `RemoteOrganizationService`：用于获取组织信息。 
 * ### 初始化方法 
 * - `init()` 方法在类初始化时被调用，用于初始化各种URL地址。 
 * ### 主要方法 
 * 1. **用户认证** 
 * - `doAuthentication(String token)`：通过提供的token进行用户认证，并将MP模型的用户信息转换为本地模型。 
 * 2. **获取用户信息** 
 * - `getById(String userId)`：通过用户ID获取用户信息。 
 * - `getList(Long page, Long perPage, String pattern, String organization)`：根据分页、模式和组织信息获取用户列表。 
 * - `getByIdList(List<String> ids)`：通过用户ID列表获取用户信息。 
 * - `getAllUsers()`：从缓存中获取所有用户信息。 
 * - `allUsers()`：获取所有用户信息并转换为Map形式。 
 * 3. **缓存管理** 
 * - `clearCache()`：清除所有缓存条目。 
 * 4. **其他功能** 
 * - `getFuzzyQueryUserList(UserPageContext userPageContext)`：通过模糊查询获取用户列表。 
 * - `getUserPermission(String userId)`：获取指定用户的所有权限。 
 * - `searchUserByUserIdInUserList(String userId, List<UserInfo> userInfos)`：在用户列表中搜索指定ID的用户。 
 * - `isDemoOpen()`：检查是否开启了演示模式。 
 * - `getCyberToken()`：获取数字中心的token。 
 * 5. **辅助方法** 
 * - `userInfoFromJsonObject(JSONObject jsonObject)`：将JSON对象转换为 `UserInfo` 对象。 
 * - `convertToLocal(MpAuthenticatedUser mpAuthenticatedUser)`：将MP模型的用户信息转换为本地模型。 
 * - `userToOrgUserInfo(JSONObject jsonObject, String orgId)`：将组织用户数据转换为 `UserInfo` 对象。 
 * ### 总结 
 * `RemoteUserServiceImpl` 类提供了一系列处理远程用户管理的功能，包括用户认证、信息获取、缓存管理和权限管理等。它利用了多种工具和框架（如Spring、Guava缓存、FastJSON等），使得代码结构清晰且功能强大。 
**/
@CacheConfig(cacheNames = { CacheConstant.REMOTE_USER })
@Slf4j
@Service
public class RemoteUserServiceImpl implements RemoteUserService {

	/**
	 * 令牌身份验证 URL
	 */
	private String TOKEN_AUTHENTICATE_URL;

	/**
	 * 用户信息由id网址
	 */
	private String USERINFO_BY_ID_URL;

	/**
	 * userinfo 列表 URL
	 */
	private String USERINFO_LIST_URL;

	/**
	 * 组织信息列表 URL
	 */
	private String ORG_INFO_LIST_URL;

	/**
	 * 身份验证权限菜单 URL
	 */
	private String AUTH_PERMISSION_MENUS_URL;

	/**
	 * 用户信息id列表网址
	 */
	private String USERINFO_IDS_URL;

	/**
	 * 黑色启用 URL
	 */
	private String BLACK_ENABLE_URL;

	/**
	 * 数字中心登陆地址
	 */
	private String CYBER_LOGIN_URL;

	/**
	 * 所有用户缓存
	 */
	private final LoadingCache<String, List<UserInfo>> allUserCache = CacheBuilder.newBuilder()
			.expireAfterWrite(15, TimeUnit.MINUTES)
			.build(new CacheLoader<String, List<UserInfo>>() {

				@Override
				public List<UserInfo> load(String key) {
					return getList(null, 0L, null, null).getJSONArray("list")
							.toJavaList(UserInfo.class);
				}
			});

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 数字中心环境
	 */
	@Autowired
	private CyberSpaceMpEnvService cyberSpaceMpEnvService;

	/**
	 * 远程组织Service
	 */
	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	/**
	 * 初始化
	 */
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
		BLACK_ENABLE_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.auth.blacklist.enabled");
		CYBER_LOGIN_URL = cyberSpaceMpEnvService.getMpHost() + ConfigUtil.getContextProperty("cyber.login");
	}

	/**
	 * 执行身份验证
	 *
	 * @param token 令 牌
	 * @return {@code UserInfo }
	 */
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

	/**
	 * 获取由id
	 *
	 * @param userId 用户id
	 * @return {@code UserInfo }
	 */
	@Override
	public UserInfo getById(String userId) {
		String targetUrl = USERINFO_BY_ID_URL + userId;
		JSONObject jsonObject = HttpClientUtil.rawGet(targetUrl, JSONObject.class);

		if (null == jsonObject || jsonObject.isEmpty()) {
			return null;
		}
		return userInfoFromJsonObject(jsonObject);
	}

	/**
	 * 获取列表
	 *
	 * @param page         分页
	 * @param perPage      每分页
	 * @param pattern      模式
	 * @param organization 组织
	 * @return {@code JSONObject }
	 */
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
				log.error("获取用户列表失败，URL: {}", USERINFO_LIST_URL, e);
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

	/**
	 * 获取由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<UserInfo> }
	 */
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

	/**
	 * 获取id名称地图
	 *
	 * @param ids id列表
	 * @return {@code Map<String, String> }
	 */
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

	/**
	 * 获取id名称地图
	 *
	 * @param entities     实体
	 * @param userIdGetter 用户id吸气剂
	 * @return {@code Map<String, String> }
	 */
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

	/**
	 * 获取id用户信息地图
	 *
	 * @param ids id列表
	 * @return {@code Map<String, UserInfo> }
	 */
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

	/**
	 * 列表由组织id
	 *
	 * @param organizationId 组织id
	 * @param children       子级
	 * @return {@code List<UserInfo> }
	 */
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

	/**
	 * id列表由组织id
	 *
	 * @param orgId 组织id
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> idsByOrgId(String orgId) {
		List<UserInfo> userInfos = ListByOrgId(orgId, true);
		return StreamApiUtils.map(userInfos, UserInfo::getUserId);
	}

	/**
	 * 编辑角色以后清空缓存
	 */
	@Override
	@CacheEvict(allEntries = true)
	public void clearCache() {

 	}

	/**
	 * 搜索用户由用户id在用户列表
	 *
	 * @param userId    用户id
	 * @param userInfos 用户信息
	 * @return {@code UserInfo }
	 */
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
	 * 是演示开启
	 *
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean isDemoOpen() {
		return HttpClientUtil.rawGet(BLACK_ENABLE_URL, Boolean.class);
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

	/**
	 * 获取都用户
	 *
	 * @return {@code List<UserInfo> }
	 */
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

	/**
	 * 所有用户
	 *
	 * @return {@code Map<String, UserInfo> }
	 */
	@Override
	public Map<String, UserInfo> allUsers() {
		List<UserInfo> userInfos = getList(null, 0L, null, null).getJSONArray("list")
				.toJavaList(UserInfo.class);

		return StreamApiUtils.toMap(userInfos, UserInfo::getUserId, userInfo -> userInfo);
	}

	@Override
	public String getCyberToken() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("account", "super");
		jsonObject.put("password", "123456");
        return HttpClientUtil.post(CYBER_LOGIN_URL, jsonObject);
	}

	/**
	 * 组织用户数据转
	 *
	 * @param jsonObject
	 * @param orgId      组织id
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

		return userInfo;
	}

}

