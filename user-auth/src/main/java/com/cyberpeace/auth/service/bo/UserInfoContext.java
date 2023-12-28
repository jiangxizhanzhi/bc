package com.cyberpeace.auth.service.bo;

import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.SpringContextUtil;

public class UserInfoContext {

	private static final ThreadLocal<UserInfo> userContext = new ThreadLocal<>();

	private UserInfoContext() {}

	public static UserInfo getUserContext() {
		if (userContext.get() == null) {
			SysUserService sysUserService = SpringContextUtil.getBeanByClass(SysUserService.class);
			UserInfo userInfo = sysUserService.getCurrent();
			userContext.set(userInfo);
		}
		return userContext.get();
	}

	public static void setUserContext(UserInfo userInfo) {
		userContext.set(userInfo);
	}

	public static void removeUserContext() {
		userContext.remove();
	}

	public static UserInfo getOldUserContext() {
		SysUser sysUser = getUserContext();
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(sysUser.getResourceId());
		userInfo.setName(sysUser.getName());
		userInfo.setUserName(sysUser.getUsername());
		userInfo.setAvatarUrl(sysUser.getAvatar());
		userInfo.setAdmin(sysUser.getAdmin());
		userInfo.setSuperuser(sysUser.getAdmin());
		return userInfo;
	}
}
