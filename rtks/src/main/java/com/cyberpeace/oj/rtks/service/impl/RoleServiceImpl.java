package com.cyberpeace.oj.rtks.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil2;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.oj.rtks.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Override
	public ListRsp<Role> searchRolesByName(ListReq<Role> listReq) {
		IPage<Role> page = this.getBaseMapper().selectPage(listReq.toPageParam(),
				new QueryWrapper<Role>().like("name", listReq.getKeyword()));
		return PageUtil2.getListResult(page);
	}

	@Override
	public void ensureRoleExists(String roleId) {
		Role role = getById(roleId);
		if (role == null) {
			throw new ResourceNotFoundException("岗位" + roleId + "未找到");
		}
	}

}