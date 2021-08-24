package com.cyberpeace.oj.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.exception.InvalidParamException;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.oj.rtks.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	@Override
	public IPage<Role> listEntity(Page<Role> page, String keyword) {
		return roleMapper.listRole(page, keyword);
	}

	@Override
	public Role getOr404(String roleId) {
		Role role = getById(roleId);
		if (role == null) {
			throw new ResourceNotFoundException("岗位" + roleId + "未找到");
		}
		return role;
	}

	@Override
	public void ensureLevelNotChildRole(String levelId) {
		List<Role> roles = roleMapper.getRolesUnderLevel(levelId);
		if (roles.size() > 0) {
			throw new InvalidParamException("层级" + levelId + "已绑定岗位");
		}
	}

}