package com.cyberpeace.common.model.user;

/**
 * 用户信息上下文
 *
 * @author SN
 * @date 2024/11/08
 */
public class UserInfoContext {

	/**
	 * 用户上下文
	 */
	private static final ThreadLocal<UserInfo> userContext = new ThreadLocal();

	/**
	 * 用户信息上下文
	 */
	private UserInfoContext() {}

	/**
	 * 获取用户上下文
	 *
	 * @return {@code UserInfo }
	 */
	public static UserInfo getUserContext() {
		return userContext.get();
	}

	/**
	 * 设置用户上下文
	 *
	 * @param userInfo 用户信息
	 */
	public static void setUserContext(UserInfo userInfo) {
		userContext.set(userInfo);
	}

	/**
	 * 删除用户上下文
	 */
	public static void removeUserContext() {
		userContext.remove();
	}
}
