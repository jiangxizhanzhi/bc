package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	@Override
	public Role getOr404(String roleId) {
		Role role = getById(roleId);
		if (role == null) {
			throw new ResourceNotFoundException("岗位" + roleId + "未找到");
		}
		return role;
	}

	@Override
	public List<Role> getRolesByLevelId(String levelId) {
		return roleMapper.getRolesUnderLevel(levelId);
	}

	@Override
	public List<String> getRoleIdsByLevelIds(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<>();
		}
		return roleMapper.getRoleIdsByLevelIds(ids);
	}

}