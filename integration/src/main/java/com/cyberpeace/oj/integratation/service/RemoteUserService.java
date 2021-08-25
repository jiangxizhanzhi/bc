package com.cyberpeace.oj.integratation.service;

import com.cyberpeace.oj.common.exception.RemoteUserAuthticationException;
import com.cyberpeace.oj.common.model.UserInfo;

import java.util.List;

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
}
