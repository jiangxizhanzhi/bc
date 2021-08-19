package com.cyberpeace.oj.rtks.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.oj.rtks.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Override
	public IPage<Role> searchRolesByName(Page<Role> page, String keyword) {
		return this.getBaseMapper().listRole(page, keyword);
	}

	@Override
	public void ensureRoleExists(String roleId) {
		Role role = getById(roleId);
		if (role == null) {
			throw new ResourceNotFoundException("岗位" + roleId + "未找到");
		}
	}

}