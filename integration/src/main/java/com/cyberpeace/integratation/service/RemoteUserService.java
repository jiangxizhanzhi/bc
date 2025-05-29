package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.model.UserPageContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * 远程用户Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `RemoteUserService` 的接口，该接口主要用于处理与远程用户相关的操作。以下是对其主要功能的简要分析： 
 * ### 接口概述 
 * - **包名**：`com.cyberpeace.integratation.service` 
 * - **作者**：SN 
 * - **日期**：2024年11月8日 
 * ### 主要方法 
 * 1. **用户认证 (`doAuthentication`)**： 
 * - 参数：`String token` (JWT token) 
 * - 返回值：`UserInfo`（如果认证成功） 
 * 2. **获取用户信息 (`getById`)**： 
 * - 参数：`String userId` 
 * - 返回值：`UserInfo` 
 * 3. **获取用户列表 (`getList`)**： 
 * - 参数：`Long page`, `Long perPage`, `String pattern`, `String organization` 
 * - 返回值：`JSONObject` 
 * 4. **获取模糊查询用户列表 (`getFuzzyQueryUserList`)**： 
 * - 参数：`UserPageContext userPageContext` 
 * - 返回值：`List<UserInfo>` 
 * 5. **获取多个用户信息 (`getByIdList`)**： 
 * - 参数：`List<String> ids` 
 * - 返回值：`List<UserInfo>` 
 * 6. **获取ID到名称的地图 (`getIdNameMap`)**： 
 * - 参数：`List<String> ids` 
 * - 返回值：`Map<String, String>` 
 * - 另一个重载版本接受泛型列表和函数来获取ID到名称的地图 
 * 7. **获取ID到用户信息的地图 (`getIdUserInfoMap`)**： 
 * - 参数：`List<String> userIds` 
 * - 返回值：`Map<String, UserInfo>` 
 * 8. **根据组织ID查询用户信息 (`ListByOrgId`)**： 
 * - 参数：`String organizationId`, `boolean children` 
 * - 返回值：`List<UserInfo>` 
 * 9. **获取组织下的用户ID列表 (`idsByOrgId`)**： 
 * - 参数：`String orgId` 
 * - 返回值：`List<String>` 
 * 10. **获取用户权限 (`getUserPermission`)**： 
 * - 参数：`String userId` 
 * - 返回值：`Set<String>` 
 * 11. **获取所有用户 (`getAllUsers`)**： 
 * - 返回值：`List<UserInfo>` 
 * 12. **获取所有用户的信息 (`allUsers`)**： 
 * - 返回值：`Map<String, UserInfo>` 
 * 13. **清除缓存 (`clearCache`)**： 
 * - 无参数，无返回值 
 * 14. **根据用户ID搜索用户信息 (`searchUserByUserIdInUserList`)**： 
 * - 参数：`String userId`, `List<UserInfo> userInfos` 
 * - 返回值：`UserInfo` 
 * 15. **检查演示模式是否开启 (`isDemoOpen`)**： 
 * - 返回值：`Boolean` 
 * 16. **获取数字空间token (`getCyberToken`)**： 
 * - 返回值：`String` 
 * ### 总结 
 * 该接口提供了丰富的功能，包括用户认证、获取用户信息、查询用户列表、获取用户权限等。这些方法覆盖了用户管理的大部分需求，并且使用了泛型和函数式编程来增加灵活性。此外，还提供了一些辅助方法如缓存管理和演示模式检查。 
**/
public interface RemoteUserService {

	/**
	 * 用户认证
	 *
	 * @param token jwttoken
	 * @return 认证成功返回用户模型
	 */
	UserInfo doAuthentication(String token);

	/**
	 * 获取由id
	 *
	 * @param userId 用户id
	 * @return {@code UserInfo }
	 */
	UserInfo getById(String userId);

	/**
	 * 获取列表
	 *
	 * @param page         分页
	 * @param perPage      每分页
	 * @param pattern      模式
	 * @param organization 组织
	 * @return {@code JSONObject }
	 */
	JSONObject getList(Long page, Long perPage, String pattern, String organization);

	/**
	 * 获取模糊查询用户列表
	 *
	 * @param userPageContext 用户分页上下文
	 * @return {@code List<UserInfo> }
	 */
	List<UserInfo> getFuzzyQueryUserList(UserPageContext userPageContext);

	/**
	 * 获取由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<UserInfo> }
	 */
	List<UserInfo> getByIdList(List<String> ids);

	/**
	 * 获取id名称地图
	 *
	 * @param ids id列表
	 * @return {@code Map<String, String> }
	 */
	Map<String, String> getIdNameMap(List<String> ids);

	/**
	 * 通过实体列表查询用户名映射
	 *
	 * @param entities     实体列表
	 * @param userIdGetter 获取用户名的方法
	 * @return {@code Map<String, String>}
	 */
	<T> Map<String, String> getIdNameMap(List<T> entities, Function<T, String> userIdGetter);

	/**
	 * 获取id用户信息地图
	 *
	 * @param userIds 用户id列表
	 * @return {@code Map<String, UserInfo> }
	 */
	Map<String, UserInfo> getIdUserInfoMap(List<String> userIds);

	/**
	 * 查询组织下的用户信息
	 *
	 * @param organizationId
	 * @param children       子级
	 * @return
	 */
	List<UserInfo> ListByOrgId(String organizationId, boolean children);

	/**
	 * 获取组织下的用户编号
	 *
	 * @param orgId
	 * @return
	 */
	List<String> idsByOrgId(String orgId);

	/**
	 * 获取用户许可
	 *
	 * @param userId 用户id
	 * @return {@code Set<String> }
	 */
	Set<String> getUserPermission(String userId);

	/**
	 * 获取mp所有用户
	 *
	 * @return
	 */
	List<UserInfo> getAllUsers();

	/**
	 * 所有用户
	 *
	 * @return {@code Map<String, UserInfo> }
	 */
	Map<String, UserInfo> allUsers();

	/**
	 * 清除缓存
	 */
	void clearCache();

	/**
	 * 根据userId搜索用户信息
	 *
	 * @param userId    查询条件
	 * @param userInfos 查询目标数组
	 * @return 用户信息
	 */
	UserInfo searchUserByUserIdInUserList(String userId, List<UserInfo> userInfos);

	/**
	 * 是演示开启
	 *
	 * @return {@code Boolean }
	 */
	Boolean isDemoOpen();

	/**
	 * 获取数字空间token
	 * @return
	 */
	String getCyberToken();
}

