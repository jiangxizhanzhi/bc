package com.cyberpeace.auth.service.bo;

import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.SpringContextUtil;

public class UserInfoContext {

	private static final ThreadLocal<SysUser> userContext = new ThreadLocal<>();

	private UserInfoContext() {}

	public static SysUser getUserContext() {
		if (userContext.get() == null) {
			SysUserService sysUserService = SpringContextUtil.getBeanByClass(SysUserService.class);
			SysUser userInfo = sysUserService.getCurrent();
			userContext.set(userInfo);
		}
		return userContext.get();
	}

	public static void setUserContext(SysUser userInfo) {
		userContext.set(userInfo);
	}

	public static void removeUserContext() {
		userContext.remove();
	}

}
