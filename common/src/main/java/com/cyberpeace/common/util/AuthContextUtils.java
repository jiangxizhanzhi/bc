package com.cyberpeace.common.util;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;

/**
 * 权限获取
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public class AuthContextUtils {

	/**
	 * 获取权限数据
	 *
	 * @param userInfo 用户信息
	 * @param isWeb    是 Web
	 * @return AuthContext
	 * @throws
	 * @see UserInfo
	 * @see UserInfo
	 * @see UserInfo
	 */
	public static AuthContext constructAuth(UserInfo userInfo, boolean isWeb) {
		AuthContext authContext = new AuthContext();
		authContext.setUserId(userInfo.getUserId());
		authContext.setOrganization(userInfo.getOrganizationId());
		authContext.setIsWeb(isWeb);
		authContext.setIsSuperAdmin(userInfo.isSuperuser());
		authContext.setIsAdmin(userInfo.isAdmin());

		return authContext;
	}

}
