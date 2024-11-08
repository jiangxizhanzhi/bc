package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.model.UserPageContext;

/**
 * 远程用户Service
 *
 * @author SN
 * @date 2024/11/08
 */
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
}
