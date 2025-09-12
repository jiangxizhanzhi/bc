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
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
public interface RemoteUserService {

	/**
	 * 用户认证
	 *
	 * @param token jwttoken
	 * @return 认证成功返回用户模型
	 * @throws
	 * @see String
	 * @see UserInfo
	 */
	UserInfo doAuthentication(String token);

	/**
	 * 获取由id
	 *
	 * @param userId 用户id
	 * @return {@code UserInfo }
	 * @throws
	 * @see String
	 * @see UserInfo
	 */
	UserInfo getById(String userId);


    List<String> getOnlineIds(List<String> searchedIds);

	/**
	 * 获取列表
	 *
	 * @param page         分页
	 * @param perPage      每分页
	 * @param pattern      模式
	 * @param organization 组织
	 * @return {@code JSONObject }
	 * @throws
	 * @see Long
	 * @see Long
	 * @see String
	 * @see String
	 * @see JSONObject
	 */
	JSONObject getList(Long page, Long perPage, String pattern, String organization);

	/**
	 * 获取模糊查询用户列表
	 *
	 * @param userPageContext 用户分页上下文
	 * @return {@code List<UserInfo> }
	 * @throws
	 * @see UserPageContext
	 * @see List<UserInfo>
	 */
	List<UserInfo> getFuzzyQueryUserList(UserPageContext userPageContext);

	/**
	 * 获取由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<UserInfo> }
	 * @throws
	 * @see List<String>
	 * @see List<UserInfo>
	 */
	List<UserInfo> getByIdList(List<String> ids);

	/**
	 * 获取id名称映射
	 *
	 * @param ids id列表
	 * @return {@code Map<String, String> }
	 * @throws
	 * @see List<String>
	 * @see Map<String, String>
	 */
	Map<String, String> getIdNameMap(List<String> ids);

	/**
	 * 通过实体列表查询用户名映射
	 *
	 * @param entities     实体列表
	 * @param userIdGetter 获取用户名的方法
	 * @return {@code Map<String, String>}
	 * @throws
	 * @see List<T>
	 * @see Function<T, String>
	 * @see Map<String, String>
	 */
	<T> Map<String, String> getIdNameMap(List<T> entities, Function<T, String> userIdGetter);

	/**
	 * 获取id用户信息映射
	 *
	 * @param userIds 用户id列表
	 * @return {@code Map<String, UserInfo> }
	 * @throws
	 * @see List<String>
	 * @see Map<String, UserInfo>
	 */
	Map<String, UserInfo> getIdUserInfoMap(List<String> userIds);

	/**
	 * 查询组织下的用户信息
	 *
	 * @param organizationId
	 * @param children       子级
	 * @return
	 * @throws
	 * @see String
	 * @see boolean
	 * @see List<UserInfo>
	 */
	List<UserInfo> ListByOrgId(String organizationId, boolean children);

	/**
	 * 获取组织下的用户编号
	 *
	 * @param orgId
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> idsByOrgId(String orgId);

	/**
	 * 获取用户许可
	 *
	 * @param userId 用户id
	 * @return {@code Set<String> }
	 * @throws
	 * @see String
	 * @see Set<String>
	 */
	Set<String> getUserPermission(String userId);

	/**
	 * 获取mp所有用户
	 *
	 * @return
	 * @throws
	 * @see List<UserInfo>
	 */
	List<UserInfo> getAllUsers();

	/**
	 * 全部用户
	 *
	 * @return {@code Map<String, UserInfo> }
	 * @throws
	 * @see Map<String, UserInfo>
	 */
	Map<String, UserInfo> allUsers();

	/**
	 * 清除缓存
	 *
	 * @throws
	 */
	void clearCache();

	/**
	 * 根据userId搜索用户信息
	 *
	 * @param userId    查询条件
	 * @param userInfos 查询目标数组
	 * @return 用户信息
	 * @throws
	 * @see String
	 * @see List<UserInfo>
	 * @see UserInfo
	 */
	UserInfo searchUserByUserIdInUserList(String userId, List<UserInfo> userInfos);

	/**
	 * 是否演示开启
	 *
	 * @return {@code Boolean }
	 * @throws
	 * @see Boolean
	 */
	Boolean isDemoOpen();
}
