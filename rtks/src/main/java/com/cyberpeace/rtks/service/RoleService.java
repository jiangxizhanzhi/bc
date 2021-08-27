package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Role;

public interface RoleService extends BaseService<Role> {

	/**
	 * 获取层级id下的岗位列表
	 * */
	List<Role> getRolesByLevelId(String levelId);

	/**
	 * 给定一个id的数组，返回level_id在该数组中的岗位id列表
	 * */
	List<String> getRoleIdsByLevelIds(List<String> ids);

}
