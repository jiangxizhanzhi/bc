package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;

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

	List<UserInfo> getList();

	List<UserInfo> getLazyUserList(UserPageContext userPageContext);

	List<UserInfo> getByIdList(List<String> ids);

	Map<String, String> getIdNameMap(List<String> ids);
}
