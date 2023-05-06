package com.cyberpeace.common.util;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;

/**
 * 身份验证上下文跑龙套
 * 权限获取
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class AuthContextUtils {

	/**
	 * 构建身份验证
	 * 获取权限数据
	 *
	 * @param userInfo 用户信息
	 * @param isWeb    是网络
	 * @return {@link AuthContext}
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
