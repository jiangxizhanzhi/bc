package com.cyberpeace.common.model.cache;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.model.user.UserInfo;

import lombok.Data;

/**
 * rtks 匹配度缓存
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class RtksMatch implements Serializable {

	private String userId;

	private String orgId;

	private String roleId;

	private String roleName;

	private Integer match;

	private String name;

	private String orgName;

	/**
	 * rtk匹配
	 *
	 * @param userInfo 用户信息
	 */
	public RtksMatch(UserInfo userInfo) {
		this.userId = userInfo.getUserId();
		this.name = userInfo.getName();
		this.orgId = userInfo.getOrganizationId();
		this.orgName = getOrgName(userInfo.getOrgpaths());
	}

	/**
	 * 得到组织名字
	 *
	 * @param orgPath org路径
	 * @return {@link String}
	 */
	private String getOrgName(List<List<String>> orgPath) {
		int size = orgPath.get(0).size();
		return orgPath.get(0).get(size - 1);
	}

}
