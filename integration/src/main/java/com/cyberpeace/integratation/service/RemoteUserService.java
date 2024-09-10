package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.model.UserPageContext;

public interface RemoteUserService {

	/**
	 * 用户认证
	 *
	 * @param token jwttoken
	 * @return 认证成功返回用户模型
	 */
	UserInfo doAuthentication(String token);

	UserInfo getById(String userId);

	JSONObject getList(Long page, Long perPage, String pattern, String organization);

	List<UserInfo> getFuzzyQueryUserList(UserPageContext userPageContext);

	List<UserInfo> pageUsersByOrg(UserPageContext userPageContext);

	List<UserInfo> getByIdList(List<String> ids);

	Map<String, String> getIdNameMap(List<String> ids);

	/**
	 * 通过实体列表查询用户名映射
	 *
	 * @param entities 实体列表
	 * @param userIdGetter 获取用户名的方法
	 * @return {@code Map<String, String>}
	 */
	<T> Map<String, String> getIdNameMap(List<T> entities, Function<T, String> userIdGetter);

	Map<String, UserInfo> getIdUserInfoMap(List<String> userIds);

	/**
	 * 查询组织下的用户信息
	 * @param organizationId
	 * @return
	 */
	List<UserInfo> ListByOrgId(String organizationId, boolean children);

	/**
	 * 获取组织下的用户编号
	 * @param orgId
	 * @return
	 */
	List<String> idsByOrgId(String orgId);

	Set<String> getUserPermission(String userId);

	/**
	 * 获取mp所有用户
	 * @return
	 */
	List<UserInfo> getAllUsers();

	Map<String, UserInfo> allUsers();

    void clearCache();

	/**
	 * 根据userId搜索用户信息
	 * @param userId 查询条件
	 * @param userInfos 查询目标数组
	 * @return 用户信息
	 */
	UserInfo searchUserByUserIdInUserList(String userId, List<UserInfo> userInfos);

	Boolean isDemoOpen();
}
