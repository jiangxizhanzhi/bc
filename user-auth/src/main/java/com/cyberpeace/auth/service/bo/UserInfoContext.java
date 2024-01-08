package com.cyberpeace.auth.service.bo;

import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.SpringContextUtil;

public class UserInfoContext {

	private static final ThreadLocal<SysUser> userContext = new ThreadLocal<>();

	private UserInfoContext() {}

	public static SysUser getNewUserContext() {
		if (userContext.get() == null) {
			SysUserService sysUserService = SpringContextUtil.getBeanByClass(SysUserService.class);
			SysUser sysUser = sysUserService.getCurrent();
			userContext.set(sysUser);
		}
		return userContext.get();
	}

	public static void setUserContext(SysUser user) {
		userContext.set(user);
	}

	public static void removeUserContext() {
		userContext.remove();
	}

	public static UserInfo getUserContext() {
		SysUser sysUser = getNewUserContext();
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
