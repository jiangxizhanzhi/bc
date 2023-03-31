package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.sun.org.apache.bcel.internal.generic.RET;

/**
 * 权限获取
 */
public class AuthContextUtils {

	/**
	 * 获取权限数据
	 * @param userInfo
	 * @param isWeb
	 * @return
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

	public static void validateBuiltin(Boolean builtin) {
		Boolean trail = UserInfoContext.getUserContext().getIsTrialAccount();
		if (trail && builtin.equals(true)) {
			throw new BusinessException(ErrorCode.TRAIL_UNAUTHORIZED);
		}
	}

	//需要禁止试用时返回true
	public static Boolean checkBuiltin(String createUserId, String userId) {
		return createUserId.equals(CommonConstant.ADMIN_ID);
	}
}
