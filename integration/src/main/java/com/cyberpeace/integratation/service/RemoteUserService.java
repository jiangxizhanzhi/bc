package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.model.UserPageContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

	List<UserInfo> getByIdList(List<String> ids);

	Map<String, String> getIdNameMap(List<String> ids);

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

	void setSuperAdmin();

}
