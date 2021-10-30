package com.cyberpeace.integratation.service;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.cyberpeace.common.model.user.UserInfo;

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

	List<UserInfo> getLazyUserList(List<BasicNameValuePair> nvps);
}
