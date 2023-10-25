package com.cyberpeace.common.model.user;

public class UserInfoContext {

	private static final ThreadLocal<UserInfo> userContext = new ThreadLocal<>();

	private UserInfoContext() {}

	public static UserInfo getUserContext() {
		return userContext.get();
	}

	public static void setUserContext(UserInfo userInfo) {
		userContext.set(userInfo);
	}

	public static void removeUserContext() {
		userContext.remove();
	}
}
