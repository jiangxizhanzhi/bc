package com.cyberpeace.oj.rtks.service;

import com.cyberpeace.oj.rtks.dao.entity.Role;

public interface RoleService extends BaseService<Role> {

	/**
	 * 确保没有绑定在给定层级id下的岗位，否则报错
	 * */
	void ensureLevelNotChildRole(String levelId);

}
