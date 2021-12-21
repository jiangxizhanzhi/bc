package com.cyberpeace.integratation.service;

import java.util.List;

public interface RemoteOrganizationService {

	/**
	 * 获取子类组织编号
	 * @param orgId 组织编号
	 * @param self 是否包含自身
	 * @return
	 */
	List<String> childrenList(String orgId, boolean self);

	/**
	 * 获取组织名称
	 * @param orgId
	 * @return
	 */
	String orgNameById(String orgId);

}
