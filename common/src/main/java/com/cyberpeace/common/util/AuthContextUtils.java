package com.cyberpeace.common.util;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;

/**
 * 权限获取
 */
/**
 * 这段代码定义了一个工具类 `AuthContextUtils`，用于根据用户信息和上下文参数构造 `AuthContext` 对象。以下是代码的主要分析： 
 * 1. **类与方法功能**： 
 * - 类名为 `AuthContextUtils`，表明其主要作用是提供权限相关的工具方法。 
 * - 方法 `constructAuth` 用于根据传入的 `UserInfo` 和布尔值 `isWeb` 构造一个 `AuthContext` 对象。 
 * 2. **输入参数**： 
 * - `UserInfo userInfo`：包含用户详细信息的对象，如用户 ID、组织 ID、是否为超级管理员等。 
 * - `boolean isWeb`：表示当前请求是否来自 Web 端。 
 * 3. **输出结果**： 
 * - 返回一个 `AuthContext` 对象，封装了用户的权限相关信息。 
 * 4. **逻辑说明**： 
 * - 创建 `AuthContext` 对象后，设置其属性： 
 * - `userId`：从 `userInfo` 中提取用户 ID。 
 * - `organization`：从 `userInfo` 中提取组织 ID。 
 * - `isWeb`：直接使用传入的布尔值。 
 * - `isSuperAdmin`：调用 `userInfo.isSuperuser()` 判断是否为超级管理员。 
 * - `isAdmin`：调用 `userInfo.isAdmin()` 判断是否为普通管理员。 
 * 5. **设计意图**： 
 * - 将用户信息和上下文参数抽象为统一的 `AuthContext` 对象，便于后续权限验证或操作。 
 * 总结：该工具类简化了权限上下文的构建过程，提高了代码复用性和可维护性。 
**/
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

}

