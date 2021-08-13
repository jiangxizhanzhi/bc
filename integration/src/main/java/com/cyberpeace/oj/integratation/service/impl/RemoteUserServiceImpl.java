package com.cyberpeace.oj.integratation.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.common.exception.RemoteUserAuthticationException;
import com.cyberpeace.oj.common.model.UserInfo;
import com.cyberpeace.oj.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

@Service
public class RemoteUserServiceImpl implements RemoteUserService {

	@Override
	public UserInfo doAuthentication(String token) throws RemoteUserAuthticationException {

		// TODO
		// 调用mp实现
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(token);

		List<String> roles = Lists.newArrayList();
		if (StringUtils.equals(token, "123")) {
			roles.add("Admin");
		} else {
			roles.add("jiaoyuan");
			roles.add("xueyuan");
		}

		userInfo.setRoles(roles);

		return userInfo;
	}

}
