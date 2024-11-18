package com.cyberpeace.common.model.cache;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.model.user.UserInfo;

import lombok.Data;

/**
 * rtks 匹配度缓存
 *
 * @author SN@version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RtksMatch implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	private String orgId;

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	private String roleId;

	/**
	 * 岗位名称
	 *
	 * @see String
	 */
	private String roleName;

	/**
	 * 火柴
	 *
	 * @see Integer
	 */
	private Integer match;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	private String orgName;

	/**
	* RTKS 比赛
	*
	* @param userInfo 用户信息
	*
	*
	*/
	public RtksMatch(UserInfo userInfo) {
		this.userId = userInfo.getUserId();
		this.name = userInfo.getName();
		this.orgId = userInfo.getOrganizationId();
		this.orgName = getOrgName(userInfo.getOrgpaths());
	}

	/**
	* 获取组织名称
	*
	* @param orgPath 组织路径
	* @return {@code String }
	*
	*/
	private String getOrgName(List<List<String>> orgPath) {
		int size = orgPath.get(0).size();
		return orgPath.get(0).get(size - 1);
	}

}
