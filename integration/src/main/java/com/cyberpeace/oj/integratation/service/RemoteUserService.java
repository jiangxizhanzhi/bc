package com.cyberpeace.oj.integratation.service;

import com.cyberpeace.oj.common.exception.RemoteUserAuthticationException;
import com.cyberpeace.oj.common.model.UserInfo;

public interface RemoteUserService {

	/**
	 * 用户认证
	 * @param token
	 * @return 认证成功返回用户模型
	 */
	UserInfo doAuthentication(String token) throws RemoteUserAuthticationException;

}
