package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.rtks.dao.entity.Role;

public interface RoleService extends IService<Role> {

	/**
	 * 根据名称模糊查询岗位
	 */
	IPage<Role> searchRolesByName(Page<Role> page, String keyword);

	/**
	 * 给定一个id，返回对应的岗位，否则报错
	 * */
	Role getOr404(String resourceId);

}
