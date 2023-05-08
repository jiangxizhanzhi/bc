package com.cyberpeace.common.model.user;

/**
 * 用户信息环境
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class UserInfoContext {

	private static final ThreadLocal<UserInfo> userContext = new ThreadLocal();

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
