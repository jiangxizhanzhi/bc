package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.model.mp.GxToken;
import com.cyberpeace.integratation.model.mp.RemoteUser;
import com.cyberpeace.integratation.service.context.MpUserIds;
import com.cyberpeace.integratation.service.context.MpUserSlimInfo;
import com.cyberpeace.integratation.service.context.RemoteUserInfos;

public interface RemoteUserService {

	/**
	 * 用户认证
	 *
	 * @param token jwttoken
	 * @return 认证成功返回用户模型
	 */
	UserInfo doAuthentication(String token);

	UserInfo getById(String userId);

	// todo getList 未传入分页参数时, 会默认返回10条
	List<UserInfo> getList();

	JSONObject getList(Long page, Long perPage, String pattern, String organization);

	List<UserInfo> getFuzzyQueryUserList(UserPageContext userPageContext);

	List<UserInfo> getByIdList(List<String> ids);

	Map<String, String> getIdNameMap(List<String> ids);

	Map<String, UserInfo> getIdUserInfoMap(List<String> userIds);

	/**
	 * 查询组织下的用户信息
	 * @param organizationId
	 * @return
	 */
	List<UserInfo> ListByOrgId(String organizationId, boolean children);

	/**
	 * mp 通过共性用户获取中台用户token
	 * @param gxToken
	 * @return
	 */
	String getMpUserToken(GxToken gxToken);

	/**
	 * 查询共性平台用户列表
	 * @return
	 */
	List<RemoteUser> getGxUserList();

	/**
	 * 查询共性平台用户token
	 * @return
	 */
	String getGxUserToken(String userId);

	/**
	 * 通过本地用户id批量获取第三方用户id
	 * @return
	 */
	Map<String, String> getMpGxUserIdMap(MpUserIds mpUserIds);

	/**
	 * 通过第三方用户信息获取本地用户信息
	 * @return
	 */
	List<MpUserSlimInfo> getMpUserInfos(RemoteUserInfos remoteUserInfos);

}
